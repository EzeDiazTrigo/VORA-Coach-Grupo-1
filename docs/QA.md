# QA visual — Figma vs app

Primera ronda de verificación visual cruzada (Fase 2, E). Referencia: los frames de Figma ([FIGMA.md](FIGMA.md)); app: `main` al 23/10 + PR #20 (Asistente y barra inferior), corrida en el emulador Pixel 10 Pro.

Cada punto tiene dueño y prioridad: **alta** (se nota a simple vista en la demo o afecta varias pantallas), **media** (diferencia visible en una pantalla), **baja** (detalle). Marquen `[x]` cuando lo corrijan y referencien el commit.

## Transversales (afectan a todas las pantallas)

- [ ] **Alta · A — Manrope no está aplicada.** `Type.kt` sigue con `AppFontFamily = FontFamily.Default`; los `.ttf` ya están en `res/font/`. Es el cambio con más impacto en fidelidad (40 pts): todas las pantallas se ven con Roboto.
  ```kotlin
  val AppFontFamily = FontFamily(
      Font(R.font.manrope_regular, FontWeight.Normal),
      Font(R.font.manrope_medium, FontWeight.Medium),
      Font(R.font.manrope_semibold, FontWeight.SemiBold),
      Font(R.font.manrope_bold, FontWeight.Bold)
  )
  ```
- [ ] **Alta · todos — Pesos de títulos.** En Figma los títulos de pantalla y de sección son Regular/Medium («Today's Overview», «Notifications», «How can I support…», «Upper Body Strength»); en la app salen Bold/SemiBold y más grandes. Revisar después de aplicar Manrope: la jerarquía de Figma está en [FIGMA.md › Tipografía](FIGMA.md#tipografía).
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

## Workout (C) y Profile (A)

- Pendiente: en `main` siguen siendo placeholders. Se revisan en la segunda ronda (Fase 3).

## Asistente VORA y barra inferior (E)

- Las seis pantallas del Asistente y la barra inferior se compararon contra Figma al implementarlas (ver PR #9, #14 y #20).
- [ ] **Media · E —** Alturas de los botones de la tarjeta de Error (depende de la transversal de `AppButton`).
- [ ] **Media · E —** Fuente (depende de Manrope).
