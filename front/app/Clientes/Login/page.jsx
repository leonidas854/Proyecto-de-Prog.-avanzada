'use client'; // Habilitar eventos en el cliente
import './LoginEstilo.css';
import { useRouter } from 'next/navigation'; // Importar el hook de navegación

export default function LoginCliente() {
  const router = useRouter(); // Hook para manejar navegación

  const handleSubmit = (e) => {
    e.preventDefault(); // Previene el comportamiento por defecto del formulario

    const email = e.target.email.value; // Obtener el valor del email
    const password = e.target.password.value; // Obtener el valor de la contraseña

    // Simulación de validación
    if (email && password) {
      alert('Inicio de sesión exitoso. Redirigiendo a la página de pedidos...');
      router.push('/PedidoCliente'); // Redirige a la página de pedidos
    } else {
      alert('Por favor, complete todos los campos.');
    }
  };

  const handleHelpClick = () => {
    alert('Redirigiendo al centro de ayuda...');
  };

  return (
    <div className="login-page">
      <div className="login-container">
        <div className="login-box">
          <img
            src="/image/login.png" // Ruta al ícono
            alt="Login Icon"
            className="login-icon"
          />
          <h2>¡BIENVENIDO!</h2>
          <p>Inicie sesión para continuar</p>
          <form onSubmit={handleSubmit}>
            <label htmlFor="email">Correo o teléfono celular</label>
            <input
              type="text"
              id="email"
              name="email"
              placeholder="Ingrese su correo o teléfono"
              required
            />

            <label htmlFor="password">Contraseña</label>
            <input
              type="password"
              id="password"
              name="password"
              placeholder="Ingrese su contraseña"
              required
            />

            <button type="submit" className="login-button">
              Ingresar
            </button>
            <button
              type="button"
              className="help-button"
              onClick={handleHelpClick}
            >
              Centro de ayuda
            </button>
          </form>
        </div>
      </div>
    </div>
  );
}
