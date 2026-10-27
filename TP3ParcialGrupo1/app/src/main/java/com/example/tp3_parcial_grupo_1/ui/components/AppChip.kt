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
import com.example.tp3_parcial_grupo_1.ui.theme.iconSizes
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val ChipBorderWidth = 1.dp

// Mint: selección única con relleno menta; Dark: relleno verde oscuro; Outlined: selección múltiple con borde menta y check.
enum class AppChipStyle { Mint, Dark, Outlined }

// Chip de selección con radio completo.
@Composable
fun AppChip(
    text: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: AppChipStyle = AppChipStyle.Mint
) {
    val colors = MaterialTheme.colorScheme
    val selectedContainer = when (style) {
        AppChipStyle.Mint -> colors.secondary
        AppChipStyle.Dark -> colors.primary
        AppChipStyle.Outlined -> colors.surface
    }
    val selectedLabel = if (style == AppChipStyle.Dark) colors.onPrimary else colors.onSurface
    val selectedBorder = if (style == AppChipStyle.Dark) colors.primary else colors.secondary
    FilterChip(
        selected = selected,
        onClick = onClick,
        label = { Text(text, style = MaterialTheme.typography.labelLarge) },
        modifier = modifier,
        shape = PillShape,
        trailingIcon = if (style == AppChipStyle.Outlined && selected) {
            { AppIcon(AppIcons.Check, contentDescription = null, size = MaterialTheme.iconSizes.md) }
        } else {
            null
        },
        colors = FilterChipDefaults.filterChipColors(
            containerColor = colors.surface,
            labelColor = colors.onSurface,
            selectedContainerColor = selectedContainer,
            selectedLabelColor = selectedLabel,
            selectedTrailingIconColor = colors.primary
        ),
        border = FilterChipDefaults.filterChipBorder(
            enabled = true,
            selected = selected,
            borderColor = colors.outlineVariant,
            selectedBorderColor = selectedBorder,
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
            AppChip(text = "4 days", selected = true, onClick = {}, style = AppChipStyle.Dark)
            AppChip(text = "Chicken", selected = true, onClick = {}, style = AppChipStyle.Outlined)
            AppChip(text = "Cardio", selected = false, onClick = {})
        }
    }
}
