package com.example.tp3_parcial_grupo_1.ui.screens.progress

import com.example.tp3_parcial_grupo_1.data.demo.ProgressDemoData
import org.junit.Assert.assertEquals
import org.junit.Test

class ProgressDashboardViewModelTest {

    @Test
    fun `given a new dashboard, when it opens, then it shows the week`() {
        val viewModel = ProgressDashboardViewModel()

        assertEquals(ProgressPeriod.Week, viewModel.uiState.value.period)
        assertEquals(ProgressDemoData.periods.getValue(ProgressPeriod.Week), viewModel.uiState.value.progress)
    }

    @Test
    fun `given the week, when selecting month, then it shows the month progress`() {
        val viewModel = ProgressDashboardViewModel()

        viewModel.onPeriodSelect(ProgressPeriod.Month)

        assertEquals(ProgressPeriod.Month, viewModel.uiState.value.period)
        assertEquals("THIS MONTH OVERVIEW", viewModel.uiState.value.progress.overviewLabel)
    }

    @Test
    fun `given three months selected, when going back to week, then it shows the week again`() {
        val viewModel = ProgressDashboardViewModel()

        viewModel.onPeriodSelect(ProgressPeriod.ThreeMonths)
        viewModel.onPeriodSelect(ProgressPeriod.Week)

        assertEquals("THIS WEEK OVERVIEW", viewModel.uiState.value.progress.overviewLabel)
    }
}
