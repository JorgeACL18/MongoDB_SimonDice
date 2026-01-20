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

```
┌─────────────────────────────────────────┐
│           Presentación (UI)             │
│        Jetpack Compose                  │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│      ViewModel (MyViewModel)            │
│   - Lógica de la aplicación             │
│   - Gestión de estados                  │
│   - Comunicación con Repository         │
└────────────────┬────────────────────────┘
                 │
┌────────────────▼────────────────────────┐
│      Repository (DataRepository)        │
│   - Coordina fuentes de datos           │
│   - Local (SharedPreferences)           │
│   - Remota (MongoDB)                    │
└────────────────┬────────────────────────┘
                 │
        ┌────────┴────────┐
        │                 │
┌───────▼────────┐ ┌──────▼──────────┐
│ Local Storage  │ │  Network (API)  │
│ SharedPrefs    │ │  MongoDB        │
└────────────────┘ └─────────────────┘
```

---

## 🎮 Funcionalidades

### 1. **Gestión de Estados del Juego**

El juego cuenta con 6 estados principales:

| Estado | Descripción |
|--------|-------------|
| `INICIO` | Pantalla inicial, esperando que el jugador inicie |
| `GENERANDO` | Preparando el nuevo nivel |
| `MOSTRANDO_SEC` | Reproduciendo la secuencia de colores |
| `ADIVINANDO` | Esperando la entrada del jugador |
| `JUEGO_PERDIDO` | Fin del juego por error o tiempo agotado |
| `JUEGO_GANADO` | Completaron los 10 niveles |

**Diagrama de flujo de estados:**
```
┌──────────────┐
│   INICIO     │
└──────┬───────┘
       │ (Iniciar juego)
       ▼
┌──────────────┐
│  GENERANDO   │
└──────┬───────┘
       │
       ▼
┌─────────────────────┐
│  MOSTRANDO_SEC      │
└──────┬──────────────┘
       │ (Secuencia completada)
       ▼
┌──────────────┐
│  ADIVINANDO  │ ◄──┐
└──┬────────┬──┘    │ (Entrada correcta)
   │        │       │
   │        └───────┘
   │ (Timeout o error)
   ▼
┌──────────────────┐
│  JUEGO_PERDIDO   │
└──────────────────┘

Alternativa:
ADIVINANDO → (10 niveles completados) → JUEGO_GANADO
```

### 2. **Secuencia de Colores**

El flujo principal del juego sigue estos pasos:

```
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
```

Ejemplo visual:
- **Nivel 1**: [🔴]
- **Nivel 2**: [🔴, 🔵]
- **Nivel 3**: [🔴, 🔵, 🟢]
- **Nivel 4**: [🔴, 🔵, 🟢, 🟡]

### 3. **Sistema de Niveles**

- **Niveles totales**: 10
- **Mecánica**: Cada nivel añade un nuevo color a la secuencia
- **Dificultad progresiva**: El tiempo disminuye con cada nivel

**Fórmula de cálculo de tiempo:**
```kotlin
tiempoSegundos = máximo(5, 15 - (nivel * 2))
```

### 4. **Sistema de Temporizador**

- **⏱️ Dinámico**: Varía según el nivel.
- **🔄 Reactivo**: Se actualiza cada segundo en tiempo real.
- **⛔ Validación**: Si el tiempo llega a 0, el juego se pierde.

**Comportamiento:**
```kotlin
while (tiempoRestante > 0 && estadoActual == ADIVINANDO) {
    delay(1000ms)
    tiempoRestante--
}
```

**Cálculo de tiempo por nivel:**
| Nivel | Tiempo (segundos) |
|-------|-------------------|
| 1 | 13 |
| 2 | 11 |
| 3 | 9 |
| 4 | 7 |
| 5 | 5 |
| 6-10 | 5 (mínimo) |

### 5. **Validación de Entrada**

El sistema valida la entrada del jugador comparándola con la secuencia generada:

```
Secuencia: [0, 2, 1]
├─ Entrada: [0, 2, 1] ✅ Correcto → Siguiente nivel
├─ Entrada: [0, 1, 1] ❌ Incorrecto → Fin del juego
└─ Entrada: [0, 2]    ❌ Incompleto → Esperar más entrada
```

### 6. **Sistema de Récords**

El juego almacena los récords localmente usando **SharedPreferences**:

**Claves de almacenamiento:**
- `KEY_MAX_LEVEL`: Nivel más alto alcanzado
- `KEY_DATETIME`: Fecha y hora del récord

**Formato de fecha:** `dd/MM/yyyy HH:mm:ss`

### 7. **Sincronización MongoDB**

Los récords se sincronizan con la base de datos MongoDB en la nube:

