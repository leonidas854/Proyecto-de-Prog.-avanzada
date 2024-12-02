package com.empresa.proyeco.empresa.Controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.proyeco.empresa.DTO.SucursalDTO;
import com.empresa.proyeco.empresa.DTO.UbicacionDTO;
import com.empresa.proyeco.empresa.model.Sucursal;
import com.empresa.proyeco.empresa.model.Ubicacion;
import com.empresa.proyeco.empresa.repository.SucursalRepository;

@RestController
@RequestMapping("/sucursales")
public class SucursalController {
    @Autowired
    private SucursalRepository sucursalRepository;


    @GetMapping
    public List<Sucursal> getSucursales() {
        return sucursalRepository.findAll();
    }


    @GetMapping("/{id}")
    public SucursalDTO getSucursal(@PathVariable Long id) {
    Sucursal sucursal = sucursalRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró la sucursal con id: " + id));

    Ubicacion ubicacion = sucursal.getUbicacion();
    UbicacionDTO ubicacionDTO = new UbicacionDTO(
            ubicacion.getNombre(),
            ubicacion.getLatitud().doubleValue(),
            ubicacion.getLongitud().doubleValue(),
            ubicacion.getFechaRegistro().toString()
    );

    return new SucursalDTO(
            sucursal.getNombre(),
            ubicacionDTO
    );
    }
   




    @PostMapping
    public Sucursal createSucursal(@RequestBody Sucursal sucursal) {
        if (sucursal.getUbicacion() == null) {
            throw new RuntimeException("La ubicación es obligatoria");
        }
        return sucursalRepository.save(sucursal);
    }


    @PutMapping("/{id}")
    public Sucursal updateSucursal(@PathVariable Long id, @RequestBody Sucursal detallesSucursal) {
        Sucursal sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la sucursal con id: " + id));

        sucursal.setNombre(detallesSucursal.getNombre());
        sucursal.setUbicacion(detallesSucursal.getUbicacion());

        return sucursalRepository.save(sucursal);
    }


    @DeleteMapping("/{id}")
    public String deleteSucursal(@PathVariable Long id) {
        Sucursal sucursal = sucursalRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró la sucursal con id: " + id));

        sucursalRepository.delete(sucursal);
        return "Sucursal eliminada con éxito";
    }
}
