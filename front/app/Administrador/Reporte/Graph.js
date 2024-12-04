'use client';
import React from 'react';

const Graph = ({ data }) => {
  if (data.length === 0) {
    return <p>No hay datos para graficar.</p>;
  }

  // Calcular los límites del eje X e Y
  const maxX = Math.max(...data.map((punto) => punto.x));
  const minY = Math.min(...data.map((punto) => punto.y));
  const maxY = Math.max(...data.map((punto) => punto.y));

  // Funciones para escalar los puntos al área del SVG
  const scaleX = (x) => (x / maxX) * 90 + 50; // Escalado para eje X
  const scaleY = (y) => 390 - ((y - minY) / (maxY - minY)) * 380; // Escalado para eje Y

  return (
    <div style={{ width: '100%', height: '400px', position: 'relative' }}>
      <svg width="100%" height="100%">
        {/* Ejes */}
        <line x1="50" y1="10" x2="50" y2="390" stroke="black" />
        <line x1="50" y1="390" x2="600" y2="390" stroke="black" />

        {/* Etiquetas del Eje X */}
        {data.map((punto, i) => (
          <text
            key={`x-label-${i}`}
            x={scaleX(punto.x)}
            y={400}
            textAnchor="middle"
            fontSize="12"
          >
            Mes {punto.x}
          </text>
        ))}

        {/* Etiquetas del Eje Y */}
        {[...Array(5)].map((_, i) => {
          const y = minY + (i * (maxY - minY)) / 4;
          return (
            <text
              key={`y-label-${i}`}
              x="20"
              y={scaleY(y)}
              textAnchor="end"
              fontSize="12"
            >
              {y.toFixed(2)}
            </text>
          );
        })}

        {/* Puntos y Líneas */}
        {data.map((punto, i) => {
          const x = scaleX(punto.x);
          const y = scaleY(punto.y);

          return (
            <g key={`punto-${i}`}>
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
    </div>
  );
};

export default Graph;
