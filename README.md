# 🎮 Simon Says - MVVM Memory Game

Un juego de memoria interactivo desarrollado en **Kotlin** con arquitectura **MVVM**, integración con **MongoDB** y persistencia local con **SharedPreferences**.

---

## 📋 Tabla de Contenidos

- [Características](#características)
- [Tecnologías](#tecnologías)
- [Arquitectura](#arquitectura)
- [Funcionalidades](#funcionalidades)
- [Instalación](#instalación)
- [Uso](#uso)
- [Estructura del Proyecto](#estructura-del-proyecto)

---

## ✨ Características

✅ **Gameplay Dinámico**: Sigue una secuencia de colores que aumenta en dificultad  
✅ **Sistema de Niveles**: 10 niveles progresivos con tiempo decreciente  
✅ **Récord Persistente**: Guarda tu mejor puntuación con fecha y hora  
✅ **Sincronización en la Nube**: Integración con MongoDB para respaldo de datos  
✅ **Efectos de Sonido**: Tonos únicos para cada botón de color  
✅ **Temporizador Dinámico**: Tiempo decreciente según el nivel  
✅ **Interfaz Reactiva**: UI actualizada en tiempo real con Compose

---

## 🛠️ Tecnologías

| Tecnología | Versión | Propósito |
|---|---|---|
| **Kotlin** | 1.9+ | Lenguaje principal |
| **Jetpack Compose** | Latest | Interfaz de usuario |
| **MVVM** | Architecture | Patrón de diseño |
| **Coroutines** | Latest | Operaciones asincrónicas |
| **MongoDB** | Cloud | Base de datos remota |
| **SharedPreferences** | Built-in | Almacenamiento local |
| **Gradle** | 8.0+ | Build system |

---

## 🏗️ Arquitectura

El proyecto sigue el patrón **MVVM (Model-View-ViewModel)**:

---

## 🎮 Funcionalidades

### 1. **Gestión de Estados del Juego**

El juego cuenta con 5 estados principales:

| Estado | Descripción |
|--------|-------------|
| `INICIO` | Pantalla inicial, esperando que el jugador inicie |
| `GENERANDO` | Preparando el nuevo nivel |
| `MOSTRANDO_SEC` | Reproduciendo la secuencia de colores |
| `ADIVINANDO` | Esperando la entrada del jugador |
| `JUEGO_PERDIDO` | Fin del juego por error o tiempo agotado |
| `JUEGO_GANADO` | Completaron los 10 niveles |

**Flujo de Estados:**

### 2. **Sistema de Niveles**

- **Niveles totales**: 10
- **Mecánica**: Cada nivel añade un nuevo color a la secuencia
- **Dificultad progresiva**: El tiempo disminuye con cada nivel

**Fórmula de cálculo de tiempo:**
```kotlin
tiempoSegundos = máximo(5, 15 - (nivel * 2))
```

### 3. **Secuencia de Colores**
┌─────────────────────────────────────────┐
│  Iniciar Juego                          │
├─────────────────────────────────────────┤
│  1️⃣  Generar número aleatorio (0-3)    │
│  2️⃣  Añadirlo a la secuencia           │
│  3️⃣  Reproducir secuencia completa     │
│  4️⃣  Esperar entrada del jugador       │
│  5️⃣  Validar secuencia                 │
│  6️⃣  Siguiente nivel o fin de juego    │
└─────────────────────────────────────────┘

### 4. **Sistema de Temporizador**

- **⏱️ Dinámico**: Varía según el nivel.
- **🔄 Reactivo**: Se actualiza cada segundo en tiempo real.
- **⛔ Validación**: Si el tiempo llega a 0, el juego se pierde.

**Comportamiento**
```kotlin
while (tiempoRestante > 0 && estadoActual == ADIVINANDO) {
    delay(1000ms)
    tiempoRestante--
}
```
### 5. **Validación de entrada**

- Secuencia:  [0, 2, 1]
- Entrada:    [0, 2, 1] ✅ Correcto → Siguiente nivel
- Entrada:    [0, 1, 1] ❌ Incorrecto → Fin del juego

### 6. **Sistema de Récords**

- KEY_MAX_LEVEL:  Nivel más alto alcanzado
- KEY_DATETIME:   Fecha y hora del récord

### 7. **Sincronización MongoDB**
```json
{
"usuario": "JorgeACL18",
"nivelMaximo": 8,
"fecha": "15/03/2025 14:30:45",
"timestamp": 1710506445000
}
```






