package com.example.MVVMSD

import android.util.Log
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.delay
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun IU(miViewModel: MyViewModel) {
    val estadoActual by miViewModel.estadoActual.collectAsStateWithLifecycle()
    val nivelActual by miViewModel.nivelActual.collectAsStateWithLifecycle()
    val tiempoRestante by miViewModel._tiempoRestante.collectAsStateWithLifecycle()
    val recordNivel by miViewModel.recordNivel.collectAsStateWithLifecycle()
    val recordFecha by miViewModel.recordFecha.collectAsStateWithLifecycle()

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.SpaceEvenly
    ) {
        // Mostrar nivel y tiempo
        Text(text = "Nivel: $nivelActual", fontSize = 20.sp)
        Text(text = "Tiempo: $tiempoRestante s", fontSize = 20.sp)
        // Mostrar récords
        Text(text = "Récord: Nivel $recordNivel", fontSize = 18.sp, color = androidx.compose.ui.graphics.Color.Gray)
        Text(text = "Fecha: $recordFecha", fontSize = 18.sp, color = androidx.compose.ui.graphics.Color.Gray)

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(20.dp)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                Boton(miViewModel, Colores.CLASE_ROJO, estadoActual)
                Boton(miViewModel, Colores.CLASE_VERDE, estadoActual)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(20.dp)) {
                Boton(miViewModel, Colores.CLASE_AZUL, estadoActual)
                Boton(miViewModel, Colores.CLASE_AMARILLO, estadoActual)
            }
        }

        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
            if (estadoActual == Estados.INICIO || estadoActual == Estados.JUEGO_PERDIDO) {
                Boton_Start(miViewModel, Colores.CLASE_START, estadoActual)
            } else if (estadoActual == Estados.JUEGO_PERDIDO || estadoActual == Estados.JUEGO_GANADO) {
                Button(
                    onClick = { miViewModel.reiniciarJuego() }
                ) {
                    Text("Reiniciar")
                }
            }
            Texto_Estados(estadoActual)
        }
    }
}

@Composable
fun Texto_Estados(actual: Estados) {
    Text(
        text = "Estado: ${actual.name}",
        fontSize = 16.sp,
        modifier = Modifier.padding(10.dp)
    )
}

@Composable
fun Boton(miViewModel: MyViewModel, enum_color: Colores, estadoActual: Estados) {
    val botonIluminadoIndex by miViewModel.botonIluminado.collectAsState()

    var estaPresionadoManualmente by remember { mutableStateOf(false) }
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(estaPresionadoManualmente) {
        if (estaPresionadoManualmente) {
            delay(180)
            estaPresionadoManualmente = false
        }
    }

    val estaIluminadoPorSecuencia = botonIluminadoIndex == enum_color.ordinal

    val colorBase = enum_color.color
    val colorObjetivo by animateColorAsState(
        targetValue = when {
            estaPresionadoManualmente -> colorBase.copy(alpha = 0.55f) // Color al presionar
            estaIluminadoPorSecuencia -> colorBase.copy(alpha = 0.8f) // Color para mostrar secuencia
            else -> colorBase // Color normal
        },
        animationSpec = tween(durationMillis = 200), // Duración de la transición de color
        label = "ColorAnimadoBoton"
    )

    val escalaBase = 1.0f
    val escalaIluminada = 1.2f
    val escalaObjetivo by animateFloatAsState(
        targetValue = if (estaIluminadoPorSecuencia) escalaIluminada else escalaBase,
        animationSpec = tween(durationMillis = 300),
        label = "EscalaAnimadaBoton"
    )

    Button(
        enabled = estadoActual.boton_activo,
        colors = ButtonDefaults.buttonColors(containerColor = colorObjetivo),
        onClick = {
            estaPresionadoManualmente = true
            reproducirTono(enum_color)
            Log.d("miDebug", "Botón pulsado: ${enum_color.name}")
            miViewModel.procesarEntradaJugador(enum_color.ordinal)
        },
        modifier = Modifier
            .size(140.dp)
            .aspectRatio(1f)
            .scale(escalaObjetivo),
        shape = RoundedCornerShape(24.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
    ) {
        Text(text = enum_color.txt, fontSize = 16.sp)
    }
}

@Composable
fun Boton_Start(miViewModel: MyViewModel, enum_color: Colores, estadoActual: Estados) {
    var presionado by remember { mutableStateOf(false) }

    val colorAnimado by animateColorAsState(
        targetValue = if (presionado) enum_color.color.copy(alpha = 0.6f) else enum_color.color,
        animationSpec = tween(durationMillis = 180),
        label = ""
    )

    LaunchedEffect(presionado) {
        if (presionado) {
            delay(180)
        }
    }

    Button(
        enabled = estadoActual.start_activo,
        colors = ButtonDefaults.buttonColors(containerColor = colorAnimado),
        onClick = {
            presionado = true
            Log.d("miDebug", "Start presionado")
            miViewModel.iniciarJuego()
        },
        modifier = Modifier
            .width(160.dp)
            .height(60.dp),
        shape = RoundedCornerShape(20.dp),
        elevation = ButtonDefaults.buttonElevation(defaultElevation = 6.dp)
    ) {
        Text(text = enum_color.txt, fontSize = 18.sp, color = contentColorForBackground(enum_color.color))
    }
}

@Composable
private fun contentColorForBackground(background: androidx.compose.ui.graphics.Color) =
    if ((background.red * 0.299 + background.green * 0.587 + background.blue * 0.114) > 0.6f)
        androidx.compose.ui.graphics.Color.Black else androidx.compose.ui.graphics.Color.White

fun reproducirTono(color: Colores) {
    val toneGen = android.media.ToneGenerator(android.media.AudioManager.STREAM_MUSIC, 100)
    toneGen.startTone(color.tono, 200)
    Thread {
        Thread.sleep(250)
        toneGen.release()
    }.start()
}