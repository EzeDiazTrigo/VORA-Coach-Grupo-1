package com.example.tp3_parcial_grupo_1.data.remote.api

import com.example.tp3_parcial_grupo_1.data.remote.dto.CoachReplyDto
import com.example.tp3_parcial_grupo_1.data.remote.dto.CoachRequestDto
import com.example.tp3_parcial_grupo_1.data.remote.dto.DataResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface CoachApi {
    @POST("v1/coach/messages")
    suspend fun sendMessage(
        @Body request: CoachRequestDto
    ): Response<DataResponse<CoachReplyDto>>
}
