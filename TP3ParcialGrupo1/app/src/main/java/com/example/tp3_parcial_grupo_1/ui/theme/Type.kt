package com.example.tp3_parcial_grupo_1.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.example.tp3_parcial_grupo_1.R

val AppFontFamily: FontFamily = FontFamily(
    Font(R.font.manrope_regular, FontWeight.Normal),
    Font(R.font.manrope_medium, FontWeight.Medium),
    Font(R.font.manrope_semibold, FontWeight.SemiBold),
    Font(R.font.manrope_bold, FontWeight.Bold)
)

private fun style(size: Int, weight: FontWeight, lineHeight: Int? = null) = TextStyle(
    fontFamily = AppFontFamily,
    fontWeight = weight,
    fontSize = size.sp,
    lineHeight = (lineHeight ?: (size * 1.4).toInt()).sp
)

val AppTypography = Typography(
    displaySmall = style(32, FontWeight.SemiBold),
    headlineLarge = style(28, FontWeight.SemiBold),
    headlineMedium = style(24, FontWeight.SemiBold),
    headlineSmall = style(22, FontWeight.SemiBold),
    titleLarge = style(20, FontWeight.Bold),
    titleMedium = style(18, FontWeight.SemiBold),
    titleSmall = style(16, FontWeight.SemiBold),
    bodyLarge = style(15, FontWeight.Normal),
    bodyMedium = style(13, FontWeight.Normal, lineHeight = 18),
    bodySmall = style(11, FontWeight.Normal),
    labelLarge = style(14, FontWeight.SemiBold),
    labelMedium = style(13, FontWeight.SemiBold),
    labelSmall = style(11, FontWeight.SemiBold)
)

// Texto de los mensajes del Asistente: 14 sp Regular con interlineado 1.4 (no tiene lugar en la escala de Material).
private val MessageStyle = style(14, FontWeight.Normal)

val Typography.message: TextStyle
    get() = MessageStyle

// Hora de cada mensaje del Asistente: 10 sp.
private val MessageTimeStyle = style(10, FontWeight.Normal)

val Typography.messageTime: TextStyle
    get() = MessageTimeStyle

// Chips de acción del panel de Inicio («Start my workout», «Log breakfast»): 12 sp Medium.
private val ChipStyle = style(12, FontWeight.Medium)

val Typography.chip: TextStyle
    get() = ChipStyle
