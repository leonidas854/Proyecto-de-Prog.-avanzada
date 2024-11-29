// app/Clientes/page.jsx
import Image from 'next/image';
import Link from 'next/link';
import '../css/Clientes.css';

export default function Clientes() {
  return (
    <div className="clientes-page">
      <h1>SECCIÓN CLIENTES</h1>
      <div className="cards-container">
        {/* Tarjeta de Nuevo Cliente */}
        <div className="card">
          <div className="face front">
            <Image
              src="/image/cliente_nuevo.jpg"
              alt="Nuevo Cliente"
              width={300}
              height={200}
            />
            <h3>NUEVO CLIENTE</h3>
          </div>
          <div className="face back">
            <h3>NUEVO CLIENTE</h3>
            <p>Regístrate como cliente nuevo y aprovecha todos los beneficios disponibles.</p>
            <div className="link">
              <Link href="Clientes/Registro">Ir al formulario</Link>
            </div>
          </div>
        </div>

        {/* Tarjeta de Cliente Anterior */}
        <div className="card">
          <div className="face front">
            <Image
              src="/image/cliente_antiguo.jpg"
              alt="Cliente Anterior"
              width={300}
              height={200}
            />
            <h3>CLIENTE ANTERIOR</h3>
          </div>
          <div className="face back">
            <h3>CLIENTE ANTERIOR</h3>
            <p>Consulta tus datos y accede a tu cuenta como cliente registrado.</p>
            <div className="link">
              <Link href="Clientes/Login">Iniciar sesión</Link>
            </div>
          </div>
        </div>
      </div>
    </div>
  );
}
