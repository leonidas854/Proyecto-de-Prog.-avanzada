package com.empresa.proyeco.empresa.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AsignacionRuta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idAsignacion;

    @Column(nullable = false)
    private String nombreRuta; // Identificador único de la ruta.

    @ManyToOne
    @JoinColumn(name = "vehiculo_id", nullable = false)
    private Vehiculo vehiculo; // Vehículo asignado.

    @Column(nullable = false)
    private String estado; // Estado de la asignación (e.g., Pendiente, Completada).

    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime fechadeAsignacion= LocalDateTime.now();

    @Column(nullable = false, length = 2000)
    private String detalles; // Detalles de la ruta e itinerario.
}
