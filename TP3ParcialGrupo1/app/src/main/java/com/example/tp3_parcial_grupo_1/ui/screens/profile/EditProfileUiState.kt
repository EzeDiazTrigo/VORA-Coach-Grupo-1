package com.example.tp3_parcial_grupo_1.ui.screens.profile

import com.example.tp3_parcial_grupo_1.model.UserProfile

data class EditProfileUiState(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val height: String,
    val weight: String,
    val gender: String,
    val firstNameError: String? = null,
    val lastNameError: String? = null,
    val dateOfBirthError: String? = null,
    val heightError: String? = null,
    val weightError: String? = null,
    val isSaved: Boolean = false,
    val message: String? = null
)

fun UserProfile.toEditUiState() = EditProfileUiState(
    firstName = firstName,
    lastName = lastName,
    dateOfBirth = dateOfBirth,
    height = heightCm.toString(),
    weight = weightKg.toString(),
    gender = gender
)
