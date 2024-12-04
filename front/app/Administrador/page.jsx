'use client'; // Habilitar eventos en el cliente
import Image from 'next/image'; // Importa el componente Image de Next.js
import Link from "next/link"; // Para navegación entre páginas
import { useState } from 'react'; // Hook para manejar estados
import "../css/Estilo_admin.css"; // Importa los estilos CSS

export default function Administrador() {
  const [isChecked, setIsChecked] = useState(false); // Estado del checkbox del menú

  // Función para manejar el cambio de estado del checkbox
  const handleCheckboxChange = () => {
    setIsChecked(!isChecked);
  };

  return (
    <div className="admin-page">
      {/* Encabezado con el menú */}
      <header>
        <nav>
          {/* Menú con checkbox para dispositivos móviles */}
          <input
            type="checkbox"
            id="check"
            checked={isChecked}
            onChange={handleCheckboxChange}
          />
          <label htmlFor="check" className="checkbtn">
            <i className="fas fa-bars"></i> {/* Ícono del menú */}
          </label>

          {/* Enlace con logo */}
          <a href="#" className="enlace">
            <img src="/image/logo.png" alt="Logo" className="logo" />
          </a>

          {/* Menú de navegación */}
          <ul>
            <li>
              <Link href="/Administrador/Registro_User">Parámetros de Sensibilidad</Link>
            </li>
            <li>
              <Link href="/Administrador/Registro_Vehiculos">Registro Vehículos</Link>
            </li>
            <li>
              <Link href="/Administrador/Ruta">Formular Ruta</Link>
            </li>
            <li>
              <Link href="/Administrador/Seguim_Ruta">Seguir Ruta</Link>
            </li>
            <li>
              <Link href="/Administrador/Reporte">Predicciones</Link>
            </li>
            <li>
              <Link href="/Administrador/Simulaciones">Simulaciones</Link>
            </li>
            <li>
              <Link href="/Administrador/Ventana_de_Tiempo">Ventana de Tiempo</Link>
            </li>
          </ul>
        </nav>
      </header>

      {/* Contenido principal */}
      <main>
        <div className="titulo">
          <h1>Bienvenido Administrador</h1>
        </div>
      </main>

      {/* Espaciado */}
      <br /><br /><br /><br /><br /><br /><br />

      {/* Imagen de fondo */}
      <section className="contenedor-imagen">
        <Image
          src="/image/f2.png"
          alt="fondo Coca-Cola"
          width={2000}
          height={500}
          className="imagen-abajo"
        />
      </section>
    </div>
  );
}
