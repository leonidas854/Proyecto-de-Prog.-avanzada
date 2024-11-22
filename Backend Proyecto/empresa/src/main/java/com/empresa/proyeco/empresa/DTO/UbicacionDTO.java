package com.empresa.proyeco.empresa.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class UbicacionDTO {
    private String nombre;
    private double latitud;
    private double longitud;
    private String fechaRegistro;


}
