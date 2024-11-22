package com.empresa.proyeco.empresa.DTO;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ClientesDTO {
    private String nombre;
    private String ventanaTiempo;
    private UbicacionDTO ubicacion;
}
