# NovaPay Backend
## Documentación Técnica Oficial

> **Proyecto:** NovaPay  
> **Componente:** Backend Fiscal  
> **Stack:** Java 21 · Spring Boot 3.5.11 · PostgreSQL 17 · Docker
> **Normativa:** VERI\*FACTU (AEAT) · TicketBAI (Álava · Bizkaia · Gipuzkoa)  
> **Versión doc:** 1.0 · Febrero 2026  
> **Estado:** En desarrollo

---

## Índice General de la Documentación

| Capítulo | Título | Estado |
|----------|--------|--------|
| **Cap. 0** | Entorno de Desarrollo · Instalación y configuración en Windows | ✅ Este documento |
| **Cap. 1** | Arquitectura del Sistema · Visión general y decisiones técnicas | ✅  Próximo |
| **Cap. 2** | Modelo de Dominio Fiscal · Entidades · Value Objects · Enums | ✅ Próximo |
| **Cap. 3** | Base de Datos · Schema SQL · Migraciones con Flyway | ✅ Próximo |
| **Cap. 4** | API REST · Contrato con Flutter · DTOs · Validaciones | ✅ Próximo |
| **Cap. 5** | Evidencia Fiscal · Hash · Encadenamiento · QR | ✅ Próximo |
| **Cap. 6** | Adaptador TicketBAI Álava · XML · Firma · Envío | 🔜 Próximo |
| **Cap. 7** | Adaptador VERI\*FACTU AEAT · SOAP · Firma · Estados | 🔜 Próximo |
| **Cap. 8** | Adaptadores Bizkaia y Gipuzkoa | 🔜 Próximo |
| **Cap. 9** | Producción · Seguridad · Certificados · Monitorización | 🔜 Próximo |

---

## Estado actual de implementación (febrero 2026)

| Capítulo | Estado | Notas |
|----------|--------|-------|
| Cap. 0 — Entorno | ✅ Completo | Spring Boot 3.5.11 · PostgreSQL 17 · pom.xml correcto |
| Cap. 1 — Arquitectura | ✅ Documentado | — |
| Cap. 2 — Dominio | 🔄 Parcial | VOs + Enums hechos · Entidades pendientes |
| Cap. 3 — Base de datos | ⬜ Pendiente | Bloquea el arranque |
| Cap. 4 — API REST | ⬜ Pendiente | — |
| Cap. 5 — Evidencia fiscal | ⬜ Pendiente | — |
| Cap. 6 — TicketBAI Álava | ⬜ Pendiente | — |
| Cap. 7 — VERIFACTU AEAT | ⬜ Pendiente | — |
| Cap. 8 — Bizkaia / Gipuzkoa | ⬜ Pendiente | — |
| Cap. 9 — Producción | ⬜ Pendiente | — |

**Implementado en código:**
- `Money.java`, `InvoiceNumber.java`, `TaxId.java` (paquete `domain.valueObject`)
- 7 enums: `FiscalRecordType`, `FiscalStatus`, `InvoiceStatus`, `InvoiceType`, `RectificationType`, `TaxAgency`, `TaxType`

**Siguiente paso:** Entidades JPA de Cap. 2 (Company, PosTerminal, Invoice, InvoiceLine, TaxBreakdown, FiscalRecord)

---

# Capítulo 0 — Entorno de Desarrollo en Windows

> Guía de instalación paso a paso · Capa 100% gratuita

---

## 0.1 Sobre este documento

Este capítulo cubre la preparación completa del entorno de desarrollo local en Windows para trabajar en el backend de NovaPay. Al finalizar tendrás todas las herramientas instaladas, la base de datos corriendo y el proyecto arrancando en tu máquina.

**¿A quién va dirigido?** A cualquier desarrollador que se incorpore al proyecto, independientemente de su nivel de experiencia con Java o Spring Boot.

**Tiempo estimado:** 45-90 minutos (dependiendo de la velocidad de descarga).

---

## 0.2 ¿Qué es NovaPay?

**NovaPay** es un sistema de punto de venta (TPV) con backend fiscal integrado. El backend es el componente servidor que:

- Recibe los datos de las facturas desde la app Flutter (el TPV)
- Genera la evidencia fiscal (hash, encadenamiento, XML firmado, QR)
- Envía los registros fiscales a las agencias tributarias (AEAT y Haciendas Forales)
- Persiste toda la información de forma auditable e inmutable

El nombre `novapay` lo usaremos como identificador en:

| Contexto | Valor |
|----------|-------|
| Nombre del proyecto | `novapay` |
| Paquete Java | `com.novapay` |
| Nombre de la base de datos | `novapay` |
| Usuario de la base de datos | `novapay` |
| Nombre del contenedor Docker | `novapay-db` |

---

## 0.3 Stack tecnológico

| Herramienta | Versión | Para qué sirve |
|-------------|---------|----------------|
| **Java JDK** | 21 LTS | Lenguaje del backend |
| **Spring Boot** | 3.5.11 | Framework principal |
| **Maven** | Integrado en IntelliJ | Gestión de dependencias y compilación |
| **IntelliJ IDEA Community** | Última estable | IDE de desarrollo (gratuito) |
| **Git** | 2.x | Control de versiones |
| **Docker Desktop** | 25.x | Contenedor para PostgreSQL |
| **PostgreSQL** | 17 (vía Docker) | Base de datos principal |

> **Nota sobre Maven:** no es necesario instalarlo manualmente. IntelliJ IDEA Community lo incluye integrado. Esto simplifica la instalación.

> **¿Por qué IntelliJ y no VS Code?** VS Code es un editor genérico. IntelliJ IDEA Community está diseñado específicamente para Java: detecta errores en tiempo real, integra Maven sin configuración extra, tiene un debugger superior y soporte nativo de Spring Boot. La versión Community es gratuita para siempre.

---

## 0.4 Instalar Java 21 JDK

### Proveedor recomendado

Usaremos **Eclipse Temurin** (proyecto Adoptium), el proveedor de Java más usado en entornos empresariales. Es gratuito, open source y tiene soporte a largo plazo (LTS).

| Campo | Valor |
|-------|-------|
| Proveedor | Eclipse Temurin · Adoptium |
| Versión | Java **21 LTS** |
| URL | https://adoptium.net |
| Formato | Windows Installer (`.msi`) |

### Instalación

**Paso 1.** Abre el navegador y ve a https://adoptium.net

**Paso 2.** Haz clic en **"Other platforms and versions"**

**Paso 3.** Filtra con estos valores:

| Filtro | Valor |
|--------|-------|
| Operating System | Windows |
| Architecture | x64 |
| Package Type | JDK |
| Version | **21 - LTS** |

**Paso 4.** Haz clic en **"Latest Release"** — se descargará un fichero `.msi` con un nombre similar a `OpenJDK21U-jdk_x64_windows_hotspot_21.x.x.msi`

**Paso 5.** Ejecuta el instalador `.msi`

**Paso 6.** ⚠️ Durante la instalación, marca obligatoriamente estas dos opciones:

| Opción | Obligatorio |
|--------|-------------|
| `Set JAVA_HOME variable` | ✅ Sí |
| `Add to PATH` | ✅ Sí |

Sin estas opciones, Java no será reconocido desde la línea de comandos ni desde IntelliJ.

**Paso 7.** Haz clic en Next → Next → Install → Finish

### Verificación

Abre **PowerShell** (búscalo en el menú Inicio) y ejecuta:

```powershell
java -version
```

Resultado esperado:
```
openjdk version "21.0.5" 2024-10-15
OpenJDK Runtime Environment Temurin-21.0.5+11
OpenJDK 64-Bit Server VM Temurin-21.0.5+11
```

> Si aparece el error `'java' no se reconoce como un comando`: cierra PowerShell completamente y ábrelo de nuevo. Las variables de entorno (PATH) solo se cargan en ventanas nuevas.

---

## 0.5 Instalar IntelliJ IDEA Community Edition

### ⚠️ Importante: Community Edition, no Ultimate

| Edición | Precio | ¿La necesitamos? |
|---------|--------|-----------------|
| IntelliJ IDEA **Ultimate** | De pago | ❌ No |
| IntelliJ IDEA **Community** | Gratuita | ✅ Sí, esta |

### Descarga e instalación

**Paso 1.** Ve a https://www.jetbrains.com/idea/download

**Paso 2.** Desplázate hacia abajo hasta encontrar **"IntelliJ IDEA Community Edition"** y haz clic en **Download**

**Paso 3.** Ejecuta el instalador `.exe`. Marca estas opciones:

| Opción del instalador | Marcar |
|-----------------------|--------|
| Add "Open Folder as Project" | ✅ |
| Asociar extensión `.java` | ✅ |
| Add launchers dir to the PATH | ✅ |
| Create Desktop Shortcut | ✅ |

**Paso 4.** Completa la instalación y abre IntelliJ IDEA

### Primera apertura

1. Te pedirá importar configuración → selecciona **"Do not import settings"**
2. Elige el tema visual que prefieras (Dark / Light)
3. IntelliJ detectará Java 21 automáticamente

Si no detecta Java 21 automáticamente, configúralo manualmente:
`File → Project Structure → SDKs → + → Add JDK`
Busca la ruta: `C:\Program Files\Eclipse Adoptium\jdk-21.x.x.x-hotspot`

---

## 0.6 Instalar Git

Git es el sistema de control de versiones. Registra cada cambio en el código, permite trabajar en equipo y es obligatorio en cualquier proyecto profesional.

### Instalación

**Paso 1.** Ve a https://git-scm.com/download/win y descarga el instalador de **64 bits**

**Paso 2.** Ejecuta el instalador con estas configuraciones (el resto déjalo por defecto):

| Opción | Valor recomendado |
|--------|-------------------|
| Default editor | Notepad++ (si lo tienes instalado) |
| Initial branch name | `main` ← cambiar de "master" a "main" |
| PATH environment | "Git from the command line and also from 3rd-party software" |
| Line endings | "Checkout Windows-style, commit Unix-style" |

**Paso 3.** Tras la instalación, abre PowerShell y configura tu identidad:

```powershell
git config --global user.name "Tu Nombre Apellido"
git config --global user.email "tu@email.com"
```

### Verificación

```powershell
git --version
# Esperado: git version 2.x.x.windows.x
```

---

## 0.7 Instalar Docker Desktop y PostgreSQL

En lugar de instalar PostgreSQL directamente en Windows (lo que suele generar problemas con permisos, servicios y versiones), lo ejecutaremos dentro de un contenedor Docker. Es la forma estándar en entornos de desarrollo profesionales.

### 0.7.1 Instalar Docker Desktop

**Paso 1.** Ve a https://www.docker.com/products/docker-desktop/

**Paso 2.** Haz clic en **"Download for Windows"**

**Paso 3.** Ejecuta el instalador. Si pregunta por **WSL2** (Windows Subsystem for Linux), acéptalo — es el motor que usa Docker en Windows moderno

**Paso 4.** Al terminar la instalación, **reinicia el ordenador**

**Paso 5.** Tras el reinicio, abre **Docker Desktop** desde el escritorio o el menú Inicio. Espera a que aparezca **"Engine running"** en la esquina inferior izquierda (icono verde). Puede tardar 1-2 minutos la primera vez.

### 0.7.2 Levantar PostgreSQL para NovaPay

Con Docker corriendo, abre **PowerShell** y ejecuta:

```powershell
docker run --name novapay-db `
  -e POSTGRES_DB=novapay `
  -e POSTGRES_USER=novapay `
  -e POSTGRES_PASSWORD=novapay123 `
  -p 5432:5432 `
  -d postgres:17
```

> **Nota sobre la sintaxis:** En PowerShell (Windows) el carácter de continuación de línea es el acento grave `` ` ``, no la barra invertida `\` que se usa en Linux/Mac. Si pegas el comando en una sola línea también funciona.

Verifica que está corriendo:

```powershell
docker ps
```

Deberías ver una línea con `novapay-db` y el estado `Up`.

### 0.7.3 Credenciales de la base de datos

Guarda estos datos — los usarás para configurar el backend:

| Parámetro | Valor |
|-----------|-------|
| Host | `localhost` |
| Puerto | `5432` |
| Base de datos | `novapay` |
| Usuario | `novapay` |
| Contraseña | `novapay123` |

> Estas credenciales son para el entorno de **desarrollo local**. En producción se usarán credenciales seguras gestionadas por un gestor de secretos.

### 0.7.4 Gestión del contenedor

| Comando | Cuándo usarlo |
|---------|---------------|
| `docker ps` | Ver si el contenedor está corriendo |
| `docker ps -a` | Ver todos los contenedores (incluso parados) |
| `docker start novapay-db` | Arrancar la base de datos tras un reinicio |
| `docker stop novapay-db` | Parar la base de datos |
| `docker logs novapay-db` | Ver logs internos de PostgreSQL |

> **Importante:** Tras reiniciar el ordenador, Docker Desktop arrancará automáticamente pero el contenedor `novapay-db` puede quedar en estado parado. Si el backend no conecta con la base de datos, ejecuta `docker start novapay-db`.

---

## 0.8 Verificación del entorno completo

Antes de crear el proyecto, verifica que todo está correctamente instalado. Abre **PowerShell** y ejecuta:

```powershell
# 1. Java 21
java -version
# Esperado: openjdk version "21.x.x"

# 2. Git
git --version
# Esperado: git version 2.x.x.windows.x

# 3. Docker
docker --version
# Esperado: Docker version 25.x.x

# 4. Contenedor de base de datos
docker ps
# Esperado: novapay-db con estado "Up"
```

### Checklist de verificación

- [ ] `java -version` → muestra `21.x.x`
- [ ] `git --version` → muestra `2.x.x`
- [ ] `docker --version` → muestra versión instalada
- [ ] `docker ps` → muestra `novapay-db` en estado `Up`
- [ ] IntelliJ IDEA Community → abre correctamente
- [ ] IntelliJ detecta Java 21 en Project Structure

No continúes al siguiente paso hasta que todos los ítems estén marcados.

---

## 0.9 Crear el Proyecto Base

Spring Initializr es la herramienta oficial de Spring para generar el esqueleto de un proyecto. Es el punto de partida estándar en la industria.

### 0.9.1 Configuración en Spring Initializr

**Paso 1.** Ve a https://start.spring.io

**Paso 2.** Rellena exactamente estos valores:

| Campo | Valor |
|-------|-------|
| Project | **Maven** |
| Language | **Java** |
| Spring Boot | **3.5.1** · última estable (no SNAPSHOT, no RC) |
| Group | `com.novapay` |
| Artifact | `novapay-backend` |
| Name | `novapay-backend` |
| Description | `NovaPay · Backend Fiscal TPV · VERIFACTU / TicketBAI` |
| Package name | `com.novapay.backend` |
| Packaging | **Jar** |
| Java | **21** |

> **Glosario rápido de estos campos:**
> - **Group** (`com.novapay`): identificador de la organización en el ecosistema Java. Por convención se usa el dominio del proyecto en orden inverso.
> - **Artifact** (`novapay-backend`): nombre técnico del módulo. Será también el nombre del fichero `.jar` que se genera al compilar.
> - **Package name**: paquete raíz de todo el código Java del proyecto.

### 0.9.2 Dependencias iniciales

Haz clic en **"ADD DEPENDENCIES"** y añade estas:

| Dependencia | Por qué la necesitamos |
|-------------|------------------------|
| `Spring Web` | Crear los endpoints REST que consumirá la app Flutter |
| `Spring Data JPA` | Capa de acceso a la base de datos |
| `PostgreSQL Driver` | Conector JDBC entre Spring y PostgreSQL |
| `Validation` | Validar los datos entrantes desde Flutter |
| `Flyway Migration` | Control de versiones del esquema de base de datos |
| `Spring Security` | Seguridad de los endpoints (se configurará en Cap. 9) |
| `Lombok` | Elimina código repetitivo en las clases Java |

### 0.9.3 Generar y abrir el proyecto

**Paso 3.** Haz clic en **"GENERATE"** — se descargará un fichero `.zip`

**Paso 4.** Crea la carpeta `C:\Proyectos\` y extrae el ZIP ahí. Resultado: `C:\Proyectos\novapay-backend\`

**Paso 5.** En IntelliJ: `File → Open` → selecciona la carpeta `novapay-backend`

**Paso 6.** IntelliJ detectará automáticamente que es un proyecto Maven y descargará todas las dependencias. La primera vez puede tardar **3-5 minutos**. Verás una barra de progreso en la parte inferior de la ventana.

### 0.9.4 Estructura del proyecto

Una vez abierto en IntelliJ verás esta estructura:

```
novapay-backend/
├── src/
│   ├── main/
│   │   ├── java/
│   │   │   └── com/novapay/backend/
│   │   │       └── NovapayBackendApplication.java   ← arranque de la app
│   │   └── resources/
│   │       └── application.properties               ← configuración
│   └── test/
│       └── java/
│           └── com/novapay/backend/
│               └── NovapayBackendApplicationTests.java
└── pom.xml                                           ← dependencias Maven
```

**Ficheros clave:**

| Fichero | Qué es |
|---------|--------|
| `NovapayBackendApplication.java` | Punto de entrada. Contiene el método `main` que arranca Spring Boot. |
| `application.properties` | Configuración de la aplicación: base de datos, puertos, flags. |
| `pom.xml` | Manifiesto Maven. Declara todas las dependencias y la versión de Java. |

### 0.9.5 Configurar la conexión a la base de datos

Abre `src/main/resources/application.properties` y reemplaza su contenido con:

```properties
# ── Aplicación ────────────────────────────────────────────
spring.application.name=novapay-backend

# ── Base de datos ─────────────────────────────────────────
spring.datasource.url=jdbc:postgresql://localhost:5432/novapay
spring.datasource.username=novapay
spring.datasource.password=novapay123
spring.datasource.driver-class-name=org.postgresql.Driver

# ── JPA / Hibernate ───────────────────────────────────────
# validate: Hibernate comprueba el schema pero NO lo modifica.
# Los cambios de schema son responsabilidad exclusiva de Flyway.
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

# ── Flyway ────────────────────────────────────────────────
spring.flyway.enabled=true

# ── Servidor ──────────────────────────────────────────────
server.port=8080
```

> **¿Por qué `ddl-auto=validate` y no `create` o `update`?**
> NovaPay es un sistema fiscal. Ningún componente debe modificar la estructura de la base de datos de forma automática o inesperada. Hibernate solo valida que el schema coincide con las entidades. Flyway gestiona todos los cambios de schema de forma versionada, revisable y auditable. Esto se detalla en el Capítulo 3.

### 0.9.6 Primer arranque

**Paso 7.** En el árbol de ficheros de IntelliJ, localiza `NovapayBackendApplication.java`

**Paso 8.** Haz clic derecho → **Run 'NovapayBackendApplication'**

**Paso 9.** En la consola inferior verás los logs. Es probable que aparezca este error en el primer arranque:

```
FlywayException: Found non-empty schema(s) "public" but no schema history table.
```

Esto es **normal y esperado**: Flyway está activo pero aún no existen migraciones. La solución correcta es crear la primera migración `V1__init.sql` en `src/main/resources/db/migration/` (Capítulo 3). **No usar `baseline-on-migrate=true`** — esa opción es para bases de datos existentes que aún no tienen historial Flyway, no para proyectos nuevos.

Una vez creada la migración V1, vuelve a ejecutar. Deberías ver:

```
Started NovapayBackendApplication in x.xxx seconds (process running for x.xxx)
```

Cuando veas este mensaje el entorno está **completamente operativo**.

---

## 0.10 Resumen del capítulo

### Lo que has instalado y configurado

| Componente | Estado |
|------------|--------|
| Java 21 JDK (Eclipse Temurin) | ✅ Instalado |
| IntelliJ IDEA Community | ✅ Instalado y configurado |
| Git | ✅ Instalado con identidad configurada |
| Docker Desktop | ✅ Instalado y corriendo |
| PostgreSQL 16 (contenedor `novapay-db`) | ✅ Corriendo en puerto 5432 |
| Proyecto `novapay-backend` | ✅ Creado, dependencias descargadas, arrancando |

### Próximo capítulo

El **Capítulo 1** cubre la arquitectura general del sistema: qué módulos existen, cómo se comunican entre sí, y las decisiones técnicas de diseño del backend de NovaPay.

---

---

*NovaPay · Documentación Técnica Backend · Versión 1.0 · Febrero 2026*  
*Este documento forma parte de la documentación oficial del proyecto y debe mantenerse actualizado con cada cambio relevante en el entorno de desarrollo.*

# NovaPay Backend
## Documentación Técnica Oficial

> **Capítulo:** 1 — Arquitectura del Sistema  
> **Versión doc:** 1.0 · Febrero 2026  
> **Prerrequisito:** Capítulo 0 completado (entorno operativo)

---

## Índice del Capítulo

- [1.1 Qué es el backend de NovaPay](#11-qué-es-el-backend-de-novapay)
- [1.2 Visión general del sistema completo](#12-visión-general-del-sistema-completo)
- [1.3 Arquitectura interna del backend](#13-arquitectura-interna-del-backend)
- [1.4 Módulos y responsabilidades](#14-módulos-y-responsabilidades)
- [1.5 Flujo de una factura de extremo a extremo](#15-flujo-de-una-factura-de-extremo-a-extremo)
- [1.6 Decisiones técnicas y por qué se tomaron](#16-decisiones-técnicas-y-por-qué-se-tomaron)
- [1.7 Lo que el backend NO hace](#17-lo-que-el-backend-no-hace)
- [1.8 Estructura de paquetes en el código](#18-estructura-de-paquetes-en-el-código)
- [1.9 Entornos: desarrollo, pruebas y producción](#19-entornos-desarrollo-pruebas-y-producción)
- [1.10 Resumen y próximo capítulo](#110-resumen-y-próximo-capítulo)

---

## 1.1 Qué es el backend de NovaPay

El backend de NovaPay es el servidor central del sistema. Es el único componente que tiene conocimiento fiscal completo y el único autorizado a comunicarse con las agencias tributarias.

### Responsabilidades del backend

| Responsabilidad | Descripción |
|----------------|-------------|
| **Recibir facturas** | Acepta los datos de venta desde la app Flutter (TPV) |
| **Validar datos** | Comprueba que los datos son correctos antes de procesarlos |
| **Calcular impuestos** | Calcula bases imponibles, cuotas IVA y totales |
| **Generar evidencia fiscal** | Crea el hash, el encadenamiento y el QR de cada factura |
| **Construir XML fiscal** | Genera el XML requerido por cada agencia tributaria |
| **Firmar digitalmente** | Firma los documentos XML con el certificado electrónico |
| **Enviar a agencias** | Transmite los registros a AEAT y/o Haciendas Forales |
| **Persistir todo** | Guarda facturas, XMLs, respuestas y logs de forma inmutable |
| **Exponer estado** | Informa a Flutter del estado fiscal de cada factura |

### Lo que el backend garantiza

- **Trazabilidad completa:** cada factura tiene un registro de todo lo que ha ocurrido con ella
- **Inmutabilidad:** los registros fiscales una vez creados no se modifican, solo se añaden nuevos
- **Resiliencia:** si una agencia no está disponible, el sistema reintenta sin perder datos
- **Auditabilidad:** cualquier inspector puede reconstruir el historial completo

---

## 1.2 Visión general del sistema completo

El sistema NovaPay tiene tres capas principales:

```
┌─────────────────────────────────────────────────────┐
│                   APP FLUTTER (TPV)                 │
│         Interfaz de caja · Gestión de ventas        │
│              Dispositivo del comercio               │
└─────────────────┬───────────────────────────────────┘
                  │  HTTPS · REST · JSON
                  │  (lo que diseñamos en Cap. 4)
┌─────────────────▼───────────────────────────────────┐
│              NOVAPAY BACKEND (Spring Boot)          │
│                                                     │
│   Validación → Cálculo → Evidencia → XML → Firma    │
│              ↓                                      │
│         PostgreSQL (persistencia)                   │
└──────┬──────────────────────┬───────────────────────┘
       │                      │
       │ SOAP/HTTPS            │ HTTPS/REST
       │ Certificado           │ Certificado
┌──────▼──────┐        ┌──────▼──────────────────────┐
│    AEAT     │        │    HACIENDAS FORALES         │
│ VERI*FACTU  │        │  TicketBAI · Álava           │
│             │        │  TicketBAI · Bizkaia (BATUZ) │
└─────────────┘        │  TicketBAI · Gipuzkoa        │
                       └─────────────────────────────┘
```

### Principio fundamental de la arquitectura

> **Flutter envía datos de negocio. El backend genera la evidencia fiscal.**

Flutter nunca debe:
- Calcular hashes ni encadenamientos
- Generar XMLs fiscales
- Firmar documentos
- Almacenar certificados electrónicos
- Comunicarse directamente con AEAT ni Haciendas Forales

Todo eso es responsabilidad exclusiva del backend. Esta separación es una decisión de seguridad, no una preferencia técnica.

---

## 1.3 Arquitectura interna del backend

El backend sigue una **arquitectura hexagonal** (también llamada Ports & Adapters). Es el patrón más adecuado para este proyecto porque:

- Separa la lógica de negocio de los detalles técnicos (base de datos, APIs externas, XML)
- Permite cambiar un adaptador (por ejemplo, añadir Gipuzkoa) sin tocar el núcleo
- Facilita las pruebas unitarias de la lógica fiscal
- Hace el código mantenible cuando cambian las especificaciones técnicas de las agencias

### Las tres capas de la arquitectura hexagonal

```
┌─────────────────────────────────────────────────────┐
│                  INTERFACES (entrada)               │
│         Controllers REST · DTOs · Validación        │
│    "Lo que el mundo exterior ve del backend"        │
└─────────────────┬───────────────────────────────────┘
                  │
┌─────────────────▼───────────────────────────────────┐
│               APLICACIÓN (casos de uso)             │
│    EmitirFactura · AnularFactura · RetransmitirXML  │
│    ConsultarEstadoFiscal · ReintentarEnvio          │
│    "Lo que el sistema puede hacer"                  │
└──────────┬──────────────────────┬───────────────────┘
           │                      │
┌──────────▼──────────┐  ┌────────▼───────────────────┐
│      DOMINIO        │  │    INFRAESTRUCTURA         │
│                     │  │                            │
│  Entidades fiscales │  │  Repositorios JPA          │
│  Value Objects      │  │  Clientes AEAT/TicketBAI   │
│  Reglas de negocio  │  │  Serializadores XML        │
│  Cálculo de hash    │  │  Motor de firma digital    │
│  Encadenamiento     │  │  Worker de reintentos      │
│                     │  │  Generador de QR           │
│  "Las reglas que    │  │  "Los detalles técnicos    │
│  nunca cambian"     │  │  que sí pueden cambiar"    │
└─────────────────────┘  └────────────────────────────┘
```

### Regla de dependencias

Las dependencias solo van hacia adentro:

- **Interfaces** depende de **Aplicación**
- **Aplicación** depende de **Dominio**
- **Infraestructura** depende de **Dominio** (implementa sus interfaces)
- **Dominio** no depende de nada

Esto significa que el dominio fiscal (las reglas de cálculo, el hash, el encadenamiento) puede probarse y ejecutarse sin base de datos, sin HTTP y sin agencias tributarias.

---

## 1.4 Módulos y responsabilidades

El proyecto `novapay-backend` se organiza internamente en estos módulos lógicos. En el código, cada uno es un paquete Java dentro de `com.novapay.backend`:

### Módulos del núcleo

| Módulo | Paquete | Responsabilidad |
|--------|---------|-----------------|
| `domain` | `com.novapay.backend.domain` | Entidades fiscales, value objects, interfaces de repositorio, reglas de negocio |
| `application` | `com.novapay.backend.application` | Casos de uso: emitir, anular, reintentar, consultar |
| `interfaces` | `com.novapay.backend.interfaces` | Controllers REST, DTOs, mappers, manejo de errores HTTP |

### Módulos de infraestructura

| Módulo | Paquete | Responsabilidad |
|--------|---------|-----------------|
| `infra.db` | `com.novapay.backend.infra.db` | Repositorios JPA, entidades de base de datos |
| `infra.xml` | `com.novapay.backend.infra.xml` | Serialización/deserialización XML fiscal (JAXB) |
| `infra.signature` | `com.novapay.backend.infra.signature` | Firma digital XML (XAdES) |
| `infra.outbox` | `com.novapay.backend.infra.outbox` | Patrón outbox para envío asíncrono confiable |

### Adaptadores por agencia tributaria

| Módulo | Paquete | Responsabilidad |
|--------|---------|-----------------|
| `adapter.aeat` | `com.novapay.backend.adapter.aeat` | Cliente SOAP AEAT · VERI\*FACTU |
| `adapter.tbai.araba` | `com.novapay.backend.adapter.tbai.araba` | TicketBAI Álava |
| `adapter.tbai.bizkaia` | `com.novapay.backend.adapter.tbai.bizkaia` | TicketBAI Bizkaia / BATUZ |
| `adapter.tbai.gipuzkoa` | `com.novapay.backend.adapter.tbai.gipuzkoa` | TicketBAI Gipuzkoa |

### Por qué un adaptador por territorio

Aunque TicketBAI es una especificación común a las tres haciendas forales, cada territorio tiene:

- **Endpoints distintos** (URLs diferentes)
- **Políticas de firma distintas** (hash de política diferente)
- **Validaciones propias** (reglas adicionales por hacienda)
- **Ciclos de actualización independientes** (una hacienda puede actualizar su XSD sin que las otras lo hagan)

Usar un solo adaptador con `if (territorio == ARABA)` hace el código frágil y difícil de mantener. Con adaptadores separados, un cambio en Bizkaia no puede romper el adaptador de Álava.

---

## 1.5 Flujo de una factura de extremo a extremo

Este es el recorrido completo de una factura desde que se genera en el TPV hasta que queda registrada en la agencia tributaria.

### Fase 1 — Recepción (síncrona, respuesta inmediata)

```
Flutter (TPV)
    │
    │  POST /api/v1/invoices
    │  { datos de la venta en JSON }
    │
    ▼
