package com.example.tp3_parcial_grupo_1.data.mapper

import com.example.tp3_parcial_grupo_1.data.local.entity.ExerciseEntity
import com.example.tp3_parcial_grupo_1.data.local.entity.WorkoutEntity
import com.example.tp3_parcial_grupo_1.data.remote.dto.ExerciseDto
import com.example.tp3_parcial_grupo_1.data.remote.dto.WorkoutDto
import com.example.tp3_parcial_grupo_1.data.remote.dto.WorkoutSummaryDto
import com.example.tp3_parcial_grupo_1.model.Exercise
import com.example.tp3_parcial_grupo_1.model.Workout

// DTO -> Entity
fun WorkoutSummaryDto.toEntity(): WorkoutEntity =
    WorkoutEntity(
        id = id,
        name = name,
        level = level,
        durationMinutes = durationMinutes,
        exerciseCount = exerciseCount,
        description = description
    )

fun WorkoutDto.toEntity(): WorkoutEntity {
    val exerciseList = exercises.orEmpty()
    return WorkoutEntity(
        id = id,
        name = name,
        level = level,
        durationMinutes = durationMinutes,
        exerciseCount = exerciseList.size,
        description = description
    )
}

fun ExerciseDto.toEntity(workoutId: String): ExerciseEntity =
    ExerciseEntity(
        id = id,
        workoutId = workoutId,
        name = name,
        sets = sets,
        reps = reps,
        muscleGroup = muscleGroup,
        restSeconds = restSeconds,
        tip = tip,
        instructions = instructions
    )

// Entity -> Domain
fun WorkoutEntity.toDomain(exercises: List<ExerciseEntity> = emptyList()): Workout =
    Workout(
        id = id,
        name = name,
        level = level,
        durationMinutes = durationMinutes,
        exerciseCount = if (exercises.isNotEmpty()) exercises.size else exerciseCount,
        description = description,
        exercises = exercises.map { it.toDomain() }
    )

fun ExerciseEntity.toDomain(): Exercise =
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

// Domain -> Entity
fun Workout.toEntity(): WorkoutEntity =
    WorkoutEntity(
        id = id,
        name = name,
        level = level,
        durationMinutes = durationMinutes,
        exerciseCount = exercises.size.takeIf { it > 0 } ?: exerciseCount,
        description = description
    )

fun Exercise.toEntity(workoutId: String): ExerciseEntity =
    ExerciseEntity(
        id = id,
        workoutId = workoutId,
        name = name,
        sets = sets,
        reps = reps,
        muscleGroup = muscleGroup,
        restSeconds = restSeconds,
        tip = tip,
        instructions = instructions
    )
