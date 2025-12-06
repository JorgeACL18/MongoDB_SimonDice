package com.example.MVVMSD

import androidx.compose.ui.graphics.Color

enum class Colores(val color: Color, val txt: String, val tono: Int) {
    CLASE_ROJO(color = Color.Red, txt = "", tono = android.media.ToneGenerator.TONE_DTMF_1),
    CLASE_VERDE(color = Color.Green, txt = "", tono = android.media.ToneGenerator.TONE_DTMF_2),
    CLASE_AZUL(color = Color.Blue, txt = "", tono = android.media.ToneGenerator.TONE_DTMF_3),
    CLASE_AMARILLO(color = Color.Yellow, txt = "", tono = android.media.ToneGenerator.TONE_DTMF_4),
    CLASE_START(color = Color.Black, txt = "Start", tono = android.media.ToneGenerator.TONE_PROP_BEEP2)
}

enum class Estados(val start_activo: Boolean, val boton_activo: Boolean) {
    INICIO(start_activo = true, boton_activo = false),
    GENERANDO(start_activo = false, boton_activo = false),
    MOSTRANDO_SEC(start_activo = false, boton_activo = false),
    ADIVINANDO(start_activo = false, boton_activo = true),
    JUEGO_PERDIDO(start_activo = true, boton_activo = false),
    JUEGO_GANADO(start_activo = false, boton_activo = false)
}

object Datos {
    const val PREF_NAME = "SimonDicePrefs"
    const val KEY_MAX_LEVEL = "RecordNivel"
    const val KEY_DATETIME = "RecordFecha"
}