Controller REST
    │  Valida formato y campos obligatorios
    ▼
Caso de uso: EmitirFactura
    │  Valida reglas de negocio (totales, tipos, serie/número)
    │  Crea entidad Invoice en estado PENDIENTE
    │  Crea entidad FiscalRecord en estado PENDIENTE_ENVIO
    │  Calcula hash de la factura
    │  Genera contenido del QR
    │  Persiste todo en base de datos (transacción)
    │  Inserta mensaje en tabla outbox
    ▼
Responde a Flutter:
    {
      invoiceId: "...",
      status: "EMITIDA",
      fiscalStatus: "PENDIENTE_ENVIO",
      qrContent: "https://...",
      invoiceNumber: "A-2026-00123"
    }
```

> Flutter recibe respuesta inmediatamente. La factura está emitida aunque aún no haya llegado a la agencia tributaria. El TPV no espera bloqueado a que AEAT responda.

### Fase 2 — Envío (asíncrona, segundo plano)

```
Worker / Scheduler (cada N segundos)
    │
    │  Lee mensajes pendientes de la tabla outbox
    ▼
Caso de uso: ProcesarEnvioFiscal
    │
    ├─► ¿Territorio AEAT?
    │       Adaptador AEAT
    │       Genera XML VERI*FACTU
    │       Firma XML con certificado
    │       Envía por SOAP a AEAT
    │       Parsea respuesta
    │
    └─► ¿Territorio TicketBAI?
            Selecciona adaptador (Álava / Bizkaia / Gipuzkoa)
            Genera XML TicketBAI
            Firma XML con certificado
            Envía por HTTPS a la hacienda foral
            Parsea respuesta
    │
    ▼
Persiste resultado:
    FiscalRecord.status = ACEPTADO | RECHAZADO | ERROR_TECNICO
    FiscalRecord.responseRaw = respuesta completa de la agencia
    FiscalRecord.sentAt = timestamp del envío
    FiscalRecord.externalReference = código de la agencia
    │
    ▼
Flutter puede consultar el estado en cualquier momento:
    GET /api/v1/invoices/{id}/fiscal-status
```

### Fase 3 — Reintentos (automáticos)

```
Si el envío falla por error técnico (red caída, agencia no disponible):
    FiscalRecord.status = REINTENTO
    FiscalRecord.retryCount++
    Siguiente intento con espera exponencial:
        1er reintento: 30 segundos
        2º reintento:  2 minutos
        3er reintento: 10 minutos
        4º reintento:  1 hora
        Tras 5 fallos: ERROR_PERMANENTE → alerta a operaciones
```

### Estados posibles de un FiscalRecord

| Estado | Significado |
|--------|-------------|
| `PENDIENTE_ENVIO` | Factura creada, envío en cola |
| `ENVIANDO` | Envío en progreso en este momento |
| `ACEPTADO` | Agencia ha confirmado el registro |
| `RECHAZADO` | Agencia ha rechazado el registro (error en los datos) |
| `REINTENTO` | Fallo técnico, programado para reintento |
| `ERROR_PERMANENTE` | Superado el máximo de reintentos, requiere intervención manual |
| `ANULADO` | Factura anulada correctamente en la agencia |

---

## 1.6 Decisiones técnicas y por qué se tomaron

Esta sección documenta las decisiones de arquitectura más importantes. Es importante que cualquier desarrollador del equipo entienda no solo qué se hizo, sino por qué.

### Decisión 1 — Arquitectura hexagonal en lugar de capas clásicas

| | Capas clásicas (Controller → Service → Repository) | Hexagonal (Ports & Adapters) |
|--|--|--|
| **Cambiar un adaptador fiscal** | Requiere modificar el Service | Sustituir solo el adaptador |
| **Probar la lógica fiscal** | Necesitas la base de datos | Puedes probar sin nada externo |
| **Añadir nueva agencia** | Riesgo de romper otras | Módulo completamente aislado |
| **Complejidad inicial** | Baja | Media |

**Decisión:** hexagonal. La complejidad inicial adicional se recupera en la primera vez que hay que añadir o modificar un adaptador fiscal sin romper los demás.

---

### Decisión 2 — Envío asíncrono con patrón Outbox

El envío síncrono (Flutter espera hasta que AEAT responde) tiene estos problemas:

- AEAT puede tardar segundos o fallar temporalmente
- El TPV queda bloqueado durante ese tiempo
- Si hay un corte de red, la factura podría perderse

El **patrón Outbox** resuelve esto:

1. La factura y el mensaje de envío se guardan en la misma transacción de base de datos
2. Si la transacción falla, no se guarda nada (consistencia total)
3. Si la transacción tiene éxito, el mensaje queda en la tabla `outbox` aunque la red falle
4. Un worker independiente procesa los mensajes pendientes con reintentos

**Resultado:** el TPV nunca pierde una factura y nunca se bloquea esperando a Hacienda.

---

### Decisión 3 — Un adaptador por territorio fiscal

Las tres opciones consideradas:

| Opción | Descripción | Problema |
|--------|-------------|---------|
| **A** | Un solo cliente con `if/switch` por territorio | Fragilidad: cambiar Bizkaia puede romper Álava |
| **B** | Herencia: clase base + subclase por territorio | Acoplamiento: la herencia es difícil de mantener en fiscal |
| **C** | Interfaz común + implementación independiente por territorio | Aislamiento total, fácil de probar y mantener |

**Decisión:** opción C. Cada adaptador es completamente independiente. Comparten una interfaz (`FiscalAgencyAdapter`) pero ningún código de implementación.

---

### Decisión 4 — Flyway para gestión del schema de base de datos

En un sistema fiscal, los cambios en la base de datos deben ser:

- **Versionados:** cada cambio tiene un número de versión
- **Auditables:** se sabe qué cambió, cuándo y en qué entorno
- **Reversibles:** en caso de error, se puede saber exactamente qué estado tenía la base de datos
- **Reproducibles:** cualquier entorno (dev, test, prod) puede llegar al mismo estado

Hibernate con `ddl-auto=update` no cumple ninguno de estos requisitos. Flyway sí.

---

### Decisión 5 — PostgreSQL como base de datos

| Criterio | PostgreSQL | MySQL | H2 |
|----------|-----------|-------|----|
| Soporte JSON nativo | ✅ JSONB | ⚠️ Limitado | ❌ |
| Transacciones robustas | ✅ ACID completo | ✅ | ✅ |
| Extensiones para auditoría | ✅ | ⚠️ | ❌ |
| Uso en producción fiscal | ✅ Ampliamente | ✅ | ❌ |
| Gratuito | ✅ | ✅ | ✅ |

**Decisión:** PostgreSQL. El soporte JSONB es especialmente útil para guardar las respuestas crudas de las agencias (que son XML/JSON variable) de forma consultable.

---

## 1.7 Lo que el backend NO hace

Es igual de importante definir qué queda fuera del alcance del backend, para evitar malentendidos durante el desarrollo.

| Funcionalidad | Dónde va | Por qué no en el backend |
|--------------|----------|--------------------------|
| Interfaz de usuario del TPV | App Flutter | El backend es headless (sin pantalla) |
| Gestión de productos/catálogo | App Flutter o servicio separado | No es funcionalidad fiscal |
| Gestión de empleados/turnos | App Flutter o servicio separado | No es funcionalidad fiscal |
| Pagos con tarjeta (datáfono) | Integración externa en Flutter | Protocolo propietario del terminal |
| Impresión de tickets | App Flutter | El backend sí genera el QR, pero no imprime |
| Contabilidad | ERP externo | NovaPay no es un ERP |
| LROE (Bizkaia) | Módulo separado futuro | Alcance diferente al de emisión de facturas |

---

## 1.8 Estructura de paquetes en el código

Esta es la estructura completa que tendrá el proyecto en IntelliJ una vez desarrollado:

```
com.novapay.backend/
│
├── domain/
│   ├── model/
│   │   ├── Invoice.java               ← factura de negocio
│   │   ├── InvoiceLine.java           ← línea de factura
│   │   ├── FiscalRecord.java          ← registro fiscal (inmutable)
│   │   ├── Company.java               ← empresa emisora
│   │   └── PosTerminal.java           ← terminal TPV
│   ├── valueobject/
│   │   ├── TaxId.java                 ← NIF/CIF validado
│   │   ├── Money.java                 ← importe con moneda
│   │   ├── InvoiceNumber.java         ← serie + número
│   │   └── FiscalHash.java            ← huella fiscal
│   ├── enums/
│   │   ├── InvoiceType.java           ← COMPLETA, SIMPLIFICADA, RECTIFICATIVA
│   │   ├── FiscalStatus.java          ← PENDIENTE_ENVIO, ACEPTADO, etc.
│   │   ├── TaxAgency.java             ← AEAT, TBAI_ARABA, TBAI_BIZKAIA, TBAI_GIPUZKOA
│   │   └── FiscalRecordType.java      ← ALTA, ANULACION, SUBSANACION
│   └── port/
│       ├── InvoiceRepository.java     ← interfaz (implementada en infra.db)
│       ├── FiscalRecordRepository.java
│       └── FiscalAgencyAdapter.java   ← interfaz de adaptador (implementada por cada agencia)
│
├── application/
│   ├── usecase/
│   │   ├── EmitirFactura.java
│   │   ├── AnularFactura.java
│   │   ├── ProcesarEnvioFiscal.java
│   │   ├── ReintentarEnvio.java
│   │   └── ConsultarEstadoFiscal.java
│   └── dto/
│       ├── EmitirFacturaCommand.java  ← datos que llegan desde Flutter
│       └── FiscalStatusResult.java   ← respuesta al TPV
│
├── interfaces/
│   ├── rest/
│   │   ├── InvoiceController.java     ← POST /api/v1/invoices
│   │   ├── FiscalStatusController.java
│   │   └── AdminController.java      ← reenvíos manuales
│   ├── dto/
│   │   ├── InvoiceRequestDto.java    ← JSON que envía Flutter
│   │   └── InvoiceResponseDto.java   ← JSON que recibe Flutter
│   └── mapper/
│       └── InvoiceMapper.java        ← DTO ↔ dominio
│
├── infra/
│   ├── db/
│   │   ├── InvoiceJpaRepository.java
│   │   ├── FiscalRecordJpaRepository.java
│   │   └── entity/                   ← entidades JPA (≠ entidades de dominio)
│   ├── xml/
│   │   ├── VerifactuXmlSerializer.java
│   │   └── TicketBaiXmlSerializer.java
│   ├── signature/
│   │   └── XmlSignatureService.java
│   └── outbox/
│       ├── OutboxMessage.java
│       ├── OutboxRepository.java
│       └── OutboxWorker.java          ← scheduler de envío
│
└── adapter/
    ├── aeat/
    │   └── AeatVerifactuAdapter.java
    └── tbai/
        ├── araba/
        │   └── TicketBaiArabaAdapter.java
        ├── bizkaia/
        │   └── TicketBaiBizkaiaAdapter.java
        └── gipuzkoa/
            └── TicketBaiGipuzkoaAdapter.java
```

> Esta estructura no existe todavía en el proyecto. Se irá creando capítulo a capítulo. Este árbol es el mapa de destino.

---

## 1.9 Entornos: desarrollo, pruebas y producción

El backend opera en tres entornos. Es crítico que nunca se mezclen, especialmente los certificados y los endpoints de las agencias tributarias.

| Parámetro | Desarrollo (local) | Pruebas (test) | Producción |
|-----------|-------------------|----------------|------------|
| Base de datos | Docker local | Servidor de test | Servidor dedicado |
| Certificado electrónico | Certificado de pruebas | Certificado de pruebas | Certificado real |
| AEAT endpoint | URL sandbox AEAT | URL sandbox AEAT | URL producción AEAT |
| TicketBAI endpoint | URL pruebas hacienda | URL pruebas hacienda | URL producción hacienda |
| Datos fiscales | Datos ficticios | Datos ficticios | Datos reales |

Spring Boot gestiona esto con **perfiles** (`application-dev.properties`, `application-test.properties`, `application-prod.properties`). Esto se configura en el Capítulo 9.

> **Regla de oro:** un certificado de producción no debe existir en ningún entorno de desarrollo ni de pruebas. Nunca.

---

## 1.10 Resumen y próximo capítulo

### Lo que cubre este capítulo

| Concepto | Documentado |
|----------|-------------|
| Responsabilidades del backend | ✅ |
| Visión general del sistema (Flutter + Backend + Agencias) | ✅ |
| Arquitectura hexagonal y sus tres capas | ✅ |
| Módulos y paquetes del proyecto | ✅ |
| Flujo completo de una factura (emisión → envío → reintento) | ✅ |
| Estados de un registro fiscal | ✅ |
| Decisiones técnicas justificadas | ✅ |
| Lo que el backend no hace | ✅ |
| Estructura de paquetes objetivo | ✅ |
| Gestión de entornos | ✅ |

### Próximo capítulo

El **Capítulo 2** cubre el modelo de dominio fiscal: las entidades Java que representan facturas, líneas, registros fiscales y empresas. Es la base sobre la que se construye todo lo demás.

Incluirá el código real de las primeras clases Java que escribirás en IntelliJ.

---

---

*NovaPay · Documentación Técnica Backend · Capítulo 1 · Versión 1.0 · Febrero 2026*

# NovaPay Backend
## Documentación Técnica Oficial

> **Capítulo:** 2 — Modelo de Dominio Fiscal  
> **Versión doc:** 1.0 · Febrero 2026  
> **Prerrequisito:** Capítulo 1 leído (arquitectura comprendida)  
> **Resultado:** Primeras clases Java escritas en IntelliJ

---

## Índice del Capítulo

- [2.1 Qué es el modelo de dominio](#21-qué-es-el-modelo-de-dominio)
- [2.2 Mapa de entidades y relaciones](#22-mapa-de-entidades-y-relaciones)
- [2.3 Enums fiscales](#23-enums-fiscales)
- [2.4 Value Objects](#24-value-objects)
- [2.5 Entidad Company](#25-entidad-company)
- [2.6 Entidad PosTerminal](#26-entidad-posterminal)
- [2.7 Entidad Invoice](#27-entidad-invoice)
- [2.8 Entidad InvoiceLine](#28-entidad-invoiceline)
- [2.9 Entidad TaxBreakdown](#29-entidad-taxbreakdown)
- [2.10 Entidad FiscalRecord](#210-entidad-fiscalrecord)
- [2.11 Interfaces de repositorio](#211-interfaces-de-repositorio)
- [2.12 Cómo crear los ficheros en IntelliJ](#212-cómo-crear-los-ficheros-en-intellij)
- [2.13 Resumen y próximo capítulo](#213-resumen-y-próximo-capítulo)

---

## 2.1 Qué es el modelo de dominio

El **modelo de dominio** es el conjunto de clases Java que representan los conceptos fiscales del negocio. Es el corazón del backend — todo lo demás (base de datos, API REST, XML fiscal) gira alrededor de estas clases.

### Principios que seguimos en este capítulo

**1. Dominio puro:** las clases del dominio no tienen anotaciones de Spring, ni de JPA, ni de Jackson. No dependen de ningún framework. Solo son Java.

**2. Inmutabilidad donde importa:** un `FiscalRecord` una vez creado no se modifica. Si hay un cambio, se crea uno nuevo. Esto es un requisito legal, no una preferencia técnica.

**3. Value Objects para datos con significado:** un NIF no es un `String` cualquiera. Un importe no es un `BigDecimal` cualquiera. Los encapsulamos en clases que garantizan su validez.

**4. Enums para estados y tipos:** ningún estado fiscal se representa como un `String` libre. Siempre un `enum` con valores controlados.

---

## 2.2 Mapa de entidades y relaciones

```
Company (empresa emisora)
    │
    │ 1 empresa tiene N terminales
    ▼
PosTerminal (terminal TPV)
    │
    │ 1 terminal emite N facturas
    ▼
Invoice (factura) ──────────────────────────────────────────┐
    │                                                        │
    │ 1 factura tiene N líneas       1 factura tiene N registros fiscales
    ▼                                                        ▼
InvoiceLine (línea)                               FiscalRecord (registro fiscal)
    │
    │ cada línea tiene su desglose fiscal
    ▼
TaxBreakdown (desglose IVA por tipo)
```

### Descripción de cada entidad

| Entidad | Qué representa |
|---------|---------------|
| `Company` | La empresa o autónomo que emite facturas. Tiene NIF, razón social, territorio fiscal. |
| `PosTerminal` | El dispositivo TPV desde donde se emite. Necesario para el encadenamiento fiscal. |
| `Invoice` | La factura de negocio. Tiene todos los datos comerciales y fiscales. |
| `InvoiceLine` | Cada producto o servicio dentro de la factura. |
| `TaxBreakdown` | El desglose del IVA: base imponible, tipo, cuota. Puede haber varios por factura. |
| `FiscalRecord` | El registro fiscal enviado a la agencia tributaria. Inmutable. Contiene hash, XML, estado. |

---

## 2.3 Enums fiscales

Los enums van en el paquete `com.novapay.backend.domain.enums`.

Crea la carpeta `enums` dentro de `domain` en IntelliJ.

---

### TaxAgency.java

Identifica a qué agencia tributaria corresponde un registro fiscal.

```java
package com.novapay.backend.domain.enums;

/**
 * Agencia tributaria receptora del registro fiscal.
 * Cada empresa tiene asignada una según su territorio fiscal.
 */
public enum TaxAgency {

    /** Agencia Estatal de Administración Tributaria — VERI*FACTU */
    AEAT,

    /** Hacienda Foral de Álava — TicketBAI */
    TBAI_ARABA,

    /** Hacienda Foral de Bizkaia — TicketBAI / BATUZ */
    TBAI_BIZKAIA,

    /** Hacienda Foral de Gipuzkoa — TicketBAI */
    TBAI_GIPUZKOA
}
```

---

### InvoiceType.java

Tipo de factura según la normativa fiscal española.

```java
package com.novapay.backend.domain.enums;

/**
 * Tipo de factura.
 * Determina los campos obligatorios y el flujo de emisión.
 */
public enum InvoiceType {

    /** Factura completa — incluye datos del destinatario */
    COMPLETA,

    /** Factura simplificada — ticket de venta sin datos del cliente */
    SIMPLIFICADA,

    /** Factura rectificativa — corrige una factura anterior */
    RECTIFICATIVA,

    /** Factura rectificativa simplificada */
    RECTIFICATIVA_SIMPLIFICADA
}
```

---

### RectificationType.java

Solo aplica cuando `InvoiceType` es `RECTIFICATIVA` o `RECTIFICATIVA_SIMPLIFICADA`.

```java
package com.novapay.backend.domain.enums;

/**
 * Método de rectificación de una factura.
 * Solo aplica a facturas de tipo RECTIFICATIVA.
 */
public enum RectificationType {

    /**
     * Por diferencias: la rectificativa contiene solo la diferencia
     * entre la factura original y la correcta.
     */
    DIFERENCIAS,

    /**
     * Por sustitución: la rectificativa anula completamente la original
     * y la sustituye por los nuevos valores.
     */
    SUSTITUCION
}
```

---

### FiscalRecordType.java

Tipo de operación que representa un registro fiscal.

```java
package com.novapay.backend.domain.enums;

/**
 * Tipo de registro fiscal enviado a la agencia tributaria.
 */
public enum FiscalRecordType {

    /** Alta de factura — registro principal de emisión */
    ALTA,

    /** Anulación de factura — deja sin efecto el alta */
    ANULACION,

    /** Subsanación — corrección de un registro rechazado */
    SUBSANACION
}
```

---

### FiscalStatus.java

Estado del registro fiscal a lo largo de su ciclo de vida.

```java
package com.novapay.backend.domain.enums;

/**
 * Estado de un FiscalRecord en su ciclo de vida.
 *
 * Ciclo normal:    PENDIENTE_ENVIO → ENVIANDO → ACEPTADO
 * Ciclo de error:  PENDIENTE_ENVIO → ENVIANDO → RECHAZADO  (error en datos)
 *                  PENDIENTE_ENVIO → ENVIANDO → REINTENTO   (error técnico)
 *                                               REINTENTO → ACEPTADO
 *                                               REINTENTO → ERROR_PERMANENTE
 */
public enum FiscalStatus {

    /** Factura creada. El envío está en cola. */
    PENDIENTE_ENVIO,

    /** El worker está procesando el envío en este momento. */
    ENVIANDO,

    /** La agencia ha confirmado y aceptado el registro. Estado final positivo. */
    ACEPTADO,

    /** La agencia ha rechazado el registro por error en los datos. Requiere corrección. */
    RECHAZADO,

    /** Fallo técnico (red, agencia caída). Programado para reintento automático. */
    REINTENTO,

    /** Superado el número máximo de reintentos. Requiere intervención manual. */
    ERROR_PERMANENTE,

    /** La factura fue anulada correctamente en la agencia. Estado final. */
    ANULADO
}
```

---

### InvoiceStatus.java

Estado de la factura desde el punto de vista del negocio (independiente del estado fiscal).

```java
package com.novapay.backend.domain.enums;

/**
 * Estado de negocio de una factura.
 * Independiente del estado fiscal (FiscalStatus).
 */
public enum InvoiceStatus {

    /** Factura emitida y válida. */
    EMITIDA,

    /** Factura anulada. Debe existir un FiscalRecord de tipo ANULACION. */
    ANULADA,

    /** Factura rectificada por otra posterior. */
    RECTIFICADA
}
```

---

### TaxType.java

Tipo de impuesto que puede aparecer en una línea de factura.

```java
package com.novapay.backend.domain.enums;

/**
 * Tipo de impuesto aplicable en una línea de factura.
 */
public enum TaxType {

    /** IVA — Impuesto sobre el Valor Añadido (régimen general) */
    IVA,

    /** IVA con recargo de equivalencia (comerciantes minoristas) */
    IVA_RECARGO_EQUIVALENCIA,

    /** Operación exenta de IVA */
    EXENTO,

    /** Operación no sujeta a IVA */
    NO_SUJETO,

    /** IPSI — Impuesto sobre la Producción, los Servicios y la Importación (Ceuta/Melilla) */
    IPSI,

    /** IGIC — Impuesto General Indirecto Canario */
    IGIC
}
```

---

## 2.4 Value Objects

Los Value Objects van en `com.novapay.backend.domain.valueObject`.

Un Value Object es una clase que representa un concepto con significado propio y reglas de validación internas. No tiene identidad propia (no tiene ID), solo valor.

---

### Money.java

Representa un importe monetario. Evita usar `BigDecimal` directamente en todo el código.

```java
package com.novapay.backend.domain.valueObject;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.Objects;

/**
 * Importe monetario con precisión fiscal.
 *
 * Siempre se trabaja con 2 decimales para importes finales
 * y hasta 6 decimales para precios unitarios intermedios.
 *
 * Uso:
 *   Money precio = Money.of("10.50");
 *   Money iva    = Money.of("2.205");
 *   Money total  = precio.add(iva);
 */
public final class Money {

    public static final Money ZERO = new Money(BigDecimal.ZERO);

    private final BigDecimal amount;

    private Money(BigDecimal amount) {
        this.amount = amount.setScale(6, RoundingMode.HALF_UP);
    }

    public static Money of(BigDecimal amount) {
        Objects.requireNonNull(amount, "El importe no puede ser nulo");
        if (amount.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("El importe no puede ser negativo: " + amount);
        }
        return new Money(amount);
    }

    public static Money of(String amount) {
        Objects.requireNonNull(amount, "El importe no puede ser nulo");
        return of(new BigDecimal(amount));
    }

    /** Importe redondeado a 2 decimales — para totales finales en facturas */
    public BigDecimal toFiscalScale() {
        return amount.setScale(2, RoundingMode.HALF_UP);
    }

    /** Importe con toda la precisión interna — para cálculos intermedios */
    public BigDecimal getValue() {
        return amount;
    }

    public Money add(Money other) {
        return new Money(this.amount.add(other.amount));
    }

    public Money subtract(Money other) {
        return new Money(this.amount.subtract(other.amount));
    }

    public Money multiply(BigDecimal factor) {
        return new Money(this.amount.multiply(factor));
    }

    public boolean isZero() {
        return amount.compareTo(BigDecimal.ZERO) == 0;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Money)) return false;
        Money money = (Money) o;
        return amount.compareTo(money.amount) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amount.stripTrailingZeros());
    }

    @Override
    public String toString() {
        return toFiscalScale().toPlainString();
    }
}
```

---

### TaxId.java

Representa un NIF/CIF/NIE validado. Nunca se trabaja con NIFs como `String` libre.

```java
package com.novapay.backend.domain.valueObject;

import java.util.Objects;
import java.util.regex.Pattern;

/**
 * Identificador fiscal español validado (NIF / CIF / NIE).
 *
 * Uso:
 *   TaxId nif = TaxId.of("B12345678");
 *   TaxId nie = TaxId.of("X1234567Z");
 */
public final class TaxId {

    // NIF persona física: 8 dígitos + letra
    private static final Pattern NIF_PATTERN = Pattern.compile("^\\d{8}[A-Z]$");
    // CIF persona jurídica: letra + 7 dígitos + letra/dígito
    private static final Pattern CIF_PATTERN = Pattern.compile("^[ABCDEFGHJKLMNPQRSUVW]\\d{7}[A-J0-9]$");
    // NIE extranjero: X/Y/Z + 7 dígitos + letra
    private static final Pattern NIE_PATTERN = Pattern.compile("^[XYZ]\\d{7}[A-Z]$");

    private final String value;

    private TaxId(String value) {
        this.value = value;
    }

    public static TaxId of(String value) {
        Objects.requireNonNull(value, "El NIF no puede ser nulo");
        String normalized = value.trim().toUpperCase();

        if (!isValid(normalized)) {
            throw new IllegalArgumentException("NIF/CIF/NIE con formato inválido: " + value);
        }

        return new TaxId(normalized);
    }

    private static boolean isValid(String value) {
        return NIF_PATTERN.matcher(value).matches()
                || CIF_PATTERN.matcher(value).matches()
                || NIE_PATTERN.matcher(value).matches();
    }

    public String getValue() {
        return value;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof TaxId)) return false;
        TaxId taxId = (TaxId) o;
        return Objects.equals(value, taxId.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(value);
    }

    @Override
    public String toString() {
        return value;
    }
}
```

---

### InvoiceNumber.java

Representa el número de factura compuesto por serie y número correlativo.

```java
package com.novapay.backend.domain.valueObject;

import java.util.Objects;

/**
 * Número de factura compuesto por serie y número correlativo.
 *
 * Ejemplo: serie="A", number=123 → "A-00123"
 *
 * La unicidad de serie+número por empresa es un requisito fiscal.
 * Se valida a nivel de caso de uso, no aquí.
 */
public final class InvoiceNumber {

    private final String series;
    private final int number;

    private InvoiceNumber(String series, int number) {
        this.series = series;
        this.number = number;
    }

    public static InvoiceNumber of(String series, int number) {
        Objects.requireNonNull(series, "La serie no puede ser nula");
        if (series.isBlank()) {
            throw new IllegalArgumentException("La serie no puede estar vacía");
        }
        if (number <= 0) {
            throw new IllegalArgumentException("El número de factura debe ser mayor que 0");
        }
        return new InvoiceNumber(series.toUpperCase().trim(), number);
    }

    public String getSeries() { return series; }
    public int getNumber() { return number; }

    /** Representación formateada: A-00123 */
    public String formatted() {
        return String.format("%s-%05d", series, number);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof InvoiceNumber)) return false;
        InvoiceNumber that = (InvoiceNumber) o;
        return number == that.number && Objects.equals(series, that.series);
    }

    @Override
    public int hashCode() {
        return Objects.hash(series, number);
    }

    @Override
    public String toString() {
        return formatted();
    }
}
```

---

## 2.5 Entidad Company

Representa la empresa o autónomo emisor de facturas.

```java
package com.novapay.backend.domain.entity;

import com.novapay.backend.domain.enums.TaxAgency;
import com.novapay.backend.domain.valueObject.TaxId;

import java.util.Objects;
import java.util.UUID;

/**
 * Empresa o autónomo emisor de facturas.
 *
 * Cada Company tiene asignada una TaxAgency que determina
 * qué adaptador fiscal se usará para sus facturas.
 */
public class Company {

    private final UUID id;
    private TaxId taxId;
    private String legalName;
    private String tradeName;
    private String address;
    private String postalCode;
    private String city;
    private TaxAgency taxAgency;
    private boolean active;

    // Constructor completo para reconstruir desde persistencia
    public Company(UUID id, TaxId taxId, String legalName, String tradeName,
                   String address, String postalCode, String city,
                   TaxAgency taxAgency, boolean active) {
        this.id = Objects.requireNonNull(id);
        this.taxId = Objects.requireNonNull(taxId);
        this.legalName = Objects.requireNonNull(legalName);
        this.tradeName = tradeName;
        this.address = address;
        this.postalCode = postalCode;
        this.city = city;
        this.taxAgency = Objects.requireNonNull(taxAgency);
        this.active = active;
    }

    // Factory method para crear una nueva empresa
    public static Company create(TaxId taxId, String legalName, String tradeName,
                                  String address, String postalCode, String city,
                                  TaxAgency taxAgency) {
        return new Company(
                UUID.randomUUID(),
                taxId, legalName, tradeName,
                address, postalCode, city,
                taxAgency, true
        );
    }

    // ── Getters ──────────────────────────────────────────────────────────────

