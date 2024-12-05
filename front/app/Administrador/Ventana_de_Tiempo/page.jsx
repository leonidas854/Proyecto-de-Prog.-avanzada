'use client'; // Habilitar eventos en el cliente
import { useState, useEffect } from 'react';

export default function LogisticaPage() {
  const [asignaciones, setAsignaciones] = useState([]);
  const [mensaje, setMensaje] = useState('');
  const [calculando, setCalculando] = useState(false);
  const [map, setMap] = useState(null);
  const [directionsRenderer, setDirectionsRenderer] = useState(null);
  const handleDownloadPDF = async () => {
    try {
      const response = await fetch('http://localhost:8080/reporte/asignaciones/pdf', {
        method: 'GET',
      });
      if (response.ok) {
        const blob = await response.blob();
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'reporte_asignaciones.pdf';
        document.body.appendChild(a);
        a.click();
        a.remove();
        setMensaje('Reporte descargado exitosamente.');
      } else {
        setMensaje('Error al generar el reporte.');
      }
    } catch (error) {
      console.error('Error al descargar el PDF:', error);
      setMensaje('Error en la conexión al servidor.');
    }
  };

  // Cargar Google Maps
  useEffect(() => {
    const scriptId = 'google-maps-script';
    const existingScript = document.getElementById(scriptId);

    if (!existingScript) {
      const script = document.createElement('script');
      script.id = scriptId;
      script.src = `https://maps.googleapis.com/maps/api/js?key=AIzaSyA_FbkeABMabU27PSRYMy2fzU1RPDaeWKY&callback=initMap`;
      script.async = true;
      script.defer = true;

      window.initMap = () => {
        const initialCoords = { lat: -17.3938, lng: -66.1568 };
        const mapInstance = new google.maps.Map(document.getElementById('map'), {
          zoom: 10,
          center: initialCoords,
        });
        setMap(mapInstance);

        const renderer = new google.maps.DirectionsRenderer({ map: mapInstance });
        setDirectionsRenderer(renderer);
      };

      document.body.appendChild(script);
    } else if (window.google) {
      window.initMap();
    }
  }, []);

  // Cargar asignaciones desde el backend
  const fetchAsignaciones = async () => {
    try {
      const response = await fetch('http://localhost:8080/logistica/asignaciones');
      const data = await response.json();

      if (Array.isArray(data)) {
        setAsignaciones(data);
        plotAssignmentsOnMap(data);
      } else {
        console.error('Respuesta inesperada:', data);
        setMensaje('Error: El servidor no devolvió un arreglo.');
      }
    } catch (error) {
      console.error('Error al cargar asignaciones:', error);
      setMensaje('Error cargando asignaciones.');
    }
  };

  // Calcular asignaciones en el backend
  const handleCalculateRoutes = async () => {
    setCalculando(true);
    setMensaje('');
    try {
      const response = await fetch('http://localhost:8080/logistica/calcular');
      if (response.ok) {
        setMensaje('Asignaciones calculadas exitosamente.');
        await fetchAsignaciones();
      } else {
        setMensaje('Error al calcular asignaciones.');
      }
    } catch (error) {
      console.error('Error en la conexión al servidor:', error);
      setMensaje('Error en la conexión al servidor.');
    } finally {
      setCalculando(false);
    }
  };

  // Dibujar asignaciones en el mapa
  const plotAssignmentsOnMap = (assignments) => {
    if (!map || !directionsRenderer) return;

    assignments.forEach((assignment) => {
      // Crear marcadores
      new google.maps.Marker({
        position: assignment.origen,
        map,
        label: assignment.sucursal,
        icon: 'http://maps.google.com/mapfiles/ms/icons/green-dot.png',
      });

      new google.maps.Marker({
        position: assignment.destino,
        map,
        label: assignment.cliente,
      });

      // Dibujar rutas
      const directionsService = new google.maps.DirectionsService();
      directionsService.route(
        {
          origin: assignment.origen,
          destination: assignment.destino,
          travelMode: google.maps.TravelMode.DRIVING,
        },
        (result, status) => {
          if (status === google.maps.DirectionsStatus.OK) {
            directionsRenderer.setDirections(result);
          } else {
            console.error('Error dibujando la ruta:', status);
          }
        }
      );
    });
  };

  // Cargar asignaciones al inicio
  useEffect(() => {
    fetchAsignaciones();
  }, []);

  return (
    <div style={{ padding: '20px', fontFamily: 'Arial, sans-serif' }}>
      <h1>Logística de Rutas Óptimas</h1>
      {mensaje && <p style={{ color: 'green' }}>{mensaje}</p>}

      <div style={{ marginBottom: '20px' }}>
        <button
          onClick={handleCalculateRoutes}
          style={{
            padding: '10px 20px',
            backgroundColor: '#007BFF',
            color: '#fff',
            border: 'none',
            borderRadius: '5px',
            cursor: 'pointer',
            marginRight: '10px',
          }}
          disabled={calculando}
        >
          {calculando ? 'Calculando...' : 'Calcular Asignaciones'}
        </button>
        <button
          onClick={handleDownloadPDF}
          style={{
            padding: '10px 20px',
            backgroundColor: '#28A745',
            color: '#fff',
            border: 'none',
            borderRadius: '5px',
            cursor: 'pointer',
          }}
        >
          Descargar Reporte PDF
        </button>
      </div>

      {/* Tabla y mapa existentes */}
      <table style={{ width: '100%', borderCollapse: 'collapse', marginBottom: '20px' }}>
        <thead>
          <tr>
            <th style={{ border: '1px solid #ddd', padding: '8px', backgroundColor: '#f4f4f4' }}>
              Sucursal
            </th>
            <th style={{ border: '1px solid #ddd', padding: '8px', backgroundColor: '#f4f4f4' }}>
              Cliente
            </th>
            <th style={{ border: '1px solid #ddd', padding: '8px', backgroundColor: '#f4f4f4' }}>
              Cantidad
            </th>
            <th style={{ border: '1px solid #ddd', padding: '8px', backgroundColor: '#f4f4f4' }}>
              Costo (Bs)
            </th>
          </tr>
        </thead>
        <tbody>
          {Array.isArray(asignaciones) &&
            asignaciones.map((asignacion, index) => (
              <tr key={index}>
                <td style={{ border: '1px solid #ddd', padding: '8px' }}>{asignacion.sucursal}</td>
                <td style={{ border: '1px solid #ddd', padding: '8px' }}>{asignacion.cliente}</td>
                <td style={{ border: '1px solid #ddd', padding: '8px' }}>
                  {asignacion.cantidad || 'N/A'}
                </td>
                <td style={{ border: '1px solid #ddd', padding: '8px' }}>
                  {asignacion.costo || 'N/A'}
                </td>
              </tr>
            ))}
        </tbody>
      </table>

      <div id="map" style={{ width: '100%', height: '500px', border: '1px solid black' }}></div>
    </div>
  );
}
