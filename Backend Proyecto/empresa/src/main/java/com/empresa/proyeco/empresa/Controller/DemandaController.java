package com.empresa.proyeco.empresa.Controller;


import com.empresa.proyeco.empresa.DTO.DemandaDTO;

import com.empresa.proyeco.empresa.model.Demanda;
import com.empresa.proyeco.empresa.model.Usuario;
import com.empresa.proyeco.empresa.repository.DemandaRepository;
import com.empresa.proyeco.empresa.repository.UsuarioRepository;
import com.empresa.proyeco.empresa.DTO.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/demandas")
public class DemandaController {

    @Autowired
    private DemandaRepository demandaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

   
    @GetMapping("/{usuarioId}")
    public ResponseEntity<List<DemandaDTO>> obtenerDemandasPorUsuario(@PathVariable Long usuarioId) {
        List<Demanda> demandas = demandaRepository.findByUsuarioId(usuarioId);

        if (demandas.isEmpty()) {
            return ResponseEntity.ok(List.of()); 
        }

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

    
    @PostMapping("/crear")
    public ResponseEntity<?> crearDemanda(@RequestBody DemandaDTO nuevaDemandaDTO) {
        
        Usuario usuario = usuarioRepository.findById(nuevaDemandaDTO.getIdUsuario()).orElse(null);
        if (usuario == null) {
            return ResponseEntity.badRequest().body("El usuario no existe.");
        }

       
        Demanda nuevaDemanda = Demanda.builder()
                .cantidad(nuevaDemandaDTO.getCantidad())
                .descripcion("Pendiente") 
                .inicioVentana(nuevaDemandaDTO.getInicioVentana())
                .finVentana(nuevaDemandaDTO.getFinVentana())
                .usuario(usuario)
                .build();

        
        Demanda demandaGuardada = demandaRepository.save(nuevaDemanda);

   
        DemandaDTO demandaDTO = DemandaDTO.builder()
                .idDemanda(demandaGuardada.getIdDemanda())
                .cantidad(demandaGuardada.getCantidad())
                .descripcion(demandaGuardada.getDescripcion())
                .inicioVentana(demandaGuardada.getInicioVentana())
                .finVentana(demandaGuardada.getFinVentana())
                .idUsuario(demandaGuardada.getUsuario().getId())
                .build();

        return ResponseEntity.ok(demandaDTO);
    }
    
    @GetMapping("/ids")
    public ResponseEntity<List<Demandaadto>> obtenerIdsYCantidadesDemanda() {
        List<Demandaadto> demandas = demandaRepository.findAll()
            .stream()
            .map(demanda -> new Demandaadto(demanda.getIdDemanda(), demanda.getCantidad()))
            .collect(Collectors.toList());
        return ResponseEntity.ok(demandas);
    }
    
}
