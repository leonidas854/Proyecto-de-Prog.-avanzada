package com.empresa.proyeco.empresa.DTO;

import java.time.LocalDate;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class VehiculoDTO {
    private Long idVehiculo;
    private String placa;
    private Integer capacidadKg;
    private String disponibilidad;
    private LocalDate fechadeCreacion;
    private String tipoVehiculo;

}
