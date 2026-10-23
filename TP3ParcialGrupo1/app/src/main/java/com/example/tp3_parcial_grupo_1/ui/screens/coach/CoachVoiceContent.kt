package com.example.tp3_parcial_grupo_1.ui.screens.coach

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.R
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.InlineLoading
import com.example.tp3_parcial_grupo_1.ui.components.VoraOrb
import com.example.tp3_parcial_grupo_1.ui.strings.CoachStrings
import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

// Figma: Voice / 02 · Listening
@Composable
internal fun CoachListeningContent(
    state: CoachUiState.Listening,
    onBack: () -> Unit,
    onFinishListening: () -> Unit,
    onCancel: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    DarkLayout(
        header = {
            CoachHeader(title = CoachStrings.ListeningTitle, onBack = onBack, dark = true) {
                LiveIndicator(text = CoachStrings.LiveAudio)
            }
        },
        bottom = {
            Surface(
                onClick = onFinishListening,
                modifier = Modifier.size(80.dp),
                shape = CircleShape,
                color = colors.secondary,
                contentColor = colors.primary
            ) {
                Icon(
                    painter = painterResource(R.drawable.ic_mic),
                    contentDescription = CoachStrings.StopListening,
                    modifier = Modifier.padding(26.dp)
                )
            }
            TextButton(onClick = onCancel) {
                Text(Strings.Cancel, style = MaterialTheme.typography.labelLarge, color = colors.secondary)
            }
        }
    ) {
        Spacer(Modifier.height(MaterialTheme.spacing.xxxl))
        VoraOrb(size = 170.dp, glowAlpha = 0.45f, showRipple = true)
        Spacer(Modifier.height(MaterialTheme.spacing.xxxl))
        DarkCard {
            Text(CoachStrings.LiveTranscript, style = MaterialTheme.typography.labelSmall, color = colors.secondary)
            Text(
                text = CoachStrings.quoted(state.transcript.trimEnd('.') + "…"),
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Medium,
                color = colors.onPrimary,
                modifier = Modifier.heightIn(min = 54.dp)
            )
        }
        Spacer(Modifier.height(MaterialTheme.spacing.xxxl))
        Text(
            text = CoachStrings.ListeningHint,
            style = MaterialTheme.typography.bodyMedium,
            color = colors.onPrimary.copy(alpha = 0.6f),
            textAlign = TextAlign.Center
        )
    }
}

// Figma: Voice / 03 · Processing
@Composable
internal fun CoachProcessingContent(
    state: CoachUiState.Processing,
    onBack: () -> Unit,
    onCancel: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    DarkLayout(
        header = { CoachHeader(title = CoachStrings.ProcessingTitle, onBack = onBack, dark = true) },
        bottom = {
            TextButton(onClick = onCancel) {
                Text(Strings.Cancel, style = MaterialTheme.typography.labelLarge, color = colors.onPrimary.copy(alpha = 0.7f))
            }
        }
    ) {
        Spacer(Modifier.height(50.dp))
        VoraOrb(size = 150.dp, glowAlpha = 0.4f)
        Spacer(Modifier.height(MaterialTheme.spacing.xxxl))
        Text(CoachStrings.Thinking, style = MaterialTheme.typography.labelLarge, color = colors.secondary)
        Spacer(Modifier.height(MaterialTheme.spacing.lg))
        DarkCard(shape = MaterialTheme.shapes.medium, contentPadding = 18.dp) {
            Text(
                text = CoachStrings.quoted(state.transcript),
                style = MaterialTheme.typography.bodyLarge,
                color = colors.onPrimary.copy(alpha = 0.8f)
            )
        }
        Spacer(Modifier.height(MaterialTheme.spacing.xxxl))
        InlineLoading(
            message = state.step,
            color = colors.onPrimary.copy(alpha = 0.6f),
            indicatorColor = colors.secondary
        )
    }
}

