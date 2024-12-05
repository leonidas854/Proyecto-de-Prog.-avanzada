package com.empresa.proyeco.empresa.service;

import com.empresa.proyeco.empresa.DTO.OfertaDTO;
import com.empresa.proyeco.empresa.model.Oferta;
import com.empresa.proyeco.empresa.repository.OfertaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OfertaService {

    @Autowired
    private OfertaRepository ofertaRepository;

    
    public List<OfertaDTO> getAllOfertas() {
        return ofertaRepository.findAll()
                .stream()
                .map(OfertaDTO::fromEntity)
                .collect(Collectors.toList());
    }

   
    public OfertaDTO getOfertaById(Long id) {
        return ofertaRepository.findById(id)
                .map(OfertaDTO::fromEntity)
                .orElse(null);
    }

    
    public OfertaDTO createOferta(OfertaDTO ofertaDTO) {
        Oferta oferta = ofertaDTO.toEntity();
        Oferta savedOferta = ofertaRepository.save(oferta);
        return OfertaDTO.fromEntity(savedOferta);
    }

  
    public OfertaDTO updateOferta(Long id, OfertaDTO ofertaDTO) {
        if (ofertaRepository.existsById(id)) {
            Oferta oferta = ofertaDTO.toEntity();
            oferta.setIdOferta(id);
            Oferta updatedOferta = ofertaRepository.save(oferta);
            return OfertaDTO.fromEntity(updatedOferta);
        }
        return null;
    }

    
    public boolean deleteOferta(Long id) {
        if (ofertaRepository.existsById(id)) {
            ofertaRepository.deleteById(id);
            return true;
        }
        return false;
    }
}
