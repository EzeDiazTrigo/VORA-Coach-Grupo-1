package com.example.tp3_parcial_grupo_1.ui.screens.nutrition

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp3_parcial_grupo_1.data.repository.MealRepository
import com.example.tp3_parcial_grupo_1.model.Meal
import com.example.tp3_parcial_grupo_1.model.MealType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

data class MealUiState(
    val selectedDate: String = "2026-09-08",
    val meals: List<Meal> = emptyList(),
    val totalCalories: Int = 1420,
    val totalProtein: Int = 104,
    val totalCarbs: Int = 152,
    val totalFat: Int = 48,
    val goalCalories: Int = 2100,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val inputName: String = "",
    val inputCalories: String = "",
    val inputProtein: String = "",
    val inputCarbs: String = "",
    val inputFat: String = "",
    val inputType: MealType = MealType.LUNCH
)

@HiltViewModel
class MealsViewModel @Inject constructor(
    private val mealRepository: MealRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(MealUiState())
    val uiState: StateFlow<MealUiState> = _uiState.asStateFlow()

    init {
        loadDefaultMeals()
        observeMeals("2026-09-08")
    }

    private fun loadDefaultMeals() {
        viewModelScope.launch {
            val current = _uiState.value.meals
            if (current.isEmpty()) {
                val demoMeals = listOf(
                    Meal(name = "Breakfast", date = "2026-09-08", type = MealType.BREAKFAST, calories = 420, protein = 25, carbs = 45, fat = 12, itemsSummary = "Greek yoghurt - berries - granola"),
                    Meal(name = "Lunch", date = "2026-09-08", type = MealType.LUNCH, calories = 520, protein = 38, carbs = 54, fat = 14, itemsSummary = "Chicken - rice - vegetables"),
                    Meal(name = "Snack", date = "2026-09-08", type = MealType.SNACK, calories = 240, protein = 20, carbs = 12, fat = 6, itemsSummary = "Protein shake")
                )
                demoMeals.forEach { mealRepository.insertMeal(it) }
            }
        }
    }

    private fun observeMeals(date: String) {
        viewModelScope.launch {
            mealRepository.observeMealsByDate(date).collect { meals ->
                val cal = meals.sumOf { it.calories }
                val pro = meals.sumOf { it.protein }
                val carb = meals.sumOf { it.carbs }
                val fat = meals.sumOf { it.fat }
                _uiState.update {
                    it.copy(
                        meals = meals,
                        totalCalories = if (meals.isNotEmpty()) cal else 1420,
                        totalProtein = if (meals.isNotEmpty()) pro else 104,
                        totalCarbs = if (meals.isNotEmpty()) carb else 152,
                        totalFat = if (meals.isNotEmpty()) fat else 48
                    )
                }
            }
        }
    }

    fun addMeal(meal: Meal) {
        viewModelScope.launch {
            mealRepository.insertMeal(meal)
        }
    }

    fun deleteMeal(id: Long) {
        viewModelScope.launch {
            mealRepository.deleteMeal(id)
        }
    }

    fun updateInput(name: String, calories: String, protein: String, carbs: String, fat: String, type: MealType) {
        _uiState.update {
            it.copy(
                inputName = name,
                inputCalories = calories,
                inputProtein = protein,
                inputCarbs = carbs,
                inputFat = fat,
                inputType = type
            )
        }
    }
}
