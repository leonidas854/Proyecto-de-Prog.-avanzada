package com.empresa.proyeco.empresa.Controller;

import com.empresa.proyeco.empresa.DTO.*;
import com.empresa.proyeco.empresa.service.PrediccionDemandaService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/prediccion")
public class PrediccionDemandaController {

    @Autowired
    private PrediccionDemandaService prediccionDemandaService;

   
    @PostMapping
public ResponseEntity<PrediccionDTO> predecirDemanda(@RequestBody PrediccionRequestDTO requestDTO) {
    PrediccionDTO resultado = prediccionDemandaService.predecirDemanda(
            requestDTO.getNombreParametros(),
            requestDTO.getIdDemanda(),
            requestDTO.getRangoMeses(),
            requestDTO.getPrecio(),
            requestDTO.getNombrePrediccion()
    );

    return ResponseEntity.ok(resultado);
}
@GetMapping("/todas")
public ResponseEntity<List<PrediccionDTO>> obtenerTodasPredicciones() {
    List<PrediccionDTO> predicciones = prediccionDemandaService.obtenerTodasPredicciones();
    return ResponseEntity.ok(predicciones);
}


}
