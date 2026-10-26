# Comparaciones visuales — Figma vs app

Las cinco comparaciones que pide la entrega, una por área y dueño:

| # | Pantalla (Figma) | Nodo | Dueño | Archivo |
|---|---|---|---|---|
| 1 | Home / 01 · Dashboard | `2007:3` | A | `01-home-dashboard.jpg` |
| 2 | Voice / 02 · Listening | `1:1372` | E | `02-voice-listening.jpg` |
| 3 | Workout / 01 · Dashboard | `1:213` | C | `03-workout-dashboard.jpg` |
| 4 | Nutrition / 01 · Dashboard | `1:730` | D | `04-nutrition-dashboard.jpg` |
| 5 | Progress / 02 · Dashboard | `1:1775` | B | `05-progress-dashboard.jpg` |

**Estado:** generadas el 26/10 con `main` (incluye #20, #22, #27, #28 y #29), en el emulador de referencia de Figma (390 × 844 dp) contra el servidor publicado. Las diferencias que muestran están anotadas en [QA.md](../QA.md).

| Comparación | Lectura rápida |
|---|---|
| ![](01-home-dashboard.jpg) | Home: estructura y contenido coinciden; faltan el orbe chico del panel de VORA y el punto de la campana. |
| ![](02-voice-listening.jpg) | Asistente: coincide en composición, colores, orbe y tipografía. |
| ![](03-workout-dashboard.jpg) | Workout: la app muestra el catálogo (RF01) en lugar del Dashboard de Figma. |
| ![](04-nutrition-dashboard.jpg) | Nutrition: misma estructura; la fila de macros se desarma con Manrope y la tarjeta de VORA no tiene íconos. |
| ![](05-progress-dashboard.jpg) | Progress: prácticamente idéntico. |

## Cómo generarlas

1. **Emulador de referencia de Figma (390 × 844 dp).** Con el Pixel 10 Pro (o cualquier AVD de 480 dpi):
   ```bash
   adb shell wm size 1170x2532
   adb shell wm density 480
   ```
   Al terminar: `adb shell wm size reset` y `adb shell wm density reset`.
2. **Datos.** Workout necesita el catálogo del mock. Si el servidor publicado no responde, usar el mock local (ver README › Servidor) con `adb reverse tcp:8000 tcp:8000` y `BASE_URL = "http://127.0.0.1:8000/"` **sin commitear**.
3. **Capturas de la app.** Entrar en modo demostración y capturar cada pantalla con `adb exec-out screencap -p > nombre.png`. Para las pantallas más largas que el viewport (Home, Workout, Nutrition, Progress), una captura arriba y otra al final del scroll.
4. **Frames de Figma.** Exportar cada nodo de la tabla en PNG a 2x (incluye el borde del frame de 6 px, que el script recorta).
5. **Componer** (Windows PowerShell):
   ```powershell
   .\compose.ps1 -Figma figma\home.png -App app\home_top.png,app\home_bottom.png -Out 01-home-dashboard.jpg -Title "Home / 01 — Dashboard (A)"
   ```
   Cada imagen pone Figma a la izquierda y la app a la derecha, a la misma escala; con dos capturas, la fila de abajo compara el final del scroll contra el final del frame.
