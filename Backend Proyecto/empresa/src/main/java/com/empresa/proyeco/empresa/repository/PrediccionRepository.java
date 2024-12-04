package com.empresa.proyeco.empresa.repository;


import com.empresa.proyeco.empresa.model.Prediccion;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PrediccionRepository extends JpaRepository<Prediccion, Long> {
    Optional<Prediccion> findByNombre(String nombre);
}

