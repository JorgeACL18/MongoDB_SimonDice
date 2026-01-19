const express = require('express');
const mongoose = require('mongoose');
const app = express();

app.use(express.json());

// Conectar a MongoDB
mongoose.connect('mongodb://192.168.1.133.:27017/miBaseDatos');

// Esquema
const jugadorSchema = new mongoose.Schema({
  id: { type: Number },
  nombre: { type: String },
  max: { type: Number },
  fecha: { type: Date, default: Date.now }
});

const Jugador = mongoose.model('Jugador', jugadorSchema);

// Endpoint
app.post('/api/save-data', async (req, res) => {
  try {
    const jugador = new Jugador(req.body);
    await jugador.save();
    res.json({ success: true, message: 'Datos guardados' });
  } catch (error) {
    res.status(500).json({ error: error.message });
  }
});

app.listen(3000, () => console.log('Servidor en puerto 3000'));
