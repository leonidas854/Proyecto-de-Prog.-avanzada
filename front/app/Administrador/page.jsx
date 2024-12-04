'use client'; // Habilitar eventos en el cliente
import Image from 'next/image'; // Importa el componente Image de Next.js
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
            {[
              { path: 'Administrador/Registro_User', label: 'Parámetros de Sensibilidad' },
              { path: 'Administrador/Registro_Vehiculos', label: 'Registro Vehículos' },
              { path: 'Administrador/Ruta', label: 'Formular Ruta' },
              { path: 'Administrador/Seguim_Ruta', label: 'Seguir Ruta' },
              { path: 'Administrador/Reporte', label: 'Predicciones' },
              { path: 'Administrador/Simulaciones', label: 'Simulaciones' },
            ].map((item, index) => (
              <li key={index} className="nav">
                <button
                  onClick={() => navigateTo(item.path)}
                  className="link-button"
                  aria-label={item.label}
                >
                  {item.label}
                </button>
              </li>
            ))}
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
          {[...Array(10)].map((_, index) => (
            <figure key={index}>
              <img src="/image/iconocc.jpg" alt={`Imagen ${index + 1}`} />
            </figure>
          ))}
        </div>
      </div>
    </div>
  );
}