// Figma: Voice / 04 · Speaking
@Composable
internal fun CoachReplyContent(
    state: CoachUiState.Reply,
    onBack: () -> Unit,
    onTogglePause: () -> Unit,
    onReplay: () -> Unit,
    onStop: () -> Unit,
    onInterrupt: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    DarkLayout(
        header = {
            CoachHeader(title = CoachStrings.SpeakingTitle, onBack = onBack, dark = true) {
                StatusPill(text = CoachStrings.Speaking, dark = true)
            }
        },
        bottom = null
    ) {
        Spacer(Modifier.height(MaterialTheme.spacing.xxl))
        VoraOrb(size = 140.dp, glowAlpha = 0.4f, showRipple = true)
        Spacer(Modifier.height(MaterialTheme.spacing.xxl))
        DarkCard {
            Text(
                text = CoachStrings.quoted(state.reply),
                style = MaterialTheme.typography.titleSmall,
                fontWeight = FontWeight.Normal,
                color = colors.onPrimary
            )
        }
        Spacer(Modifier.height(MaterialTheme.spacing.xxl))
        ReplyProgress(state = state)
        Spacer(Modifier.height(MaterialTheme.spacing.lg))
        Row(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xl),
            verticalAlignment = Alignment.CenterVertically
        ) {
            PlaybackButton(R.drawable.ic_repeat_1, CoachStrings.Replay, onReplay)
            PlaybackButton(
                icon = if (state.isPaused) R.drawable.ic_play else R.drawable.ic_pause,
                description = if (state.isPaused) CoachStrings.Resume else CoachStrings.Pause,
                onClick = onTogglePause,
                size = 56.dp,
                iconSize = 20.dp,
                container = colors.secondary,
                content = colors.primary
            )
            PlaybackButton(R.drawable.ic_stop_circle, CoachStrings.Stop, onStop)
        }
        // En Figma el botón va debajo de los controles, no anclado al borde inferior.
        Spacer(Modifier.height(MaterialTheme.spacing.lg))
        AppButton(text = CoachStrings.TapToInterrupt, onClick = onInterrupt, style = AppButtonStyle.Accent)
        Spacer(Modifier.height(MaterialTheme.spacing.xl))
    }
}

@Composable
private fun ReplyProgress(state: CoachUiState.Reply) {
    val colors = MaterialTheme.colorScheme
    Column(modifier = Modifier.fillMaxWidth()) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(4.dp)
                .background(colors.onPrimary.copy(alpha = 0.15f), PillShape)
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(state.progress)
                    .height(4.dp)
                    .background(colors.secondary, PillShape)
            )
        }
        Spacer(Modifier.height(MaterialTheme.spacing.lg))
        Row(modifier = Modifier.fillMaxWidth()) {
            val timeColor = colors.onPrimary.copy(alpha = 0.5f)
            Text(CoachStrings.time(state.elapsedSeconds), style = MaterialTheme.typography.bodySmall, color = timeColor)
            Spacer(Modifier.weight(1f))
            Text(CoachStrings.remaining(state.remainingSeconds), style = MaterialTheme.typography.bodySmall, color = timeColor)
        }
    }
}

@Composable
private fun PlaybackButton(
    @DrawableRes icon: Int,
    description: String,
    onClick: () -> Unit,
    size: Dp = 40.dp,
    iconSize: Dp = 18.dp,
    container: Color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.1f),
    content: Color = MaterialTheme.colorScheme.onPrimary
) {
    Surface(
        onClick = onClick,
        modifier = Modifier.size(size),
        shape = CircleShape,
        color = container,
        contentColor = content
    ) {
        Box(contentAlignment = Alignment.Center) {
            Icon(painterResource(icon), contentDescription = description, modifier = Modifier.size(iconSize))
        }
    }
}

// Estructura común de las pantallas oscuras: encabezado, contenido desplazable y controles abajo (opcionales).
@Composable
private fun DarkLayout(
    header: @Composable () -> Unit,
    bottom: (@Composable ColumnScope.() -> Unit)?,
    content: @Composable ColumnScope.() -> Unit
) {
    Column(modifier = Modifier.fillMaxSize()) {
        header()
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = MaterialTheme.spacing.xl),
            horizontalAlignment = Alignment.CenterHorizontally,
            content = content
        )
        if (bottom != null) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.spacing.lg, bottom = MaterialTheme.spacing.xl),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm),
                content = bottom
            )
        }
    }
}

// Tarjeta translúcida de las pantallas oscuras (transcripción, respuesta).
@Composable
private fun DarkCard(
    shape: Shape = MaterialTheme.shapes.large,
    contentPadding: Dp = MaterialTheme.spacing.screen,
    content: @Composable ColumnScope.() -> Unit
) {
    val onPrimary = MaterialTheme.colorScheme.onPrimary
    Surface(
        modifier = Modifier.fillMaxWidth(),
        shape = shape,
        color = onPrimary.copy(alpha = 0.08f),
        border = BorderStroke(1.dp, onPrimary.copy(alpha = 0.1f))
    ) {
        Column(
            modifier = Modifier.padding(contentPadding),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md),
            content = content
        )
    }
}
