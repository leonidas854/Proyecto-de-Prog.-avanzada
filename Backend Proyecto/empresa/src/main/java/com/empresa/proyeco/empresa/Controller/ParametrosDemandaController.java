package com.empresa.proyeco.empresa.Controller;

import com.empresa.proyeco.empresa.DTO.ParametrosDemandaDTO;
import com.empresa.proyeco.empresa.model.ParametrosDemanda;
import com.empresa.proyeco.empresa.repository.ParametrosDemandaRepository;
import com.empresa.proyeco.empresa.service.ParametrosDemandaService;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/parametros")
public class ParametrosDemandaController {

    @Autowired
    private ParametrosDemandaService service;
    @Autowired
    private ParametrosDemandaRepository parametrosDemandaRepository;

    // Endpoint para guardar parámetros
    // Endpoint para guardar Alpha y Gamma
    @PostMapping
    public ResponseEntity<ParametrosDemandaDTO> guardarParametros(@RequestBody ParametrosDemandaDTO dto) {
        ParametrosDemandaDTO saved = service.guardarParametros(dto);
        return ResponseEntity.ok(saved);
    }
    @GetMapping("/nombres")
public ResponseEntity<List<String>> obtenerNombresParametros() {
    List<String> nombres = parametrosDemandaRepository.findAll()
        .stream()
        .map(ParametrosDemanda::getNombre) // Asume que tienes un campo "nombre" en ParametrosDemanda
        .collect(Collectors.toList());
    return ResponseEntity.ok(nombres);
}
}