    public UUID getId() { return id; }
    public TaxId getTaxId() { return taxId; }
    public String getLegalName() { return legalName; }
    public String getTradeName() { return tradeName != null ? tradeName : legalName; }
    public String getAddress() { return address; }
    public String getPostalCode() { return postalCode; }
    public String getCity() { return city; }
    public TaxAgency getTaxAgency() { return taxAgency; }
    public boolean isActive() { return active; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Company)) return false;
        Company company = (Company) o;
        return Objects.equals(id, company.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
```

---

## 2.6 Entidad PosTerminal

Representa el dispositivo TPV físico desde donde se emite.

```java
package com.novapay.backend.domain.entity;

import java.util.Objects;
import java.util.UUID;

/**
 * Terminal punto de venta (TPV).
 *
 * Necesario para el encadenamiento fiscal: cada terminal
 * mantiene su propio registro del último hash emitido.
 *
 * Un Company puede tener múltiples PosTerminals.
 */
public class PosTerminal {

    private final UUID id;
    private final UUID companyId;
    private String name;
    private String deviceId;       // identificador del dispositivo físico
    private String softwareVersion;
    private boolean active;

    public PosTerminal(UUID id, UUID companyId, String name,
                       String deviceId, String softwareVersion, boolean active) {
        this.id = Objects.requireNonNull(id);
        this.companyId = Objects.requireNonNull(companyId);
        this.name = Objects.requireNonNull(name);
        this.deviceId = deviceId;
        this.softwareVersion = softwareVersion;
        this.active = active;
    }

    public static PosTerminal create(UUID companyId, String name,
                                      String deviceId, String softwareVersion) {
        return new PosTerminal(
                UUID.randomUUID(),
                companyId, name, deviceId, softwareVersion, true
        );
    }

    public UUID getId() { return id; }
    public UUID getCompanyId() { return companyId; }
    public String getName() { return name; }
    public String getDeviceId() { return deviceId; }
    public String getSoftwareVersion() { return softwareVersion; }
    public boolean isActive() { return active; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof PosTerminal)) return false;
        PosTerminal that = (PosTerminal) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
```

---

## 2.7 Entidad Invoice

La entidad central del dominio. Representa una factura de negocio.

```java
package com.novapay.backend.domain.entity;

import com.novapay.backend.domain.enums.InvoiceStatus;
import com.novapay.backend.domain.enums.InvoiceType;
import com.novapay.backend.domain.enums.RectificationType;
import com.novapay.backend.domain.valueObject.InvoiceNumber;
import com.novapay.backend.domain.valueObject.Money;
import com.novapay.backend.domain.valueObject.TaxId;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Factura de negocio.
 *
 * Contiene todos los datos comerciales y fiscales de una transacción.
 * Es la entidad principal del dominio.
 *
 * Una Invoice tiene:
 *   - N InvoiceLine (líneas de detalle)
 *   - N TaxBreakdown (desglose por tipo de IVA)
 *   - N FiscalRecord (registros enviados a agencias tributarias)
 */
public class Invoice {

    private final UUID id;
    private final UUID companyId;
    private final UUID terminalId;

    // ── Identificación fiscal ─────────────────────────────────────────────
    private final InvoiceNumber invoiceNumber;
    private final InvoiceType invoiceType;
    private final LocalDateTime issueDateTime;   // fecha y hora de emisión
    private final LocalDate operationDate;        // fecha de operación (puede diferir)

    // ── Datos del cliente (obligatorio en factura completa) ───────────────
    private TaxId customerTaxId;
    private String customerName;
    private String customerAddress;

    // ── Rectificativa (solo si invoiceType es RECTIFICATIVA) ─────────────
    private RectificationType rectificationType;
    private List<InvoiceNumber> rectifiedInvoices;  // facturas que rectifica

    // ── Líneas y totales ──────────────────────────────────────────────────
    private final List<InvoiceLine> lines;
    private final List<TaxBreakdown> taxBreakdowns;
    private Money subtotal;      // suma de líneas antes de impuestos
    private Money totalTax;      // suma de todas las cuotas de impuesto
    private Money total;         // total final de la factura

    // ── Estado ────────────────────────────────────────────────────────────
    private InvoiceStatus status;
    private final LocalDateTime createdAt;

    // Constructor completo (usado al reconstruir desde base de datos)
    public Invoice(UUID id, UUID companyId, UUID terminalId,
                   InvoiceNumber invoiceNumber, InvoiceType invoiceType,
                   LocalDateTime issueDateTime, LocalDate operationDate,
                   TaxId customerTaxId, String customerName, String customerAddress,
                   RectificationType rectificationType, List<InvoiceNumber> rectifiedInvoices,
                   List<InvoiceLine> lines, List<TaxBreakdown> taxBreakdowns,
                   Money subtotal, Money totalTax, Money total,
                   InvoiceStatus status, LocalDateTime createdAt) {

        this.id = Objects.requireNonNull(id);
        this.companyId = Objects.requireNonNull(companyId);
        this.terminalId = Objects.requireNonNull(terminalId);
        this.invoiceNumber = Objects.requireNonNull(invoiceNumber);
        this.invoiceType = Objects.requireNonNull(invoiceType);
        this.issueDateTime = Objects.requireNonNull(issueDateTime);
        this.operationDate = operationDate != null ? operationDate : issueDateTime.toLocalDate();
        this.customerTaxId = customerTaxId;
        this.customerName = customerName;
        this.customerAddress = customerAddress;
        this.rectificationType = rectificationType;
        this.rectifiedInvoices = rectifiedInvoices != null
                ? new ArrayList<>(rectifiedInvoices) : new ArrayList<>();
        this.lines = new ArrayList<>(Objects.requireNonNull(lines));
        this.taxBreakdowns = new ArrayList<>(Objects.requireNonNull(taxBreakdowns));
        this.subtotal = Objects.requireNonNull(subtotal);
        this.totalTax = Objects.requireNonNull(totalTax);
        this.total = Objects.requireNonNull(total);
        this.status = Objects.requireNonNull(status);
        this.createdAt = Objects.requireNonNull(createdAt);
    }

    // Factory method para crear una factura nueva
    public static Invoice create(UUID companyId, UUID terminalId,
                                  InvoiceNumber invoiceNumber, InvoiceType invoiceType,
                                  LocalDateTime issueDateTime, LocalDate operationDate,
                                  TaxId customerTaxId, String customerName, String customerAddress,
                                  RectificationType rectificationType, List<InvoiceNumber> rectifiedInvoices,
                                  List<InvoiceLine> lines, List<TaxBreakdown> taxBreakdowns,
                                  Money subtotal, Money totalTax, Money total) {
        return new Invoice(
                UUID.randomUUID(), companyId, terminalId,
                invoiceNumber, invoiceType, issueDateTime, operationDate,
                customerTaxId, customerName, customerAddress,
                rectificationType, rectifiedInvoices,
                lines, taxBreakdowns,
                subtotal, totalTax, total,
                InvoiceStatus.EMITIDA, LocalDateTime.now()
        );
    }

    // ── Operaciones de dominio ─────────────────────────────────────────────

    /**
     * Marca la factura como anulada.
     * Solo se puede anular una factura EMITIDA.
     */
    public void cancel() {
        if (this.status != InvoiceStatus.EMITIDA) {
            throw new IllegalStateException(
                "Solo se puede anular una factura EMITIDA. Estado actual: " + this.status
            );
        }
        this.status = InvoiceStatus.ANULADA;
    }

    /**
     * Valida que la factura completa tiene los datos del cliente.
     * Debe llamarse antes de persistir.
     */
    public void validateFiscalRequirements() {
        if (invoiceType == InvoiceType.COMPLETA) {
            if (customerTaxId == null) {
                throw new IllegalStateException("La factura completa requiere NIF del cliente");
            }
            if (customerName == null || customerName.isBlank()) {
                throw new IllegalStateException("La factura completa requiere nombre del cliente");
            }
        }
        if (invoiceType == InvoiceType.RECTIFICATIVA
                || invoiceType == InvoiceType.RECTIFICATIVA_SIMPLIFICADA) {
            if (rectificationType == null) {
                throw new IllegalStateException("La rectificativa requiere tipo de rectificación");
            }
            if (rectifiedInvoices == null || rectifiedInvoices.isEmpty()) {
                throw new IllegalStateException("La rectificativa requiere al menos una factura rectificada");
            }
        }
    }

    // ── Getters ───────────────────────────────────────────────────────────

    public UUID getId() { return id; }
    public UUID getCompanyId() { return companyId; }
    public UUID getTerminalId() { return terminalId; }
    public InvoiceNumber getInvoiceNumber() { return invoiceNumber; }
    public InvoiceType getInvoiceType() { return invoiceType; }
    public LocalDateTime getIssueDateTime() { return issueDateTime; }
    public LocalDate getOperationDate() { return operationDate; }
    public TaxId getCustomerTaxId() { return customerTaxId; }
    public String getCustomerName() { return customerName; }
    public String getCustomerAddress() { return customerAddress; }
    public RectificationType getRectificationType() { return rectificationType; }
    public List<InvoiceNumber> getRectifiedInvoices() { return Collections.unmodifiableList(rectifiedInvoices); }
    public List<InvoiceLine> getLines() { return Collections.unmodifiableList(lines); }
    public List<TaxBreakdown> getTaxBreakdowns() { return Collections.unmodifiableList(taxBreakdowns); }
    public Money getSubtotal() { return subtotal; }
    public Money getTotalTax() { return totalTax; }
    public Money getTotal() { return total; }
    public InvoiceStatus getStatus() { return status; }
    public LocalDateTime getCreatedAt() { return createdAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Invoice)) return false;
        Invoice invoice = (Invoice) o;
        return Objects.equals(id, invoice.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
```

---

## 2.8 Entidad InvoiceLine

Cada línea de producto o servicio dentro de una factura.

```java
package com.novapay.backend.domain.entity;

import com.novapay.backend.domain.enums.TaxType;
import com.novapay.backend.domain.valueObject.Money;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * Línea de factura — un producto o servicio.
 *
 * El lineTotal debe coincidir con:
 *   quantity × unitPrice − discount + taxAmount
 * Esta validación se hace en el caso de uso, no aquí.
 */
public class InvoiceLine {

    private final UUID id;
    private final UUID invoiceId;
    private int lineOrder;          // posición en la factura (1, 2, 3...)
    private String productCode;     // código interno o SKU (opcional)
    private String description;
    private BigDecimal quantity;
    private Money unitPrice;
    private Money discount;
    private TaxType taxType;
    private BigDecimal taxRate;     // porcentaje: 21.00, 10.00, 4.00, 0.00
    private Money taxBase;          // base imponible de esta línea
    private Money taxAmount;        // cuota de esta línea
    private BigDecimal surchargeRate;  // recargo de equivalencia (si aplica)
    private Money surchargeAmount;
    private Money lineTotal;        // total de la línea (base + cuota + recargo)

    public InvoiceLine(UUID id, UUID invoiceId, int lineOrder,
                       String productCode, String description,
                       BigDecimal quantity, Money unitPrice, Money discount,
                       TaxType taxType, BigDecimal taxRate,
                       Money taxBase, Money taxAmount,
                       BigDecimal surchargeRate, Money surchargeAmount,
                       Money lineTotal) {
        this.id = Objects.requireNonNull(id);
        this.invoiceId = Objects.requireNonNull(invoiceId);
        this.lineOrder = lineOrder;
        this.productCode = productCode;
        this.description = Objects.requireNonNull(description);
        this.quantity = Objects.requireNonNull(quantity);
        this.unitPrice = Objects.requireNonNull(unitPrice);
        this.discount = discount != null ? discount : Money.ZERO;
        this.taxType = Objects.requireNonNull(taxType);
        this.taxRate = Objects.requireNonNull(taxRate);
        this.taxBase = Objects.requireNonNull(taxBase);
        this.taxAmount = Objects.requireNonNull(taxAmount);
        this.surchargeRate = surchargeRate;
        this.surchargeAmount = surchargeAmount != null ? surchargeAmount : Money.ZERO;
        this.lineTotal = Objects.requireNonNull(lineTotal);
    }

    public static InvoiceLine create(UUID invoiceId, int lineOrder,
                                      String productCode, String description,
                                      BigDecimal quantity, Money unitPrice, Money discount,
                                      TaxType taxType, BigDecimal taxRate,
                                      Money taxBase, Money taxAmount,
                                      BigDecimal surchargeRate, Money surchargeAmount,
                                      Money lineTotal) {
        return new InvoiceLine(
                UUID.randomUUID(), invoiceId, lineOrder,
                productCode, description,
                quantity, unitPrice, discount,
                taxType, taxRate,
                taxBase, taxAmount,
                surchargeRate, surchargeAmount,
                lineTotal
        );
    }

    public UUID getId() { return id; }
    public UUID getInvoiceId() { return invoiceId; }
    public int getLineOrder() { return lineOrder; }
    public String getProductCode() { return productCode; }
    public String getDescription() { return description; }
    public BigDecimal getQuantity() { return quantity; }
    public Money getUnitPrice() { return unitPrice; }
    public Money getDiscount() { return discount; }
    public TaxType getTaxType() { return taxType; }
    public BigDecimal getTaxRate() { return taxRate; }
    public Money getTaxBase() { return taxBase; }
    public Money getTaxAmount() { return taxAmount; }
    public BigDecimal getSurchargeRate() { return surchargeRate; }
    public Money getSurchargeAmount() { return surchargeAmount; }
    public Money getLineTotal() { return lineTotal; }
}
```

---

## 2.9 Entidad TaxBreakdown

Desglose del impuesto agrupado por tipo y porcentaje. Una factura puede tener varios (IVA 21%, IVA 10%, IVA 4%).

```java
package com.novapay.backend.domain.entity;

import com.novapay.backend.domain.enums.TaxType;
import com.novapay.backend.domain.valueObject.Money;

import java.math.BigDecimal;
import java.util.Objects;
import java.util.UUID;

/**
 * Desglose fiscal agrupado por tipo y porcentaje de impuesto.
 *
 * Ejemplo para una factura con dos tipos de IVA:
 *   TaxBreakdown(IVA, 21%, base=100.00, cuota=21.00)
 *   TaxBreakdown(IVA, 10%, base=50.00,  cuota=5.00)
 *
 * Las agencias tributarias exigen este desglose explícito en el XML.
 */
public class TaxBreakdown {

    private final UUID id;
    private final UUID invoiceId;
    private TaxType taxType;
    private BigDecimal taxRate;         // porcentaje: 21.00
    private Money taxBase;              // base imponible a este tipo
    private Money taxAmount;            // cuota (base × tipo / 100)
    private BigDecimal surchargeRate;   // recargo de equivalencia (si aplica)
    private Money surchargeAmount;      // cuota de recargo

    public TaxBreakdown(UUID id, UUID invoiceId,
                        TaxType taxType, BigDecimal taxRate,
                        Money taxBase, Money taxAmount,
                        BigDecimal surchargeRate, Money surchargeAmount) {
        this.id = Objects.requireNonNull(id);
        this.invoiceId = Objects.requireNonNull(invoiceId);
        this.taxType = Objects.requireNonNull(taxType);
        this.taxRate = Objects.requireNonNull(taxRate);
        this.taxBase = Objects.requireNonNull(taxBase);
        this.taxAmount = Objects.requireNonNull(taxAmount);
        this.surchargeRate = surchargeRate;
        this.surchargeAmount = surchargeAmount != null ? surchargeAmount : Money.ZERO;
    }

    public static TaxBreakdown create(UUID invoiceId,
                                       TaxType taxType, BigDecimal taxRate,
                                       Money taxBase, Money taxAmount,
                                       BigDecimal surchargeRate, Money surchargeAmount) {
        return new TaxBreakdown(
                UUID.randomUUID(), invoiceId,
                taxType, taxRate,
                taxBase, taxAmount,
                surchargeRate, surchargeAmount
        );
    }

    public UUID getId() { return id; }
    public UUID getInvoiceId() { return invoiceId; }
    public TaxType getTaxType() { return taxType; }
    public BigDecimal getTaxRate() { return taxRate; }
    public Money getTaxBase() { return taxBase; }
    public Money getTaxAmount() { return taxAmount; }
    public BigDecimal getSurchargeRate() { return surchargeRate; }
    public Money getSurchargeAmount() { return surchargeAmount; }
}
```

---

## 2.10 Entidad FiscalRecord

El registro fiscal es la entidad más crítica del sistema. Es **inmutable** una vez creado.

```java
package com.novapay.backend.domain.entity;

import com.novapay.backend.domain.enums.FiscalRecordType;
import com.novapay.backend.domain.enums.FiscalStatus;
import com.novapay.backend.domain.enums.TaxAgency;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

/**
 * Registro fiscal enviado a una agencia tributaria.
 *
 * INMUTABILIDAD: los campos fiscales (hash, xml, qr) no cambian
 * una vez establecidos. Solo el estado y los campos de respuesta
 * se actualizan durante el ciclo de vida del envío.
 *
 * Un Invoice puede tener múltiples FiscalRecord:
 *   - Uno de tipo ALTA (el original)
 *   - Uno de tipo ANULACION (si se anula)
 *   - Uno de tipo SUBSANACION (si el alta fue rechazada y se corrige)
 */
public class FiscalRecord {

    private final UUID id;
    private final UUID invoiceId;
    private final TaxAgency agency;
    private final FiscalRecordType recordType;

    // ── Datos fiscales inmutables ──────────────────────────────────────────
    private final String schemaVersion;     // versión del XSD usado: "1.0", "2.0"
    private final String previousHash;      // hash del registro anterior (encadenamiento)
    private final String currentHash;       // hash de este registro
    private final String xmlPayload;        // XML sin firmar
    private final String xmlSignedPayload;  // XML firmado (el que se envía)
    private final String qrContent;         // contenido del código QR
    private final LocalDateTime createdAt;

    // ── Datos del envío (se actualizan) ──────────────────────────────────
    private FiscalStatus status;
    private LocalDateTime sentAt;
    private String errorCode;
    private String errorMessage;
    private String responseRaw;             // respuesta completa de la agencia (XML/JSON)
    private String externalReference;       // código o CSV de la agencia
    private int retryCount;
    private LocalDateTime nextRetryAt;

    // Constructor completo
    public FiscalRecord(UUID id, UUID invoiceId,
                        TaxAgency agency, FiscalRecordType recordType,
                        String schemaVersion,
                        String previousHash, String currentHash,
                        String xmlPayload, String xmlSignedPayload,
                        String qrContent, LocalDateTime createdAt,
                        FiscalStatus status, LocalDateTime sentAt,
                        String errorCode, String errorMessage,
                        String responseRaw, String externalReference,
                        int retryCount, LocalDateTime nextRetryAt) {

        this.id = Objects.requireNonNull(id);
        this.invoiceId = Objects.requireNonNull(invoiceId);
        this.agency = Objects.requireNonNull(agency);
        this.recordType = Objects.requireNonNull(recordType);
        this.schemaVersion = Objects.requireNonNull(schemaVersion);
        this.previousHash = previousHash;   // puede ser null en el primer registro
        this.currentHash = Objects.requireNonNull(currentHash);
        this.xmlPayload = Objects.requireNonNull(xmlPayload);
        this.xmlSignedPayload = Objects.requireNonNull(xmlSignedPayload);
        this.qrContent = Objects.requireNonNull(qrContent);
        this.createdAt = Objects.requireNonNull(createdAt);
        this.status = Objects.requireNonNull(status);
        this.sentAt = sentAt;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.responseRaw = responseRaw;
        this.externalReference = externalReference;
        this.retryCount = retryCount;
        this.nextRetryAt = nextRetryAt;
    }

    // Factory: crea un registro nuevo en estado PENDIENTE_ENVIO
    public static FiscalRecord createPending(UUID invoiceId,
                                              TaxAgency agency, FiscalRecordType recordType,
                                              String schemaVersion,
                                              String previousHash, String currentHash,
                                              String xmlPayload, String xmlSignedPayload,
                                              String qrContent) {
        return new FiscalRecord(
                UUID.randomUUID(), invoiceId,
                agency, recordType,
                schemaVersion,
                previousHash, currentHash,
                xmlPayload, xmlSignedPayload,
                qrContent, LocalDateTime.now(),
                FiscalStatus.PENDIENTE_ENVIO, null,
                null, null, null, null,
                0, null
        );
    }

    // ── Operaciones de estado ──────────────────────────────────────────────

    public void markAsSending() {
        this.status = FiscalStatus.ENVIANDO;
        this.sentAt = LocalDateTime.now();
    }

    public void markAsAccepted(String externalReference, String responseRaw) {
        this.status = FiscalStatus.ACEPTADO;
        this.externalReference = externalReference;
        this.responseRaw = responseRaw;
    }

    public void markAsRejected(String errorCode, String errorMessage, String responseRaw) {
        this.status = FiscalStatus.RECHAZADO;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.responseRaw = responseRaw;
    }

    public void markForRetry(LocalDateTime nextRetryAt) {
        this.status = FiscalStatus.REINTENTO;
        this.retryCount++;
        this.nextRetryAt = nextRetryAt;
    }

    public void markAsPermanentError(String errorMessage) {
        this.status = FiscalStatus.ERROR_PERMANENTE;
        this.errorMessage = errorMessage;
    }

    // ── Getters ───────────────────────────────────────────────────────────

    public UUID getId() { return id; }
    public UUID getInvoiceId() { return invoiceId; }
    public TaxAgency getAgency() { return agency; }
    public FiscalRecordType getRecordType() { return recordType; }
    public String getSchemaVersion() { return schemaVersion; }
    public String getPreviousHash() { return previousHash; }
    public String getCurrentHash() { return currentHash; }
    public String getXmlPayload() { return xmlPayload; }
    public String getXmlSignedPayload() { return xmlSignedPayload; }
    public String getQrContent() { return qrContent; }
    public LocalDateTime getCreatedAt() { return createdAt; }
    public FiscalStatus getStatus() { return status; }
    public LocalDateTime getSentAt() { return sentAt; }
    public String getErrorCode() { return errorCode; }
    public String getErrorMessage() { return errorMessage; }
    public String getResponseRaw() { return responseRaw; }
    public String getExternalReference() { return externalReference; }
    public int getRetryCount() { return retryCount; }
    public LocalDateTime getNextRetryAt() { return nextRetryAt; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FiscalRecord)) return false;
        FiscalRecord that = (FiscalRecord) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
```

---

## 2.11 Interfaces de repositorio

Las interfaces de repositorio van en `com.novapay.backend.domain.port`.

Definen **qué** necesita el dominio para persistir datos, sin saber **cómo** se persiste (eso es responsabilidad de la infraestructura).

---

### InvoiceRepository.java

```java
package com.novapay.backend.domain.port;

import com.novapay.backend.domain.entity.Invoice;
import com.novapay.backend.domain.valueObject.InvoiceNumber;

import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de salida para persistencia de facturas.
 * La implementación está en infra.db.
 */
public interface InvoiceRepository {

    Invoice save(Invoice invoice);

    Optional<Invoice> findById(UUID id);

    /**
     * Busca una factura por empresa y número.
     * Usado para validar duplicidad antes de emitir.
     */
    Optional<Invoice> findByCompanyAndNumber(UUID companyId, InvoiceNumber invoiceNumber);

    boolean existsByCompanyAndNumber(UUID companyId, InvoiceNumber invoiceNumber);
}
```

---

### FiscalRecordRepository.java

```java
package com.novapay.backend.domain.port;

import com.novapay.backend.domain.enums.FiscalStatus;
import com.novapay.backend.domain.enums.TaxAgency;
import com.novapay.backend.domain.entity.FiscalRecord;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de salida para persistencia de registros fiscales.
 * La implementación está en infra.db.
 */
public interface FiscalRecordRepository {

    FiscalRecord save(FiscalRecord fiscalRecord);

    Optional<FiscalRecord> findById(UUID id);

    List<FiscalRecord> findByInvoiceId(UUID invoiceId);

    /**
     * Obtiene el último registro aceptado de una agencia para un terminal.
     * Necesario para calcular el hash de encadenamiento del siguiente registro.
     */
    Optional<FiscalRecord> findLastAcceptedByAgencyAndTerminal(TaxAgency agency, UUID terminalId);

    /**
     * Obtiene registros pendientes de envío o en reintento.
     * Usado por el OutboxWorker para procesar la cola de envío.
     */
    List<FiscalRecord> findPendingToSend(LocalDateTime now);

    List<FiscalRecord> findByStatus(FiscalStatus status);
}
```

---

### FiscalAgencyAdapter.java

La interfaz que deben implementar todos los adaptadores de agencias tributarias.

```java
package com.novapay.backend.domain.port;

import com.novapay.backend.domain.enums.TaxAgency;
import com.novapay.backend.domain.entity.FiscalRecord;
import com.novapay.backend.domain.entity.Invoice;

/**
 * Interfaz común para todos los adaptadores de agencias tributarias.
 *
 * Implementaciones:
 *   - AeatVerifactuAdapter      (adapter.aeat)
 *   - TicketBaiArabaAdapter     (adapter.tbai.araba)
 *   - TicketBaiBizkaiaAdapter   (adapter.tbai.bizkaia)
 *   - TicketBaiGipuzkoaAdapter  (adapter.tbai.gipuzkoa)
 */
public interface FiscalAgencyAdapter {

    /** Indica si este adaptador gestiona la agencia dada. */
    boolean supports(TaxAgency agency);

    /**
     * Genera el XML fiscal (sin firmar) para la factura.
     * El resultado se guardará en FiscalRecord.xmlPayload.
     */
    String buildXml(Invoice invoice, String previousHash);

    /**
     * Firma el XML generado con el certificado de la empresa.
     * El resultado se guardará en FiscalRecord.xmlSignedPayload.
     */
    String signXml(String xml, String companyTaxId);

    /**
     * Calcula el hash fiscal según el algoritmo de la agencia.
     * El resultado se guardará en FiscalRecord.currentHash.
     */
    String calculateHash(Invoice invoice, String previousHash);

    /**
     * Genera el contenido del código QR según la especificación de la agencia.
     * El resultado se guardará en FiscalRecord.qrContent.
     */
    String buildQrContent(Invoice invoice, String currentHash);

    /**
     * Envía el registro fiscal a la agencia.
     * Devuelve la respuesta cruda (XML o JSON) de la agencia.
     */
    AgencyResponse send(FiscalRecord fiscalRecord);

    /** Versión del esquema XSD que usa este adaptador. */
    String getSchemaVersion();
}
```

---

### AgencyResponse.java

El resultado del envío a la agencia.

```java
package com.novapay.backend.domain.port;

/**
 * Resultado del envío de un FiscalRecord a una agencia tributaria.
 */
public class AgencyResponse {

    private final boolean accepted;
    private final String externalReference;  // CSV o código de la agencia
    private final String errorCode;
    private final String errorMessage;
    private final String rawResponse;        // respuesta completa para auditoría

    private AgencyResponse(boolean accepted, String externalReference,
                            String errorCode, String errorMessage, String rawResponse) {
        this.accepted = accepted;
        this.externalReference = externalReference;
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.rawResponse = rawResponse;
    }

    public static AgencyResponse accepted(String externalReference, String rawResponse) {
        return new AgencyResponse(true, externalReference, null, null, rawResponse);
    }

    public static AgencyResponse rejected(String errorCode, String errorMessage, String rawResponse) {
        return new AgencyResponse(false, null, errorCode, errorMessage, rawResponse);
    }

    public boolean isAccepted() { return accepted; }
    public String getExternalReference() { return externalReference; }
    public String getErrorCode() { return errorCode; }
    public String getErrorMessage() { return errorMessage; }
    public String getRawResponse() { return rawResponse; }
}
```

---

## 2.12 Cómo crear los ficheros en IntelliJ

### Estructura de paquetes a crear

En IntelliJ, dentro de `src/main/java/com/novapay/backend/`, crea esta estructura de carpetas:

```
com/novapay/backend/
├── domain/
│   ├── enums/
│   │   ├── TaxAgency.java
│   │   ├── InvoiceType.java
│   │   ├── RectificationType.java
│   │   ├── FiscalRecordType.java
│   │   ├── FiscalStatus.java
│   │   ├── InvoiceStatus.java
│   │   └── TaxType.java
│   ├── valueobject/
│   │   ├── Money.java
│   │   ├── TaxId.java
│   │   └── InvoiceNumber.java
│   ├── model/
│   │   ├── Company.java
│   │   ├── PosTerminal.java
│   │   ├── Invoice.java
│   │   ├── InvoiceLine.java
│   │   ├── TaxBreakdown.java
│   │   └── FiscalRecord.java
│   └── port/
│       ├── InvoiceRepository.java
│       ├── FiscalRecordRepository.java
│       ├── FiscalAgencyAdapter.java
│       └── AgencyResponse.java
```

### Cómo crear un paquete en IntelliJ

1. Clic derecho sobre la carpeta `com/novapay/backend/` en el árbol de la izquierda
2. `New → Package`
3. Escribe el nombre completo: `com.novapay.backend.domain.enums`
4. IntelliJ crea la carpeta automáticamente

### Cómo crear una clase Java en IntelliJ

1. Clic derecho sobre el paquete donde quieres crear la clase
2. `New → Java Class`
3. Escribe el nombre de la clase (sin `.java`, IntelliJ lo añade solo)
4. Pega el código del capítulo

### Verificar que no hay errores

Una vez creados todos los ficheros, IntelliJ no debe mostrar ningún subrayado rojo. Si hay errores, son casi siempre imports que faltan — IntelliJ te los sugiere automáticamente con `Alt+Enter`.

---

## 2.13 Resumen y próximo capítulo

### Lo que has construido en este capítulo

| Fichero | Tipo | Propósito |
|---------|------|-----------|
| `TaxAgency.java` | Enum | Identifica la agencia tributaria |
| `InvoiceType.java` | Enum | Tipo de factura |
| `RectificationType.java` | Enum | Método de rectificación |
| `FiscalRecordType.java` | Enum | Tipo de registro fiscal |
| `FiscalStatus.java` | Enum | Estado del ciclo de vida fiscal |
| `InvoiceStatus.java` | Enum | Estado de negocio de la factura |
| `TaxType.java` | Enum | Tipo de impuesto |
| `Money.java` | Value Object | Importe monetario con precisión fiscal |
| `TaxId.java` | Value Object | NIF/CIF/NIE validado |
| `InvoiceNumber.java` | Value Object | Serie + número de factura |
| `Company.java` | Entidad | Empresa emisora |
| `PosTerminal.java` | Entidad | Terminal TPV |
| `Invoice.java` | Entidad | Factura de negocio |
| `InvoiceLine.java` | Entidad | Línea de factura |
| `TaxBreakdown.java` | Entidad | Desglose fiscal por tipo de IVA |
| `FiscalRecord.java` | Entidad | Registro fiscal inmutable |
| `InvoiceRepository.java` | Interfaz | Puerto de persistencia de facturas |
| `FiscalRecordRepository.java` | Interfaz | Puerto de persistencia fiscal |
| `FiscalAgencyAdapter.java` | Interfaz | Contrato de los adaptadores |
| `AgencyResponse.java` | DTO dominio | Resultado de envío a agencia |

### Importante: estas clases no tienen anotaciones de Spring ni de JPA

Están completamente limpias. En el Capítulo 3 crearemos las entidades JPA (en `infra.db`) que mapean estas clases a la base de datos, y en el Capítulo 4 los DTOs que exponen los datos a Flutter.

### Próximo capítulo

El **Capítulo 3** cubre la base de datos: el schema SQL completo de NovaPay, las entidades JPA que mapean el dominio a PostgreSQL, y las migraciones Flyway. Incluye el SQL real listo para ejecutar.

---

---

*NovaPay · Documentación Técnica Backend · Capítulo 2 · Versión 1.0 · Febrero 2026*

# NovaPay Backend
## Documentación Técnica Oficial

> **Capítulo:** 3 — Base de Datos · Schema SQL · JPA · Flyway  
> **Versión doc:** 1.0 · Febrero 2026  
> **Prerrequisito:** Capítulo 2 completado (dominio creado en IntelliJ)  
> **Resultado:** Schema SQL en PostgreSQL + entidades JPA + migraciones Flyway funcionando

---

## Índice del Capítulo

- [3.1 Estrategia de base de datos](#31-estrategia-de-base-de-datos)
- [3.2 Cómo funciona Flyway](#32-cómo-funciona-flyway)
- [3.3 Migración V1 — Schema completo](#33-migración-v1--schema-completo)
- [3.4 Migración V2 — Datos iniciales](#34-migración-v2--datos-iniciales)
- [3.5 Entidades JPA](#35-entidades-jpa)
- [3.6 Repositorios JPA](#36-repositorios-jpa)
- [3.7 Implementaciones de los puertos del dominio](#37-implementaciones-de-los-puertos-del-dominio)
- [3.8 Configuración de JPA](#38-configuración-de-jpa)
- [3.9 Cómo crear los ficheros en IntelliJ](#39-cómo-crear-los-ficheros-en-intellij)
- [3.10 Verificar que todo funciona](#310-verificar-que-todo-funciona)
- [3.11 Resumen y próximo capítulo](#311-resumen-y-próximo-capítulo)

---

## 3.1 Estrategia de base de datos

### Separación entre dominio y persistencia

En el Capítulo 2 creamos las entidades de dominio (`Invoice`, `FiscalRecord`, etc.) sin ninguna anotación JPA. En este capítulo crearemos un segundo conjunto de clases — las **entidades JPA** — que son las que realmente se mapean a las tablas de PostgreSQL.

Esta separación tiene una razón importante: las entidades de dominio representan conceptos fiscales y sus reglas. Las entidades JPA representan cómo se almacenan esos conceptos. Son cosas distintas y no deben mezclarse.

| | Entidades de dominio | Entidades JPA |
|--|----------------------|---------------|
| **Paquete** | `domain.entity` | `infra.db.entity` |
| **Anotaciones** | Ninguna | `@Entity`, `@Table`, `@Column`... |
| **Propósito** | Reglas de negocio | Persistencia en PostgreSQL |
| **Depende de** | Nada | Spring Data, Hibernate |

### Por qué Flyway y no `ddl-auto=create`

| Criterio | Flyway | `ddl-auto=create` |
|----------|---------|--------------------|
| Cambios versionados | ✅ Sí | ❌ No |
| Historial de cambios | ✅ Tabla `flyway_schema_history` | ❌ No existe |
| Reproducible en todos los entornos | ✅ Sí | ⚠️ Depende de las entidades |
| Seguro en producción | ✅ Sí | ❌ Puede borrar datos |
| Revisable antes de ejecutar | ✅ SQL explícito | ❌ Hibernate lo genera solo |

En un sistema fiscal, la base de datos es evidencia. Su estructura debe ser controlada, documentada y auditada. `ddl-auto=create` está prohibido en este proyecto fuera de pruebas unitarias aisladas.

---

## 3.2 Cómo funciona Flyway

Flyway busca ficheros SQL en `src/main/resources/db/migration/` con este formato de nombre:

```
V{número}__{descripción}.sql
```

Ejemplos:
```
V1__schema_inicial.sql
V2__datos_iniciales.sql
V3__add_column_invoice_currency.sql
```

Reglas importantes:

- El número de versión debe ser único y creciente
- El separador es **doble guion bajo** `__`
- Una vez ejecutada una migración, Flyway no la vuelve a ejecutar
- Si modificas un fichero ya ejecutado, Flyway lanzará un error de checksum — **nunca modifiques un fichero ya ejecutado**, crea uno nuevo

Flyway guarda el estado en la tabla `flyway_schema_history` que crea automáticamente.

---

## 3.3 Migración V1 — Schema completo

Crea el fichero en `src/main/resources/db/migration/V1__schema_inicial.sql`:

```sql
-- ============================================================
-- NovaPay · Schema inicial de base de datos
-- Versión: V1
-- Fecha: 2026-02-01
-- ============================================================

-- ── Extensiones ──────────────────────────────────────────────────────────────
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

-- ── Tipos enumerados ─────────────────────────────────────────────────────────

CREATE TYPE tax_agency AS ENUM (
    'AEAT',
    'TBAI_ARABA',
    'TBAI_BIZKAIA',
    'TBAI_GIPUZKOA'
);

CREATE TYPE invoice_type AS ENUM (
    'COMPLETA',
    'SIMPLIFICADA',
    'RECTIFICATIVA',
    'RECTIFICATIVA_SIMPLIFICADA'
);

CREATE TYPE rectification_type AS ENUM (
    'DIFERENCIAS',
    'SUSTITUCION'
);

CREATE TYPE invoice_status AS ENUM (
    'EMITIDA',
    'ANULADA',
    'RECTIFICADA'
);

CREATE TYPE fiscal_record_type AS ENUM (
    'ALTA',
    'ANULACION',
    'SUBSANACION'
);

CREATE TYPE fiscal_status AS ENUM (
    'PENDIENTE_ENVIO',
    'ENVIANDO',
    'ACEPTADO',
    'RECHAZADO',
    'REINTENTO',
    'ERROR_PERMANENTE',
    'ANULADO'
);

CREATE TYPE tax_type AS ENUM (
    'IVA',
    'IVA_RECARGO_EQUIVALENCIA',
    'EXENTO',
    'NO_SUJETO',
    'IPSI',
    'IGIC'
);

-- ── Tabla: company ────────────────────────────────────────────────────────────
-- Empresa o autónomo emisor de facturas.
CREATE TABLE company (
    id              UUID            PRIMARY KEY DEFAULT uuid_generate_v4(),
    tax_id          VARCHAR(20)     NOT NULL UNIQUE,
    legal_name      VARCHAR(250)    NOT NULL,
    trade_name      VARCHAR(250),
    address         VARCHAR(300),
    postal_code     VARCHAR(10),
    city            VARCHAR(100),
    tax_agency      tax_agency      NOT NULL,
    active          BOOLEAN         NOT NULL DEFAULT TRUE,
    created_at      TIMESTAMP       NOT NULL DEFAULT NOW(),
    updated_at      TIMESTAMP       NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE company IS 'Empresa o autónomo emisor de facturas. Cada company tiene asignada una agencia tributaria.';
COMMENT ON COLUMN company.tax_id IS 'NIF / CIF / NIE del emisor';
COMMENT ON COLUMN company.tax_agency IS 'Agencia tributaria a la que se envían los registros fiscales';

-- ── Tabla: pos_terminal ───────────────────────────────────────────────────────
-- Terminal TPV. Necesario para el encadenamiento fiscal por terminal.
CREATE TABLE pos_terminal (
    id                  UUID        PRIMARY KEY DEFAULT uuid_generate_v4(),
    company_id          UUID        NOT NULL REFERENCES company(id),
    name                VARCHAR(100) NOT NULL,
    device_id           VARCHAR(100),
    software_version    VARCHAR(50),
    active              BOOLEAN     NOT NULL DEFAULT TRUE,
    created_at          TIMESTAMP   NOT NULL DEFAULT NOW()
);

COMMENT ON TABLE pos_terminal IS 'Terminal TPV. Cada terminal mantiene su propio encadenamiento de hash fiscal.';
COMMENT ON COLUMN pos_terminal.device_id IS 'Identificador del dispositivo físico (UUID del móvil/tablet)';

CREATE INDEX idx_pos_terminal_company ON pos_terminal(company_id);

-- ── Tabla: invoice ────────────────────────────────────────────────────────────
-- Factura de negocio. Tabla principal del sistema.
CREATE TABLE invoice (
    id                      UUID                PRIMARY KEY DEFAULT uuid_generate_v4(),
    company_id              UUID                NOT NULL REFERENCES company(id),
    terminal_id             UUID                NOT NULL REFERENCES pos_terminal(id),

    -- Identificación fiscal
    invoice_series          VARCHAR(20)         NOT NULL,
    invoice_number          INTEGER             NOT NULL,
    invoice_type            invoice_type        NOT NULL,
    issue_datetime          TIMESTAMP           NOT NULL,
    operation_date          DATE                NOT NULL,

    -- Datos del cliente (obligatorio en factura completa)
    customer_tax_id         VARCHAR(20),
    customer_name           VARCHAR(250),
    customer_address        VARCHAR(300),

    -- Rectificativa
    rectification_type      rectification_type,
    rectified_invoices      TEXT,               -- JSON array de serie+número rectificados

    -- Totales (todos en EUR con 6 decimales internos, 2 en presentación)
    subtotal                NUMERIC(14, 6)      NOT NULL,
    total_tax               NUMERIC(14, 6)      NOT NULL,
    total                   NUMERIC(14, 6)      NOT NULL,

    -- Estado
    status                  invoice_status      NOT NULL DEFAULT 'EMITIDA',
    created_at              TIMESTAMP           NOT NULL DEFAULT NOW(),
    updated_at              TIMESTAMP           NOT NULL DEFAULT NOW(),

    -- Restricción de unicidad fiscal: serie+número únicos por empresa
    CONSTRAINT uq_invoice_company_number UNIQUE (company_id, invoice_series, invoice_number)
);

COMMENT ON TABLE invoice IS 'Factura de negocio. Tabla principal del sistema NovaPay.';
COMMENT ON COLUMN invoice.rectified_invoices IS 'Array JSON con las referencias de facturas rectificadas. Ej: [{"series":"A","number":100}]';

CREATE INDEX idx_invoice_company     ON invoice(company_id);
CREATE INDEX idx_invoice_terminal    ON invoice(terminal_id);
CREATE INDEX idx_invoice_status      ON invoice(status);
CREATE INDEX idx_invoice_issue_date  ON invoice(issue_datetime);

-- ── Tabla: invoice_line ───────────────────────────────────────────────────────
-- Líneas de detalle de la factura.
CREATE TABLE invoice_line (
    id                  UUID            PRIMARY KEY DEFAULT uuid_generate_v4(),
    invoice_id          UUID            NOT NULL REFERENCES invoice(id),
    line_order          INTEGER         NOT NULL,
    product_code        VARCHAR(100),
    description         VARCHAR(500)    NOT NULL,
    quantity            NUMERIC(14, 6)  NOT NULL,
    unit_price          NUMERIC(14, 6)  NOT NULL,
    discount            NUMERIC(14, 6)  NOT NULL DEFAULT 0,
    tax_type            tax_type        NOT NULL,
    tax_rate            NUMERIC(6, 4)   NOT NULL,   -- 21.0000, 10.0000, 4.0000
    tax_base            NUMERIC(14, 6)  NOT NULL,
    tax_amount          NUMERIC(14, 6)  NOT NULL,
    surcharge_rate      NUMERIC(6, 4),
    surcharge_amount    NUMERIC(14, 6)  NOT NULL DEFAULT 0,
    line_total          NUMERIC(14, 6)  NOT NULL,

    CONSTRAINT uq_invoice_line_order UNIQUE (invoice_id, line_order)
);

COMMENT ON TABLE invoice_line IS 'Líneas de producto o servicio de una factura.';

CREATE INDEX idx_invoice_line_invoice ON invoice_line(invoice_id);

-- ── Tabla: tax_breakdown ──────────────────────────────────────────────────────
-- Desglose fiscal por tipo de IVA. Las agencias exigen este desglose explícito.
CREATE TABLE tax_breakdown (
    id                  UUID            PRIMARY KEY DEFAULT uuid_generate_v4(),
    invoice_id          UUID            NOT NULL REFERENCES invoice(id),
    tax_type            tax_type        NOT NULL,
    tax_rate            NUMERIC(6, 4)   NOT NULL,
    tax_base            NUMERIC(14, 6)  NOT NULL,
    tax_amount          NUMERIC(14, 6)  NOT NULL,
    surcharge_rate      NUMERIC(6, 4),
    surcharge_amount    NUMERIC(14, 6)  NOT NULL DEFAULT 0
);

COMMENT ON TABLE tax_breakdown IS 'Desglose de impuestos agrupado por tipo y porcentaje. Requerido explícitamente por las agencias tributarias en el XML fiscal.';

CREATE INDEX idx_tax_breakdown_invoice ON tax_breakdown(invoice_id);

-- ── Tabla: fiscal_record ──────────────────────────────────────────────────────
-- Registro fiscal enviado a la agencia. Inmutable en sus campos fiscales.
-- Es la tabla más crítica del sistema.
CREATE TABLE fiscal_record (
    id                  UUID                PRIMARY KEY DEFAULT uuid_generate_v4(),
    invoice_id          UUID                NOT NULL REFERENCES invoice(id),
    agency              tax_agency          NOT NULL,
    record_type         fiscal_record_type  NOT NULL,

    -- Datos fiscales inmutables (no se modifican una vez creados)
    schema_version      VARCHAR(20)         NOT NULL,
    previous_hash       VARCHAR(512),       -- null solo en el primer registro del terminal
    current_hash        VARCHAR(512)        NOT NULL,
    xml_payload         TEXT                NOT NULL,   -- XML sin firmar
    xml_signed_payload  TEXT                NOT NULL,   -- XML firmado (el que se envía)
    qr_content          TEXT                NOT NULL,   -- contenido del QR
    created_at          TIMESTAMP           NOT NULL DEFAULT NOW(),

    -- Estado del envío (se actualiza durante el ciclo de vida)
    status              fiscal_status       NOT NULL DEFAULT 'PENDIENTE_ENVIO',
    sent_at             TIMESTAMP,
    error_code          VARCHAR(50),
    error_message       TEXT,
    response_raw        TEXT,               -- respuesta completa de la agencia
    external_reference  VARCHAR(200),       -- CSV o código de la agencia
    retry_count         INTEGER             NOT NULL DEFAULT 0,
    next_retry_at       TIMESTAMP
);

COMMENT ON TABLE fiscal_record IS 'Registro fiscal enviado a una agencia tributaria. Los campos fiscales son inmutables una vez creados. Solo el estado y los campos de respuesta se actualizan.';
COMMENT ON COLUMN fiscal_record.previous_hash IS 'Hash del registro anterior. NULL solo en el primer registro de cada terminal+agencia.';
COMMENT ON COLUMN fiscal_record.xml_payload IS 'XML fiscal generado antes de la firma. Se guarda para auditoría.';
COMMENT ON COLUMN fiscal_record.xml_signed_payload IS 'XML fiscal firmado digitalmente. Es el que se envía a la agencia.';
COMMENT ON COLUMN fiscal_record.response_raw IS 'Respuesta completa de la agencia en formato original (XML o JSON). Se guarda íntegra para auditoría.';

CREATE INDEX idx_fiscal_record_invoice     ON fiscal_record(invoice_id);
CREATE INDEX idx_fiscal_record_agency      ON fiscal_record(agency);
CREATE INDEX idx_fiscal_record_status      ON fiscal_record(status);
CREATE INDEX idx_fiscal_record_retry       ON fiscal_record(next_retry_at) WHERE status = 'REINTENTO';
CREATE INDEX idx_fiscal_record_pending     ON fiscal_record(created_at)    WHERE status = 'PENDIENTE_ENVIO';

-- ── Tabla: outbox_message ─────────────────────────────────────────────────────
-- Patrón Outbox: mensajes de envío fiscal pendientes de procesar.
-- Garantiza que ninguna factura se pierde aunque falle la red.
CREATE TABLE outbox_message (
    id                  UUID        PRIMARY KEY DEFAULT uuid_generate_v4(),
    fiscal_record_id    UUID        NOT NULL REFERENCES fiscal_record(id),
    created_at          TIMESTAMP   NOT NULL DEFAULT NOW(),
    processed_at        TIMESTAMP,
    processed           BOOLEAN     NOT NULL DEFAULT FALSE
);

COMMENT ON TABLE outbox_message IS 'Patrón Outbox. Garantiza el envío confiable de registros fiscales. Se inserta en la misma transacción que el fiscal_record.';

CREATE INDEX idx_outbox_pending ON outbox_message(created_at) WHERE processed = FALSE;

-- ── Tabla: audit_log ──────────────────────────────────────────────────────────
-- Auditoría de cambios de estado en facturas y registros fiscales.
CREATE TABLE audit_log (
    id              UUID        PRIMARY KEY DEFAULT uuid_generate_v4(),
    entity_type     VARCHAR(50) NOT NULL,   -- 'INVOICE' | 'FISCAL_RECORD'
    entity_id       UUID        NOT NULL,
    action          VARCHAR(50) NOT NULL,   -- 'CREATED' | 'STATUS_CHANGED' | 'SENT' | etc.
    old_value       TEXT,
    new_value       TEXT,
    performed_by    VARCHAR(100),           -- userId o 'SYSTEM'
    performed_at    TIMESTAMP   NOT NULL DEFAULT NOW(),
    details         JSONB                   -- información adicional en formato JSON
);

COMMENT ON TABLE audit_log IS 'Registro de auditoría de todos los cambios relevantes del sistema. No sustituye al fiscal_record como evidencia fiscal.';

CREATE INDEX idx_audit_log_entity   ON audit_log(entity_type, entity_id);
CREATE INDEX idx_audit_log_date     ON audit_log(performed_at);
```

---

## 3.4 Migración V2 — Datos iniciales

Crea el fichero `src/main/resources/db/migration/V2__datos_iniciales.sql`:

```sql
-- ============================================================
-- NovaPay · Datos iniciales para desarrollo
-- Versión: V2
-- Fecha: 2026-02-01
-- ATENCIÓN: Solo para entorno de desarrollo.
-- En producción, los datos de empresa se insertan por la API.
-- ============================================================

-- Empresa de ejemplo para desarrollo y pruebas
INSERT INTO company (
    id,
    tax_id,
    legal_name,
    trade_name,
    address,
    postal_code,
    city,
    tax_agency,
    active
) VALUES (
    'a0000000-0000-0000-0000-000000000001',
    'B12345678',
    'NovaPay Demo S.L.',
    'NovaPay Demo',
    'Calle Gran Vía 1',
    '28013',
    'Madrid',
    'AEAT',
    TRUE
);

-- Terminal de ejemplo para desarrollo y pruebas
INSERT INTO pos_terminal (
    id,
    company_id,
    name,
    device_id,
    software_version,
    active
) VALUES (
    'b0000000-0000-0000-0000-000000000001',
    'a0000000-0000-0000-0000-000000000001',
    'Terminal 1 - Mostrador',
    'dev-device-001',
    '1.0.0',
    TRUE
);
```

---

## 3.5 Entidades JPA

Las entidades JPA van en `com.novapay.backend.infra.db.entity`.

Son clases separadas de las entidades de dominio. Se mapean directamente a las tablas SQL.

---

### CompanyEntity.java

```java
package com.novapay.backend.infra.db.entity;

import com.novapay.backend.domain.enums.TaxAgency;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "company")
public class CompanyEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "tax_id", nullable = false, unique = true, length = 20)
    private String taxId;

    @Column(name = "legal_name", nullable = false, length = 250)
    private String legalName;

    @Column(name = "trade_name", length = 250)
    private String tradeName;

    @Column(name = "address", length = 300)
    private String address;

    @Column(name = "postal_code", length = 10)
    private String postalCode;

    @Column(name = "city", length = 100)
    private String city;

    @Enumerated(EnumType.STRING)
    @Column(name = "tax_agency", nullable = false)
    private TaxAgency taxAgency;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ── Getters y Setters ─────────────────────────────────────────────────

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public String getTaxId() { return taxId; }
    public void setTaxId(String taxId) { this.taxId = taxId; }

    public String getLegalName() { return legalName; }
    public void setLegalName(String legalName) { this.legalName = legalName; }

    public String getTradeName() { return tradeName; }
    public void setTradeName(String tradeName) { this.tradeName = tradeName; }

    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    public String getPostalCode() { return postalCode; }
    public void setPostalCode(String postalCode) { this.postalCode = postalCode; }

    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }

    public TaxAgency getTaxAgency() { return taxAgency; }
    public void setTaxAgency(TaxAgency taxAgency) { this.taxAgency = taxAgency; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
```

---

### PosTerminalEntity.java

```java
package com.novapay.backend.infra.db.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "pos_terminal")
public class PosTerminalEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "company_id", nullable = false)
    private UUID companyId;

    @Column(name = "name", nullable = false, length = 100)
    private String name;

    @Column(name = "device_id", length = 100)
    private String deviceId;

    @Column(name = "software_version", length = 50)
    private String softwareVersion;

    @Column(name = "active", nullable = false)
    private boolean active;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getCompanyId() { return companyId; }
    public void setCompanyId(UUID companyId) { this.companyId = companyId; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDeviceId() { return deviceId; }
    public void setDeviceId(String deviceId) { this.deviceId = deviceId; }

    public String getSoftwareVersion() { return softwareVersion; }
    public void setSoftwareVersion(String softwareVersion) { this.softwareVersion = softwareVersion; }

    public boolean isActive() { return active; }
    public void setActive(boolean active) { this.active = active; }

    public LocalDateTime getCreatedAt() { return createdAt; }
}
```

---

### InvoiceEntity.java

```java
package com.novapay.backend.infra.db.entity;

