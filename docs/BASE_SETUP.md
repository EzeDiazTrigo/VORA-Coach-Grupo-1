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

## Cómo se verificó
- `./gradlew :app:assembleDebug` y `:app:testDebugUnitTest` verdes.
- Se confirmó que existen las clases `Hilt_*`.
- El tema se revisó en el emulador Pixel_9 con `ThemeShowcase`.

## Entorno
- No hay `local.properties` versionado (está en `.gitignore`). Para compilar desde terminal: `ANDROID_HOME=~/Android/Sdk sh gradlew :app:assembleDebug` (`gradlew` no tiene bit de ejecución en este checkout).

## Pendiente
- Componentes de formularios y `Strings` común.
- Fuente Manrope: se incorpora cuando se exporte el `.ttf` de Figma a `res/font/` (hoy `AppFontFamily` usa la del sistema).
