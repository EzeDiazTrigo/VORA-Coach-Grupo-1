package com.example.tp3_parcial_grupo_1.model

enum class DietaryPreference { Everything, Vegetarian, Vegan, Pescatarian, Other }

enum class NutritionPriority { HitProteinTargets, EatMoreConsistently }

data class NutritionPreferences(
    val diet: DietaryPreference,
    val foodsEnjoyed: Set<String>,
    val foodsToAvoid: List<String>,
    val mealsPerDay: String,
    val preferredBreakfast: String,
    val preferredDinner: String,
    val priority: NutritionPriority
)
