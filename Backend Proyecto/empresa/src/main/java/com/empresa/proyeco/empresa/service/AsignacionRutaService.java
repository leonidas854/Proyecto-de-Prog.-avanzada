package com.empresa.proyeco.empresa.service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.proyeco.empresa.DTO.AsignacionRutaDTO;
import com.empresa.proyeco.empresa.DTO.AsignacionRutaRequestDTO;
import com.empresa.proyeco.empresa.model.AsignacionRuta;
import com.empresa.proyeco.empresa.model.Vehiculo;
import com.empresa.proyeco.empresa.repository.AsignacionRutaRepository;
import com.empresa.proyeco.empresa.repository.VehiculoRepository;

@Service
public class AsignacionRutaService {

    @Autowired
    private AsignacionRutaRepository asignacionRutaRepository;

    @Autowired
    private VehiculoRepository vehiculoRepository;

    public AsignacionRutaDTO asignarRuta(AsignacionRutaRequestDTO request) {
        try {
            Optional<Vehiculo> vehiculoOpt = vehiculoRepository.findById(request.getVehiculoId());
            if (vehiculoOpt.isEmpty()) {
                throw new RuntimeException("Vehículo no encontrado con ID: " + request.getVehiculoId());
            }
    
            Vehiculo vehiculo = vehiculoOpt.get();
            String detallesConcatenados = request.getDetalles().stream()
                    .map(punto -> String.format("Lat: %.6f, Lng: %.6f", punto.get("lat"), punto.get("lng")))
                    .collect(Collectors.joining("; "));
    
            AsignacionRuta asignacion = AsignacionRuta.builder()
                    .nombreRuta(request.getNombreRuta())
                    .vehiculo(vehiculo)
                    .estado("Pendiente")
                    .fechadeAsignacion(LocalDateTime.now())
                    .detalles(detallesConcatenados)
                    .build();
    
            AsignacionRuta savedAsignacion = asignacionRutaRepository.save(asignacion);
    
            return AsignacionRutaDTO.builder()
                    .idAsignacion(savedAsignacion.getIdAsignacion())
                    .nombreRuta(savedAsignacion.getNombreRuta())
                    .vehiculoId(savedAsignacion.getVehiculo().getIdVehiculo())
                    .estado(savedAsignacion.getEstado())
                    .fechadeAsignacion(savedAsignacion.getFechadeAsignacion())
                    .detalles(savedAsignacion.getDetalles())
                    .build();
        } catch (Exception e) {
            throw new RuntimeException("Error al asignar ruta: " + e.getMessage(), e);
        }
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