```json
{
  "usuario": "JorgeACL18",
  "nivelMaximo": 8,
  "fecha": "15/03/2025 14:30:45",
  "timestamp": 1710506445000
}
```

**Características:**
- 🌐 Sincronización automática en la nube
- 🔄 Respaldo de los mejores récords
- 📊 Registro de intentos y logros
- ⚡ Operaciones asincrónicas con Coroutines

---

## 📦 Instalación

### Requisitos Previos

- **Android Studio** 2022.1+
- **Kotlin** 1.9+
- **Gradle** 8.0+
- **JDK** 11+
- **API Level** 24+ (Android 7.0 Nougat)

### Pasos de Instalación

1. **Clonar el repositorio:**
   ```bash
   git clone https://github.com/JorgeACL18/MongoDB_SimonDice.git
   cd MongoDB_SimonDice
   ```

2. **Abrir en Android Studio:**
   - Selecciona `File → Open`
   - Navega a la carpeta del proyecto
   - Haz clic en `Open`

3. **Sincronizar Gradle:**
   - Android Studio sincronizará automáticamente las dependencias
   - Si no ocurre, ejecuta: `Build → Make Project`

4. **Configurar MongoDB (Opcional):**
   - Accede a `app/src/main/java/com/example/MVVMSD/network/MongoDBApi.kt`
   - Configura tu URL de conexión a MongoDB

5. **Ejecutar la aplicación:**
   - Selecciona un emulador o dispositivo físico
   - Haz clic en `Run → Run 'app'`

---

## 🎮 Uso

### Instrucciones de Juego

1. **Iniciar Juego:** Toca el botón "Iniciar Juego"
2. **Ver Secuencia:** El juego mostrará una secuencia de colores
3. **Repetir Secuencia:** Toca los botones en el mismo orden
4. **Avanzar de Nivel:** Si aciertas, el nivel aumenta en 1
5. **Terminar Partida:** Si fallas o se agota el tiempo, el juego termina

### Controles

| Control | Acción |
|---------|--------|
| 🔴 Botón Rojo | Reproduce color rojo y registra entrada |
| 🔵 Botón Azul | Reproduce color azul y registra entrada |
| 🟢 Botón Verde | Reproduce color verde y registra entrada |
| 🟡 Botón Amarillo | Reproduce color amarillo y registra entrada |
| 🎮 Iniciar/Reiniciar | Comienza una nueva partida |

---

## 📁 Estructura del Proyecto

```
app/src/main/java/com/example/MVVMSD/
├── MainActivity.kt              # Punto de entrada principal
├── MyViewModel.kt              # ViewModel con lógica del juego
├── Datos.kt                    # Clases de datos/modelos
├── IU.kt                       # Composables de interfaz (UI)
├── DAO/
│   ├── BBDD.kt                # Base de datos local
│   ├── DAO.kt                 # Acceso a datos
│   └── Entidad.kt             # Entidades de BD
├── network/
│   └── MongoDBApi.kt          # Integración con MongoDB
├── repository/
│   └── DataRepository.kt       # Repositorio (patrón MVVM)
├── ui/
│   └── theme/
│       ├── Color.kt           # Paleta de colores
│       ├── Theme.kt           # Tema de la aplicación
│       └── Type.kt            # Tipografía
└── backend/
    └── Node.js                # (Opcional) Backend servidor
```

---

## 🛠️ Desarrollo

### Stack Tecnológico Utilizado

- **Lenguaje:** Kotlin
- **Framework UI:** Jetpack Compose
- **Arquitectura:** MVVM + Repository Pattern
- **Concurrencia:** Kotlin Coroutines
- **BD Local:** SharedPreferences
- **BD Remota:** MongoDB Atlas
- **API REST:** (Integración personalizada)

### Próximas Mejoras

- [ ] Multiplayer en línea
- [ ] Leaderboard global
- [ ] Temas personalizables
- [ ] Modo de dificultad personalizado
- [ ] Integración con redes sociales

---

## 📄 Licencia

Este proyecto está bajo la licencia MIT. Consulta el archivo `LICENSE` para más detalles.

---

## 👨‍💻 Autor

**JorgeACL18**
- GitHub: [@JorgeACL18](https://github.com/JorgeACL18)
- Email: jcanozolorenzo@danielcastelao.org

---

## 📞 Soporte

Si encuentras problemas o tienes sugerencias:
1. Abre un [Issue](https://github.com/JorgeACL18/MongoDB_SimonDice/issues)
2. Envía un correo al autor
3. Contribuye con un Pull Request

---

## 🙏 Agradecimientos

- Comunidad de Kotlin
- Jetpack Compose Team
- MongoDB Community
- Android Developers






