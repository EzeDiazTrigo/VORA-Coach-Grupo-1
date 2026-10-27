package com.example.tp3_parcial_grupo_1.ui.screens.workout

import com.example.tp3_parcial_grupo_1.model.Workout

data class WorkoutDetailUiState(
    val isLoading: Boolean = true,
    val workout: Workout? = null,
    val errorMessage: String? = null,
    // El detalle nunca se descargó y no hay red: no se puede mostrar sin conexión (RF02).
    val isUnavailableOffline: Boolean = false
)
