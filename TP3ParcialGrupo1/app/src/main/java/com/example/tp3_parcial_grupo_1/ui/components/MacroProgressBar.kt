package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

// Valor actual destacado y meta en gris: "1,420 / 2,100 kcal". `stacked` es el estilo de Nutrition: valor en Bold y "104g/140g" sin espacios.
@Composable
fun GoalValueText(current: String, goal: String, modifier: Modifier = Modifier, stacked: Boolean = false) {
    val colors = MaterialTheme.colorScheme
    val valueStyle = if (stacked) {
        MaterialTheme.typography.bodyLarge.toSpanStyle().copy(color = colors.onSurface, fontWeight = FontWeight.Bold)
    } else {
        MaterialTheme.typography.titleSmall.toSpanStyle().copy(color = colors.onSurface)
    }
    val goalStyle = MaterialTheme.typography.bodySmall.toSpanStyle().copy(color = colors.onSurfaceVariant)
    val separator = if (stacked) "/" else " / "
    Text(
        text = buildAnnotatedString {
            withStyle(valueStyle) { append(current) }
            withStyle(goalStyle) { append("$separator$goal") }
        },
        modifier = modifier
    )
}

@Composable
fun MacroProgressBar(
    name: String,
    current: String,
    goal: String,
    progress: Float,
    color: Color,
    modifier: Modifier = Modifier,
    stacked: Boolean = false
) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xxs)) {
        if (stacked) {
            Text(
                text = name,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.extendedColors.textSubtle
            )
            GoalValueText(current = current, goal = goal, stacked = true)
        } else {
            Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
                Text(name, style = MaterialTheme.typography.bodyMedium)
                GoalValueText(current = current, goal = goal)
            }
        }
        LinearProgressIndicator(
            progress = { progress },
            modifier = Modifier.fillMaxWidth().height(4.dp),
            color = color,
            trackColor = MaterialTheme.colorScheme.outlineVariant,
            gapSize = 0.dp,
            drawStopIndicator = {}
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MacroProgressBarPreview() {
    AppTheme {
        MacroProgressBar(
            name = "Protein",
            current = "104g",
            goal = "140g",
            progress = 0.74f,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(MaterialTheme.spacing.screen)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun MacroProgressBarStackedPreview() {
    AppTheme {
        MacroProgressBar(
            name = "Protein",
            current = "104g",
            goal = "140g",
            progress = 0.74f,
            color = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(MaterialTheme.spacing.screen),
            stacked = true
        )
    }
}
