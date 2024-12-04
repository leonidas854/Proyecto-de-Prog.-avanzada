'use client'; // Habilitar eventos en el cliente
import { useState } from 'react';
import './registro.css';
export default function ParametrosSensibilidad() {
  const [nombre, setNombre] = useState(''); // Nombre del conjunto de parámetros
  const [alpha, setAlpha] = useState(''); // Tasa de crecimiento
  const [gamma, setGamma] = useState(''); // Sensibilidad a la estacionalidad
  const [mensaje, setMensaje] = useState(''); // Mensaje de respuesta

  const handleSubmit = async (e) => {
    e.preventDefault();

    const parametros = {
      nombre,
      alpha: parseFloat(alpha),
      gamma: parseFloat(gamma),
    };

    try {
      const response = await fetch('http://localhost:8080/api/parametros', { // Ajusta la URL según tu backend
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
        },
        body: JSON.stringify(parametros),
      });

      if (response.ok) {
        setMensaje('Parámetros actualizados correctamente.');
        setNombre('');
        setAlpha('');
        setGamma('');
      } else {
        const errorData = await response.json();
        setMensaje(`Error al actualizar los parámetros: ${errorData.message}`);
      }
    } catch (error) {
      setMensaje('Error al conectarse con el servidor.');
    }
  };

  return (
    <div>
      <div className="form-container">
      <h1>Actualizar Parámetros</h1>
      <form onSubmit={handleSubmit}>
      <div className="grupo">
        <label htmlFor="nombre">Nombre del Conjunto de Parámetros:</label>
        <input
          id="nombre"
          type="text"
          value={nombre}
          onChange={(e) => setNombre(e.target.value)}
          required
        />
        </div>
        
        <div className="grupo">
        <label htmlFor="alpha">Tasa de Crecimiento (alpha):</label>
        <input
          id="alpha"
          type="number"
          step="0.01"
          value={alpha}
          onChange={(e) => setAlpha(e.target.value)}
          required
        />
        <br />
        </div>
        <div className="grupo">
        <label htmlFor="gamma">Sensibilidad a la Estacionalidad (gamma):</label>
        <input
          id="gamma"
          type="number"
          step="0.01"
          value={gamma}
          onChange={(e) => setGamma(e.target.value)}
          required
        /></div>
        
        <br />
        
        <button type="submit">Actualizar</button>
      </form>
      {mensaje && <p>{mensaje}</p>}
    </div>
    </div>
  );
}
