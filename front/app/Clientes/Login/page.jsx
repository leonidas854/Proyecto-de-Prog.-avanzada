'use client';
import { useState, useEffect } from 'react';
import './LoginEstilo.css';

export default function DemandaPage() {
  const [email, setEmail] = useState('');
  const [password, setPassword] = useState('');
  const [usuario, setUsuario] = useState(null);
  const [demandas, setDemandas] = useState([]);
  const [nuevaDemanda, setNuevaDemanda] = useState({
    cantidad: '',
    inicioVentana: '',
    finVentana: '',
  });

  const [mapLoaded, setMapLoaded] = useState(false);

  useEffect(() => {
    // Cargar el script de Google Maps
    const script = document.createElement('script');
    script.src = `https://maps.googleapis.com/maps/api/js?key=AIzaSyA_FbkeABMabU27PSRYMy2fzU1RPDaeWKY&callback=initMap`;
    script.async = true;
    script.defer = true;
    window.initMap = () => {
      setMapLoaded(true);
    };
    document.head.appendChild(script);

    return () => {
      // Eliminar el script si el componente se desmonta
      document.head.removeChild(script);
    };
  }, []);

  const buscarUsuario = async () => {
    if (!email || !password) {
      alert('Por favor, complete los campos de email y contraseña.');
      return;
    }

    try {
      const response = await fetch(`http://localhost:8080/usuarios/buscar?email=${email}&password=${password}`);
      if (!response.ok) {
        throw new Error('Usuario no encontrado o credenciales inválidas.');
      }
      const data = await response.json();
      setUsuario(data);
      setTimeout(() => {
        initMap(data.latitud, data.longitud); // Inicializar el mapa con las coordenadas del usuario
      }, 500);
      fetchDemandas(data.id);
    } catch (error) {
      alert(`Error al buscar usuario: ${error.message}`);
    }
  };

  const fetchDemandas = async (userId) => {
    try {
      const response = await fetch(`http://localhost:8080/demandas/${userId}`);
      if (!response.ok) {
        throw new Error('Error al obtener las demandas.');
      }
      const data = await response.json();
      setDemandas(data);
    } catch (error) {
      alert(`Error cargando demandas: ${error.message}`);
    }
  };

  const handleInputChange = (e) => {
    const { name, value } = e.target;
    setNuevaDemanda({ ...nuevaDemanda, [name]: value });
  };

  const handleCreateDemanda = async () => {
    if (!nuevaDemanda.cantidad || !nuevaDemanda.inicioVentana || !nuevaDemanda.finVentana) {
      alert('Por favor, complete todos los campos.');
      return;
    }

    if (!usuario) {
      alert('Debe iniciar sesión antes de realizar una demanda.');
      return;
    }

    const payload = {
      idUsuario: usuario.id,
      cantidad: parseInt(nuevaDemanda.cantidad, 10),
      inicioVentana: nuevaDemanda.inicioVentana,
      finVentana: nuevaDemanda.finVentana,
    };

    try {
      const response = await fetch('http://localhost:8080/demandas/crear', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json' },
        body: JSON.stringify(payload),
      });

      if (!response.ok) {
        throw new Error('Error al crear la demanda.');
      }

      alert('Demanda creada con éxito.');
      setNuevaDemanda({
        cantidad: '',
        inicioVentana: '',
        finVentana: '',
      });
      fetchDemandas(usuario.id);
    } catch (error) {
      alert(`Error creando demanda: ${error.message}`);
    }
  };

  const initMap = (lat = -17.824858, lng = -63.156085) => {
    if (!window.google) return;

    const map = new window.google.maps.Map(document.getElementById('map'), {
      center: { lat, lng },
      zoom: 14,
    });

    new window.google.maps.Marker({
      position: { lat, lng },
      map,
      title: 'Ubicación del Usuario',
    });
  };

  return (
    <div className="login-container">
  <h1 className="login-header">LOGIN USUARIO</h1>
  {!usuario ? (
    <div className="login-form">
      <h2 className="form-title">Login</h2>
      <div className="form-group">
        <label htmlFor="email" className="form-label">Email:</label>
        <input
          type="email"
          id="email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          placeholder="Email"
          className="form-input"
        />
      </div>
      
      <div className="form-group">
        <label htmlFor="password" className="form-label">Contraseña:</label>
        <input
          type="password"
          id="password"
          value={password}
          onChange={(e) => setPassword(e.target.value)}
          placeholder="Contraseña"
          className="form-input"
        />
      </div>
      
      <br/><br/>
      <button onClick={buscarUsuario} className="form-button">Buscar Usuario</button>
    </div>
      ) : (
        <div>
          <h2>Información del Usuario</h2>
          <p><b>Nombre:</b> {usuario.nombre}</p>
          <p><b>Email:</b> {usuario.email}</p>
          <p><b>Contacto:</b> {usuario.contacto}</p>
          <p><b>Ubicación:</b> Latitud: {usuario.latitud}, Longitud: {usuario.longitud}</p>

          <h2>Demandas</h2>
          {demandas.length > 0 ? (
            <table border="1" style={{ width: '100%', marginBottom: '20px' }}>
              <thead>
                <tr>
                  <th>ID</th>
                  <th>Cantidad</th>
                  <th>Descripción</th>
                  <th>Inicio Ventana</th>
                  <th>Fin Ventana</th>
                </tr>
              </thead>
              <tbody>
                {demandas.map((demanda) => (
                  <tr key={demanda.idDemanda}>
                    <td>{demanda.idDemanda}</td>
                    <td>{demanda.cantidad}</td>
                    <td>{demanda.descripcion}</td>
                    <td>{new Date(demanda.inicioVentana).toLocaleString()}</td>
                    <td>{new Date(demanda.finVentana).toLocaleString()}</td>
                  </tr>
                ))}
              </tbody>
            </table>
          ) : (
            <p>No se encontraron demandas.</p>
          )}

          <h2>Mapa de Ubicación del Usuario</h2>
          <div id="map" style={{ width: '100%', height: '400px', marginBottom: '20px' }}></div>

          <h2>Nueva Demanda</h2>
          <input
            type="number"
            name="cantidad"
            placeholder="Cantidad"
            value={nuevaDemanda.cantidad}
            onChange={handleInputChange}
            style={{ marginBottom: '10px' }}
          />
          <input
            type="datetime-local"
            name="inicioVentana"
            value={nuevaDemanda.inicioVentana}
            onChange={handleInputChange}
            style={{ marginBottom: '10px' }}
          />
          <input
            type="datetime-local"
            name="finVentana"
            value={nuevaDemanda.finVentana}
            onChange={handleInputChange}
            style={{ marginBottom: '10px' }}
          />
          <button onClick={handleCreateDemanda}>Crear Demanda</button>
        </div>
      )}
    </div>
  );
}
