package com.example.tp3_parcial_grupo_1.data.remote.dto

import com.example.tp3_parcial_grupo_1.model.Workout

data class WorkoutDto(
    val id: String,
    val name: String,
    val level: String,
    val durationMinutes: Int,
    val exerciseCount: Int,
    val description: String? = null,
    val exercises: List<ExerciseDto> = emptyList()
)

data class WorkoutListResponseDto(
    val data: List<WorkoutDto>
)

data class WorkoutDetailResponseDto(
    val data: WorkoutDto
)

fun WorkoutDto.toDomain(): Workout =
    Workout(
        id = id,
        name = name,
        level = level,
        durationMinutes = durationMinutes,
        exerciseCount = exerciseCount,
        description = description,
        exercises = exercises.map(ExerciseDto::toDomain)
    )
