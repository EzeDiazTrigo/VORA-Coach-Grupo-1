package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.HairlineWidth
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

// Neutral: gris claro. Accent: lima. Outlined: blanca con borde. OnDark: blanca al 12 % sobre los paneles verdes.
enum class AppPillStyle { Neutral, Accent, Outlined, OnDark }

private const val OnDarkAlpha = 0.12f

/**
 * Botón chico en píldora del diseño («Edit profile», «Start my workout», sugerencias del Asistente);
 * AppButton tiene un alto mínimo de 52 dp. [shape] permite la esquina de 12 dp que Figma usa en algunos grupos.
 */
@Composable
fun AppPillButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: AppPillStyle = AppPillStyle.Neutral,
    textStyle: TextStyle = MaterialTheme.typography.labelMedium,
    contentPadding: PaddingValues = PaddingValues(horizontal = MaterialTheme.spacing.lg, vertical = MaterialTheme.spacing.sm),
    shape: Shape = PillShape
) {
    val colors = MaterialTheme.colorScheme
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = shape,
        color = when (style) {
            AppPillStyle.Neutral -> colors.background
            AppPillStyle.Accent -> colors.tertiary
            AppPillStyle.Outlined -> colors.surface
            AppPillStyle.OnDark -> colors.onPrimary.copy(alpha = OnDarkAlpha)
        },
        contentColor = when (style) {
            AppPillStyle.Accent -> colors.onTertiary
            AppPillStyle.OnDark -> colors.onPrimary
            else -> colors.onSurface
        },
        border = if (style == AppPillStyle.Outlined) BorderStroke(HairlineWidth, colors.outlineVariant) else null
    ) {
        Text(text, style = textStyle, modifier = Modifier.padding(contentPadding))
    }
}

/**
 * Etiqueta en píldora que no se toca: nivel, estado, tendencia o macro («BEGINNER», «Synced», «+5 kg»).
 */
@Composable
fun AppTag(
    text: String,
    modifier: Modifier = Modifier,
    containerColor: Color = MaterialTheme.extendedColors.mintSoft,
    contentColor: Color = LocalContentColor.current,
    borderColor: Color? = null,
    textStyle: TextStyle = MaterialTheme.typography.labelSmall,
    contentPadding: PaddingValues = PaddingValues(horizontal = MaterialTheme.spacing.md, vertical = MaterialTheme.spacing.xs),
    shape: Shape = PillShape
) {
    Surface(
        modifier = modifier,
        shape = shape,
        color = containerColor,
        contentColor = contentColor,
        border = borderColor?.let { BorderStroke(HairlineWidth, it) }
    ) {
        Text(text, style = textStyle, modifier = Modifier.padding(contentPadding))
    }
}

@Preview(showBackground = true)
@Composable
private fun AppPillButtonPreview() {
    AppTheme {
        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.screen),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
        ) {
            Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)) {
                AppPillButton(text = "Edit profile", onClick = {})
                AppPillButton(text = "Review preferences", onClick = {}, style = AppPillStyle.Accent)
                AppPillButton(text = "What should I eat?", onClick = {}, style = AppPillStyle.Outlined)
            }
            Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)) {
                AppTag(text = "Synced", containerColor = MaterialTheme.colorScheme.primary, contentColor = MaterialTheme.colorScheme.onPrimary)
                AppTag(text = "+5 kg", contentColor = MaterialTheme.colorScheme.primary)
                AppTag(text = "P 38g", containerColor = MaterialTheme.colorScheme.background, borderColor = MaterialTheme.colorScheme.outlineVariant)
            }
        }
    }
}
