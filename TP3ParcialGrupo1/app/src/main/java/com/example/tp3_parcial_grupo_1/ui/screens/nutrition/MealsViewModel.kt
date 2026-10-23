package com.example.tp3_parcial_grupo_1.ui.screens.nutrition

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp3_parcial_grupo_1.data.repository.MealRepository
import com.example.tp3_parcial_grupo_1.model.Meal
import com.example.tp3_parcial_grupo_1.model.MealType
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

data class MealUiState(
    val selectedDate: String = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()),
    val meals: List<Meal> = emptyList(),
    val totalCalories: Int = 0,
    val totalProtein: Int = 0,
    val totalCarbs: Int = 0,
    val totalFat: Int = 0,
    val goalCalories: Int = 2100,
    val isLoading: Boolean = false,
    val errorMessage: String? = null,
    val inputName: String = "",
    val inputCalories: String = "",
    val inputProtein: String = "",
    val inputCarbs: String = "",
    val inputFat: String = "",
    val inputType: MealType = MealType.LUNCH,
    val currentMealDetail: Meal? = null
)

@HiltViewModel
class MealsViewModel @Inject constructor(
    private val mealRepository: MealRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val mealIdArg: Long = savedStateHandle.get<Long>("mealId") ?: 0L

    private val _uiState = MutableStateFlow(MealUiState())
    val uiState: StateFlow<MealUiState> = _uiState.asStateFlow()

    init {
        val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
        loadDefaultMealsIfDatabaseEmpty()
        observeMeals(today)
        if (mealIdArg > 0L) {
            loadMealDetail(mealIdArg)
        }
    }

    fun loadMealDetail(id: Long) {
        viewModelScope.launch {
            val meal = mealRepository.getMealById(id)
            _uiState.update { it.copy(currentMealDetail = meal) }
        }
    }

    fun setDate(date: String) {
        observeMeals(date)
    }

    private fun loadDefaultMealsIfDatabaseEmpty() {
        viewModelScope.launch {
            mealRepository.observeAllMeals().take(1).collect { allMeals ->
                if (allMeals.isEmpty()) {
                    val today = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())
                    val demoMeals = listOf(
                        Meal(name = "Breakfast", date = today, type = MealType.BREAKFAST, calories = 420, protein = 25, carbs = 45, fat = 12, itemsSummary = "Greek yoghurt - berries - granola"),
                        Meal(name = "Lunch", date = today, type = MealType.LUNCH, calories = 520, protein = 38, carbs = 54, fat = 14, itemsSummary = "Chicken - rice - vegetables"),
                        Meal(name = "Snack", date = today, type = MealType.SNACK, calories = 240, protein = 20, carbs = 12, fat = 6, itemsSummary = "Protein shake")
                    )
                    demoMeals.forEach { mealRepository.insertMeal(it) }
                }
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
                        selectedDate = date,
                        meals = meals,
                        totalCalories = cal,
                        totalProtein = pro,
                        totalCarbs = carb,
                        totalFat = fat
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
}
