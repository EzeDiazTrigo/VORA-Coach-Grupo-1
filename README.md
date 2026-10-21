# VORA Coach — TP3 Grupo 1

App Android en Kotlin y Jetpack Compose basada en el diseño de VORA Coach (Desarrollo de Aplicaciones Móviles, Universidad ORT Argentina, 2026). Recorre los 7 flujos del diseño (Acceso, Inicio, Asistente VORA, Entrenamiento, Nutrición, Progreso y Perfil), con Retrofit y Room en el catálogo de entrenamientos y el registro de comidas.

- **Diseño:** [Figma — Parcial Domiciliario 7 Oct 26](https://www.figma.com/design/1nvbbfYNYmKAevuaG28J85/Parcial-Domiciliario-7-Oct-26)
- **Inventario de pantallas:** [docs/INVENTORY.md](docs/INVENTORY.md)
- **Base, tema y componentes:** [docs/BASE_SETUP.md](docs/BASE_SETUP.md)
- **Recursos exportados de Figma:** [docs/FIGMA.md](docs/FIGMA.md)

## Integrantes

| Integrante | GitHub | Área |
|---|---|---|
| Lucas Beronne | [@luckberonne](https://github.com/luckberonne) | Base, Hilt, tema, componentes de formulario, Acceso, Inicio, Perfil |
| Rodrigo Sanz | [@rodrisanz-ort](https://github.com/rodrisanz-ort) | Navegación, inventario, README, Progreso |
| Blas Laborde | [@blaslaborde](https://github.com/blaslaborde) | Entrenamiento, Retrofit, caché en Room |
| Ezequiel Diaz Trigo | [@EzeDiazTrigo](https://github.com/EzeDiazTrigo) | Nutrición, Room |
| Santino Menin | [@santinomenin-ort](https://github.com/santinomenin-ort) | Componentes de estado, barra inferior, recursos de Figma, Asistente VORA, QA visual |

## Cómo ejecutar

### Requisitos

- Android Studio (con su JBR como JDK de Gradle).
- Un emulador de **≈ 390 dp de ancho** como referencia de Figma (por ejemplo Pixel 7) y otro de tamaño distinto para verificar la adaptación.

### Compilar e instalar

Desde `TP3ParcialGrupo1/`:

```sh
./gradlew :app:assembleDebug        # compila
./gradlew :app:testDebugUnitTest    # tests unitarios
./gradlew :app:installDebug         # instala en el emulador conectado
```

También se puede abrir `TP3ParcialGrupo1/` en Android Studio y ejecutar la configuración `app`.

### Servidor

La app consume la VORA Mock API (sin autenticación).

| Opción | URL |
|---|---|
| Publicado | `https://tp3.martinrivas.ar/` (Swagger en `/docs`) |
| Local | `python3 mock/server.py` del paquete del docente → `http://localhost:8000` (desde el emulador: `http://10.0.2.2:8000/`) |

> **Pendiente (Entrenamiento):** dónde se configura `BASE_URL` y cómo cambiarla entre el servidor publicado y el local.

## Versiones

| Herramienta | Versión |
|---|---|
| Android Gradle Plugin | 9.4.1 |
| Gradle | 9.6.0 |
| Kotlin | 2.2.10 |
| KSP | 2.3.12 |
| Compose BOM | 2026.02.01 |
| Navigation Compose | 2.10.2 |
| Hilt | 2.60.1 |
| Retrofit / OkHttp | 3.0.0 / 5.5.0 |
| Room | 2.8.5 |
| SDK | min 24 · target 37 |

## Arquitectura

```
com.example.tp3_parcial_grupo_1
├─ navigation/     rutas (AppDestination), AppNavHost y un grafo por área
├─ ui/theme/       colores, tipografía, formas y espaciado del diseño
├─ ui/components/  componentes compartidos (botones, tarjetas, campos, anillo de progreso…)
├─ ui/strings/     textos visibles, uno por área
├─ ui/screens/     una carpeta por área: XxxRoute → XxxScreen + XxxViewModel + XxxUiState
├─ data/           repositorios, Retrofit (remote), Room (local) y datos de demostración (demo)
└─ di/             módulos de Hilt
```

- Cada pantalla tiene un `XxxRoute`, que obtiene su `ViewModel` con `hiltViewModel()` y observa el estado con `collectAsStateWithLifecycle`, y un `XxxScreen` sin estado con `@Preview`. Las pantallas no reciben el `NavController`: exponen callbacks que conecta la navegación.
- Los datos que no vienen de Retrofit ni Room están en `data/demo/` y se identifican como demostración.

> **Pendiente:** flujo DTO → modelo → Entity, repositorios y qué objetos construye Hilt y su relación con Dagger.

## Navegación

- La app arranca en **Login**. Iniciar sesión, registrarse o entrar en modo demostración lleva a Home y saca Acceso del historial: atrás desde Home cierra la app. Cerrar sesión desde Settings vuelve a Login y limpia el historial.
- **Barra inferior:** Home, Workout, Nutrition, Progress y Profile. Se muestra solo en las pantallas que la tienen en Figma, cada tab conserva la última pantalla visitada y atrás desde otro tab vuelve a Home.
- **Selección coherente:** el detalle de entrenamiento recibe su `workoutId`, la guía de ejercicio `workoutId` y `exerciseId`, y el detalle de comida su `mealId`.
- **Recorridos que Figma no define:** el diseño no tiene interacciones de prototipo, así que donde no hay un control explícito el equipo eligió el destino:
  - El tab **Progress** abre *Your progress*; *Today's Overview* se abre desde la sección homónima de Home.
  - Cada notificación abre su área: revisión semanal, plan semanal, nutrición y progreso de entrenamiento.

## Estado por funcionalidad

| Área | Pantallas | Estado previsto |
|---|---|---|
| Acceso | 2 | Demostración (sin Firebase) |
| Inicio | 2 | Demostración |
| Asistente VORA | 6 estados | Demostración (conversación simulada, sin permiso de micrófono) |
| Entrenamiento | 7 | **Funcional:** catálogo y detalle con Retrofit y caché en Room · resto demostración |
| Nutrición | 8 | **Funcional:** carga manual, listado por fecha, eliminación y total diario en Room · voz y foto simulados |
| Progreso | 6 | Demostración |
| Perfil | 6 | Demostración (perfil local) |

El detalle por pantalla (nombre en Figma, ruta, barra inferior y estado) está en el [inventario](docs/INVENTORY.md).

## Idioma de la interfaz

Los textos visibles siguen el idioma del diseño de Figma (inglés), incluidos los mensajes que el diseño no muestra, para no mezclar idiomas en una misma pantalla. El código y los identificadores están en inglés.

## Video de demostración

> Pendiente.

## Comparaciones visuales

> Pendiente: cinco comparaciones Figma vs. app.

## Uso de IA generativa

> Pendiente: declaración redactada por el equipo.
