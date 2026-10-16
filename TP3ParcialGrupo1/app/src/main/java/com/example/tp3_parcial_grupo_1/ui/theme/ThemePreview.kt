package com.example.tp3_parcial_grupo_1.ui.theme

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun ThemeShowcase() {
    val colors = MaterialTheme.colorScheme
    Column(
        modifier = Modifier
            .background(colors.background)
            .verticalScroll(rememberScrollState())
            .padding(MaterialTheme.spacing.screen),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.lg)
    ) {
        Text("VORA Coach", style = MaterialTheme.typography.titleLarge, color = colors.primary)
        Text("1,420 kcal", style = MaterialTheme.typography.headlineMedium)
        Text("Upper Body Strength", style = MaterialTheme.typography.titleMedium)
        Text("42 min • 5 exercises • Intermediate", style = MaterialTheme.typography.bodyMedium, color = colors.onSurfaceVariant)
        Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)) {
            Swatch("primary", colors.primary, colors.onPrimary)
            Swatch("secondary", colors.secondary, colors.onSecondary)
            Swatch("tertiary", colors.tertiary, colors.onTertiary)
        }
        Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)) {
            Swatch("error", colors.error, colors.onError)
            Swatch("success", MaterialTheme.extendedColors.success, colors.onError)
            Swatch("mint soft", MaterialTheme.extendedColors.mintSoft, colors.primary)
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(colors.surface, MaterialTheme.shapes.medium)
                .border(1.dp, colors.outlineVariant, MaterialTheme.shapes.medium)
                .padding(MaterialTheme.spacing.lg)
        ) {
            Text("Card · radio 16 · borde #E4E9E6", style = MaterialTheme.typography.bodyLarge)
        }
        Box(
            modifier = Modifier
                .background(colors.tertiary, PillShape)
                .padding(horizontal = MaterialTheme.spacing.xl, vertical = MaterialTheme.spacing.md)
        ) {
            Text("Start workout", style = MaterialTheme.typography.labelLarge, color = colors.onTertiary)
        }
        listOf(FontWeight.Normal, FontWeight.Medium, FontWeight.SemiBold, FontWeight.Bold).forEach {
            Text("Manrope ${it.weight} · ¿Cómo vas hoy?", fontWeight = it, style = MaterialTheme.typography.bodyLarge)
        }
    }
}

@Composable
private fun Swatch(name: String, color: Color, onColor: Color) {
    Box(
        modifier = Modifier
            .size(width = 104.dp, height = 56.dp)
            .background(color, MaterialTheme.shapes.small),
        contentAlignment = Alignment.Center
    ) {
        Text(name, style = MaterialTheme.typography.labelSmall, color = onColor)
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 780)
@Composable
fun ThemeShowcasePreview() {
    AppTheme { ThemeShowcase() }
}
