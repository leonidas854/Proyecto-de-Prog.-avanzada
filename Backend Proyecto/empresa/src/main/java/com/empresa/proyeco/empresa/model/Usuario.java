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
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) 
    private Long id;

    @Column(nullable = false)
    private String nombre;

    @Column(nullable = false,unique =true)
    private String email;

    @Column(nullable = false)
    private String password;

    @Column(nullable = false)
    private Integer contacto;
    
    @Column(nullable = false,precision = 10, scale = 7)
    private BigDecimal latitud;

    @Column(nullable = false,precision = 10, scale = 7)
    private BigDecimal longitud;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private TipoUsuario tipoUsuario;
    
    @Column(nullable = false)
    @Builder.Default
    private LocalDateTime fechaCreacion = LocalDateTime.now();
}
