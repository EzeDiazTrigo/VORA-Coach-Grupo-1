# Base del proyecto

Registro de lo que se hizo en la rama `feature/project-base` y por qué. Cada punto corresponde a un commit.

## Qué se hizo

### 1. Limpieza del template
- Se eliminaron `colors.xml`, `strings.xml` y `themes.xml` de `res/values/`.
- El manifest usa el label literal «VORA Coach» y un tema del sistema (`Theme.Material.Light.NoActionBar`).
- El `.gitignore` ahora excluye `google-services.json`, `*.jks`, `*.keystore` y `.kotlin/`.

**Por qué**: no se escribe XML a mano (textos, colores y tema van en Kotlin); las excepciones son el manifest, los íconos y los recursos de Figma. Los valores de ejemplo del template (púrpura/teal) no tienen que quedar en el repo. El `.gitignore` evita subir secretos antes de que existan.

### 2. Hilt y dependencias
- Catálogo `libs.versions.toml` con Hilt 2.60.1, KSP 2.3.12, `hilt-navigation-compose`, Navigation 2.10.2, lifecycle Compose 2.11.0, Retrofit 3.0.0, OkHttp 5.5.0, Room 2.8.5 y `coroutines-test`.
- `App` con `@HiltAndroidApp` (registrada en el manifest) y `MainActivity` con `@AndroidEntryPoint`.

**Por qué**: las dependencias se cargan juntas para que el resto del equipo no tenga que tocar los archivos de Gradle. Se usa KSP y no kapt.

**Resultado del spike**: Hilt + KSP **funciona** bajo AGP 9.4.1 y Kotlin 2.2.10 (se generan `Hilt_App` y `Hilt_MainActivity`); no hace falta el fallback con `kapt`.

### 3. Tema desde Figma
Fuente: la página «Sistema de diseño» del PDF de Figma.

| Token | Valores | Archivo |
|---|---|---|
| Colores | verde `#123D35` (primary), menta `#65D6B0` (secondary), lima `#C9E86A` (tertiary), fondo `#F7F8F5`, superficie `#FFFFFF`, borde `#E4E9E6` (outlineVariant), texto `#17211F` / `#68736F` / `#9AA39F`, error `#D95D5D`, éxito `#27AE60`, menta translúcida `#65D6B01F` | `Color.kt` |
| Tipografía | Manrope 400/500/600/700; escala 10–32 px (cuerpo 13 y 15, acciones 14, títulos 18/20/22/24) | `Type.kt` |
| Radios | 8, 12, 16, 20, 24 y píldora 100 | `Shape.kt` |
| Espaciado | 4, 6, 8, 12, 16, 20 (margen de pantalla), 24, 32, 40 | `Spacing.kt` |

**Por qué**
- **Solo tema claro, sin dynamic color:** Figma no define dark mode; el dynamic color pisaría la identidad verde y rompería la fidelidad con el diseño.
- **`ExtendedColors`:** éxito, texto tenue y menta translúcida no existen en Material 3; se exponen con `MaterialTheme.extendedColors` para que las pantallas no usen literales.
- **`MaterialTheme.spacing`:** las pantallas usan tokens y no dp sueltos.
- **Fuente:** la escala usa `AppFontFamily` (fuente del sistema por ahora). Manrope se aplica cuando se agregue el `.ttf` exportado de Figma a `res/font/`, cambiando solo esa línea de `Type.kt`.
- **Nombre:** `TP3ParcialGrupo1Theme` pasó a `AppTheme`.
- El frame de Figma es de 390 px de ancho, con márgenes de 20 px.

### 4. Componentes de formularios y `Strings`
- **Componentes (`ui/components/`)**: `AppButton` (estilos principal, lima y contorno; `isLoading`, `trailingIcon`, `isCompact`), `AppCard` (tarjeta y panel verde), `AppTextField`, `PasswordTextField` y `AppScaffold`. Son sin estado, reciben `modifier` y toman todo del tema; cada uno tiene su `@Preview`.
- **`Strings`**: textos compartidos (volver, reintentar, error genérico, modo demostración).
- **Íconos extendidos**: `material-icons-extended` aporta el ojo de contraseña, el candado y el sobre.

