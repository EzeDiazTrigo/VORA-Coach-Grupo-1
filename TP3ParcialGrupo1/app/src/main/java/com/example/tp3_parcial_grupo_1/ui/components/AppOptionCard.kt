package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
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

// Tarjeta de opción única: título, descripción opcional y radio; la elegida resalta el borde.
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
        colors = CardDefaults.cardColors(containerColor = colors.surface, contentColor = colors.onSurface),
        border = BorderStroke(
            width = if (selected) SelectedBorderWidth else BorderWidth,
            color = if (selected) colors.primary else colors.outlineVariant
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
            RadioButton(selected = selected, onClick = null)
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
