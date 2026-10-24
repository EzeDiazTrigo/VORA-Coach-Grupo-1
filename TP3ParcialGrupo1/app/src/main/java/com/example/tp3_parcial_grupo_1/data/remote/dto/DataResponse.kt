package com.example.tp3_parcial_grupo_1.data.remote.dto

import com.google.gson.annotations.SerializedName

data class DataResponse<T>(
    @SerializedName("data")
    val data: T
)
