package com.example.tp3_parcial_grupo_1.ui.screens.coach

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.togetherWith
import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tp3_parcial_grupo_1.R
import com.example.tp3_parcial_grupo_1.data.demo.CoachDemoData
import com.example.tp3_parcial_grupo_1.ui.components.LightSystemBarIcons
import com.example.tp3_parcial_grupo_1.ui.strings.CoachStrings
import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing
import kotlinx.coroutines.launch

// Eventos de la pantalla del Asistente; agrupados para no pasar 17 lambdas sueltas.
data class CoachActions(
    val onBack: () -> Unit = {},
    val onMicClick: () -> Unit = {},
    val onSuggestionClick: (String) -> Unit = {},
    val onTypeInstead: () -> Unit = {},
    val onFinishListening: () -> Unit = {},
    val onCancel: () -> Unit = {},
    val onTogglePause: () -> Unit = {},
    val onReplay: () -> Unit = {},
    val onStop: () -> Unit = {},
    val onInterrupt: () -> Unit = {},
    val onDraftChange: (String) -> Unit = {},
    val onSend: () -> Unit = {},
    val onRetrySend: () -> Unit = {},
    val onListenToMessage: (Long) -> Unit = {},
    val onRetry: () -> Unit = {},
    val onDismissError: () -> Unit = {},
    // Acciones sin servicio real («+», «Permitir acceso»): informan el modo demostración.
    val onDemoAction: () -> Unit = {}
)

@Composable
fun CoachRoute(
    onBack: () -> Unit,
    viewModel: CoachViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()

    BackHandler(enabled = uiState !is CoachUiState.Idle) { viewModel.onBack() }
    LightSystemBarIcons(enabled = uiState.isDark)

    val actions = remember(viewModel) {
        CoachActions(
            onBack = { if (!viewModel.onBack()) onBack() },
            onMicClick = viewModel::onMicClick,
            onSuggestionClick = viewModel::onSuggestionClick,
            onTypeInstead = viewModel::onTypeInstead,
            onFinishListening = viewModel::onFinishListening,
            onCancel = viewModel::onCancel,
            onTogglePause = viewModel::onTogglePause,
            onReplay = viewModel::onReplay,
            onStop = viewModel::onStop,
            onInterrupt = viewModel::onInterrupt,
            onDraftChange = viewModel::onDraftChange,
            onSend = viewModel::onSend,
            onRetrySend = viewModel::onRetrySend,
            onListenToMessage = viewModel::onListenToMessage,
            onRetry = viewModel::onRetry,
            onDismissError = viewModel::onDismissError,
            onDemoAction = {
                scope.launch {
                    snackbarHostState.currentSnackbarData?.dismiss()
                    snackbarHostState.showSnackbar(Strings.DemoMode)
                }
            }
        )
    }

    CoachScreen(
        uiState = uiState,
        actions = actions,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    )
}

@Composable
fun CoachScreen(
    uiState: CoachUiState,
    actions: CoachActions,
    modifier: Modifier = Modifier,
    snackbarHost: @Composable () -> Unit = {}
) {
    val background by animateColorAsState(
        targetValue = if (uiState.isDark) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.background,
        label = "coachBackground"
    )
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(background)
    ) {
        // El cambio de pantalla se anima por tipo de estado; los ticks de la respuesta no lo disparan.
        AnimatedContent(
            targetState = uiState,
            contentKey = { it::class },
            transitionSpec = { fadeIn() togetherWith fadeOut() },
            label = "coachState"
        ) { state ->
            Box(modifier = Modifier.safeDrawingPadding()) {
                when (state) {
                    is CoachUiState.Idle -> CoachIdleContent(state, actions)
                    is CoachUiState.Listening -> CoachListeningContent(state, actions)
                    is CoachUiState.Processing -> CoachProcessingContent(state, actions)
                    is CoachUiState.Reply -> CoachReplyContent(state, actions)
                    is CoachUiState.Conversation -> CoachConversationContent(state, actions)
                    CoachUiState.MicError -> CoachMicErrorContent(actions)
                }
            }
        }
        Box(
            modifier = Modifier
                .align(Alignment.BottomCenter)
                .safeDrawingPadding()
        ) { snackbarHost() }
    }
}

// Escuchando, Procesando y Respuesta usan el fondo verde oscuro de Figma.
private val CoachUiState.isDark: Boolean
    get() = this is CoachUiState.Listening || this is CoachUiState.Processing || this is CoachUiState.Reply

