package com.empresa.proyeco.empresa.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class SucursalDTO {
    private String nombre;
    private UbicacionDTO ubicacion;

}
