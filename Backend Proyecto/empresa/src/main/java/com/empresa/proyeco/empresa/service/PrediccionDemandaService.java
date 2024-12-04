package com.empresa.proyeco.empresa.service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.empresa.proyeco.empresa.DTO.PrediccionDTO;
import com.empresa.proyeco.empresa.model.Demanda;
import com.empresa.proyeco.empresa.model.Oferta;
import com.empresa.proyeco.empresa.model.ParametrosDemanda;
import com.empresa.proyeco.empresa.model.Prediccion;
import com.empresa.proyeco.empresa.repository.DemandaRepository;
import com.empresa.proyeco.empresa.repository.OfertaRepository;
import com.empresa.proyeco.empresa.repository.ParametrosDemandaRepository;
import com.empresa.proyeco.empresa.repository.PrediccionRepository;

@Service
public class PrediccionDemandaService {

    @Autowired
    private ParametrosDemandaRepository parametrosDemandaRepository;

    @Autowired
    private DemandaRepository demandaRepository;
    @Autowired
    private PrediccionRepository prediccionRepository;

    @Autowired
    private OfertaRepository ofertaRepository;

    public PrediccionDTO predecirDemanda(String nombreParametros, Long idDemanda, int rangoMeses, double precio, String nombrePrediccion) {
        // Obtener los parámetros de demanda por nombre
        ParametrosDemanda parametros = parametrosDemandaRepository.findByNombre(nombreParametros)
                .orElseThrow(() -> new IllegalArgumentException("Parámetros no encontrados"));
    
        double alpha = parametros.getAlpha();
        double gamma = parametros.getGamma();
    
        // Calcular K (sumatoria de todas las ofertas)
        double K = ofertaRepository.findAll().stream()
                .mapToDouble(Oferta::getCantidad)
                .sum();
       
    
        // Obtener la demanda inicial por ID
        Demanda demanda = demandaRepository.findById(idDemanda)
                .orElseThrow(() -> new IllegalArgumentException("Demanda no encontrada"));
    
        double D0 = demanda.getCantidad();
    
        // Resolver predicción con Runge-Kutta
        double h = 1; // Paso temporal (1 mes)
        double t = 0; // Tiempo inicial
        double D = D0; // Demanda inicial
    
        StringBuilder prediccionTexto = new StringBuilder(); // Construir predicción como String
    
        for (int i = 0; i < rangoMeses; i++) {
            double k1 = h * ecuacionDemanda(t, D, alpha, gamma, K, precio);
            double k2 = h * ecuacionDemanda(t + h / 2, D + k1 / 2, alpha, gamma, K, precio);
            double k3 = h * ecuacionDemanda(t + h / 2, D + k2 / 2, alpha, gamma, K, precio);
            double k4 = h * ecuacionDemanda(t + h, D + k3, alpha, gamma, K, precio);
    
            D = D + (k1 + 2 * k2 + 2 * k3 + k4) / 6;
    
            // Guardar cada iteración como texto
            prediccionTexto.append(String.format("Mes %d: Demanda = %.2f%n", (i + 1), D));
    
            t += h;
        }
        Prediccion prediccion = Prediccion.builder()
            .nombre(nombrePrediccion)
            .rangoMeses(rangoMeses)
            .precio(precio)
            .parametrosDemanda(parametros)
            .prediccion(prediccionTexto.toString())
            .build();
    
        // Retornar un PrediccionDTO con los datos generales
        Prediccion prediccionGuardada = prediccionRepository.save(prediccion);

    // Convertir a DTO y devolver
    return PrediccionDTO.builder()
            .id(prediccionGuardada.getId()) // Ahora el ID está generado automáticamente
            .nombre(prediccionGuardada.getNombre())
            .rangoMeses(prediccionGuardada.getRangoMeses())
            .precio(prediccionGuardada.getPrecio())
            .idParametrosDemanda(prediccionGuardada.getParametrosDemanda().getIdParametro())
            .prediccion(prediccionGuardada.getPrediccion())
            .build();
    }
    


    // Implementación de la ecuación diferencial f(D(t), t)
    private double ecuacionDemanda(double t, double D, double alpha,double gamma, double K, double P) {
        return  alpha * (1 - D / K)*D - 12.41 * D * P +D * Math.pow(gamma, 2) * Math.sin((2 * Math.PI * t) / 12) - 0.00720474*D;
    }
    public List<PrediccionDTO> obtenerTodasPredicciones() {
    return prediccionRepository.findAll()
        .stream()
        .map(this::convertirADto)
        .collect(Collectors.toList());
}
private PrediccionDTO convertirADto(Prediccion prediccion) {
    return PrediccionDTO.builder()
        .id(prediccion.getId())
        .nombre(prediccion.getNombre())
        .rangoMeses(prediccion.getRangoMeses())
        .precio(prediccion.getPrecio())
        .idParametrosDemanda(prediccion.getParametrosDemanda().getIdParametro()) // Asegúrate de que `getParametrosDemanda` existe
        .prediccion(prediccion.getPrediccion()) // Texto o datos serializados
        .build();
}


}