**Por qué**: las pantallas se arman componiendo estos elementos en lugar de repetir campos y botones, y así mantienen la fidelidad con Figma desde un solo lugar.

### 5. Acceso
- **Pantallas**: `LoginScreen` y `RegisterScreen` (sin estado, con `@Preview`) y sus `LoginRoute` y `RegisterRoute` con `hiltViewModel`. Los `Route` reciben lambdas de navegación y no un `NavController`, así se conectan a las rutas sin tocar las pantallas.
- **Validación**: funciones puras (`validateEmail`, `validatePassword`, `validateFullName`) con tests, y tests del `LoginViewModel`.
- **Modo demostración**: las credenciales válidas entran sin servidor; los botones sociales, «Forgot your password?» y «Recover» informan «modo demostración».
- **Textos**: `AuthStrings` mantiene los textos del diseño de Figma; los mensajes de error van en español.
- **Provisorio**: el logo y los botones de Apple y Google usan marcadores hasta contar con los recursos exportados de Figma.

### 6. Inicio
- **Dashboard** (`HomeScreen`) y **Notificaciones** (`NotificationsScreen`), sin estado y con `@Preview`, con sus `Route` y `ViewModel`. Los `Route` reciben lambdas de navegación (campana, sesión de voz, entrenamiento, registrar comida) y no un `NavController`.
- **Datos de demostración**: `data/demo/HomeDemoData.kt`, identificados como demostración. Las notificaciones se pueden marcar como leídas (una o todas).
- **Barra inferior**: no se incluye aquí; se agrega cuando exista el componente compartido.
- **Provisorio**: el avatar usa las iniciales hasta contar con la foto exportada de Figma.

### 7. Perfil
- **Pantallas** (`ui/screens/profile/`): Dashboard, Editar perfil, Objetivos y preferencias, Preferencias nutricionales, Voz e IA y Configuración, cada una con su `Route`, `Screen` sin estado, `UiState`, `ViewModel` y `@Preview`. Los `Route` reciben lambdas de navegación.
- **Perfil de demostración**: `ProfileRepository` (`@Singleton`, en memoria) guarda el perfil, las preferencias nutricionales y los ajustes de voz; los datos iniciales están en `data/demo/ProfileDemoData.kt`.
- **Componentes nuevos**: `AppChip`, `AppOptionCard`, `AppDropdownField` y `AppSwitchRow`.
- **Validación**: `ProfileValidation.kt` (nombres, fecha con el formato del diseño, altura y peso) con tests.
- **Navegación**: `profileGraph` (`navigation/ProfileGraph.kt`). Desde el Dashboard: engranaje y «Account» → Configuración, «Edit profile» → Editar perfil, «Your Goals» → Objetivos, «Your Preferences» → Pref. nutricionales, «Review preferences» → Voz e IA. «Connected data» y la fila de privacidad de Voz e IA informan «modo demostración». Cerrar sesión vuelve a Acceso y limpia el historial.

**Por qué**: Dashboard y pantallas de edición comparten un único perfil, así que lo que se guarda en una se ve en las otras. Los ajustes de Voz e IA se aplican al instante porque el diseño no tiene botón de guardar. Los controles sin destino informan «modo demostración».

## Cómo se verificó
- `./gradlew :app:assembleDebug` y `:app:testDebugUnitTest` verdes.
- Se confirmó que existen las clases `Hilt_*`.
- El tema y los componentes se revisaron en el emulador Pixel_9.

## Entorno
- No hay `local.properties` versionado (está en `.gitignore`). Para compilar desde terminal: `ANDROID_HOME=~/Android/Sdk sh gradlew :app:assembleDebug` (`gradlew` no tiene bit de ejecución en este checkout).

## Pendiente
- Conectar las lambdas de Acceso a las rutas de navegación.
- Fuente Manrope: se incorpora cuando se exporte el `.ttf` de Figma a `res/font/` (hoy `AppFontFamily` usa la del sistema).
