package com.empresa.proyeco.empresa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Prediccion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; // ID de la predicción

    @Column(nullable = false, length = 100)
    private String nombre; // Nombre de la predicción

    @Lob
    @Basic(fetch = FetchType.EAGER) // Asegúrate de que se cargue de inmediato
    @Column(nullable = false)
    private String prediccion; // Datos de la predicción en formato serializado

    @Column(nullable = false)
    private int rangoMeses; // Rango de meses para predecir

    @Column(nullable = false)
    private double precio; // Precio del producto

    @ManyToOne
    @JoinColumn(name = "id_parametros_demanda", nullable = false)
    private ParametrosDemanda parametrosDemanda; // Relación con los parámetros de demanda
}