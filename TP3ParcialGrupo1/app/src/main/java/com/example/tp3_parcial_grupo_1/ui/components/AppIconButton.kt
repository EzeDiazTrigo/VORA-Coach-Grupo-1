package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.HairlineWidth
import com.example.tp3_parcial_grupo_1.ui.theme.iconSizes
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

// Tamaño mínimo de un elemento tocable (regla de 48 dp).
val MinTouchTarget = 48.dp

// Colores del botón circular: fondo, ícono y borde opcional.
data class AppIconButtonColors(
    val container: Color,
    val content: Color,
    val border: Color? = null
)

// Las cuatro variantes de Figma: sin fondo, blanco, blanco con borde y translúcido sobre verde.
object AppIconButtonDefaults {
    @Composable
    fun plain(content: Color = LocalContentColor.current) = AppIconButtonColors(Color.Transparent, content)

    @Composable
    fun surface() = AppIconButtonColors(MaterialTheme.colorScheme.surface, MaterialTheme.colorScheme.onSurface)

    @Composable
    fun outlined() = AppIconButtonColors(
        container = MaterialTheme.colorScheme.surface,
        content = MaterialTheme.colorScheme.onSurface,
        border = MaterialTheme.colorScheme.outlineVariant
    )

    @Composable
    fun onDark(withBorder: Boolean = false): AppIconButtonColors {
        val tint = MaterialTheme.colorScheme.onPrimary.copy(alpha = OnDarkAlpha)
        return AppIconButtonColors(tint, MaterialTheme.colorScheme.onPrimary, if (withBorder) tint else null)
    }
}

private const val OnDarkAlpha = 0.1f

/**
 * Botón circular con un ícono del diseño. [size] es el tamaño visible (32, 40, 44 o 48 dp según la pantalla);
 * si es menor a [MinTouchTarget], Compose amplía el área táctil hasta 48 dp sin mover el resto de la pantalla.
 */
@Composable
fun AppIconButton(
    icon: AppIcons,
    contentDescription: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = MinTouchTarget,
    iconSize: Dp = MaterialTheme.iconSizes.xxl,
    colors: AppIconButtonColors = AppIconButtonDefaults.plain(),
    shadowElevation: Dp = 0.dp,
    enabled: Boolean = true
) {
    Surface(
        onClick = onClick,
        modifier = modifier.size(size),
        enabled = enabled,
        shape = CircleShape,
        color = colors.container,
        contentColor = colors.content,
        border = colors.border?.let { BorderStroke(HairlineWidth, it) },
        shadowElevation = shadowElevation
    ) {
        Box(contentAlignment = Alignment.Center) {
            AppIcon(icon, contentDescription = contentDescription, size = iconSize)
        }
    }
}

// Botón «volver» de las pantallas internas: el chevron de Figma dentro de un AppIconButton.
@Composable
fun AppBackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    size: Dp = MinTouchTarget,
    iconSize: Dp = MaterialTheme.iconSizes.xxl,
    colors: AppIconButtonColors = AppIconButtonDefaults.plain()
) {
    AppIconButton(
        icon = AppIcons.ChevronLeft,
        contentDescription = Strings.Back,
        onClick = onClick,
        modifier = modifier,
        size = size,
        iconSize = iconSize,
        colors = colors
    )
}

@Preview(showBackground = true)
@Composable
private fun AppIconButtonPreview() {
    AppTheme {
        Row(
            modifier = Modifier.padding(MaterialTheme.spacing.screen),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md),
            verticalAlignment = Alignment.CenterVertically
        ) {
            AppBackButton(onClick = {})
            AppBackButton(onClick = {}, size = 40.dp, iconSize = MaterialTheme.iconSizes.xl, colors = AppIconButtonDefaults.surface())
            AppBackButton(onClick = {}, size = 32.dp, iconSize = MaterialTheme.iconSizes.sm, colors = AppIconButtonDefaults.outlined())
            AppIconButton(AppIcons.Settings, contentDescription = "Settings", onClick = {}, size = 44.dp, colors = AppIconButtonDefaults.outlined())
        }
    }
}
