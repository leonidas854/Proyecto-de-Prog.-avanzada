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

    // Guardar solo Alpha y Gamma
    public ParametrosDemandaDTO guardarParametros(ParametrosDemandaDTO dto) {
        if (dto == null) {
            throw new IllegalArgumentException("El DTO no puede ser nulo.");
        }
        validarParametros(dto);

        // Crear una nueva entidad con Alpha y Gamma
        ParametrosDemanda parametros = new ParametrosDemanda();
        parametros.setIdParametro(dto.getId());
        parametros.setNombre(dto.getNombre());
        parametros.setAlpha(dto.getAlpha());
        parametros.setGamma(dto.getGamma());

        // Guardar en la base de datos
        ParametrosDemanda saved = repository.save(parametros);

        // Retornar el DTO de respuesta
        return convertirADto(saved);
    }

    // Validar Alpha y Gamma
    private void validarParametros(ParametrosDemandaDTO dto) {
        if (dto.getAlpha() <= 0) {
            throw new IllegalArgumentException("Alpha debe ser mayor que 0.");
        }
        if (dto.getGamma() < 0) {
            throw new IllegalArgumentException("Gamma no puede ser negativo.");
        }
    }

    // Conversión de Entidad a DTO
    private ParametrosDemandaDTO convertirADto(ParametrosDemanda parametros) {
        return ParametrosDemandaDTO.builder()
                .id(parametros.getIdParametro())
                .alpha(parametros.getAlpha())
                .gamma(parametros.getGamma())
                .build();
    }
}  