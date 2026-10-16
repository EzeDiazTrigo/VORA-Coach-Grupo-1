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

### 7. Tema desde Figma — `Add shape tokens`, `Add spacing tokens`, `Add Manrope typography`, `Add VORA color scheme and AppTheme`
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

### 8. Componentes y Acceso
- **Componentes (`ui/components/`)**: `AppButton` (estilos principal, lima y contorno; `isLoading`, `trailingIcon`, `isCompact`), `AppCard` (tarjeta y panel verde), `AppTextField`, `PasswordTextField` y `AppScaffold`. Son sin estado, reciben `modifier` y toman todo del tema, y cada uno tiene su `@Preview`.
- **`Strings` y `AuthStrings`**: textos compartidos y de Acceso. Los textos de diseño se mantienen como en Figma (inglés); los mensajes de error y de modo demostración van en español.
- **Íconos extendidos**: `material-icons-extended` aporta el ojo de contraseña, el candado y el sobre.
- **Acceso**: `LoginScreen` y `RegisterScreen` (sin estado) con `LoginRoute` y `RegisterRoute`. Los `Route` reciben lambdas de navegación y no un `NavController`, así se conectan a las rutas sin tocar las pantallas.
- **Autenticación de demostración**: `AuthRepository` con `DemoAuthRepository` (acepta credenciales válidas tras una espera simulada), enlazado con `@Binds` en `AuthModule`. Se reemplaza por una implementación real sin tocar los `ViewModel`.
- **Validación**: funciones puras (`validateEmail`, `validatePassword`, `validateFullName`) con tests, más tests de `LoginViewModel` con un repositorio falso.
- **Provisorio**: el logo y los botones de Apple y Google usan marcadores hasta contar con los recursos exportados de Figma.

## Cómo se verificó
- Después de cada cambio de dependencias: `./gradlew :app:assembleDebug` verde.
- Tras agregar Room y las pantallas de Acceso: también `:app:testDebugUnitTest` verde (10 tests).
- Los componentes y las pantallas de Acceso se revisaron en el emulador Pixel_9.
- Se confirmó que existen las clases `Hilt_*`.

## Entorno
- No hay `local.properties` versionado (está en `.gitignore`). Para compilar desde terminal: `ANDROID_HOME=~/Android/Sdk sh gradlew :app:assembleDebug` (`gradlew` no tiene bit de ejecución en este checkout).

## Pendiente
- Pantallas de Inicio (Dashboard y Notificaciones) y de Perfil.
- Conectar las lambdas de Acceso a las rutas de navegación.
- Reemplazar los marcadores por los recursos exportados de Figma, incluida la fuente Manrope.
