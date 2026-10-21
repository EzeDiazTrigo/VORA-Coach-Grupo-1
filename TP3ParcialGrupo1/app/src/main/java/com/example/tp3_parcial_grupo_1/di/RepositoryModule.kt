package com.example.tp3_parcial_grupo_1.di

import com.example.tp3_parcial_grupo_1.data.repository.WorkoutRepository
import com.example.tp3_parcial_grupo_1.data.repository.WorkoutRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindWorkoutRepository(
        workoutRepositoryImpl: WorkoutRepositoryImpl
    ): WorkoutRepository
}
