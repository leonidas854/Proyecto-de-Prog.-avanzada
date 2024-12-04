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
                Parametros de sensibilidad
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
                PREDICCIONES
              </button>
            </li>
            <li className="nav">
              <button onClick={() => navigateTo('Administrador/Simulaciones')} className="link-button">
                Simulaciones
              </button>
            </li>
          </ul>
        </nav>
      </header>

      <section className="contenedor-imagen">
      
          <Image
            src="/image/f2.png"
            alt="Logo Coca-Cola"
            width={1200}
            height={400}
            layout="responsive"
            className="imagen-arriba"
          />
        </section>

      <main>
        <section className="contenido">
          <h1>Bienvenido Administrador al Sistema de Distribución de Coca-Cola</h1>
        </section>
      </main>

      <div className="content-all">
          <div className="content-carrousel">
            <figure><img src="/image/iconocc.jpg" alt="Imagen 1" /></figure>
            <figure><img src="/image/iconocc.jpg" alt="Imagen 2" /></figure>
            <figure><img src="/image/iconocc.jpg" alt="Imagen 3" /></figure>
            <figure><img src="/image/iconocc.jpg" alt="Imagen 4" /></figure>
            <figure><img src="/image/iconocc.jpg" alt="Imagen 5" /></figure>
            <figure><img src="/image/iconocc.jpg" alt="Imagen 6" /></figure>
            <figure><img src="/image/iconocc.jpg" alt="Imagen 7" /></figure>
            <figure><img src="/image/iconocc.jpg" alt="Imagen 8" /></figure>
            <figure><img src="/image/iconocc.jpg" alt="Imagen 9" /></figure>
            <figure><img src="/image/iconocc.jpg" alt="Imagen 10" /></figure>
          </div>
        </div>

    </div>
  );
}
