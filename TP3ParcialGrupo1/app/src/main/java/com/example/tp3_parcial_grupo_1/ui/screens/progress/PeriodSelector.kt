package com.example.tp3_parcial_grupo_1.ui.screens.progress

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.ui.strings.ProgressStrings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

@Composable
fun PeriodSelector(
    selected: ProgressPeriod,
    onSelect: (ProgressPeriod) -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    Surface(
        modifier = modifier.fillMaxWidth(),
        shape = PillShape,
        color = colors.surface,
        border = BorderStroke(1.dp, colors.outlineVariant)
    ) {
        Row(modifier = Modifier.padding(MaterialTheme.spacing.xxs)) {
            ProgressPeriod.entries.forEach { period ->
                val isSelected = period == selected
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .clip(PillShape)
                        .clickable { onSelect(period) }
                        .then(if (isSelected) Modifier.background(colors.primary, PillShape) else Modifier)
                        .padding(vertical = MaterialTheme.spacing.sm),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        period.label(),
                        style = MaterialTheme.typography.labelLarge,
                        color = if (isSelected) colors.onPrimary else colors.onSurfaceVariant
                    )
                }
            }
        }
    }
}

private fun ProgressPeriod.label(): String = when (this) {
    ProgressPeriod.Week -> ProgressStrings.PeriodWeek
    ProgressPeriod.Month -> ProgressStrings.PeriodMonth
    ProgressPeriod.ThreeMonths -> ProgressStrings.PeriodThreeMonths
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun PeriodSelectorPreview() {
    AppTheme {
        PeriodSelector(selected = ProgressPeriod.Week, onSelect = {}, modifier = Modifier.padding(MaterialTheme.spacing.screen))
    }
}
