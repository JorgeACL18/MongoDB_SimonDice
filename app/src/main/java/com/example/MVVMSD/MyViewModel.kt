package com.example.MVVMSD

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.compose.runtime.mutableStateListOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import kotlin.random.Random

class MyViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(MyViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return MyViewModel(context.getSharedPreferences(Datos.PREF_NAME, Context.MODE_PRIVATE)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}

class MyViewModel(private val sharedPreferences: SharedPreferences) : ViewModel() {

    private val TAG_LOG = "miDebug"

    private val _estadoActual = MutableStateFlow(Estados.INICIO)
    val estadoActual: StateFlow<Estados> = _estadoActual

    private val _secuencia = mutableStateListOf<Int>()
    private val _nivelActual = MutableStateFlow(1)
    val nivelActual: StateFlow<Int> = _nivelActual

    private val _recordNivel = MutableStateFlow(sharedPreferences.getInt(Datos.KEY_MAX_LEVEL, 0))
    val recordNivel: StateFlow<Int> = _recordNivel

    private val _recordFecha = MutableStateFlow(sharedPreferences.getString(Datos.KEY_DATETIME, "") ?: "")
    val recordFecha: StateFlow<String> = _recordFecha

    private val _indiceJugador = MutableStateFlow(0)
     val _tiempoRestante = MutableStateFlow(0)
    private var _tiempoTotalPartida = 0

    private val _botonIluminado = MutableStateFlow<Int?>(null)
    val botonIluminado: StateFlow<Int?> = _botonIluminado

    private var _temporizadorActivo = false
    private var _nivelMaximo = 10

    init {
        Log.d(TAG_LOG, "ViewModel iniciado - Record: ${_recordNivel.value} en ${_recordFecha.value}")
    }

    fun iniciarJuego() {
        Log.d(TAG_LOG, "Iniciando juego... Estado antes: ${_estadoActual.value.name}")
        _estadoActual.value = Estados.GENERANDO
        _nivelActual.value = 1
        _secuencia.clear()
        _indiceJugador.value = 0
        _tiempoRestante.value = calcularTiempoPorNivel(1)
        _estadoActual.value = Estados.MOSTRANDO_SEC // Cambia a MOSTRANDO_SEC
        Log.d(TAG_LOG, "Iniciando juego... Estado después de cambiar a MOSTRANDO_SEC: ${_estadoActual.value.name}")
        iniciarNivel()
    }

    private fun iniciarNivel() {
        Log.d(TAG_LOG, "Iniciando nivel ${_nivelActual.value}")
        _secuencia.add(Random.nextInt(4))
        Log.d(TAG_LOG, "Secuencia actual: ${_secuencia.joinToString(", ")}")
        _indiceJugador.value = 0
        _tiempoRestante.value = calcularTiempoPorNivel(_nivelActual.value)
        reproducirSecuencia()
    }

    private fun reproducirSecuencia() {
        viewModelScope.launch {
            Log.d(TAG_LOG, "Reproduciendo secuencia: ${_secuencia.joinToString(", ")}")
            for (i in _secuencia.indices) {
                val colorIndex = _secuencia[i]
                Log.d(TAG_LOG, "Iluminando botón en índice: $colorIndex, Estado actual: ${_estadoActual.value.name}")
                _botonIluminado.value = colorIndex
                reproducirTono(Colores.values()[colorIndex])
                delay(800L)
                _botonIluminado.value = null
                delay(200L)
            }
            Log.d(TAG_LOG, "Fin de la reproducción de la secuencia.")
            _estadoActual.value = Estados.ADIVINANDO
            Log.d(TAG_LOG, "Estado cambiado a ADIVINANDO: ${_estadoActual.value.name}")
            iniciarTemporizador()
        }
    }

    private fun reproducirTono(color: Colores) {
        val toneGen = android.media.ToneGenerator(android.media.AudioManager.STREAM_MUSIC, 100)
        toneGen.startTone(color.tono, 200)
        Thread {
            Thread.sleep(250)
            toneGen.release()
        }.start()
    }

    private fun iniciarTemporizador() {
        if (_temporizadorActivo) return
        _temporizadorActivo = true
        viewModelScope.launch {
            Log.d(TAG_LOG, "iniciarTemporizador() - Iniciando temporizador con ${_tiempoRestante.value}s, Estado: ${_estadoActual.value.name}")
            while (_tiempoRestante.value > 0 && _estadoActual.value == Estados.ADIVINANDO) {
                delay(1000L)
                _tiempoRestante.value--
                Log.d(TAG_LOG, "Temporizador: ${_tiempoRestante.value}s restantes, Estado: ${_estadoActual.value.name}")
            }
            if (_tiempoRestante.value <= 0 && _estadoActual.value == Estados.ADIVINANDO) {
                Log.d(TAG_LOG, "Tiempo agotado!")
                perderJuego()
            }
            _temporizadorActivo = false
        }
    }

    fun procesarEntradaJugador(colorIndex: Int) {
        Log.d(TAG_LOG, "procesarEntradaJugador() - Estado actual: ${_estadoActual.value.name}, Color pulsado: $colorIndex")
        if (_estadoActual.value != Estados.ADIVINANDO) {
            Log.d(TAG_LOG, "Entrada ignorada, estado no es ADIVINANDO")
            return
        }

        if (colorIndex == _secuencia[_indiceJugador.value]) {
            Log.d(TAG_LOG, "Correcto! Botón ${colorIndex}")
            _indiceJugador.value++
            if (_indiceJugador.value == _secuencia.size) {
                Log.d(TAG_LOG, "Nivel completado!")
                if (_nivelActual.value >= _nivelMaximo) {
                    ganarJuego()
                } else {
                    _nivelActual.value++
                    _estadoActual.value = Estados.MOSTRANDO_SEC
                    Log.d(TAG_LOG, "procesarEntradaJugador() - Estado cambiado a: ${_estadoActual.value.name} para nuevo nivel")
                    iniciarNivel()
                }
            }
        } else {
            Log.d(TAG_LOG, "Incorrecto! Se esperaba ${_secuencia[_indiceJugador.value]}, se recibió $colorIndex")
            perderJuego()
        }
    }

    private fun perderJuego() {
        val nivelActual = _nivelActual.value
        val recordActual = _recordNivel.value
        Log.d(TAG_LOG, "Intento fallido en nivel: $nivelActual. Récord anterior: $recordActual")

        if (nivelActual > recordActual) {
            Log.d(TAG_LOG, "¡Nuevo récord! Nivel: $nivelActual")
            val currentTime = SimpleDateFormat("dd/MM/yyyy HH:mm:ss", Locale.getDefault()).format(Date())
            sharedPreferences.edit()
                .putInt(Datos.KEY_MAX_LEVEL, nivelActual)
                .putString(Datos.KEY_DATETIME, currentTime)
                .apply()

            _recordNivel.value = nivelActual
            _recordFecha.value = currentTime
        }

        _estadoActual.value = Estados.JUEGO_PERDIDO
        Log.d(TAG_LOG, "Juego perdido! Estado: ${_estadoActual.value.name}")
    }

    private fun ganarJuego() {
        _estadoActual.value = Estados.JUEGO_GANADO
        Log.d(TAG_LOG, "Juego ganado!")
    }

    fun reiniciarJuego() {
        Log.d(TAG_LOG, "reiniciarJuego() - Estado actual: ${_estadoActual.value.name}")
        _estadoActual.value = Estados.INICIO
        Log.d(TAG_LOG, "reiniciarJuego() - Estado cambiado a: ${_estadoActual.value.name}")
        _nivelActual.value = 1
        _secuencia.clear()
        _indiceJugador.value = 0
        _tiempoRestante.value = 0
        _botonIluminado.value = null
    }

    private fun calcularTiempoPorNivel(nivel: Int): Int {
        return maxOf(5, 15 - (nivel * 2))
    }
}