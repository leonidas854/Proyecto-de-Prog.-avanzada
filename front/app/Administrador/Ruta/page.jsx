'use client'; // Habilitar eventos en el cliente
import { useState, useEffect } from 'react';

export default function RutaOptimaPage() {
  const [sucursales, setSucursales] = useState([]);
  const [clientes, setClientes] = useState([]);
  const [vehiculos, setVehiculos] = useState([]);
  const [selectedSucursal, setSelectedSucursal] = useState('');
  const [selectedVehiculo, setSelectedVehiculo] = useState('');
  const [nombreRuta, setNombreRuta] = useState('');
  const [map, setMap] = useState(null);
  const [sucursalMarker, setSucursalMarker] = useState(null);
  const [directionsRenderer, setDirectionsRenderer] = useState(null);
  const [rutaOptima, setRutaOptima] = useState([]);
  const [showModal, setShowModal] = useState(false);

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

  useEffect(() => {
    fetch('http://localhost:8080/usuarios/sucursales')
      .then((res) => res.json())
      .then(setSucursales)
      .catch(() => alert('Error cargando sucursales.'));

    fetch('http://localhost:8080/vehiculos')
      .then((res) => res.json())
      .then(setVehiculos)
      .catch(() => alert('Error cargando vehículos.'));
  }, []);

  useEffect(() => {
    if (!map) return;

    fetch('http://localhost:8080/usuarios/clientes')
      .then((res) => res.json())
      .then((data) => {
        setClientes(data);
        plotClientsOnMap(data);
      })
      .catch(() => alert('Error cargando clientes.'));
  }, [map]);

  const plotClientsOnMap = (clients) => {
    if (!map) return;

    clients.forEach((client) => {
      new google.maps.Marker({
        position: { lat: client.latitud, lng: client.longitud },
        map,
        title: client.nombre,
      });
    });
  };

  const handleSucursalChange = (e) => {
    const selectedName = e.target.value;
    setSelectedSucursal(selectedName);

    if (sucursalMarker) {
      sucursalMarker.setMap(null);
    }

    const sucursal = sucursales.find((s) => s.nombre === selectedName);
    if (sucursal && map) {
      const marker = new google.maps.Marker({
        position: { lat: sucursal.latitud, lng: sucursal.longitud },
        map,
        title: sucursal.nombre,
        icon: 'http://maps.google.com/mapfiles/ms/icons/green-dot.png',
      });
      setSucursalMarker(marker);
      map.setCenter(marker.getPosition());
      map.setZoom(12);
    }
  };

  const handleCalculateRoute = () => {
    if (!selectedSucursal) {
      alert('Seleccione una sucursal.');
      return;
    }

    fetch(`http://localhost:8080/rutas/calcular?nombreSucursal=${selectedSucursal}`, { method: 'POST' })
      .then((res) => res.json())
      .then((data) => {
        setRutaOptima(data);
        drawRouteOnMap(data);
      })
      .catch(() => alert('Error calculando la ruta.'));
  };

  const handleAssignRoute = () => {
    if (!rutaOptima.length) {
      alert('Primero debe calcular la ruta.');
      return;
    }
    setShowModal(true);
  };

  const handleSaveRoute = () => {
    if (!nombreRuta || !selectedVehiculo) {
      alert('Complete todos los campos del formulario.');
      return;
    }
  
    // Crear el payload para enviar al backend
    const payload = {
      nombreRuta, // Nombre de la ruta
      vehiculoId: selectedVehiculo, // ID del vehículo seleccionado
      detalles: rutaOptima.map((punto) => ({
        lat: punto.lat,
        lng: punto.lng,
      })), // Detalles de la ruta como lista de objetos {lat, lng}
    };
  
    // Enviar el POST al backend
    fetch('http://localhost:8080/asignaciones', {
      method: 'POST',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(payload),
    })
      .then((response) => {
        if (response.ok) {
          alert('Ruta asignada con éxito.');
          setShowModal(false); // Cerrar modal al completar con éxito
        } else {
          return response.json().then((error) => {
            alert(`Error asignando la ruta: ${error.message || 'Error desconocido'}`);
          });
        }
      })
      .catch((err) => {
        console.error('Error asignando la ruta:', err);
        alert(`Error en la conexión: ${err.message}`);
      });
  };
  

  const drawRouteOnMap = (route) => {
    if (!directionsRenderer || !map) return;

    const waypoints = route.slice(1, -1).map((point) => ({
      location: { lat: point.lat, lng: point.lng },
      stopover: true,
    }));

    const directionsService = new google.maps.DirectionsService();
    directionsService.route(
      {
        origin: { lat: route[0].lat, lng: route[0].lng },
        destination: { lat: route[route.length - 1].lat, lng: route[route.length - 1].lng },
        waypoints,
        travelMode: google.maps.TravelMode.DRIVING,
      },
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
      <h1>Rutas Óptimas</h1>
      <div>
        <label htmlFor="sucursal">Selecciona una sucursal:</label>
        <select id="sucursal" value={selectedSucursal} onChange={handleSucursalChange}>
          <option value="">-- Seleccionar Sucursal --</option>
          {sucursales.map((s) => (
            <option key={s.id} value={s.nombre}>
              {s.nombre}
            </option>
          ))}
        </select>
      </div>
      <button onClick={handleCalculateRoute}>Calcular Ruta</button>
      <button onClick={handleAssignRoute}>Asignar Ruta</button>
      <div id="map" style={{ width: '100%', height: '400px', marginBottom: '20px' }}></div>

      {showModal && (
        <div style={styles.modalOverlay}>
          <div style={styles.modalContent}>
            <h2>Asignar Ruta</h2>
            <label>Nombre de la Ruta:</label>
            <input
              type="text"
              value={nombreRuta}
              onChange={(e) => setNombreRuta(e.target.value)}
              style={styles.input}
            />
            <label>Seleccione un Vehículo:</label>
            <select
              onChange={(e) => setSelectedVehiculo(e.target.value)}
              style={styles.input}
            >
              <option value="">Seleccione un Vehículo</option>
              {vehiculos.map((v) => (
                <option key={v.idVehiculo} value={v.idVehiculo}>
                  {v.placa} - {v.tipoVehiculo}
                </option>
              ))}
            </select>
            <button onClick={handleSaveRoute} style={styles.saveButton}>
              Guardar
            </button>
            <button onClick={() => setShowModal(false)} style={styles.cancelButton}>
              Cancelar
            </button>
          </div>
        </div>
      )}
    </div>
  );
}

const styles = {
  modalOverlay: {
    position: 'fixed',
    top: 0,
    left: 0,
    width: '100%',
    height: '100%',
    backgroundColor: 'rgba(0, 0, 0, 0.5)',
    display: 'flex',
    justifyContent: 'center',
    alignItems: 'center',
  },
  modalContent: {
    backgroundColor: 'white',
    padding: '20px',
    borderRadius: '8px',
    width: '400px',
  },
  input: {
    width: '100%',
    padding: '10px',
    marginBottom: '10px',
  },
  saveButton: {
    backgroundColor: 'green',
    color: 'white',
    padding: '10px',
    marginRight: '10px',
    border: 'none',
    borderRadius: '5px',
    cursor: 'pointer',
  },
  cancelButton: {
    backgroundColor: 'red',
    color: 'white',
    padding: '10px',
    border: 'none',
    borderRadius: '5px',
    cursor: 'pointer',
  },
};