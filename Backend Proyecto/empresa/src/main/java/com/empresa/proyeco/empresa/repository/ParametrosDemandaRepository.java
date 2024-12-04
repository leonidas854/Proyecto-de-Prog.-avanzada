package com.empresa.proyeco.empresa.repository;

import com.empresa.proyeco.empresa.model.ParametrosDemanda;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;



public interface ParametrosDemandaRepository extends JpaRepository<ParametrosDemanda, Long> {
    Optional<ParametrosDemanda> findByNombre(String nombre);
}