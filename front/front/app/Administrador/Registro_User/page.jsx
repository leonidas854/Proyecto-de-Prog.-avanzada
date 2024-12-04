'use client'; // Habilita eventos en el cliente
import '../EstiloUsuario.css'; // Importa los estilos
import { useRouter } from 'next/navigation'; // Hook para manejar navegación

export default function FrmUsuario() {
  const router = useRouter(); // Inicializa el router para la navegación

  const handleSubmit = (event) => {
    event.preventDefault(); // Evita la recarga de la página al enviar el formulario
    alert('¡Usuario registrado con éxito!'); // Muestra el mensaje emergente
    router.back(); // Navega a la página anterior
  };

  return (
    <div className="registro-page">
      {/* Barra de navegación */}
      <nav className="navbar">
        <div className="navbar-left">
          <img src="/image/cambioRap.jpg" alt="Icono Izquierdo" className="icono" />
        </div>
        <div className="navbar-center">
          <span>ADMINISTRADOR</span>
        </div>
        <div className="navbar-right">
          <img src="/image/iconlogi.jpg" alt="Icono Derecho" className="icono" />
        </div>
      </nav>

      {/* Contenedor del formulario */}
      <div className="form-container">
        <h2>REGISTRO DE USUARIO</h2>
        <form onSubmit={handleSubmit}>
          <label htmlFor="nombre">Nombre</label>
          <input
            type="text"
            id="nombre"
            name="nombre"
            placeholder="Ingresa el nombre"
            required
          />

          <label htmlFor="email">Email</label>
          <input
            type="email"
            id="email"
            name="email"
            placeholder="Ingresa el correo electrónico"
            required
          />

          <label htmlFor="contraseña">Contraseña:</label>
          <input
            type="password"
            id="contraseña"
            name="contraseña"
            placeholder="Ingresa su contraseña"
            required
          />

          <label htmlFor="rol">Rol</label>
          <input
            type="text"
            id="rol"
            name="rol"
            placeholder="Ingresa su rol"
            required
          />

          <button type="submit">Guardar</button>
        </form>
      </div>
    </div>
  );
}
