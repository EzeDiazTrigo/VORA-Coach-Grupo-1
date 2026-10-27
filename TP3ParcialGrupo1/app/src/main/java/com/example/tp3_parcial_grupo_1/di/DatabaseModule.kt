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

    // Estrategia de migración: solo las versiones 1 y 2 (desarrollo, nunca publicadas) se recrean desde cero.
    // Desde la 3 en adelante un cambio de esquema exige una Migration explícita (schemas/ guarda cada versión);
    // si falta, Room falla en lugar de borrar las comidas, el historial del Asistente y los workouts guardados.
    private val UnreleasedVersions = intArrayOf(1, 2)

    @Provides
    @Singleton
    fun provideAppDatabase(
        @ApplicationContext context: Context
    ): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigrationFrom(true, *UnreleasedVersions)
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

    // La demo se siembra una sola vez por base: una marca en el encabezado de SQLite (application_id, que Room no usa)
    // evita que borrar todas las comidas las vuelva a traer. Una migración destructiva conserva el archivo,
    // así que ahí se borra la marca para sembrar de nuevo. Se siembra en onOpen y no en onDestructiveMigration
    // porque en ese momento las tablas todavía no existen.
    private const val DEMO_SEEDED_MARK = 0x564F5241

    private object DemoMealsCallback : RoomDatabase.Callback() {
        override fun onDestructiveMigration(db: SupportSQLiteDatabase) {
            db.execSQL("PRAGMA application_id = 0")
        }

        override fun onOpen(db: SupportSQLiteDatabase) {
            if (db.queryInt("PRAGMA application_id") == DEMO_SEEDED_MARK) return
            if (db.queryInt("SELECT COUNT(*) FROM meals") == 0) seedDemoMeals(db)
            db.execSQL("PRAGMA application_id = $DEMO_SEEDED_MARK")
        }

        private fun SupportSQLiteDatabase.queryInt(sql: String): Int =
            query(sql).use { cursor -> if (cursor.moveToFirst()) cursor.getInt(0) else 0 }

        private fun seedDemoMeals(db: SupportSQLiteDatabase) {
            val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
            NutritionDemoData.meals(today).map(MealEntity::fromDomain).forEach { meal ->
                db.insert(
                    "meals",
                    OnConflictStrategy.NONE,
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
