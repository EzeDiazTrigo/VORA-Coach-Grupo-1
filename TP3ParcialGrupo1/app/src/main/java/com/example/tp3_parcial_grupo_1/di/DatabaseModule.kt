package com.example.tp3_parcial_grupo_1.di

import android.content.Context
import androidx.core.content.contentValuesOf
import androidx.room.OnConflictStrategy
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.tp3_parcial_grupo_1.data.demo.NutritionDemoData
import com.example.tp3_parcial_grupo_1.data.local.AppDatabase
import com.example.tp3_parcial_grupo_1.data.local.dao.ExerciseDao
import com.example.tp3_parcial_grupo_1.data.local.dao.MealDao
import com.example.tp3_parcial_grupo_1.data.local.dao.WorkoutDao
import com.example.tp3_parcial_grupo_1.data.local.entity.MealEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration(dropAllTables = true)
            .addCallback(DemoMealsCallback)
            .build()
    }

    @Provides
    fun provideWorkoutDao(database: AppDatabase): WorkoutDao {
        return database.workoutDao()
    }

    @Provides
    fun provideExerciseDao(database: AppDatabase): ExerciseDao {
        return database.exerciseDao()
    }

    @Provides
    @Singleton
    fun provideMealDao(database: AppDatabase): MealDao {
        return database.mealDao()
    }

    // onCreate corre solo cuando se crea el archivo de la base: borrar comidas no vuelve a sembrar la demo.
    // Una versión nueva con migración destructiva recrea la base sin pasar por onCreate, así que también se siembra ahí.
    private object DemoMealsCallback : RoomDatabase.Callback() {
        override fun onCreate(db: SupportSQLiteDatabase) {
            seedDemoMeals(db)
        }

        override fun onOpen(db: SupportSQLiteDatabase) {
            super.onOpen(db)
            try {
                db.query("SELECT COUNT(*) FROM meals").use { cursor ->
                    if (cursor.moveToFirst() && cursor.getInt(0) == 0) {
                        seedDemoMeals(db)
                    }
                }
            } catch (_: Exception) {
                // Table might not exist yet if opened before table creation
            }
        }

        private fun seedDemoMeals(db: SupportSQLiteDatabase) {
            val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            NutritionDemoData.meals(today).map(MealEntity::fromDomain).forEach { meal ->
                db.insert(
                    "meals",
                    OnConflictStrategy.IGNORE,
                    contentValuesOf(
                        "name" to meal.name,
                        "date" to meal.date,
                        "type" to meal.type,
                        "calories" to meal.calories,
                        "protein" to meal.protein,
                        "carbs" to meal.carbs,
                        "fat" to meal.fat,
                        "itemsSummary" to meal.itemsSummary
                    )
                )
            }
        }
    }
}
