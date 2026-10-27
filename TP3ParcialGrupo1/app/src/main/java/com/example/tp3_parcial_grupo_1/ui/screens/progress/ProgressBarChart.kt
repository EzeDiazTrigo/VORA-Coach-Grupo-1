package com.example.tp3_parcial_grupo_1.ui.screens.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.data.demo.ProgressDemoData
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

// Figma: gráfico de 100 dp, barras de 24 dp (Workout) o 18 dp (Health Trends) con las esquinas de arriba de 6 dp.
private val ChartHeight = 100.dp
private val BarCorner = 6.dp
val WorkoutBarWidth = 24.dp
val HealthBarWidth = 18.dp
private const val EmptyBarFraction = 0.06f

@Composable
fun ProgressBarChart(bars: List<ChartBar>, modifier: Modifier = Modifier, barWidth: Dp = WorkoutBarWidth) {
    val maxValue = bars.maxOfOrNull { it.value } ?: 0f
    val barShape = RoundedCornerShape(topStart = BarCorner, topEnd = BarCorner)
    Row(modifier = modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)) {
        bars.forEach { bar ->
            Column(
                modifier = Modifier.weight(1f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)
            ) {
                Box(modifier = Modifier.fillMaxWidth().height(ChartHeight), contentAlignment = Alignment.BottomCenter) {
                    Box(
                        Modifier
                            .width(barWidth)
                            .fillMaxHeight(bar.heightFraction(maxValue))
                            .background(bar.color(), barShape)
                    )
                }
                Text(bar.label, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}

private fun ChartBar.heightFraction(maxValue: Float): Float =
    if (value <= 0f || maxValue <= 0f) EmptyBarFraction else (value / maxValue).coerceAtLeast(EmptyBarFraction)

@Composable
private fun ChartBar.color(): Color = when {
    value <= 0f -> MaterialTheme.colorScheme.outlineVariant
    isHighlighted -> MaterialTheme.colorScheme.primary
    else -> MaterialTheme.extendedColors.mint
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun ProgressBarChartPreview() {
    AppTheme {
        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.screen),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xl)
        ) {
            ProgressBarChart(ProgressDemoData.workoutPeriods.getValue(ProgressPeriod.Week).bars)
            ProgressBarChart(ProgressDemoData.healthTrends.bars, barWidth = HealthBarWidth)
        }
    }
}
