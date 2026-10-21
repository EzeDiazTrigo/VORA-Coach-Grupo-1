package com.example.tp3_parcial_grupo_1.data.remote.dto

import com.example.tp3_parcial_grupo_1.model.Exercise

data class ExerciseDto(
    val id: String,
    val name: String,
    val muscleGroup: String,
    val sets: Int,
    val reps: Int,
    val restSeconds: Int,
    val instructions: String,
    val tip: String
)

fun ExerciseDto.toDomain(): Exercise =
    Exercise(
        id = id,
        name = name,
        muscleGroup = muscleGroup,
        sets = sets,
        reps = reps,
        restSeconds = restSeconds,
        instructions = instructions,
        tip = tip
    )
