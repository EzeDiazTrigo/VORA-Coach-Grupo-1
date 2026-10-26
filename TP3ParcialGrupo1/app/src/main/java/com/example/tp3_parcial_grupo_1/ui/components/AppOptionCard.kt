package com.example.tp3_parcial_grupo_1.ui.components

import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material.icons.outlined.RadioButtonUnchecked
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.Icons
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val BorderWidth = 1.dp
private val SelectedBorderWidth = 2.dp

private val StateIconSize = 24.dp

// Tarjeta de opción: título, descripción opcional y check; la elegida se rellena de menta.
@Composable
fun AppOptionCard(
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    description: String? = null
) {
    val colors = MaterialTheme.colorScheme
    Card(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        shape = MaterialTheme.shapes.medium,
        colors = CardDefaults.cardColors(
            containerColor = if (selected) MaterialTheme.extendedColors.mintSoft else colors.surface,
            contentColor = colors.onSurface
        ),
        border = BorderStroke(
            width = if (selected) SelectedBorderWidth else BorderWidth,
            color = if (selected) colors.secondary else colors.outlineVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(MaterialTheme.spacing.lg),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
        ) {
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xxs)
            ) {
                Text(title, style = MaterialTheme.typography.labelLarge)
                if (description != null) {
                    Text(description, style = MaterialTheme.typography.bodyMedium, color = colors.onSurfaceVariant)
                }
            }
            Icon(
                imageVector = if (selected) Icons.Filled.CheckCircle else Icons.Outlined.RadioButtonUnchecked,
                contentDescription = null,
                modifier = Modifier.size(StateIconSize),
                tint = if (selected) colors.primary else colors.outlineVariant
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppOptionCardPreview() {
    AppTheme {
        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.screen),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
        ) {
            AppOptionCard(
                title = "Improve fitness",
                description = "Condition cardiovascular & energy systems",
                selected = true,
                onClick = {}
            )
            AppOptionCard(title = "Lose body fat", selected = false, onClick = {})
        }
    }
}
