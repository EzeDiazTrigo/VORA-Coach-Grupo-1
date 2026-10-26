package com.example.tp3_parcial_grupo_1.model

enum class CoachAuthor { User, Vora }

data class CoachMessage(
    val id: Long,
    val author: CoachAuthor,
    val text: String,
    val time: String,
    val audioSeconds: Int? = null
)
