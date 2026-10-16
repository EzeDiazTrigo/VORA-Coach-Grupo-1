# Base del proyecto

Registro de lo que se hizo en la rama `feature/project-base` y por qué. Cada punto corresponde a un commit.

## Qué se hizo

### 1. `Set up project base` — limpieza del template
- Se eliminaron `colors.xml`, `strings.xml` y `themes.xml` de `res/values/`.
- El manifest usa el label literal «VORA Coach» y un tema del sistema (`Theme.Material.Light.NoActionBar`).
- El `.gitignore` ahora excluye `google-services.json`, `*.jks`, `*.keystore` y `.kotlin/`.

**Por qué**: no se escribe XML a mano (textos, colores y tema van en Kotlin); las excepciones son el manifest, los íconos y los recursos de Figma. Los valores de ejemplo del template (púrpura/teal) no tienen que quedar en el repo. El `.gitignore` evita subir secretos antes de que existan.

### 2. `Add Hilt and KSP dependencies`
- Catálogo `libs.versions.toml`: Hilt 2.60.1, `hilt-navigation-compose` 1.4.0, KSP 2.3.12.
- Plugins `com.google.dagger.hilt.android` y `com.google.devtools.ksp`; `ksp(hilt-compiler)`.

**Por qué**: Hilt provee las dependencias de red y base de datos mediante módulos. Se usa KSP y no kapt: es el camino moderno y lo que pide la capa 4.

### 3. `Add Hilt application and entry point`
- `App.kt` con `@HiltAndroidApp`, registrada con `android:name=".App"`.
- `MainActivity` con `@AndroidEntryPoint`.

**Por qué**: sin estas dos anotaciones Hilt no genera el grafo. Permite escribir `NetworkModule` y `DatabaseModule`.

**Resultado del spike**: Hilt + KSP **funciona** bajo AGP 9.4.1 y Kotlin 2.2.10 (se generan `Hilt_App` y `Hilt_MainActivity`). No hace falta el fallback con `kapt`.

### 4. `Add Navigation and lifecycle Compose dependencies`
- `navigation-compose` 2.10.2, `lifecycle-viewmodel-compose` y `lifecycle-runtime-compose` 2.11.0.

**Por qué**: `AppNavHost` y todos los `XxxRoute` usan `hiltViewModel` y `collectAsStateWithLifecycle`.

### 5. `Add Retrofit and OkHttp dependencies`
- Retrofit 3.0.0, `converter-gson`, OkHttp 5.5.0 y `logging-interceptor`.

**Por qué**: Se usan para `WorkoutApi` y `NetworkModule`. El interceptor de logging queda como `implementation` y no `debugImplementation`: si `NetworkModule` (en `main`) lo referencia, un `debugImplementation` rompería el build de release. Si se prefiere restringirlo a debug, se mueve a `debugImplementation`.

### 6. `Add Room and coroutines test dependencies`
- Room 2.8.5 (`runtime`, `ktx`, `compiler` con KSP) y `kotlinx-coroutines-test` 1.11.0.

**Por qué**: Se usa para las entidades, DAOs y `AppDatabase`. `coroutines-test` es para testear los ViewModels y repositorios.

### 7. `Add BASE_URL build config field`
- `BuildConfig.BASE_URL = "https://tp3.martinrivas.ar/"` y `buildFeatures.buildConfig = true`.

**Por qué**: la URL debe ser configurable (servidor local `http://10.0.2.2:8000/` para pruebas y para la demo si el mock cae) sin tocar código de red.

### 8. `Add internet permission`
- `<uses-permission android:name="android.permission.INTERNET" />`.

**Por qué**: sin él Retrofit falla en el dispositivo. No se agregó ningún permiso de micrófono ni cámara, porque la app todavía no los usa.

### 9. Tema desde Figma (Capa 2) — `Add shape tokens`, `Add spacing tokens`, `Add Manrope font resource`, `Add Manrope typography`, `Add VORA color scheme and AppTheme`
Fuente: la página «Sistema de diseño» del PDF de Figma.

| Token | Valores | Archivo |
|---|---|---|
| Colores | verde `#123D35` (primary), menta `#65D6B0` (secondary), lima `#C9E86A` (tertiary), fondo `#F7F8F5`, superficie `#FFFFFF`, borde `#E4E9E6` (outlineVariant), texto `#17211F` / `#68736F` / `#9AA39F`, error `#D95D5D`, éxito `#27AE60`, menta translúcida `#65D6B01F` | `Color.kt` |
| Tipografía | Manrope 400/500/600/700; escala 10–32 px (cuerpo 13 y 15, acciones 14, títulos 18/20/22/24) | `Type.kt`, `res/font/manrope.ttf` |
| Radios | 8, 12, 16, 20, 24 y píldora 100 | `Shape.kt` |
| Espaciado | 4, 6, 8, 12, 16, 20 (margen de pantalla), 24, 32, 40 | `Spacing.kt` |

**Por qué**
- **Solo tema claro, sin dynamic color:** Figma no define dark mode; el dynamic color pisaría la identidad verde y rompería la fidelidad con el diseño.
- **`ExtendedColors`:** éxito, texto tenue y menta translúcida no existen en Material 3; se exponen con `MaterialTheme.extendedColors` para que las pantallas no usen literales.
- **`MaterialTheme.spacing`:** las pantallas usan tokens y no dp sueltos.
- **Fuente:** se usó la Manrope variable oficial de Google Fonts (licencia OFL). Es provisoria hasta contar con los `.ttf` exportados de Figma; si se reemplaza `manrope.ttf` sin tocar `Type.kt` más que los pesos. En Android < 8 (minSdk 24) la fuente variable no aplica el peso, por lo que se ve en peso regular.
- **Nombre:** `TP3ParcialGrupo1Theme` pasó a `AppTheme`.
- El frame de Figma es de 390 px de ancho, con márgenes de 20 px.

## Cómo se verificó
- Después de cada cambio de dependencias: `./gradlew :app:assembleDebug` verde.
- Tras agregar Room: también `:app:testDebugUnitTest` verde.
- Se confirmó que `BuildConfig.BASE_URL` se genera y que existen las clases `Hilt_*`.

## Entorno
- No hay `local.properties` versionado (está en `.gitignore`). Para compilar desde terminal: `ANDROID_HOME=~/Android/Sdk sh gradlew :app:assembleDebug` (`gradlew` no tiene bit de ejecución en este checkout).

## Pendiente
- Componentes de formularios: `AppScaffold`, `AppButton(isLoading)`, `AppTextField`, `PasswordTextField`, `AppCard` con previews, y `Strings` común.
