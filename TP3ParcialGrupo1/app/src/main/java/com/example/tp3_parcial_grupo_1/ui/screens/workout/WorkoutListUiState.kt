package com.example.tp3_parcial_grupo_1.ui.screens.workout

import com.example.tp3_parcial_grupo_1.model.Workout

data class WorkoutListUiState(
    val isLoading: Boolean = true,
    val workouts: List<Workout> = emptyList(),
    val selectedLevelFilter: String = "All",
    val errorMessage: String? = null
)
