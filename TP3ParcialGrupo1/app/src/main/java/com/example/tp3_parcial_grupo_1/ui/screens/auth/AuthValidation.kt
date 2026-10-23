package com.example.tp3_parcial_grupo_1.ui.screens.auth

import com.example.tp3_parcial_grupo_1.ui.strings.AuthStrings

const val MIN_PASSWORD_LENGTH = 8

private val EMAIL_REGEX = Regex("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

fun validateEmail(email: String): String? = when {
    email.isBlank() -> AuthStrings.EmailRequired
    !EMAIL_REGEX.matches(email.trim()) -> AuthStrings.EmailInvalid
    else -> null
}

fun validatePassword(password: String): String? = when {
    password.isEmpty() -> AuthStrings.PasswordRequired
    password.length < MIN_PASSWORD_LENGTH -> AuthStrings.PasswordTooShort
    else -> null
}

fun validateFullName(name: String): String? =
    if (name.isBlank()) AuthStrings.NameRequired else null
