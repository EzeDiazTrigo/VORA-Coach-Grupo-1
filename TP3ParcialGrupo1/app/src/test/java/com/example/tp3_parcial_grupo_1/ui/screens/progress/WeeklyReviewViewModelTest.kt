package com.example.tp3_parcial_grupo_1.ui.screens.progress

import org.junit.Assert.assertEquals
import org.junit.Test

class WeeklyReviewViewModelTest {

    @Test
    fun `given the demo week, when opening the review, then it shows three wins and three recommendations`() {
        val review = WeeklyReviewViewModel().uiState.value

        assertEquals(3, review.wins.size)
        assertEquals(3, review.recommendations.size)
    }

    @Test
    fun `given the demo week, when opening the review, then the summary shows workouts, steps and protein`() {
        val review = WeeklyReviewViewModel().uiState.value

        assertEquals(listOf("WORKOUTS", "AVG STEPS", "PROTEIN"), review.stats.map { it.label })
    }
}
