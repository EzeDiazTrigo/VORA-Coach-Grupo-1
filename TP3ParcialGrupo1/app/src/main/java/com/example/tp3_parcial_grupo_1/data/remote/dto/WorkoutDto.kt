package com.example.tp3_parcial_grupo_1.data.remote.dto

import com.example.tp3_parcial_grupo_1.data.mapper.toDomain
import com.example.tp3_parcial_grupo_1.data.mapper.toEntity
import com.example.tp3_parcial_grupo_1.model.Workout
import com.google.gson.annotations.SerializedName

data class WorkoutDto(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("level")
    val level: String? = null,
    @SerializedName("durationMinutes", alternate = ["duration_minutes", "duration"])
    val durationMinutes: Int? = null,
    @SerializedName("description")
    val description: String? = null,
    @SerializedName("exercises")
    val exercises: List<ExerciseDto>? = null
) {
    fun toDomain(overrideId: String? = null): Workout {
        val workoutId = overrideId ?: id.orEmpty().ifEmpty { "w01" }
        val exerciseEntities = exercises.orEmpty().map { it.toEntity(workoutId) }
        return toEntity(workoutId).toDomain(exerciseEntities)
    }
}
