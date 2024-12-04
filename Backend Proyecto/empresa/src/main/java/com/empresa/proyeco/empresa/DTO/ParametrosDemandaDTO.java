package com.empresa.proyeco.empresa.DTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ParametrosDemandaDTO {
    private Long id;
    private String nombre;
    private double alpha;
    private double gamma;
   
}
