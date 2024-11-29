// app/page.jsx
import Image from 'next/image';
import Link from 'next/link';
import './css/Estilo.css'; // Asegúrate de que el archivo CSS esté en la ubicación correcta
import Head from 'next/head';

export default function HomePage() {
  return (
    <>
      <Head>
        <meta charSet="utf-8" />
        <title>SISTEMA DE DISTRIBUCIÓN DE COCA - COLA</title>
        <meta
          name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"
        />
        <link rel="icon" href="/image/iconocc.jpg" />
      </Head>
      <body>
        <header>
          <div className="contenedor-imagen">
            <nav className="navbar">
              <ul>
                <li className="nav">
                  <Link href="/Clientes">Clientes</Link>
                </li>
                <li className="nav">
                  <Link href="/Administrador">Administrador</Link>
                </li>
                <li className="nav">
                  <Link href="/Conductor">Conductor</Link>
                </li>
              </ul>
            </nav>
          </div>
        </header>
        <main>
          <section className="contenido">
            <h1>Bienvenidos al Sistema de Distribución de Coca-Cola</h1>
            <div className="texto-imagen">
              <Image
                src="/image/iconocc.jpg"
                alt="Coca-Cola"
                width={200}
                height={200}
                className="imagen"
              />
              <p>
                El Sistema de Distribución de Coca-Cola garantiza que nuestros
                productos lleguen a todos los rincones del mundo con calidad y
                frescura. Nos enorgullece ofrecer un servicio eficiente y
                confiable.
              </p>
            </div>
            <div className="texto-imagen">
              <Image
                src="/image/camionsin.png"
                alt="Camión de Coca-Cola"
                width={300}
                height={200}
                className="imagen"
              />
              <p>
                Nuestros camiones están diseñados para mantener las bebidas en
                perfectas condiciones durante su transporte, asegurando una
                experiencia única para nuestros clientes.
              </p>
            </div>
          </section>
        </main>
      </body>
    </>
  );
}
