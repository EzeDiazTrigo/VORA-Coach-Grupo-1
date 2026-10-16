package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

enum class AppButtonStyle { Primary, Accent, Outlined }

@Composable
fun AppButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: AppButtonStyle = AppButtonStyle.Primary,
    enabled: Boolean = true,
    isLoading: Boolean = false,
    trailingIcon: ImageVector? = null,
    isCompact: Boolean = false
) {
    val colors = MaterialTheme.colorScheme
    val onClickSafe = { if (!isLoading) onClick() }
    val content: @Composable () -> Unit = {
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (isLoading) {
                CircularProgressIndicator(
                    modifier = Modifier.size(18.dp),
                    strokeWidth = 2.dp,
                    color = LocalContentColorOrDefault(style)
                )
            }
            Text(text, style = MaterialTheme.typography.labelLarge, maxLines = 1)
            if (trailingIcon != null) {
                Icon(trailingIcon, contentDescription = null, modifier = Modifier.size(18.dp))
            }
        }
    }
    val contentPadding = if (isCompact) PaddingValues(horizontal = 8.dp) else ButtonDefaults.ContentPadding
    val buttonModifier = modifier
        .fillMaxWidth()
        .heightIn(min = 52.dp)
    when (style) {
        AppButtonStyle.Outlined -> OutlinedButton(
            onClick = onClickSafe,
            modifier = buttonModifier,
            enabled = enabled,
            shape = PillShape,
            contentPadding = contentPadding,
            border = BorderStroke(1.dp, colors.outlineVariant),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = colors.primary),
            content = { content() }
        )
        else -> Button(
            onClick = onClickSafe,
            modifier = buttonModifier,
            enabled = enabled,
            shape = PillShape,
            contentPadding = contentPadding,
            colors = if (style == AppButtonStyle.Accent) {
                ButtonDefaults.buttonColors(containerColor = colors.tertiary, contentColor = colors.onTertiary)
            } else {
                ButtonDefaults.buttonColors(containerColor = colors.primary, contentColor = colors.onPrimary)
            },
            content = { content() }
        )
    }
}

@Composable
private fun LocalContentColorOrDefault(style: AppButtonStyle) = when (style) {
    AppButtonStyle.Primary -> MaterialTheme.colorScheme.onPrimary
    AppButtonStyle.Accent -> MaterialTheme.colorScheme.onTertiary
    AppButtonStyle.Outlined -> MaterialTheme.colorScheme.primary
}

@Preview(showBackground = true)
@Composable
private fun AppButtonPreview() {
    AppTheme {
        androidx.compose.foundation.layout.Column(
            modifier = Modifier.padding(MaterialTheme.spacing.screen),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
        ) {
            AppButton(text = "Save changes", onClick = {})
            AppButton(text = "Start workout", onClick = {}, style = AppButtonStyle.Accent)
            AppButton(text = "Details", onClick = {}, style = AppButtonStyle.Outlined)
            AppButton(text = "Sign in", onClick = {}, isLoading = true)
            AppButton(text = "Disabled", onClick = {}, enabled = false)
        }
    }
}