import com.novapay.backend.domain.enums.InvoiceStatus;
import com.novapay.backend.domain.enums.InvoiceType;
import com.novapay.backend.domain.enums.RectificationType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "invoice")
public class InvoiceEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "company_id", nullable = false)
    private UUID companyId;

    @Column(name = "terminal_id", nullable = false)
    private UUID terminalId;

    @Column(name = "invoice_series", nullable = false, length = 20)
    private String invoiceSeries;

    @Column(name = "invoice_number", nullable = false)
    private Integer invoiceNumber;

    @Enumerated(EnumType.STRING)
    @Column(name = "invoice_type", nullable = false)
    private InvoiceType invoiceType;

    @Column(name = "issue_datetime", nullable = false)
    private LocalDateTime issueDateTime;

    @Column(name = "operation_date", nullable = false)
    private LocalDate operationDate;

    @Column(name = "customer_tax_id", length = 20)
    private String customerTaxId;

    @Column(name = "customer_name", length = 250)
    private String customerName;

    @Column(name = "customer_address", length = 300)
    private String customerAddress;

    @Enumerated(EnumType.STRING)
    @Column(name = "rectification_type")
    private RectificationType rectificationType;

    @Column(name = "rectified_invoices", columnDefinition = "TEXT")
    private String rectifiedInvoices;  // JSON serializado

    @Column(name = "subtotal", nullable = false, precision = 14, scale = 6)
    private BigDecimal subtotal;

    @Column(name = "total_tax", nullable = false, precision = 14, scale = 6)
    private BigDecimal totalTax;

    @Column(name = "total", nullable = false, precision = 14, scale = 6)
    private BigDecimal total;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private InvoiceStatus status;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // ── Getters y Setters ─────────────────────────────────────────────────

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getCompanyId() { return companyId; }
    public void setCompanyId(UUID companyId) { this.companyId = companyId; }

    public UUID getTerminalId() { return terminalId; }
    public void setTerminalId(UUID terminalId) { this.terminalId = terminalId; }

    public String getInvoiceSeries() { return invoiceSeries; }
    public void setInvoiceSeries(String invoiceSeries) { this.invoiceSeries = invoiceSeries; }

    public Integer getInvoiceNumber() { return invoiceNumber; }
    public void setInvoiceNumber(Integer invoiceNumber) { this.invoiceNumber = invoiceNumber; }

    public InvoiceType getInvoiceType() { return invoiceType; }
    public void setInvoiceType(InvoiceType invoiceType) { this.invoiceType = invoiceType; }

    public LocalDateTime getIssueDateTime() { return issueDateTime; }
    public void setIssueDateTime(LocalDateTime issueDateTime) { this.issueDateTime = issueDateTime; }

    public LocalDate getOperationDate() { return operationDate; }
    public void setOperationDate(LocalDate operationDate) { this.operationDate = operationDate; }

    public String getCustomerTaxId() { return customerTaxId; }
    public void setCustomerTaxId(String customerTaxId) { this.customerTaxId = customerTaxId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerAddress() { return customerAddress; }
    public void setCustomerAddress(String customerAddress) { this.customerAddress = customerAddress; }

    public RectificationType getRectificationType() { return rectificationType; }
    public void setRectificationType(RectificationType rectificationType) { this.rectificationType = rectificationType; }

    public String getRectifiedInvoices() { return rectifiedInvoices; }
    public void setRectifiedInvoices(String rectifiedInvoices) { this.rectifiedInvoices = rectifiedInvoices; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public BigDecimal getTotalTax() { return totalTax; }
    public void setTotalTax(BigDecimal totalTax) { this.totalTax = totalTax; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public InvoiceStatus getStatus() { return status; }
    public void setStatus(InvoiceStatus status) { this.status = status; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public LocalDateTime getUpdatedAt() { return updatedAt; }
}
```

---

### FiscalRecordEntity.java

```java
package com.novapay.backend.infra.db.entity;

import com.novapay.backend.domain.enums.FiscalRecordType;
import com.novapay.backend.domain.enums.FiscalStatus;
import com.novapay.backend.domain.enums.TaxAgency;
import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "fiscal_record")
public class FiscalRecordEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "invoice_id", nullable = false)
    private UUID invoiceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "agency", nullable = false)
    private TaxAgency agency;

    @Enumerated(EnumType.STRING)
    @Column(name = "record_type", nullable = false)
    private FiscalRecordType recordType;

    @Column(name = "schema_version", nullable = false, length = 20)
    private String schemaVersion;

    @Column(name = "previous_hash", length = 512)
    private String previousHash;

    @Column(name = "current_hash", nullable = false, length = 512)
    private String currentHash;

    @Column(name = "xml_payload", nullable = false, columnDefinition = "TEXT")
    private String xmlPayload;

    @Column(name = "xml_signed_payload", nullable = false, columnDefinition = "TEXT")
    private String xmlSignedPayload;

    @Column(name = "qr_content", nullable = false, columnDefinition = "TEXT")
    private String qrContent;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private FiscalStatus status;

    @Column(name = "sent_at")
    private LocalDateTime sentAt;

    @Column(name = "error_code", length = 50)
    private String errorCode;

    @Column(name = "error_message", columnDefinition = "TEXT")
    private String errorMessage;

    @Column(name = "response_raw", columnDefinition = "TEXT")
    private String responseRaw;

    @Column(name = "external_reference", length = 200)
    private String externalReference;

    @Column(name = "retry_count", nullable = false)
    private int retryCount;

    @Column(name = "next_retry_at")
    private LocalDateTime nextRetryAt;

    @PrePersist
    protected void onCreate() {
        if (createdAt == null) createdAt = LocalDateTime.now();
        if (retryCount == 0) retryCount = 0;
    }

    // ── Getters y Setters ─────────────────────────────────────────────────

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getInvoiceId() { return invoiceId; }
    public void setInvoiceId(UUID invoiceId) { this.invoiceId = invoiceId; }

    public TaxAgency getAgency() { return agency; }
    public void setAgency(TaxAgency agency) { this.agency = agency; }

    public FiscalRecordType getRecordType() { return recordType; }
    public void setRecordType(FiscalRecordType recordType) { this.recordType = recordType; }

    public String getSchemaVersion() { return schemaVersion; }
    public void setSchemaVersion(String schemaVersion) { this.schemaVersion = schemaVersion; }

    public String getPreviousHash() { return previousHash; }
    public void setPreviousHash(String previousHash) { this.previousHash = previousHash; }

    public String getCurrentHash() { return currentHash; }
    public void setCurrentHash(String currentHash) { this.currentHash = currentHash; }

    public String getXmlPayload() { return xmlPayload; }
    public void setXmlPayload(String xmlPayload) { this.xmlPayload = xmlPayload; }

    public String getXmlSignedPayload() { return xmlSignedPayload; }
    public void setXmlSignedPayload(String xmlSignedPayload) { this.xmlSignedPayload = xmlSignedPayload; }

    public String getQrContent() { return qrContent; }
    public void setQrContent(String qrContent) { this.qrContent = qrContent; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public FiscalStatus getStatus() { return status; }
    public void setStatus(FiscalStatus status) { this.status = status; }

    public LocalDateTime getSentAt() { return sentAt; }
    public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }

    public String getErrorCode() { return errorCode; }
    public void setErrorCode(String errorCode) { this.errorCode = errorCode; }

    public String getErrorMessage() { return errorMessage; }
    public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }

    public String getResponseRaw() { return responseRaw; }
    public void setResponseRaw(String responseRaw) { this.responseRaw = responseRaw; }

    public String getExternalReference() { return externalReference; }
    public void setExternalReference(String externalReference) { this.externalReference = externalReference; }

    public int getRetryCount() { return retryCount; }
    public void setRetryCount(int retryCount) { this.retryCount = retryCount; }

    public LocalDateTime getNextRetryAt() { return nextRetryAt; }
    public void setNextRetryAt(LocalDateTime nextRetryAt) { this.nextRetryAt = nextRetryAt; }
}
```

---

### OutboxMessageEntity.java

```java
package com.novapay.backend.infra.db.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "outbox_message")
public class OutboxMessageEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "fiscal_record_id", nullable = false)
    private UUID fiscalRecordId;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "processed_at")
    private LocalDateTime processedAt;

    @Column(name = "processed", nullable = false)
    private boolean processed;

    @PrePersist
    protected void onCreate() {
        if (id == null) id = UUID.randomUUID();
        createdAt = LocalDateTime.now();
        processed = false;
    }

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getFiscalRecordId() { return fiscalRecordId; }
    public void setFiscalRecordId(UUID fiscalRecordId) { this.fiscalRecordId = fiscalRecordId; }

    public LocalDateTime getCreatedAt() { return createdAt; }

    public LocalDateTime getProcessedAt() { return processedAt; }
    public void setProcessedAt(LocalDateTime processedAt) { this.processedAt = processedAt; }

    public boolean isProcessed() { return processed; }
    public void setProcessed(boolean processed) { this.processed = processed; }
}
```

---

## 3.6 Repositorios JPA

Los repositorios JPA van en `com.novapay.backend.infra.db`.

Son interfaces de Spring Data que generan las consultas SQL automáticamente.

---

### InvoiceJpaRepository.java

```java
package com.novapay.backend.infra.db;

