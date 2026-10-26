package com.example.tp3_parcial_grupo_1.ui.screens.coach

import com.example.tp3_parcial_grupo_1.data.demo.CoachMessage
import com.example.tp3_parcial_grupo_1.data.demo.LastConversation

// Un estado por frame de «AI Voice Flow» en Figma; todos viven en el mismo destino.
sealed interface CoachUiState {
    data class Idle(
        val suggestions: List<String>,
        val lastConversation: LastConversation?
    ) : CoachUiState

    data class Listening(val transcript: String) : CoachUiState

    data class Processing(val transcript: String, val step: String) : CoachUiState

    data class Reply(
        val reply: String,
        val elapsedSeconds: Int,
        val totalSeconds: Int,
        val isPaused: Boolean
    ) : CoachUiState {
        val progress: Float get() = if (totalSeconds == 0) 0f else elapsedSeconds / totalSeconds.toFloat()
        val remainingSeconds: Int get() = totalSeconds - elapsedSeconds
    }

    data class Conversation(
        val messages: List<CoachMessage>,
        val draft: String,
        val isVoraTyping: Boolean
    ) : CoachUiState {
        val canSend: Boolean get() = draft.isNotBlank() && !isVoraTyping
    }

    // «No te entendí»: no hay micrófono real, se llega al terminar de hablar sin decir nada.
    data object MicError : CoachUiState
}
