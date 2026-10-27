# Comparaciones visuales — Figma vs app

Las cinco comparaciones que pide la entrega, una por área y dueño:

| # | Pantalla (Figma) | Nodo | Dueño | Archivo |
|---|---|---|---|---|
| 1 | Home / 01 · Dashboard | `2007:3` | A | `01-home-dashboard.jpg` |
| 2 | Voice / 02 · Listening | `1:1372` | E | `02-voice-listening.jpg` |
| 3 | Workout / 01 · Dashboard | `1:213` | C | `03-workout-dashboard.jpg` |
| 4 | Nutrition / 01 · Dashboard | `1:730` | D | `04-nutrition-dashboard.jpg` |
| 5 | Progress / 02 · Dashboard | `1:1775` | B | `05-progress-dashboard.jpg` |

**Estado:** regeneradas el 27/10 con `main` después de las fases 1 a 5 (#38, #39, #40, #41, #42 y #43), en un emulador de 390 × 844 dp. El servidor publicado no respondía ese día, así que Workout se capturó con la caché de Room sembrada con los entrenamientos del mock (el primero es «Upper Body Strength», `MODERATE`, 5 ejercicios) y Nutrition con las tres comidas de demostración de Room. Los fragmentos de Figma son los mismos de la versión anterior: el diseño no cambió. Las diferencias que quedan están anotadas en [QA.md](../QA.md).

| Comparación | Lectura rápida |
|---|---|
| ![](01-home-dashboard.jpg) | Home: estructura y contenido coinciden; el orbe del panel de VORA y el punto de la campana ya están. La app queda algo más alta que Figma por el espaciado entre tarjetas. |
| ![](02-voice-listening.jpg) | Asistente: coincide en composición, colores, orbe y tipografía; la transcripción usa comillas tipográficas. |
| ![](03-workout-dashboard.jpg) | Workout: coincide con Figma; el catálogo ya no se suma debajo (vive en Weekly Plan). Los días de la semana sin entrenar («Zzz») van sin el círculo gris de Figma. |
| ![](04-nutrition-dashboard.jpg) | Nutrition: las macros ya van en tres columnas y los botones de la tarjeta de VORA llevan ícono. El total es la suma real de Room (1180 kcal, sin separador de miles) y no el 1,420 de ejemplo de Figma. |
| ![](05-progress-dashboard.jpg) | Progress: prácticamente idéntico; el 🔥 de la racha se ve en la app y no en el export de Figma. |

## Cómo generarlas

1. **Emulador de referencia de Figma (390 × 844 dp).** Con el Pixel 10 Pro (o cualquier AVD de 480 dpi):
   ```bash
   adb shell wm size 1170x2532
   adb shell wm density 480
   ```
   Con un AVD de 420 dpi y 1080 px de ancho (Pixel 9) se llega al mismo tamaño en dp con `wm size 1080x2337` y `wm density 443`.
   Al terminar: `adb shell wm size reset` y `adb shell wm density reset`.
2. **Datos.** Workout necesita el catálogo del mock. Si el servidor publicado no responde, usar el mock local (ver README › Servidor) con `adb reverse tcp:8000 tcp:8000` y `BASE_URL = "http://127.0.0.1:8000/"` **sin commitear**.
3. **Capturas de la app.** Entrar en modo demostración y capturar cada pantalla con `adb exec-out screencap -p > nombre.png`. Para las pantallas más largas que el viewport (Home, Workout, Nutrition, Progress), una captura arriba y otra al final del scroll.
4. **Frames de Figma.** Exportar cada nodo de la tabla en PNG a 2x (incluye el borde del frame de 6 px, que el script recorta).
5. **Componer** (Windows PowerShell):
   ```powershell
   .\compose.ps1 -Figma figma\home.png -App app\home_top.png,app\home_bottom.png -Out 01-home-dashboard.jpg -Title "Home / 01 — Dashboard (A)"
   ```
   Cada imagen pone Figma a la izquierda y la app a la derecha, a la misma escala; con dos capturas, la fila de abajo compara el final del scroll contra el final del frame.

   En Linux o macOS, con ImageMagick 7, el equivalente es `compose.sh` (mismos parámetros en forma de opciones):
   ```bash
   ./compose.sh -f figma/home.png -a app/home_top.png -a app/home_bottom.png -o 01-home-dashboard.jpg -t "Home / 01 — Dashboard (A)"
   ```
