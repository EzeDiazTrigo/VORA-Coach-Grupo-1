package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

// Tarjeta blanca con borde sutil; Panel es el contenedor verde destacado.
enum class AppCardStyle { Surface, Panel }

@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    style: AppCardStyle = AppCardStyle.Surface,
    onClick: (() -> Unit)? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val isPanel = style == AppCardStyle.Panel
    val shape = if (isPanel) MaterialTheme.shapes.large else MaterialTheme.shapes.medium
    val cardColors = CardDefaults.cardColors(
        containerColor = if (isPanel) colors.primary else colors.surface,
        contentColor = if (isPanel) colors.onPrimary else colors.onSurface
    )
    val border = if (isPanel) null else BorderStroke(1.dp, colors.outlineVariant)
    val inner: @Composable ColumnScope.() -> Unit = {
        Column(
            modifier = Modifier.padding(if (isPanel) MaterialTheme.spacing.screen else MaterialTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md),
            content = content
        )
    }
    if (onClick != null) {
        Card(onClick = onClick, modifier = modifier.fillMaxWidth(), shape = shape, colors = cardColors, border = border, content = inner)
    } else {
        Card(modifier = modifier.fillMaxWidth(), shape = shape, colors = cardColors, border = border, content = inner)
    }
}

@Preview(showBackground = true)
@Composable
private fun AppCardPreview() {
    AppTheme {
        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.screen),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
        ) {
            AppCard { Text("Breakfast · 420 kcal", style = MaterialTheme.typography.bodyLarge) }
            AppCard(style = AppCardStyle.Panel) {
                Text("Upper Body Strength", style = MaterialTheme.typography.headlineSmall)
                AppButton(text = "Start workout", onClick = {}, style = AppButtonStyle.Accent)
            }
        }
    }
}
