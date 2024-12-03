package com.empresa.proyeco.empresa.DTO;

import lombok.Data;
import java.util.List;
import java.util.Map;

@Data
public class AsignacionRutaRequestDTO {
    private String nombreRuta; // Nombre de la ruta.
    private Long vehiculoId;   // ID del vehículo asignado.
    private List<Map<String, Double>> detalles; // Lista de puntos {lat, lng}.
}
