package com.example.tp3_parcial_grupo_1.ui.screens.progress

import com.example.tp3_parcial_grupo_1.data.demo.ProgressDemoData
import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class WorkoutProgressViewModelTest {

    @Test
    fun `given a new workout progress, when it opens, then it shows the week`() {
        val viewModel = WorkoutProgressViewModel()

        assertEquals(ProgressPeriod.Week, viewModel.uiState.value.period)
        assertEquals(ProgressDemoData.workoutPeriods.getValue(ProgressPeriod.Week), viewModel.uiState.value.progress)
    }

    @Test
    fun `given the week, when selecting three months, then it shows the three months progress`() {
        val viewModel = WorkoutProgressViewModel()

        viewModel.onPeriodSelect(ProgressPeriod.ThreeMonths)

        assertEquals(ProgressPeriod.ThreeMonths, viewModel.uiState.value.period)
        assertEquals(ProgressDemoData.workoutPeriods.getValue(ProgressPeriod.ThreeMonths), viewModel.uiState.value.progress)
    }

    @Test
    fun `given no message, when tapping more, then it shows the demo mode message`() {
        val viewModel = WorkoutProgressViewModel()

        viewModel.onMoreClick()

        assertEquals(Strings.DemoMode, viewModel.uiState.value.message)
    }

    @Test
    fun `given the demo message, when it was shown, then the message is cleared`() {
        val viewModel = WorkoutProgressViewModel()
        viewModel.onMoreClick()

        viewModel.onMessageShown()

        assertNull(viewModel.uiState.value.message)
    }
}
