package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

/**
 * Estadística: rótulo chico sobre el valor («STEPS / 6,240»). Con [valueFirst] el valor va arriba, como en
 * Workout Complete; [valueTrailing] agrega algo al lado del valor (la racha o la tendencia de Progress).
 */
@Composable
fun AppStat(
    label: String,
    value: String,
    modifier: Modifier = Modifier,
    labelStyle: TextStyle = MaterialTheme.typography.labelSmall,
    labelColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    valueStyle: TextStyle = MaterialTheme.typography.titleSmall,
    valueColor: Color = Color.Unspecified,
    spacing: Dp = MaterialTheme.spacing.xxs,
    horizontalAlignment: Alignment.Horizontal = Alignment.Start,
    valueFirst: Boolean = false,
    valueTrailing: (@Composable RowScope.() -> Unit)? = null
) {
    Column(
        modifier = modifier,
        horizontalAlignment = horizontalAlignment,
        verticalArrangement = Arrangement.spacedBy(spacing)
    ) {
        val labelText: @Composable () -> Unit = { Text(label, style = labelStyle, color = labelColor) }
        val valueText: @Composable () -> Unit = {
            if (valueTrailing == null) {
                Text(value, style = valueStyle, color = valueColor)
            } else {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xs)) {
                    Text(value, style = valueStyle, color = valueColor)
                    valueTrailing()
                }
            }
        }
        if (valueFirst) {
            valueText()
            labelText()
        } else {
            labelText()
            valueText()
        }
    }
}

// Rótulo y valor de los macros de Nutrition (10 sp y 16 sp en Bold).
object AppStatDefaults {
    val MacroLabelStyle: TextStyle
        @Composable get() = MaterialTheme.typography.labelSmall.copy(fontSize = 10.sp, fontWeight = FontWeight.Bold)

    val MacroValueStyle: TextStyle
        @Composable get() = MaterialTheme.typography.titleMedium.copy(fontSize = 16.sp, fontWeight = FontWeight.Bold)
}

@Preview(showBackground = true)
@Composable
private fun AppStatPreview() {
    AppTheme {
        Row(
            modifier = Modifier.padding(MaterialTheme.spacing.screen),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xl)
        ) {
            AppStat(label = "STEPS", value = "6,240")
            AppStat(
                label = "PROTEIN",
                value = "38g",
                labelStyle = AppStatDefaults.MacroLabelStyle,
                labelColor = MaterialTheme.extendedColors.textSubtle,
                valueStyle = AppStatDefaults.MacroValueStyle
            )
            AppStat(
                label = "DURATION",
                value = "42 min",
                valueStyle = MaterialTheme.typography.titleLarge,
                horizontalAlignment = Alignment.CenterHorizontally,
                valueFirst = true
            )
        }
    }
}
