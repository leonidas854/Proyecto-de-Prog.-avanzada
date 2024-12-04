package com.empresa.proyeco.empresa.Controller;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.empresa.proyeco.empresa.DTO.VehiculoDTO;
import com.empresa.proyeco.empresa.service.TransporteService;

@RestController
@RequestMapping("/api/transporte")
public class TransporteController {

    @Autowired
    private TransporteService transporteService;

    @PostMapping("/simular")
    public ResponseEntity<?> simularTransporte(@RequestBody Map<String, String> request) {
    try {
        String nombrePrediccion = request.get("nombrePrediccion");
        if (nombrePrediccion == null || nombrePrediccion.isEmpty()) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("El campo 'nombrePrediccion' es requerido.");
        }
        List<VehiculoDTO> vehiculosAsignados = transporteService.simularTransporte(nombrePrediccion);
        return ResponseEntity.ok(vehiculosAsignados);
    } catch (IllegalArgumentException e) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
    } catch (Exception e) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al simular transporte.");
    }
    }

/* 
    @PostMapping("/reporte")
    public ResponseEntity<?> generarReporte(@RequestBody Map<String, String> request) {
        try {
            String nombrePrediccion = request.get("nombrePrediccion");
            String reporte = transporteService.generarReporte(nombrePrediccion);
            return ResponseEntity.ok(reporte);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al generar el reporte.");
        }
    }
*/}




