package com.empresa.proyeco.empresa.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.persistence.*;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vehiculo {
    @Id 
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false,unique =true)
    private String placa;

    @Column(nullable = false)
    private Integer capacidadKg;
    
    @Column(nullable = false)
    private Boolean disponibilidad;
    
    @Column(nullable = false)
    private String tipoVehiculo;

}
