package com.empresa.proyeco.empresa.repository;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import com.empresa.proyeco.empresa.model.*;
public interface VehiculoRepository extends JpaRepository<Vehiculo, Long> {
    Optional<Vehiculo> findByPlaca(String placa);

}
