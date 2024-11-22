package com.empresa.proyeco.empresa.Controller;

import com.empresa.proyeco.empresa.model.*;
import com.empresa.proyeco.empresa.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@RestController
@RequestMapping("/ubicaciones")
public class UbicacionController {
    @Autowired
    private UbicacionRepository ubicacionRepository;


    @GetMapping
    public List<Ubicacion> getUbicaciones() {
        return ubicacionRepository.findAll();
    }

    @GetMapping("/{id}")
    public Ubicacion getUbicacion(@PathVariable Long id) {
        return ubicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la ubicación con id: " + id));
    }

    @PostMapping
    public Ubicacion createUbicacion(@RequestBody Ubicacion ubicacion) {
        return ubicacionRepository.save(ubicacion);
    }

    @PutMapping("/{id}")
    public Ubicacion updateUbicacion(@PathVariable Long id, @RequestBody Ubicacion detallesUbicacion) {
        Ubicacion ubicacion = ubicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la ubicación con id: " + id));

        ubicacion.setNombre(detallesUbicacion.getNombre());
        ubicacion.setLatitud(detallesUbicacion.getLatitud());
        ubicacion.setLongitud(detallesUbicacion.getLongitud());

        return ubicacionRepository.save(ubicacion);
    }

    @DeleteMapping("/{id}")
    public String deleteUbicacion(@PathVariable Long id) {
        Ubicacion ubicacion = ubicacionRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la ubicación con id: " + id));

        ubicacionRepository.delete(ubicacion);
        return "Ubicación eliminada con éxito";
    }
}
