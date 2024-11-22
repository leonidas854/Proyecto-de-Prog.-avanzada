package com.empresa.proyeco.empresa.model;

import java.time.LocalTime;
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
public class Itinerario {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idItinerario;

    @ManyToOne
    @JoinColumn(name = "id_ruta", nullable = false)
    private Ruta ruta;

    @ManyToOne
    @JoinColumn(name = "id_ubicacion", nullable = false)
    private Ubicacion ubicacion;

    @Column(nullable = false)
    private Integer orden;

    @Column(nullable = false, length = 20)
    private String estado;

    @Column
    private LocalTime horaLlegadaEstimada;

    @Column
    private LocalTime horaSalidaEstimada;

}
