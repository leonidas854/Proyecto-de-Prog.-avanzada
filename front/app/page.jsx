"use client";
//import 'bootstrap/dist/css/bootstrap.min.css';
import Image from "next/image";
import Link from "next/link";
import "./css/Estilo.css"; // Asegúrate de que el archivo CSS esté en la ubicación correcta
import Head from "next/head";
import { useState } from "react";


export default function HomePage() {
  const [isChecked, setIsChecked] = useState(false);

  const handleCheckboxChange = () => {
    setIsChecked(!isChecked);
  };

  return (
    <>
      <Head>
        <meta charSet="utf-8" />
        <title>SISTEMA DE DISTRIBUCIÓN DE COCA-COLA</title>
        <link rel="icon" type="text/css" href="/image/iconocc.jpg" />
        <link
          href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.13.0/css/all.min.css"
          rel="stylesheet"
        />
        <meta
          name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0"
        />
       
      </Head>

      <body>
      <section className="contenedor-imagen">
      <div className="titulo-imagen">
            <h1>Bienvenidos al </h1>
            <h1>Sistema de Distribución </h1>
            <h1>de Coca-Cola</h1>
          </div>
          <Image
            src="/image/coqui.png"
            alt="Logo Coca-Cola"
            width={1200}
            height={400}
            layout="responsive"
            className="imagen-arriba"
          />
        </section>
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
              <i className="fas fa-bars"></i>
            </label>

            {/* Enlace con logo */}
            <a href="#" className="enlace">
              <img src="/image/logo.png" alt="Logo" className="logo" />
            </a>

            {/* Menú de navegación */}
            <ul>
              <li>
                <Link href="/Clientes">Clientes</Link>
              </li>
              <li>
                <Link href="/Administrador">Administrador</Link>
              </li>
              <li>
                <Link href="/Conductor">Conductor</Link>
              </li>
            </ul>
          </nav>
          
        </header>
        <br/> <br/>

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
        

       
        <br/> <br/> <br/> <br/> <br/> <br/> <br/> <br/> <br/> <br/> <br/><br/> <br/> <br/> <br/> <br/> <br/>
        
        
      </body>
    </>
  );
}
