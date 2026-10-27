package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.HairlineWidth
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.iconSizes
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

/**
 * Aviso menta de VORA. Sin [title], el ícono u orbe va al lado del texto («Great protein choice…»);
 * con [title], va al lado de un rótulo en mayúsculas y el texto queda debajo («VORA COACH NOTE»).
 */
@Composable
fun AppNoticeCard(
    text: String,
    modifier: Modifier = Modifier,
    title: String? = null,
    leading: (@Composable () -> Unit)? = null,
    shape: Shape = MaterialTheme.shapes.small,
    borderColor: Color? = MaterialTheme.colorScheme.secondary,
    contentPadding: Dp = MaterialTheme.spacing.md,
    spacing: Dp = MaterialTheme.spacing.md,
    textStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(fontWeight = FontWeight.Medium),
    textColor: Color = MaterialTheme.colorScheme.primary,
    footer: (@Composable ColumnScope.() -> Unit)? = null
) {
    val container = Modifier
        .fillMaxWidth()
        .background(MaterialTheme.extendedColors.mintSoft, shape)
        .then(if (borderColor != null) Modifier.border(HairlineWidth, borderColor, shape) else Modifier)
        .padding(contentPadding)
    if (title == null && footer == null) {
        Row(
            modifier = modifier.then(container),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(spacing)
        ) {
            leading?.invoke()
            Text(text, style = textStyle, color = textColor)
        }
    } else {
        Column(modifier = modifier.then(container)) {
            if (title != null) {
                Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(spacing)) {
                    leading?.invoke()
                    Text(
                        text = title,
                        style = MaterialTheme.typography.labelSmall.copy(fontWeight = FontWeight.Bold),
                        color = MaterialTheme.colorScheme.primary
                    )
                }
                Spacer(Modifier.height(MaterialTheme.spacing.xs))
            }
            Text(text, style = textStyle, color = textColor)
            footer?.invoke(this)
        }
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun AppNoticeCardPreview() {
    AppTheme {
        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.screen),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
        ) {
            AppNoticeCard(
                text = "You're on track today. A protein-rich dinner would help you reach your target.",
                leading = {
                    AppIcon(AppIcons.Sparkles, contentDescription = null, size = MaterialTheme.iconSizes.xl, tint = MaterialTheme.colorScheme.primary)
                }
            )
            AppNoticeCard(
                text = "Based on your last session, keep the same weights and focus on controlled repetitions.",
                title = "VORA COACH NOTE",
                leading = { VoraLogo(size = 16.dp) },
                shape = MaterialTheme.shapes.large,
                borderColor = null,
                contentPadding = MaterialTheme.spacing.lg,
                spacing = MaterialTheme.spacing.xs,
                textStyle = MaterialTheme.typography.bodyMedium,
                textColor = MaterialTheme.colorScheme.onSurface
            )
        }
    }
}
