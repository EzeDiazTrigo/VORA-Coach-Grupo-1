package com.example.tp3_parcial_grupo_1.model

enum class MealType {
    BREAKFAST, LUNCH, SNACK, DINNER;

    fun displayName(): String = when (this) {
        BREAKFAST -> "Breakfast"
        LUNCH -> "Lunch"
        SNACK -> "Snack"
        DINNER -> "Dinner"
    }
}
