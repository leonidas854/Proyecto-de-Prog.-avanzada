'use client'; // Habilitar eventos en el cliente
import { useState, useEffect } from 'react';

export default function VerAsignacionPage() {
  const [placa, setPlaca] = useState('');
  const [asignacion, setAsignacion] = useState(null);
  const [map, setMap] = useState(null);
  const [directionsRenderer, setDirectionsRenderer] = useState(null);

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

  const handleSearch = () => {
    if (!placa) {
      alert('Ingrese una placa válida.');
      return;
    }

    fetch(`http://localhost:8080/asignaciones/vehiculo?placa=${placa}`)
      .then((res) => res.json())
      .then((data) => {
        setAsignacion(data);
        if (data && data.detalles) {
          plotRouteOnMap(data.detalles);
        }
      })
      .catch(() => alert('No se encontró asignación para esta placa.'));
  };

  const plotRouteOnMap = (detalles) => {
    if (!directionsRenderer || !map) return;

    const puntos = detalles.split('; ').map((detalle) => {
      const [lat, lng] = detalle.replace('Lat: ', '').replace('Lng: ', '').split(', ');
      return { lat: parseFloat(lat), lng: parseFloat(lng) };
    });

    const waypoints = puntos.slice(1, -1).map((punto) => ({
      location: punto,
      stopover: true,
    }));

    const directionsService = new google.maps.DirectionsService();
    directionsService.route(
      {
        origin: puntos[0],
        destination: puntos[puntos.length - 1],
        waypoints,
        travelMode: google.maps.TravelMode.DRIVING,
      },
      //ALGO POR AQUI
      (result, status) => {
        if (status === google.maps.DirectionsStatus.OK) {
          directionsRenderer.setDirections(result);
        } else {
          alert('Error trazando la ruta.');
        }
      }
    );
  };

  return (
    <div style={{ padding: '20px' }}>
      <h1>Ver Asignación de Ruta</h1>
      <div>
        <label htmlFor="placa">Ingrese la Placa del Vehículo:</label>
        <input
          id="placa"
          type="text"
          value={placa}
          onChange={(e) => setPlaca(e.target.value)}
          style={{ marginLeft: '10px', padding: '5px' }}
        />
        <button onClick={handleSearch} style={{ marginLeft: '10px', padding: '5px 10px' }}>
          Buscar
        </button>
      </div>

      {asignacion && (
        <div style={{ marginTop: '20px' }}>
          <h2>Asignación Encontrada</h2>
          <p><strong>Placa:</strong> {placa}</p>
          <p><strong>Nombre de la Ruta:</strong> {asignacion.nombreRuta}</p>
          <p><strong>Fecha de Asignación:</strong> {asignacion.fechadeAsignacion}</p>
        </div>
      )}

      <div id="map" style={{ width: '100%', height: '400px', marginTop: '20px' }}></div>
    </div>
  );
}
