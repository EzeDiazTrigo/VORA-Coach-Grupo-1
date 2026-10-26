package com.example.tp3_parcial_grupo_1.ui.screens.profile

import com.example.tp3_parcial_grupo_1.model.DietaryPreference
import com.example.tp3_parcial_grupo_1.model.NutritionPreferences
import com.example.tp3_parcial_grupo_1.model.NutritionPriority

data class NutritionPreferencesUiState(
    val diet: DietaryPreference,
    val foodsEnjoyed: Set<String>,
    val foodsToAvoid: List<String>,
    val ingredientInput: String = "",
    val ingredientError: String? = null,
    val mealsPerDay: String,
    val preferredBreakfast: String,
    val preferredDinner: String,
    val priority: NutritionPriority,
    val isSaved: Boolean = false
)

fun NutritionPreferences.toUiState() = NutritionPreferencesUiState(
    diet = diet,
    foodsEnjoyed = foodsEnjoyed,
    foodsToAvoid = foodsToAvoid,
    mealsPerDay = mealsPerDay,
    preferredBreakfast = preferredBreakfast,
    preferredDinner = preferredDinner,
    priority = priority
)
