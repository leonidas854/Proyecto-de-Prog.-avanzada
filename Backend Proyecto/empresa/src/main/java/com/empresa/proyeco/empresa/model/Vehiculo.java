package com.empresa.proyeco.empresa.model;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDate;

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

    @ManyToOne
    @JoinColumn(name = "id_sucursal", nullable = false)
    private Sucursal sucursal;

    @Column(nullable = false, length = 20)
    private String disponibilidad;

    @Column(nullable = false)
    private LocalDate fechaMantenimiento;

    @Column(nullable = false, length = 50)
    private String tipoVehiculo;

}
