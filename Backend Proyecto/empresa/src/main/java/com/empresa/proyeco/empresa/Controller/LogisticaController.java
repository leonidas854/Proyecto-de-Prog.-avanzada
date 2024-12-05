package com.empresa.proyeco.empresa.Controller;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.proyeco.empresa.DTO.AsignacionDTO;
import com.empresa.proyeco.empresa.model.Asignacion;
import com.empresa.proyeco.empresa.model.Demanda;
import com.empresa.proyeco.empresa.model.Oferta;
import com.empresa.proyeco.empresa.model.TipoUsuario;
import com.empresa.proyeco.empresa.model.Usuario;
import com.empresa.proyeco.empresa.repository.AsignacionRepository;
import com.empresa.proyeco.empresa.repository.DemandaRepository;
import com.empresa.proyeco.empresa.repository.OfertaRepository;
import com.empresa.proyeco.empresa.repository.UsuarioRepository;
import com.empresa.proyeco.empresa.service.MetodoVogel;
import com.empresa.proyeco.empresa.service.RouteService;
import org.apache.commons.math3.util.Precision;

@RestController
@RequestMapping("/logistica")
public class LogisticaController {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private DemandaRepository demandaRepository;

    @Autowired
    private RouteService routeService;

    @Autowired
    private AsignacionRepository asignacionRepository;
    @Autowired
    private OfertaRepository ofertaRepository;

    @GetMapping("/asignaciones")
    public ResponseEntity<List<AsignacionDTO>> obtenerAsignaciones() {
        List<Asignacion> asignaciones = asignacionRepository.findAll();

        List<AsignacionDTO> rutas = asignaciones.stream()
                .map(asignacion -> new AsignacionDTO(
                        asignacion.getSucursal().getNombre(),
                        asignacion.getCliente().getNombre(),
                        asignacion.getCantidad(),
                        asignacion.getCosto(),
                        Map.of(
                                "lat", asignacion.getSucursal().getLatitud().doubleValue(),
                                "lng", asignacion.getSucursal().getLongitud().doubleValue()
                        ),
                        Map.of(
                                "lat", asignacion.getCliente().getLatitud().doubleValue(),
                                "lng", asignacion.getCliente().getLongitud().doubleValue()
                        )
                ))
                .toList();

        return ResponseEntity.ok(rutas);
    }


@GetMapping("/calcular")
    public ResponseEntity<?> calcularLogistica() {
        try {
            
         
            TipoUsuario sucursalEnum = TipoUsuario.valueOf("SUCURSAL");
            TipoUsuario clienteEnum = TipoUsuario.valueOf("CLIENTE");
        
            List<Usuario> sucursales = usuarioRepository.findAllByTipoUsuario(sucursalEnum);
            List<Usuario> clientes = usuarioRepository.findAllByTipoUsuario(clienteEnum);

            if (sucursales.isEmpty() || clientes.isEmpty()) {
                return ResponseEntity.badRequest().body("No hay sucursales o clientes registrados.");
            }

          
            validarCoordenadas(sucursales, clientes);

            
            int[] oferta = sucursales.stream().mapToInt(this::calcularOferta).toArray();
            int[] demanda = clientes.stream().mapToInt(this::calcularDemanda).toArray();

          
            if (Arrays.stream(oferta).sum() == 0) {
                return ResponseEntity.badRequest().body("No hay oferta disponible en las sucursales.");
            }
            if (Arrays.stream(demanda).sum() == 0) {
                return ResponseEntity.badRequest().body("No hay demanda registrada en los clientes.");
            }

           
            double[][] matrizDeCostos = generarMatrizDeCostos(sucursales, clientes);
            int[][] matrizCostosInt = convertirMatrizDeCostos(matrizDeCostos);

            
            MetodoVogel.resolverMetodoVogel(oferta, demanda, matrizCostosInt);

         
            int costoTotal = MetodoVogel.obtenerCostoTotal();

          
            return ResponseEntity.ok("Cálculo exitoso. Costo total: " + costoTotal + " Bs.");
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Error al calcular logística: " + e.getMessage());
        }
    }

    private void validarCoordenadas(List<Usuario> sucursales, List<Usuario> clientes) {
        for (Usuario sucursal : sucursales) {
            if (sucursal.getLatitud() == null || sucursal.getLongitud() == null) {
                throw new IllegalArgumentException("La sucursal '" + sucursal.getNombre() + "' no tiene coordenadas válidas.");
            }
        }
        for (Usuario cliente : clientes) {
            if (cliente.getLatitud() == null || cliente.getLongitud() == null) {
                throw new IllegalArgumentException("El cliente '" + cliente.getNombre() + "' no tiene coordenadas válidas.");
            }
        }
    }

    private int calcularOferta(Usuario sucursal) {
        return ofertaRepository.findByUsuario(sucursal).stream()
                .mapToInt(oferta -> oferta.getCantidad() != null ? oferta.getCantidad() : 0)
                .sum();
    }

    private int calcularDemanda(Usuario cliente) {
        return demandaRepository.findByUsuario(cliente).stream()
                .mapToInt(demanda -> demanda.getCantidad() != null ? demanda.getCantidad() : 0)
                .sum();
    }

    private double[][] generarMatrizDeCostos(List<Usuario> sucursales, List<Usuario> clientes) {
        double[][] matriz = new double[sucursales.size()][clientes.size()];
        for (int i = 0; i < sucursales.size(); i++) {
            for (int j = 0; j < clientes.size(); j++) {
                try {
                    String origen = sucursales.get(i).getLatitud() + "," + sucursales.get(i).getLongitud();
                    String destino = clientes.get(j).getLatitud() + "," + clientes.get(j).getLongitud();
                    matriz[i][j] = routeService.calcularCosto(origen, destino);
                } catch (Exception e) {
                    matriz[i][j] = 999999; 
                }
            }
        }
        return matriz;
    }

    private int[][] convertirMatrizDeCostos(double[][] matrizDeCostos) {
        int[][] matrizInt = new int[matrizDeCostos.length][matrizDeCostos[0].length];
        for (int i = 0; i < matrizDeCostos.length; i++) {
            for (int j = 0; j < matrizDeCostos[i].length; j++) {
                matrizInt[i][j] = (int) Math.round(matrizDeCostos[i][j]);
            }
        }
        return matrizInt;
    }
}

