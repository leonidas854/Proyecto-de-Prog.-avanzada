package com.empresa.proyeco.empresa.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

import jakarta.persistence.*;
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Vehiculo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idVehiculo;

    @Column(nullable = false, unique = true, length = 50)
    private String placa;

    @Column(nullable = false)
    private Integer capacidadKg;

    @Column(nullable = false, length = 20)
    private String disponibilidad;

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime fechadeCreacion= LocalDateTime.now();


    @Column(nullable = false, length = 50)
    private String tipoVehiculo;

}
