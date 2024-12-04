package com.empresa.proyeco.empresa.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.empresa.proyeco.empresa.model.*;

public interface DemandaRepository extends JpaRepository<Demanda, Long> {
    List<Demanda> findByUsuarioId(Long userId);
    
}
