package com.example.tp3_parcial_grupo_1.ui.screens.profile

import com.example.tp3_parcial_grupo_1.data.repository.ProfileRepository
import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import com.example.tp3_parcial_grupo_1.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class ProfileViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = ProfileRepository()
    // Lazy: el ViewModel debe crearse después de que la regla fije Dispatchers.Main.
    private val viewModel by lazy { ProfileViewModel(repository) }

    @Test
    fun `starts with the demo profile and no message`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.uiState.collect {} }

        assertEquals("Alex Morgan", viewModel.uiState.value.fullName)
        assertNull(viewModel.uiState.value.message)
    }

    @Test
    fun `demo notice shows the demo mode message`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.uiState.collect {} }

        viewModel.onDemoNoticeClick()

        assertEquals(Strings.DemoMode, viewModel.uiState.value.message)
    }

    @Test
    fun `message shown clears the message`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.uiState.collect {} }
        viewModel.onDemoNoticeClick()

        viewModel.onMessageShown()

        assertNull(viewModel.uiState.value.message)
    }

    @Test
    fun `reflects changes made to the shared profile`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.uiState.collect {} }

        repository.update { it.copy(firstName = "Sam", trainingDaysPerWeek = 5) }

        assertEquals("Sam Morgan", viewModel.uiState.value.fullName)
        assertEquals("5 days/week", viewModel.uiState.value.training)
    }
}
