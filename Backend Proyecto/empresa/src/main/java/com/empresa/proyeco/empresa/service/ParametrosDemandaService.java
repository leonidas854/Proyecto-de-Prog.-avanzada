package com.empresa.proyeco.empresa.service;

import com.empresa.proyeco.empresa.DTO.ParametrosDemandaDTO;
import com.empresa.proyeco.empresa.model.ParametrosDemanda;
import com.empresa.proyeco.empresa.repository.ParametrosDemandaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ParametrosDemandaService {

    @Autowired
    private ParametrosDemandaRepository repository;

   
    public ParametrosDemandaDTO guardarParametros(ParametrosDemandaDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El DTO no puede ser nulo.");
        }
        validarParametros(dto);

     
        ParametrosDemanda parametros = new ParametrosDemanda();
        parametros.setIdParametro(dto.getId());
        parametros.setNombre(dto.getNombre());
        parametros.setAlpha(dto.getAlpha());
        parametros.setGamma(dto.getGamma());

        
        ParametrosDemanda saved = repository.save(parametros);

        
        return convertirADto(saved);
    }

   
    private void validarParametros(ParametrosDemandaDTO dto) {
        if (dto.getAlpha() <= 0) {
            throw new IllegalArgumentException("Alpha debe ser mayor que 0.");
        }
        if (dto.getGamma() < 0) {
            throw new IllegalArgumentException("Gamma no puede ser negativo.");
        }
    }

    
    private ParametrosDemandaDTO convertirADto(ParametrosDemanda parametros) {
        return ParametrosDemandaDTO.builder()
                .id(parametros.getIdParametro())
                .alpha(parametros.getAlpha())
                .gamma(parametros.getGamma())
                .build();
    }
}  