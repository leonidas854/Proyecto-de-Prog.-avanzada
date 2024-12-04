'use client';

import React, { useState, useEffect } from 'react';

// Componente para la gráfica
const Graph = ({ data }) => {
  if (data.length === 0) {
    return <p>No hay datos para graficar.</p>;
  }

  const maxX = Math.max(...data.map((d) => d.x));
  const maxY = Math.max(...data.map((d) => d.y));
  const minY = Math.min(...data.map((d) => d.y));

  const scaleX = (x) => (x / maxX) * 600 + 50;
  const scaleY = (y) => 400 - ((y - minY) / (maxY - minY)) * 350;

  return (
    <svg width="700" height="450" style={{ border: '1px solid black' }}>
      <line x1="50" y1="400" x2="650" y2="400" stroke="black" />
      <line x1="50" y1="50" x2="50" y2="400" stroke="black" />
      {data.map((point, i) => (
        <text key={`x-label-${i}`} x={scaleX(point.x)} y="420" fontSize="12" textAnchor="middle">
          Mes {point.x}
        </text>
      ))}
      {[...Array(5)].map((_, i) => {
        const y = minY + (i * (maxY - minY)) / 4;
        return (
          <text key={`y-label-${i}`} x="20" y={scaleY(y)} fontSize="12" textAnchor="end">
            {y.toFixed(2)}
          </text>
        );
      })}
      {data.map((point, i) => {
        const x = scaleX(point.x);
        const y = scaleY(point.y);

        return (
          <g key={`point-${i}`}>
            {i > 0 && (
              <line
                x1={scaleX(data[i - 1].x)}
                y1={scaleY(data[i - 1].y)}
                x2={x}
                y2={y}
                stroke="blue"
                strokeWidth="2"
              />
            )}
            <circle cx={x} cy={y} r="4" fill="red" />
          </g>
        );
      })}
    </svg>
  );
};

// Componente principal
export default function SimulacionTransporte() {
  const [nombrePrediccion, setNombrePrediccion] = useState('');
  const [predicciones, setPredicciones] = useState([]);
  const [vehiculosAsignados, setVehiculosAsignados] = useState([]);
  const [mensaje, setMensaje] = useState('');
  const [reporte, setReporte] = useState('');
  const [graphData, setGraphData] = useState([]);

  // Cargar predicciones desde el backend
  useEffect(() => {
    const fetchPredicciones = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/prediccion/todas');
        if (response.ok) {
          const data = await response.json();
          setPredicciones(data);
        } else {
          setMensaje('Error al cargar predicciones.');
        }
      } catch (error) {
        console.error('Error al obtener las predicciones:', error);
        setMensaje('Error de conexión.');
      }
    };

    fetchPredicciones();
  }, []);

  // Simular transporte
  const handleSimulacion = async (e) => {
    e.preventDefault();
    setMensaje('');
    setReporte('');

    if (!nombrePrediccion) {
      setMensaje('Debe seleccionar una predicción válida.');
      return;
    }

    try {
      const response = await fetch(
        `http://localhost:8080/api/transporte/simular?nombrePrediccion=${nombrePrediccion}`,
        { method: 'POST' }
      );

      if (response.ok) {
        const data = await response.json();
        setVehiculosAsignados(data);
        setMensaje('Simulación exitosa.');
      } else {
        const errorText = await response.text();
        setMensaje(`Error: ${errorText}`);
      }
    } catch (error) {
      setMensaje('Error al conectar con el servidor.');
    }
  };

  // Generar reporte
  const handleGenerarReporte = async () => {
    setMensaje('');
    setVehiculosAsignados([]);

    if (!nombrePrediccion) {
      setMensaje('Debe seleccionar una predicción válida.');
      return;
    }

    try {
      const response = await fetch(
        `http://localhost:8080/api/transporte/reporte?nombrePrediccion=${nombrePrediccion}`,
        { method: 'POST' }
      );

      if (response.ok) {
        const data = await response.text();
        setReporte(data);

        const processedData = data
          .split('\n')
          .filter((line) => line.includes('Demanda'))
          .map((line) => {
            const match = line.match(/Mes (\d+): Demanda = ([\d.-]+)/);
            if (match) {
              return { x: parseInt(match[1], 10), y: parseFloat(match[2]) };
            }
            return null;
          })
          .filter((item) => item !== null);

        setGraphData(processedData);
      } else {
        const errorText = await response.text();
        setMensaje(`Error: ${errorText}`);
      }
    } catch (error) {
      setMensaje('Error al conectar con el servidor.');
    }
  };

  return (
    <div>
      <h1>Simulación de Transporte</h1>
      <form onSubmit={handleSimulacion}>
        <label>Seleccione una Predicción:</label>
        <select
          value={nombrePrediccion}
          onChange={(e) => setNombrePrediccion(e.target.value)}
          required
        >
          <option value="">Seleccione...</option>
          {predicciones.map((prediccion) => (
            <option key={prediccion.id} value={prediccion.nombre}>
              {prediccion.nombre}
            </option>
          ))}
        </select>
        <br />
        <button type="submit">Simular Transporte</button>
        <button
          type="button"
          onClick={handleGenerarReporte}
          style={{ marginLeft: '10px' }}
        >
          Generar Reporte
        </button>
      </form>
      {mensaje && <p>{mensaje}</p>}
      {vehiculosAsignados.length > 0 && (
        <ul>
          {vehiculosAsignados.map((vehiculo, index) => (
            <li key={index}>
              {vehiculo.placa} - Capacidad: {vehiculo.capacidadKg} Kg
            </li>
          ))}
        </ul>
      )}
      {reporte && <pre>{reporte}</pre>}
      {graphData.length > 0 && <Graph data={graphData} />}
    </div>
  );
}
