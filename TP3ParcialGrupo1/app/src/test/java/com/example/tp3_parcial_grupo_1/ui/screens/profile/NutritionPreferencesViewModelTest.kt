package com.example.tp3_parcial_grupo_1.ui.screens.profile

import com.example.tp3_parcial_grupo_1.data.repository.ProfileRepository
import com.example.tp3_parcial_grupo_1.model.DietaryPreference
import com.example.tp3_parcial_grupo_1.ui.strings.ProfileStrings
import com.example.tp3_parcial_grupo_1.model.NutritionPriority
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class NutritionPreferencesViewModelTest {
    private val repository = ProfileRepository()
    private val viewModel = NutritionPreferencesViewModel(repository)

    @Test
    fun `toggling a food adds and removes it`() {
        viewModel.onFoodEnjoyedToggled("Fish")
        assertTrue("Fish" in viewModel.uiState.value.foodsEnjoyed)

        viewModel.onFoodEnjoyedToggled("Fish")
        assertFalse("Fish" in viewModel.uiState.value.foodsEnjoyed)
    }

    @Test
    fun `adding an ingredient trims it and clears the input`() {
        viewModel.onIngredientInputChange("  Soy ")

        viewModel.onAddIngredientClick()

        assertEquals(listOf("Peanuts", "Shellfish", "Mushrooms", "Soy"), viewModel.uiState.value.foodsToAvoid)
        assertEquals("", viewModel.uiState.value.ingredientInput)
    }

    @Test
    fun `blank ingredient is ignored`() {
        viewModel.onIngredientInputChange("   ")

        viewModel.onAddIngredientClick()

        assertEquals(3, viewModel.uiState.value.foodsToAvoid.size)
    }

    @Test
    fun `duplicate ingredient shows an error and is not added`() {
        viewModel.onIngredientInputChange("peanuts")

        viewModel.onAddIngredientClick()

        assertEquals(ProfileStrings.IngredientDuplicate, viewModel.uiState.value.ingredientError)
        assertEquals(3, viewModel.uiState.value.foodsToAvoid.size)
    }

    @Test
    fun `editing the input clears the error`() {
        viewModel.onIngredientInputChange("Peanuts")
        viewModel.onAddIngredientClick()

        viewModel.onIngredientInputChange("Pe")

        assertNull(viewModel.uiState.value.ingredientError)
    }

    @Test
    fun `removing an ingredient drops it from the list`() {
        viewModel.onRemoveIngredientClick("Shellfish")

        assertEquals(listOf("Peanuts", "Mushrooms"), viewModel.uiState.value.foodsToAvoid)
    }

    @Test
    fun `save writes the selections to the shared preferences`() {
        viewModel.onDietSelected(DietaryPreference.Vegan)
        viewModel.onMealsPerDaySelected("4 meals")

        viewModel.onSaveClick()

        assertTrue(viewModel.uiState.value.isSaved)
        assertEquals(DietaryPreference.Vegan, repository.nutritionPreferences.value.diet)
        assertEquals("4 meals", repository.nutritionPreferences.value.mealsPerDay)
    }

    @Test
    fun `toggling a priority alternates it and never leaves the list empty`() {
        val both = setOf(NutritionPriority.HitProteinTargets, NutritionPriority.EatMoreConsistently)
        assertEquals(both, viewModel.uiState.value.priorities)

        viewModel.onPriorityToggled(NutritionPriority.HitProteinTargets)
        assertEquals(setOf(NutritionPriority.EatMoreConsistently), viewModel.uiState.value.priorities)

        viewModel.onPriorityToggled(NutritionPriority.EatMoreConsistently)
        assertEquals(setOf(NutritionPriority.EatMoreConsistently), viewModel.uiState.value.priorities)

        viewModel.onPriorityToggled(NutritionPriority.HitProteinTargets)
        assertEquals(both, viewModel.uiState.value.priorities)
    }
}
