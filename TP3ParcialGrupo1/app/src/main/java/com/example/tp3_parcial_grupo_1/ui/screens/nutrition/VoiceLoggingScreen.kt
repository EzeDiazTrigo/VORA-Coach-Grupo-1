package com.example.tp3_parcial_grupo_1.ui.screens.nutrition

import com.example.tp3_parcial_grupo_1.ui.theme.spacing
import com.example.tp3_parcial_grupo_1.ui.theme.message
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonDefaults
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppIcon
import com.example.tp3_parcial_grupo_1.ui.components.AppIcons
import com.example.tp3_parcial_grupo_1.ui.components.VoraOrb
import com.example.tp3_parcial_grupo_1.ui.strings.NutritionStrings

@Composable
fun VoiceLoggingScreen(
    onCloseClick: () -> Unit,
    onDoneSpeaking: () -> Unit,
    onTypeInsteadClick: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .safeDrawingPadding()
            .padding(MaterialTheme.spacing.screen)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.spacing.lg),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    Box(
                        modifier = Modifier
                            .size(8.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.secondary)
                    )
                    Spacer(modifier = Modifier.width(MaterialTheme.spacing.sm))
                    Text(
                        text = NutritionStrings.voiceLogging,
                        style = MaterialTheme.typography.message.copy(
                            color = MaterialTheme.colorScheme.surface,
                            fontWeight = FontWeight.Medium
)
                    )
                }
                IconButton(
                    onClick = onCloseClick,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.1f))
                ) {
                    AppIcon(AppIcons.Close, contentDescription = NutritionStrings.close, tint = MaterialTheme.colorScheme.surface, size = 18.dp)
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Pulsing Audio Orb
            VoraOrb(size = 160.dp, glowAlpha = 0.3f, showRipple = true)

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.xxl))

            Text(
                text = NutritionStrings.listening,
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.surface,
                    fontWeight = FontWeight.Bold
)
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.sm))
            Text(
                text = NutritionStrings.keepTalking,
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f)
)
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.xxl))

            // Transcript Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.1f))
                    .padding(MaterialTheme.spacing.lg)
            ) {
                Text(
                    text = NutritionStrings.voiceTranscript,
                    style = MaterialTheme.typography.message.copy(
                        color = MaterialTheme.colorScheme.surface
),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.xxl))

            AppButton(
                text = NutritionStrings.doneSpeaking,
                onClick = onDoneSpeaking,
                style = AppButtonStyle.Secondary,
                minHeight = AppButtonDefaults.MediumHeight,
                textStyle = AppButtonDefaults.BoldTextStyle
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))

            TextButton(onClick = onTypeInsteadClick) {
                Text(
                    text = NutritionStrings.typeInstead,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))
        }
    }
}
