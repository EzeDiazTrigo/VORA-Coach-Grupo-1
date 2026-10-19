package com.example.tp3_parcial_grupo_1.ui.screens.profile

data class SettingsUiState(
    val email: String,
    val showLogOutDialog: Boolean = false,
    val message: String? = null
)
