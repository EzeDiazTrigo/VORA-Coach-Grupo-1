package com.example.tp3_parcial_grupo_1.ui.screens.profile

import com.example.tp3_parcial_grupo_1.ui.strings.ProfileStrings
import java.util.Calendar

const val MIN_HEIGHT_CM = 100
const val MAX_HEIGHT_CM = 250
const val MIN_WEIGHT_KG = 30
const val MAX_WEIGHT_KG = 300
const val MIN_BIRTH_YEAR = 1900

// Formato del diseño: «14 March 1995».
private val DATE_OF_BIRTH_REGEX = Regex("^(\\d{1,2}) [A-Za-z]+ (\\d{4})$")

fun validateFirstName(name: String): String? =
    if (name.isBlank()) ProfileStrings.FirstNameRequired else null

fun validateLastName(name: String): String? =
    if (name.isBlank()) ProfileStrings.LastNameRequired else null

fun validateDateOfBirth(
    date: String,
    currentYear: Int = Calendar.getInstance().get(Calendar.YEAR)
): String? {
    if (date.isBlank()) return ProfileStrings.DateOfBirthRequired
    val match = DATE_OF_BIRTH_REGEX.matchEntire(date.trim()) ?: return ProfileStrings.DateOfBirthInvalid
    val day = match.groupValues[1].toInt()
    val year = match.groupValues[2].toInt()
    return if (day !in 1..31 || year !in MIN_BIRTH_YEAR..currentYear) ProfileStrings.DateOfBirthInvalid else null
}

fun validateHeight(heightCm: String): String? =
    if (heightCm.trim().toIntOrNull() in MIN_HEIGHT_CM..MAX_HEIGHT_CM) null else ProfileStrings.HeightInvalid

fun validateWeight(weightKg: String): String? =
    if (weightKg.trim().toIntOrNull() in MIN_WEIGHT_KG..MAX_WEIGHT_KG) null else ProfileStrings.WeightInvalid
