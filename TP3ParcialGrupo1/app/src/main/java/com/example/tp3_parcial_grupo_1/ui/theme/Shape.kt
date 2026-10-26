package com.example.tp3_parcial_grupo_1.ui.theme

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Shapes
import androidx.compose.ui.unit.dp

val AppShapes = Shapes(
    extraSmall = RoundedCornerShape(8.dp),
    small = RoundedCornerShape(12.dp),
    medium = RoundedCornerShape(16.dp),
    large = RoundedCornerShape(20.dp),
    extraLarge = RoundedCornerShape(24.dp)
)

// Botones, chips y etiquetas del diseño usan radio 100.
val PillShape = RoundedCornerShape(100.dp)

// Grosor del borde sutil de tarjetas, botones y avatares.
val HairlineWidth = 1.dp
