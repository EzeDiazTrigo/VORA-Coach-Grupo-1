package com.example.tp3_parcial_grupo_1.data.repository

import kotlinx.coroutines.delay
import javax.inject.Inject
import javax.inject.Singleton

// Repositorio de demostración: acepta cualquier credencial válida, sin servidor.
@Singleton
class DemoAuthRepository @Inject constructor() : AuthRepository {
    override suspend fun signIn(email: String, password: String): Result<Unit> {
        delay(DEMO_DELAY_MS)
        return Result.success(Unit)
    }

    override suspend fun signUp(fullName: String, email: String, password: String): Result<Unit> {
        delay(DEMO_DELAY_MS)
        return Result.success(Unit)
    }

    private companion object {
        const val DEMO_DELAY_MS = 600L
    }
}
