package com.empresa.proyeco.empresa.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import com.empresa.proyeco.empresa.model.Clientes;

public interface  ClientesRepository extends JpaRepository<Clientes,Long> {

}
