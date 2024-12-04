package com.empresa.proyeco.empresa.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PrediccionDTO {

    private Long id; // ID de la predicción
    private String nombre; // Nombre de la predicción
    private String prediccion; // Datos de la predicción (en formato serializado o texto)
    private int rangoMeses; // Rango de meses para predecir
    private double precio; // Precio del producto
    private Long idParametrosDemanda; // ID de los parámetros de demanda seleccionados
}