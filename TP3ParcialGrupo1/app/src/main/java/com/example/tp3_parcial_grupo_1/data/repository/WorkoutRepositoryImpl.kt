package com.example.tp3_parcial_grupo_1.data.repository

import com.example.tp3_parcial_grupo_1.data.local.dao.ExerciseDao
import com.example.tp3_parcial_grupo_1.data.local.dao.WorkoutDao
import com.example.tp3_parcial_grupo_1.data.mapper.toDomain
import com.example.tp3_parcial_grupo_1.data.mapper.toEntity
import com.example.tp3_parcial_grupo_1.data.remote.api.WorkoutApi
import com.example.tp3_parcial_grupo_1.model.Workout
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkoutRepositoryImpl @Inject constructor(
    private val workoutDao: WorkoutDao,
    private val exerciseDao: ExerciseDao,
    private val workoutApi: WorkoutApi
) : WorkoutRepository {

    override fun getWorkouts(): Flow<List<Workout>> {
        return workoutDao.getAllWorkouts().map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override fun getWorkoutById(id: String): Flow<Workout?> {
        return workoutDao.getWorkoutById(id).combine(exerciseDao.getExercisesByWorkout(id)) { workoutEntity, exerciseEntities ->
            workoutEntity?.toDomain(exerciseEntities)
        }
    }

    override suspend fun refreshWorkouts(): Result<Unit> {
        return runCatching {
            val response = workoutApi.getWorkouts()
            if (response.isSuccessful) {
                val summaries = response.body().orEmpty()
                summaries.forEach { summaryDto ->
                    workoutDao.insert(summaryDto.toEntity())
                }
            } else {
                throw Exception("HTTP Error: ${response.code()} ${response.message()}")
            }
        }
    }

    override suspend fun refreshWorkoutDetails(id: String): Result<Unit> {
        return runCatching {
            val response = workoutApi.getWorkoutById(id)
            if (response.isSuccessful) {
                val workoutDto = response.body() ?: throw Exception("Empty response body")
                workoutDao.insert(workoutDto.toEntity())
                val exerciseEntities = workoutDto.exercises.orEmpty().map { it.toEntity(id) }
                if (exerciseEntities.isNotEmpty()) {
                    exerciseDao.insertExercises(exerciseEntities)
                }
            } else {
                throw Exception("HTTP Error: ${response.code()} ${response.message()}")
            }
        }
    }
}
