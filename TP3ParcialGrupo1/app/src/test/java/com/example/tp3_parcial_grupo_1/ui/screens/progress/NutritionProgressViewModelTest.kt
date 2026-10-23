package com.example.tp3_parcial_grupo_1.ui.screens.progress

import com.example.tp3_parcial_grupo_1.data.demo.ProgressDemoData
import org.junit.Assert.assertEquals
import org.junit.Test

class NutritionProgressViewModelTest {

    @Test
    fun `given a new nutrition progress, when it opens, then it shows the protein target of the week`() {
        val viewModel = NutritionProgressViewModel()

        assertEquals(ProgressPeriod.Week, viewModel.uiState.value.period)
        assertEquals(NutritionMacro.Protein, viewModel.uiState.value.macro)
        assertEquals(ProgressDemoData.macroTargets.getValue(NutritionMacro.Protein), viewModel.uiState.value.target)
    }

    @Test
    fun `given protein selected, when selecting carbs, then it shows the carbs daily compliance`() {
        val viewModel = NutritionProgressViewModel()

        viewModel.onMacroSelect(NutritionMacro.Carbs)

        assertEquals(NutritionMacro.Carbs, viewModel.uiState.value.macro)
        assertEquals(ProgressDemoData.macroTargets.getValue(NutritionMacro.Carbs), viewModel.uiState.value.target)
    }

    @Test
    fun `given carbs selected, when selecting month, then it keeps carbs and shows the month progress`() {
        val viewModel = NutritionProgressViewModel()
        viewModel.onMacroSelect(NutritionMacro.Carbs)

        viewModel.onPeriodSelect(ProgressPeriod.Month)

        assertEquals(NutritionMacro.Carbs, viewModel.uiState.value.macro)
        assertEquals(ProgressDemoData.nutritionPeriods.getValue(ProgressPeriod.Month), viewModel.uiState.value.progress)
    }
}
