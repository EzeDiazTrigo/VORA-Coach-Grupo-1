package com.example.tp3_parcial_grupo_1.data.remote.dto

import com.google.gson.annotations.SerializedName
import com.example.tp3_parcial_grupo_1.model.Workout

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
        val exercisesDomain = exercises.orEmpty().map(ExerciseDto::toDomain)
        return Workout(
            id = id,
            name = name,
            level = level,
            durationMinutes = durationMinutes,
            exerciseCount = exercisesDomain.size,
            description = description,
            exercises = exercisesDomain
        )
    }
}