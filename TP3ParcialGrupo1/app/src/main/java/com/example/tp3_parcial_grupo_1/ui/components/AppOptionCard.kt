package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.role
import androidx.compose.ui.semantics.selected
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.iconSizes
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val BorderWidth = 1.dp
private val SelectedBorderWidth = 2.dp

private val MarkSize = 20.dp

/**
 * Tarjeta de opción: título, descripción opcional y check; la elegida se rellena de menta.
 * Con [multiSelect] el lector de pantalla la anuncia como casilla (varias a la vez) en lugar de opción única.
 */
@Composable
fun AppOptionCard(
    title: String,
    selected: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    description: String? = null,
    multiSelect: Boolean = false
) {
    val colors = MaterialTheme.colorScheme
    Card(
        onClick = onClick,
        modifier = modifier
            .fillMaxWidth()
            .semantics {
                role = if (multiSelect) Role.Checkbox else Role.RadioButton
                this.selected = selected
            },
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
            SelectionMark(selected)
        }
    }
}

// Círculo de check de 20 dp de Figma: verde con check blanco si está elegida, borde sutil si no.
@Composable
private fun SelectionMark(selected: Boolean) {
    val colors = MaterialTheme.colorScheme
    Box(
        modifier = Modifier
            .size(MarkSize)
            .then(
                if (selected) Modifier.background(colors.primary, CircleShape)
                else Modifier.border(SelectedBorderWidth, colors.outlineVariant, CircleShape)
            ),
        contentAlignment = Alignment.Center
    ) {
        if (selected) {
            AppIcon(AppIcons.Check, contentDescription = null, size = MaterialTheme.iconSizes.xs, tint = colors.onPrimary)
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
            AppOptionCard(title = "Build strength", selected = true, onClick = {}, multiSelect = true)
            AppOptionCard(title = "Lose body fat", selected = false, onClick = {}, multiSelect = true)
        }
    }
}
