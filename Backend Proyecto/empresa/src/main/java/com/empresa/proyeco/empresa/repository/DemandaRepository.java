package com.empresa.proyeco.empresa.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.empresa.proyeco.empresa.model.Demanda;

public interface DemandaRepository extends JpaRepository<Demanda, Long> {
    
}
