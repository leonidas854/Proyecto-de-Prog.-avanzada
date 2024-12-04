package com.empresa.proyeco.empresa.service;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.proyeco.empresa.DTO.VehiculoDTO;
import com.empresa.proyeco.empresa.model.Prediccion;
import com.empresa.proyeco.empresa.model.Vehiculo;
import com.empresa.proyeco.empresa.repository.PrediccionRepository;
import com.empresa.proyeco.empresa.repository.VehiculoRepository;

@Service
public class TransporteService {
    

    @Autowired
    private VehiculoRepository vehiculoRepository;

    @Autowired
    private PrediccionRepository prediccionRepository;

    public List<VehiculoDTO> simularTransporte(String nombrePrediccion) {
        // Buscar predicción por nombre
        Prediccion prediccion = prediccionRepository.findByNombre(nombrePrediccion)
                .orElseThrow(() -> new IllegalArgumentException("Predicción no encontrada"));
    
        // Deserializar el texto en el campo predicción
        String[] lineasPrediccion;
        try {
            lineasPrediccion = prediccion.getPrediccion().split("\\r?\\n");
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al deserializar el campo 'prediccion': " + e.getMessage());
        }
    
        double demandaTotal = 0;
    
        // Procesar las líneas para calcular la demanda total
        for (String linea : lineasPrediccion) {
            if (linea.contains("Demanda")) {
                try {
                    String[] partes = linea.split("=");
                    demandaTotal += Double.parseDouble(partes[1].trim());
                } catch (NumberFormatException e) {
                    throw new IllegalArgumentException("Error al procesar la línea: " + linea);
                }
            }
        }
    
        // Obtener vehículos disponibles
        List<Vehiculo> vehiculosDisponibles = vehiculoRepository.findByDisponibilidad("Disponible");
    
        if (vehiculosDisponibles.isEmpty()) {
            throw new IllegalArgumentException("No hay vehículos disponibles para la simulación.");
        }
    
        // Calcular la capacidad total disponible
        int capacidadTotal = vehiculosDisponibles.stream()
                .mapToInt(Vehiculo::getCapacidadKg)
                .sum();
    
        if (demandaTotal > capacidadTotal) {
            throw new IllegalArgumentException("Capacidad insuficiente para la demanda proyectada.");
        }
    
        // Asignar vehículos
        List<VehiculoDTO> vehiculosAsignados = new ArrayList<>();
        double capacidadRestante = demandaTotal;
    
        for (Vehiculo vehiculo : vehiculosDisponibles) {
            if (capacidadRestante <= 0) break;
            capacidadRestante -= vehiculo.getCapacidadKg();
            vehiculosAsignados.add(convertirAVehiculoDTO(vehiculo));
        }
    
        return vehiculosAsignados;
    }
    
    

    public String generarReporte(String nombrePrediccion) {
        Prediccion prediccion = prediccionRepository.findByNombre(nombrePrediccion)
                .orElseThrow(() -> new IllegalArgumentException("Predicción no encontrada"));
    
        // Deserializar el texto en el campo predicción
        String[] lineasPrediccion;
        try {
            lineasPrediccion = prediccion.getPrediccion().split("\\r?\\n");
        } catch (Exception e) {
            throw new IllegalArgumentException("Error al deserializar el campo 'prediccion': " + e.getMessage());
        }
    
        StringBuilder reporte = new StringBuilder("Reporte de Simulación:\n");
    
        for (String linea : lineasPrediccion) {
            if (linea.contains("Demanda")) {
                try {
                    String[] partes = linea.split("=");
                    double demanda = Double.parseDouble(partes[1].trim());
                    List<VehiculoDTO> vehiculosAsignados = simularTransporte(prediccion.getNombre());
                    reporte.append(String.format("%s: Vehículos asignados: %d\n", linea, vehiculosAsignados.size()));
                } catch (IllegalArgumentException e) {
                    reporte.append(String.format("%s: Error - %s\n", linea, e.getMessage()));
                } catch (Exception e) {
                    reporte.append(String.format("%s: Error desconocido\n", linea));
                }
            }
        }
    
        return reporte.toString();
    }
    

    private VehiculoDTO convertirAVehiculoDTO(Vehiculo vehiculo) {
        return VehiculoDTO.builder()
                .idVehiculo(vehiculo.getIdVehiculo())
                .placa(vehiculo.getPlaca())
                .capacidadKg(vehiculo.getCapacidadKg())
                .disponibilidad(vehiculo.getDisponibilidad())
                .fechadeCreacion(vehiculo.getFechadeCreacion().toLocalDate())
                .tipoVehiculo(vehiculo.getTipoVehiculo())
                .build();
    }
}
