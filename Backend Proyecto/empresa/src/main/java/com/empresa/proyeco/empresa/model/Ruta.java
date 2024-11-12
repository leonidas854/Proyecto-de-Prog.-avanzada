package com.empresa.proyeco.empresa.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Ruta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_vehiculo",nullable = false)
    private Vehiculo vehiculo;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(precision = 10, scale = 2)
    private BigDecimal distanciaTotal;

    private Integer tiempoTotal;

    @Column(precision = 10, scale = 2)
    private BigDecimal costo;

    @Column(nullable = false)
    private String estado;


}
