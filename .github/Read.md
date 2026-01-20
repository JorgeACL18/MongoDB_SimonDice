![RoomApp Logo](../assets/firma.png)

# Configuración de GitHub Copilot para RoomApp

[![Galego](https://img.shields.io/badge/Language-Galego-green?style=flat-square)](README.md)
[![GitHub](https://img.shields.io/badge/GitHub-Copilot-black?style=flat-square&logo=github)](https://github.com/features/copilot)
[![Status](https://img.shields.io/badge/Status-Active-brightgreen?style=flat-square)](https://github.com/damiancastelao/RoomApp)
[![License](https://img.shields.io/badge/License-MIT-green?style=flat-square)](../LICENSE)

Este directorio contén a configuración e instruccións para traballar con **GitHub Copilot** no proxecto RoomApp. Inclúe instruccións personalizadas, prompts, templates e scripts de utilidade que melloran a experiencia de desenvolvemento con Copilot.

---

## 📋 Contido do Directorio

### Arquivos de Configuración

```
.github/
├── README.md                          # Este arquivo - Guía de configuración
├── copilot-instructions.md            # Instruccións xerais do repositorio
├── Readme-copilot.md                  # Información sobre personalización de MCP
├── pr-template.md                     # Template para pull requests (en galego)
│
├── instructions/                      # Instruccións especializadas
│   ├── git-commit-instructions.md     # Guía para mensaxes de commit
│   ├── add-comment.instructions.md    # Instruccións para comentarios educacionais
│   └── shell.instrucctions.md         # Guía para scripts de shell
│
├── prompts/                           # Prompts para xeración de código
│   ├── crear-readme.prompt.md         # Prompt para crear README
│   ├── crear-pr.prompt.md             # Prompt para crear Pull Requests
│   └── create-implemention-plan.prompt.md # Prompt para plans de implementación
│
└── scripts/                           # Scripts de utilidade
    ├── README.md                      # Documentación dos scripts
    ├── find-last-modified-files.sh    # Busca arquivos modificados recentemente
    └── find-last-modified-files-simple.sh # Versión simplificada
```

---

## 🚀 Comezar Rápidamente

### Configuración de MCP (Model Context Protocol)

Para personalizar a configuración de GitHub Copilot coa API de GitHub, accede ao arquivo `mcp.json` a través do icono de herramientas ao lado de "*Agent*":

```json
{
  "servers": {
    "github": {
      "url": "https://api.githubcopilot.com/mcp/",
      "requestInit": {
        "headers": {
          "Authorization": "Bearer github_pat_....fV16zII8U6ZV...."
        }
      }
    }
  }
}
```

> **Nota:** Reemplaza o token coa túa credencial persoal de GitHub.

### Pasos de Configuración

1. **Instala GitHub Copilot** na túa IDE (VS Code, JetBrains, etc.)
2. **Autentica** coa túa conta de GitHub
3. **Revisa** as instruccións en `copilot-instructions.md`
4. **Configura MCP** segundo as necesidades do proxecto
5. **Utiliza os prompts** dispoñibles no directorio `prompts/`

---

## 📖 Instruccións Principais

### 1. Instruccións Xerais do Repositorio
**Arquivo:** `copilot-instructions.md`

Define as normas globais para traballar no proxecto:

- **Gitflow**: Sistema de xestión de ramas e versións
  - `release/x.y` - Versións estables
  - `develop` - Rama de desenvolvemento e integración continua
  - `feature/nome-descriptivo` - Novas funcionalidades
  - `hotfix/nome-descriptivo` - Correccións urxentes en producción

- **Versionado SemVer**: MAJOR.MINOR.PATCH

### 2. Instruccións para Commits Git
**Arquivo:** `instructions/git-commit-instructions.md`

Normas para escribir mensaxes de commit:

- ✓ Escribe as mensaxes en **galego**
- ✓ Comeza con mensaxe curta (50 caracteres máximo)
- ✓ Seguida de descripción máis extensa nos detalles
- ✓ Exemplo:
  ```
  feat: traducir template de pull request
  
  - Traducción completa do arquivo pr-template.md
  - Mantemos a estructura e formato orixinal
  - Refactorización de seccións clave
  ```

### 3. Instruccións para Comentarios Educacionais
**Arquivo:** `instructions/add-comment.instructions.md`

Transforma arquivos de código en recursos educativos:

- Engade comentarios que expliquen o "por qué"
- Adapta o nivel de detalle segundo a experiencia do usuario
- Mantén a estructura e codificación orixinal
- Incrementa o número de liñas ata o 125% coa engadida de comentarios

### 4. Guía para Scripts de Shell
**Arquivo:** `instructions/shell.instrucctions.md`

Mellorar prácticas para scripts en bash, sh e zsh:

- Utiliza `set -euo pipefail` para erro handling
- Valida todos os parámetros antes de execución
- Crea funcións reutilizables
- Documenta dependencias (jq, yq, etc.)

---

## 🎯 Prompts Dispoñibles

### Crear README
**Arquivo:** `prompts/crear-readme.prompt.md`

Xeración automática de README seguindo mellorador prácticas:

- Estrutura clara e ben organizada
- Inspirada en projectos Open Source destacados
- Inclúe logos de shields.io
- Escrito en galego
- Autoría: Profesor Damián Nogueiras

### Crear Pull Request
**Arquivo:** `prompts/crear-pr.prompt.md`

Automatiza a creación de Pull Requests desde especificacións:

1. Analiza requirements do template
2. Crea PR en borrador
3. Obtén cambios do PR
4. Actualiza título e descripción
5. Cambia a estado "Listo para revisar"
6. Asigna ao usuario

### Plan de Implementación
**Arquivo:** `prompts/create-implemention-plan.prompt.md`

Xeración automática de plans de implementación estruturados.

---

## 🛠️ Scripts de Utilidade

### find-last-modified-files.sh

Busca arquivos modificados recentemente coa información completa:

```bash
# Mostrar os 3 últimos arquivos (por defecto)
./.github/scripts/find-last-modified-files.sh

# Mostrar os 10 últimos arquivos
./.github/scripts/find-last-modified-files.sh 10
```

**Características:**
- Data e hora de última modificación
- Hash do commit
- Autor do cambio
- Saída con cores

### find-last-modified-files-simple.sh

Versión simplificada sen información adicional:

```bash
./.github/scripts/find-last-modified-files-simple.sh 5
```

**Ideal para:**
- Uso en tuberías ou scripts
- Integración en fluxos de traballo
- Requisitos de salida mínima

---

## 📝 Template de Pull Request

**Arquivo:** `pr-template.md`

Template personalizado en galego para pull requests que inclúe:

- ✓ Lista de verificación de requisitos
- ✓ Descripción da contribución
- ✓ Clasificación do tipo de contribución
- ✓ Notas adicionales
- ✓ Confirmación de cumplimento do Código de Conducta

---

## 🔧 Configuración Avanzada

### Personalizar Instruccións Xerais

Edita `copilot-instructions.md` para cambiar normas específicas do proxecto.

### Engadir Novos Prompts

1. Crea un arquivo en `prompts/` con extensión `.prompt.md`
2. Seguir a estrutura YAML do front matter
3. Documenta os pasos e requisitos claramente
4. Inclúe exemplos de uso

### Estender Scripts

Todos os scripts están documentados e son fáciles de estender:

```bash
# Verificar a estructura
cat .github/scripts/README.md
```

---

## 📚 Mellorador Prácticas

### Ao Crear Código

- Segue as instruccións en `shell.instrucctions.md` para scripts
- Utiliza os prompts dispoñibles para casos específicos
- Mantén os comentarios educacionais segundo as normas

### Ao Facer Commits

- Escribe mensaxes en galego
- Estrutura: mensaxe curta + descripción extensa
- Referencia issues cando sexa apropiado

### Ao Crear Pull Requests

- Utiliza o template en `pr-template.md`
- Completa todas as seccións
- Usa o prompt `crear-pr.prompt.md` se necesario

---

## 🐛 Solución de Problemas

### MCP Non Conecta

1. Verifica o token en `mcp.json`
2. Asegúrate de ter permisos de acceso en GitHub
3. Comproba a URL do servidor

### Scripts Non Executan

```bash
# Concede permisos de execución
chmod +x ./.github/scripts/*.sh

# Verifica a instalación de bash
which bash
```

### Prompts Non Aparecen

1. Asegúrate de que os arquivos están en `prompts/`
2. Comproba a sintaxe YAML do front matter
3. Recarga Copilot na túa IDE

---

## 📊 Estadísticas do Directorio

| Elemento | Cantidade |
|----------|----------|
| Arquivos de instruccións | 4 |
| Prompts dispoñibles | 3 |
| Scripts de utilidade | 2 |
| Templates | 1 |
| Total de arquivos | 11+ |

---

## 🎓 Recursos Educacionais

### Para Developers

- [GitHub Copilot Documentation](https://docs.github.com/en/copilot)
- [GitHub Skills](https://skills.github.com)
- [Gitflow Workflow](https://www.atlassian.com/git/tutorials/comparing-workflows/gitflow-workflow)

### Para Educadores

- [Semantic Versioning](https://semver.org/)
- [Clean Code Principles](https://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)
- [Shell Scripting Best Practices](https://mywiki.wooledge.org/BashGuide)

---

## 👤 Autor

**Profesor Damián Nogueiras**

Proxecto educativo desenvolvido para demostrar as mellorador prácticas no desenvolvemento de software con GitHub Copilot.

---

## 📄 Licencia

Este proxecto e toda a configuración están baixo a licencia MIT. Véxase o arquivo [LICENSE](../LICENSE) para máis detalles.

---

## 🔗 Enlaces Útiles

- [RoomApp - Repositorio Principal](https://github.com/damiancastelao/RoomApp)
- [Directorio de Scripts](./scripts/)
- [Directorio de Instruccións](./instructions/)
- [Directorio de Prompts](./prompts/)

---

**Última actualización:** Xaneiro 2026  
**Versión:** 1.0  
**Estado:** Activo e mantenido regularmente
