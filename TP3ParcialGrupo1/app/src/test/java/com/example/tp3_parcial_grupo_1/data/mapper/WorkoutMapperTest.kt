package com.example.tp3_parcial_grupo_1.data.mapper

import com.example.tp3_parcial_grupo_1.data.local.entity.ExerciseEntity
import com.example.tp3_parcial_grupo_1.data.local.entity.WorkoutEntity
import com.example.tp3_parcial_grupo_1.data.remote.dto.ExerciseDto
import com.example.tp3_parcial_grupo_1.data.remote.dto.WorkoutDto
import com.example.tp3_parcial_grupo_1.data.remote.dto.WorkoutSummaryDto
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class WorkoutMapperTest {

    @Test
    fun `WorkoutSummaryDto toEntity maps correctly`() {
        val dto = WorkoutSummaryDto(
            id = "w01",
            name = "Fuerza Total",
            level = "Intermedio",
            durationMinutes = 45,
            exerciseCount = 5,
            description = "Rutina completa"
        )

        val entity = dto.toEntity()!!

        assertEquals("w01", entity.id)
        assertEquals("Fuerza Total", entity.name)
        assertEquals("Intermedio", entity.level)
        assertEquals(45, entity.durationMinutes)
        assertEquals(5, entity.exerciseCount)
        assertEquals("Rutina completa", entity.description)
    }

    @Test
    fun `WorkoutDto toEntity maps correctly with exercises`() {
        val exerciseDto = ExerciseDto(
            id = "e01",
            name = "Sentadilla",
            muscleGroup = "Piernas",
            sets = 4,
            reps = 10,
            restSeconds = 90,
            instructions = "Espalda recta",
            tip = "Empuja con los talones"
        )
        val workoutDto = WorkoutDto(
            id = "w01",
            name = "Leg Day",
            level = "Avanzado",
            durationMinutes = 60,
            description = "Entrenamiento intenso",
            exercises = listOf(exerciseDto)
        )

        val workoutEntity = workoutDto.toEntity()!!

        assertEquals("w01", workoutEntity.id)
        assertEquals(1, workoutEntity.exerciseCount)

        val exerciseEntity = exerciseDto.toEntity("w01")!!
        assertEquals("e01", exerciseEntity.id)
        assertEquals("w01", exerciseEntity.workoutId)
        assertEquals("Sentadilla", exerciseEntity.name)
    }

    @Test
    fun `WorkoutEntity and ExerciseEntity toDomain maps correctly`() {
        val workoutEntity = WorkoutEntity(
            id = "w01",
            name = "Full Body",
            level = "Principiante",
            durationMinutes = 30,
            exerciseCount = 1,
            description = null
        )
        val exerciseEntity = ExerciseEntity(
            id = "e01",
            workoutId = "w01",
            name = "Flexiones",
            sets = 3,
            reps = 12,
            muscleGroup = "Pecho",
            restSeconds = 60,
            tip = "Mantén el core firme",
            instructions = "Baja hasta rozar el suelo"
        )

        val domainWorkout = workoutEntity.toDomain(listOf(exerciseEntity))

        assertEquals("w01", domainWorkout.id)
        assertEquals("Full Body", domainWorkout.name)
        assertNull(domainWorkout.description)
        assertEquals(1, domainWorkout.exercises.size)
        assertEquals("Flexiones", domainWorkout.exercises.first().name)
    }
}