// Encabezado de Figma: botón circular de 32 dp con chevron, título y un indicador a la derecha.
@Composable
internal fun CoachHeader(
    title: String,
    onBack: () -> Unit,
    dark: Boolean,
    modifier: Modifier = Modifier,
    showOnlineDot: Boolean = false,
    trailing: @Composable RowScope.() -> Unit = {}
) {
    val colors = MaterialTheme.colorScheme
    val contentColor = if (dark) colors.onPrimary else colors.onSurface
    Row(
        modifier = modifier
            .fillMaxWidth()
            .height(56.dp)
            .padding(horizontal = MaterialTheme.spacing.screen),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(10.dp)
    ) {
        Surface(
            onClick = onBack,
            modifier = Modifier.size(32.dp),
            shape = CircleShape,
            color = if (dark) colors.onPrimary.copy(alpha = 0.1f) else colors.surface,
            contentColor = contentColor,
            border = BorderStroke(1.dp, if (dark) colors.onPrimary.copy(alpha = 0.1f) else colors.outlineVariant)
        ) {
            Box(contentAlignment = Alignment.Center) {
                Icon(
                    painter = painterResource(R.drawable.ic_chevron_left),
                    contentDescription = Strings.Back,
                    modifier = Modifier.size(14.dp)
                )
            }
        }
        Text(
            text = title,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Medium,
            color = contentColor
        )
        if (showOnlineDot) {
            Box(
                modifier = Modifier
                    .size(6.dp)
                    .background(colors.secondary, CircleShape)
            )
        }
        Spacer(Modifier.weight(1f))
        trailing()
    }
}

@Composable
internal fun StatusPill(text: String, dark: Boolean) {
    val colors = MaterialTheme.colorScheme
    Text(
        text = text,
        style = MaterialTheme.typography.labelSmall,
        color = if (dark) colors.secondary else colors.primary,
        modifier = Modifier
            .background(
                if (dark) colors.secondary.copy(alpha = 0.15f) else MaterialTheme.extendedColors.mintSoft,
                PillShape
            )
            .padding(horizontal = 10.dp, vertical = MaterialTheme.spacing.xxs)
    )
}

@Composable
internal fun LiveIndicator(text: String) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xs)
    ) {
        Box(
            modifier = Modifier
                .size(8.dp)
                .background(MaterialTheme.colorScheme.secondary, CircleShape)
        )
        Text(text = text, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.secondary)
    }
}

@Composable
private fun PreviewScreen(state: CoachUiState) {
    AppTheme {
        CoachScreen(uiState = state, actions = CoachActions())
    }
}

@Preview(name = "Listo", widthDp = 390, heightDp = 844)
@Composable
private fun CoachIdlePreview() = PreviewScreen(
    CoachUiState.Idle(CoachDemoData.suggestedQuestions, CoachDemoData.lastConversation)
)

@Preview(name = "Escuchando", widthDp = 390, heightDp = 844)
@Composable
private fun CoachListeningPreview() = PreviewScreen(
    CoachUiState.Listening("I had chicken, rice and some")
)

@Preview(name = "Procesando", widthDp = 390, heightDp = 844)
@Composable
private fun CoachProcessingPreview() = PreviewScreen(
    CoachUiState.Processing(CoachDemoData.lunch.question, CoachDemoData.lunch.processingStep)
)

@Preview(name = "Respuesta", widthDp = 390, heightDp = 844)
@Composable
private fun CoachReplyPreview() = PreviewScreen(
    CoachUiState.Reply(CoachDemoData.lunch.reply, elapsedSeconds = 8, totalSeconds = 22, isPaused = false)
)

@Preview(name = "Listo · pantalla chica", widthDp = 320, heightDp = 640)
@Composable
private fun CoachIdleSmallPreview() = PreviewScreen(
    CoachUiState.Idle(CoachDemoData.suggestedQuestions, CoachDemoData.lastConversation)
)

@Preview(name = "Conversación", widthDp = 390, heightDp = 844)
@Composable
private fun CoachConversationPreview() = PreviewScreen(
    CoachUiState.Conversation(
        CoachDemoData.conversation,
        draft = "",
        isVoraTyping = false,
        dateLabel = CoachDemoData.ConversationDate
    )
)

@Preview(name = "Conversación · error de envío", widthDp = 390, heightDp = 844)
@Composable
private fun CoachConversationErrorPreview() = PreviewScreen(
    CoachUiState.Conversation(
        CoachDemoData.conversation,
        draft = "",
        isVoraTyping = false,
        dateLabel = CoachDemoData.ConversationDate,
        sendError = CoachStrings.SendErrorOffline
    )
)

@Preview(name = "Error de micrófono", widthDp = 390, heightDp = 844)
@Composable
private fun CoachMicErrorPreview() = PreviewScreen(CoachUiState.MicError)
