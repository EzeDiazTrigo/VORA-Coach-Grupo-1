package com.example.tp3_parcial_grupo_1.ui.screens.coach

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp3_parcial_grupo_1.data.demo.CoachAuthor
import com.example.tp3_parcial_grupo_1.data.demo.CoachDemoData
import com.example.tp3_parcial_grupo_1.data.demo.CoachExchange
import com.example.tp3_parcial_grupo_1.data.demo.CoachMessage
import com.example.tp3_parcial_grupo_1.data.demo.LastConversation
import com.example.tp3_parcial_grupo_1.ui.strings.CoachStrings
import dagger.hilt.android.lifecycle.HiltViewModel
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

/**
 * Simula el recorrido de voz (escucha → procesamiento → respuesta → conversación) con demoras,
 * sin micrófono ni servicio remoto. El estado vive acá para que la rotación no reinicie la simulación.
 */
@HiltViewModel
class CoachViewModel @Inject constructor() : ViewModel() {

    private var lastConversation: LastConversation = CoachDemoData.lastConversation
    private var exchange: CoachExchange = CoachDemoData.lunch
    private var messages: List<CoachMessage> = CoachDemoData.conversation
    private var nextMessageId = messages.maxOf { it.id } + 1
    private var draft = ""
    private var simulation: Job? = null

    // Una respuesta nueva se agrega al historial al terminar; una escuchada desde el chat, no.
    private var appendReplyToConversation = true

    private val _uiState = MutableStateFlow<CoachUiState>(idleState())
    val uiState: StateFlow<CoachUiState> = _uiState.asStateFlow()

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
        val conversation = _uiState.value as? CoachUiState.Conversation ?: return
        _uiState.value = conversation.copy(draft = text)
    }

    fun onSend() {
        val conversation = _uiState.value as? CoachUiState.Conversation ?: return
        if (!conversation.canSend) return
        val question = conversation.draft.trim()
        draft = ""
        messages = messages + message(CoachAuthor.User, question)
        lastConversation = LastConversation(text = question, timeAgo = CoachStrings.JustNow)
        simulation?.cancel()
        simulation = viewModelScope.launch {
            _uiState.value = CoachUiState.Conversation(messages, draft = "", isVoraTyping = true)
            delay(TypingDelayMs)
            messages = messages + message(CoachAuthor.Vora, CoachDemoData.replyFor(question), ReplyAudioSeconds)
            showConversation()
        }
    }

    // «Escuchar respuesta» de un mensaje de VORA: reproduce ese mensaje sin duplicarlo en el chat.
    fun onListenToMessage(messageId: Long) {
        val vora = messages.firstOrNull { it.id == messageId && it.author == CoachAuthor.Vora } ?: return
        exchange = exchange.copy(reply = vora.text, replySeconds = vora.audioSeconds ?: ReplyAudioSeconds)
        appendReplyToConversation = false
        playReply(fromSecond = 0)
    }

    /** Atrás del sistema: vuelve a Listo. Devuelve false si ya estaba en Listo (sale de la pantalla). */
    fun onBack(): Boolean {
        if (_uiState.value is CoachUiState.Idle) return false
        goIdle()
        return true
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
            appendReplyToConversation = true
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
            if (appendReplyToConversation) {
                messages = messages +
                    message(CoachAuthor.User, exchange.question) +
                    message(CoachAuthor.Vora, exchange.reply, exchange.replySeconds)
                appendReplyToConversation = false
            }
            showConversation()
        }
    }

    private fun showConversation() {
        simulation?.cancel()
        simulation = null
        _uiState.value = CoachUiState.Conversation(messages, draft, isVoraTyping = false)
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

    private fun message(author: CoachAuthor, text: String, audioSeconds: Int? = null) = CoachMessage(
        id = nextMessageId++,
        author = author,
        text = text,
        time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date()),
        audioSeconds = audioSeconds
    )

    companion object {
        const val ListeningStartDelayMs = 600L
        const val WordDelayMs = 220L
        const val ListeningEndDelayMs = 900L
        const val ProcessingDelayMs = 1_800L
        const val SecondMs = 1_000L
        const val TypingDelayMs = 1_200L
        const val ReplyAudioSeconds = 12
    }
}
