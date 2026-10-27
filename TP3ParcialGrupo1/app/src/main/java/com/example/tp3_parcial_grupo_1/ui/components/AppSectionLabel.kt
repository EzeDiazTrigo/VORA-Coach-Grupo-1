package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

// Caps: mayúsculas chicas (Voice, Nutrition, Profile). Title: título de sección sin mayúsculas (Progress).
enum class AppSectionLabelStyle { Caps, Title }

// Rótulo de sección del diseño, en gris tenue salvo que la pantalla pida otro color.
@Composable
fun AppSectionLabel(
    text: String,
    modifier: Modifier = Modifier,
    style: AppSectionLabelStyle = AppSectionLabelStyle.Caps,
    color: Color = MaterialTheme.extendedColors.textSubtle
) {
    Text(
        text = if (style == AppSectionLabelStyle.Caps) text.uppercase() else text,
        style = if (style == AppSectionLabelStyle.Caps) MaterialTheme.typography.labelSmall else MaterialTheme.typography.titleSmall,
        color = color,
        modifier = modifier
    )
}

// Rótulo y contenido de una sección de formulario, separados como en Figma.
@Composable
fun AppSection(
    label: String,
    modifier: Modifier = Modifier,
    spacing: Dp = MaterialTheme.spacing.md,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(spacing)) {
        AppSectionLabel(label)
        content()
    }
}

@Preview(showBackground = true)
@Composable
private fun AppSectionLabelPreview() {
    AppTheme {
        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.screen),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.lg)
        ) {
            AppSectionLabel(text = "Primary goal — what's your main goal?")
            AppSectionLabel(text = "Habit signals", style = AppSectionLabelStyle.Title)
            AppSection(label = "Coaching style") {
                AppChip(text = "Balanced", selected = true, onClick = {})
            }
        }
    }
}
