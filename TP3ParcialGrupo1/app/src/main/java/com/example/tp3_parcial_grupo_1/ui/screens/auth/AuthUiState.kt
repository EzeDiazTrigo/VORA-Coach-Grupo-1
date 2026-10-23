package com.example.tp3_parcial_grupo_1.ui.screens.auth

data class LoginUiState(
    val email: String = "",
    val password: String = "",
    val emailError: String? = null,
    val passwordError: String? = null,
    val isSignedIn: Boolean = false,
    val message: String? = null
)

data class RegisterUiState(
    val fullName: String = "",
    val email: String = "",
    val password: String = "",
    val fullNameError: String? = null,
    val emailError: String? = null,
    val passwordError: String? = null,
    val isSignedUp: Boolean = false,
    val message: String? = null
) {
    val hasSecurePassword: Boolean get() = password.length >= MIN_PASSWORD_LENGTH
}
