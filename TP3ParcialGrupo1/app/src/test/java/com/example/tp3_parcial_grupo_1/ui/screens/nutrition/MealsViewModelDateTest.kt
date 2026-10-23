package com.example.tp3_parcial_grupo_1.ui.screens.nutrition

import androidx.lifecycle.SavedStateHandle
import com.example.tp3_parcial_grupo_1.data.local.dao.MealDao
import com.example.tp3_parcial_grupo_1.data.local.entity.MealEntity
import com.example.tp3_parcial_grupo_1.data.repository.MealRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MealsViewModelDateTest {

    private val dao = InMemoryMealDao()
    private lateinit var viewModel: MealsViewModel

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        viewModel = MealsViewModel(MealRepository(dao), SavedStateHandle())
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given another date selected, when a meal is added to the previous date, then the selected date keeps its meals`() {
        val previousDate = viewModel.uiState.value.selectedDate
        viewModel.setDate(OTHER_DATE)

        dao.add(meal(name = "Late snack", date = previousDate))

        val state = viewModel.uiState.value
        assertEquals(OTHER_DATE, state.selectedDate)
        assertEquals(emptyList<String>(), state.meals.map { it.name })
    }

    private fun meal(name: String, date: String) = MealEntity(
        name = name,
        date = date,
        type = "SNACK",
        calories = 200,
        protein = 10,
        carbs = 20,
        fat = 5,
        itemsSummary = name
    )

    private class InMemoryMealDao : MealDao {
        private val meals = MutableStateFlow(listOf(SEED_MEAL))
        private var nextId = 2L

        fun add(meal: MealEntity) {
            meals.value = meals.value + meal.copy(id = nextId++)
        }

        override fun observeByDate(date: String): Flow<List<MealEntity>> =
            meals.map { all -> all.filter { it.date == date } }.distinctUntilChanged()

        override fun observeAll(): Flow<List<MealEntity>> = meals

        override suspend fun getById(id: Long): MealEntity? = meals.value.firstOrNull { it.id == id }

        override suspend fun insert(meal: MealEntity): Long {
            add(meal)
            return nextId - 1
        }

        override suspend fun deleteById(id: Long) {
            meals.value = meals.value.filterNot { it.id == id }
        }

        override suspend fun deleteAll() {
            meals.value = emptyList()
        }
    }

    private companion object {
        const val OTHER_DATE = "2026-01-15"
        val SEED_MEAL = MealEntity(
            id = 1L,
            name = "Seed",
            date = "2020-01-01",
            type = "LUNCH",
            calories = 100,
            protein = 5,
            carbs = 10,
            fat = 2,
            itemsSummary = "Seed"
        )
    }
}
