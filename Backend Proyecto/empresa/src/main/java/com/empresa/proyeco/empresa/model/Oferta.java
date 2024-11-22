package com.empresa.proyeco.empresa.model;

import java.time.LocalDate;
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

public class Oferta {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idReporte;

    @Column(nullable = false, length = 100)
    private String nombreReporte;

    @Column(nullable = false)
    private LocalDate fechaGeneracion;

    @Column(nullable = false, length = 50)
    private String tipoReporte;

    @Column(length = 500)
    private String descripcion;

}
