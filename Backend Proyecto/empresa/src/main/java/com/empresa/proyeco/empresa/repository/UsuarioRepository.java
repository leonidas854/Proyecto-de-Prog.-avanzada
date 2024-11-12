package com.empresa.proyeco.empresa.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.empresa.proyeco.empresa.model.*;
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

}
