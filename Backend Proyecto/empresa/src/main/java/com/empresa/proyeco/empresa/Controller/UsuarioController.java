package com.empresa.proyeco.empresa.Controller;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.empresa.proyeco.empresa.DTO.DemandaDTO;
import com.empresa.proyeco.empresa.DTO.UsuarioDTO;
import com.empresa.proyeco.empresa.model.*;
import com.empresa.proyeco.empresa.repository.*;
import com.empresa.proyeco.empresa.service.RouteService;


import java.util.Optional;
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    //private final RouteService routeService;
    private final UsuarioRepository usuarioRepository;
  @Autowired
    private DemandaRepository demandaRepository;
    
    

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
@GetMapping("/demandas/{usuarioId}")
public ResponseEntity<List<DemandaDTO>> getDemandasByUsuario(@PathVariable Long usuarioId) {
    List<Demanda> demandas = demandaRepository.findByUsuarioId(usuarioId);

    // Validación adicional para evitar problemas con datos nulos
    List<DemandaDTO> demandaDTOs = demandas.stream().map(demanda -> {
        Long usuarioIdSafe = (demanda.getUsuario() != null) ? demanda.getUsuario().getId() : null;
        return DemandaDTO.builder()
            .idDemanda(demanda.getIdDemanda())
            .cantidad(demanda.getCantidad())
            .descripcion(demanda.getDescripcion())
            .inicioVentana(demanda.getInicioVentana())
            .finVentana(demanda.getFinVentana())
            .idUsuario(usuarioIdSafe)
            .build();
    }).collect(Collectors.toList());

    return ResponseEntity.ok(demandaDTOs);
}
    // Eliminar un usuario
    @DeleteMapping("/{id}")
    public String deleteUsuario(@PathVariable Long id) {
        Usuario usuario = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el usuario con id: " + id));
        usuarioRepository.delete(usuario);
        return "Usuario eliminado con éxito";
    }
     @PostMapping("/login")
    public ResponseEntity<UsuarioDTO> login(@RequestParam String email, @RequestParam String password) {
        Optional<Usuario> usuario = usuarioRepository.findByEmailAndPassword(email, password);
        if (usuario.isEmpty()) {
            return ResponseEntity.status(401).build();
        }

        Usuario user = usuario.get();
        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                .id(user.getId())
                .email(user.getEmail())
                .nombre(user.getNombre())
                .contacto(user.getContacto())
                .build();

        return ResponseEntity.ok(usuarioDTO);
    }

   

    @PostMapping("/demandas")
    public ResponseEntity<DemandaDTO> createDemanda(@RequestBody DemandaDTO demandaDTO) {
        Optional<Usuario> usuario = usuarioRepository.findById(demandaDTO.getIdUsuario());
        if (usuario.isEmpty()) {
            return ResponseEntity.badRequest().build();
        }

        Demanda demanda = Demanda.builder()
                .cantidad(demandaDTO.getCantidad())
                .descripcion("Pendiente")
                .inicioVentana(demandaDTO.getInicioVentana())
                .finVentana(demandaDTO.getFinVentana())
                .usuario(usuario.get())
                .build();

        demanda = demandaRepository.save(demanda);

        DemandaDTO createdDemandaDTO = DemandaDTO.builder()
                .idDemanda(demanda.getIdDemanda())
                .cantidad(demanda.getCantidad())
                .descripcion(demanda.getDescripcion())
                .inicioVentana(demanda.getInicioVentana())
                .finVentana(demanda.getFinVentana())
                .idUsuario(demanda.getUsuario().getId())
                .build();

        return ResponseEntity.ok(createdDemandaDTO);
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

    @GetMapping("/buscar")
    public ResponseEntity<UsuarioDTO> buscarPorEmailYPassword(@RequestParam String email, @RequestParam String password) {
        Optional<Usuario> usuario = usuarioRepository.findByEmailAndPassword(email, password);

        if (usuario.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        UsuarioDTO usuarioDTO = UsuarioDTO.builder()
                .id(usuario.get().getId())
                .nombre(usuario.get().getNombre())
                .email(usuario.get().getEmail())
                .contacto(usuario.get().getContacto())
                .latitud(usuario.get().getLatitud())
                .longitud(usuario.get().getLongitud())
                .build();

        return ResponseEntity.ok(usuarioDTO);
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
