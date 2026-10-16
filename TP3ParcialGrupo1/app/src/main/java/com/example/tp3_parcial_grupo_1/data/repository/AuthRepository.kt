package com.example.tp3_parcial_grupo_1.data.repository

interface AuthRepository {
    suspend fun signIn(email: String, password: String): Result<Unit>
    suspend fun signUp(fullName: String, email: String, password: String): Result<Unit>
}
