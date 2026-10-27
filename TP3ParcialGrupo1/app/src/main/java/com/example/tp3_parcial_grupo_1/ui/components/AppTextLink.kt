package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.iconSizes
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

/**
 * Enlace de texto del diseño. [prefix] agrega una frase antes del enlace («Don't have an account? Sign up here»)
 * y [trailingIcon] una flecha después («See weekly review →»).
 */
@Composable
fun AppTextLink(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    prefix: String? = null,
    style: TextStyle = MaterialTheme.typography.labelLarge,
    color: Color = MaterialTheme.colorScheme.primary,
    prefixColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    underline: Boolean = false,
    fontWeight: FontWeight? = null,
    textAlign: TextAlign? = null,
    trailingIcon: AppIcons? = null,
    contentPadding: PaddingValues = PaddingValues()
) {
    // El margen va dentro del área tocable para que el enlace sea más fácil de tocar.
    val clickModifier = modifier.clickable(role = Role.Button, onClick = onClick).padding(contentPadding)
    val linkStyle = SpanStyle(
        color = color,
        fontWeight = fontWeight,
        textDecoration = if (underline) TextDecoration.Underline else null
    )
    val label = buildAnnotatedString {
        if (prefix != null) append(prefix)
        withStyle(linkStyle) { append(text) }
    }
    if (trailingIcon == null) {
        Text(text = label, style = style, color = prefixColor, textAlign = textAlign, modifier = clickModifier)
    } else {
        Row(
            modifier = clickModifier,
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xs)
        ) {
            Text(text = label, style = style, color = prefixColor, textAlign = textAlign)
            AppIcon(trailingIcon, contentDescription = null, tint = color, size = MaterialTheme.iconSizes.md)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppTextLinkPreview() {
    AppTheme {
        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.screen),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
        ) {
            AppTextLink(text = "See weekly review", onClick = {}, trailingIcon = AppIcons.ArrowRight)
            AppTextLink(text = "Forgot your password?", onClick = {}, style = MaterialTheme.typography.bodySmall, underline = true)
            AppTextLink(
                text = "Sign up here",
                onClick = {},
                prefix = "Don't have an account? ",
                style = MaterialTheme.typography.bodyMedium,
                underline = true,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
