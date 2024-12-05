package com.empresa.proyeco.empresa.DTO;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AsignacionDTO {
    private String sucursal;
    private String cliente;
    private Integer cantidad;
    private Double costo;
    private Map<String, Double> origen;
    private Map<String, Double> destino;
    

}

