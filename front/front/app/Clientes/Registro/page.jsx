'use client'; // Habilitar eventos en el cliente
import { useState, useEffect } from 'react';
import '../ClienteEstilo.css';
//import './Registro.css';


export default function RegistroCliente() {
  const [nombre, setNombre] = useState('');
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [contacto, setContacto] = useState('');
  const [direccion, setDireccion] = useState('');
  const [latitud, setLatitud] = useState(0);
  const [longitud, setLongitud] = useState(0);
  const [circle, setCircle] = useState(null); // Estado para el círculo
  const [mapInstance, setMapInstance] = useState(null); // Para guardar la instancia del mapa

  


  useEffect(() => {
    // Verificar si el script de Google Maps ya está cargado
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
        initMap(); // Inicializar el mapa cuando el script se haya cargado
      };
    } else if (window.google) {
      // Si el script ya está cargado, inicializar directamente
      initMap();
    }

    return () => {
      if (script && !document.getElementById(scriptId)) {
        script.remove();
      }
    };  
  },   []); // Solo se ejecuta una vez al montar el componente

  // Función para inicializar el mapa
  const initMap = () => {
    const cercadoBounds = {
      north: -17.3400, // Límite norte
      south: -17.4400, // Límite sur
      east: -66.1200, // Límite este
      west: -66.2000, // Límite oeste
    };

    const coordInicial = { lat: -17.3926, lng: -66.1570 }; // Coordenadas iniciales de Cochabamba
    const map = new google.maps.Map(document.getElementById('map'), {
      zoom: 10,
      center: coordInicial,
      restriction: {
        latLngBounds: cercadoBounds,
        strictBounds: true,
      },
    });

    setMapInstance(map); // Guardamos la instancia del mapa

    // Evento de clic en el mapa
    map.addListener('click', (e) => {
      const lat = e.latLng.lat();
      const lng = e.latLng.lng();

      // Comprobamos que la ubicación esté dentro de los límites de Cochabamba
      if (
        lat <= cercadoBounds.north &&
        lat >= cercadoBounds.south &&
        lng <= cercadoBounds.east &&
        lng >= cercadoBounds.west
      ) {
        setLatitud(lat);
        setLongitud(lng);

        // Si ya existe un círculo, lo eliminamos de forma diferente
        removePreviousCircle(); // Función para eliminar el círculo anterior

        // Crear un nuevo círculo para marcar la ubicación
        const newCircle = new google.maps.Circle({
          center: { lat, lng },
          radius: 100, // Radio en metros del círculo (puedes ajustarlo)
          map: map,
          fillColor: "#FF0000", // Color de relleno
          fillOpacity: 0.35,  // Opacidad
          strokeColor: "#FF0000", // Color del borde
          strokeWeight: 1, // Grosor del borde
        });

        // Actualizar el círculo en el estado
        setCircle(newCircle); // Guardamos el nuevo círculo en el estado
      } else {
        alert('Por favor, selecciona un punto dentro de Cochabamba.');
      }
    });
  };

  // Función para eliminar el círculo anterior
  const removePreviousCircle = () => {
    if (circle) {
      // Si el círculo ya existe, lo eliminamos de la vista
      circle.setMap(null); // Eliminar el círculo actual de la vista
      setCircle(null); // Limpiar el estado de 'circle'
    }
  };

  // Función para manejar el submit del formulario
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
                /><span className="barra"></span> </div>

            <label  htmlFor="email"></label>
              <div className="grupo">
                <input
                type="email"
                id="email"
                name="email"
                placeholder="Email"
                required
                onChange={(e) => setEmail(e.target.value)}
                /><span className="barra"></span></div>

            <label htmlFor="password"></label>
              <div className="grupo">
                <input
                type="password"
                id="password"
                name="password"
                placeholder="Contraseña"
                required
                onChange={(e) => setPassword(e.target.value)}
                /><span className="barra"></span> </div>

            <label  htmlFor="contacto"></label>
              <div className="grupo">
                <input
                type="text"
                id="contacto"
                name="contacto"
                placeholder="Contacto"
                required
                onChange={(e) => setContacto(e.target.value)}
                /><span className="barra"></span> </div>

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
                  /></div>
              <div className="grupo">
                <input
                type="text"
                id="longitud"
                name="longitud"
                placeholder="Longitud"
                value={longitud}
                readOnly
              /></div>
          </div>
          </form>

          <div id="map" className="map-container" style={{ width: '100%', height: '400px' }}></div>

          <button type="submit">Registrarse</button>
        
    </div>
    </div>
  );
}
