package com.empresa.proyeco.empresa.DTO;
import com.empresa.proyeco.empresa.model.Oferta;
import com.empresa.proyeco.empresa.model.Usuario;
import lombok.Data;
import lombok.NoArgsConstructor;
@Data
@NoArgsConstructor
public class OfertaDTO {
    private Long idOferta;
    private String nombre;
    private Integer cantidad;
    private Long idUsuario; // Referencia al ID del Usuario
    private String estado;

    // Método para convertir de Entidad a DTO
    public static OfertaDTO fromEntity(Oferta oferta) {
        OfertaDTO dto = new OfertaDTO();
        dto.setIdOferta(oferta.getIdOferta());
        dto.setNombre(oferta.getNombre());
        dto.setCantidad(oferta.getCantidad());
        dto.setIdUsuario(oferta.getUsuario().getId());
        dto.setEstado(oferta.getEstado());
        return dto;
    }

    // Método para convertir de DTO a Entidad
    public Oferta toEntity() {
        Oferta oferta = new Oferta();
        oferta.setIdOferta(this.idOferta);
        oferta.setNombre(this.nombre);
        oferta.setCantidad(this.cantidad);

        // Crear un Usuario vacío con solo el ID para evitar cargar toda la entidad
        Usuario usuario = new Usuario();
        usuario.setId(this.idUsuario);
        oferta.setUsuario(usuario);

        oferta.setEstado(this.estado);
        return oferta;
    }
}
