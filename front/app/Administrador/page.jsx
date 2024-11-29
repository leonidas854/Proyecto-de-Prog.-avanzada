'use client'; // Habilitar eventos en el cliente
import '../css/Estilo_admin.css'; // Importa los estilos
import { useRouter } from 'next/navigation'; // Hook para navegación

export default function Administrador() {
  const router = useRouter();

  const navigateTo = (path) => {
    router.push(path); // Redirige a la ruta correspondiente
  };

  return (
    <div className="admin-page">
      <header>
        <nav className="navbar">
          <ul>
            <li className="nav">
              <button onClick={() => navigateTo('Administrador/Registro_User')} className="link-button">
                REGISTRO USUARIO
              </button>
            </li>
            <li className="nav">
              <button onClick={() => navigateTo('Administrador/Registro_Vehiculos')} className="link-button">
                REGISTRO VEHÍCULOS
              </button>
            </li>
            <li className="nav">
              <button onClick={() => navigateTo('Administrador/Ruta')} className="link-button">
                FORMULAR RUTA
              </button>
            </li>
            <li className="nav">
              <button onClick={() => navigateTo('Administrador/Seguim_Ruta')} className="link-button">
                SEGUIR RUTA
              </button>
            </li>
            <li className="nav">
              <button onClick={() => navigateTo('Administrador/Reporte')} className="link-button">
                GENERAR REPORTE
              </button>
            </li>
          </ul>
        </nav>
      </header>

      <main>
        <section className="contenido">
          <h1>Bienvenido Administrador al Sistema de Distribución de Coca-Cola</h1>
        </section>
      </main>
    </div>
  );
}
