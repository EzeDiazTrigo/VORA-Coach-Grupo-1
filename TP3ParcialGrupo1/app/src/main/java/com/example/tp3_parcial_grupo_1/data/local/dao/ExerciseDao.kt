package com.example.tp3_parcial_grupo_1.data.local.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import com.example.tp3_parcial_grupo_1.data.local.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercises WHERE workout_id = :workoutId")
    fun getExercisesByWorkout(workoutId: String): Flow<List<ExerciseEntity>>

    @Upsert
    suspend fun insertExercises(exercises: List<ExerciseEntity>)

    @Query("DELETE FROM exercises WHERE workout_id = :workoutId")
    suspend fun deleteExercisesByWorkout(workoutId: String)
    
    @Query("SELECT * FROM exercises WHERE workout_id = :workoutId AND id = :exerciseId")
    fun getExerciseById(workoutId: String, exerciseId: String): Flow<ExerciseEntity?>
}
