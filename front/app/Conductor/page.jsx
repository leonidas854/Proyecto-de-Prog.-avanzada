// app/Conductor/page.jsx
import '../css/EstiloConductor.css';

export default function Conductor() {
  return (
    <div className="conductor-form">
      <div className="cuadros-superiores">
        {/* Formulario de Itinerario */}
        <div className="form-cuadro">
          <h3>ITINERARIO</h3>
          <label htmlFor="cliente">Cliente</label>
          <input type="text" id="cliente" name="cliente" />

          <label htmlFor="fecha">Fecha</label>
          <input type="date" id="fecha" name="fecha" />

          <label htmlFor="destino">Destino del cliente</label>
          <input type="text" id="destino" name="destino" />

          <label htmlFor="horaLlegada">Hora de llegada estimada</label>
          <input type="time" id="horaLlegada" name="horaLlegada" />

          <label htmlFor="numeroPedido">N° de pedido</label>
          <input type="text" id="numeroPedido" name="numeroPedido" />
         

          <label htmlFor="origen">Origen</label>
          <input type="text" id="origen" name="origen" />

          <label htmlFor="orden">Orden</label>
          <input type="text" id="orden" name="orden" />

          <label htmlFor="horaSalida">Hora de salida estimada</label>
          <input type="time" id="horaSalida" name="horaSalida" />

          <button type="submit">Ver ruta</button>
        </div>

        {/* Segundo cuadro para captura de ruta */}
        <div className="form-cuadro">
          <h3>CAPTURA DE LA RUTA</h3>
          {/* Puedes agregar aquí contenido adicional */}
        </div>
      </div>

      {/* Tabla de vehículos registrados */}
      <div className="datagrid-container">
        <h3>Vehículos Registrados</h3>
        <table id="dataGridView">
          <thead>
            <tr>
              <th>Placa</th>
              <th>Dispositivo</th>
              <th>Capacidad</th>
              <th>Tipo</th>
            </tr>
          </thead>
          <tbody>
            {/* Los datos se agregarán dinámicamente */}
          </tbody>
        </table>
      </div>
    </div>
  );
}
