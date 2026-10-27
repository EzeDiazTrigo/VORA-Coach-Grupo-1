package com.example.tp3_parcial_grupo_1.ui.components

import android.app.Activity
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.ui.platform.LocalView
import androidx.core.view.WindowCompat

/**
 * Íconos claros en las barras del sistema mientras la pantalla pinta un fondo oscuro detrás de ellas.
 * La ruta tiene que estar en `fullScreenRoutes` de AppNavHost para que el fondo llegue hasta el borde.
 */
@Composable
fun LightSystemBarIcons(enabled: Boolean = true) {
    val view = LocalView.current
    if (view.isInEditMode) return
    DisposableEffect(enabled) {
        val window = (view.context as Activity).window
        val controller = WindowCompat.getInsetsController(window, view)
        controller.isAppearanceLightStatusBars = !enabled
        controller.isAppearanceLightNavigationBars = !enabled
        onDispose {
            controller.isAppearanceLightStatusBars = true
            controller.isAppearanceLightNavigationBars = true
        }
    }
}
