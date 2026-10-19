package com.example.tp3_parcial_grupo_1.ui.screens.profile

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
