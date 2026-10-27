package com.example.tp3_parcial_grupo_1.ui.screens.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.ui.components.AppIcon
import com.example.tp3_parcial_grupo_1.ui.components.AppIcons
import com.example.tp3_parcial_grupo_1.ui.components.AppTextLink
import com.example.tp3_parcial_grupo_1.ui.components.VoraBrandMark
import com.example.tp3_parcial_grupo_1.ui.components.VoraBrandMarkStyle
import com.example.tp3_parcial_grupo_1.ui.strings.AuthStrings
import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import com.example.tp3_parcial_grupo_1.ui.theme.HairlineWidth
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.iconSizes
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

@Composable
fun AuthHeader(modifier: Modifier = Modifier, markStyle: VoraBrandMarkStyle = VoraBrandMarkStyle.SignIn) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xxs)
    ) {
        VoraBrandMark(style = markStyle)
        Text(Strings.AppName, style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
        Text(
            AuthStrings.Tagline,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

// Píldora «EN ⌄» de la barra superior de Acceso. La app solo tiene textos en inglés: por ahora informa el modo demostración.
@Composable
fun LanguagePill(onClick: () -> Unit, modifier: Modifier = Modifier) {
    val contentColor = MaterialTheme.colorScheme.onSurfaceVariant
    Surface(
        onClick = onClick,
        modifier = modifier.semantics { contentDescription = AuthStrings.LanguageSelector },
        shape = PillShape,
        color = MaterialTheme.colorScheme.surface,
        contentColor = contentColor
    ) {
        Row(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.md, vertical = MaterialTheme.spacing.xs),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xs)
        ) {
            Text(AuthStrings.Language, style = MaterialTheme.typography.labelMedium)
            AppIcon(AppIcons.ChevronDown, contentDescription = null, size = MaterialTheme.iconSizes.sm, tint = contentColor)
        }
    }
}

private val SocialButtonHeight = 46.dp

@Composable
fun SocialButtonsRow(onClick: () -> Unit, modifier: Modifier = Modifier) {
    val brand = MaterialTheme.extendedColors.brand
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
    ) {
        SocialButton(AppIcons.LogoApple, AuthStrings.Apple, onClick)
        SocialButton(AppIcons.LogoGoogle, AuthStrings.Google, onClick)
        SocialButton(
            AppIcons.FaceId,
            AuthStrings.Biometric,
            onClick,
            containerColor = brand.biometricContainer,
            borderColor = brand.biometricBorder,
            iconTint = brand.dark
        )
    }
}

// Botón de solo ícono: Figma no muestra texto, así que el nombre va en contentDescription.
@Composable
private fun RowScope.SocialButton(
    icon: AppIcons,
    description: String,
    onClick: () -> Unit,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    borderColor: Color = MaterialTheme.colorScheme.outlineVariant,
    iconTint: Color = MaterialTheme.colorScheme.primary
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.weight(1f).height(SocialButtonHeight),
        shape = MaterialTheme.shapes.medium,
        color = containerColor,
        border = BorderStroke(HairlineWidth, borderColor)
    ) {
        Box(contentAlignment = Alignment.Center) {
            AppIcon(icon, contentDescription = description, size = MaterialTheme.iconSizes.xl, tint = iconTint)
        }
    }
}

@Composable
fun BiometricNote(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xs)
    ) {
        AppIcon(AppIcons.ShieldCheck, contentDescription = null, size = MaterialTheme.iconSizes.md, tint = MaterialTheme.colorScheme.primary)
        Text(AuthStrings.BiometricSignIn, style = MaterialTheme.typography.bodyMedium)
    }
}

// Enlace subrayado chico de los formularios («Forgot your password?»).
@Composable
fun AuthTextLink(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    AppTextLink(
        text = text,
        onClick = onClick,
        modifier = modifier,
        style = MaterialTheme.typography.bodySmall,
        underline = true,
        contentPadding = PaddingValues(vertical = MaterialTheme.spacing.xs)
    )
}

// Frase con enlace del pie de Login y Registro («Don't have an account? Sign up here»).
@Composable
fun AuthFooterLink(text: String, linkText: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    AppTextLink(
        text = linkText,
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        prefix = text,
        style = MaterialTheme.typography.bodyMedium,
        underline = true,
        fontWeight = FontWeight.Bold,
        textAlign = TextAlign.Center
    )
}
