package com.example.tp3_parcial_grupo_1.ui.screens.profile

import com.example.tp3_parcial_grupo_1.data.repository.ProfileRepository
import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
import org.junit.Test

class SettingsViewModelTest {
    private val viewModel = SettingsViewModel(ProfileRepository())

    @Test
    fun `shows the profile email`() {
        assertEquals("alex@morgan.com", viewModel.uiState.value.email)
    }

    @Test
    fun `log out opens and dismisses the confirmation dialog`() {
        viewModel.onLogOutClick()
        assertTrue(viewModel.uiState.value.showLogOutDialog)

        viewModel.onLogOutDismissed()
        assertFalse(viewModel.uiState.value.showLogOutDialog)
    }

    @Test
    fun `rows without a destination show the demo message`() {
        viewModel.onDemoNoticeClick()
        assertEquals(Strings.DemoMode, viewModel.uiState.value.message)

        viewModel.onMessageShown()
        assertNull(viewModel.uiState.value.message)
    }
}
