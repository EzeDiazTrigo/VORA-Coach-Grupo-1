package com.example.tp3_parcial_grupo_1.di

import com.example.tp3_parcial_grupo_1.data.local.AppDatabase
import com.example.tp3_parcial_grupo_1.data.local.dao.CoachMessageDao
import com.example.tp3_parcial_grupo_1.data.remote.api.CoachApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton
import retrofit2.Retrofit

// Dependencias del Asistente: reutiliza el Retrofit de NetworkModule y la base de DatabaseModule.
@Module
@InstallIn(SingletonComponent::class)
object CoachModule {

    @Provides
    @Singleton
    fun provideCoachApi(retrofit: Retrofit): CoachApi = retrofit.create(CoachApi::class.java)

    @Provides
    fun provideCoachMessageDao(database: AppDatabase): CoachMessageDao = database.coachMessageDao()
}
