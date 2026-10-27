package com.example.tp3_parcial_grupo_1.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tp3_parcial_grupo_1.data.local.dao.CoachMessageDao
import com.example.tp3_parcial_grupo_1.data.local.dao.ExerciseDao
import com.example.tp3_parcial_grupo_1.data.local.dao.MealDao
import com.example.tp3_parcial_grupo_1.data.local.dao.WorkoutDao
import com.example.tp3_parcial_grupo_1.data.local.entity.CoachMessageEntity
import com.example.tp3_parcial_grupo_1.data.local.entity.ExerciseEntity
import com.example.tp3_parcial_grupo_1.data.local.entity.MealEntity
import com.example.tp3_parcial_grupo_1.data.local.entity.WorkoutEntity

@Database(
    entities = [
        WorkoutEntity::class,
        ExerciseEntity::class,
        MealEntity::class,
        CoachMessageEntity::class
    ],
    // v3: tabla coach_messages (historial del Asistente). Para subir de versión hay que escribir una Migration
    // y dejar el esquema exportado en app/schemas (ver la estrategia en DatabaseModule).
    version = 3,
    exportSchema = true
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun workoutDao(): WorkoutDao
    abstract fun exerciseDao(): ExerciseDao
    abstract fun mealDao(): MealDao
    abstract fun coachMessageDao(): CoachMessageDao

    companion object {
        const val DATABASE_NAME = "vora_database"
    }
}
