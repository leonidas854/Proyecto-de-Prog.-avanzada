package com.empresa.proyeco.empresa.service;

import com.empresa.proyeco.empresa.DTO.AsignacionRutaDTO;
import com.empresa.proyeco.empresa.DTO.AsignacionRutaRequestDTO;
import com.empresa.proyeco.empresa.model.AsignacionRuta;
import com.empresa.proyeco.empresa.model.Vehiculo;
import com.empresa.proyeco.empresa.repository.AsignacionRutaRepository;
import com.empresa.proyeco.empresa.repository.VehiculoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class AsignacionRutaService {

    @Autowired
    private AsignacionRutaRepository asignacionRutaRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public AsignacionRutaDTO asignarRuta(AsignacionRutaRequestDTO request) {
        // Buscar vehículo por ID
        Optional<Vehiculo> vehiculoOpt = vehiculoRepository.findById(request.getVehiculoId());
        if (vehiculoOpt.isEmpty()) {
            throw new RuntimeException("Vehículo no encontrado con ID: " + request.getVehiculoId());
        }

        Vehiculo vehiculo = vehiculoOpt.get();

        // Convertir detalles a cadena
        String detallesConcatenados = request.getDetalles().stream()
                .map(punto -> String.format("Lat: %.6f, Lng: %.6f", punto.get("lat"), punto.get("lng")))
                .collect(Collectors.joining("; "));

        // Crear la asignación
        AsignacionRuta asignacion = AsignacionRuta.builder()
                .nombreRuta(request.getNombreRuta())
                .vehiculo(vehiculo)
                .estado("Pendiente")
                .fechadeAsignacion(LocalDateTime.now())
                .detalles(detallesConcatenados)
                .build();

        // Guardar en la base de datos
        AsignacionRuta savedAsignacion = asignacionRutaRepository.save(asignacion);

        // Retornar como DTO
        return AsignacionRutaDTO.builder()
                .idAsignacion(savedAsignacion.getIdAsignacion())
                .nombreRuta(savedAsignacion.getNombreRuta())
                .vehiculoId(savedAsignacion.getVehiculo().getIdVehiculo())
                .estado(savedAsignacion.getEstado())
                .fechadeAsignacion(savedAsignacion.getFechadeAsignacion())
                .detalles(savedAsignacion.getDetalles())
                .build();
    }
    public AsignacionRutaDTO obtenerAsignacionPorPlaca(String placa) {
        Vehiculo vehiculo = vehiculoRepository.findByPlaca(placa)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con la placa: " + placa));

        AsignacionRuta asignacion = asignacionRutaRepository.findByVehiculo(vehiculo)
                .orElseThrow(() -> new RuntimeException("No hay asignaciones para este vehículo."));

        return AsignacionRutaDTO.builder()
                .idAsignacion(asignacion.getIdAsignacion())
                .nombreRuta(asignacion.getNombreRuta())
                .vehiculoId(asignacion.getVehiculo().getIdVehiculo())
                .estado(asignacion.getEstado())
                .fechadeAsignacion(asignacion.getFechadeAsignacion())
                .detalles(asignacion.getDetalles())
                .build();
    }
}