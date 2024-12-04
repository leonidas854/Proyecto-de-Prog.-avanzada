'use client'; // Habilitar eventos en el cliente
import { useState, useEffect } from 'react';
import '../ClienteEstilo.css';

export default function RegistroCliente() {
  const [nombre, setNombre] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [contacto, setContacto] = useState('');
  const [direccion, setDireccion] = useState('');
  const [latitud, setLatitud] = useState(0);
  const [longitud, setLongitud] = useState(0);
  const [circle, setCircle] = useState(null);
  const [mapInstance, setMapInstance] = useState(null);

  useEffect(() => {
    const scriptId = 'google-maps-script';
    let script = document.getElementById(scriptId);

    if (!script) {
      script = document.createElement('script');
      script.id = scriptId;
      script.src = `https://maps.googleapis.com/maps/api/js?key=AIzaSyA_FbkeABMabU27PSRYMy2fzU1RPDaeWKY&callback=initMap`;
      script.async = true;
      script.defer = true;
      document.body.appendChild(script);

      script.onload = () => {
        window.initMap = initMap; // Asignar initMap al ámbito global
        initMap();
      };
    } else if (window.google) {
      initMap();
    }

    return () => {
      if (circle) {
        circle.setMap(null); // Eliminar el círculo si existe
      }
    };
  }, []);

  const initMap = () => {
    const cercadoBounds = {
      north: -17.3400,
      south: -17.4400,
      east: -66.1200,
      west: -66.2000,
    };

    const coordInicial = { lat: -17.3926, lng: -66.1570 };

    if (window.google && window.google.maps) {
      const map = new google.maps.Map(document.getElementById('map'), {
        zoom: 10,
        center: coordInicial,
        restriction: {
          latLngBounds: cercadoBounds,
          strictBounds: true,
        },
      });

      setMapInstance(map);

      map.addListener('click', (e) => {
        const lat = e.latLng.lat();
        const lng = e.latLng.lng();

        if (
          lat <= cercadoBounds.north &&
          lat >= cercadoBounds.south &&
          lng <= cercadoBounds.east &&
          lng >= cercadoBounds.west
        ) {
          setLatitud(lat);
          setLongitud(lng);
          removePreviousCircle();

          const newCircle = new google.maps.Circle({
            center: { lat, lng },
            radius: 100,
            map: map,
            fillColor: "#FF0000",
            fillOpacity: 0.35,
            strokeColor: "#FF0000",
            strokeWeight: 1,
          });

          setCircle(newCircle);
        } else {
          alert('Por favor, selecciona un punto dentro de Cochabamba.');
        }
      });
    } else {
      console.error('Google Maps API no está cargada.');
    }
  };

  const removePreviousCircle = () => {
    if (circle && circle.getMap()) {
      circle.setMap(null);
    }
    setCircle(null);
  };

  async function handleSubmit(e) {
    e.preventDefault();

    if (!latitud || !longitud) {
      alert("Por favor, selecciona una ubicación en el mapa.");
      return;
    }

    try {
      const response = await fetch("http://localhost:8080/usuarios", {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          nombre,
          email,
          password,
          contacto,
          direccion,
          latitud,
          longitud,
          tipoUsuario: "CLIENTE"
        }),
      });

      if (response.ok) {
        const data = await response.json();
        alert(`Usuario registrado con éxito: ID ${data.id}`);
      } else {
        const errorData = await response.json();
        alert(`Error al registrar el usuario: ${errorData.message}`);
      }
    } catch (error) {
      alert(`Error al conectar con el servidor: ${error.message}`);
    }
  }

  return (
    <div className="page-container form-page">
      <div className="form-container">
        <h1>REGISTRO CLIENTE</h1>
        <form onSubmit={handleSubmit}>
          <label htmlFor="nombre"></label>
          <div className="grupo">
            <input
              type="text"
              id="nombre"
              name="nombre"
              placeholder="Nombre"
              required
              onChange={(e) => setNombre(e.target.value)}
            /><span className="barra"></span>
          </div>

          <label htmlFor="email"></label>
          <div className="grupo">
            <input
              type="email"
              id="email"
              name="email"
              placeholder="Email"
              required
              onChange={(e) => setEmail(e.target.value)}
            /><span className="barra"></span>
          </div>

          <label htmlFor="password"></label>
          <div className="grupo">
            <input
              type="password"
              id="password"
              name="password"
              placeholder="Contraseña"
              required
              onChange={(e) => setPassword(e.target.value)}
            /><span className="barra"></span>
          </div>

          <label htmlFor="contacto"></label>
          <div className="grupo">
            <input
              type="text"
              id="contacto"
              name="contacto"
              placeholder="Contacto"
              required
              onChange={(e) => setContacto(e.target.value)}
            /><span className="barra"></span>
          </div>

          <label htmlFor="ubicacion"></label>
          <div className="ubicacion">
            <div className="grupo">
              <input
                type="text"
                id="latitud"
                name="latitud"
                placeholder="Latitud"
                value={latitud}
                readOnly
              />
            </div>
            <div className="grupo">
              <input
                type="text"
                id="longitud"
                name="longitud"
                placeholder="Longitud"
                value={longitud}
                readOnly
              />
            </div>
          </div>

          <button type="submit">Registrarse</button>
        </form>
        <div id="map" className="map-container" style={{ width: '100%', height: '400px' }}></div>
      </div>
    </div>
  );
}
