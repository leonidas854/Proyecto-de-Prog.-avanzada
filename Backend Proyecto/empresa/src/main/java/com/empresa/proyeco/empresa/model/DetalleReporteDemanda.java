package com.empresa.proyeco.empresa.model;

import jakarta.persistence.*;
import lombok.*;
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DetalleReporteDemanda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_reporte", nullable = false)
    private Reporte reporte;

    @ManyToOne
    @JoinColumn(name = "id_demanda", nullable = false)
    private Demanda demanda;

    @Column(nullable = false, length = 50)
    private String periodo; 

}
