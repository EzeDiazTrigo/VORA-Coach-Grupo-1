package com.example.tp3_parcial_grupo_1.ui.screens.coach

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp3_parcial_grupo_1.data.demo.CoachDemoData
import com.example.tp3_parcial_grupo_1.data.demo.CoachExchange
import com.example.tp3_parcial_grupo_1.data.demo.LastConversation
import com.example.tp3_parcial_grupo_1.data.repository.CoachMalformedReplyException
import com.example.tp3_parcial_grupo_1.data.repository.CoachRepository
import com.example.tp3_parcial_grupo_1.data.repository.CoachServiceException
import com.example.tp3_parcial_grupo_1.model.CoachAuthor
import com.example.tp3_parcial_grupo_1.model.CoachMessage
import com.example.tp3_parcial_grupo_1.ui.strings.CoachStrings
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Recorrido del Asistente. La voz (escucha → procesamiento → respuesta) sigue simulada con demoras;
 * el chat escrito usa [CoachRepository]: historial en Room y respuestas de POST /v1/coach/messages.
 * El estado vive acá para que la rotación no reinicie la simulación ni repita pedidos.
 */
@HiltViewModel
class CoachViewModel @Inject constructor(
    private val repository: CoachRepository
) : ViewModel() {

    private var lastConversation: LastConversation = CoachDemoData.lastConversation
    private var exchange: CoachExchange = CoachDemoData.lunch
    private var history: List<CoachMessage> = emptyList()
    private var draft = ""
    private var isVoraTyping = false
    private var sendError: String? = null
    private var pendingQuestion: String? = null
    private var simulation: Job? = null

    // Una respuesta por voz nueva se guarda en el historial al terminar; una escuchada desde el chat, no.
    private var saveReplyToHistory = true

    private val _uiState = MutableStateFlow<CoachUiState>(idleState())
    val uiState: StateFlow<CoachUiState> = _uiState.asStateFlow()

    init {
        viewModelScope.launch {
            repository.observeMessages().collect { messages ->
                history = messages
                refreshConversation()
            }
        }
    }

    fun onMicClick() = startListening(CoachDemoData.lunch)

    // Una pregunta sugerida se envía como texto: salta la escucha.
    fun onSuggestionClick(question: String) {
        exchange = CoachDemoData.exchangeFor(question).copy(question = question)
        startProcessing()
    }

    // El usuario terminó de hablar: sin palabras transcriptas, VORA no entendió.
    fun onFinishListening() {
        val listening = _uiState.value as? CoachUiState.Listening ?: return
        if (listening.transcript.isBlank()) showMicError() else startProcessing()
    }

    fun onInterrupt() = startListening(exchange)

    fun onTogglePause() {
        val reply = _uiState.value as? CoachUiState.Reply ?: return
        if (reply.isPaused) {
            playReply(reply.elapsedSeconds)
        } else {
            simulation?.cancel()
            _uiState.value = reply.copy(isPaused = true)
        }
    }

    fun onReplay() {
        if (_uiState.value is CoachUiState.Reply) playReply(fromSecond = 0)
    }

    fun onCancel() = goIdle()

    fun onStop() = goIdle()

    fun onTypeInstead() = showConversation()

    fun onRetry() = startListening(CoachDemoData.lunch)

    fun onDismissError() = goIdle()

    fun onDraftChange(text: String) {
        draft = text
        refreshConversation()
    }

    fun onSend() {
        val conversation = _uiState.value as? CoachUiState.Conversation ?: return
        if (!conversation.canSend) return
        val question = conversation.draft.trim()
        draft = ""
        lastConversation = LastConversation(text = question, timeAgo = CoachStrings.JustNow)
        viewModelScope.launch {
            repository.addUserMessage(question)
            requestReply(question)
        }
    }

    // Reintenta la última pregunta cuyo pedido falló, sin volver a guardarla.
    fun onRetrySend() {
        val question = pendingQuestion ?: return
        if (isVoraTyping) return
        viewModelScope.launch { requestReply(question) }
    }

    // «Escuchar respuesta» de un mensaje de VORA: reproduce ese mensaje sin duplicarlo en el chat.
    fun onListenToMessage(messageId: Long) {
        val vora = visibleMessages().firstOrNull { it.id == messageId && it.author == CoachAuthor.Vora } ?: return
        exchange = exchange.copy(reply = vora.text, replySeconds = vora.audioSeconds ?: DefaultAudioSeconds)
        saveReplyToHistory = false
        playReply(fromSecond = 0)
    }

    /** Atrás del sistema: vuelve a Listo. Devuelve false si ya estaba en Listo (sale de la pantalla). */
    fun onBack(): Boolean {
        if (_uiState.value is CoachUiState.Idle) return false
        goIdle()
        return true
    }

    private suspend fun requestReply(question: String) {
        isVoraTyping = true
        sendError = null
        pendingQuestion = question
        refreshConversation()
        repository.requestReply(question)
            .onSuccess { pendingQuestion = null }
            .onFailure { sendError = it.toUserMessage() }
        isVoraTyping = false
        refreshConversation()
    }

    // Si el usuario salió del chat mientras VORA respondía, no se lo trae de vuelta.
    private fun refreshConversation() {
        if (_uiState.value is CoachUiState.Conversation) publishConversation()
    }

    private fun startListening(next: CoachExchange) {
        exchange = next
        simulation?.cancel()
        simulation = viewModelScope.launch {
            val words = next.question.split(" ")
            _uiState.value = CoachUiState.Listening(transcript = "")
            delay(ListeningStartDelayMs)
            for (count in 1..words.size) {
                _uiState.value = CoachUiState.Listening(transcript = words.take(count).joinToString(" "))
                delay(WordDelayMs)
            }
            delay(ListeningEndDelayMs)
            startProcessing()
        }
    }

    private fun startProcessing() {
        simulation?.cancel()
        simulation = viewModelScope.launch {
            _uiState.value = CoachUiState.Processing(transcript = exchange.question, step = exchange.processingStep)
            delay(ProcessingDelayMs)
            lastConversation = LastConversation(text = exchange.question, timeAgo = CoachStrings.JustNow)
            saveReplyToHistory = true
            playReply(fromSecond = 0)
        }
    }

    private fun playReply(fromSecond: Int) {
        simulation?.cancel()
        simulation = viewModelScope.launch {
            val total = exchange.replySeconds
            for (second in fromSecond..total) {
                _uiState.value = CoachUiState.Reply(
                    reply = exchange.reply,
                    elapsedSeconds = second,
                    totalSeconds = total,
                    isPaused = false
                )
                delay(SecondMs)
            }
            if (saveReplyToHistory) {
                saveReplyToHistory = false
                repository.saveExchange(exchange.question, exchange.reply, exchange.replySeconds)
            }
            showConversation()
        }
    }

    // Sin historial guardado se muestra la conversación de ejemplo del frame de Figma.
    private fun visibleMessages(): List<CoachMessage> = history.ifEmpty { CoachDemoData.conversation }

    private fun publishConversation() {
        _uiState.value = CoachUiState.Conversation(
            messages = visibleMessages(),
            draft = draft,
            isVoraTyping = isVoraTyping,
            sendError = sendError
        )
    }

    private fun showConversation() {
        simulation?.cancel()
        simulation = null
        publishConversation()
    }

    private fun showMicError() {
        simulation?.cancel()
        simulation = null
        _uiState.value = CoachUiState.MicError
    }

    private fun goIdle() {
        simulation?.cancel()
        simulation = null
        _uiState.value = idleState()
    }

    private fun idleState() = CoachUiState.Idle(
        suggestions = CoachDemoData.suggestedQuestions,
        lastConversation = lastConversation
    )

    private fun Throwable.toUserMessage(): String = when (this) {
        is CoachServiceException -> CoachStrings.sendErrorServer(code)
        is CoachMalformedReplyException -> CoachStrings.SendErrorMalformed
        else -> CoachStrings.SendErrorOffline
    }

    companion object {
        const val ListeningStartDelayMs = 600L
        const val WordDelayMs = 220L
        const val ListeningEndDelayMs = 900L
        const val ProcessingDelayMs = 1_800L
        const val SecondMs = 1_000L
        const val DefaultAudioSeconds = 12
    }
}
