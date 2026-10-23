package com.example.tp3_parcial_grupo_1.ui.screens.progress

import com.example.tp3_parcial_grupo_1.data.demo.ProgressDemoData
import org.junit.Assert.assertEquals
import org.junit.Test

class TodaysOverviewViewModelTest {

    @Test
    fun `given the demo day, when opening the overview, then the daily completion is 72 percent`() {
        val viewModel = TodaysOverviewViewModel()

        assertEquals(72, viewModel.uiState.value.completionPercent)
    }

    @Test
    fun `given a completion just below the next percent, when reading it, then it rounds to the nearest percent`() {
        val overview = ProgressDemoData.todaysOverview.copy(completion = 0.729f)

        assertEquals(73, overview.completionPercent)
    }
}
