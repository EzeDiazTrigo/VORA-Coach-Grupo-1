package com.example.tp3_parcial_grupo_1.ui.screens.coach

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.R
import com.example.tp3_parcial_grupo_1.data.demo.CoachAuthor
import com.example.tp3_parcial_grupo_1.data.demo.CoachDemoData
import com.example.tp3_parcial_grupo_1.data.demo.CoachMessage
import com.example.tp3_parcial_grupo_1.ui.components.InlineLoading
import com.example.tp3_parcial_grupo_1.ui.components.VoraOrb
import com.example.tp3_parcial_grupo_1.ui.strings.CoachStrings
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val BubbleCorner = 20.dp
private val BubbleTail = 4.dp
private val UserBubbleShape = RoundedCornerShape(BubbleCorner, BubbleCorner, BubbleTail, BubbleCorner)
private val VoraBubbleShape = RoundedCornerShape(BubbleTail, BubbleCorner, BubbleCorner, BubbleCorner)
private val BubbleMaxWidth = 280.dp
private val MiniOrbSize = 28.dp

// Figma: Voice / 05 · Conversation
@Composable
internal fun CoachConversationContent(state: CoachUiState.Conversation, actions: CoachActions) {
    val listState = rememberLazyListState()
    // Al llegar un mensaje (o el indicador de escritura) se baja hasta el final.
    LaunchedEffect(state.messages.size, state.isVoraTyping) {
        listState.animateScrollToItem(listState.layoutInfo.totalItemsCount.coerceAtLeast(1) - 1)
    }
    Column(modifier = Modifier.fillMaxSize()) {
        Surface(color = MaterialTheme.colorScheme.surface) {
            Column {
                CoachHeader(title = CoachStrings.Title, onBack = actions.onBack, dark = false, showOnlineDot = true) {
                    VoraOrb(size = MiniOrbSize, glowAlpha = 0.25f, icon = null)
                }
                HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            }
        }
        LazyColumn(
            state = listState,
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(MaterialTheme.spacing.screen),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.lg)
        ) {
            item(key = "date") {
                Text(
                    text = CoachDemoData.ConversationDate,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.extendedColors.textSubtle,
                    modifier = Modifier.fillMaxWidth().padding(bottom = MaterialTheme.spacing.xs),
                    textAlign = TextAlign.Center
                )
            }
            items(state.messages, key = { it.id }) { message ->
                when (message.author) {
                    CoachAuthor.User -> UserBubble(message)
                    CoachAuthor.Vora -> VoraBubble(message, onListen = { actions.onListenToMessage(message.id) })
                }
            }
            if (state.isVoraTyping) {
                item(key = "typing") {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        VoraOrb(size = MiniOrbSize, glowAlpha = 0.25f, icon = null)
                        Spacer(Modifier.width(10.dp))
                        InlineLoading(message = CoachStrings.VoraTyping)
                    }
                }
            }
        }
        ChatInputBar(
            draft = state.draft,
            canSend = state.canSend,
            onDraftChange = actions.onDraftChange,
            onSend = actions.onSend,
            onMicClick = actions.onMicClick,
            onAttach = actions.onDemoAction
        )
    }
}

@Composable
private fun UserBubble(message: CoachMessage) {
    val colors = MaterialTheme.colorScheme
    Box(modifier = Modifier.fillMaxWidth(), contentAlignment = Alignment.CenterEnd) {
        Column(
            modifier = Modifier
                .widthIn(max = BubbleMaxWidth)
                .background(colors.primary, UserBubbleShape)
                .padding(horizontal = MaterialTheme.spacing.lg, vertical = MaterialTheme.spacing.md)
                .semantics(mergeDescendants = true) {},
            horizontalAlignment = Alignment.End,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xs)
        ) {
            Text(
                text = message.text,
                style = MaterialTheme.typography.bodyLarge,
                color = colors.onPrimary,
                modifier = Modifier.fillMaxWidth()
            )
            Text(message.time, style = MaterialTheme.typography.bodySmall, color = colors.onPrimary.copy(alpha = 0.5f))
        }
    }
}

