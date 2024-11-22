package com.empresa.proyeco.empresa.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.empresa.proyeco.empresa.model.*;

public interface PedidoRepository extends JpaRepository<Pedido, Long> {

}
