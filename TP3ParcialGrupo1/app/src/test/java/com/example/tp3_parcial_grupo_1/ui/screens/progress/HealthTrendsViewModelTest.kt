package com.example.tp3_parcial_grupo_1.ui.screens.progress

import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class HealthTrendsViewModelTest {

    @Test
    fun `given no message, when tapping more, then it shows the demo mode message`() {
        val viewModel = HealthTrendsViewModel()

        viewModel.onMoreClick()

        assertEquals(Strings.DemoMode, viewModel.uiState.value.message)
    }

    @Test
    fun `given the demo message, when it was shown, then the message is cleared`() {
        val viewModel = HealthTrendsViewModel()
        viewModel.onMoreClick()

        viewModel.onMessageShown()

        assertNull(viewModel.uiState.value.message)
    }
}
