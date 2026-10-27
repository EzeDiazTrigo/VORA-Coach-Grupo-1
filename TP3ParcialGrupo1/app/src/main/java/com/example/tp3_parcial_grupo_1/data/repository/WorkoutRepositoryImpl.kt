package com.example.tp3_parcial_grupo_1.data.repository

import androidx.room.withTransaction
import com.example.tp3_parcial_grupo_1.data.local.AppDatabase
import com.example.tp3_parcial_grupo_1.data.local.dao.ExerciseDao
import com.example.tp3_parcial_grupo_1.data.local.dao.WorkoutDao
import com.example.tp3_parcial_grupo_1.data.mapper.toDomain
import com.example.tp3_parcial_grupo_1.data.mapper.toEntity
import com.example.tp3_parcial_grupo_1.data.remote.api.WorkoutApi
import com.example.tp3_parcial_grupo_1.model.Workout
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkoutRepositoryImpl @Inject constructor(
    private val workoutDao: WorkoutDao,
    private val exerciseDao: ExerciseDao,
    private val workoutApi: WorkoutApi,
    private val database: AppDatabase? = null
) : WorkoutRepository {

    private suspend fun <R> runInTransaction(block: suspend () -> R): R {
        val db = database
        return if (db != null) {
            db.withTransaction(block)
        } else {
            block()
        }
    }

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
        return try {
            val response = workoutApi.getWorkouts()
            if (response.isSuccessful) {
                val summaries = response.body()?.data.orEmpty()
                val validEntities = summaries.mapNotNull { it.toEntity() }
                val serverIds = validEntities.map { it.id }.toSet()
                runInTransaction {
                    val currentEntities = workoutDao.getAllWorkouts().first()
                    currentEntities.filter { it.id !in serverIds }.forEach {
                        workoutDao.delete(it.id)
                    }
                    validEntities.forEach { workoutEntity ->
                        workoutDao.insert(workoutEntity)
                    }
                }
                Result.success(Unit)
            } else {
                Result.failure(Exception("HTTP Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Result.failure(e)
        }
    }

    override suspend fun refreshWorkoutDetails(id: String): Result<Unit> {
        return try {
            val response = workoutApi.getWorkoutById(id)
            if (response.isSuccessful) {
                val workoutDto = response.body()?.data ?: throw Exception("Empty response body")
                val workoutEntity = workoutDto.toEntity(overrideId = id) ?: throw Exception("Invalid workout data")
                runInTransaction {
                    workoutDao.insert(workoutEntity)
                    exerciseDao.deleteExercisesByWorkout(id)
                    val exerciseEntities = workoutDto.exercises.orEmpty().mapIndexedNotNull { idx, dto -> dto.toEntity(id, idx) }
                    if (exerciseEntities.isNotEmpty()) {
                        exerciseDao.insertExercises(exerciseEntities)
                    }
                }
                Result.success(Unit)
            } else {
                Result.failure(Exception("HTTP Error: ${response.code()} ${response.message()}"))
            }
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Result.failure(e)
        }
    }
}
