package com.empresa.proyeco.empresa.Controller;

import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.*;
import com.empresa.proyeco.empresa.service.*;

import java.util.Map;
@RestController
@RequestMapping("/api/routes")
public class RouteController {
    private final RouteService routeManagerService;

    public RouteController(RouteService routeManagerService) {
        this.routeManagerService = routeManagerService;
    }


    @PostMapping("/add-location")
    public ResponseEntity<String> agregarLocacion(@RequestParam String locacion) {
        routeManagerService.agregarCiudad(locacion);
        return ResponseEntity.ok("Locación '" + locacion + "' agregada exitosamente.");
    }

    @PostMapping("/add-edge")
    public ResponseEntity<String> agregarArco(@RequestParam String origen, @RequestParam String destino) {
        routeManagerService.agregarArco(origen, destino);
        return ResponseEntity.ok("Arco entre '" + origen + "' y '" + destino + "' agregado exitosamente.");
    }

    @GetMapping("/locations")
    public ResponseEntity<Map<String, String>> obtenerLocaciones() {
        return ResponseEntity.ok(routeManagerService.obtenerLocaciones());
    }

    @GetMapping("/calculate-distance")
    public ResponseEntity<Double> calcularDistancia(
            @RequestParam String origen,
            @RequestParam String destino) {
        double distancia = routeManagerService.calcularDistancia(origen, destino);
        if (distancia == -1) {
            return ResponseEntity.badRequest().body(-1.0);
        }
        return ResponseEntity.ok(distancia);
    }

}
