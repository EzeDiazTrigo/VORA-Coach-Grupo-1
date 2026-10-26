package com.example.tp3_parcial_grupo_1.model

enum class ResponseStyle { Concise, Balanced, Detailed }

data class VoiceSettings(
    val voiceName: String,
    val speakResponses: Boolean,
    val autoPlayCoaching: Boolean,
    val allowInterruptions: Boolean,
    val responseStyle: ResponseStyle,
    val voiceFeedback: Boolean,
    val hapticFeedback: Boolean
)