import com.novapay.backend.infra.db.entity.InvoiceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface InvoiceJpaRepository extends JpaRepository<InvoiceEntity, UUID> {

    Optional<InvoiceEntity> findByCompanyIdAndInvoiceSeriesAndInvoiceNumber(
            UUID companyId, String series, Integer number
    );

    boolean existsByCompanyIdAndInvoiceSeriesAndInvoiceNumber(
            UUID companyId, String series, Integer number
    );
}
```

---

### FiscalRecordJpaRepository.java

```java
package com.novapay.backend.infra.db;

import com.novapay.backend.domain.enums.FiscalStatus;
import com.novapay.backend.domain.enums.TaxAgency;
import com.novapay.backend.infra.db.entity.FiscalRecordEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface FiscalRecordJpaRepository extends JpaRepository<FiscalRecordEntity, UUID> {

    List<FiscalRecordEntity> findByInvoiceId(UUID invoiceId);

    List<FiscalRecordEntity> findByStatus(FiscalStatus status);

    /**
     * Último registro ACEPTADO de una agencia para un terminal dado.
     * Necesario para calcular el hash de encadenamiento.
     */
    @Query("""
        SELECT fr FROM FiscalRecordEntity fr
        JOIN InvoiceEntity i ON i.id = fr.invoiceId
        WHERE fr.agency = :agency
          AND i.terminalId = :terminalId
          AND fr.status = 'ACEPTADO'
        ORDER BY fr.createdAt DESC
        LIMIT 1
    """)
    Optional<FiscalRecordEntity> findLastAcceptedByAgencyAndTerminal(
            @Param("agency") TaxAgency agency,
            @Param("terminalId") UUID terminalId
    );

    /**
     * Registros pendientes de envío o con reintento programado para ahora.
     * Usado por el OutboxWorker.
     */
    @Query("""
        SELECT fr FROM FiscalRecordEntity fr
        WHERE fr.status = 'PENDIENTE_ENVIO'
           OR (fr.status = 'REINTENTO' AND fr.nextRetryAt <= :now)
        ORDER BY fr.createdAt ASC
    """)
    List<FiscalRecordEntity> findPendingToSend(@Param("now") LocalDateTime now);
}
```

---

### OutboxJpaRepository.java

```java
package com.novapay.backend.infra.db;

import com.novapay.backend.infra.db.entity.OutboxMessageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface OutboxJpaRepository extends JpaRepository<OutboxMessageEntity, UUID> {

    List<OutboxMessageEntity> findByProcessedFalseOrderByCreatedAtAsc();
}
```

---

## 3.7 Implementaciones de los puertos del dominio

Estas clases conectan el dominio con la infraestructura JPA. Implementan las interfaces definidas en `domain.port` usando los repositorios JPA.

Van en `com.novapay.backend.infra.db`.

---

### InvoiceRepositoryImpl.java

```java
package com.novapay.backend.infra.db;

