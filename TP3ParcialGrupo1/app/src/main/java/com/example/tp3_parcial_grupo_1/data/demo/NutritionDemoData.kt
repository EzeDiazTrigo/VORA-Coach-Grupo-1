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
