package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

/**
 * Encabezado de las pantallas internas: control de navegación a la izquierda (casi siempre [AppBackButton]),
 * título con rótulo, subtítulo o indicador opcionales, y acciones a la derecha. [centerTitle] centra el título, como en Workout.
 */
@Composable
fun AppTopBar(
    title: String,
    modifier: Modifier = Modifier,
    navigation: (@Composable () -> Unit)? = null,
    eyebrow: String? = null,
    subtitle: String? = null,
    titleStyle: TextStyle = MaterialTheme.typography.headlineMedium,
    titleColor: Color = Color.Unspecified,
    subtitleColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    centerTitle: Boolean = false,
    spacing: Dp = MaterialTheme.spacing.sm,
    titleAccessory: (@Composable () -> Unit)? = null,
    trailing: @Composable RowScope.() -> Unit = {}
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(spacing)
    ) {
        navigation?.invoke()
        Column(modifier = Modifier.weight(1f)) {
            eyebrow?.let {
                Text(it, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.extendedColors.textSubtle)
            }
            if (titleAccessory == null) {
                Text(
                    text = title,
                    style = titleStyle,
                    color = titleColor,
                    textAlign = if (centerTitle) TextAlign.Center else TextAlign.Start,
                    modifier = Modifier.fillMaxWidth()
                )
            } else {
                // Indicador pegado al título, como el punto «en línea» del Asistente.
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(spacing)) {
                    Text(text = title, style = titleStyle, color = titleColor)
                    titleAccessory()
                }
            }
            subtitle?.let { Text(it, style = MaterialTheme.typography.bodyMedium, color = subtitleColor) }
        }
        trailing()
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun AppTopBarPreview() {
    AppTheme {
        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.screen),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.lg)
        ) {
            AppTopBar(title = "Add Food", navigation = { AppBackButton(onClick = {}) })
            AppTopBar(
                title = "Health Trends",
                subtitle = "Last 30 days",
                spacing = MaterialTheme.spacing.lg,
                navigation = { AppBackButton(onClick = {}, size = 44.dp, colors = AppIconButtonDefaults.outlined()) },
                trailing = {
                    AppIconButton(AppIcons.MoreHorizontal, "More", onClick = {}, size = 44.dp, colors = AppIconButtonDefaults.outlined())
                }
            )
            AppTopBar(
                title = "Workout Overview",
                titleStyle = MaterialTheme.typography.titleMedium,
                centerTitle = true,
                navigation = { AppBackButton(onClick = {}, size = 40.dp, colors = AppIconButtonDefaults.surface()) },
                trailing = { AppIconButton(AppIcons.HelpCircle, "Help", onClick = {}, size = 40.dp, colors = AppIconButtonDefaults.surface()) }
            )
        }
    }
}
