package com.example.tp3_parcial_grupo_1.di

import com.example.tp3_parcial_grupo_1.data.repository.AuthRepository
import com.example.tp3_parcial_grupo_1.data.repository.DemoAuthRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class AuthModule {
    @Binds
    @Singleton
    abstract fun bindAuthRepository(impl: DemoAuthRepository): AuthRepository
}
