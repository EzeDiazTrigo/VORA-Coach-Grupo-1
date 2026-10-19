package com.example.tp3_parcial_grupo_1.ui.strings

import com.example.tp3_parcial_grupo_1.model.CoachingStyle
import com.example.tp3_parcial_grupo_1.model.PrimaryGoal
import com.example.tp3_parcial_grupo_1.model.WorkoutPreference

// Textos de cada opción del perfil, tal como aparecen en el diseño de Figma.
fun PrimaryGoal.title(): String = when (this) {
    PrimaryGoal.BuildStrength -> "Build strength"
    PrimaryGoal.ImproveFitness -> "Improve fitness"
    PrimaryGoal.LoseBodyFat -> "Lose body fat"
    PrimaryGoal.BuildHealthyHabits -> "Build healthy habits"
}

fun PrimaryGoal.description(): String? = when (this) {
    PrimaryGoal.BuildStrength -> "Focus on weight training & mechanical load"
    PrimaryGoal.ImproveFitness -> "Condition cardiovascular & energy systems"
    PrimaryGoal.LoseBodyFat, PrimaryGoal.BuildHealthyHabits -> null
}

fun WorkoutPreference.chipLabel(): String = when (this) {
    WorkoutPreference.Strength -> "Strength"
    WorkoutPreference.Cardio -> "Cardio"
    WorkoutPreference.Hiit -> "HIIT"
    WorkoutPreference.Mobility -> "Mobility"
    WorkoutPreference.Mixed -> "Mixed"
}

// El Dashboard de Perfil muestra «Strength training» para la preferencia «Strength».
fun WorkoutPreference.summaryLabel(): String = when (this) {
    WorkoutPreference.Strength -> "Strength training"
    else -> chipLabel()
}

fun CoachingStyle.title(): String = when (this) {
    CoachingStyle.Supportive -> "Supportive"
    CoachingStyle.Balanced -> "Balanced"
    CoachingStyle.Direct -> "Direct"
}

fun CoachingStyle.description(): String = when (this) {
    CoachingStyle.Supportive -> "Encouragement first, with a focus on consistency"
    CoachingStyle.Balanced -> "Practical guidance with gentle motivation"
    CoachingStyle.Direct -> "Short, straight-to-the-point feedback"
}

val TrainingDaysOptions = listOf(3, 4, 5, 6)

fun trainingDaysLabel(days: Int): String = if (days >= 6) "6+ days" else "$days days"
