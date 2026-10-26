package com.example.tp3_parcial_grupo_1.ui.screens.profile

import com.example.tp3_parcial_grupo_1.ui.strings.ProfileStrings
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ProfileValidationTest {
    @Test
    fun `names are required`() {
        assertEquals(ProfileStrings.FirstNameRequired, validateFirstName(" "))
        assertEquals(ProfileStrings.LastNameRequired, validateLastName(""))
        assertNull(validateFirstName("Alex"))
    }

    @Test
    fun `date of birth accepts the figma format`() {
        assertNull(validateDateOfBirth("14 March 1995", currentYear = 2026))
    }

    @Test
    fun `date of birth rejects blank wrong format and out of range years`() {
        assertEquals(ProfileStrings.DateOfBirthRequired, validateDateOfBirth(""))
        assertEquals(ProfileStrings.DateOfBirthInvalid, validateDateOfBirth("1995-03-14"))
        assertEquals(ProfileStrings.DateOfBirthInvalid, validateDateOfBirth("14 March 1850"))
        assertEquals(ProfileStrings.DateOfBirthInvalid, validateDateOfBirth("14 March 2030", currentYear = 2026))
        assertEquals(ProfileStrings.DateOfBirthInvalid, validateDateOfBirth("40 March 1995", currentYear = 2026))
    }

    @Test
    fun `height and weight must be inside their ranges`() {
        assertNull(validateHeight("178"))
        assertEquals(ProfileStrings.HeightInvalid, validateHeight("99"))
        assertEquals(ProfileStrings.HeightInvalid, validateHeight("abc"))
        assertNull(validateWeight("76"))
        assertEquals(ProfileStrings.WeightInvalid, validateWeight("301"))
        assertEquals(ProfileStrings.WeightInvalid, validateWeight(""))
    }
}
