package com.empresa.proyeco.empresa.Controller;

import com.empresa.proyeco.empresa.model.*;
import com.empresa.proyeco.empresa.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import com.empresa.proyeco.empresa.DTO.*;

@RestController
@RequestMapping("/clientes")
public class ClienteController {
    @Autowired
    private ClientesRepository clientesRepository;

    @GetMapping
    public List<Clientes> getClientes() {
        return clientesRepository.findAll();
    }

    @GetMapping("/{id}")
    public ClientesDTO getCliente(@PathVariable Long id) {
    Clientes cliente = clientesRepository.findById(id)
            .orElseThrow(() -> new RuntimeException("No se encontró el cliente con id: " + id));
            Ubicacion ubicacion = cliente.getUbicacion();
            UbicacionDTO ubicacionDTO = new UbicacionDTO(
            ubicacion.getNombre(),
            ubicacion.getLatitud().doubleValue(),
            ubicacion.getLongitud().doubleValue(),
            ubicacion.getFechaRegistro().toString()
    );

    return new ClientesDTO(
            cliente.getNombre(),
            cliente.getVentanaTiempo(),
            ubicacionDTO
    );
    }

    @PostMapping
    public Clientes createCliente(@RequestBody Clientes cliente) {
        if (cliente.getUbicacion() == null) {
            throw new RuntimeException("La ubicación es obligatoria");
        }
        return clientesRepository.save(cliente);
    }

    @PutMapping("/{id}")
    public Clientes updateCliente(@PathVariable Long id, @RequestBody Clientes detallesCliente) {
        Clientes cliente = clientesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el cliente con id: " + id));

        cliente.setNombre(detallesCliente.getNombre());
        cliente.setVentanaTiempo(detallesCliente.getVentanaTiempo());
        cliente.setUbicacion(detallesCliente.getUbicacion());

        return clientesRepository.save(cliente);
    }

    @DeleteMapping("/{id}")
    public String deleteCliente(@PathVariable Long id) {
        Clientes cliente = clientesRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No se encontró el cliente con id: " + id));

        clientesRepository.delete(cliente);
        return "Cliente eliminado con éxito";
    }

}
