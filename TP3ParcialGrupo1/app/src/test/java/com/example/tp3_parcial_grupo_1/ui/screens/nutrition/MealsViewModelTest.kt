package com.example.tp3_parcial_grupo_1.ui.screens.nutrition

import androidx.lifecycle.SavedStateHandle
import com.example.tp3_parcial_grupo_1.data.local.dao.MealDao
import com.example.tp3_parcial_grupo_1.data.local.entity.MealEntity
import com.example.tp3_parcial_grupo_1.data.repository.MealRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.*
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class FakeMealDao : MealDao {
    var meals = mutableListOf<MealEntity>()
    var mealToReturn: MealEntity? = null

    override fun observeByDate(date: String): Flow<List<MealEntity>> = flowOf(meals.filter { it.date == date })
    override fun observeAll(): Flow<List<MealEntity>> = flowOf(meals)
    override suspend fun getById(id: Long): MealEntity? = mealToReturn ?: meals.find { it.id == id }
    override suspend fun insert(meal: MealEntity): Long {
        meals.add(meal)
        return meal.id
    }
    override suspend fun deleteById(id: Long) {
        meals.removeIf { it.id == id }
    }
    override suspend fun deleteAll() {
        meals.clear()
    }
}

@OptIn(ExperimentalCoroutinesApi::class)
class MealsViewModelTest {

    private val testDispatcher = UnconfinedTestDispatcher()
    private lateinit var fakeDao: FakeMealDao
    private lateinit var mealRepository: MealRepository
    private lateinit var savedStateHandle: SavedStateHandle

    @Before
    fun setUp() {
        Dispatchers.setMain(testDispatcher)
        fakeDao = FakeMealDao()
        mealRepository = MealRepository(fakeDao)
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given a viewmodel, when initialized, then goal calories is 2100`() {
        savedStateHandle = SavedStateHandle()
        val viewModel = MealsViewModel(mealRepository, savedStateHandle)

        assertEquals(2100, viewModel.uiState.value.goalCalories)
    }

    @Test
    fun `given a meal id arg, when initialized, then it loads meal detail`() = runTest {
        val testEntity = MealEntity(id = 5L, name = "Lunch", date = "2026-10-26", type = "LUNCH", calories = 500, protein = 30, carbs = 40, fat = 15, itemsSummary = "")
        fakeDao.mealToReturn = testEntity
        savedStateHandle = SavedStateHandle(mapOf("mealId" to 5L))

        val viewModel = MealsViewModel(mealRepository, savedStateHandle)
        testScheduler.advanceUntilIdle()

        assertEquals(5L, viewModel.uiState.value.currentMealDetail?.id)
        assertEquals("Lunch", viewModel.uiState.value.currentMealDetail?.name)
    }

    @Test
    fun `given an existing meal, when duplicateMeal is called, then a copy is inserted`() = runTest {
        val testEntity = MealEntity(id = 1L, name = "Breakfast", date = "2026-10-26", type = "BREAKFAST", calories = 400, protein = 20, carbs = 30, fat = 10, itemsSummary = "")
        fakeDao.meals.add(testEntity)
        savedStateHandle = SavedStateHandle()

        val viewModel = MealsViewModel(mealRepository, savedStateHandle)
        viewModel.duplicateMeal(1L)
        testScheduler.advanceUntilIdle()

        assertEquals(2, fakeDao.meals.size)
        assertEquals("Breakfast", fakeDao.meals.last().name)
    }
}
