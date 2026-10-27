package com.example.tp3_parcial_grupo_1.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

// El diseño de Figma solo define el tema claro, por eso no hay dark mode ni dynamic color.
@Composable
fun AppTheme(content: @Composable () -> Unit) {
    CompositionLocalProvider(
        LocalSpacing provides Spacing(),
        LocalIconSizes provides IconSizes(),
        LocalExtendedColors provides ExtendedColors()
    ) {
        MaterialTheme(
            colorScheme = AppColorScheme,
            typography = AppTypography,
            shapes = AppShapes,
            content = content
        )
    }
}
