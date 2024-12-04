'use client'; // Habilitar eventos en el cliente
import { useState } from 'react';
import { useRouter } from 'next/navigation';
import './hi.css';

export default function RegistrarVehiculo() {
  const [formData, setFormData] = useState({
    placa: '',
    capacidadKg: '',
    disponibilidad: 'Disponible',
    tipoVehiculo: '',
  });

  const [mensaje, setMensaje] = useState('');
  const [mensajeTipo, setMensajeTipo] = useState('');

  const router = useRouter();

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setFormData({
      ...formData,
      [name]: value,
    });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    if (!formData.placa || !formData.capacidadKg || !formData.tipoVehiculo) {
      setMensaje('Todos los campos son obligatorios.');
      setMensajeTipo('error');
      return;
    }

    const capacidad = parseInt(formData.capacidadKg, 10);
    if (isNaN(capacidad) || capacidad <= 0) {
      setMensaje('La capacidad en kg debe ser un número mayor a 0.');
      setMensajeTipo('error');
      return;
    }

    const vehiculo = {
      placa: formData.placa,
      capacidadKg: capacidad,
      disponibilidad: 'Disponible',
      tipoVehiculo: formData.tipoVehiculo,
    };

    try {
      const response = await fetch('http://localhost:8080/vehiculos', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(vehiculo),
      });

      if (response.ok) {
        setMensaje('Vehículo registrado con éxito.');
        setMensajeTipo('success');
        setTimeout(() => {
          router.back();
        }, 1500);
      } else {
        const errorData = await response.json();
        setMensaje(`Error al registrar el vehículo: ${errorData.message}`);
        setMensajeTipo('error');
      }
    } catch (error) {
      setMensaje(`Error de conexión: ${error.message}`);
      setMensajeTipo('error');
    }
  };

  return (
    <div className="form-container">
      <h1 className="form-header">Registrar Vehículo</h1>
      <form onSubmit={handleSubmit} className="form">
        <div className="form-group">
          <label htmlFor="placa" className="form-label">Placa:</label>
          <input
            type="text"
            id="placa"
            name="placa"
            value={formData.placa}
            onChange={handleInputChange}
            required
            className="form-input"
          />
        </div>

        <div className="form-group">
          <label htmlFor="capacidadKg" className="form-label">Capacidad en Kg:</label>
          <input
            type="number"
            id="capacidadKg"
            name="capacidadKg"
            value={formData.capacidadKg}
            onChange={handleInputChange}
            required
            min="1"
            step="1"
            className="form-input"
          />
        </div>

        <div className="form-group">
          <label htmlFor="tipoVehiculo" className="form-label">Tipo de Vehículo:</label>
          <input
            type="text"
            id="tipoVehiculo"
            name="tipoVehiculo"
            value={formData.tipoVehiculo}
            onChange={handleInputChange}
            required
            className="form-input"
          />
        </div>

        <button type="submit" className="form-button">Registrar</button>
      </form>

      {mensaje && (
        <div className={`form-message ${mensajeTipo === 'success' ? 'form-message-success' : 'form-message-error'}`}>
          {mensaje}
        </div>
      )}
    </div>
  );
}
