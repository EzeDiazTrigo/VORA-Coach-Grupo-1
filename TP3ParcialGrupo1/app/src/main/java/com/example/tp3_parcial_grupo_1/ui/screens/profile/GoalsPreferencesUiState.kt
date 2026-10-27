package com.example.tp3_parcial_grupo_1.ui.screens.profile

import com.example.tp3_parcial_grupo_1.model.CoachingStyle
import com.example.tp3_parcial_grupo_1.model.PrimaryGoal
import com.example.tp3_parcial_grupo_1.model.UserProfile
import com.example.tp3_parcial_grupo_1.model.WorkoutPreference

data class GoalsPreferencesUiState(
    val goals: Set<PrimaryGoal>,
    val trainingDays: Int,
    val workoutPreference: WorkoutPreference,
    val coachingStyle: CoachingStyle,
    val isSaved: Boolean = false
)

fun UserProfile.toGoalsUiState() = GoalsPreferencesUiState(
    goals = goals,
    trainingDays = trainingDaysPerWeek,
    workoutPreference = preferredWorkout,
    coachingStyle = coachingStyle
)
