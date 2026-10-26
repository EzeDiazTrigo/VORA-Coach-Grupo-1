# QA visual — Figma vs app

Verificación visual cruzada (E). Primera ronda: `main` al 23/10 + PR #20. Segunda ronda (26/10): `main` con #20, #22, #24, #25, #27, #28, #29 y #33, en el emulador de referencia de Figma (390 × 844 dp). Referencia: los frames de Figma ([FIGMA.md](FIGMA.md)); las comparaciones lado a lado están en [comparisons/](comparisons/README.md).

Cada punto tiene dueño y prioridad: **alta** (se nota a simple vista en la demo o afecta varias pantallas), **media** (diferencia visible en una pantalla), **baja** (detalle). Marquen `[x]` cuando lo corrijan y referencien el commit.

## Transversales (afectan a todas las pantallas)

- [x] **Alta · A — Manrope no está aplicada.** Resuelto en #29. `Type.kt` sigue con `AppFontFamily = FontFamily.Default`; los `.ttf` ya están en `res/font/`. Es el cambio con más impacto en fidelidad (40 pts): todas las pantallas se ven con Roboto.
  ```kotlin
  val AppFontFamily = FontFamily(
      Font(R.font.manrope_regular, FontWeight.Normal),
      Font(R.font.manrope_medium, FontWeight.Medium),
      Font(R.font.manrope_semibold, FontWeight.SemiBold),
      Font(R.font.manrope_bold, FontWeight.Bold)
  )
  ```