import com.novapay.backend.domain.entity.Invoice;
import com.novapay.backend.domain.port.InvoiceRepository;
import com.novapay.backend.domain.valueObject.InvoiceNumber;
import com.novapay.backend.infra.db.entity.InvoiceEntity;
import com.novapay.backend.infra.db.mapper.InvoiceEntityMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class InvoiceRepositoryImpl implements InvoiceRepository {

    private final InvoiceJpaRepository jpaRepository;
    private final InvoiceEntityMapper mapper;

    public InvoiceRepositoryImpl(InvoiceJpaRepository jpaRepository,
                                  InvoiceEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Invoice save(Invoice invoice) {
        InvoiceEntity entity = mapper.toEntity(invoice);
        InvoiceEntity saved = jpaRepository.save(entity);
        return mapper.toDomain(saved);
    }

    @Override
    public Optional<Invoice> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Invoice> findByCompanyAndNumber(UUID companyId, InvoiceNumber invoiceNumber) {
        return jpaRepository
                .findByCompanyIdAndInvoiceSeriesAndInvoiceNumber(
                        companyId,
                        invoiceNumber.getSeries(),
                        invoiceNumber.getNumber()
                )
                .map(mapper::toDomain);
    }

    @Override
    public boolean existsByCompanyAndNumber(UUID companyId, InvoiceNumber invoiceNumber) {
        return jpaRepository.existsByCompanyIdAndInvoiceSeriesAndInvoiceNumber(
                companyId,
                invoiceNumber.getSeries(),
                invoiceNumber.getNumber()
        );
    }
}
```

---

### FiscalRecordRepositoryImpl.java

```java
package com.novapay.backend.infra.db;

import com.novapay.backend.domain.enums.FiscalStatus;
import com.novapay.backend.domain.enums.TaxAgency;
import com.novapay.backend.domain.entity.FiscalRecord;
import com.novapay.backend.domain.port.FiscalRecordRepository;
import com.novapay.backend.infra.db.mapper.FiscalRecordEntityMapper;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Component
public class FiscalRecordRepositoryImpl implements FiscalRecordRepository {

    private final FiscalRecordJpaRepository jpaRepository;
    private final FiscalRecordEntityMapper mapper;

    public FiscalRecordRepositoryImpl(FiscalRecordJpaRepository jpaRepository,
                                       FiscalRecordEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public FiscalRecord save(FiscalRecord fiscalRecord) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(fiscalRecord)));
    }

    @Override
    public Optional<FiscalRecord> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public List<FiscalRecord> findByInvoiceId(UUID invoiceId) {
        return jpaRepository.findByInvoiceId(invoiceId)
                .stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public Optional<FiscalRecord> findLastAcceptedByAgencyAndTerminal(
            TaxAgency agency, UUID terminalId) {
        return jpaRepository
                .findLastAcceptedByAgencyAndTerminal(agency, terminalId)
                .map(mapper::toDomain);
    }

    @Override
    public List<FiscalRecord> findPendingToSend(LocalDateTime now) {
        return jpaRepository.findPendingToSend(now)
                .stream().map(mapper::toDomain).collect(Collectors.toList());
    }

    @Override
    public List<FiscalRecord> findByStatus(FiscalStatus status) {
        return jpaRepository.findByStatus(status)
                .stream().map(mapper::toDomain).collect(Collectors.toList());
    }
}
```

---

### InvoiceEntityMapper.java

Convierte entre la entidad de dominio `Invoice` y la entidad JPA `InvoiceEntity`.

```java
package com.novapay.backend.infra.db.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.novapay.backend.domain.entity.Invoice;
import com.novapay.backend.domain.entity.InvoiceLine;
import com.novapay.backend.domain.entity.TaxBreakdown;
import com.novapay.backend.domain.valueObject.InvoiceNumber;
import com.novapay.backend.domain.valueObject.Money;
import com.novapay.backend.domain.valueObject.TaxId;
import com.novapay.backend.infra.db.entity.InvoiceEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class InvoiceEntityMapper {

    private final ObjectMapper objectMapper;

    public InvoiceEntityMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    public InvoiceEntity toEntity(Invoice domain) {
        InvoiceEntity entity = new InvoiceEntity();
        entity.setId(domain.getId());
        entity.setCompanyId(domain.getCompanyId());
        entity.setTerminalId(domain.getTerminalId());
        entity.setInvoiceSeries(domain.getInvoiceNumber().getSeries());
        entity.setInvoiceNumber(domain.getInvoiceNumber().getNumber());
        entity.setInvoiceType(domain.getInvoiceType());
        entity.setIssueDateTime(domain.getIssueDateTime());
        entity.setOperationDate(domain.getOperationDate());
        entity.setCustomerTaxId(domain.getCustomerTaxId() != null
                ? domain.getCustomerTaxId().getValue() : null);
        entity.setCustomerName(domain.getCustomerName());
        entity.setCustomerAddress(domain.getCustomerAddress());
        entity.setRectificationType(domain.getRectificationType());
        entity.setRectifiedInvoices(serializeRectifiedInvoices(domain.getRectifiedInvoices()));
        entity.setSubtotal(domain.getSubtotal().getValue());
        entity.setTotalTax(domain.getTotalTax().getValue());
        entity.setTotal(domain.getTotal().getValue());
        entity.setStatus(domain.getStatus());
        return entity;
    }

    public Invoice toDomain(InvoiceEntity entity) {
        return new Invoice(
                entity.getId(),
                entity.getCompanyId(),
                entity.getTerminalId(),
                InvoiceNumber.of(entity.getInvoiceSeries(), entity.getInvoiceNumber()),
                entity.getInvoiceType(),
                entity.getIssueDateTime(),
                entity.getOperationDate(),
                entity.getCustomerTaxId() != null ? TaxId.of(entity.getCustomerTaxId()) : null,
                entity.getCustomerName(),
                entity.getCustomerAddress(),
                entity.getRectificationType(),
                deserializeRectifiedInvoices(entity.getRectifiedInvoices()),
                Collections.emptyList(),   // líneas se cargan por separado si se necesitan
                Collections.emptyList(),   // breakdowns se cargan por separado
                Money.of(entity.getSubtotal()),
                Money.of(entity.getTotalTax()),
                Money.of(entity.getTotal()),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }

    private String serializeRectifiedInvoices(List<InvoiceNumber> invoices) {
        if (invoices == null || invoices.isEmpty()) return null;
        try {
            List<Map<String, Object>> list = invoices.stream()
                    .map(n -> Map.of("series", (Object) n.getSeries(), "number", n.getNumber()))
                    .toList();
            return objectMapper.writeValueAsString(list);
        } catch (Exception e) {
            return null;
        }
    }

    private List<InvoiceNumber> deserializeRectifiedInvoices(String json) {
        if (json == null || json.isBlank()) return Collections.emptyList();
        try {
            List<Map<String, Object>> list = objectMapper.readValue(
                    json, new TypeReference<>() {});
            return list.stream()
                    .map(m -> InvoiceNumber.of(
                            (String) m.get("series"),
                            (Integer) m.get("number")
                    ))
                    .toList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
```

---

### FiscalRecordEntityMapper.java

```java
package com.novapay.backend.infra.db.mapper;

import com.novapay.backend.domain.entity.FiscalRecord;
import com.novapay.backend.infra.db.entity.FiscalRecordEntity;
import org.springframework.stereotype.Component;

@Component
public class FiscalRecordEntityMapper {

    public FiscalRecordEntity toEntity(FiscalRecord domain) {
        FiscalRecordEntity entity = new FiscalRecordEntity();
        entity.setId(domain.getId());
        entity.setInvoiceId(domain.getInvoiceId());
        entity.setAgency(domain.getAgency());
        entity.setRecordType(domain.getRecordType());
        entity.setSchemaVersion(domain.getSchemaVersion());
        entity.setPreviousHash(domain.getPreviousHash());
        entity.setCurrentHash(domain.getCurrentHash());
        entity.setXmlPayload(domain.getXmlPayload());
        entity.setXmlSignedPayload(domain.getXmlSignedPayload());
        entity.setQrContent(domain.getQrContent());
        entity.setCreatedAt(domain.getCreatedAt());
        entity.setStatus(domain.getStatus());
        entity.setSentAt(domain.getSentAt());
        entity.setErrorCode(domain.getErrorCode());
        entity.setErrorMessage(domain.getErrorMessage());
        entity.setResponseRaw(domain.getResponseRaw());
        entity.setExternalReference(domain.getExternalReference());
        entity.setRetryCount(domain.getRetryCount());
        entity.setNextRetryAt(domain.getNextRetryAt());
        return entity;
    }

    public FiscalRecord toDomain(FiscalRecordEntity entity) {
        return new FiscalRecord(
                entity.getId(),
                entity.getInvoiceId(),
                entity.getAgency(),
                entity.getRecordType(),
                entity.getSchemaVersion(),
                entity.getPreviousHash(),
                entity.getCurrentHash(),
                entity.getXmlPayload(),
                entity.getXmlSignedPayload(),
                entity.getQrContent(),
                entity.getCreatedAt(),
                entity.getStatus(),
                entity.getSentAt(),
                entity.getErrorCode(),
                entity.getErrorMessage(),
                entity.getResponseRaw(),
                entity.getExternalReference(),
                entity.getRetryCount(),
                entity.getNextRetryAt()
        );
    }
}
```

---

## 3.8 Configuración de JPA

Actualiza `src/main/resources/application.properties` con esta configuración final:

```properties
# ── Aplicación ────────────────────────────────────────────
spring.application.name=novapay-backend

# ── Base de datos ─────────────────────────────────────────
spring.datasource.url=jdbc:postgresql://localhost:5432/novapay
spring.datasource.username=novapay
spring.datasource.password=novapay123
spring.datasource.driver-class-name=org.postgresql.Driver

# Pool de conexiones (HikariCP — incluido con Spring Boot)
spring.datasource.hikari.maximum-pool-size=10
spring.datasource.hikari.minimum-idle=2
spring.datasource.hikari.connection-timeout=30000

# ── JPA / Hibernate ───────────────────────────────────────
# validate: Hibernate verifica que el schema coincide con las entidades
# pero NO lo modifica. Flyway gestiona todos los cambios de schema.
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.properties.hibernate.format_sql=false
spring.jpa.open-in-view=false

# ── Flyway ────────────────────────────────────────────────
spring.flyway.enabled=true
spring.flyway.locations=classpath:db/migration
spring.flyway.baseline-on-migrate=true

# ── Servidor ──────────────────────────────────────────────
server.port=8080
```

---

## 3.9 Cómo crear los ficheros en IntelliJ

### Estructura completa a crear en este capítulo

```
src/main/
├── java/com/novapay/backend/
│   └── infra/
│       └── db/
│           ├── entity/
│           │   ├── CompanyEntity.java
│           │   ├── PosTerminalEntity.java
│           │   ├── InvoiceEntity.java
│           │   ├── FiscalRecordEntity.java
│           │   └── OutboxMessageEntity.java
│           ├── mapper/
│           │   ├── InvoiceEntityMapper.java
│           │   └── FiscalRecordEntityMapper.java
│           ├── InvoiceJpaRepository.java
│           ├── FiscalRecordJpaRepository.java
│           ├── OutboxJpaRepository.java
│           ├── InvoiceRepositoryImpl.java
│           └── FiscalRecordRepositoryImpl.java
└── resources/
    └── db/
        └── migration/
            ├── V1__schema_inicial.sql
            └── V2__datos_iniciales.sql
```

### Cómo crear los ficheros SQL de migración en IntelliJ

1. Clic derecho sobre `src/main/resources/`
2. `New → Directory` → escribe `db/migration`
3. IntelliJ crea la carpeta. Luego clic derecho sobre ella
4. `New → File` → escribe `V1__schema_inicial.sql`
5. Pega el SQL del apartado 3.3

> **Atención al nombre:** son dos guiones bajos `__` entre la versión y la descripción. Con uno solo Flyway no reconoce el fichero.

---

## 3.10 Verificar que todo funciona

### Paso 1 — Arranca el proyecto

En IntelliJ, ejecuta `NovapayBackendApplication`. En los logs debes ver:

```
Flyway Community Edition ... by Redgate
Database: jdbc:postgresql://localhost:5432/novapay
Successfully validated 2 migrations
Creating Schema History table "public"."flyway_schema_history"
Current version of schema "public": << Empty Schema >>
Migrating schema "public" to version "1 - schema inicial"
Migrating schema "public" to version "2 - datos iniciales"
Successfully applied 2 migrations to schema "public"
Started NovapayBackendApplication in x.xxx seconds
```

### Paso 2 — Verifica las tablas en PostgreSQL

Abre IntelliJ → pestaña **Database** (barra lateral derecha) → `+` → `Data Source` → `PostgreSQL`:

| Campo | Valor |
|-------|-------|
| Host | localhost |
| Port | 5432 |
| Database | novapay |
| User | novapay |
| Password | novapay123 |

Haz clic en **Test Connection** → debe aparecer "Successful". Luego en el árbol de la izquierda verás todas las tablas creadas.

### Paso 3 — Comprueba los datos de ejemplo

En la consola de IntelliJ Database ejecuta:

```sql
SELECT id, tax_id, legal_name, tax_agency FROM company;
-- Debe devolver la fila de 'NovaPay Demo S.L.'

SELECT id, name, device_id FROM pos_terminal;
-- Debe devolver el terminal de ejemplo
```

---

## 3.11 Resumen y próximo capítulo

### Lo que has construido en este capítulo

| Fichero | Tipo | Propósito |
|---------|------|-----------|
| `V1__schema_inicial.sql` | Migración Flyway | Crea todas las tablas, tipos e índices |
| `V2__datos_iniciales.sql` | Migración Flyway | Inserta datos de ejemplo para desarrollo |
| `CompanyEntity.java` | Entidad JPA | Mapea la tabla `company` |
| `PosTerminalEntity.java` | Entidad JPA | Mapea la tabla `pos_terminal` |
| `InvoiceEntity.java` | Entidad JPA | Mapea la tabla `invoice` |
| `FiscalRecordEntity.java` | Entidad JPA | Mapea la tabla `fiscal_record` |
| `OutboxMessageEntity.java` | Entidad JPA | Mapea la tabla `outbox_message` |
| `InvoiceJpaRepository.java` | Repositorio Spring Data | Consultas sobre `invoice` |
| `FiscalRecordJpaRepository.java` | Repositorio Spring Data | Consultas sobre `fiscal_record` |
| `OutboxJpaRepository.java` | Repositorio Spring Data | Consultas sobre `outbox_message` |
| `InvoiceRepositoryImpl.java` | Implementación de puerto | Conecta dominio con JPA |
| `FiscalRecordRepositoryImpl.java` | Implementación de puerto | Conecta dominio con JPA |
| `InvoiceEntityMapper.java` | Mapper | Convierte entre dominio y JPA |
| `FiscalRecordEntityMapper.java` | Mapper | Convierte entre dominio y JPA |

### Próximo capítulo

El **Capítulo 4** cubre la API REST: los endpoints que consumirá Flutter, los DTOs de entrada y salida, los controllers, las validaciones y el manejo de errores. Es la capa que conecta la app móvil con todo lo que hemos construido hasta ahora.

---

---

*NovaPay · Documentación Técnica Backend · Capítulo 3 · Versión 1.0 · Febrero 2026*

# NovaPay Backend
## Documentación Técnica Oficial

> **Capítulo:** 4 — API REST · Casos de Uso · DTOs · Controllers · Validaciones  
> **Versión doc:** 1.0 · Febrero 2026  
> **Prerrequisito:** Capítulos 2 y 3 completados (dominio + base de datos operativos)  
> **Resultado:** API REST funcional que Flutter puede consumir

---

## Índice del Capítulo

- [4.1 Visión general de la capa API](#41-visión-general-de-la-capa-api)
- [4.2 Correcciones retroactivas de capítulos anteriores](#42-correcciones-retroactivas-de-capítulos-anteriores)
- [4.3 Contrato de la API — endpoints disponibles](#43-contrato-de-la-api--endpoints-disponibles)
- [4.4 DTOs de entrada — lo que envía Flutter](#44-dtos-de-entrada--lo-que-envía-flutter)
- [4.5 DTOs de salida — lo que recibe Flutter](#45-dtos-de-salida--lo-que-recibe-flutter)
- [4.6 Casos de uso](#46-casos-de-uso)
- [4.7 Mappers de aplicación](#47-mappers-de-aplicación)
- [4.8 Controllers REST](#48-controllers-rest)
- [4.9 Manejo global de errores](#49-manejo-global-de-errores)
- [4.10 Excepciones de dominio](#410-excepciones-de-dominio)
- [4.11 Configuración de Spring Security (provisional)](#411-configuración-de-spring-security-provisional)
- [4.12 Estructura de ficheros de este capítulo](#412-estructura-de-ficheros-de-este-capítulo)
- [4.13 Verificar que todo funciona](#413-verificar-que-todo-funciona)
- [4.14 Resumen y próximo capítulo](#414-resumen-y-próximo-capítulo)

---

## 4.1 Visión general de la capa API

La capa API es la frontera entre Flutter y el backend. Su responsabilidad es:

1. Recibir el JSON de Flutter
2. Validar que el formato es correcto
3. Transformar el DTO en un comando del dominio
4. Invocar el caso de uso correspondiente
5. Transformar el resultado en un DTO de respuesta
6. Devolver el JSON a Flutter

La capa API **no contiene lógica de negocio**. Solo traduce y delega.

```
Flutter
  │  POST /api/v1/invoices  { JSON }
  ▼
InvoiceController          ← valida formato HTTP, llama al caso de uso
  ▼
EmitirFacturaUseCase       ← orquesta la lógica de negocio
  ├── InvoiceRepository    ← comprueba duplicados, guarda
  ├── CompanyRepository    ← obtiene la empresa y su agencia
  └── FiscalRecordRepository ← guarda el registro fiscal
  ▼
InvoiceController          ← transforma resultado en DTO
  │  { JSON respuesta }
  ▼
Flutter
```

---

## 4.2 Correcciones retroactivas de capítulos anteriores

Durante la preparación de este capítulo se detectaron dos interfaces de repositorio que faltan en el Capítulo 2. Son necesarias para los casos de uso. **Añade estos dos ficheros** al paquete `com.novapay.backend.domain.port`:

### CompanyRepository.java ← AÑADIR AL CAP.2

```java
package com.novapay.backend.domain.port;

import com.novapay.backend.domain.entity.Company;

import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de salida para persistencia de empresas.
 * Implementación en infra.db.CompanyRepositoryImpl.
 */
public interface CompanyRepository {

    Optional<Company> findById(UUID id);

    Optional<Company> findByTaxId(String taxId);

    Company save(Company company);
}
```

### PosTerminalRepository.java ← AÑADIR AL CAP.2

```java
package com.novapay.backend.domain.port;

import com.novapay.backend.domain.entity.PosTerminal;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

/**
 * Puerto de salida para persistencia de terminales TPV.
 * Implementación en infra.db.PosTerminalRepositoryImpl.
 */
public interface PosTerminalRepository {

    Optional<PosTerminal> findById(UUID id);

    List<PosTerminal> findByCompanyId(UUID companyId);

    PosTerminal save(PosTerminal terminal);
}
```

### CompanyJpaRepository.java ← AÑADIR AL CAP.3

Añade este repositorio JPA en `com.novapay.backend.infra.db`:

```java
package com.novapay.backend.infra.db;

import com.novapay.backend.infra.db.entity.CompanyEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface CompanyJpaRepository extends JpaRepository<CompanyEntity, UUID> {

    Optional<CompanyEntity> findByTaxId(String taxId);
}
```

### PosTerminalJpaRepository.java ← AÑADIR AL CAP.3

```java
package com.novapay.backend.infra.db;

import com.novapay.backend.infra.db.entity.PosTerminalEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface PosTerminalJpaRepository extends JpaRepository<PosTerminalEntity, UUID> {

    List<PosTerminalEntity> findByCompanyId(UUID companyId);
}
```

### CompanyEntityMapper.java ← AÑADIR AL CAP.3

```java
package com.novapay.backend.infra.db.mapper;

import com.novapay.backend.domain.entity.Company;
import com.novapay.backend.domain.valueObject.TaxId;
import com.novapay.backend.infra.db.entity.CompanyEntity;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class CompanyEntityMapper {

    public CompanyEntity toEntity(Company domain) {
        CompanyEntity entity = new CompanyEntity();
        entity.setId(domain.getId());
        entity.setTaxId(domain.getTaxId().getValue());
        entity.setLegalName(domain.getLegalName());
        entity.setTradeName(domain.getTradeName());
        entity.setAddress(domain.getAddress());
        entity.setPostalCode(domain.getPostalCode());
        entity.setCity(domain.getCity());
        entity.setTaxAgency(domain.getTaxAgency());
        entity.setActive(domain.isActive());
        return entity;
    }

    public Company toDomain(CompanyEntity entity) {
        return new Company(
                entity.getId(),
                TaxId.of(entity.getTaxId()),
                entity.getLegalName(),
                entity.getTradeName(),
                entity.getAddress(),
                entity.getPostalCode(),
                entity.getCity(),
                entity.getTaxAgency(),
                entity.isActive()
        );
    }
}
```

### CompanyRepositoryImpl.java ← AÑADIR AL CAP.3

```java
package com.novapay.backend.infra.db;

import com.novapay.backend.domain.entity.Company;
import com.novapay.backend.domain.port.CompanyRepository;
import com.novapay.backend.infra.db.mapper.CompanyEntityMapper;
import org.springframework.stereotype.Component;

import java.util.Optional;
import java.util.UUID;

@Component
public class CompanyRepositoryImpl implements CompanyRepository {

    private final CompanyJpaRepository jpaRepository;
    private final CompanyEntityMapper mapper;

    public CompanyRepositoryImpl(CompanyJpaRepository jpaRepository,
                                  CompanyEntityMapper mapper) {
        this.jpaRepository = jpaRepository;
        this.mapper = mapper;
    }

    @Override
    public Optional<Company> findById(UUID id) {
        return jpaRepository.findById(id).map(mapper::toDomain);
    }

    @Override
    public Optional<Company> findByTaxId(String taxId) {
        return jpaRepository.findByTaxId(taxId).map(mapper::toDomain);
    }

    @Override
    public Company save(Company company) {
        return mapper.toDomain(jpaRepository.save(mapper.toEntity(company)));
    }
}
```

---

## 4.3 Contrato de la API — endpoints disponibles

Todos los endpoints tienen el prefijo `/api/v1`.

| Método | Endpoint | Descripción | Caso de uso |
|--------|----------|-------------|-------------|
| `POST` | `/api/v1/invoices` | Emitir una factura nueva | `EmitirFacturaUseCase` |
| `POST` | `/api/v1/invoices/{id}/cancel` | Anular una factura | `AnularFacturaUseCase` |
| `GET` | `/api/v1/invoices/{id}` | Detalle completo de una factura | `ConsultarFacturaUseCase` |
| `GET` | `/api/v1/invoices/{id}/fiscal-status` | Estado fiscal de una factura | `ConsultarEstadoFiscalUseCase` |
| `GET` | `/api/v1/health` | Estado del backend (healthcheck) | — |

### Formato de respuesta estándar

Todas las respuestas siguen este envoltorio:

```json
{
  "success": true,
  "data": { ... },
  "error": null,
  "timestamp": "2026-02-01T10:30:00"
}
```

En caso de error:
```json
{
  "success": false,
  "data": null,
  "error": {
    "code": "INVOICE_DUPLICATE",
    "message": "Ya existe una factura con la serie A y número 123"
  },
  "timestamp": "2026-02-01T10:30:00"
}
```

---

## 4.4 DTOs de entrada — lo que envía Flutter

Los DTOs de entrada van en `com.novapay.backend.interfaces.dto.request`.

---

### EmitirFacturaRequestDto.java

```java
package com.novapay.backend.interfaces.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.*;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DTO de entrada para la emisión de una factura.
 * Es el JSON que envía Flutter al endpoint POST /api/v1/invoices.
 */
public class EmitirFacturaRequestDto {

    @NotNull(message = "El companyId es obligatorio")
    private UUID companyId;

    @NotNull(message = "El terminalId es obligatorio")
    private UUID terminalId;

    // ── Identificación fiscal ─────────────────────────────────────────────

    @NotBlank(message = "La serie es obligatoria")
    @Size(max = 20, message = "La serie no puede superar 20 caracteres")
    private String invoiceSeries;

    @NotNull(message = "El número de factura es obligatorio")
    @Min(value = 1, message = "El número de factura debe ser mayor que 0")
    private Integer invoiceNumber;

    @NotBlank(message = "El tipo de factura es obligatorio")
    private String invoiceType;  // COMPLETA | SIMPLIFICADA | RECTIFICATIVA | RECTIFICATIVA_SIMPLIFICADA

    @NotNull(message = "La fecha y hora de emisión es obligatoria")
    private LocalDateTime issueDateTime;

    private LocalDate operationDate;  // opcional — si null se usa la fecha de issueDateTime

    // ── Datos del cliente (obligatorio para facturas COMPLETA) ────────────

    private String customerTaxId;
    private String customerName;
    private String customerAddress;

    // ── Rectificativa (obligatorio si invoiceType = RECTIFICATIVA) ────────

    private String rectificationType;  // DIFERENCIAS | SUSTITUCION
    private List<RectifiedInvoiceDto> rectifiedInvoices;

    // ── Líneas ────────────────────────────────────────────────────────────

    @NotEmpty(message = "La factura debe tener al menos una línea")
    @Valid
    private List<InvoiceLineRequestDto> lines;

    // ── Totales ───────────────────────────────────────────────────────────

    @NotNull(message = "El subtotal es obligatorio")
    @DecimalMin(value = "0.00", message = "El subtotal no puede ser negativo")
    private BigDecimal subtotal;

    @NotNull(message = "El total de impuestos es obligatorio")
    @DecimalMin(value = "0.00", message = "El total de impuestos no puede ser negativo")
    private BigDecimal totalTax;

    @NotNull(message = "El total es obligatorio")
    @DecimalMin(value = "0.00", message = "El total no puede ser negativo")
    private BigDecimal total;

    // ── Desglose fiscal ───────────────────────────────────────────────────

    @NotEmpty(message = "El desglose fiscal es obligatorio")
    @Valid
    private List<TaxBreakdownRequestDto> taxBreakdowns;

    // ── Getters y Setters ─────────────────────────────────────────────────

    public UUID getCompanyId() { return companyId; }
    public void setCompanyId(UUID companyId) { this.companyId = companyId; }

    public UUID getTerminalId() { return terminalId; }
    public void setTerminalId(UUID terminalId) { this.terminalId = terminalId; }

    public String getInvoiceSeries() { return invoiceSeries; }
    public void setInvoiceSeries(String invoiceSeries) { this.invoiceSeries = invoiceSeries; }

    public Integer getInvoiceNumber() { return invoiceNumber; }
    public void setInvoiceNumber(Integer invoiceNumber) { this.invoiceNumber = invoiceNumber; }

    public String getInvoiceType() { return invoiceType; }
    public void setInvoiceType(String invoiceType) { this.invoiceType = invoiceType; }

    public LocalDateTime getIssueDateTime() { return issueDateTime; }
    public void setIssueDateTime(LocalDateTime issueDateTime) { this.issueDateTime = issueDateTime; }

    public LocalDate getOperationDate() { return operationDate; }
    public void setOperationDate(LocalDate operationDate) { this.operationDate = operationDate; }

    public String getCustomerTaxId() { return customerTaxId; }
    public void setCustomerTaxId(String customerTaxId) { this.customerTaxId = customerTaxId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public String getCustomerAddress() { return customerAddress; }
    public void setCustomerAddress(String customerAddress) { this.customerAddress = customerAddress; }

    public String getRectificationType() { return rectificationType; }
    public void setRectificationType(String rectificationType) { this.rectificationType = rectificationType; }

    public List<RectifiedInvoiceDto> getRectifiedInvoices() { return rectifiedInvoices; }
    public void setRectifiedInvoices(List<RectifiedInvoiceDto> rectifiedInvoices) { this.rectifiedInvoices = rectifiedInvoices; }

    public List<InvoiceLineRequestDto> getLines() { return lines; }
    public void setLines(List<InvoiceLineRequestDto> lines) { this.lines = lines; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public BigDecimal getTotalTax() { return totalTax; }
    public void setTotalTax(BigDecimal totalTax) { this.totalTax = totalTax; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public List<TaxBreakdownRequestDto> getTaxBreakdowns() { return taxBreakdowns; }
    public void setTaxBreakdowns(List<TaxBreakdownRequestDto> taxBreakdowns) { this.taxBreakdowns = taxBreakdowns; }

    // ── DTOs anidados ─────────────────────────────────────────────────────

    public static class RectifiedInvoiceDto {
        @NotBlank
        private String series;
        @NotNull @Min(1)
        private Integer number;

        public String getSeries() { return series; }
        public void setSeries(String series) { this.series = series; }
        public Integer getNumber() { return number; }
        public void setNumber(Integer number) { this.number = number; }
    }

    public static class InvoiceLineRequestDto {
        private String productCode;

        @NotBlank(message = "La descripción de la línea es obligatoria")
        @Size(max = 500)
        private String description;

        @NotNull @DecimalMin("0.001")
        private BigDecimal quantity;

        @NotNull @DecimalMin("0.00")
        private BigDecimal unitPrice;

        @DecimalMin("0.00")
        private BigDecimal discount;

        @NotBlank(message = "El tipo de impuesto es obligatorio")
        private String taxType;   // IVA | EXENTO | NO_SUJETO | ...

        @NotNull @DecimalMin("0.00") @DecimalMax("100.00")
        private BigDecimal taxRate;

        @NotNull @DecimalMin("0.00")
        private BigDecimal taxBase;

        @NotNull @DecimalMin("0.00")
        private BigDecimal taxAmount;

        @DecimalMin("0.00")
        private BigDecimal surchargeRate;

        @DecimalMin("0.00")
        private BigDecimal surchargeAmount;

        @NotNull @DecimalMin("0.00")
        private BigDecimal lineTotal;

        public String getProductCode() { return productCode; }
        public void setProductCode(String productCode) { this.productCode = productCode; }
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        public BigDecimal getQuantity() { return quantity; }
        public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
        public BigDecimal getUnitPrice() { return unitPrice; }
        public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
        public BigDecimal getDiscount() { return discount; }
        public void setDiscount(BigDecimal discount) { this.discount = discount; }
        public String getTaxType() { return taxType; }
        public void setTaxType(String taxType) { this.taxType = taxType; }
        public BigDecimal getTaxRate() { return taxRate; }
        public void setTaxRate(BigDecimal taxRate) { this.taxRate = taxRate; }
        public BigDecimal getTaxBase() { return taxBase; }
        public void setTaxBase(BigDecimal taxBase) { this.taxBase = taxBase; }
        public BigDecimal getTaxAmount() { return taxAmount; }
        public void setTaxAmount(BigDecimal taxAmount) { this.taxAmount = taxAmount; }
        public BigDecimal getSurchargeRate() { return surchargeRate; }
        public void setSurchargeRate(BigDecimal surchargeRate) { this.surchargeRate = surchargeRate; }
        public BigDecimal getSurchargeAmount() { return surchargeAmount; }
        public void setSurchargeAmount(BigDecimal surchargeAmount) { this.surchargeAmount = surchargeAmount; }
        public BigDecimal getLineTotal() { return lineTotal; }
        public void setLineTotal(BigDecimal lineTotal) { this.lineTotal = lineTotal; }
    }

    public static class TaxBreakdownRequestDto {
        @NotBlank
        private String taxType;
        @NotNull @DecimalMin("0.00") @DecimalMax("100.00")
        private BigDecimal taxRate;
        @NotNull @DecimalMin("0.00")
        private BigDecimal taxBase;
        @NotNull @DecimalMin("0.00")
        private BigDecimal taxAmount;
        @DecimalMin("0.00")
        private BigDecimal surchargeRate;
        @DecimalMin("0.00")
        private BigDecimal surchargeAmount;

        public String getTaxType() { return taxType; }
        public void setTaxType(String taxType) { this.taxType = taxType; }
        public BigDecimal getTaxRate() { return taxRate; }
        public void setTaxRate(BigDecimal taxRate) { this.taxRate = taxRate; }
        public BigDecimal getTaxBase() { return taxBase; }
        public void setTaxBase(BigDecimal taxBase) { this.taxBase = taxBase; }
        public BigDecimal getTaxAmount() { return taxAmount; }
        public void setTaxAmount(BigDecimal taxAmount) { this.taxAmount = taxAmount; }
        public BigDecimal getSurchargeRate() { return surchargeRate; }
        public void setSurchargeRate(BigDecimal surchargeRate) { this.surchargeRate = surchargeRate; }
        public BigDecimal getSurchargeAmount() { return surchargeAmount; }
        public void setSurchargeAmount(BigDecimal surchargeAmount) { this.surchargeAmount = surchargeAmount; }
    }
}
```

---

## 4.5 DTOs de salida — lo que recibe Flutter

Los DTOs de salida van en `com.novapay.backend.interfaces.dto.response`.

---

### ApiResponseDto.java

Envoltorio estándar para todas las respuestas.

```java
package com.novapay.backend.interfaces.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import java.time.LocalDateTime;

/**
 * Envoltorio estándar para todas las respuestas de la API.
 * Flutter siempre recibe este formato, tanto en éxito como en error.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiResponseDto<T> {

    private final boolean success;
    private final T data;
    private final ErrorDto error;
    private final LocalDateTime timestamp;

    private ApiResponseDto(boolean success, T data, ErrorDto error) {
        this.success = success;
        this.data = data;
        this.error = error;
        this.timestamp = LocalDateTime.now();
    }

    public static <T> ApiResponseDto<T> ok(T data) {
        return new ApiResponseDto<>(true, data, null);
    }

    public static <T> ApiResponseDto<T> error(String code, String message) {
        return new ApiResponseDto<>(false, null, new ErrorDto(code, message));
    }

    public boolean isSuccess() { return success; }
    public T getData() { return data; }
    public ErrorDto getError() { return error; }
    public LocalDateTime getTimestamp() { return timestamp; }

    public static class ErrorDto {
        private final String code;
        private final String message;

        public ErrorDto(String code, String message) {
            this.code = code;
            this.message = message;
        }

        public String getCode() { return code; }
        public String getMessage() { return message; }
    }
}
```

---

### InvoiceResponseDto.java

Respuesta completa al emitir o consultar una factura.

```java
package com.novapay.backend.interfaces.dto.response;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * DTO de respuesta con los datos completos de una factura.
 * Devuelto en POST /api/v1/invoices y GET /api/v1/invoices/{id}.
 */
public class InvoiceResponseDto {

    private UUID id;
    private UUID companyId;
    private UUID terminalId;
    private String invoiceNumber;       // formato legible: "A-00123"
    private String invoiceType;
    private LocalDateTime issueDateTime;
    private LocalDate operationDate;
    private String customerTaxId;
    private String customerName;
    private BigDecimal subtotal;
    private BigDecimal totalTax;
    private BigDecimal total;
    private String status;              // EMITIDA | ANULADA | RECTIFICADA
    private String fiscalStatus;        // PENDIENTE_ENVIO | ACEPTADO | RECHAZADO | ...
    private String qrContent;           // contenido del QR para mostrar en pantalla
    private LocalDateTime createdAt;
    private List<TaxBreakdownResponseDto> taxBreakdowns;

    // ── Getters y Setters ─────────────────────────────────────────────────

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }

    public UUID getCompanyId() { return companyId; }
    public void setCompanyId(UUID companyId) { this.companyId = companyId; }

    public UUID getTerminalId() { return terminalId; }
    public void setTerminalId(UUID terminalId) { this.terminalId = terminalId; }

    public String getInvoiceNumber() { return invoiceNumber; }
    public void setInvoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; }

    public String getInvoiceType() { return invoiceType; }
    public void setInvoiceType(String invoiceType) { this.invoiceType = invoiceType; }

    public LocalDateTime getIssueDateTime() { return issueDateTime; }
    public void setIssueDateTime(LocalDateTime issueDateTime) { this.issueDateTime = issueDateTime; }

    public LocalDate getOperationDate() { return operationDate; }
    public void setOperationDate(LocalDate operationDate) { this.operationDate = operationDate; }

    public String getCustomerTaxId() { return customerTaxId; }
    public void setCustomerTaxId(String customerTaxId) { this.customerTaxId = customerTaxId; }

    public String getCustomerName() { return customerName; }
    public void setCustomerName(String customerName) { this.customerName = customerName; }

    public BigDecimal getSubtotal() { return subtotal; }
    public void setSubtotal(BigDecimal subtotal) { this.subtotal = subtotal; }

    public BigDecimal getTotalTax() { return totalTax; }
    public void setTotalTax(BigDecimal totalTax) { this.totalTax = totalTax; }

    public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }

    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    public String getFiscalStatus() { return fiscalStatus; }
    public void setFiscalStatus(String fiscalStatus) { this.fiscalStatus = fiscalStatus; }

    public String getQrContent() { return qrContent; }
    public void setQrContent(String qrContent) { this.qrContent = qrContent; }

    public LocalDateTime getCreatedAt() { return createdAt; }
    public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }

    public List<TaxBreakdownResponseDto> getTaxBreakdowns() { return taxBreakdowns; }
    public void setTaxBreakdowns(List<TaxBreakdownResponseDto> taxBreakdowns) { this.taxBreakdowns = taxBreakdowns; }

    // ── DTO anidado ───────────────────────────────────────────────────────

    public static class TaxBreakdownResponseDto {
        private String taxType;
        private BigDecimal taxRate;
        private BigDecimal taxBase;
        private BigDecimal taxAmount;
        private BigDecimal surchargeRate;
        private BigDecimal surchargeAmount;

        public String getTaxType() { return taxType; }
        public void setTaxType(String taxType) { this.taxType = taxType; }
        public BigDecimal getTaxRate() { return taxRate; }
        public void setTaxRate(BigDecimal taxRate) { this.taxRate = taxRate; }
        public BigDecimal getTaxBase() { return taxBase; }
        public void setTaxBase(BigDecimal taxBase) { this.taxBase = taxBase; }
        public BigDecimal getTaxAmount() { return taxAmount; }
        public void setTaxAmount(BigDecimal taxAmount) { this.taxAmount = taxAmount; }
        public BigDecimal getSurchargeRate() { return surchargeRate; }
        public void setSurchargeRate(BigDecimal surchargeRate) { this.surchargeRate = surchargeRate; }
        public BigDecimal getSurchargeAmount() { return surchargeAmount; }
        public void setSurchargeAmount(BigDecimal surchargeAmount) { this.surchargeAmount = surchargeAmount; }
    }
}
```

---

### FiscalStatusResponseDto.java

Respuesta al consultar el estado fiscal de una factura.

```java
package com.novapay.backend.interfaces.dto.response;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

/**
 * Estado fiscal detallado de una factura.
 * Devuelto en GET /api/v1/invoices/{id}/fiscal-status.
 */
public class FiscalStatusResponseDto {

    private UUID invoiceId;
    private String invoiceNumber;
    private String invoiceStatus;

    /** Lista de registros fiscales — puede haber más de uno (alta + anulación, etc.) */
    private List<FiscalRecordSummaryDto> fiscalRecords;

    public UUID getInvoiceId() { return invoiceId; }
    public void setInvoiceId(UUID invoiceId) { this.invoiceId = invoiceId; }

    public String getInvoiceNumber() { return invoiceNumber; }
    public void setInvoiceNumber(String invoiceNumber) { this.invoiceNumber = invoiceNumber; }

    public String getInvoiceStatus() { return invoiceStatus; }
    public void setInvoiceStatus(String invoiceStatus) { this.invoiceStatus = invoiceStatus; }

    public List<FiscalRecordSummaryDto> getFiscalRecords() { return fiscalRecords; }
    public void setFiscalRecords(List<FiscalRecordSummaryDto> fiscalRecords) { this.fiscalRecords = fiscalRecords; }

    public static class FiscalRecordSummaryDto {
        private UUID id;
        private String agency;          // AEAT | TBAI_ARABA | ...
        private String recordType;      // ALTA | ANULACION | SUBSANACION
        private String status;          // PENDIENTE_ENVIO | ACEPTADO | RECHAZADO | ...
        private String externalReference;
        private String errorCode;
        private String errorMessage;
        private int retryCount;
        private LocalDateTime sentAt;
        private LocalDateTime createdAt;

        public UUID getId() { return id; }
        public void setId(UUID id) { this.id = id; }
        public String getAgency() { return agency; }
        public void setAgency(String agency) { this.agency = agency; }
        public String getRecordType() { return recordType; }
        public void setRecordType(String recordType) { this.recordType = recordType; }
        public String getStatus() { return status; }
        public void setStatus(String status) { this.status = status; }
        public String getExternalReference() { return externalReference; }
        public void setExternalReference(String externalReference) { this.externalReference = externalReference; }
        public String getErrorCode() { return errorCode; }
        public void setErrorCode(String errorCode) { this.errorCode = errorCode; }
        public String getErrorMessage() { return errorMessage; }
        public void setErrorMessage(String errorMessage) { this.errorMessage = errorMessage; }
        public int getRetryCount() { return retryCount; }
        public void setRetryCount(int retryCount) { this.retryCount = retryCount; }
        public LocalDateTime getSentAt() { return sentAt; }
        public void setSentAt(LocalDateTime sentAt) { this.sentAt = sentAt; }
        public LocalDateTime getCreatedAt() { return createdAt; }
        public void setCreatedAt(LocalDateTime createdAt) { this.createdAt = createdAt; }
    }
}
```

---

## 4.6 Casos de uso

Los casos de uso van en `com.novapay.backend.application.usecase`.

Cada caso de uso orquesta una operación completa de negocio. No contienen lógica de persistencia ni de presentación.

---

### EmitirFacturaUseCase.java

```java
package com.novapay.backend.application.usecase;

import com.novapay.backend.domain.enums.FiscalRecordType;
import com.novapay.backend.domain.enums.InvoiceType;
import com.novapay.backend.domain.enums.RectificationType;
import com.novapay.backend.domain.enums.TaxType;
import com.novapay.backend.domain.entity.*;
import com.novapay.backend.domain.port.*;
import com.novapay.backend.domain.valueObject.InvoiceNumber;
import com.novapay.backend.domain.valueObject.Money;
import com.novapay.backend.domain.valueObject.TaxId;
import com.novapay.backend.interfaces.dto.request.EmitirFacturaRequestDto;
import com.novapay.backend.interfaces.dto.response.InvoiceResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Caso de uso: emitir una factura nueva.
 *
 * Orquesta:
 *   1. Validación de negocio (empresa existe, terminal válido, no duplicado)
 *   2. Construcción de la entidad Invoice
 *   3. Generación del FiscalRecord en estado PENDIENTE_ENVIO
 *      (el XML, hash, firma y QR reales se generan en el OutboxWorker — Cap.5)
 *   4. Persistencia en una sola transacción
 *   5. Respuesta a Flutter con estado inmediato
 */
@Service
public class EmitirFacturaUseCase {

    private final InvoiceRepository invoiceRepository;
    private final FiscalRecordRepository fiscalRecordRepository;
    private final CompanyRepository companyRepository;
    private final PosTerminalRepository posTerminalRepository;
    private final InvoiceMapper invoiceMapper;

    public EmitirFacturaUseCase(InvoiceRepository invoiceRepository,
                                 FiscalRecordRepository fiscalRecordRepository,
                                 CompanyRepository companyRepository,
                                 PosTerminalRepository posTerminalRepository,
                                 InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.fiscalRecordRepository = fiscalRecordRepository;
        this.companyRepository = companyRepository;
        this.posTerminalRepository = posTerminalRepository;
        this.invoiceMapper = invoiceMapper;
    }

    @Transactional
    public InvoiceResponseDto execute(EmitirFacturaRequestDto request) {

        // ── 1. Validar que la empresa existe y está activa ────────────────
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Empresa no encontrada: " + request.getCompanyId()));

        if (!company.isActive()) {
            throw new BusinessRuleException("COMPANY_INACTIVE",
                    "La empresa no está activa: " + request.getCompanyId());
        }

        // ── 2. Validar que el terminal existe y pertenece a la empresa ────
        PosTerminal terminal = posTerminalRepository.findById(request.getTerminalId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Terminal no encontrado: " + request.getTerminalId()));

        if (!terminal.getCompanyId().equals(request.getCompanyId())) {
            throw new BusinessRuleException("TERMINAL_COMPANY_MISMATCH",
                    "El terminal no pertenece a la empresa indicada");
        }

        // ── 3. Validar unicidad de serie+número ───────────────────────────
        InvoiceNumber invoiceNumber = InvoiceNumber.of(
                request.getInvoiceSeries(), request.getInvoiceNumber());

        if (invoiceRepository.existsByCompanyAndNumber(company.getId(), invoiceNumber)) {
            throw new DuplicateInvoiceException(
                    request.getInvoiceSeries(), request.getInvoiceNumber());
        }

        // ── 4. Construir las líneas ───────────────────────────────────────
        List<InvoiceLine> lines = buildLines(request);

        // ── 5. Construir los desgloses fiscales ───────────────────────────
        List<TaxBreakdown> breakdowns = buildBreakdowns(request);

        // ── 6. Construir la entidad Invoice ───────────────────────────────
        InvoiceType invoiceType = InvoiceType.valueOf(request.getInvoiceType());

        TaxId customerTaxId = (request.getCustomerTaxId() != null
                && !request.getCustomerTaxId().isBlank())
                ? TaxId.of(request.getCustomerTaxId())
                : null;

        RectificationType rectType = (request.getRectificationType() != null)
                ? RectificationType.valueOf(request.getRectificationType())
                : null;

        List<InvoiceNumber> rectifiedRefs = buildRectifiedInvoices(request);

        Invoice invoice = Invoice.create(
                company.getId(),
                terminal.getId(),
                invoiceNumber,
                invoiceType,
                request.getIssueDateTime(),
                request.getOperationDate(),
                customerTaxId,
                request.getCustomerName(),
                request.getCustomerAddress(),
                rectType,
                rectifiedRefs,
                lines,
                breakdowns,
                Money.of(request.getSubtotal()),
                Money.of(request.getTotalTax()),
                Money.of(request.getTotal())
        );

        // ── 7. Validaciones fiscales del dominio ──────────────────────────
        invoice.validateFiscalRequirements();

        // ── 8. Persistir la factura ───────────────────────────────────────
        Invoice savedInvoice = invoiceRepository.save(invoice);

        // ── 9. Crear FiscalRecord en PENDIENTE_ENVIO ──────────────────────
        // El hash, XML y firma reales se generan en el OutboxWorker (Cap.5).
        // Aquí usamos valores provisionales para cumplir la restricción NOT NULL.
        FiscalRecord fiscalRecord = FiscalRecord.createPending(
                savedInvoice.getId(),
                company.getTaxAgency(),
                FiscalRecordType.ALTA,
                "1.0",
                null,                   // previousHash: se calcula en el worker
                "PENDING",              // currentHash: se calcula en el worker
                "PENDING",              // xmlPayload: se genera en el worker
                "PENDING",              // xmlSignedPayload: se genera en el worker
                "PENDING"               // qrContent: se genera en el worker
        );

        fiscalRecordRepository.save(fiscalRecord);

        // ── 10. Construir respuesta para Flutter ──────────────────────────
        return invoiceMapper.toResponseDto(savedInvoice, fiscalRecord);
    }

    // ── Métodos privados de construcción ──────────────────────────────────

    private List<InvoiceLine> buildLines(EmitirFacturaRequestDto request) {
        List<InvoiceLine> lines = new ArrayList<>();
        int order = 1;
        for (EmitirFacturaRequestDto.InvoiceLineRequestDto dto : request.getLines()) {
            lines.add(InvoiceLine.create(
                    null,  // invoiceId: se asignará al persistir
                    order++,
                    dto.getProductCode(),
                    dto.getDescription(),
                    dto.getQuantity(),
                    Money.of(dto.getUnitPrice()),
                    dto.getDiscount() != null ? Money.of(dto.getDiscount()) : Money.ZERO,
                    TaxType.valueOf(dto.getTaxType()),
                    dto.getTaxRate(),
                    Money.of(dto.getTaxBase()),
                    Money.of(dto.getTaxAmount()),
                    dto.getSurchargeRate(),
                    dto.getSurchargeAmount() != null ? Money.of(dto.getSurchargeAmount()) : null,
                    Money.of(dto.getLineTotal())
            ));
        }
        return lines;
    }

    private List<TaxBreakdown> buildBreakdowns(EmitirFacturaRequestDto request) {
        List<TaxBreakdown> breakdowns = new ArrayList<>();
        for (EmitirFacturaRequestDto.TaxBreakdownRequestDto dto : request.getTaxBreakdowns()) {
            breakdowns.add(TaxBreakdown.create(
                    null,  // invoiceId: se asignará al persistir
                    TaxType.valueOf(dto.getTaxType()),
                    dto.getTaxRate(),
                    Money.of(dto.getTaxBase()),
                    Money.of(dto.getTaxAmount()),
                    dto.getSurchargeRate(),
                    dto.getSurchargeAmount() != null ? Money.of(dto.getSurchargeAmount()) : null
            ));
        }
        return breakdowns;
    }

    private List<InvoiceNumber> buildRectifiedInvoices(EmitirFacturaRequestDto request) {
        if (request.getRectifiedInvoices() == null || request.getRectifiedInvoices().isEmpty()) {
            return new ArrayList<>();
        }
        return request.getRectifiedInvoices().stream()
                .map(r -> InvoiceNumber.of(r.getSeries(), r.getNumber()))
                .toList();
    }
}
```

---

### AnularFacturaUseCase.java

```java
package com.novapay.backend.application.usecase;

import com.novapay.backend.domain.enums.FiscalRecordType;
import com.novapay.backend.domain.entity.FiscalRecord;
import com.novapay.backend.domain.entity.Invoice;
import com.novapay.backend.domain.port.CompanyRepository;
import com.novapay.backend.domain.port.FiscalRecordRepository;
import com.novapay.backend.domain.port.InvoiceRepository;
import com.novapay.backend.interfaces.dto.response.InvoiceResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

/**
 * Caso de uso: anular una factura emitida.
 *
 * Genera un FiscalRecord de tipo ANULACION en PENDIENTE_ENVIO.
 * El OutboxWorker lo enviará a la agencia.
 */
@Service
public class AnularFacturaUseCase {

    private final InvoiceRepository invoiceRepository;
    private final FiscalRecordRepository fiscalRecordRepository;
    private final CompanyRepository companyRepository;
    private final InvoiceMapper invoiceMapper;

    public AnularFacturaUseCase(InvoiceRepository invoiceRepository,
                                 FiscalRecordRepository fiscalRecordRepository,
                                 CompanyRepository companyRepository,
                                 InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.fiscalRecordRepository = fiscalRecordRepository;
        this.companyRepository = companyRepository;
        this.invoiceMapper = invoiceMapper;
    }

    @Transactional
    public InvoiceResponseDto execute(UUID invoiceId) {

        // ── 1. Obtener la factura ─────────────────────────────────────────
        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Factura no encontrada: " + invoiceId));

        // ── 2. El método cancel() del dominio valida que está EMITIDA ─────
        invoice.cancel();

        // ── 3. Obtener la agencia de la empresa ───────────────────────────
        var company = companyRepository.findById(invoice.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Empresa no encontrada: " + invoice.getCompanyId()));

        // ── 4. Crear FiscalRecord de ANULACION en PENDIENTE_ENVIO ─────────
        FiscalRecord anulacion = FiscalRecord.createPending(
                invoice.getId(),
                company.getTaxAgency(),
                FiscalRecordType.ANULACION,
                "1.0",
                null,
                "PENDING",
                "PENDING",
                "PENDING",
                "PENDING"
        );

        // ── 5. Persistir ──────────────────────────────────────────────────
        Invoice savedInvoice = invoiceRepository.save(invoice);
        FiscalRecord savedRecord = fiscalRecordRepository.save(anulacion);

        return invoiceMapper.toResponseDto(savedInvoice, savedRecord);
    }
}
```

---

### ConsultarFacturaUseCase.java

```java
package com.novapay.backend.application.usecase;

import com.novapay.backend.domain.entity.FiscalRecord;
import com.novapay.backend.domain.entity.Invoice;
import com.novapay.backend.domain.port.FiscalRecordRepository;
import com.novapay.backend.domain.port.InvoiceRepository;
import com.novapay.backend.interfaces.dto.response.InvoiceResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Caso de uso: consultar el detalle completo de una factura.
 */
@Service
public class ConsultarFacturaUseCase {

    private final InvoiceRepository invoiceRepository;
    private final FiscalRecordRepository fiscalRecordRepository;
    private final InvoiceMapper invoiceMapper;

    public ConsultarFacturaUseCase(InvoiceRepository invoiceRepository,
                                    FiscalRecordRepository fiscalRecordRepository,
                                    InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.fiscalRecordRepository = fiscalRecordRepository;
        this.invoiceMapper = invoiceMapper;
    }

    @Transactional(readOnly = true)
    public InvoiceResponseDto execute(UUID invoiceId) {

        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Factura no encontrada: " + invoiceId));

        // Obtener el último registro fiscal para mostrar el estado actual
        List<FiscalRecord> records = fiscalRecordRepository.findByInvoiceId(invoiceId);
        FiscalRecord latestRecord = records.isEmpty() ? null : records.get(records.size() - 1);

        return invoiceMapper.toResponseDto(invoice, latestRecord);
    }
}
```

---

### ConsultarEstadoFiscalUseCase.java

```java
package com.novapay.backend.application.usecase;

import com.novapay.backend.domain.entity.FiscalRecord;
import com.novapay.backend.domain.entity.Invoice;
import com.novapay.backend.domain.port.FiscalRecordRepository;
import com.novapay.backend.domain.port.InvoiceRepository;
import com.novapay.backend.interfaces.dto.response.FiscalStatusResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

/**
 * Caso de uso: consultar el estado fiscal detallado de una factura.
 * Devuelve todos los FiscalRecords asociados con su estado individual.
 */
@Service
public class ConsultarEstadoFiscalUseCase {

    private final InvoiceRepository invoiceRepository;
    private final FiscalRecordRepository fiscalRecordRepository;
    private final InvoiceMapper invoiceMapper;

    public ConsultarEstadoFiscalUseCase(InvoiceRepository invoiceRepository,
                                         FiscalRecordRepository fiscalRecordRepository,
                                         InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.fiscalRecordRepository = fiscalRecordRepository;
        this.invoiceMapper = invoiceMapper;
    }

    @Transactional(readOnly = true)
    public FiscalStatusResponseDto execute(UUID invoiceId) {

        Invoice invoice = invoiceRepository.findById(invoiceId)
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Factura no encontrada: " + invoiceId));

        List<FiscalRecord> records = fiscalRecordRepository.findByInvoiceId(invoiceId);

        return invoiceMapper.toFiscalStatusDto(invoice, records);
    }
}
```

---

## 4.7 Mappers de aplicación

El mapper de aplicación transforma entre entidades de dominio y DTOs de respuesta.
Va en `com.novapay.backend.application.usecase` (mismo paquete que los casos de uso).

### InvoiceMapper.java

```java
package com.novapay.backend.application.usecase;

import com.novapay.backend.domain.entity.FiscalRecord;
import com.novapay.backend.domain.entity.Invoice;
import com.novapay.backend.domain.entity.TaxBreakdown;
import com.novapay.backend.interfaces.dto.response.FiscalStatusResponseDto;
import com.novapay.backend.interfaces.dto.response.InvoiceResponseDto;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Mapper entre entidades de dominio y DTOs de respuesta de la API.
 */
@Component
public class InvoiceMapper {

    /**
     * Construye la respuesta estándar de una factura con su estado fiscal más reciente.
     */
    public InvoiceResponseDto toResponseDto(Invoice invoice, FiscalRecord latestRecord) {
        InvoiceResponseDto dto = new InvoiceResponseDto();

        dto.setId(invoice.getId());
        dto.setCompanyId(invoice.getCompanyId());
        dto.setTerminalId(invoice.getTerminalId());
        dto.setInvoiceNumber(invoice.getInvoiceNumber().formatted());
        dto.setInvoiceType(invoice.getInvoiceType().name());
        dto.setIssueDateTime(invoice.getIssueDateTime());
        dto.setOperationDate(invoice.getOperationDate());
        dto.setCustomerTaxId(invoice.getCustomerTaxId() != null
                ? invoice.getCustomerTaxId().getValue() : null);
        dto.setCustomerName(invoice.getCustomerName());
        dto.setSubtotal(invoice.getSubtotal().toFiscalScale());
        dto.setTotalTax(invoice.getTotalTax().toFiscalScale());
        dto.setTotal(invoice.getTotal().toFiscalScale());
        dto.setStatus(invoice.getStatus().name());
        dto.setCreatedAt(invoice.getCreatedAt());

        if (latestRecord != null) {
            dto.setFiscalStatus(latestRecord.getStatus().name());
            dto.setQrContent(
                "PENDING".equals(latestRecord.getQrContent()) ? null : latestRecord.getQrContent()
            );
        }

        dto.setTaxBreakdowns(invoice.getTaxBreakdowns().stream()
                .map(this::toBreakdownDto)
                .collect(Collectors.toList()));

        return dto;
    }

    /**
     * Construye la respuesta de estado fiscal con todos los registros.
     */
    public FiscalStatusResponseDto toFiscalStatusDto(Invoice invoice,
                                                      List<FiscalRecord> records) {
        FiscalStatusResponseDto dto = new FiscalStatusResponseDto();
        dto.setInvoiceId(invoice.getId());
        dto.setInvoiceNumber(invoice.getInvoiceNumber().formatted());
        dto.setInvoiceStatus(invoice.getStatus().name());

        dto.setFiscalRecords(records.stream()
                .map(this::toRecordSummaryDto)
                .collect(Collectors.toList()));

        return dto;
    }

    private InvoiceResponseDto.TaxBreakdownResponseDto toBreakdownDto(TaxBreakdown tb) {
        InvoiceResponseDto.TaxBreakdownResponseDto dto =
                new InvoiceResponseDto.TaxBreakdownResponseDto();
        dto.setTaxType(tb.getTaxType().name());
        dto.setTaxRate(tb.getTaxRate());
        dto.setTaxBase(tb.getTaxBase().toFiscalScale());
        dto.setTaxAmount(tb.getTaxAmount().toFiscalScale());
        dto.setSurchargeRate(tb.getSurchargeRate());
        dto.setSurchargeAmount(tb.getSurchargeAmount().toFiscalScale());
        return dto;
    }

    private FiscalStatusResponseDto.FiscalRecordSummaryDto toRecordSummaryDto(
            FiscalRecord record) {
        FiscalStatusResponseDto.FiscalRecordSummaryDto dto =
                new FiscalStatusResponseDto.FiscalRecordSummaryDto();
        dto.setId(record.getId());
        dto.setAgency(record.getAgency().name());
        dto.setRecordType(record.getRecordType().name());
        dto.setStatus(record.getStatus().name());
        dto.setExternalReference(record.getExternalReference());
        dto.setErrorCode(record.getErrorCode());
        dto.setErrorMessage(record.getErrorMessage());
        dto.setRetryCount(record.getRetryCount());
        dto.setSentAt(record.getSentAt());
        dto.setCreatedAt(record.getCreatedAt());
        return dto;
    }
}
```

---

## 4.8 Controllers REST

Los controllers van en `com.novapay.backend.interfaces.rest`.

---

### InvoiceController.java

```java
package com.novapay.backend.interfaces.rest;

import com.novapay.backend.application.usecase.AnularFacturaUseCase;
import com.novapay.backend.application.usecase.ConsultarEstadoFiscalUseCase;
import com.novapay.backend.application.usecase.ConsultarFacturaUseCase;
import com.novapay.backend.application.usecase.EmitirFacturaUseCase;
import com.novapay.backend.interfaces.dto.request.EmitirFacturaRequestDto;
import com.novapay.backend.interfaces.dto.response.ApiResponseDto;
import com.novapay.backend.interfaces.dto.response.FiscalStatusResponseDto;
import com.novapay.backend.interfaces.dto.response.InvoiceResponseDto;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

/**
 * Controller REST para operaciones sobre facturas.
 * Prefijo de ruta: /api/v1/invoices
 */
@RestController
@RequestMapping("/api/v1/invoices")
public class InvoiceController {

    private final EmitirFacturaUseCase emitirFacturaUseCase;
    private final AnularFacturaUseCase anularFacturaUseCase;
    private final ConsultarFacturaUseCase consultarFacturaUseCase;
    private final ConsultarEstadoFiscalUseCase consultarEstadoFiscalUseCase;

    public InvoiceController(EmitirFacturaUseCase emitirFacturaUseCase,
                              AnularFacturaUseCase anularFacturaUseCase,
                              ConsultarFacturaUseCase consultarFacturaUseCase,
                              ConsultarEstadoFiscalUseCase consultarEstadoFiscalUseCase) {
        this.emitirFacturaUseCase = emitirFacturaUseCase;
        this.anularFacturaUseCase = anularFacturaUseCase;
        this.consultarFacturaUseCase = consultarFacturaUseCase;
        this.consultarEstadoFiscalUseCase = consultarEstadoFiscalUseCase;
    }

    /**
     * POST /api/v1/invoices
     * Emite una factura nueva desde el TPV.
     */
    @PostMapping
    public ResponseEntity<ApiResponseDto<InvoiceResponseDto>> emitirFactura(
            @Valid @RequestBody EmitirFacturaRequestDto request) {

        InvoiceResponseDto result = emitirFacturaUseCase.execute(request);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(ApiResponseDto.ok(result));
    }

    /**
     * POST /api/v1/invoices/{id}/cancel
     * Anula una factura emitida.
     */
    @PostMapping("/{id}/cancel")
    public ResponseEntity<ApiResponseDto<InvoiceResponseDto>> anularFactura(
            @PathVariable UUID id) {

        InvoiceResponseDto result = anularFacturaUseCase.execute(id);
        return ResponseEntity.ok(ApiResponseDto.ok(result));
    }

    /**
     * GET /api/v1/invoices/{id}
     * Consulta el detalle completo de una factura.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponseDto<InvoiceResponseDto>> consultarFactura(
            @PathVariable UUID id) {

        InvoiceResponseDto result = consultarFacturaUseCase.execute(id);
        return ResponseEntity.ok(ApiResponseDto.ok(result));
    }

    /**
     * GET /api/v1/invoices/{id}/fiscal-status
     * Consulta el estado fiscal de una factura (todos sus FiscalRecords).
     */
    @GetMapping("/{id}/fiscal-status")
    public ResponseEntity<ApiResponseDto<FiscalStatusResponseDto>> consultarEstadoFiscal(
            @PathVariable UUID id) {

        FiscalStatusResponseDto result = consultarEstadoFiscalUseCase.execute(id);
        return ResponseEntity.ok(ApiResponseDto.ok(result));
    }
}
```

---

### HealthController.java

```java
package com.novapay.backend.interfaces.rest;

import com.novapay.backend.interfaces.dto.response.ApiResponseDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * Controller de estado del backend.
 * Usado por Flutter para verificar conectividad y por herramientas de monitorización.
 */
@RestController
@RequestMapping("/api/v1")
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<ApiResponseDto<Map<String, String>>> health() {
        return ResponseEntity.ok(ApiResponseDto.ok(
                Map.of(
                        "status", "UP",
                        "service", "novapay-backend",
                        "version", "1.0.0"
                )
        ));
    }
}
```

---

## 4.9 Manejo global de errores

El manejador global captura todas las excepciones y las convierte en respuestas JSON coherentes.
Va en `com.novapay.backend.interfaces.rest`.

### GlobalExceptionHandler.java

```java
package com.novapay.backend.interfaces.rest;

import com.novapay.backend.application.usecase.BusinessRuleException;
import com.novapay.backend.application.usecase.DuplicateInvoiceException;
import com.novapay.backend.application.usecase.ResourceNotFoundException;
import com.novapay.backend.interfaces.dto.response.ApiResponseDto;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

/**
 * Manejador global de excepciones.
 * Convierte todas las excepciones en respuestas ApiResponseDto coherentes.
 * Flutter siempre recibe el mismo formato, independientemente del error.
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    /** Recurso no encontrado → 404 */
    @ExceptionHandler(ResourceNotFoundException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleNotFound(ResourceNotFoundException ex) {
        return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .body(ApiResponseDto.error("NOT_FOUND", ex.getMessage()));
    }

    /** Regla de negocio violada → 422 */
    @ExceptionHandler(BusinessRuleException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleBusinessRule(BusinessRuleException ex) {
        return ResponseEntity
                .status(HttpStatus.UNPROCESSABLE_ENTITY)
                .body(ApiResponseDto.error(ex.getCode(), ex.getMessage()));
    }

    /** Factura duplicada → 409 */
    @ExceptionHandler(DuplicateInvoiceException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleDuplicate(DuplicateInvoiceException ex) {
        return ResponseEntity
                .status(HttpStatus.CONFLICT)
                .body(ApiResponseDto.error("INVOICE_DUPLICATE", ex.getMessage()));
    }

    /** Error de argumento inválido en Value Object (NIF, Money, etc.) → 400 */
    @ExceptionHandler(IllegalArgumentException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleIllegalArgument(IllegalArgumentException ex) {
        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDto.error("INVALID_ARGUMENT", ex.getMessage()));
    }

    /** Errores de validación de Bean Validation (@NotNull, @Size, etc.) → 400 */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<Void>> handleValidationErrors(
            MethodArgumentNotValidException ex) {

        String message = ex.getBindingResult().getFieldErrors().stream()
                .map(FieldError::getDefaultMessage)
                .collect(Collectors.joining("; "));

        return ResponseEntity
                .status(HttpStatus.BAD_REQUEST)
                .body(ApiResponseDto.error("VALIDATION_ERROR", message));
    }

    /** Cualquier otro error no previsto → 500 */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiResponseDto<Void>> handleGeneric(Exception ex) {
        return ResponseEntity
                .status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(ApiResponseDto.error("INTERNAL_ERROR",
                        "Error interno del servidor. Contacte con soporte."));
    }
}
```

---

## 4.10 Excepciones de dominio

Las excepciones van en `com.novapay.backend.application.usecase`.

### ResourceNotFoundException.java

```java
package com.novapay.backend.application.usecase;

/**
 * Se lanza cuando un recurso solicitado no existe en la base de datos.
 * Resulta en HTTP 404.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }
}
```

### BusinessRuleException.java

```java
package com.novapay.backend.application.usecase;

/**
 * Se lanza cuando se viola una regla de negocio.
 * Resulta en HTTP 422.
 *
 * Incluye un código de error legible por Flutter para mostrar mensajes específicos.
 */
public class BusinessRuleException extends RuntimeException {

    private final String code;

    public BusinessRuleException(String code, String message) {
        super(message);
        this.code = code;
    }

    public String getCode() {
        return code;
    }
}
```

### DuplicateInvoiceException.java

```java
package com.novapay.backend.application.usecase;

/**
 * Se lanza cuando se intenta emitir una factura con una serie+número ya existente.
 * Resulta en HTTP 409.
 */
public class DuplicateInvoiceException extends RuntimeException {

    public DuplicateInvoiceException(String series, int number) {
        super(String.format(
                "Ya existe una factura con la serie '%s' y número %d", series, number));
    }
}
```

---

## 4.11 Configuración de Spring Security (provisional)

Spring Security está en el classpath y bloquearía todos los endpoints por defecto.
Esta configuración lo desactiva temporalmente hasta el Capítulo 9 (Producción).

Va en `com.novapay.backend.infra.config`.

### SecurityConfig.java

```java
package com.novapay.backend.infra.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.SecurityFilterChain;

/**
 * Configuración provisional de Spring Security.
 *
 * ATENCIÓN: Esta configuración permite acceso libre a todos los endpoints.
 * Solo válida para desarrollo local.
 * La seguridad real (JWT, mTLS) se implementa en el Capítulo 9.
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .csrf(AbstractHttpConfigurer::disable)
            .authorizeHttpRequests(auth -> auth
                .anyRequest().permitAll()
            );
        return http.build();
    }
}
```

---

## 4.12 Estructura de ficheros de este capítulo

```
src/main/java/com/novapay/backend/
│
├── domain/port/                          ← AÑADIDOS (corrección Cap.2)
│   ├── CompanyRepository.java
│   └── PosTerminalRepository.java
│
├── application/
│   └── usecase/
│       ├── EmitirFacturaUseCase.java
│       ├── AnularFacturaUseCase.java
│       ├── ConsultarFacturaUseCase.java
│       ├── ConsultarEstadoFiscalUseCase.java
│       ├── InvoiceMapper.java
│       ├── ResourceNotFoundException.java
│       ├── BusinessRuleException.java
│       └── DuplicateInvoiceException.java
│
├── interfaces/
│   ├── rest/
│   │   ├── InvoiceController.java
│   │   ├── HealthController.java
│   │   └── GlobalExceptionHandler.java
│   └── dto/
│       ├── request/
│       │   └── EmitirFacturaRequestDto.java
│       └── response/
│           ├── ApiResponseDto.java
│           ├── InvoiceResponseDto.java
│           └── FiscalStatusResponseDto.java
│
└── infra/
    ├── config/
    │   └── SecurityConfig.java
    └── db/                               ← AÑADIDOS (corrección Cap.3)
        ├── CompanyJpaRepository.java
        ├── PosTerminalJpaRepository.java
        ├── CompanyRepositoryImpl.java
        └── mapper/
            └── CompanyEntityMapper.java
```

---

## 4.13 Verificar que todo funciona

### Paso 1 — Arranca el proyecto

Ejecuta `NovapayBackendApplication` en IntelliJ. Debes ver:

```
Started NovapayBackendApplication in x.xxx seconds
Tomcat started on port 8080
```

Sin errores de ningún tipo en la consola.

### Paso 2 — Prueba el healthcheck

Abre el navegador o usa la herramienta **HTTP Client** de IntelliJ (`Tools → HTTP Client`):

```http
GET http://localhost:8080/api/v1/health
```

Respuesta esperada:
```json
{
  "success": true,
  "data": {
    "status": "UP",
    "service": "novapay-backend",
    "version": "1.0.0"
  },
  "timestamp": "2026-02-01T10:30:00"
}
```

### Paso 3 — Emite una factura de prueba

```http
POST http://localhost:8080/api/v1/invoices
Content-Type: application/json

{
  "companyId": "a0000000-0000-0000-0000-000000000001",
  "terminalId": "b0000000-0000-0000-0000-000000000001",
  "invoiceSeries": "A",
  "invoiceNumber": 1,
  "invoiceType": "SIMPLIFICADA",
  "issueDateTime": "2026-02-01T10:30:00",
  "lines": [
    {
      "description": "Café con leche",
      "quantity": 2,
      "unitPrice": 1.50,
      "taxType": "IVA",
      "taxRate": 10.00,
      "taxBase": 2.73,
      "taxAmount": 0.27,
      "lineTotal": 3.00
    }
  ],
  "subtotal": 2.73,
  "totalTax": 0.27,
  "total": 3.00,
  "taxBreakdowns": [
    {
      "taxType": "IVA",
      "taxRate": 10.00,
      "taxBase": 2.73,
      "taxAmount": 0.27
    }
  ]
}
```

Respuesta esperada:
```json
{
  "success": true,
  "data": {
    "id": "...",
    "invoiceNumber": "A-00001",
    "invoiceType": "SIMPLIFICADA",
    "status": "EMITIDA",
    "fiscalStatus": "PENDIENTE_ENVIO",
    "total": 3.00
  },
  "timestamp": "..."
}
```

### Paso 4 — Prueba de error (duplicado)

Repite exactamente la misma petición. Respuesta esperada:
```json
{
  "success": false,
  "error": {
    "code": "INVOICE_DUPLICATE",
    "message": "Ya existe una factura con la serie 'A' y número 1"
  }
}
```

---

## 4.14 Resumen y próximo capítulo

### Lo que has construido en este capítulo

| Fichero | Tipo | Propósito |
|---------|------|-----------|
| `CompanyRepository.java` | Interfaz de puerto | Corrección retroactiva Cap.2 |
| `PosTerminalRepository.java` | Interfaz de puerto | Corrección retroactiva Cap.2 |
| `CompanyJpaRepository.java` | Repositorio Spring Data | Corrección retroactiva Cap.3 |
| `CompanyRepositoryImpl.java` | Implementación de puerto | Corrección retroactiva Cap.3 |
| `CompanyEntityMapper.java` | Mapper | Corrección retroactiva Cap.3 |
| `EmitirFacturaUseCase.java` | Caso de uso | Orquesta la emisión completa |
| `AnularFacturaUseCase.java` | Caso de uso | Orquesta la anulación |
| `ConsultarFacturaUseCase.java` | Caso de uso | Detalle de factura |
| `ConsultarEstadoFiscalUseCase.java` | Caso de uso | Estado fiscal detallado |
| `InvoiceMapper.java` | Mapper de aplicación | Dominio → DTOs de respuesta |
| `EmitirFacturaRequestDto.java` | DTO entrada | JSON que envía Flutter |
| `ApiResponseDto.java` | DTO salida | Envoltorio estándar de respuesta |
| `InvoiceResponseDto.java` | DTO salida | Datos de factura para Flutter |
| `FiscalStatusResponseDto.java` | DTO salida | Estado fiscal para Flutter |
| `InvoiceController.java` | Controller REST | Endpoints de factura |
| `HealthController.java` | Controller REST | Healthcheck |
| `GlobalExceptionHandler.java` | Manejador de errores | Respuestas de error coherentes |
| `ResourceNotFoundException.java` | Excepción | Recurso no encontrado (404) |
| `BusinessRuleException.java` | Excepción | Regla de negocio violada (422) |
| `DuplicateInvoiceException.java` | Excepción | Factura duplicada (409) |
| `SecurityConfig.java` | Configuración | Security desactivado para dev |

### Próximo capítulo

El **Capítulo 5** cubre la evidencia fiscal local: el hash de encadenamiento, el cálculo del QR, y el OutboxWorker — el componente que toma los `FiscalRecord` en estado `PENDIENTE_ENVIO` y ejecuta el flujo completo de generación XML + firma + envío a la agencia. Es el capítulo que cierra el ciclo completo del sistema.

---

---

*NovaPay · Documentación Técnica Backend · Capítulo 4 · Versión 1.0 · Febrero 2026*

# NovaPay Backend
## Documentación Técnica Oficial

> **Capítulo:** 5 — Evidencia Fiscal · Hash · Encadenamiento · OutboxWorker  
> **Versión doc:** 1.0 · Febrero 2026  
> **Prerrequisito:** Capítulos 2, 3 y 4 completados y funcionando  
> **Resultado:** Ciclo fiscal completo operativo — desde emisión hasta ACEPTADO

---

## Índice del Capítulo

- [5.1 Qué cierra este capítulo](#51-qué-cierra-este-capítulo)
- [5.2 Decisión arquitectónica central — inmutabilidad y flujo en dos fases](#52-decisión-arquitectónica-central--inmutabilidad-y-flujo-en-dos-fases)
- [5.3 Correcciones retroactivas de capítulos anteriores](#53-correcciones-retroactivas-de-capítulos-anteriores)
- [5.4 FiscalEvidenceService — hash y encadenamiento](#54-fiscalevidenceservice--hash-y-encadenamiento)
- [5.5 StubFiscalAgencyAdapter — adaptador de desarrollo](#55-stubfiscalagencyadapter--adaptador-de-desarrollo)
- [5.6 FiscalProcessingService — orquestador del ciclo fiscal](#56-fiscalprocessingservice--orquestador-del-ciclo-fiscal)
- [5.7 OutboxWorker — scheduler asíncrono](#57-outboxworker--scheduler-asíncrono)
- [5.8 Habilitar scheduling y configuración](#58-habilitar-scheduling-y-configuración)
- [5.9 Estructura de ficheros de este capítulo](#59-estructura-de-ficheros-de-este-capítulo)
- [5.10 Verificar que todo funciona](#510-verificar-que-todo-funciona)
- [5.11 Resumen y próximo capítulo](#511-resumen-y-próximo-capítulo)

---

## 5.1 Qué cierra este capítulo

En el Capítulo 4 el TPV envía una factura y recibe respuesta inmediata con
`fiscalStatus: PENDIENTE_ENVIO`. Los campos de evidencia fiscal (`currentHash`,
`xmlPayload`, `xmlSignedPayload`, `qrContent`) se guardaron como `"PENDING"` —
valores provisionales que cumplen el `NOT NULL` de la base de datos pero que
aún no tienen significado fiscal real.

Este capítulo implementa el componente que completa ese ciclo:

```
Flutter
  │  POST /api/v1/invoices
  │  ← respuesta inmediata: fiscalStatus=PENDIENTE_ENVIO (Cap.4)
  │
  │  (el TPV ya puede imprimir y seguir vendiendo)
  │
  ▼
OutboxWorker  (cada 10 segundos, en segundo plano)
  │
  ├─ Lee FiscalRecord en PENDIENTE_ENVIO o REINTENTO
  │
  ├─ FiscalEvidenceService
  │    ├─ previousHash   ← último hash ACEPTADO del terminal
  │    ├─ currentHash    ← SHA-256 de campos fiscales
  │    ├─ xmlPayload     ← XML estructural de la factura
  │    ├─ xmlSignedPayload ← XML con firma (placeholder en dev)
  │    └─ qrContent      ← URL de verificación con hash
  │
  ├─ Escribe los 5 campos reales en FiscalRecordEntity (JPA)
  │
  ├─ FiscalAgencyAdapter.send()
  │    ├─ StubAdapter (perfil dev) → simula ACEPTADO
  │    ├─ AeatAdapter  (Cap.6) → VERI*FACTU real
  │    └─ TbaiAdapter  (Cap.7) → TicketBAI real
  │
  └─ Persiste respuesta → ACEPTADO | RECHAZADO | REINTENTO
```

---

## 5.2 Decisión arquitectónica central — inmutabilidad y flujo en dos fases

### El problema: campos `final` en el dominio vs. `PENDING` en la DB

`FiscalRecord` (dominio, Cap.2) declara sus campos de evidencia como `final`:

```java
private final String previousHash;
private final String currentHash;
private final String xmlPayload;
private final String xmlSignedPayload;
private final String qrContent;
```

En Cap.4, `EmitirFacturaUseCase` los guarda con `"PENDING"` porque el hash y
el XML requieren una factura ya persistida (con su `UUID` real) y porque el
cálculo del hash anterior necesita consultar la base de datos. Separar la
emisión del cálculo fiscal es una decisión deliberada: el TPV no espera bloqueado.

### La solución: dos capas, dos responsabilidades

`FiscalRecordEntity` (infraestructura JPA, Cap.3) **no** declara esos campos
como `final` y tiene setters. El worker actualiza `FiscalRecordEntity`
directamente antes del primer envío. Tras esa actualización, recarga un
`FiscalRecord` de dominio ya completo y desde ese punto la inmutabilidad
del dominio queda garantizada.

```
Fase 1 — EmitirFacturaUseCase (Cap.4, transacción síncrona)
  FiscalRecord.createPending(..., "PENDING", "PENDING", "PENDING", "PENDING", "PENDING")
  fiscalRecordRepository.save(record)
  → FiscalRecordEntity en DB: currentHash="PENDING", status=PENDIENTE_ENVIO

Fase 2 — FiscalProcessingService (Cap.5, transacción asíncrona)
  [Sólo si currentHash == "PENDING"]
  FiscalRecordEntity.setCurrentHash(hash_real)
  FiscalRecordEntity.setXmlPayload(xml_real)
  FiscalRecordEntity.setXmlSignedPayload(xml_firmado)
  FiscalRecordEntity.setQrContent(qr_real)
  fiscalRecordJpaRepository.save(entity)
  ← Punto de no retorno: evidencia fiscal escrita

  freshRecord = fiscalRecordRepository.findById(id)   ← recarga con datos reales
  freshRecord.markAsSending()
  adapter.send(freshRecord)
  freshRecord.markAsAccepted(...) | markAsRejected(...) | markForRetry(...)
  fiscalRecordRepository.save(freshRecord)
```

---

## 5.3 Correcciones retroactivas de capítulos anteriores

Durante la preparación de este capítulo se detectaron cuatro grupos de
omisiones en los capítulos anteriores. **Añade estos ficheros antes de
implementar el Cap.5** — sin ellos el proyecto no compila correctamente.

---

### 5.3.1 Entidades JPA que faltan (corrección Cap.3)

Las tablas `invoice_line` y `tax_breakdown` existen en el SQL de Cap.3 pero
no tienen entidades JPA ni repositorios. El `FiscalEvidenceService` necesita
los breakdowns para calcular el hash. El `InvoiceRepositoryImpl` necesita
persistirlos y cargarlos.

Añade estos ficheros en `com.novapay.backend.infra.db.entity`:

#### InvoiceLineEntity.java

```java
package com.novapay.backend.infra.db.entity;

import com.novapay.backend.domain.enums.TaxType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "invoice_line")
public class InvoiceLineEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "invoice_id", nullable = false)
    private UUID invoiceId;

    @Column(name = "line_order", nullable = false)
    private int lineOrder;

    @Column(name = "product_code", length = 100)
    private String productCode;

    @Column(name = "description", nullable = false, length = 500)
    private String description;

    @Column(name = "quantity", nullable = false, precision = 14, scale = 6)
    private BigDecimal quantity;

    @Column(name = "unit_price", nullable = false, precision = 14, scale = 6)
    private BigDecimal unitPrice;

    @Column(name = "discount", nullable = false, precision = 14, scale = 6)
    private BigDecimal discount;

    @Enumerated(EnumType.STRING)
    @Column(name = "tax_type", nullable = false)
    private TaxType taxType;

    @Column(name = "tax_rate", nullable = false, precision = 6, scale = 4)
    private BigDecimal taxRate;

    @Column(name = "tax_base", nullable = false, precision = 14, scale = 6)
    private BigDecimal taxBase;

    @Column(name = "tax_amount", nullable = false, precision = 14, scale = 6)
    private BigDecimal taxAmount;

    @Column(name = "surcharge_rate", precision = 6, scale = 4)
    private BigDecimal surchargeRate;

    @Column(name = "surcharge_amount", nullable = false, precision = 14, scale = 6)
    private BigDecimal surchargeAmount;

    @Column(name = "line_total", nullable = false, precision = 14, scale = 6)
    private BigDecimal lineTotal;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getInvoiceId() { return invoiceId; }
    public void setInvoiceId(UUID invoiceId) { this.invoiceId = invoiceId; }
    public int getLineOrder() { return lineOrder; }
    public void setLineOrder(int lineOrder) { this.lineOrder = lineOrder; }
    public String getProductCode() { return productCode; }
    public void setProductCode(String productCode) { this.productCode = productCode; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public BigDecimal getQuantity() { return quantity; }
    public void setQuantity(BigDecimal quantity) { this.quantity = quantity; }
    public BigDecimal getUnitPrice() { return unitPrice; }
    public void setUnitPrice(BigDecimal unitPrice) { this.unitPrice = unitPrice; }
    public BigDecimal getDiscount() { return discount; }
    public void setDiscount(BigDecimal discount) { this.discount = discount; }
    public TaxType getTaxType() { return taxType; }
    public void setTaxType(TaxType taxType) { this.taxType = taxType; }
    public BigDecimal getTaxRate() { return taxRate; }
    public void setTaxRate(BigDecimal taxRate) { this.taxRate = taxRate; }
    public BigDecimal getTaxBase() { return taxBase; }
    public void setTaxBase(BigDecimal taxBase) { this.taxBase = taxBase; }
    public BigDecimal getTaxAmount() { return taxAmount; }
    public void setTaxAmount(BigDecimal taxAmount) { this.taxAmount = taxAmount; }
    public BigDecimal getSurchargeRate() { return surchargeRate; }
    public void setSurchargeRate(BigDecimal surchargeRate) { this.surchargeRate = surchargeRate; }
    public BigDecimal getSurchargeAmount() { return surchargeAmount; }
    public void setSurchargeAmount(BigDecimal surchargeAmount) { this.surchargeAmount = surchargeAmount; }
    public BigDecimal getLineTotal() { return lineTotal; }
    public void setLineTotal(BigDecimal lineTotal) { this.lineTotal = lineTotal; }
}
```

#### TaxBreakdownEntity.java

```java
package com.novapay.backend.infra.db.entity;

import com.novapay.backend.domain.enums.TaxType;
import jakarta.persistence.*;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "tax_breakdown")
public class TaxBreakdownEntity {

    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "invoice_id", nullable = false)
    private UUID invoiceId;

    @Enumerated(EnumType.STRING)
    @Column(name = "tax_type", nullable = false)
    private TaxType taxType;

    @Column(name = "tax_rate", nullable = false, precision = 6, scale = 4)
    private BigDecimal taxRate;

    @Column(name = "tax_base", nullable = false, precision = 14, scale = 6)
    private BigDecimal taxBase;

    @Column(name = "tax_amount", nullable = false, precision = 14, scale = 6)
    private BigDecimal taxAmount;

    @Column(name = "surcharge_rate", precision = 6, scale = 4)
    private BigDecimal surchargeRate;

    @Column(name = "surcharge_amount", nullable = false, precision = 14, scale = 6)
    private BigDecimal surchargeAmount;

    public UUID getId() { return id; }
    public void setId(UUID id) { this.id = id; }
    public UUID getInvoiceId() { return invoiceId; }
    public void setInvoiceId(UUID invoiceId) { this.invoiceId = invoiceId; }
    public TaxType getTaxType() { return taxType; }
    public void setTaxType(TaxType taxType) { this.taxType = taxType; }
    public BigDecimal getTaxRate() { return taxRate; }
    public void setTaxRate(BigDecimal taxRate) { this.taxRate = taxRate; }
    public BigDecimal getTaxBase() { return taxBase; }
    public void setTaxBase(BigDecimal taxBase) { this.taxBase = taxBase; }
    public BigDecimal getTaxAmount() { return taxAmount; }
    public void setTaxAmount(BigDecimal taxAmount) { this.taxAmount = taxAmount; }
    public BigDecimal getSurchargeRate() { return surchargeRate; }
    public void setSurchargeRate(BigDecimal surchargeRate) { this.surchargeRate = surchargeRate; }
    public BigDecimal getSurchargeAmount() { return surchargeAmount; }
    public void setSurchargeAmount(BigDecimal surchargeAmount) { this.surchargeAmount = surchargeAmount; }
}
```

---

### 5.3.2 Repositorios JPA que faltan (corrección Cap.3)

Añade en `com.novapay.backend.infra.db`:

#### InvoiceLineJpaRepository.java

```java
package com.novapay.backend.infra.db;

import com.novapay.backend.infra.db.entity.InvoiceLineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface InvoiceLineJpaRepository extends JpaRepository<InvoiceLineEntity, UUID> {

    List<InvoiceLineEntity> findByInvoiceIdOrderByLineOrder(UUID invoiceId);

    void deleteByInvoiceId(UUID invoiceId);
}
```

#### TaxBreakdownJpaRepository.java

```java
package com.novapay.backend.infra.db;

import com.novapay.backend.infra.db.entity.TaxBreakdownEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaxBreakdownJpaRepository extends JpaRepository<TaxBreakdownEntity, UUID> {

    List<TaxBreakdownEntity> findByInvoiceId(UUID invoiceId);

    void deleteByInvoiceId(UUID invoiceId);
}
```

---

### 5.3.3 Mappers que faltan (corrección Cap.3)

Añade en `com.novapay.backend.infra.db.mapper`:

#### InvoiceLineMapper.java

```java
package com.novapay.backend.infra.db.mapper;

import com.novapay.backend.domain.entity.InvoiceLine;
import com.novapay.backend.domain.valueObject.Money;
import com.novapay.backend.infra.db.entity.InvoiceLineEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class InvoiceLineMapper {

    public InvoiceLineEntity toEntity(InvoiceLine domain) {
        InvoiceLineEntity entity = new InvoiceLineEntity();
        entity.setId(domain.getId());
        entity.setInvoiceId(domain.getInvoiceId());
        entity.setLineOrder(domain.getLineOrder());
        entity.setProductCode(domain.getProductCode());
        entity.setDescription(domain.getDescription());
        entity.setQuantity(domain.getQuantity());
        entity.setUnitPrice(domain.getUnitPrice().getValue());
        entity.setDiscount(domain.getDiscount().getValue());
        entity.setTaxType(domain.getTaxType());
        entity.setTaxRate(domain.getTaxRate());
        entity.setTaxBase(domain.getTaxBase().getValue());
        entity.setTaxAmount(domain.getTaxAmount().getValue());
        entity.setSurchargeRate(domain.getSurchargeRate());
        entity.setSurchargeAmount(domain.getSurchargeAmount() != null
                ? domain.getSurchargeAmount().getValue() : BigDecimal.ZERO);
        entity.setLineTotal(domain.getLineTotal().getValue());
        return entity;
    }

    public InvoiceLine toDomain(InvoiceLineEntity entity) {
        return new InvoiceLine(
                entity.getId(),
                entity.getInvoiceId(),
                entity.getLineOrder(),
                entity.getProductCode(),
                entity.getDescription(),
                entity.getQuantity(),
                Money.of(entity.getUnitPrice()),
                Money.of(entity.getDiscount()),
                entity.getTaxType(),
                entity.getTaxRate(),
                Money.of(entity.getTaxBase()),
                Money.of(entity.getTaxAmount()),
                entity.getSurchargeRate(),
                entity.getSurchargeAmount() != null
                        ? Money.of(entity.getSurchargeAmount()) : null,
                Money.of(entity.getLineTotal())
        );
    }
}
```

#### TaxBreakdownMapper.java

```java
package com.novapay.backend.infra.db.mapper;

import com.novapay.backend.domain.entity.TaxBreakdown;
import com.novapay.backend.domain.valueObject.Money;
import com.novapay.backend.infra.db.entity.TaxBreakdownEntity;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.UUID;

@Component
public class TaxBreakdownMapper {

    public TaxBreakdownEntity toEntity(TaxBreakdown domain) {
        TaxBreakdownEntity entity = new TaxBreakdownEntity();
        entity.setId(domain.getId());
        entity.setInvoiceId(domain.getInvoiceId());
        entity.setTaxType(domain.getTaxType());
        entity.setTaxRate(domain.getTaxRate());
        entity.setTaxBase(domain.getTaxBase().getValue());
        entity.setTaxAmount(domain.getTaxAmount().getValue());
        entity.setSurchargeRate(domain.getSurchargeRate());
        entity.setSurchargeAmount(domain.getSurchargeAmount() != null
                ? domain.getSurchargeAmount().getValue() : BigDecimal.ZERO);
        return entity;
    }

    public TaxBreakdown toDomain(TaxBreakdownEntity entity) {
        return new TaxBreakdown(
                entity.getId(),
                entity.getInvoiceId(),
                entity.getTaxType(),
                entity.getTaxRate(),
                Money.of(entity.getTaxBase()),
                Money.of(entity.getTaxAmount()),
                entity.getSurchargeRate(),
                entity.getSurchargeAmount() != null
                        ? Money.of(entity.getSurchargeAmount()) : null
        );
    }
}
```

---

### 5.3.4 InvoiceRepositoryImpl actualizado (corrección Cap.3)

El `InvoiceRepositoryImpl` de Cap.3 persistía la `Invoice` pero ignoraba las
líneas y breakdowns. También los devolvía vacíos al cargar. Aquí se corrige.

**Sustituye** el fichero `InvoiceRepositoryImpl.java` completo:

```java
package com.novapay.backend.infra.db;

import com.novapay.backend.domain.entity.Invoice;
import com.novapay.backend.domain.entity.InvoiceLine;
import com.novapay.backend.domain.entity.TaxBreakdown;
import com.novapay.backend.domain.port.InvoiceRepository;
import com.novapay.backend.domain.valueObject.InvoiceNumber;
import com.novapay.backend.infra.db.entity.InvoiceEntity;
import com.novapay.backend.infra.db.entity.InvoiceLineEntity;
import com.novapay.backend.infra.db.entity.TaxBreakdownEntity;
import com.novapay.backend.infra.db.mapper.InvoiceEntityMapper;
import com.novapay.backend.infra.db.mapper.InvoiceLineMapper;
import com.novapay.backend.infra.db.mapper.TaxBreakdownMapper;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

/**
 * Implementación del puerto InvoiceRepository.
 *
 * Persiste y carga Invoice junto con sus InvoiceLine y TaxBreakdown.
 * Las líneas y breakdowns se gestionan de forma independiente (no hay
 * cascade JPA entre InvoiceEntity y las entidades hijo) para mantener
 * el control explícito y evitar problemas de lazy loading.
 */
@Component
public class InvoiceRepositoryImpl implements InvoiceRepository {

    private final InvoiceJpaRepository invoiceJpa;
    private final InvoiceLineJpaRepository lineJpa;
    private final TaxBreakdownJpaRepository breakdownJpa;
    private final InvoiceEntityMapper invoiceMapper;
    private final InvoiceLineMapper lineMapper;
    private final TaxBreakdownMapper breakdownMapper;

    public InvoiceRepositoryImpl(InvoiceJpaRepository invoiceJpa,
                                  InvoiceLineJpaRepository lineJpa,
                                  TaxBreakdownJpaRepository breakdownJpa,
                                  InvoiceEntityMapper invoiceMapper,
                                  InvoiceLineMapper lineMapper,
                                  TaxBreakdownMapper breakdownMapper) {
        this.invoiceJpa = invoiceJpa;
        this.lineJpa = lineJpa;
        this.breakdownJpa = breakdownJpa;
        this.invoiceMapper = invoiceMapper;
        this.lineMapper = lineMapper;
        this.breakdownMapper = breakdownMapper;
    }

    @Override
    @Transactional
    public Invoice save(Invoice invoice) {
        // 1. Persistir la cabecera de la factura
        InvoiceEntity entity = invoiceMapper.toEntity(invoice);
        invoiceJpa.save(entity);

        // 2. Persistir las líneas (borrar las anteriores si es una actualización)
        lineJpa.deleteByInvoiceId(invoice.getId());
        for (InvoiceLine line : invoice.getLines()) {
            InvoiceLineEntity lineEntity = lineMapper.toEntity(line);
            lineEntity.setInvoiceId(invoice.getId());  // garantizar FK correcta
            lineJpa.save(lineEntity);
        }

        // 3. Persistir los breakdowns fiscales
        breakdownJpa.deleteByInvoiceId(invoice.getId());
        for (TaxBreakdown tb : invoice.getTaxBreakdowns()) {
            TaxBreakdownEntity tbEntity = breakdownMapper.toEntity(tb);
            tbEntity.setInvoiceId(invoice.getId());    // garantizar FK correcta
            breakdownJpa.save(tbEntity);
        }

        // 4. Devolver la invoice cargada con líneas y breakdowns reales
        return loadFull(entity);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Invoice> findById(UUID id) {
        return invoiceJpa.findById(id).map(this::loadFull);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Invoice> findByCompanyAndNumber(UUID companyId,
                                                     InvoiceNumber invoiceNumber) {
        return invoiceJpa
                .findByCompanyIdAndInvoiceSeriesAndInvoiceNumber(
                        companyId,
                        invoiceNumber.getSeries(),
                        invoiceNumber.getNumber())
                .map(this::loadFull);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean existsByCompanyAndNumber(UUID companyId,
                                             InvoiceNumber invoiceNumber) {
        return invoiceJpa.existsByCompanyIdAndInvoiceSeriesAndInvoiceNumber(
                companyId,
                invoiceNumber.getSeries(),
                invoiceNumber.getNumber());
    }

    /**
     * Carga una Invoice completa: cabecera + líneas + breakdowns.
     * Método interno reutilizado por save() y findById().
     */
    private Invoice loadFull(InvoiceEntity entity) {
        List<InvoiceLine> lines = lineJpa
                .findByInvoiceIdOrderByLineOrder(entity.getId())
                .stream()
                .map(lineMapper::toDomain)
                .collect(Collectors.toList());

        List<TaxBreakdown> breakdowns = breakdownJpa
                .findByInvoiceId(entity.getId())
                .stream()
                .map(breakdownMapper::toDomain)
                .collect(Collectors.toList());

        return invoiceMapper.toDomain(entity, lines, breakdowns);
    }
}
```

---

### 5.3.5 InvoiceEntityMapper actualizado (corrección Cap.3)

El `InvoiceEntityMapper.toDomain()` de Cap.3 ignoraba los parámetros de líneas
y breakdowns (los ponía siempre como `emptyList()`). Ahora acepta las listas
cargadas por `InvoiceRepositoryImpl`.

**Sustituye** `InvoiceEntityMapper.java` completo:

```java
package com.novapay.backend.infra.db.mapper;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.novapay.backend.domain.entity.Invoice;
import com.novapay.backend.domain.entity.InvoiceLine;
import com.novapay.backend.domain.entity.TaxBreakdown;
import com.novapay.backend.domain.valueObject.InvoiceNumber;
import com.novapay.backend.domain.valueObject.Money;
import com.novapay.backend.domain.valueObject.TaxId;
import com.novapay.backend.infra.db.entity.InvoiceEntity;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;
import java.util.Map;

@Component
public class InvoiceEntityMapper {

    private final ObjectMapper objectMapper;

    public InvoiceEntityMapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    /** Convierte Invoice de dominio a entidad JPA (solo la cabecera). */
    public InvoiceEntity toEntity(Invoice domain) {
        InvoiceEntity entity = new InvoiceEntity();
        entity.setId(domain.getId());
        entity.setCompanyId(domain.getCompanyId());
        entity.setTerminalId(domain.getTerminalId());
        entity.setInvoiceSeries(domain.getInvoiceNumber().getSeries());
        entity.setInvoiceNumber(domain.getInvoiceNumber().getNumber());
        entity.setInvoiceType(domain.getInvoiceType());
        entity.setIssueDateTime(domain.getIssueDateTime());
        entity.setOperationDate(domain.getOperationDate());
        entity.setCustomerTaxId(domain.getCustomerTaxId() != null
                ? domain.getCustomerTaxId().getValue() : null);
        entity.setCustomerName(domain.getCustomerName());
        entity.setCustomerAddress(domain.getCustomerAddress());
        entity.setRectificationType(domain.getRectificationType());
        entity.setRectifiedInvoices(serializeRectifiedInvoices(domain.getRectifiedInvoices()));
        entity.setSubtotal(domain.getSubtotal().getValue());
        entity.setTotalTax(domain.getTotalTax().getValue());
        entity.setTotal(domain.getTotal().getValue());
        entity.setStatus(domain.getStatus());
        return entity;
    }

    /**
     * Convierte entidad JPA + listas ya cargadas a Invoice de dominio.
     * Las listas se cargan externamente por InvoiceRepositoryImpl.
     */
    public Invoice toDomain(InvoiceEntity entity,
                             List<InvoiceLine> lines,
                             List<TaxBreakdown> breakdowns) {
        return new Invoice(
                entity.getId(),
                entity.getCompanyId(),
                entity.getTerminalId(),
                InvoiceNumber.of(entity.getInvoiceSeries(), entity.getInvoiceNumber()),
                entity.getInvoiceType(),
                entity.getIssueDateTime(),
                entity.getOperationDate(),
                entity.getCustomerTaxId() != null ? TaxId.of(entity.getCustomerTaxId()) : null,
                entity.getCustomerName(),
                entity.getCustomerAddress(),
                entity.getRectificationType(),
                deserializeRectifiedInvoices(entity.getRectifiedInvoices()),
                lines,
                breakdowns,
                Money.of(entity.getSubtotal()),
                Money.of(entity.getTotalTax()),
                Money.of(entity.getTotal()),
                entity.getStatus(),
                entity.getCreatedAt()
        );
    }

    // ── Serialización de facturas rectificadas ────────────────────────────

    private String serializeRectifiedInvoices(List<InvoiceNumber> invoices) {
        if (invoices == null || invoices.isEmpty()) return null;
        try {
            List<Map<String, Object>> list = invoices.stream()
                    .map(n -> Map.of("series", (Object) n.getSeries(), "number", n.getNumber()))
                    .toList();
            return objectMapper.writeValueAsString(list);
        } catch (Exception e) {
            return null;
        }
    }

    private List<InvoiceNumber> deserializeRectifiedInvoices(String json) {
        if (json == null || json.isBlank()) return Collections.emptyList();
        try {
            List<Map<String, Object>> list = objectMapper.readValue(
                    json, new TypeReference<>() {});
            return list.stream()
                    .map(m -> InvoiceNumber.of(
                            (String) m.get("series"),
                            (Integer) m.get("number")))
                    .toList();
        } catch (Exception e) {
            return Collections.emptyList();
        }
    }
}
```

---

### 5.3.6 EmitirFacturaUseCase corregido (corrección Cap.4)

`buildLines()` pasaba `null` como `invoiceId`, pero `InvoiceLine` tiene
`Objects.requireNonNull(invoiceId)` en su constructor, lo que causa
`NullPointerException`. La corrección consiste en generar el `UUID` de la
factura **antes** de construir las líneas, de modo que todos los objetos
se construyan con referencias correctas.

**Sustituye** `EmitirFacturaUseCase.java` completo:

```java
package com.novapay.backend.application.usecase;

import com.novapay.backend.domain.enums.FiscalRecordType;
import com.novapay.backend.domain.enums.InvoiceType;
import com.novapay.backend.domain.enums.RectificationType;
import com.novapay.backend.domain.enums.TaxType;
import com.novapay.backend.domain.entity.*;
import com.novapay.backend.domain.port.*;
import com.novapay.backend.domain.valueObject.InvoiceNumber;
import com.novapay.backend.domain.valueObject.Money;
import com.novapay.backend.domain.valueObject.TaxId;
import com.novapay.backend.interfaces.dto.request.EmitirFacturaRequestDto;
import com.novapay.backend.interfaces.dto.response.InvoiceResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Caso de uso: emitir una factura nueva.
 *
 * Genera el Invoice con sus líneas y breakdowns, lo persiste y crea
 * el FiscalRecord inicial en PENDIENTE_ENVIO con evidencia provisional.
 * El OutboxWorker (Cap.5) completa la evidencia y realiza el envío.
 */
@Service
public class EmitirFacturaUseCase {

    private final InvoiceRepository invoiceRepository;
    private final FiscalRecordRepository fiscalRecordRepository;
    private final CompanyRepository companyRepository;
    private final PosTerminalRepository posTerminalRepository;
    private final InvoiceMapper invoiceMapper;

    public EmitirFacturaUseCase(InvoiceRepository invoiceRepository,
                                 FiscalRecordRepository fiscalRecordRepository,
                                 CompanyRepository companyRepository,
                                 PosTerminalRepository posTerminalRepository,
                                 InvoiceMapper invoiceMapper) {
        this.invoiceRepository = invoiceRepository;
        this.fiscalRecordRepository = fiscalRecordRepository;
        this.companyRepository = companyRepository;
        this.posTerminalRepository = posTerminalRepository;
        this.invoiceMapper = invoiceMapper;
    }

    @Transactional
    public InvoiceResponseDto execute(EmitirFacturaRequestDto request) {

        // ── 1. Validar empresa ────────────────────────────────────────────
        Company company = companyRepository.findById(request.getCompanyId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Empresa no encontrada: " + request.getCompanyId()));

        if (!company.isActive()) {
            throw new BusinessRuleException("COMPANY_INACTIVE",
                    "La empresa no está activa: " + request.getCompanyId());
        }

        // ── 2. Validar terminal ───────────────────────────────────────────
        PosTerminal terminal = posTerminalRepository.findById(request.getTerminalId())
                .orElseThrow(() -> new ResourceNotFoundException(
                        "Terminal no encontrado: " + request.getTerminalId()));

        if (!terminal.getCompanyId().equals(request.getCompanyId())) {
            throw new BusinessRuleException("TERMINAL_COMPANY_MISMATCH",
                    "El terminal no pertenece a la empresa indicada");
        }

        // ── 3. Validar unicidad de serie+número ───────────────────────────
        InvoiceNumber invoiceNumber = InvoiceNumber.of(
                request.getInvoiceSeries(), request.getInvoiceNumber());

        if (invoiceRepository.existsByCompanyAndNumber(company.getId(), invoiceNumber)) {
            throw new DuplicateInvoiceException(
                    request.getInvoiceSeries(), request.getInvoiceNumber());
        }

        // ── 4. Generar el UUID de la factura ANTES de construir las líneas
        //      Las líneas necesitan invoiceId (campo final, requireNonNull).
        UUID invoiceId = UUID.randomUUID();

        // ── 5. Construir líneas con invoiceId correcto ────────────────────
        List<InvoiceLine> lines = buildLines(invoiceId, request);

        // ── 6. Construir breakdowns con invoiceId correcto ────────────────
        List<TaxBreakdown> breakdowns = buildBreakdowns(invoiceId, request);

        // ── 7. Construir la entidad Invoice ───────────────────────────────
        InvoiceType invoiceType = InvoiceType.valueOf(request.getInvoiceType());

        TaxId customerTaxId = (request.getCustomerTaxId() != null
                && !request.getCustomerTaxId().isBlank())
                ? TaxId.of(request.getCustomerTaxId()) : null;

        RectificationType rectType = (request.getRectificationType() != null)
                ? RectificationType.valueOf(request.getRectificationType()) : null;

        List<InvoiceNumber> rectifiedRefs = buildRectifiedInvoices(request);

        // Usamos el constructor completo para pasar el UUID pregenerado
        Invoice invoice = new Invoice(
                invoiceId,
                company.getId(),
                terminal.getId(),
                invoiceNumber,
                invoiceType,
                request.getIssueDateTime(),
                request.getOperationDate(),
                customerTaxId,
                request.getCustomerName(),
                request.getCustomerAddress(),
                rectType,
                rectifiedRefs,
                lines,
                breakdowns,
                Money.of(request.getSubtotal()),
                Money.of(request.getTotalTax()),
                Money.of(request.getTotal()),
                com.novapay.backend.domain.enums.InvoiceStatus.EMITIDA,
                java.time.LocalDateTime.now()
        );

        // ── 8. Validaciones fiscales del dominio ──────────────────────────
        invoice.validateFiscalRequirements();

        // ── 9. Persistir (Invoice + líneas + breakdowns en una transacción)
        Invoice savedInvoice = invoiceRepository.save(invoice);

        // ── 10. Crear FiscalRecord en PENDIENTE_ENVIO ─────────────────────
        //       Los campos de evidencia ("PENDING") los rellena el OutboxWorker.
        FiscalRecord fiscalRecord = FiscalRecord.createPending(
                savedInvoice.getId(),
                company.getTaxAgency(),
                FiscalRecordType.ALTA,
                "1.0",
                null,        // previousHash: lo calcula el worker
                "PENDING",   // currentHash: lo calcula el worker
                "PENDING",   // xmlPayload: lo genera el worker
                "PENDING",   // xmlSignedPayload: lo genera el worker
                "PENDING"    // qrContent: lo genera el worker
        );

        fiscalRecordRepository.save(fiscalRecord);

        // ── 11. Respuesta a Flutter ───────────────────────────────────────
        return invoiceMapper.toResponseDto(savedInvoice, fiscalRecord);
    }

    // ── Métodos privados ──────────────────────────────────────────────────

    private List<InvoiceLine> buildLines(UUID invoiceId,
                                          EmitirFacturaRequestDto request) {
        List<InvoiceLine> lines = new ArrayList<>();
        int order = 1;
        for (EmitirFacturaRequestDto.InvoiceLineRequestDto dto : request.getLines()) {
            lines.add(new InvoiceLine(
                    UUID.randomUUID(),
                    invoiceId,                    // ← UUID real, nunca null
                    order++,
                    dto.getProductCode(),
                    dto.getDescription(),
                    dto.getQuantity(),
                    Money.of(dto.getUnitPrice()),
                    dto.getDiscount() != null ? Money.of(dto.getDiscount()) : Money.ZERO,
                    TaxType.valueOf(dto.getTaxType()),
                    dto.getTaxRate(),
                    Money.of(dto.getTaxBase()),
                    Money.of(dto.getTaxAmount()),
                    dto.getSurchargeRate(),
                    dto.getSurchargeAmount() != null
                            ? Money.of(dto.getSurchargeAmount()) : null,
                    Money.of(dto.getLineTotal())
            ));
        }
        return lines;
    }

    private List<TaxBreakdown> buildBreakdowns(UUID invoiceId,
                                                EmitirFacturaRequestDto request) {
        List<TaxBreakdown> breakdowns = new ArrayList<>();
        for (EmitirFacturaRequestDto.TaxBreakdownRequestDto dto : request.getTaxBreakdowns()) {
            breakdowns.add(new TaxBreakdown(
                    UUID.randomUUID(),
                    invoiceId,                    // ← UUID real, nunca null
                    TaxType.valueOf(dto.getTaxType()),
                    dto.getTaxRate(),
                    Money.of(dto.getTaxBase()),
                    Money.of(dto.getTaxAmount()),
                    dto.getSurchargeRate(),
                    dto.getSurchargeAmount() != null
                            ? Money.of(dto.getSurchargeAmount()) : null
            ));
        }
        return breakdowns;
    }

    private List<InvoiceNumber> buildRectifiedInvoices(EmitirFacturaRequestDto request) {
        if (request.getRectifiedInvoices() == null
                || request.getRectifiedInvoices().isEmpty()) {
            return new ArrayList<>();
        }
        return request.getRectifiedInvoices().stream()
                .map(r -> InvoiceNumber.of(r.getSeries(), r.getNumber()))
                .toList();
    }
}
```

---

## 5.4 FiscalEvidenceService — hash y encadenamiento

Calcula todos los elementos de la evidencia fiscal local.
Va en `com.novapay.backend.infra.fiscal`.

> **Nota sobre el XML en Cap.5:** el XML generado aquí es válido estructuralmente
> pero no cumple los XSD oficiales de AEAT ni TicketBAI. Los adaptadores reales
> (Cap.6 y Cap.7) sobreescriben `buildXml()` con el XML conforme. Este servicio
> proporciona la implementación base para el entorno de desarrollo.

### FiscalEvidenceService.java

```java
package com.novapay.backend.infra.fiscal;

import com.novapay.backend.application.usecase.ResourceNotFoundException;
import com.novapay.backend.domain.entity.Company;
import com.novapay.backend.domain.entity.FiscalRecord;
import com.novapay.backend.domain.entity.Invoice;
import com.novapay.backend.domain.entity.TaxBreakdown;
import com.novapay.backend.domain.port.CompanyRepository;
import com.novapay.backend.domain.port.FiscalRecordRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.format.DateTimeFormatter;
import java.util.HexFormat;

/**
 * Servicio de generación de evidencia fiscal local.
 *
 * Genera el hash de encadenamiento SHA-256, el XML provisional y el QR.
 *
 * HASH — algoritmo base (común a todas las agencias):
 *   SHA-256 de la concatenación con "&" de:
 *     IDEmisorFactura & NumSerieFactura & FechaExpedicionFactura &
 *     TipoFactura & CuotaTotal & ImporteTotal & HuellaAnterior &
 *     FechaHoraHusoGenRegistro
 *
 *   Los adaptadores reales (Cap.6/7) usan exactamente los mismos campos
 *   y orden que exige cada especificación técnica.
 */
@Service
public class FiscalEvidenceService {

    private static final DateTimeFormatter DATE_FMT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");
    private static final DateTimeFormatter DATETIME_FMT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

    private final FiscalRecordRepository fiscalRecordRepository;
    private final CompanyRepository companyRepository;

    public FiscalEvidenceService(FiscalRecordRepository fiscalRecordRepository,
                                  CompanyRepository companyRepository) {
        this.fiscalRecordRepository = fiscalRecordRepository;
        this.companyRepository = companyRepository;
    }

    /**
     * Resuelve el hash del registro anterior del mismo terminal y agencia.
     * Devuelve null si es el primer registro — es correcto y esperado.
     */
    public String resolvePreviousHash(FiscalRecord fiscalRecord, Invoice invoice) {
        return fiscalRecordRepository
                .findLastAcceptedByAgencyAndTerminal(
                        fiscalRecord.getAgency(),
                        invoice.getTerminalId())
                .map(FiscalRecord::getCurrentHash)
                .orElse(null);
    }

    /**
     * Calcula el hash SHA-256 del registro fiscal.
     *
     * El parámetro previousHash puede ser null (primer registro del terminal).
     */
    public String calculateHash(Invoice invoice, String previousHash) {
        Company company = loadCompany(invoice);

        BigDecimal cuotaTotal = invoice.getTaxBreakdowns().stream()
                .map(tb -> tb.getTaxAmount().toFiscalScale())
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        String hashInput = String.join("&",
                company.getTaxId().getValue(),
                invoice.getInvoiceNumber().formatted(),
                invoice.getIssueDateTime().format(DATE_FMT),
                invoice.getInvoiceType().name(),
                cuotaTotal.toPlainString(),
                invoice.getTotal().toFiscalScale().toPlainString(),
                previousHash != null ? previousHash : "",
                invoice.getIssueDateTime().format(DATETIME_FMT)
        );

        return sha256Hex(hashInput);
    }

    /**
     * Genera el XML provisional del registro fiscal.
     * Recibe el currentHash ya calculado para incluirlo en el XML.
     */
    public String buildXml(Invoice invoice, String previousHash, String currentHash) {
        Company company = loadCompany(invoice);

        StringBuilder sb = new StringBuilder();
        sb.append("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
        sb.append("<RegistroFacturacion xmlns=\"http://novapay.com/fiscal/dev\">\n");
        sb.append("  <Cabecera>\n");
        sb.append("    <IDEmisorFactura>")
                .append(escapeXml(company.getTaxId().getValue()))
                .append("</IDEmisorFactura>\n");
        sb.append("    <NumSerieFactura>")
                .append(escapeXml(invoice.getInvoiceNumber().formatted()))
                .append("</NumSerieFactura>\n");
        sb.append("    <FechaExpedicionFactura>")
                .append(invoice.getIssueDateTime().format(DATE_FMT))
                .append("</FechaExpedicionFactura>\n");
        sb.append("    <TipoFactura>")
                .append(invoice.getInvoiceType().name())
                .append("</TipoFactura>\n");
        sb.append("    <HuellaAnterior>")
                .append(previousHash != null ? escapeXml(previousHash) : "")
                .append("</HuellaAnterior>\n");
        sb.append("    <Huella>").append(escapeXml(currentHash)).append("</Huella>\n");
        sb.append("  </Cabecera>\n");
        sb.append("  <DatosFactura>\n");
        sb.append("    <ImporteTotal>")
                .append(invoice.get