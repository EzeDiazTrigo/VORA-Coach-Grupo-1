package com.example.tp3_parcial_grupo_1.model

data class Meal(
    val id: Long = 0L,
    val name: String,
    val date: String, // "yyyy-MM-dd"
    val type: MealType,
    val calories: Int,
    val protein: Int,
    val carbs: Int,
    val fat: Int,
    val itemsSummary: String = ""
)
