package com.example.tp3_parcial_grupo_1.ui.screens.profile

import com.example.tp3_parcial_grupo_1.model.CoachingStyle
import com.example.tp3_parcial_grupo_1.model.ResponseStyle
import com.example.tp3_parcial_grupo_1.model.VoiceSettings

data class VoiceSettingsUiState(
    val coachingStyle: CoachingStyle,
    val voiceName: String,
    val speakResponses: Boolean,
    val autoPlayCoaching: Boolean,
    val allowInterruptions: Boolean,
    val responseStyle: ResponseStyle,
    val voiceFeedback: Boolean,
    val hapticFeedback: Boolean,
    val message: String? = null
)

fun VoiceSettings.toUiState(coachingStyle: CoachingStyle, message: String? = null) = VoiceSettingsUiState(
    coachingStyle = coachingStyle,
    voiceName = voiceName,
    speakResponses = speakResponses,
    autoPlayCoaching = autoPlayCoaching,
    allowInterruptions = allowInterruptions,
    responseStyle = responseStyle,
    voiceFeedback = voiceFeedback,
    hapticFeedback = hapticFeedback,
    message = message
)
