package com.empresa.proyeco.empresa.DTO;



import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;
import lombok.Builder;

import java.time.LocalDateTime;

import com.empresa.proyeco.empresa.model.Demanda;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DemandaDTO {
    private Long idDemanda;
    private Integer cantidad;
    private Long idUsuario; // Referencia al usuario
    private String descripcion;
    private LocalDateTime inicioVentana;
    private LocalDateTime finVentana;
    public DemandaDTO(Demanda demanda) {
        this.idDemanda = demanda.getIdDemanda();
        this.cantidad = demanda.getCantidad();
        this.idUsuario = demanda.getUsuario().getId();
        this.descripcion = demanda.getDescripcion();
        this.inicioVentana = demanda.getInicioVentana();
        this.finVentana = demanda.getFinVentana();
    }
}

