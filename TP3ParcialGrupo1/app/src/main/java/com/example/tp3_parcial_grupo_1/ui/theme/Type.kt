package com.example.tp3_parcial_grupo_1.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

// Se reemplaza por Manrope cuando se incorpore la fuente exportada de Figma (res/font).
val AppFontFamily: FontFamily = FontFamily.Default

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
