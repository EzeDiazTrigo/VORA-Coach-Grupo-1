package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.iconSizes
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val IconBoxSize = 40.dp

/**
 * Fila tocable de una lista: ícono en un círculo, título, subtítulo opcional y, por defecto, la flecha de Figma.
 * Se usa en Settings, Profile, Progress y Add Food.
 */
@Composable
fun AppListRow(
    icon: AppIcons,
    title: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    subtitle: String? = null,
    iconSize: Dp = MaterialTheme.iconSizes.xl,
    iconBoxSize: Dp = IconBoxSize,
    iconContainerColor: Color = MaterialTheme.colorScheme.background,
    iconContentDescription: String? = null,
    titleStyle: TextStyle = MaterialTheme.typography.labelLarge,
    subtitleStyle: TextStyle = MaterialTheme.typography.bodyMedium,
    subtitleColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    spacing: Dp = MaterialTheme.spacing.md,
    textSpacing: Dp = MaterialTheme.spacing.xxs,
    trailing: @Composable RowScope.() -> Unit = { AppListRowDefaults.Chevron() }
) {
    AppCard(modifier = modifier, onClick = onClick) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing)
        ) {
            Box(
                modifier = Modifier.size(iconBoxSize).background(iconContainerColor, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                AppIcon(icon, contentDescription = iconContentDescription, size = iconSize, tint = MaterialTheme.colorScheme.primary)
            }
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(textSpacing)) {
                Text(title, style = titleStyle)
                subtitle?.let { Text(it, style = subtitleStyle, color = subtitleColor) }
            }
            Row(verticalAlignment = Alignment.CenterVertically, content = trailing)
        }
    }
}

object AppListRowDefaults {
    // Flecha de las filas de Figma, en gris tenue.
    @Composable
    fun Chevron(size: Dp = MaterialTheme.iconSizes.xxl, tint: Color = MaterialTheme.extendedColors.textSubtle) {
        AppIcon(AppIcons.ChevronRight, contentDescription = null, size = size, tint = tint)
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun AppListRowPreview() {
    AppTheme {
        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.screen),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)
        ) {
            AppListRow(AppIcons.Bell, title = "Notifications", subtitle = "Reminders and summaries", onClick = {})
            AppListRow(AppIcons.Lock, title = "Privacy policy", onClick = {})
        }
    }
}
