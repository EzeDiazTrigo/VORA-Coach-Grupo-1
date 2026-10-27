package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.HairlineWidth
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

/**
 * Surface: tarjeta blanca con borde sutil. Panel: contenedor verde destacado.
 * Translucent: tarjeta blanca al 12 % sobre los fondos verdes (Pre Workout, Active Workout, Asistente y Weekly Review).
 */
enum class AppCardStyle { Surface, Panel, Translucent }

private const val TranslucentAlpha = 0.12f

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun AppCard(
    modifier: Modifier = Modifier,
    style: AppCardStyle = AppCardStyle.Surface,
    onClick: (() -> Unit)? = null,
    onLongClick: (() -> Unit)? = null,
    onLongClickLabel: String? = null,
    shape: Shape? = null,
    contentPadding: PaddingValues? = null,
    spacing: Dp = MaterialTheme.spacing.md,
    containerColor: Color? = null,
    borderColor: Color? = null,
    content: @Composable ColumnScope.() -> Unit
) {
    val colors = MaterialTheme.colorScheme
    val cardShape = shape ?: when (style) {
        AppCardStyle.Surface -> MaterialTheme.shapes.medium
        AppCardStyle.Panel, AppCardStyle.Translucent -> MaterialTheme.shapes.large
    }
    val cardColors = CardDefaults.cardColors(
        containerColor = containerColor ?: when (style) {
            AppCardStyle.Surface -> colors.surface
            AppCardStyle.Panel -> colors.primary
            AppCardStyle.Translucent -> colors.onPrimary.copy(alpha = TranslucentAlpha)
        },
        contentColor = if (style == AppCardStyle.Surface) colors.onSurface else colors.onPrimary
    )
    val border = (borderColor ?: when (style) {
        AppCardStyle.Surface -> colors.outlineVariant
        AppCardStyle.Panel -> null
        AppCardStyle.Translucent -> colors.onPrimary.copy(alpha = TranslucentAlpha)
    })?.let { BorderStroke(HairlineWidth, it) }
    val padding = contentPadding ?: PaddingValues(
        if (style == AppCardStyle.Panel) MaterialTheme.spacing.screen else MaterialTheme.spacing.lg
    )
    val inner: @Composable ColumnScope.() -> Unit = {
        Column(
            modifier = Modifier.padding(padding),
            verticalArrangement = Arrangement.spacedBy(spacing),
            content = content
        )
    }
    if (onClick != null && onLongClick != null) {
        // Card(onClick) no tiene toque largo: se recorta a la forma de la tarjeta para que el efecto no se salga.
        Card(
            modifier = modifier
                .fillMaxWidth()
                .clip(cardShape)
                .combinedClickable(onClick = onClick, onLongClick = onLongClick, onLongClickLabel = onLongClickLabel),
            shape = cardShape,
            colors = cardColors,
            border = border,
            content = inner
        )
    } else if (onClick != null) {
        Card(onClick = onClick, modifier = modifier.fillMaxWidth(), shape = cardShape, colors = cardColors, border = border, content = inner)
    } else {
        Card(modifier = modifier.fillMaxWidth(), shape = cardShape, colors = cardColors, border = border, content = inner)
    }
}

/**
 * Encabezado de los paneles de VORA: orbe, título en menta y algo opcional a la derecha (una etiqueta o un estado).
 */
@Composable
fun AppPanelHeader(
    title: String,
    orb: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    titleStyle: TextStyle = MaterialTheme.typography.labelLarge,
    titleColor: Color = MaterialTheme.extendedColors.mint,
    spacing: Dp = MaterialTheme.spacing.md,
    trailing: @Composable RowScope.() -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing)
    ) {
        orb()
        Text(title, style = titleStyle, color = titleColor, modifier = Modifier.weight(1f))
        trailing()
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
                AppPanelHeader(title = "VORA Insight", orb = { VoraLogo(size = 36.dp) })
                Text("Upper Body Strength", style = MaterialTheme.typography.headlineSmall)
                AppButton(text = "Start workout", onClick = {}, style = AppButtonStyle.Accent)
                AppCard(style = AppCardStyle.Translucent) {
                    Text("Today's focus", style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.tertiary)
                }
            }
        }
    }
}
