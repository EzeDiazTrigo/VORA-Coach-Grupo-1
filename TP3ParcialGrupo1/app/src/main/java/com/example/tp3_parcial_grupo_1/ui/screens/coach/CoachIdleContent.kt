package com.example.tp3_parcial_grupo_1.ui.screens.coach

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.data.demo.LastConversation
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppIcon
import com.example.tp3_parcial_grupo_1.ui.components.AppIcons
import com.example.tp3_parcial_grupo_1.ui.components.VoraOrb
import com.example.tp3_parcial_grupo_1.ui.strings.CoachStrings
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

// Figma: Voice / 01 · Idle
@Composable
internal fun CoachIdleContent(state: CoachUiState.Idle, actions: CoachActions) {
    val spacing = MaterialTheme.spacing
    Column(modifier = Modifier.fillMaxSize()) {
        CoachHeader(title = CoachStrings.Title, onBack = actions.onBack, dark = false) {
            StatusPill(text = CoachStrings.Online, dark = false)
        }
        Column(
            modifier = Modifier
                .weight(1f)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = spacing.xl),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(Modifier.height(spacing.screen))
            VoraOrb(size = 140.dp)
            Spacer(Modifier.height(spacing.xxl))
            Text(
                text = CoachStrings.IdleHeadline,
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.onBackground,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(spacing.sm))
            Text(
                text = CoachStrings.IdleSubtitle,
                style = MaterialTheme.typography.bodyLarge,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textAlign = TextAlign.Center
            )
            Spacer(Modifier.height(spacing.xxl))
            SuggestedQuestions(questions = state.suggestions, onClick = actions.onSuggestionClick)
            state.lastConversation?.let {
                Spacer(Modifier.height(spacing.xxl))
                LastConversationCard(conversation = it, onClick = actions.onTypeInstead)
            }
            Spacer(Modifier.height(spacing.xl))
        }
        MicControl(onMicClick = actions.onMicClick, onTypeInstead = actions.onTypeInstead)
    }
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
private fun SuggestedQuestions(questions: List<String>, onClick: (String) -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
    ) {
        Text(
            text = CoachStrings.SuggestedQuestions,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.extendedColors.textSubtle
        )
        FlowRow(
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm, Alignment.CenterHorizontally),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)
        ) {
            questions.forEach { question ->
                SuggestionChip(text = question, onClick = { onClick(question) })
            }
        }
    }
}

@Composable
private fun SuggestionChip(text: String, onClick: () -> Unit) {
    Surface(
        onClick = onClick,
        shape = PillShape,
        color = MaterialTheme.colorScheme.surface,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.padding(horizontal = 14.dp, vertical = MaterialTheme.spacing.sm)
        )
    }
}

@Composable
private fun LastConversationCard(conversation: LastConversation, onClick: () -> Unit) {
    AppCard(onClick = onClick) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                text = CoachStrings.LastConversation,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.extendedColors.textSubtle,
                modifier = Modifier.weight(1f)
            )
            Text(
                text = conversation.timeAgo,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.extendedColors.textSubtle
            )
        }
        Text(
            text = CoachStrings.quoted(conversation.text),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

@Composable
private fun MicControl(onMicClick: () -> Unit, onTypeInstead: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = MaterialTheme.spacing.lg, bottom = MaterialTheme.spacing.md),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Surface(
            onClick = onMicClick,
            modifier = Modifier.size(72.dp),
            shape = CircleShape,
            color = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
            shadowElevation = 6.dp
        ) {
            AppIcon(
                AppIcons.Mic,
                contentDescription = CoachStrings.Microphone,
                modifier = Modifier.padding(22.dp)
            )
        }
        Spacer(Modifier.height(MaterialTheme.spacing.sm))
        Text(
            text = CoachStrings.TapToTalk,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.primary
        )
        TextButton(onClick = onTypeInstead) {
            Text(
                text = CoachStrings.TypeInstead,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
                textDecoration = TextDecoration.Underline
            )
        }
    }
}
