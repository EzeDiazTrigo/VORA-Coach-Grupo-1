package com.example.tp3_parcial_grupo_1.ui.screens.profile

import androidx.lifecycle.ViewModel
import com.example.tp3_parcial_grupo_1.data.repository.ProfileRepository
import com.example.tp3_parcial_grupo_1.model.DietaryPreference
import com.example.tp3_parcial_grupo_1.model.NutritionPriority
import com.example.tp3_parcial_grupo_1.ui.strings.ProfileStrings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NutritionPreferencesViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(repository.nutritionPreferences.value.toUiState())
    val uiState: StateFlow<NutritionPreferencesUiState> = _uiState.asStateFlow()

    fun onDietSelected(diet: DietaryPreference) = _uiState.update { it.copy(diet = diet) }

    fun onFoodEnjoyedToggled(food: String) = _uiState.update {
        it.copy(foodsEnjoyed = if (food in it.foodsEnjoyed) it.foodsEnjoyed - food else it.foodsEnjoyed + food)
    }

    fun onIngredientInputChange(value: String) =
        _uiState.update { it.copy(ingredientInput = value, ingredientError = null) }

    fun onAddIngredientClick() = _uiState.update { state ->
        val ingredient = state.ingredientInput.trim()
        when {
            ingredient.isEmpty() -> state
            state.foodsToAvoid.any { it.equals(ingredient, ignoreCase = true) } ->
                state.copy(ingredientError = ProfileStrings.IngredientDuplicate)
            else -> state.copy(foodsToAvoid = state.foodsToAvoid + ingredient, ingredientInput = "")
        }
    }

    fun onRemoveIngredientClick(ingredient: String) =
        _uiState.update { it.copy(foodsToAvoid = it.foodsToAvoid - ingredient) }

    fun onMealsPerDaySelected(value: String) = _uiState.update { it.copy(mealsPerDay = value) }

    fun onBreakfastSelected(value: String) = _uiState.update { it.copy(preferredBreakfast = value) }

    fun onDinnerSelected(value: String) = _uiState.update { it.copy(preferredDinner = value) }

    // Igual que las metas: se alternan y queda al menos una.
    fun onPriorityToggled(priority: NutritionPriority) = _uiState.update { state ->
        val priorities = if (priority in state.priorities) state.priorities - priority else state.priorities + priority
        if (priorities.isEmpty()) state else state.copy(priorities = priorities)
    }

    fun onSaveClick() {
        val state = _uiState.value
        repository.updateNutritionPreferences {
            it.copy(
                diet = state.diet,
                foodsEnjoyed = state.foodsEnjoyed,
                foodsToAvoid = state.foodsToAvoid,
                mealsPerDay = state.mealsPerDay,
                preferredBreakfast = state.preferredBreakfast,
                preferredDinner = state.preferredDinner,
                priorities = state.priorities
            )
        }
        _uiState.update { it.copy(isSaved = true) }
    }
}
