package com.example.tp3_parcial_grupo_1.data.demo

import com.example.tp3_parcial_grupo_1.model.CoachingStyle
import com.example.tp3_parcial_grupo_1.model.DietaryPreference
import com.example.tp3_parcial_grupo_1.model.NutritionPreferences
import com.example.tp3_parcial_grupo_1.model.NutritionPriority
import com.example.tp3_parcial_grupo_1.model.PrimaryGoal
import com.example.tp3_parcial_grupo_1.model.UserProfile
import com.example.tp3_parcial_grupo_1.model.WorkoutPreference
import com.example.tp3_parcial_grupo_1.ui.screens.profile.toUiState

// Datos de demostración: no provienen de ningún servidor.
object ProfileDemoData {
    val profile = UserProfile(
        firstName = "Alex",
        lastName = "Morgan",
        dateOfBirth = "14 March 1995",
        heightCm = 178,
        weightKg = 76,
        gender = "Prefer not to say",
        tagline = "Building strength & improving fitness",
        goal = PrimaryGoal.ImproveFitness,
        experience = "Intermediate",
        trainingDaysPerWeek = 4,
        preferredWorkout = WorkoutPreference.Strength,
        coachingStyle = CoachingStyle.Balanced
    )

    val nutritionPreferences = NutritionPreferences(
        diet = DietaryPreference.Everything,
        foodsEnjoyed = setOf("Chicken", "Eggs", "Rice"),
        foodsToAvoid = listOf("Peanuts", "Shellfish", "Mushrooms"),
        mealsPerDay = "3 meals + 1 snack",
        preferredBreakfast = "Quick & high protein",
        preferredDinner = "Balanced plate",
        priority = NutritionPriority.HitProteinTargets
    )

    val dashboard = profile.toUiState()
}
