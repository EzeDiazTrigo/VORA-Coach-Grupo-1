package com.example.tp3_parcial_grupo_1.data.remote.dto

import com.example.tp3_parcial_grupo_1.data.mapper.toDomain
import com.example.tp3_parcial_grupo_1.data.mapper.toEntity
import com.example.tp3_parcial_grupo_1.model.Workout
import com.google.gson.annotations.SerializedName

data class WorkoutDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("level")
    val level: String,
    @SerializedName("durationMinutes")
    val durationMinutes: Int,
    @SerializedName("description")
    val description: String?,
    @SerializedName("exercises")
    val exercises: List<ExerciseDto>?
) {
    fun toDomain(): Workout {
        val exerciseEntities = exercises.orEmpty().map { it.toEntity(id) }
        return toEntity().toDomain(exerciseEntities)
    }
}
