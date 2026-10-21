package com.example.tp3_parcial_grupo_1.data.remote.api

import com.example.tp3_parcial_grupo_1.data.remote.dto.WorkoutDto
import com.example.tp3_parcial_grupo_1.data.remote.dto.WorkoutSummaryDto
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface WorkoutApi {
    @GET("v1/workouts")
    suspend fun getWorkouts(): Response<List<WorkoutSummaryDto>>

    @GET("v1/workouts/{id}")
    suspend fun getWorkoutById(
        @Path("id") id: String
    ): Response<WorkoutDto>
}
