package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val ChipBorderWidth = 1.dp

// Chip de selección con radio completo; el seleccionado se rellena de verde.
@Composable
fun AppChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(text, style = MaterialTheme.typography.labelLarge) },
        modifier = modifier,
        shape = PillShape,
        colors = FilterChipDefaults.filterChipColors(
            containerColor = colors.surface,
            labelColor = colors.onSurface,
            selectedContainerColor = colors.primary,
            selectedLabelColor = colors.onPrimary
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selected,
            borderColor = colors.outlineVariant,
            selectedBorderColor = colors.primary,
            borderWidth = ChipBorderWidth
        )
    )
}

@Preview(showBackground = true)
@Composable
private fun AppChipPreview() {
    AppTheme {
        Row(
            modifier = Modifier.padding(MaterialTheme.spacing.screen),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)
        ) {
            AppChip(text = "Strength", selected = true, onClick = {})
            AppChip(text = "Cardio", selected = false, onClick = {})
        }
    }
}