- [ ] **Media · todos — Pesos de títulos.** Mejoró mucho con Manrope (Home, Progress y el Asistente ya coinciden); queda revisar títulos puntuales como «Upper Body Strength» de Home y los de Nutrition. En Figma los títulos de pantalla y de sección son Regular/Medium («Today's Overview», «Notifications», «How can I support…», «Upper Body Strength»); en la app salen Bold/SemiBold y más grandes. Revisar después de aplicar Manrope: la jerarquía de Figma está en [FIGMA.md › Tipografía](FIGMA.md#tipografía).
- [ ] **Media · A — Alturas de `AppButton`.** El mínimo de 52 dp agranda botones que en Figma son más bajos: «Start voice session» (44), botones de tarjeta como «Allow Access»/«Not Now» (38), «+ Add» de Dinner (24). Propuesta: un parámetro de tamaño (`Large` 52 / `Medium` 44 / `Small` 32) en lugar de `isCompact`, que hoy solo cambia el padding.
- [ ] **Media · A, D — Orbe de VORA.** Donde Figma muestra el orbe se ven círculos planos. Usar `VoraOrb` (ya está en `ui/components`): logo de Acceso, panel de Home (orbe chico junto a «VORA Coach»), tarjeta «Talk to VORA» de Add Food, Voice Logging y Analyzing Meal.

## Acceso — Login / SignUp (A)

- [ ] **Alta — Botones sociales.** En Figma son solo ícono (logo de Apple, logo de Google, Face ID con fondo menta); en la app son texto «Apple / Google / Biometric». Los recursos están exportados: `ic_logo_apple`, `ic_logo_google` (usar `Image`, conservan colores) y `ic_face_id`.
- [ ] **Alta — Logo.** Círculo menta con «AI» en lugar del orbe con la insignia «AI» (ver transversal del orbe).
- [ ] **Media — Encabezado.** Falta la fila superior: botón atrás circular con chevron y selector de idioma «EN ⌄». En SignUp el atrás usa la flecha de Material; en Figma es `ic_chevron_left` dentro de un círculo blanco.
- [ ] **Media — Fondo.** Figma tiene un brillo menta suave en la parte superior (`Top Decorative Ambient Glow`, 320 × 320); la app es plana.
- [ ] **Media — Campos de Login.** En Figma tienen relleno gris claro (`background`) sin borde visible; en la app son blancos con borde. (En SignUp sí son blancos con borde, como en la app.)
- [ ] **Media — «Forgot your password?»** va a la derecha del rótulo «Password», no debajo del campo.
- [ ] **Media — Botón principal.** «Sign In»/«Sign Up» en Figma es un rectángulo con radio ~12, no una píldora.
- [ ] **Baja — Subtítulo** «Your strength, nutrition, and progress plan in one place.» falta bajo «ADAPTIVE INTELLIGENCE».
- [ ] **Baja — Separador** «Or continue with» lleva líneas a ambos lados.
- [ ] **Baja — Pie.** El ícono de «End-to-end encrypted…» en Figma es un escudo con check verde (`ic_shield_check`/`ic_verified_user`), no la cara.
- [ ] **Baja — Requisito de contraseña (SignUp).** El check de «8+ secure characters» en Figma es relleno verde (`ic_check_circle_filled`).
- «Enter demo mode» no está en Figma, pero es obligatorio por la consigna (acceso de demostración): se mantiene.

## Home y Notifications (A)

- [ ] **Media — Campana con punto.** En Figma la campana tiene un punto menta de no leídas.
- [ ] **Media — Panel de VORA.** Falta el orbe chico antes de «VORA Coach»; los chips «Start my workout» / «Log breakfast» en Figma son más chicos (12 sp, Regular).
- [ ] **Baja — Recommended workout.** «Upper Body Strength» en Figma es 18 sp Regular; en la app es mucho más grande y en negrita.
- [ ] **Baja — Accesibilidad.** La descripción de la campana está en español («Notificaciones»); el resto de los textos está en inglés.
- Notifications coincide en estructura, íconos y agrupación (Today / Yesterday).

## Nutrition (D)

- [ ] **Alta — Fila de macros del Dashboard (segunda ronda).** Sigue en `main` después de #33: con valores reales («83g / 140g», «111g / 220g») el valor queda pegado al rótulo y el objetivo baja a otra línea; con «0g» entra bien, por eso puede no verse con la base vacía. En Figma cada columna es rótulo arriba y «104g/140g» abajo, en una sola línea (ver comparación 04).
- [ ] **Media — Tarjeta «Tell Vora what you ate» (segunda ronda).** El orbe ya está (#33). Faltan los íconos de los botones Voice / Photo / Search (`ic_mic`, `ic_camera`, `ic_search`); Photo y Search en Figma son píldoras con fondo translúcido.
- [ ] **Media — Orbe** en Add Food («Talk to VORA»), Voice Logging y Analyzing Meal (ver transversal).
- [ ] **Media — Add Food.** Las filas «Snap a Photo», «Search Database», «Log Manually» llevan flecha `ic_chevron_right` a la derecha.
- [ ] **Media — Botones anclados abajo.** En Voice Logging y Analyzing Meal, «Done Speaking» / «Type instead» y «Cancel analysis» van debajo del contenido (como en Figma), no pegados al borde inferior.
- [ ] **Baja — Voice Logging.** «Listening…» en Figma es menta (`secondary`), en la app es blanco.
- [ ] **Baja — Formato de calorías.** Figma usa separador de miles («1,420»); la app muestra «1180».
- [ ] **Baja — Dinner «+ Add».** En Figma es una píldora chica; en la app ocupa 52 dp de alto (ver alturas de `AppButton`).
- [ ] **Baja — Meal Details.** Figma tiene «Edit Meal» y «Duplicate Meal»; la app tiene «Edit Meal» y «Delete Meal». Delete es necesario para RF04; si se quiere fidelidad, sumar «Duplicate Meal» como acción de demostración.
- Today's Meals muestra una línea por comida en lugar del desglose de ingredientes de Figma: es consecuencia de que los datos vienen de Room (RF03/RF04), no hace falta cambiarlo.
- Photo Analysis, Review Meal y el Dashboard coinciden en estructura y colores.

## Progress (B)

- Es el área más fiel: estructura, gráficos, colores y segmentos coinciden en las seis pantallas.
- [ ] **Baja — Pesos de títulos** («Your progress», «Workout progress», etc.), ver transversal.

## Profile (A) — segunda ronda

Revisado el 25/10 sobre la rama del PR #27 (`feature/profile-screens`), en el emulador de referencia (390 × 844 dp). La estructura y el contenido de las seis pantallas coinciden con Figma; Settings es prácticamente idéntica. Todos los controles navegan o informan modo demostración (NAV03): «Connected Data» muestra el aviso, «Account» abre Settings, «Review preferences» abre Voice & AI.

- [ ] **Media — Botón atrás.** Las cinco subpantallas usan la flecha de Material (←); en Figma es un botón circular de 32 dp con `ic_chevron_left` y borde, igual al del Asistente (`CoachHeader`).
- [ ] **Media — Nutrition Preferences › Meal preferences.** En Figma es una tarjeta compacta de tres filas de texto (Meals per day / Preferred breakfast / Preferred dinner con su valor a la derecha); en la app son tres desplegables apilados.
- [ ] **Media — Voice & AI › «Your control matters».** En Figma es una tarjeta menta clara con un enlace de texto «Privacy & permissions»; en la app es un panel verde oscuro con botón lima.
- [ ] **Baja — Voice & AI.** El botón «Preview» en Figma es una píldora chica con `ic_sparkles`; los interruptores van en filas compactas dentro de una tarjeta (en la app las filas son más altas, con divisores).
- [ ] **Baja — Your Goals.** En Figma el objetivo principal tiene dos opciones marcadas (Build strength e Improve fitness); la app permite una sola. Si se mantiene la selección simple, el Dashboard debería mostrar el mismo texto que Figma («Build strength & improve fitness») o el elegido, como hoy.
- [ ] **Baja — Edit Profile.** El campo Gender en Figma no muestra el chevron de desplegable.
- Datos de ejemplo distintos (alimentos marcados, Auto-play desactivado) no son diferencias de diseño.
- La barra inferior de esta rama es la provisoria de Material porque la rama es anterior al PR #20; al mergear con `main` toma `BottomNavBar`.

## Workout (C) — segunda ronda

Revisado el 26/10 en `main` con #22, #24 y #25 (flujo completo) contra el servidor publicado.

- [x] **Alta — Dashboard.** Resuelto en #24/#25: tarjeta «Ready for today?» con «Start Workout» y «Details», «VORA recommends», «This Week's Activity» y «Your Progress» como en Figma (ver comparación 03). El catálogo de RF01 queda como sección «Workouts» debajo, con su `workoutId` por tarjeta (NAV04).
- [x] **Baja — Encabezado.** «Workout Goal» y el botón de calendario ya están.
- [ ] **Baja — This Week's Activity.** La app agrega un chevron a la derecha del título que no está en Figma.
- La tarjeta principal muestra la primera rutina del servidor («Fuerza inicial», en español porque así viene del mock) en lugar de «Upper Body Strength»: es contenido real, no un error.

## Asistente VORA y barra inferior (E)

- Las seis pantallas del Asistente y la barra inferior se compararon contra Figma al implementarlas (ver PR #9, #14 y #20).
- [ ] **Media · E —** Alturas de los botones de la tarjeta de Error (depende de la transversal de `AppButton`).
- [x] **Media · E —** Fuente (resuelto con Manrope en #29; ver comparación 02).

## Rotación sin consultas duplicadas (RF07)

Verificado el 25/10 en el emulador (Pixel 10 Pro en 390 × 844 dp), rotando a horizontal y de vuelta a vertical con `adb emu rotate`. Las consultas se contaron en el log de OkHttp (`--> GET`), contra el mock local.

| Pantalla | Rama | Consultas antes → después de rotar | Estado tras rotar |
|---|---|---|---|
| Workout / 01 · Dashboard (catálogo) | #22 | `GET /v1/workouts`: 1 → 1 | Lista conservada |
| Workout / 03 · Details (`w01`) | #22 | `GET /v1/workouts/w01`: 1 → 1; catálogo sigue en 1 | Detalle conservado |
| Nutrition · Log Manually | `main` | sin red | Nombre, tipo de comida y calorías conservados |
| Asistente (todos los estados) | #20 | sin red | Estado y progreso de la respuesta conservados; la simulación no se reinicia |

- [x] El catálogo y el detalle no se vuelven a pedir al rotar: el estado vive en los ViewModel.
- [ ] **Baja · D —** En horizontal, el formulario de Log Manually deja las calorías fuera de la vista inicial (se llega con scroll; no se pierde nada).
