package com.example.tp3_parcial_grupo_1.data.remote.dto

import com.example.tp3_parcial_grupo_1.data.mapper.toDomain
import com.example.tp3_parcial_grupo_1.data.mapper.toEntity
import com.example.tp3_parcial_grupo_1.model.Workout
import com.google.gson.annotations.SerializedName

data class WorkoutSummaryDto(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("level")
    val level: String? = null,
    @SerializedName("durationMinutes", alternate = ["duration_minutes", "duration"])
    val durationMinutes: Int? = null,
    @SerializedName("exerciseCount", alternate = ["exercise_count", "exercises_count"])
    val exerciseCount: Int? = null,
    @SerializedName("description")
    val description: String? = null
) {
    fun toDomain(): Workout = toEntity().toDomain()
}
