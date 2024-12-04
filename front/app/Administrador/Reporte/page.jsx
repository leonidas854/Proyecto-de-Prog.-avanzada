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

  // Escalado de los datos al tamaño del SVG
  const scaleX = (x) => (x / maxX) * 600 + 50;
  const scaleY = (y) => 400 - ((y - minY) / (maxY - minY)) * 350;

  return (
    <svg width="700" height="450" style={{ border: '1px solid black' }}>
      {/* Ejes */}
      <line x1="50" y1="400" x2="650" y2="400" stroke="black" />
      <line x1="50" y1="50" x2="50" y2="400" stroke="black" />

      {/* Etiquetas de los Ejes */}
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

      {/* Puntos y Líneas */}
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

// Formulario de Predicción
export default function PredictionForm() {
  const [formData, setFormData] = useState({
    nombreParametros: '',
    idDemanda: '',
    rangoMeses: '',
    precio: '',
    nombrePrediccion: '',
  });

  const [nombresParametros, setNombresParametros] = useState([]);
  const [idsDemanda, setIdsDemanda] = useState([]);
  const [graphData, setGraphData] = useState([]);
  const [mensaje, setMensaje] = useState('');

  // Cargar opciones de los combo box al montar el componente
  useEffect(() => {
    const fetchNombresParametros = async () => {
      try {
        const response = await fetch('http://localhost:8080/api/parametros/nombres');
        if (response.ok) {
          const data = await response.json();
          setNombresParametros(data);
        }
      } catch (error) {
        console.error('Error al obtener los nombres de parámetros:', error);
      }
    };

    const fetchIdsDemanda = async () => {
      try {
        const response = await fetch('http://localhost:8080/demandas/ids');
        if (response.ok) {
          const data = await response.json();
          setIdsDemanda(data);
        }
      } catch (error) {
        console.error('Error al obtener los IDs de demanda:', error);
      }
    };

    fetchNombresParametros();
    fetchIdsDemanda();
  }, []);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

  const handleSubmit = async (e) => {
    e.preventDefault();

    try {
        const response = await fetch('http://localhost:8080/api/prediccion', {
          method: 'POST',
          headers: { 'Content-Type': 'application/json' },
          body: JSON.stringify({
            ...formData,
            idDemanda: parseInt(formData.idDemanda, 10),
            rangoMeses: parseInt(formData.rangoMeses, 10),
            precio: parseFloat(formData.precio),
          }),
        });
      
        if (!response.ok) {
          const errorText = await response.text();
          console.error('Error en la respuesta del servidor:', errorText);
          setMensaje(`Error al generar la predicción: ${response.status}`);
          return;
        }
      
        const data = await response.json();
        if (!data.prediccion) {
          console.error('Respuesta inesperada del servidor:', data);
          setMensaje('Error: La predicción no está disponible.');
          return;
        }
      
        const processedData = data.prediccion
        .split('\r\n')
        .filter((line) => line.includes('Mes')) // Filtrar líneas relevantes
        .map((line) => {
          const match = line.match(/Mes (\d+): Demanda = ([\d.-]+)/);
          if (!match || isNaN(parseFloat(match[2]))) {
            console.warn('Línea no válida omitida:', line);
            return null; // Omitir líneas no válidas
          }
          return {
            x: parseInt(match[1], 10),
            y: parseFloat(match[2]),
          };
        })
        .filter((point) => point !== null); // Eliminar valores nulos
      
      
        setGraphData(processedData);
        setMensaje('Predicción generada exitosamente.');
      } catch (error) {
        console.error('Error al conectarse con el servidor:', error);
        setMensaje('Error al conectarse con el servidor.');
      }
      

  };

  return (
    <div>
      <h1>Predicción de Demanda</h1>
      <form onSubmit={handleSubmit}>
        <label>Nombre de Parámetros:</label>
        <select
          name="nombreParametros"
          value={formData.nombreParametros}
          onChange={handleChange}
          required
        >
          <option value="">Seleccione...</option>
          {nombresParametros.map((nombre, index) => (
            <option key={index} value={nombre}>
              {nombre}
            </option>
          ))}
        </select>
        <br />
        <label>ID de Demanda:</label>
        <select
          name="idDemanda"
          value={formData.idDemanda}
          onChange={handleChange}
          required
        >
          <option value="">Seleccione...</option>
          {idsDemanda.map((demanda, index) => (
            <option key={index} value={demanda.idDemanda}>
              {`ID: ${demanda.idDemanda}, Cantidad: ${demanda.cantidad}`}
            </option>
          ))}
        </select>
        <br />
        <label>Rango de Meses:</label>
        <input
          name="rangoMeses"
          type="number"
          value={formData.rangoMeses}
          onChange={handleChange}
          required
        />
        <br />
        <label>Precio:</label>
        <input
          name="precio"
          type="number"
          step="0.01"
          value={formData.precio}
          onChange={handleChange}
          required
        />
        <br />
        <label>Nombre de Predicción:</label>
        <input
          name="nombrePrediccion"
          type="text"
          value={formData.nombrePrediccion}
          onChange={handleChange}
          required
        />
        <br />
        <button type="submit">Generar Predicción</button>
      </form>
      {mensaje && <p>{mensaje}</p>}
      {graphData.length > 0 && <Graph data={graphData} />}
    </div>
  );
}