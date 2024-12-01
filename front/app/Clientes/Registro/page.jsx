'use client'; // Habilitar eventos en el cliente
import { useState, useEffect } from 'react';
//import React, { SyntheticEvent ,useState} from 'react';
import '../ClienteEstilo.css';

export default function RegistroCliente() {
  const [nombre, setNombre] = useState('');
    const [email, setEmail] = useState('');
    const [password, setPassword] = useState('');
    const [contacto, setContacto] = useState('');
    const [direccion, setDireccion] = useState('');
    const [latitud, setLatitud] = useState(0);
    const [longitud, setLongitud] = useState(0);
    const [marker, setMarker] = useState(null);
    
  useEffect(() => {
    // Verificar si el script ya existe
    const scriptId = 'google-maps-script';
    let script = document.getElementById(scriptId);

    if (!script) {
      // Crear y agregar el script si no existe
      script = document.createElement('script');
      script.id = scriptId;
      script.src = `https://maps.googleapis.com/maps/api/js?key=AIzaSyA_FbkeABMabU27PSRYMy2fzU1RPDaeWKY&callback=initMap`;
      script.async = true;
      script.defer = true;
      document.body.appendChild(script);

      script.onload = () => {
        // Inicializar el mapa después de cargar el script
        initMap();
      };
    } else if (window.google) {
      // Si el script ya está cargado, inicializar directamente
      initMap();
    }

    // Limpiar el script al desmontar solo si lo hemos agregado
    return () => {
      if (script && !document.getElementById(scriptId)) {
        script.remove();
      }
    };
  }, [marker]);

  // Función para inicializar el mapa
  const initMap = () => {
    const cochabambaBounds = {
      north: -16.3,
      south: -17.7,
      east: -65.2,
      west: -67.2,
    };

    const coordInicial = { lat: -17.3938, lng: -66.1568 }; // Coordenadas iniciales de Cochabamba
    const mapInstance = new google.maps.Map(document.getElementById('map'), {
      zoom: 10,
      center: coordInicial,
      restriction: {
        latLngBounds: cochabambaBounds,
        strictBounds: true,
      },
    });

    // Evento de clic en el mapa
    mapInstance.addListener('click', (e) => {
      const lat = e.latLng.lat();
      const lng = e.latLng.lng();

      if (
        lat <= cochabambaBounds.north &&
        lat >= cochabambaBounds.south &&
        lng <= cochabambaBounds.east &&
        lng >= cochabambaBounds.west
      ) {
        setLatitud(lat);
        setLongitud(lng);

        // Crear o actualizar el marcador
        if (!marker) {
          const newMarker = new google.maps.Marker({
            position: { lat, lng },
            map: mapInstance,
            title: 'Ubicación Seleccionada',
          });
          setMarker(newMarker);
        } else {
          marker.setPosition({ lat, lng });
        }
      } else {
        alert('Por favor, selecciona un punto dentro de Cochabamba.');
      }
    });
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
      //await router.push('/Clientes');

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
          <label htmlFor="nombre">Nombre:</label>
          <input type="text" id="nombre" name="nombre" placeholder="Ingrese su nombre" 
          required onChange={(e) => setNombre(e.target.value)}/>

          <label htmlFor="email">Email:</label>
          <input type="email" id="email" name="email" placeholder="Ingrese su email" 
          required onChange={(e) => setEmail(e.target.value)}/>

          <label htmlFor="password">Contraseña:</label>
          <input type="password" id="password" name="password" placeholder="Ingrese su contraseña" 
          required onChange={(e) => setPassword(e.target.value)}/>

          <label htmlFor="contacto">Contacto:</label>
          <input type="text" id="contacto" name="contacto" placeholder="Ingrese su contacto" 
          required onChange={(e) => setContacto(e.target.value)}/>

          <label htmlFor="ubicacion">Ubicación:</label>
          <div className="ubicacion">
            <input
              type="text"
              id="latitud"
              name="latitud"
              placeholder="Latitud"
              value={latitud}
              readOnly
            onChange={(e) => setLatitud(e.target.value)}/>
            <input
              type="text"
              id="longitud"
              name="longitud"
              placeholder="Longitud"
              value={longitud}
              readOnly
              onChange={(e) => setLongitud(e.target.value)}
            />
          </div>

          <div id="map" className="map-container"></div>

          <button type="submit">Registrarse</button>
        </form>
      </div>
    </div>
  );
}
