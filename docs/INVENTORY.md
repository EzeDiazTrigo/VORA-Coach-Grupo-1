# Inventario de pantallas

Referencia de cobertura (NAV01). Nombres tal como figuran en Figma; nodos en [FIGMA.md](FIGMA.md). Cada dueño completa su área.

Estado: **funcional** (datos reales: Retrofit/Room) · **demostración** (datos de ejemplo identificados).

## Asistente VORA (E)

Las seis pantallas son estados de un mismo destino (`coach`), modelados con `CoachUiState`. La conversación es simulada y no se pide permiso de micrófono (NAV05): «Toca para hablar» avanza por los estados con datos de demostración.

| Figma | Estado en la app | Contenido visible | Acciones → destino | Estado |
|---|---|---|---|---|
| Voice / 01 · Idle | `Idle` | Encabezado «VORA Coach» + etiqueta `ONLINE`; orbe; «How can I help?» + subtítulo; «Suggested questions» (4 chips: Start my workout, Log my breakfast, What should I eat tonight?, How am I doing?); tarjeta «Last conversation» (hace 2 h); botón de micrófono «Tap to talk»; enlace «Type instead» | Atrás → destino anterior · Mic → Listening · Chip → Processing con esa pregunta · Última conversación / Type instead → Conversation | demostración |
| Voice / 02 · Listening | `Listening` | Fondo verde oscuro; «Listening» + `LIVE AUDIO`; orbe con anillos; tarjeta «Live transcript» con el texto que se va dictando; «Speak naturally. I'm listening.»; botón mic activo; «Cancel» | Atrás / Cancel → Idle · al terminar la transcripción simulada → Processing | demostración |
| Voice / 03 · Processing | `Processing` | «Processing»; orbe; «Thinking…»; tarjeta con la frase transcripta; indicador «Checking your nutrition target…»; «Cancel» | Atrás / Cancel → Idle · al terminar → Speaking | demostración |
| Voice / 04 · Speaking | `Reply` | «VORA Speaking» + `SPEAKING`; orbe; tarjeta con la respuesta; barra de progreso del audio (0:08 / -0:14); controles repetir, pausa, detener; botón «Tap to Interrupt» | Atrás / Tap to Interrupt → Listening · Detener → Idle · Repetir / Pausa → demostración · al terminar → Conversation | demostración |
| Voice / 05 · Conversation | `Conversation` | Encabezado «VORA Coach» con punto de estado y orbe chico; separador de fecha («Today at 12:40 PM»); burbujas del usuario (verde oscuro, derecha) y de VORA (blancas, izquierda, con «Listen to response (0:14)»); barra de entrada: `+`, «Ask VORA anything…», mic, enviar | Atrás → Idle · Enviar → agrega mensaje y respuesta simulada (extra: `POST /v1/coach/messages`) · Mic → Listening · `+` → demostración | demostración |
| Voice / 06 · Error | `MicError` | «System Alert»; orbe gris con ícono de reintentar; «Sorry, I didn't catch that» + subtítulo; tarjeta «Microphone access needed» con «Allow access» / «Not now»; botón «Try again»; enlace «Type instead» | Atrás / Not now → Idle · Allow access → aviso de modo demostración (no pide permiso) · Try again → Listening · Type instead → Conversation | demostración |

**Cómo se llega al Asistente:** desde Home (tarjeta del orbe de VORA) y desde los accesos de VORA en otras áreas (a confirmar con B al cerrar el inventario). El estado `MicError` se fuerza desde la demo (por ejemplo, con un chip o un ajuste de demostración), ya que no hay micrófono real.

**Back del sistema:** desde cualquier estado distinto de `Idle` vuelve a `Idle` (`BackHandler`); desde `Idle` sale del Asistente.
