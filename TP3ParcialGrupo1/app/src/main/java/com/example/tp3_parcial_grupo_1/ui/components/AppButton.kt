package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
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
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.iconSizes
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

// Secondary: menta, como «Try again» del Asistente.
enum class AppButtonStyle { Primary, Secondary, Accent, Outlined }

// Altos y textos de botón que usa Figma.
object AppButtonDefaults {
    // Botón principal de las pantallas.
    val LargeHeight = 52.dp

    // «Start voice session», «Edit Meal» y los botones de 350 × 43 de Nutrition.
    val MediumHeight = 43.dp

    // Píldoras de acción de los paneles («Voice», «Photo», «Search»).
    val SmallHeight = 38.dp

    // Texto en negrita de «Sign In», «Sign Up» y los botones de Nutrition.
    val BoldTextStyle: TextStyle
        @Composable get() = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
}

/**
 * Botón del diseño. [shape] permite la esquina de 16 dp de Login; [contentAlignment] = Start pega el ícono y el texto
 * al borde izquierdo, como «Start voice session»; [minHeight] elige entre los altos de [AppButtonDefaults].
 */
@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: AppButtonStyle = AppButtonStyle.Primary,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    leadingIcon: AppIcons? = null,
    trailingIcon: AppIcons? = null,
    isCompact: Boolean = false,
    fullWidth: Boolean = true,
    textColor: Color? = null,
    borderColor: Color? = null,
    containerColor: Color? = null,
    shape: Shape = PillShape,
    minHeight: Dp = AppButtonDefaults.LargeHeight,
    textStyle: TextStyle = MaterialTheme.typography.labelLarge,
    contentAlignment: Alignment.Horizontal = Alignment.CenterHorizontally
) {
    val colors = MaterialTheme.colorScheme
    val isEnabled = enabled && !isLoading
    val actualTextColor = textColor ?: contentColorFor(style)
    val content: @Composable () -> Unit = {
        Row(
            modifier = if (contentAlignment == Alignment.Start) Modifier.fillMaxWidth() else Modifier,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm, contentAlignment),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(MaterialTheme.iconSizes.lg),
                    strokeWidth = 2.dp,
                    color = actualTextColor
                )
            }
            if (leadingIcon != null) {
                AppIcon(leadingIcon, contentDescription = null, size = MaterialTheme.iconSizes.lg, tint = actualTextColor)
            }
            Text(text, style = textStyle, maxLines = 1, color = actualTextColor)
            if (trailingIcon != null) {
                AppIcon(trailingIcon, contentDescription = null, size = MaterialTheme.iconSizes.lg, tint = actualTextColor)
            }
        }
    }
    val contentPadding = if (isCompact) PaddingValues(horizontal = MaterialTheme.spacing.sm) else ButtonDefaults.ContentPadding
    val buttonModifier = (if (fullWidth) modifier.fillMaxWidth() else modifier).heightIn(min = minHeight)
    when (style) {
        AppButtonStyle.Outlined -> OutlinedButton(
            onClick = onClick,
            modifier = buttonModifier,
            enabled = isEnabled,
            shape = shape,
            contentPadding = contentPadding,
            border = BorderStroke(1.dp, borderColor ?: colors.outlineVariant),
            colors = ButtonDefaults.outlinedButtonColors(
                containerColor = containerColor ?: Color.Unspecified,
                contentColor = actualTextColor
            ),
            content = { content() }
        )
        else -> Button(
            onClick = onClick,
            modifier = buttonModifier,
            enabled = isEnabled,
            shape = shape,
            contentPadding = contentPadding,
            border = borderColor?.let { BorderStroke(1.dp, it) },
            colors = ButtonDefaults.buttonColors(
                containerColor = containerColor ?: when (style) {
                    AppButtonStyle.Accent -> colors.tertiary
                    AppButtonStyle.Secondary -> colors.secondary
                    else -> colors.primary
                },
                contentColor = actualTextColor
            ),
            content = { content() }
        )
    }
}

@Composable
private fun contentColorFor(style: AppButtonStyle) = when (style) {
    AppButtonStyle.Primary -> MaterialTheme.colorScheme.onPrimary
    AppButtonStyle.Secondary -> MaterialTheme.colorScheme.onSecondary
    AppButtonStyle.Accent -> MaterialTheme.colorScheme.onTertiary
    AppButtonStyle.Outlined -> MaterialTheme.colorScheme.primary
}

@Preview(showBackground = true)
@Composable
private fun AppButtonPreview() {
    AppTheme {
        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.screen),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
        ) {
            AppButton(text = "Save changes", onClick = {})
            AppButton(text = "Start workout", onClick = {}, style = AppButtonStyle.Accent)
            AppButton(text = "Details", onClick = {}, style = AppButtonStyle.Outlined)
            AppButton(
                text = "Start voice session",
                onClick = {},
                style = AppButtonStyle.Accent,
                leadingIcon = AppIcons.Mic,
                minHeight = AppButtonDefaults.MediumHeight,
                contentAlignment = Alignment.Start
            )
            AppButton(
                text = "Sign In",
                onClick = {},
                style = AppButtonStyle.Accent,
                shape = MaterialTheme.shapes.medium,
                textStyle = AppButtonDefaults.BoldTextStyle
            )
            AppButton(text = "Sign in", onClick = {}, isLoading = true)
            AppButton(text = "Disabled", onClick = {}, enabled = false)
        }
    }
}
