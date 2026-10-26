package com.example.tp3_parcial_grupo_1.ui.screens.nutrition

import androidx.lifecycle.SavedStateHandle
import com.example.tp3_parcial_grupo_1.data.demo.NutritionDemoData
import com.example.tp3_parcial_grupo_1.data.repository.MealRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class MealsViewModelSeedTest {

    private lateinit var dao: FakeMealDao

    @Before
    fun setUp() {
        Dispatchers.setMain(UnconfinedTestDispatcher())
        dao = FakeMealDao()
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
    }

    @Test
    fun `given an empty database, when a viewmodel is created, then it does not insert meals`() {
        MealsViewModel(MealRepository(dao), SavedStateHandle())

        assertTrue(dao.meals.isEmpty())
    }

    @Test
    fun `given all meals deleted, when another viewmodel is created, then the meals stay deleted`() {
        val first = MealsViewModel(MealRepository(dao), SavedStateHandle())
        NutritionDemoData.meals("2026-10-25").forEach { first.addMeal(it) }
        dao.meals.map { it.id }.forEach { first.deleteMeal(it) }

        val second = MealsViewModel(MealRepository(dao), SavedStateHandle())

        assertTrue(dao.meals.isEmpty())
        assertTrue(second.uiState.value.meals.isEmpty())
    }

    @Test
    fun `given a date, when building the demo meals, then all of them use that date`() {
        val meals = NutritionDemoData.meals("2026-10-25")

        assertEquals(3, meals.size)
        assertTrue(meals.all { it.date == "2026-10-25" })
    }
}
