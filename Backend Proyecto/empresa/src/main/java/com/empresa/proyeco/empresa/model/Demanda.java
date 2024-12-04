package com.empresa.proyeco.empresa.model;



import java.time.LocalDateTime;

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
public class Demanda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDemanda;


    @Column(nullable = false)
    private Integer cantidad;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;

    
    @Column(nullable = false)
    private String descripcion;


    @Column(nullable = false)
    private LocalDateTime inicioVentana;

    @Column(nullable = false)
    private LocalDateTime finVentana;
}
