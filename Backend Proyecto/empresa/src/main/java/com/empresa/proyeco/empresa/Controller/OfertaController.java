package com.empresa.proyeco.empresa.Controller;

import com.empresa.proyeco.empresa.DTO.OfertaDTO;
import com.empresa.proyeco.empresa.service.OfertaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/ofertas")
public class OfertaController {

    @Autowired
    private OfertaService ofertaService;

    // Obtener todas las ofertas
    @GetMapping
    public ResponseEntity<List<OfertaDTO>> getAllOfertas() {
        List<OfertaDTO> ofertas = ofertaService.getAllOfertas();
        return ResponseEntity.ok(ofertas);
    }

    // Obtener una oferta por ID
    @GetMapping("/{id}")
    public ResponseEntity<OfertaDTO> getOfertaById(@PathVariable Long id) {
        OfertaDTO oferta = ofertaService.getOfertaById(id);
        if (oferta == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(oferta);
    }

    // Crear una nueva oferta
    @PostMapping
    public ResponseEntity<OfertaDTO> createOferta(@RequestBody OfertaDTO ofertaDTO) {
        OfertaDTO nuevaOferta = ofertaService.createOferta(ofertaDTO);
        return ResponseEntity.ok(nuevaOferta);
    }

    // Actualizar una oferta existente
    @PutMapping("/{id}")
    public ResponseEntity<OfertaDTO> updateOferta(@PathVariable Long id, @RequestBody OfertaDTO ofertaDTO) {
        OfertaDTO ofertaActualizada = ofertaService.updateOferta(id, ofertaDTO);
        if (ofertaActualizada == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(ofertaActualizada);
    }

    // Eliminar una oferta
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOferta(@PathVariable Long id) {
        boolean eliminado = ofertaService.deleteOferta(id);
        if (!eliminado) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.noContent().build();
    }
}
