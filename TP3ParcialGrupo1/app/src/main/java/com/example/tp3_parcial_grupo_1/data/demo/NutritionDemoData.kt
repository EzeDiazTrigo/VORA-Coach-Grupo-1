package com.example.tp3_parcial_grupo_1.data.demo

import com.example.tp3_parcial_grupo_1.model.Meal
import com.example.tp3_parcial_grupo_1.model.MealType

// Datos de demostración: se insertan una sola vez, cuando Room crea la base por primera vez.
object NutritionDemoData {
    fun meals(date: String): List<Meal> = listOf(
        Meal(
            name = "Breakfast",
            date = date,
            type = MealType.BREAKFAST,
            calories = 420,
            protein = 25,
            carbs = 45,
            fat = 12,
            itemsSummary = "Greek yoghurt - berries - granola"
        ),
        Meal(
            name = "Lunch",
            date = date,
            type = MealType.LUNCH,
            calories = 520,
            protein = 38,
            carbs = 54,
            fat = 14,
            itemsSummary = "Chicken - rice - vegetables"
        ),
        Meal(
            name = "Snack",
            date = date,
            type = MealType.SNACK,
            calories = 240,
            protein = 20,
            carbs = 12,
            fat = 6,
            itemsSummary = "Protein shake"
        )
    )
}

// La comida que «analiza» la IA en Analyse Meal y Review Meal (Figma: 520 kcal, P 38 g, C 54 g, F 14 g).
object AnalysedMeal {
    data class Ingredient(val name: String, val quantity: String, val calories: Int)

    const val calories = 520
    const val protein = 38
    const val carbs = 54
    const val fat = 14

    val ingredients = listOf(
        Ingredient("Grilled Chicken Breast", "180g", 280),
        Ingredient("Basmati Rice", "150g", 190),
        Ingredient("Mixed Steamed Vegetables", "100g", 50)
    )

    fun asLunch(date: String) = Meal(
        name = "Lunch",
        date = date,
        type = MealType.LUNCH,
        calories = calories,
        protein = protein,
        carbs = carbs,
        fat = fat,
        itemsSummary = "Grilled Chicken - Basmati Rice - Vegetables"
    )
}
