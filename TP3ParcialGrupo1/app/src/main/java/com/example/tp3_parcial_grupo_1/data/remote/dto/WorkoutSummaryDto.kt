package com.example.tp3_parcial_grupo_1.data.remote.dto

import com.example.tp3_parcial_grupo_1.model.Workout
import com.google.gson.annotations.SerializedName

data class WorkoutSummaryDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("name")
    val name: String,
    @SerializedName("level")
    val level: String,
    @SerializedName("durationMinutes")
    val durationMinutes: Int,
    @SerializedName("exerciseCount")
    val exerciseCount: Int,
    @SerializedName("description")
    val description: String?
) {
    fun toDomain(): Workout =
        Workout(
            id = id,
            name = name,
            level = level,
            durationMinutes = durationMinutes,
            exerciseCount = exerciseCount,
            description = description
        )
}
