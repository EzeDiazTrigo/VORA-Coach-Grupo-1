package com.example.tp3_parcial_grupo_1.ui.screens.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp3_parcial_grupo_1.data.repository.ProfileRepository
import com.example.tp3_parcial_grupo_1.model.CoachingStyle
import com.example.tp3_parcial_grupo_1.model.ResponseStyle
import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import javax.inject.Inject

// Los ajustes se aplican al instante: el diseño no tiene botón de guardar.
@HiltViewModel
class VoiceSettingsViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {

    private val message = MutableStateFlow<String?>(null)

    val uiState: StateFlow<VoiceSettingsUiState> = combine(
        repository.voiceSettings,
        repository.profile,
        message
    ) { settings, profile, message ->
        settings.toUiState(coachingStyle = profile.coachingStyle, message = message)
    }.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(STOP_TIMEOUT_MILLIS),
        initialValue = repository.voiceSettings.value.toUiState(repository.profile.value.coachingStyle)
    )

    fun onCoachingStyleSelected(style: CoachingStyle) = repository.update { it.copy(coachingStyle = style) }

    fun onSpeakResponsesChange(enabled: Boolean) =
        repository.updateVoiceSettings { it.copy(speakResponses = enabled) }

    fun onAutoPlayChange(enabled: Boolean) =
        repository.updateVoiceSettings { it.copy(autoPlayCoaching = enabled) }

    fun onAllowInterruptionsChange(enabled: Boolean) =
        repository.updateVoiceSettings { it.copy(allowInterruptions = enabled) }

    fun onResponseStyleSelected(style: ResponseStyle) =
        repository.updateVoiceSettings { it.copy(responseStyle = style) }

    fun onVoiceFeedbackChange(enabled: Boolean) =
        repository.updateVoiceSettings { it.copy(voiceFeedback = enabled) }

    fun onHapticFeedbackChange(enabled: Boolean) =
        repository.updateVoiceSettings { it.copy(hapticFeedback = enabled) }

    fun onPreviewClick() = message.update { Strings.DemoMode }

    fun onPrivacyClick() = message.update { Strings.DemoMode }

    fun onMessageShown() = message.update { null }

    private companion object {
        const val STOP_TIMEOUT_MILLIS = 5_000L
    }
}
