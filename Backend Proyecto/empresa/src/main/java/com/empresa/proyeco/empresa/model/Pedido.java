package com.empresa.proyeco.empresa.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;
import jakarta.persistence.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Pedido {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPedido;

    

    @Column(nullable = false)
    private LocalDateTime fechaPedido;

    @Column
    private LocalDateTime fechaEntrega;

    @Column(nullable = false, length = 20)
    private String estado;

    @Column(precision = 10, scale = 2)
    private BigDecimal totalPedido;
}
