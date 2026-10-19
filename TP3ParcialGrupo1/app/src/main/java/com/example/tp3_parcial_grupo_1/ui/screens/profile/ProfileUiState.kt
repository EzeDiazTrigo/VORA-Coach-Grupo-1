package com.example.tp3_parcial_grupo_1.ui.screens.profile

import com.example.tp3_parcial_grupo_1.model.UserProfile
import com.example.tp3_parcial_grupo_1.ui.strings.ProfileStrings

data class ProfileUiState(
    val fullName: String,
    val initials: String,
    val tagline: String,
    val goal: String,
    val experience: String,
    val training: String,
    val preferredWorkout: String,
    val isDataSynced: Boolean,
    val message: String? = null
)

fun UserProfile.toUiState(isDataSynced: Boolean = true, message: String? = null) = ProfileUiState(
    fullName = fullName,
    initials = initials,
    tagline = tagline,
    goal = goal,
    experience = experience,
    training = ProfileStrings.daysPerWeek(trainingDaysPerWeek),
    preferredWorkout = preferredWorkout,
    isDataSynced = isDataSynced,
    message = message
)
