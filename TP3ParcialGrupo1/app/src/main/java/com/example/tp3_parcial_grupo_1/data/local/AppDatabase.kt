package com.example.tp3_parcial_grupo_1.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tp3_parcial_grupo_1.data.local.dao.ExerciseDao
import com.example.tp3_parcial_grupo_1.data.local.dao.WorkoutDao
import com.example.tp3_parcial_grupo_1.data.local.entity.ExerciseEntity
import com.example.tp3_parcial_grupo_1.data.local.entity.WorkoutEntity

@Database(
    entities = [WorkoutEntity::class, ExerciseEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
    abstract fun exerciseDao(): ExerciseDao
}
