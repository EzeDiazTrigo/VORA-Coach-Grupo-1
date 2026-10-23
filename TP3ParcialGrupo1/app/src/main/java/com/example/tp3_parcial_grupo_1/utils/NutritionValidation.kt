package com.example.tp3_parcial_grupo_1.utils

data class MealValidationResult(
    val isValid: Boolean,
    val errorMessage: String? = null
)

fun validateMeal(
    name: String,
    caloriesStr: String,
    proteinStr: String,
    carbsStr: String,
    fatStr: String
): MealValidationResult {
    if (name.isBlank()) {
        return MealValidationResult(false, "Meal name cannot be empty")
    }
    val calories = caloriesStr.toIntOrNull()
    if (calories == null || calories < 0) {
        return MealValidationResult(false, "Invalid calories value")
    }
    val protein = proteinStr.toIntOrNull()
    if (protein == null || protein < 0) {
        return MealValidationResult(false, "Invalid protein value")
    }
    val carbs = carbsStr.toIntOrNull()
    if (carbs == null || carbs < 0) {
        return MealValidationResult(false, "Invalid carbs value")
    }
    val fat = fatStr.toIntOrNull()
    if (fat == null || fat < 0) {
        return MealValidationResult(false, "Invalid fat value")
    }
    return MealValidationResult(true)
}
