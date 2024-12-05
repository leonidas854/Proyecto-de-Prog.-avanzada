package com.empresa.proyeco.empresa.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.empresa.proyeco.empresa.model.Demanda;
import com.empresa.proyeco.empresa.model.Usuario;

public interface DemandaRepository extends JpaRepository<Demanda, Long> {
    List<Demanda> findByUsuarioId(Long userId);
 List<Demanda> findByUsuario(Usuario usuario);

    
}
