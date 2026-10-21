package com.example.tp3_parcial_grupo_1.model

data class Workout(
    val id: String,
    val name: String,
    val level: String,
    val durationMinutes: Int,
    val exerciseCount: Int,
    val description: String? = null,
    val exercises: List<Exercise> = emptyList()
)
