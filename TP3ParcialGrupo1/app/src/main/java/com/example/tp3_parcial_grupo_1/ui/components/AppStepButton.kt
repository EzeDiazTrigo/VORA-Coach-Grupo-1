package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val StepButtonSize = 32.dp

/**
 * Botón cuadrado de 32 dp para sumar o restar (gramos en Review Meal): esquina de 8 dp, fondo gris claro
 * y el signo en 18 sp. El área táctil se amplía a 48 dp sin cambiar el tamaño visible.
 */
@Composable
fun AppStepButton(
    symbol: String,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true
) {
    Surface(
        onClick = onClick,
        modifier = modifier
            .size(StepButtonSize)
            .semantics { this.contentDescription = contentDescription },
        enabled = enabled,
        shape = MaterialTheme.shapes.extraSmall,
        color = MaterialTheme.colorScheme.background,
        contentColor = MaterialTheme.colorScheme.onSurface
    ) {
        Box(contentAlignment = Alignment.Center) {
            Text(symbol, style = MaterialTheme.typography.titleMedium)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppStepButtonPreview() {
    AppTheme {
        Row(
            modifier = Modifier.padding(MaterialTheme.spacing.screen),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppStepButton(symbol = "-", contentDescription = "Decrease quantity", onClick = {})
            Text("180g", style = MaterialTheme.typography.labelLarge)
            AppStepButton(symbol = "+", contentDescription = "Increase quantity", onClick = {})
        }
    }
}
