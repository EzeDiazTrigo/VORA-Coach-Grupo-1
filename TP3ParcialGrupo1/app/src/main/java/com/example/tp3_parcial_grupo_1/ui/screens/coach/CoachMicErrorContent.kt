package com.example.tp3_parcial_grupo_1.ui.screens.coach

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppIcon
import com.example.tp3_parcial_grupo_1.ui.components.AppIcons
import com.example.tp3_parcial_grupo_1.ui.components.OrbVariant
import com.example.tp3_parcial_grupo_1.ui.components.VoraOrb
import com.example.tp3_parcial_grupo_1.ui.strings.CoachStrings
import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

// Figma: Voice / 06 · Error. «Permitir acceso» no pide el permiso: la app no usa el micrófono (NAV05).
@Composable
internal fun CoachMicErrorContent(actions: CoachActions) {
    val spacing = MaterialTheme.spacing
    Column(modifier = Modifier.fillMaxSize()) {
        CoachHeader(title = CoachStrings.ErrorTitle, onBack = actions.onBack, dark = false)
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = spacing.xl),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(spacing.xxl))
            VoraOrb(
                size = 120.dp,
                variant = OrbVariant.Muted,
                glowAlpha = 0.1f,
                icon = AppIcons.VoraRetry,
                iconFraction = 0.28f
            )
            Spacer(Modifier.height(spacing.xxl))
            Text(
                text = CoachStrings.ErrorHeadline,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(spacing.sm))
            Text(
                text = CoachStrings.ErrorSubtitle,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(spacing.xxl))
            MicAccessCard(onAllow = actions.onDemoAction, onNotNow = actions.onDismissError)
            Spacer(Modifier.height(spacing.xl))
        }
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = spacing.xl, end = spacing.xl, bottom = spacing.md),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            AppButton(text = Strings.Retry, onClick = actions.onRetry, style = AppButtonStyle.Secondary)
            TextButton(onClick = actions.onTypeInstead) {
                Text(
                    text = CoachStrings.TypeInstead,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textDecoration = TextDecoration.Underline
                )
            }
        }
    }
}

@Composable
private fun MicAccessCard(onAllow: () -> Unit, onNotNow: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    AppCard {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
        ) {
            Box(
                modifier = Modifier
                    .size(36.dp)
                    .background(colors.error.copy(alpha = 0.12f), CircleShape),
                contentAlignment = Alignment.Center
            ) {
                AppIcon(
                    AppIcons.Mic,
                    contentDescription = null,
                    tint = colors.error,
                    size = 18.dp
                )
            }
            Text(
                text = CoachStrings.MicAccessTitle,
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Medium,
                color = colors.onSurface
            )
        }
        Text(
            text = CoachStrings.MicAccessBody,
            style = MaterialTheme.typography.bodyMedium,
            color = colors.onSurfaceVariant
        )
        Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)) {
            AppButton(
                text = CoachStrings.AllowAccess,
                onClick = onAllow,
                modifier = Modifier.weight(1f),
                isCompact = true
            )
            AppButton(
                text = CoachStrings.NotNow,
                onClick = onNotNow,
                modifier = Modifier.weight(1f),
                style = AppButtonStyle.Outlined,
                isCompact = true
            )
        }
    }
}
