package com.example.tp3_parcial_grupo_1.data.remote.dto

import com.example.tp3_parcial_grupo_1.model.Exercise
import com.google.gson.annotations.SerializedName

data class ExerciseDto(
    @SerializedName("id")
    val id: String? = null,
    @SerializedName("name")
    val name: String? = null,
    @SerializedName("muscleGroup", alternate = ["muscle_group", "muscle"])
    val muscleGroup: String? = null,
    @SerializedName("sets")
    val sets: Int? = null,
    @SerializedName("reps")
    val reps: Int? = null,
    @SerializedName("restSeconds", alternate = ["rest_seconds", "rest"])
    val restSeconds: Int? = null,
    @SerializedName("instructions")
    val instructions: String? = null,
    @SerializedName("tip")
    val tip: String? = null
)

fun ExerciseDto.toDomain(): Exercise =
    Exercise(
        id = id.orEmpty(),
        name = name.orEmpty(),
        muscleGroup = muscleGroup.orEmpty(),
        sets = sets ?: 3,
        reps = reps ?: 10,
        restSeconds = restSeconds ?: 60,
        instructions = instructions.orEmpty(),
        tip = tip.orEmpty()
    )
