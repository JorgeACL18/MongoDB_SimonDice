# Scripts de Utilidad

Este directorio contiene scripts útiles para el desarrollo del proyecto RoomApp.

## Scripts Disponibles

### 1. `find-last-modified-files.sh`

Script que busca los últimos ficheros modificados en el repositorio Git con información detallada.

#### Características:
- Muestra los últimos ficheros modificados
- Incluye fecha y hora de última modificación
- Muestra el hash del commit
- Muestra el autor del cambio
- Utiliza colores para mejor legibilidad
- Validación de repositorio Git

#### Uso:

```bash
# Mostrar los 3 últimos ficheros modificados (por defecto)
./.github/scripts/find-last-modified-files.sh

# Mostrar los 5 últimos ficheros modificados
./.github/scripts/find-last-modified-files.sh 5

# Mostrar los 10 últimos ficheros modificados
./.github/scripts/find-last-modified-files.sh 10
```

#### Ejemplo de Salida:

```
╔════════════════════════════════════════════════════════════╗
║  Últimos 3 ficheros modificados en el repositorio
╚════════════════════════════════════════════════════════════╝

Ficheiro: .github/git-commit-instructions.md
├─ Última modificación: 2026-01-09 08:20:31 +0100
├─ Commit: b556321
└─ Autor: Damian

Ficheiro: .github/copilot-instructions.md
├─ Última modificación: 2025-12-16 13:06:01 +0100
├─ Commit: 2b3292f
└─ Autor: damian

Ficheiro: app/src/main/java/gz/dam/roomapp/AppDatabase.kt
├─ Última modificación: 2025-12-16 13:06:01 +0100
├─ Commit: 2b3292f
└─ Autor: damian

═══════════════════════════════════════════════════════════
✓ Script completado exitosamente
```

---

### 2. `find-last-modified-files-simple.sh`

Script más simple que muestra únicamente los ficheros sin información adicional.

#### Características:
- Salida mínima y directa
- Perfecto para uso en tuberías o scripts automatizados
- Requiere menos procesamiento

#### Uso:

```bash
# Mostrar los 3 últimos ficheros modificados
./.github/scripts/find-last-modified-files-simple.sh

# Mostrar los 7 últimos ficheros modificados
./.github/scripts/find-last-modified-files-simple.sh 7
```

#### Ejemplo de Salida:

```
Los 3 últimos ficheros modificados en el repositorio:
==============================================================

.github/git-commit-instructions.md
.github/copilot-instructions.md
app/src/main/java/gz/dam/roomapp/AppDatabase.kt

==============================================================
```

---

## Requisitos

- **Bash 4.0** o superior
- **Git** instalado y disponible en la ruta del sistema
- El repositorio debe ser un repositorio Git válido

## Cómo Ejecutar los Scripts

### Opción 1: Desde la raíz del proyecto

```bash
cd /ruta/del/proyecto
./.github/scripts/find-last-modified-files.sh
```

### Opción 2: Desde cualquier ubicación

```bash
/Users/mini/Damian/castelao/PMDM/.github/scripts/find-last-modified-files.sh
```

### Opción 3: Alias (recomendado para uso frecuente)

Añade esto a tu fichero `~/.zshrc` o `~/.bash_profile`:

```bash
alias last-files="cd /Users/mini/Damian/castelao/PMDM && ./.github/scripts/find-last-modified-files.sh"
alias last-files-simple="cd /Users/mini/Damian/castelao/PMDM && ./.github/scripts/find-last-modified-files-simple.sh"
```

Luego, recarga la configuración:

```bash
source ~/.zshrc
# o
source ~/.bash_profile
```

Ahora puedes ejecutar simplemente:

```bash
last-files
last-files-simple
```

---

## Notas Técnicas

### Cómo Funcionan los Scripts

#### `find-last-modified-files.sh`

1. Verifica que existe un repositorio Git válido
2. Obtiene el histórico de commits sin saltos de línea
3. Filtra ficheros duplicados manteniendo el orden
4. Selecciona los N primeros ficheros
5. Para cada fichero, obtiene:
   - Fecha/hora de última modificación
   - Hash del commit
   - Autor del cambio
6. Formatea y muestra la información con colores

#### `find-last-modified-files-simple.sh`

1. Verifica que existe un repositorio Git válido
2. Obtiene el histórico de commits
3. Filtra ficheros duplicados
4. Muestra únicamente los N primeros ficheros

---

## Solución de Problemas

### Error: "No es un repositorio Git válido"

Verifica que estás en el directorio correcto:

```bash
cd /Users/mini/Damian/castelao/PMDM
git status
```

### Error: "Comando no encontrado"

Asegúrate de que el script tiene permisos de ejecución:

```bash
chmod +x ./.github/scripts/find-last-modified-files.sh
```

### Los colores no aparecen correctamente

Algunos terminales pueden no soportar códigos de color ANSI. Considera usar:

```bash
# Sin colores
./.github/scripts/find-last-modified-files-simple.sh
```

---

## Autor

Damián Nogueiras

## Licencia

Proyecto educativo

