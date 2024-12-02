package com.empresa.proyeco.empresa.Controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.proyeco.empresa.model.TipoUsuario;
import com.empresa.proyeco.empresa.model.Usuario;
import com.empresa.proyeco.empresa.repository.UsuarioRepository;
import com.empresa.proyeco.empresa.service.RouteService;

@RestController
@RequestMapping("/rutas")
public class RouteController {

    @Autowired
    private RouteService routeService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @PostMapping("/calcular")
    public List<Map<String, Double>> calcularRutaOptima(@RequestParam String nombreSucursal) {
    // Buscar la sucursal en la base de datos
    Usuario sucursal = usuarioRepository.findByNombreAndTipoUsuario(nombreSucursal, TipoUsuario.SUCURSAL)
            .orElseThrow(() -> new RuntimeException("Sucursal no encontrada: " + nombreSucursal));

    // Buscar todos los clientes registrados
    List<Usuario> clientes = usuarioRepository.findAllByTipoUsuario(TipoUsuario.CLIENTE);

    if (clientes.isEmpty()) {
        throw new RuntimeException("No hay clientes registrados en la base de datos.");
    }

    // Agregar sucursal al grafo
    routeService.agregarUbicacion(
            sucursal.getNombre(),
            sucursal.getLatitud().doubleValue(),
            sucursal.getLongitud().doubleValue()
    );

    // Agregar todos los clientes al grafo
    for (Usuario cliente : clientes) {
        routeService.agregarUbicacion(
                cliente.getNombre(),
                cliente.getLatitud().doubleValue(),
                cliente.getLongitud().doubleValue()
        );
    }

    // Calcular la ruta óptima desde la sucursal
    List<String> nombresClientes = clientes.stream()
                                           .map(Usuario::getNombre)
                                           .toList();
    return routeService.calcularRutaOptimaDesdeSucursal(sucursal.getNombre(), nombresClientes);
}
}