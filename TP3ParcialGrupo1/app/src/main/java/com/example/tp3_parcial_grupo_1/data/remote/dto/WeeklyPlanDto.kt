package com.example.tp3_parcial_grupo_1.data.remote.dto

import com.google.gson.annotations.SerializedName

data class WeeklyPlanDto(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("days")
    val days: List<WeeklyPlanDayDto>? = null
)

data class WeeklyPlanDayDto(
    @SerializedName("dayName", alternate = ["day_name", "day"])
    val dayName: String? = null,
    @SerializedName("dayNumber", alternate = ["day_number", "date"])
    val dayNumber: String? = null,
    @SerializedName("workoutId", alternate = ["workout_id"])
    val workoutId: String? = null,
    @SerializedName("workoutName", alternate = ["workout_name", "title"])
    val workoutName: String? = null,
    @SerializedName("status")
    val status: String? = null,
    @SerializedName("workout")
    val workout: WorkoutSummaryDto? = null
)
