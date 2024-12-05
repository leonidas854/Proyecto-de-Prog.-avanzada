package com.empresa.proyeco.empresa.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.empresa.proyeco.empresa.model.Oferta;
import com.empresa.proyeco.empresa.model.Usuario;
public interface OfertaRepository extends JpaRepository<Oferta, Long> {
   

List<Oferta> findByUsuario(Usuario usuario);
}
