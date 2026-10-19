package com.example.tp3_parcial_grupo_1.data.demo

import com.example.tp3_parcial_grupo_1.ui.screens.profile.ProfileUiState

// Datos de demostración: no provienen de ningún servidor.
object ProfileDemoData {
    val dashboard = ProfileUiState(
        fullName = "Alex Morgan",
        initials = "AM",
        tagline = "Building strength & improving fitness",
        goal = "Improve fitness",
        experience = "Intermediate",
        training = "4 days/week",
        preferredWorkout = "Strength training",
        isDataSynced = true
    )
}
