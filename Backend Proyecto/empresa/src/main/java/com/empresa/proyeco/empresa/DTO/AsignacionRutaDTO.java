package com.empresa.proyeco.empresa.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AsignacionRutaDTO {
    private Long idAsignacion;
    private String nombreRuta;
    private Long vehiculoId;
    private String estado;
    private LocalDateTime fechadeAsignacion;
    private String detalles;
}
