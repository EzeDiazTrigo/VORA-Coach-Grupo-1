package com.example.tp3_parcial_grupo_1.ui.screens.nutrition

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
import androidx.compose.ui.unit.sp
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonDefaults
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppIcon
import com.example.tp3_parcial_grupo_1.ui.components.AppIcons
import com.example.tp3_parcial_grupo_1.ui.components.VoraOrb

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
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
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
                    Spacer(modifier = Modifier.width(8.dp))
                    Text(
                        text = "Voice Logging",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.surface,
                            fontWeight = FontWeight.Medium,
                            fontSize = 14.sp
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
                    AppIcon(AppIcons.Close, contentDescription = "Close", tint = MaterialTheme.colorScheme.surface, size = 18.dp)
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Pulsing Audio Orb
            VoraOrb(size = 160.dp, glowAlpha = 0.3f, showRipple = true)

            Spacer(modifier = Modifier.height(32.dp))

            Text(
                text = "Listening...",
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = MaterialTheme.colorScheme.surface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "\"Keep talking. I'm listening.\"",
                style = MaterialTheme.typography.bodyMedium.copy(
                    color = MaterialTheme.colorScheme.surface.copy(alpha = 0.7f),
                    fontSize = 13.sp
                )
            )

            Spacer(modifier = Modifier.height(32.dp))

            // Transcript Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.1f))
                    .padding(16.dp)
            ) {
                Text(
                    text = "\"I had grilled chicken with rice and some mixed vegetables for lunch.\"",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.surface,
                        fontSize = 14.sp
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            AppButton(
                text = "Done Speaking",
                onClick = onDoneSpeaking,
                style = AppButtonStyle.Secondary,
                minHeight = AppButtonDefaults.MediumHeight,
                textStyle = AppButtonDefaults.BoldTextStyle
            )

            Spacer(modifier = Modifier.height(12.dp))

            TextButton(onClick = onTypeInsteadClick) {
                Text(
                    text = "Type instead",
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                        fontSize = 13.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
        }
    }
}
