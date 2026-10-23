package com.example.tp3_parcial_grupo_1.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.tp3_parcial_grupo_1.model.Meal
import com.example.tp3_parcial_grupo_1.model.MealType

@Entity(tableName = "meals")
data class MealEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val date: String, // "yyyy-MM-dd"
    val type: String, // BREAKFAST, LUNCH, SNACK, DINNER
    val calories: Int,
    val protein: Int,
    val carbs: Int,
    val fat: Int,
    val itemsSummary: String
) {
    fun toDomain(): Meal = Meal(
        id = id,
        name = name,
        date = date,
        type = try { MealType.valueOf(type) } catch (e: Exception) { MealType.LUNCH },
        calories = calories,
        protein = protein,
        carbs = carbs,
        fat = fat,
        itemsSummary = itemsSummary
    )

    companion object {
        fun fromDomain(meal: Meal): MealEntity = MealEntity(
            id = meal.id,
            name = meal.name,
            date = meal.date,
            type = meal.type.name,
            calories = meal.calories,
            protein = meal.protein,
            carbs = meal.carbs,
            fat = meal.fat,
            itemsSummary = meal.itemsSummary
        )
    }
}