@Composable
private fun VoraBubble(message: CoachMessage, onListen: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    Row(verticalAlignment = Alignment.Top) {
        VoraOrb(size = MiniOrbSize, glowAlpha = 0.25f, icon = null)
        Spacer(Modifier.width(10.dp))
        Surface(
            modifier = Modifier.widthIn(max = BubbleMaxWidth),
            shape = VoraBubbleShape,
            color = colors.surface,
            border = BorderStroke(1.dp, colors.outlineVariant)
        ) {
            Column(
                modifier = Modifier.padding(MaterialTheme.spacing.lg),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
            ) {
                Text(message.text, style = MaterialTheme.typography.bodyLarge, color = colors.onSurface)
                val audio = message.audioSeconds
                if (audio != null) {
                    HorizontalDivider(color = colors.outlineVariant)
                    ListenRow(seconds = audio, onClick = onListen)
                } else {
                    Text(
                        message.time,
                        style = MaterialTheme.typography.bodySmall,
                        color = MaterialTheme.extendedColors.textSubtle
                    )
                }
            }
        }
    }
}

@Composable
private fun ListenRow(seconds: Int, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .clickable(role = Role.Button, onClick = onClick)
            .heightIn(min = 32.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
    ) {
        Box(
            modifier = Modifier
                .size(24.dp)
                .background(MaterialTheme.extendedColors.mintSoft, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.ic_play),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(12.dp)
            )
        }
        Text(
            text = CoachStrings.listenToResponse(seconds),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun ChatInputBar(
    draft: String,
    canSend: Boolean,
    onDraftChange: (String) -> Unit,
    onSend: () -> Unit,
    onMicClick: () -> Unit,
    onAttach: () -> Unit
) {
    val colors = MaterialTheme.colorScheme
    Surface(color = colors.surface) {
        Column {
            HorizontalDivider(color = colors.outlineVariant)
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.screen, vertical = MaterialTheme.spacing.md),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
            ) {
                Surface(
                    onClick = onAttach,
                    modifier = Modifier
                        .size(40.dp)
                        .semantics { contentDescription = CoachStrings.Attach },
                    shape = CircleShape,
                    color = colors.background,
                    contentColor = colors.onSurfaceVariant
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(painterResource(R.drawable.ic_plus), contentDescription = null, modifier = Modifier.size(18.dp))
                    }
                }
                Row(
                    modifier = Modifier
                        .weight(1f)
                        .heightIn(min = 44.dp)
                        .background(colors.background, PillShape)
                        .padding(start = MaterialTheme.spacing.lg),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    BasicTextField(
                        value = draft,
                        onValueChange = onDraftChange,
                        modifier = Modifier.weight(1f),
                        textStyle = MaterialTheme.typography.bodyLarge.copy(color = colors.onSurface),
                        cursorBrush = SolidColor(colors.primary),
                        maxLines = 4,
                        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Send),
                        keyboardActions = KeyboardActions(onSend = { onSend() }),
                        decorationBox = { field ->
                            if (draft.isEmpty()) {
                                Text(
                                    CoachStrings.AskPlaceholder,
                                    style = MaterialTheme.typography.bodyLarge,
                                    color = MaterialTheme.extendedColors.textSubtle
                                )
                            }
                            field()
                        }
                    )
                    IconButton(onClick = onMicClick) {
                        Icon(
                            painterResource(R.drawable.ic_mic),
                            contentDescription = CoachStrings.Talk,
                            tint = MaterialTheme.extendedColors.textSubtle,
                            modifier = Modifier.size(18.dp)
                        )
                    }
                }
                Surface(
                    onClick = onSend,
                    enabled = canSend,
                    modifier = Modifier
                        .size(40.dp)
                        .alpha(if (canSend) 1f else 0.5f)
                        .semantics { contentDescription = CoachStrings.Send },
                    shape = CircleShape,
                    color = colors.primary,
                    contentColor = colors.onPrimary
                ) {
                    Box(contentAlignment = Alignment.Center) {
                        Icon(painterResource(R.drawable.ic_send), contentDescription = null, modifier = Modifier.size(18.dp))
                    }
                }
            }
        }
    }
}
