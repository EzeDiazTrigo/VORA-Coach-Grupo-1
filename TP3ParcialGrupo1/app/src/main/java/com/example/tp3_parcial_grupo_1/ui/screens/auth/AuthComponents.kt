package com.example.tp3_parcial_grupo_1.ui.screens.auth

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Face
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.VoraLogo
import com.example.tp3_parcial_grupo_1.ui.strings.AuthStrings
import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon

private val LogoSize = 64.dp
private val BiometricIconSize = 16.dp

@Composable
fun AuthHeader(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xxs)
    ) {
        VoraLogo(size = LogoSize, showWaveform = true)
        Text(Strings.AppName, style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.primary)
        Text(
            AuthStrings.Tagline,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
fun SocialButtonsRow(onClick: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)
    ) {
        SocialButton(AuthStrings.Apple, onClick)
        SocialButton(AuthStrings.Google, onClick)
        SocialButton(AuthStrings.Biometric, onClick)
    }
}

@Composable
private fun RowScope.SocialButton(text: String, onClick: () -> Unit) {
    Box(modifier = Modifier.weight(1f)) {
        AppButton(text = text, onClick = onClick, style = AppButtonStyle.Outlined, isCompact = true)
    }
}

@Composable
fun BiometricNote(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xs)
    ) {
        Icon(Icons.Outlined.Face, contentDescription = null, modifier = Modifier.size(BiometricIconSize), tint = MaterialTheme.colorScheme.primary)
        Text(AuthStrings.BiometricSignIn, style = MaterialTheme.typography.bodyMedium)
    }
}

@Composable
fun AuthFooterLink(
    text: String,
    linkText: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val linkColor = MaterialTheme.colorScheme.primary
    Text(
        text = buildAnnotatedString {
            append(text)
            withStyle(SpanStyle(color = linkColor, fontWeight = FontWeight.Bold, textDecoration = TextDecoration.Underline)) {
                append(linkText)
            }
        },
        style = MaterialTheme.typography.bodyMedium,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        textAlign = TextAlign.Center,
        modifier = modifier.fillMaxWidth().clickable(onClick = onClick)
    )
}

@Composable
fun AuthTextLink(text: String, onClick: () -> Unit, modifier: Modifier = Modifier) {
    Text(
        text = text,
        style = MaterialTheme.typography.bodySmall.copy(textDecoration = TextDecoration.Underline),
        color = MaterialTheme.colorScheme.primary,
        modifier = modifier.clickable(onClick = onClick).padding(vertical = MaterialTheme.spacing.xs)
    )
}
