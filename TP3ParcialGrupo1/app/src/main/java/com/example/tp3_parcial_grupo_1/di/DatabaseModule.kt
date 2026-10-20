package com.example.tp3_parcial_grupo_1.di

import android.content.Context
import androidx.room.Room
import com.example.tp3_parcial_grupo_1.data.local.AppDatabase
import com.example.tp3_parcial_grupo_1.data.local.dao.MealDao
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
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            AppDatabase.DATABASE_NAME
        ).fallbackToDestructiveMigration(true).build()
    }

    @Provides
    @Singleton
    fun provideMealDao(database: AppDatabase): MealDao {
        return database.mealDao()
    }
}
