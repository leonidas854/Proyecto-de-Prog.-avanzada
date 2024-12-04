package com.empresa.proyeco.empresa.repository;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.empresa.proyeco.empresa.model.*;
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    Optional<Vehiculo> findByPlaca(String placa);
    List<Vehiculo>findByDisponibilidad(String disponibilidad);

}
