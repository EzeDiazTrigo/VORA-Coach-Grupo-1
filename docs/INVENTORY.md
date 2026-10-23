# Inventario de pantallas

Referencia de cobertura (NAV01). Nombres tal como figuran en Figma; nodos en [FIGMA.md](FIGMA.md). Rutas definidas en `navigation/AppDestination.kt`.

Estado previsto: **funcional** (datos reales: Retrofit/Room) · **demostración** (datos de ejemplo identificados). Cada dueño detalla contenido y acciones de su área en una sección propia, como la de Asistente VORA.

## Cobertura (37 pantallas)

| # | Figma | Ruta | Barra inferior | Estado | Dueño |
|---|---|---|---|---|---|
| 1 | Login - 01 — SignIn | `auth/login` | no | demostración | A |
| 2 | Login - 02 — SignUp | `auth/register` | no | demostración | A |
| 3 | Home / 01 — Dashboard | `home/dashboard` | sí | demostración | A |
| 4 | Home / 02 — Notifications | `home/notifications` | sí | demostración | A |
| 5 | Voice / 01 — Idle | `coach` (estado `Idle`) | no | demostración | E |
| 6 | Voice / 02 — Listening | `coach` (estado `Listening`) | no | demostración | E |
| 7 | Voice / 03 — Processing | `coach` (estado `Processing`) | no | demostración | E |
| 8 | Voice / 04 — Speaking | `coach` (estado `Reply`) | no | demostración | E |
| 9 | Voice / 05 — Conversation | `coach` (estado `Conversation`) | no | demostración | E |
| 10 | Voice / 06 — Error | `coach` (estado `MicError`) | no | demostración | E |
| 11 | Workout / 01 — Dashboard | `workout/dashboard` | sí | funcional (catálogo Retrofit + caché Room) | C |
| 12 | Workout / 02 — Weekly Plan | `workout/weekly_plan` | sí | demostración | C |
| 13 | Workout / 03 — Workout Details | `workout/detail/{workoutId}` | no | funcional (detalle Retrofit + caché Room) | C |
| 14 | Workout / 04 — Pre Workout | `workout/pre/{workoutId}` | no | demostración | C |
| 15 | Workout / 05 — Active Workout | `workout/active/{workoutId}` | no | demostración | C |
| 16 | Workout / 06 — Exercise Details | `workout/exercise/{workoutId}/{exerciseId}` | no | demostración | C |
| 17 | Workout / 07 — Workout Complete | `workout/complete/{workoutId}` | no | demostración | C |
| 18 | Nutrition / 01 — Dashboard | `nutrition/dashboard` | sí | funcional (total diario desde Room) | D |
| 19 | Nutrition / 02 — Today's Meals | `nutrition/todays_meals` | sí | funcional (listado por fecha en Room) | D |
| 20 | Nutrition / 03 — Add Food | `nutrition/add_food` | sí | funcional (carga manual en Room) | D |
| 21 | Nutrition / 04 — Voice Food Logging | `nutrition/voice_logging` | no | demostración | D |
| 22 | Nutrition / 05 — Food Analysis | `nutrition/food_analysis` | no | demostración | D |
| 23 | Nutrition / 06 — Photo Analysis | `nutrition/photo_analysis` | sí | demostración | D |
| 24 | Nutrition / 07 — Food Confirmation | `nutrition/food_confirmation` | sí | funcional (guarda en Room) | D |
| 25 | Nutrition / 08 — Meal Details | `nutrition/meal/{mealId}` | sí | funcional (detalle y eliminación en Room) | D |
| 26 | Progress / 01 — Today's Overview | `progress/todays_overview` | sí | demostración | B |
| 27 | Progress / 02 — Dashboard | `progress/dashboard` | sí | demostración | B |
| 28 | Progress / 03 — Workout Progress | `progress/workout` | sí | demostración | B |
| 29 | Progress / 04 — Nutrition Progress | `progress/nutrition` | sí | demostración | B |
| 30 | Progress / 05 — Health Trends | `progress/health_trends` | sí | demostración | B |
| 31 | Progress / 06 — Weekly Review | `progress/weekly_review` | no | demostración | B |
| 32 | Profile / 01 — Dashboard | `profile/dashboard` | sí | demostración | A |
| 33 | Profile / 02 — Edit Profile | `profile/edit` | no | demostración | A |
| 34 | Profile / 03 — Goals & Preferences | `profile/goals` | no | demostración | A |
| 35 | Profile / 04 — Nutrition Preferences | `profile/nutrition_preferences` | no | demostración | A |
| 36 | Profile / 05 — Voice & AI Settings | `profile/voice_ai` | no | demostración | A |
| 37 | Profile / 06 — Settings | `profile/settings` | no | demostración | A |

## Navegación general

- **Inicio:** la app arranca en Login. Entrar (o el modo demostración) lleva a Home y saca Acceso del historial: atrás desde Home cierra la app.
- **Barra inferior:** Home, Workout, Nutrition, Progress y Profile. Se muestra solo en las pantallas que la tienen en Figma (columna «Barra inferior»). Cada tab conserva la última pantalla visitada y atrás desde un tab vuelve a Home.
- **Selección coherente (NAV04):** el detalle de entrenamiento recibe su `workoutId`, la guía de ejercicio `workoutId` + `exerciseId` y el detalle de comida su `mealId`.
- **Cerrar sesión:** desde Settings vuelve a Login y limpia el historial.

