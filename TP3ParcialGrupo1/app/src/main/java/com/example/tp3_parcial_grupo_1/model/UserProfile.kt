package com.example.tp3_parcial_grupo_1.model

data class UserProfile(
    val firstName: String,
    val lastName: String,
    val dateOfBirth: String,
    val heightCm: Int,
    val weightKg: Int,
    val gender: String,
    val tagline: String,
    val goal: String,
    val experience: String,
    val trainingDaysPerWeek: Int,
    val preferredWorkout: String
) {
    val fullName: String get() = "$firstName $lastName".trim()
    val initials: String get() = "${firstName.firstOrNull() ?: ""}${lastName.firstOrNull() ?: ""}".uppercase()
}
