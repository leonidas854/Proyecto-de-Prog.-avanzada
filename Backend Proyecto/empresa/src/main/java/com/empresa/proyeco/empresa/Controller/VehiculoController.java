package com.empresa.proyeco.empresa.Controller;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.empresa.proyeco.empresa.DTO.VehiculoDTO;
import com.empresa.proyeco.empresa.model.Vehiculo;
import com.empresa.proyeco.empresa.repository.VehiculoRepository;
import java.util.List;
import java.util.stream.Collectors;
@RestController
@RequestMapping("/vehiculos")

public class VehiculoController {

    @Autowired
    private VehiculoRepository vehiculoRepository;

    // Obtener todos los vehículos
    @GetMapping
    public List<VehiculoDTO> getVehiculos() {
        return vehiculoRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Obtener un vehículo por ID
    @GetMapping("/{id}")
    public VehiculoDTO getVehiculo(@PathVariable Long id) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con ID: " + id));
        return convertToDTO(vehiculo);
    }

    // Crear un nuevo vehículo
    @PostMapping
    public VehiculoDTO createVehiculo(@RequestBody VehiculoDTO vehiculoDTO) {
        Vehiculo vehiculo = convertToEntity(vehiculoDTO);
        Vehiculo savedVehiculo = vehiculoRepository.save(vehiculo);
        return convertToDTO(savedVehiculo);
    }

    // Actualizar un vehículo existente
    @PutMapping("/{id}")
    public VehiculoDTO updateVehiculo(@PathVariable Long id, @RequestBody VehiculoDTO vehiculoDTO) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con ID: " + id));

        vehiculo.setPlaca(vehiculoDTO.getPlaca());
        vehiculo.setCapacidadKg(vehiculoDTO.getCapacidadKg());
        vehiculo.setDisponibilidad(vehiculoDTO.getDisponibilidad());
        vehiculo.setTipoVehiculo(vehiculoDTO.getTipoVehiculo());

        Vehiculo updatedVehiculo = vehiculoRepository.save(vehiculo);
        return convertToDTO(updatedVehiculo);
    }

    // Eliminar un vehículo
    @DeleteMapping("/{id}")
    public String deleteVehiculo(@PathVariable Long id) {
        Vehiculo vehiculo = vehiculoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Vehículo no encontrado con ID: " + id));

        vehiculoRepository.delete(vehiculo);
        return "Vehículo eliminado con éxito";
    }

    // Métodos de conversión entre DTO y entidad
    private VehiculoDTO convertToDTO(Vehiculo vehiculo) {
        return VehiculoDTO.builder()
                .idVehiculo(vehiculo.getIdVehiculo())
                .placa(vehiculo.getPlaca())
                .capacidadKg(vehiculo.getCapacidadKg())
                .disponibilidad(vehiculo.getDisponibilidad())
                
                .tipoVehiculo(vehiculo.getTipoVehiculo())
                .build();
    }

    private Vehiculo convertToEntity(VehiculoDTO vehiculoDTO) {
        return Vehiculo.builder()
                .idVehiculo(vehiculoDTO.getIdVehiculo())
                .placa(vehiculoDTO.getPlaca())
                .capacidadKg(vehiculoDTO.getCapacidadKg())
                .disponibilidad(vehiculoDTO.getDisponibilidad())
               
                .tipoVehiculo(vehiculoDTO.getTipoVehiculo())
                .build();
    }

}
