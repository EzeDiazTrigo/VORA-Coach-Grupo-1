package com.example.tp3_parcial_grupo_1.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.tp3_parcial_grupo_1.data.local.dao.MealDao
import com.example.tp3_parcial_grupo_1.data.local.entity.MealEntity

@Database(entities = [MealEntity::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {
    abstract fun mealDao(): MealDao

    companion object {
        const val DATABASE_NAME = "vora_database"
    }
}
