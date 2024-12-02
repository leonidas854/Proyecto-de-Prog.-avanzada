package com.empresa.proyeco.empresa.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.empresa.proyeco.empresa.model.TipoUsuario;
import com.empresa.proyeco.empresa.model.Usuario;
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    Optional<Usuario> findByNombreAndTipoUsuario(String nombre, TipoUsuario tipoUsuario);
    List<Usuario> findAllByTipoUsuario(TipoUsuario tipoUsuario);


}
