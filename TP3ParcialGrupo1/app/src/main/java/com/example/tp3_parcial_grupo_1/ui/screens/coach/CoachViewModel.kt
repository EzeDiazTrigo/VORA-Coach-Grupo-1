package com.example.tp3_parcial_grupo_1.ui.screens.coach

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp3_parcial_grupo_1.data.demo.CoachDemoData
import com.example.tp3_parcial_grupo_1.data.demo.CoachExchange
import com.example.tp3_parcial_grupo_1.data.demo.LastConversation
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
 * Simula el recorrido de voz (escucha → procesamiento → respuesta) con demoras, sin micrófono
 * ni servicio remoto. El estado vive acá para que la rotación no reinicie la simulación.
 */
@HiltViewModel
class CoachViewModel @Inject constructor() : ViewModel() {

    private var lastConversation: LastConversation = CoachDemoData.lastConversation
    private var exchange: CoachExchange = CoachDemoData.lunch
    private var simulation: Job? = null

    private val _uiState = MutableStateFlow<CoachUiState>(idleState())
    val uiState: StateFlow<CoachUiState> = _uiState.asStateFlow()

    fun onMicClick() = startListening(CoachDemoData.lunch)

    // Una pregunta sugerida se envía como texto: salta la escucha.
    fun onSuggestionClick(question: String) {
        exchange = CoachDemoData.exchangeFor(question)
        startProcessing()
    }

    // El usuario terminó de hablar antes de que la transcripción simulada se complete.
    fun onFinishListening() {
        if (_uiState.value is CoachUiState.Listening) startProcessing()
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
            goIdle()
        }
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

    companion object {
        const val ListeningStartDelayMs = 600L
        const val WordDelayMs = 220L
        const val ListeningEndDelayMs = 900L
        const val ProcessingDelayMs = 1_800L
        const val SecondMs = 1_000L
    }
}
