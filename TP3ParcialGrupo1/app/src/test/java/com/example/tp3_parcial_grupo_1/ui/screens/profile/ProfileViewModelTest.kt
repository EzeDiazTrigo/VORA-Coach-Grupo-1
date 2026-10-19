package com.example.tp3_parcial_grupo_1.ui.screens.profile

import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class ProfileViewModelTest {
    private val viewModel = ProfileViewModel()

    @Test
    fun `starts with the demo profile and no message`() {
        val state = viewModel.uiState.value

        assertEquals("Alex Morgan", state.fullName)
        assertNull(state.message)
    }

    @Test
    fun `demo notice shows the demo mode message`() {
        viewModel.onDemoNoticeClick()

        assertEquals(Strings.DemoMode, viewModel.uiState.value.message)
    }

    @Test
    fun `message shown clears the message`() {
        viewModel.onDemoNoticeClick()

        viewModel.onMessageShown()

        assertNull(viewModel.uiState.value.message)
    }
}
