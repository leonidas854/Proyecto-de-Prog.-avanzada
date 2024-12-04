package com.empresa.proyeco.empresa.DTO;

import lombok.Data;

@Data
public class PrediccionRequestDTO {
    private String nombreParametros;
    private Long idDemanda;
    private int rangoMeses;
    private double precio;
    private String nombrePrediccion;
}