## Asistente VORA (E)

Las seis pantallas son estados de un mismo destino (`coach`), modelados con `CoachUiState`. La conversación es simulada y no se pide permiso de micrófono (NAV05): «Toca para hablar» avanza por los estados con datos de demostración.

| Figma | Estado en la app | Contenido visible | Acciones → destino | Estado |
|---|---|---|---|---|
| Voice / 01 — Idle | `Idle` | Encabezado «VORA Coach» + etiqueta `ONLINE`; orbe; «How can I help?» + subtítulo; «Suggested questions» (4 chips: Start my workout, Log my breakfast, What should I eat tonight?, How am I doing?); tarjeta «Last conversation» (hace 2 h); botón de micrófono «Tap to talk»; enlace «Type instead» | Atrás → destino anterior · Mic → Listening · Chip → Processing con esa pregunta · Última conversación / Type instead → Conversation | demostración |
| Voice / 02 — Listening | `Listening` | Fondo verde oscuro; «Listening» + `LIVE AUDIO`; orbe con anillos; tarjeta «Live transcript» con el texto que se va dictando; «Speak naturally. I'm listening.»; botón mic activo; «Cancel» | Atrás / Cancel → Idle · al terminar la transcripción simulada → Processing | demostración |
| Voice / 03 — Processing | `Processing` | «Processing»; orbe; «Thinking…»; tarjeta con la frase transcripta; indicador «Checking your nutrition target…»; «Cancel» | Atrás / Cancel → Idle · al terminar → Speaking | demostración |
| Voice / 04 — Speaking | `Reply` | «VORA Speaking» + `SPEAKING`; orbe; tarjeta con la respuesta; barra de progreso del audio (0:08 / -0:14); controles repetir, pausa, detener; botón «Tap to Interrupt» | Atrás / Tap to Interrupt → Listening · Detener → Idle · Repetir / Pausa → demostración · al terminar → Conversation | demostración |
| Voice / 05 — Conversation | `Conversation` | Encabezado «VORA Coach» con punto de estado y orbe chico; separador de fecha («Today at 12:40 PM»); burbujas del usuario (verde oscuro, derecha) y de VORA (blancas, izquierda, con «Listen to response (0:14)»); barra de entrada: `+`, «Ask VORA anything…», mic, enviar | Atrás → Idle · Enviar → agrega mensaje y respuesta simulada (extra: `POST /v1/coach/messages`) · Mic → Listening · `+` → demostración | demostración |
| Voice / 06 — Error | `MicError` | «System Alert»; orbe gris con ícono de reintentar; «Sorry, I didn't catch that» + subtítulo; tarjeta «Microphone access needed» con «Allow access» / «Not now»; botón «Try again»; enlace «Type instead» | Atrás / Not now → Idle · Allow access → aviso de modo demostración (no pide permiso) · Try again → Listening · Type instead → Conversation | demostración |

**Cómo se llega al Asistente:** desde Home (tarjeta del orbe de VORA) y desde los accesos de VORA en otras áreas (a confirmar con B al cerrar el inventario). El estado `MicError` se fuerza desde la demo (por ejemplo, con un chip o un ajuste de demostración), ya que no hay micrófono real.

**Back del sistema:** desde cualquier estado distinto de `Idle` vuelve a `Idle` (`BackHandler`); desde `Idle` sale del Asistente.

## Progreso (B)

Seis pantallas con datos de demostración identificados (no hay integración con sensores ni servicios de salud). La revisión semanal puede usar `POST /v1/progress/weekly-review` como ampliación.

| Figma | Contenido visible | Acciones → destino | Estado |
|---|---|---|---|
| Progress / 01 — Today's Overview | Fecha; «Daily completion» 72 % con anillo; avance de entrenamiento, nutrición, actividad e hidratación; tarjeta «VORA advice»; «Category breakdowns» con el entrenamiento del día; objetivo actual y meta semanal | Start Workout → Workout Details del entrenamiento recomendado · tab Progress → Dashboard | demostración |
| Progress / 02 — Dashboard | «Your progress»; selector Week / Month / 3 Months; resumen semanal (workouts, pasos, proteína, racha); «VORA insight»; accesos Workout, Nutrition y Activity; objetivo activo | See weekly review → Weekly Review · Workout → Workout Progress · Nutrition → Nutrition Progress · Activity → Health Trends · selector → cambia el período (demostración) | demostración |
| Progress / 03 — Workout Progress | Selector de período; sesiones, volumen y tiempo promedio; gráfico «Training volume»; récord personal; comentario de VORA; progreso por ejercicio | Atrás → Dashboard · `…` → aviso de modo demostración | demostración |
| Progress / 04 — Nutrition Progress | Selector de período; calorías y proteína promedio, días en meta; selector de macro y cumplimiento diario; «Nutrition analysis»; hábitos y patrones de comida | Atrás → Dashboard · selector de macro → cambia el indicador (demostración) | demostración |
| Progress / 05 — Health Trends | Pasos, distancia y kcal activas promedio; gráfico «Weekly steps trend»; comentario de VORA; «Habit signals» | Atrás → Dashboard | demostración |
| Progress / 06 — Weekly Review | Fondo verde oscuro; «You had a strong week»; qué salió bien; qué mejorar; recomendaciones para la próxima semana | Atrás / Done → pantalla anterior · Ask VORA → Asistente · Get dinner ideas → Nutrition · View next week's plan → Weekly Plan | demostración |
