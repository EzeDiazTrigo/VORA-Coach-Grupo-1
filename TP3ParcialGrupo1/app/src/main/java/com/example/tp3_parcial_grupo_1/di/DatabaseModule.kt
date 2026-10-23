package com.example.tp3_parcial_grupo_1.di

import android.content.Context
import androidx.room.Room
import com.example.tp3_parcial_grupo_1.data.local.AppDatabase
import com.example.tp3_parcial_grupo_1.data.local.dao.ExerciseDao
import com.example.tp3_parcial_grupo_1.data.local.dao.MealDao
import com.example.tp3_parcial_grupo_1.data.local.dao.WorkoutDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
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
        ).fallbackToDestructiveMigration(dropAllTables = true).build()
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
}
