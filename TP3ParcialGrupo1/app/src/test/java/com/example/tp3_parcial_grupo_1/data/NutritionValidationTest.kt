package com.example.tp3_parcial_grupo_1.data

import com.example.tp3_parcial_grupo_1.utils.validateMeal
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class NutritionValidationTest {

    @Test
    fun validateMeal_validInput_returnsTrue() {
        val result = validateMeal("Chicken Rice", "420", "38", "54", "14")
        assertTrue(result.isValid)
    }

    @Test
    fun validateMeal_emptyName_returnsFalse() {
        val result = validateMeal("", "420", "38", "54", "14")
        assertFalse(result.isValid)
    }

    @Test
    fun validateMeal_negativeCalories_returnsFalse() {
        val result = validateMeal("Chicken Rice", "-10", "38", "54", "14")
        assertFalse(result.isValid)
    }

    @Test
    fun validateMeal_invalidNumberFormat_returnsFalse() {
        val result = validateMeal("Chicken Rice", "abc", "38", "54", "14")
        assertFalse(result.isValid)
    }
}
