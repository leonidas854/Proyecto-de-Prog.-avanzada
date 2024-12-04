package com.empresa.proyeco.empresa.DTO;
import com.empresa.proyeco.empresa.model.TipoUsuario;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor

public class UsuarioDTO {
    private Long id;
    private String nombre;
    private String email;
    private String password;
    private Integer contacto;
    private BigDecimal latitud;
    private BigDecimal longitud;
    private TipoUsuario tipoUsuario;
    private LocalDateTime fechaCreacion;

    public UsuarioDTO(Long id, String nombre, String email, Integer contacto, BigDecimal latitud, BigDecimal longitud) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
        this.contacto = contacto;
        this.latitud = latitud;
        this.longitud = longitud;
    }
    
    

}
