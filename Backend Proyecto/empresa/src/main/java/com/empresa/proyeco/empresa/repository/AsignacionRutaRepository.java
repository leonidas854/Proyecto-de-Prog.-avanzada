package com.empresa.proyeco.empresa.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


import com.empresa.proyeco.empresa.model.AsignacionRuta;
import com.empresa.proyeco.empresa.model.Vehiculo;

public interface AsignacionRutaRepository extends JpaRepository<AsignacionRuta, Long> {
    Optional<AsignacionRuta> findByVehiculo(Vehiculo vehiculo);

}
