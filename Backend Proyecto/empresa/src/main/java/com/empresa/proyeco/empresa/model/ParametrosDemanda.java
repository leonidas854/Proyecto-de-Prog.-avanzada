package com.empresa.proyeco.empresa.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ParametrosDemanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idParametro;


    @Column(nullable = false)
    private String nombre;
    
    @Column(nullable = false)
    private double alpha; // Tasa de crecimiento

    

    @Column(nullable = false)
    private double gamma; // Sensibilidad a la estacionalidad


}
