package com.empresa.proyeco.empresa.Controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.proyeco.empresa.DTO.AsignacionRutaDTO;
import com.empresa.proyeco.empresa.DTO.AsignacionRutaRequestDTO;
import com.empresa.proyeco.empresa.service.AsignacionRutaService;





@RestController
@RequestMapping("/asignaciones")
public class AsignacionRutaController {


    @Autowired
    private AsignacionRutaService asignacionRutaService;

    
    @PostMapping
        public ResponseEntity<?> asignarRuta(@RequestBody AsignacionRutaRequestDTO request) {
    try {
        AsignacionRutaDTO asignacion = asignacionRutaService.asignarRuta(request);
        return ResponseEntity.ok(asignacion);
    } catch (RuntimeException e) {
        return ResponseEntity.badRequest().body("Error: " + e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body("Error inesperado en el servidor.");
    }
}


    @GetMapping("/vehiculo")
    public ResponseEntity<AsignacionRutaDTO> obtenerAsignacionPorPlaca(@RequestParam String placa) {
        AsignacionRutaDTO asignacion = asignacionRutaService.obtenerAsignacionPorPlaca(placa);
        return ResponseEntity.ok(asignacion);
    }

    
}

