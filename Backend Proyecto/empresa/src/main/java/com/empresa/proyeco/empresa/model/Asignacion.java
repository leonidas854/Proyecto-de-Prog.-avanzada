package com.empresa.proyeco.empresa.model;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Asignacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_sucursal", nullable = false)
    private Usuario sucursal;

    @ManyToOne
    @JoinColumn(name = "id_cliente", nullable = false)
    private Usuario cliente;

    @Column(nullable = false)
    private Integer cantidad; // Cantidad asignada

    @Column(nullable = false)
    private Double costo; // Costo de la asignación
}

