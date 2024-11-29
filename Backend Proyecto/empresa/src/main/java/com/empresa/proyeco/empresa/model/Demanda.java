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
public class Demanda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDemanda;

    @Column(nullable = false)
    private Integer cantidad;

    @ManyToOne
    //demanda inicial
    @JoinColumn(name = "id_usuario", nullable = false)
    private Usuario usuario;
}
