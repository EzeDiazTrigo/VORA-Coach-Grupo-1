package com.example.tp3_parcial_grupo_1.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.tp3_parcial_grupo_1.data.local.entity.ExerciseEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExerciseDao {
    @Query("SELECT * FROM exercises WHERE workout_id = :workoutId")
    fun getExercisesByWorkout(workoutId: String): Flow<List<ExerciseEntity>>

    @Insert
    suspend fun insertExercises(exercises: List<ExerciseEntity>)

    @Query("DELETE FROM exercises WHERE workout_id = :workoutId")
    suspend fun deleteExercisesByWorkout(workoutId: String)
    
    @Query("SELECT * FROM exercises WHERE id = :exerciseId")
    fun getExerciseById(exerciseId: String): Flow<ExerciseEntity?>
}
