package com.example.tp3_parcial_grupo_1.data.repository

import com.example.tp3_parcial_grupo_1.model.Workout
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    fun getWorkouts(): Flow<List<Workout>>
    fun getWorkoutById(id: String): Flow<Workout?>
    suspend fun refreshWorkouts(): Result<Unit>
    suspend fun refreshWorkoutDetails(id: String): Result<Unit>
}
