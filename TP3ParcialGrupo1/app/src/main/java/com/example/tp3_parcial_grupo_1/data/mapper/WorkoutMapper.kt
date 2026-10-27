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
        id = id.orEmpty().ifEmpty { "w01" },
        name = name.orEmpty().ifEmpty { "Workout" },
        level = level.orEmpty().ifEmpty { "MODERATE" },
        durationMinutes = durationMinutes ?: 45,
        exerciseCount = exerciseCount ?: 5,
        description = description
    )

fun WorkoutDto.toEntity(overrideId: String? = null): WorkoutEntity {
    val workoutId = overrideId ?: id.orEmpty().ifEmpty { "w01" }
    val exerciseList = exercises.orEmpty()
    return WorkoutEntity(
        id = workoutId,
        name = name.orEmpty().ifEmpty { "Workout" },
        level = level.orEmpty().ifEmpty { "MODERATE" },
        durationMinutes = durationMinutes ?: 45,
        exerciseCount = if (exerciseList.isNotEmpty()) exerciseList.size else 5,
        description = description
    )
}

fun ExerciseDto.toEntity(workoutId: String): ExerciseEntity =
    ExerciseEntity(
        id = id.orEmpty().ifEmpty { java.util.UUID.randomUUID().toString() },
        workoutId = workoutId,
        name = name.orEmpty().ifEmpty { "Exercise" },
        sets = sets ?: 3,
        reps = reps ?: 10,
        muscleGroup = muscleGroup.orEmpty().ifEmpty { "Full Body" },
        restSeconds = restSeconds ?: 60,
        tip = tip.orEmpty(),
        instructions = instructions.orEmpty()
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
