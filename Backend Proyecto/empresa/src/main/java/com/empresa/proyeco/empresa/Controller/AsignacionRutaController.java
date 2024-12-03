package com.empresa.proyeco.empresa.Controller;
import com.empresa.proyeco.empresa.DTO.AsignacionRutaDTO;
import com.empresa.proyeco.empresa.DTO.AsignacionRutaRequestDTO;
import com.empresa.proyeco.empresa.service.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.util.List;


@RestController
@RequestMapping("/asignaciones")
public class AsignacionRutaController {


    @Autowired
    private AsignacionRutaService asignacionRutaService;

  
  

    @PostMapping
    public ResponseEntity<AsignacionRutaDTO> asignarRuta(@RequestBody AsignacionRutaRequestDTO request) {
        AsignacionRutaDTO asignacion = asignacionRutaService.asignarRuta(request);
        return ResponseEntity.ok(asignacion);
    }

    @GetMapping("/vehiculo")
    public ResponseEntity<AsignacionRutaDTO> obtenerAsignacionPorPlaca(@RequestParam String placa) {
        AsignacionRutaDTO asignacion = asignacionRutaService.obtenerAsignacionPorPlaca(placa);
        return ResponseEntity.ok(asignacion);
    }

    
}

