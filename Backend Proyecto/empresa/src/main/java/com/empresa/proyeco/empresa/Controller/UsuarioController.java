package com.empresa.proyeco.empresa.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.proyeco.empresa.DTO.UsuarioDTO;
import com.empresa.proyeco.empresa.model.TipoUsuario;
import com.empresa.proyeco.empresa.model.Usuario;
import com.empresa.proyeco.empresa.repository.UsuarioRepository;
import com.empresa.proyeco.empresa.service.RouteService;
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    //private final RouteService routeService;
    private final UsuarioRepository usuarioRepository;

    

    public UsuarioController(UsuarioRepository usuarioRepository, RouteService routeService) {
        this.usuarioRepository = usuarioRepository;
        //this.routeService = routeService;
    }

    // Obtener todos los usuarios como DTO
    @GetMapping
    public List<UsuarioDTO> getUsuarios() {
        return usuarioRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obtener un usuario por ID como DTO
    @GetMapping("/{id}")
    public UsuarioDTO getUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con id: " + id));
        return convertToDTO(usuario);
    }

    // Crear un nuevo usuario a partir de un DTO
    @PostMapping
    public UsuarioDTO createUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        Usuario usuario = convertToEntity(usuarioDTO);
        Usuario savedUsuario = usuarioRepository.save(usuario);
       
        return convertToDTO(savedUsuario);
    }
    @GetMapping("/sucursal/{nombre}")
    public UsuarioDTO getSucursalPorNombre(@PathVariable String nombre) {
    Usuario sucursal = usuarioRepository.findByNombreAndTipoUsuario(nombre, TipoUsuario.SUCURSAL)
            .orElseThrow(() -> new RuntimeException("No se encontró la sucursal con nombre: " + nombre));

    return convertToDTO(sucursal);
}


    // Actualizar un usuario existente
    @PutMapping("/{id}")
    public UsuarioDTO updateUsuario(@PathVariable Long id, @RequestBody UsuarioDTO detallesUsuarioDTO) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con id: " + id));

        usuario.setNombre(detallesUsuarioDTO.getNombre());
        usuario.setEmail(detallesUsuarioDTO.getEmail());
        usuario.setPassword(detallesUsuarioDTO.getPassword());
        usuario.setContacto(detallesUsuarioDTO.getContacto());
        usuario.setLatitud(detallesUsuarioDTO.getLatitud());
        usuario.setLongitud(detallesUsuarioDTO.getLongitud());
        usuario.setTipoUsuario(detallesUsuarioDTO.getTipoUsuario());

        Usuario updatedUsuario = usuarioRepository.save(usuario);
        return convertToDTO(updatedUsuario);
    }
    @GetMapping("/sucursales")
    public List<UsuarioDTO> getSucursales() {
    return usuarioRepository.findAllByTipoUsuario(TipoUsuario.SUCURSAL)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
}
@GetMapping("/clientes")
    public List<UsuarioDTO> getClientes() {
    return usuarioRepository.findAllByTipoUsuario(TipoUsuario.CLIENTE)
            .stream()
            .map(this::convertToDTO)
            .collect(Collectors.toList());
}
    // Eliminar un usuario
    @DeleteMapping("/{id}")
    public String deleteUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con id: " + id));
        usuarioRepository.delete(usuario);
        return "Usuario eliminado con éxito";
    }

    // Métodos para convertir entre DTO y Entidad
    private UsuarioDTO convertToDTO(Usuario usuario) {
        return UsuarioDTO.builder()
                .id(usuario.getId())
                .nombre(usuario.getNombre())
                .email(usuario.getEmail())
                .password(usuario.getPassword()) // Asegúrate de asignar el campo password
                .contacto(usuario.getContacto())
                .latitud(usuario.getLatitud())
                .longitud(usuario.getLongitud())
                .tipoUsuario(usuario.getTipoUsuario())
                .fechaCreacion(usuario.getFechaCreacion())
                .build();
    }
    

    private Usuario convertToEntity(UsuarioDTO usuarioDTO) {
        return Usuario.builder()
                .id(usuarioDTO.getId())
                .nombre(usuarioDTO.getNombre())
                .email(usuarioDTO.getEmail())
                .password(usuarioDTO.getPassword()) // Asegúrate de manejar adecuadamente la seguridad del password
                .contacto(usuarioDTO.getContacto())
                .latitud(usuarioDTO.getLatitud())
                .longitud(usuarioDTO.getLongitud())
                .tipoUsuario(usuarioDTO.getTipoUsuario())
                .build();
    }
}
