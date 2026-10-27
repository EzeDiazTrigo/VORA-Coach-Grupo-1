package com.example.tp3_parcial_grupo_1.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

// Tamaños de ícono que usa Figma: 12, 14, 16, 18, 20 y 24.
data class IconSizes(
    val xs: Dp = 12.dp,
    val sm: Dp = 14.dp,
    val md: Dp = 16.dp,
    val lg: Dp = 18.dp,
    val xl: Dp = 20.dp,
    val xxl: Dp = 24.dp
)

val LocalIconSizes = staticCompositionLocalOf { IconSizes() }

val MaterialTheme.iconSizes: IconSizes
    @Composable
    @ReadOnlyComposable
    get() = LocalIconSizes.current
