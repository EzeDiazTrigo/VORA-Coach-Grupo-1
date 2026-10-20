# Figma — referencia y recursos exportados

Diseño: [Parcial Domiciliario 7 Oct 26](https://www.figma.com/design/1nvbbfYNYmKAevuaG28J85/Parcial-Domiciliario-7-Oct-26) (página `0:1`).

## Frame de referencia

- Todas las pantallas miden **390 × 844** (iPhone 14/15). Las que superan 844 de alto son pantallas con scroll (Dashboard de Home 1009, Profile 1171, Settings 1309, etc.).
- Emulador de referencia sugerido: un dispositivo de **≈ 390 dp de ancho** (ej. Pixel 7: 412 × 915 dp es el más cercano de los predefinidos; o un perfil custom 390 × 844 dp).
- El status bar (`9:41`, señal, wifi, batería) y el home indicator son de iOS: **no se dibujan**, se usan las barras del sistema Android (edge-to-edge).
- Los textos del diseño están en inglés; en la app se muestran en español (ver PLAN §2).

## Secciones y pantallas (37)

| Sección | Pantalla (nombre en Figma) | Nodo |
|---|---|---|
| Login Flow | Login - 01 · SignIn | `2007:214` |
| | Login - 02 · SignUp | `4002:2` |
| Home Flow | Home / 01 · Dashboard | `2007:3` |
| | Home / 02 · Notifications | `2007:127` |
| AI Voice Flow | Voice / 01 · Idle | `1:1624` |
| | Voice / 02 · Listening | `1:1372` |
| | Voice / 03 · Processing | `1:1419` |
| | Voice / 04 · Speaking | `1:1459` |
| | Voice / 05 · Conversation | `1:1512` |
| | Voice / 06 · Error | `1:1579` |
| Workout Flow | Workout / 01 · Dashboard | `1:213` |
| | Workout / 02 · Weekly Plan | `1:328` |
| | Workout / 03 · Workout Details | `1:424` |
| | Workout / 04 · Pre Workout | `1:510` |
| | Workout / 05 · Active Workout | `1:554` |
| | Workout / 06 · Exercise Details | `1:628` |
| | Workout / 07 · Workout Complete | `1:673` |
| Nutrition Flow | Nutrition / 01 · Dashboard | `1:730` |
| | Nutrition / 02 · Today's Meals | `1:855` |
| | Nutrition / 03 · Add Food | `1:953` |
| | Nutrition / 04 · Voice Food Logging | `1:1038` |
| | Nutrition / 05 · Food Analysis | `1:1077` |
| | Nutrition / 06 · Photo Analysis | `1:1121` |
| | Nutrition / 07 · Food Confirmation | `1:1185` |
| | Nutrition / 08 · Meal Details | `1:1284` |
| Progress Flow | Progress / 01 · Today's Overview | `1:1682` |
| | Progress / 02 · Dashboard | `1:1775` |
| | Progress / 03 · Workout Progress | `1:1904` |
| | Progress / 04 · Nutrition Progress | `1:2032` |
| | Progress / 05 · Health Trends | `1:2156` |
| | Progress / 06 · Weekly Review | `1:2287` |
| Profile Flow | Profile / 01 · Dashboard | `1:2382` |
| | Profile / 02 · Edit Profile | `1:2514` |
| | Profile / 03 · Goals & Preferences | `1:2572` |
| | Profile / 04 · Nutrition Preferences | `1:2655` |
| | Profile / 05 · Voice & AI Settings | `1:2785` |
| | Profile / 06 · Settings | `1:2879` |

Sistema de diseño (sección `2004:1966`): Colores `2004:1396`, Tipografía `2004:1512`, Espaciado y radios `2004:1649`, Elementos de interfaz `2004:1779`.

## Recursos en `res/`

Convertidos desde los SVG de Figma a VectorDrawable. Son los únicos XML de UI permitidos (PLAN §3).

### Íconos de línea (Lucide)

El diseño usa el set **Lucide** (trazo 2, extremos redondeados; verificado: el `mic` de Figma es el de Lucide escalado de 24 a 28). Se exportan en negro para teñirlos desde Compose:

```kotlin
Icon(painterResource(R.drawable.ic_mic), contentDescription = null, tint = MaterialTheme.colorScheme.onPrimary)
```

`ic_activity`, `ic_alert_circle`, `ic_apple`, `ic_arrow_right`, `ic_audio_waveform`, `ic_bed`, `ic_bell`, `ic_calendar`, `ic_camera`, `ic_chart_line`, `ic_check`, `ic_chevron_down`, `ic_chevron_left`, `ic_chevron_right`, `ic_clock`, `ic_close`, `ic_compass`, `ic_dumbbell`, `ic_edit`, `ic_eye`, `ic_flame`, `ic_help_circle`, `ic_home`, `ic_info`, `ic_loader_circle`, `ic_lock`, `ic_mail`, `ic_mic`, `ic_more_horizontal`, `ic_navigation`, `ic_pause`, `ic_pen`, `ic_play`, `ic_plus`, `ic_repeat_1`, `ic_search`, `ic_send`, `ic_settings`, `ic_shield`, `ic_shield_check`, `ic_sliders`, `ic_sparkles`, `ic_star`, `ic_stop_circle`, `ic_target`, `ic_trending_up`, `ic_trophy`, `ic_user`, `ic_volume_2`, `ic_x_circle`.

Los nombres siguen las capas de Figma (`home`, `more-horizontal`, `x-circle`…) aunque Lucide los haya renombrado (`house`, `ellipsis`, `circle-x`).

### Íconos de Acceso (rellenos, exportados de Figma)

| Recurso | Uso en Figma |
|---|---|
| `ic_field_mail`, `ic_field_lock`, `ic_field_eye` | Íconos dentro de los campos de Iniciar sesión |
| `ic_person_filled`, `ic_mail_filled`, `ic_lock_filled` | Campos de Crear cuenta |
| `ic_visibility` | Mostrar contraseña |
| `ic_verified_user`, `ic_check_circle_filled` | Insignias de seguridad / requisitos cumplidos |
| `ic_arrow_forward` | Botón principal de Acceso |
| `ic_face_id` | Ingreso biométrico (demostración) |
| `ic_logo_google`, `ic_logo_apple` | Botones sociales. **Usar `Image`, no `Icon`** (conservan sus colores) |

### Imágenes

| Recurso | Uso |
|---|---|
| `drawable-nodpi/img_avatar_demo.png` | Avatar del perfil de demostración (Home, Profile) |
| `drawable-nodpi/img_meal_demo.jpg` | Foto del plato (Nutrition / 05 · Food Analysis, 06 · Photo Analysis). Las etiquetas «Grilled Chicken (~180g)» / «Basmati Rice (~150g)» van superpuestas en Compose |
| `drawable-nodpi/img_exercise_demo.jpg` | Imagen del ejercicio (Workout / 06 · Exercise Details, «Seated Cable Row») |

Las fotos se redujeron a 1050 px de ancho (350 dp × 3) en JPEG 85 % para no inflar el APK.

### Tipografía

**Manrope** en toda la app (frame Tipografía `2004:1512`), pesos Regular 400, Medium 500, SemiBold 600 y Bold 700, en `res/font/manrope_{regular,medium,semibold,bold}.ttf` (estáticos de Google Fonts; licencia SIL OFL 1.1 en [MANROPE_OFL.txt](MANROPE_OFL.txt)).

Jerarquía observada en Figma (para `Type.kt`, A):

| Uso | Tamaño · peso |
|---|---|
| Cifra destacada | 24 px · SemiBold (también hay cifras de 32) |
| Título de pantalla | 20 px · Bold (titulares de 22, 26 y 28 en algunas pantallas) |
| Título de tarjeta | 18 px · SemiBold |
| Acción principal | 14 px · SemiBold |
| Acción contextual | 13 px · SemiBold |
| Cuerpo | 13 px · Regular, interlineado auto (algunos 13/18) |
| Etiqueta (`TODAY`, mayúsculas) | 10 px · SemiBold |
| Tiempo (`2 hours ago`) | 11 px · Regular |

Escala habitual: 10, 11, 12, 13, 14, 15, 16, 18, 20 y 24 px (1 px de Figma = 1 dp/sp).

### Lo que se dibuja en Compose (no es recurso)

- **Orbe de VORA** (`vora-orb`: `orb-glow` radial, `orb-ring`, `orb-core` con degradado, `orb-ripple` en Listening/Speaking): cambia de tamaño, opacidad y anillos según el estado, así que se dibuja con `Canvas` + `Brush.radialGradient`/`sweepGradient` en un componente `VoraOrb(state)`.
- Barra de progreso del audio (Speaking), separadores, puntos de estado (`ONLINE`, `LIVE AUDIO`).

### Pendiente

- Íconos de Acceso: se dejan con el tamaño exportado de Figma (recortados al dibujo); darles `Modifier.size(...)` igual al del diseño.
- Íconos sin nombre en Figma (`icon-frame`, 65 instancias en Workout/Nutrition/Progress): cada dueño de área los mapea a un ícono Lucide de la lista o pide que se agregue.

## Componentes repetidos (para `ui/components`)

| Componente | Dónde aparece | Dueño |
|---|---|---|
| Encabezado: botón atrás circular + título + etiqueta de estado (`ONLINE`, `SPEAKING`) | Voice, Workout, Nutrition, Profile | A (`AppScaffold`/top bar) |
| Barra de navegación inferior: Home, Workout, Nutrition, Progress, Profile (`ic_home`, `ic_dumbbell`, `ic_apple`, `ic_chart_line`, `ic_user`) | Dashboards de cada área | E (`BottomNavBar`) |
| Botón píldora principal (lima `Tap to Interrupt`, menta `Try again`, verde oscuro) | Todas | A (`AppButton`) |
| Enlace de texto subrayado (`Type instead`) | Voice, Login | A |
| Tarjeta blanca redondeada con borde suave | Todas | A (`AppCard`) |
| Chip de sugerencia (borde, radio completo) | Voice Idle, Nutrition | E |
| Etiqueta de sección en mayúsculas pequeñas (`SUGGESTED QUESTIONS`, `LAST CONVERSATION`) | Todas | A (tipografía `labelSmall`) |
| Orbe de VORA | Voice, Home, recomendaciones de Workout/Nutrition | E (`VoraOrb`) |
| Botón circular de micrófono | Voice, Nutrition / Voice Food Logging | E |
| Burbujas de chat + barra de entrada (`+`, campo, mic, enviar) | Voice / Conversation | E |
| Tarjeta de alerta con acciones (`Allow Access` / `Not Now`) | Voice / Error | E (`ErrorState`) |
| Indicador de carga circular (`loader-circle`) + texto | Voice / Processing | E (`LoadingIndicator`) |
