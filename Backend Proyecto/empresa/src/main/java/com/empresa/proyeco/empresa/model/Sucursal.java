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
public class Sucursal {
  
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idSucursal;

    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private String Nombre;

    @Column(nullable = false, length = 100)
    private String nombre;

    @OneToOne
    @JoinColumn(name = "id_ubicacion", nullable = false, unique = true)
    private Ubicacion ubicacion;


    
}
