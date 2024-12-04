package com.empresa.proyeco.empresa.service;


import com.empresa.proyeco.empresa.DTO.DemandaDTO;
import com.empresa.proyeco.empresa.model.Demanda;
import com.empresa.proyeco.empresa.model.Usuario;
import com.empresa.proyeco.empresa.repository.DemandaRepository;
import com.empresa.proyeco.empresa.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class DemandaService {

    @Autowired
    private DemandaRepository demandaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    public List<DemandaDTO> obtenerDemandasPorUsuario(Long userId) {
        List<Demanda> demandas = demandaRepository.findByUsuarioId(userId);
    
        if (demandas == null || demandas.isEmpty()) {
            return new ArrayList<>(); // Devuelve una lista vacía si no hay demandas
        }
    
        return demandas.stream()
                .map(demanda -> DemandaDTO.builder()
                        .idDemanda(demanda.getIdDemanda())
                        .cantidad(demanda.getCantidad())
                        .idUsuario(demanda.getUsuario().getId())
                        .descripcion(demanda.getDescripcion())
                        .inicioVentana(demanda.getInicioVentana())
                        .finVentana(demanda.getFinVentana())
                        .build())
                .collect(Collectors.toList());
    }
    
    

private DemandaDTO convertToDTO(Demanda demanda) {
    return DemandaDTO.builder()
            .idDemanda(demanda.getIdDemanda())
            .cantidad(demanda.getCantidad())
            .idUsuario((demanda.getUsuario().getId()))
            .descripcion(demanda.getDescripcion())
            .inicioVentana(demanda.getInicioVentana())
            .finVentana(demanda.getFinVentana())
            .build();
}
public DemandaDTO crearDemanda(DemandaDTO demandaDTO) {
    Usuario usuario = usuarioRepository.findById(demandaDTO.getIdUsuario())
            .orElseThrow(() -> new RuntimeException("Usuario no encontrado con ID: " + demandaDTO.getIdUsuario()));

    Demanda demanda = Demanda.builder()
            .cantidad(demandaDTO.getCantidad())
            .usuario(usuario)
            .descripcion(demandaDTO.getDescripcion())
            .inicioVentana(demandaDTO.getInicioVentana())
            .finVentana(demandaDTO.getFinVentana())
            .build();

    Demanda nuevaDemanda = demandaRepository.save(demanda);

    return DemandaDTO.builder()
            .idDemanda(nuevaDemanda.getIdDemanda())
            .cantidad(nuevaDemanda.getCantidad())
            .idUsuario(nuevaDemanda.getUsuario().getId())
            .descripcion(nuevaDemanda.getDescripcion())
            .inicioVentana(nuevaDemanda.getInicioVentana())
            .finVentana(nuevaDemanda.getFinVentana())
            .build();
}


}

