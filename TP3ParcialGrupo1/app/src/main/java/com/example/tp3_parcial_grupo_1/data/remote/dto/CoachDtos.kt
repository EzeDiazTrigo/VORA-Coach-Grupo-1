package com.example.tp3_parcial_grupo_1.data.remote.dto

import com.google.gson.annotations.SerializedName

// Contrato de POST /v1/coach/messages (mock/openapi.json: CoachRequest y CoachReply).
data class CoachRequestDto(
    @SerializedName("text")
    val text: String,
    @SerializedName("context")
    val context: CoachContextDto? = null
)

data class CoachContextDto(
    @SerializedName("coachingStyle")
    val coachingStyle: String
)

data class CoachReplyDto(
    @SerializedName("role")
    val role: String?,
    @SerializedName("text")
    val text: String?,
    @SerializedName("topic")
    val topic: String?,
    @SerializedName("isSimulated")
    val isSimulated: Boolean?
)
