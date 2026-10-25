package com.example.tp3_parcial_grupo_1.ui.screens.profile

import com.example.tp3_parcial_grupo_1.data.repository.ProfileRepository
import com.example.tp3_parcial_grupo_1.model.CoachingStyle
import com.example.tp3_parcial_grupo_1.model.ResponseStyle
import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import com.example.tp3_parcial_grupo_1.util.MainDispatcherRule
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class VoiceSettingsViewModelTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    private val repository = ProfileRepository()

    // Lazy: el ViewModel debe crearse después de que la regla fije Dispatchers.Main.
    private val viewModel by lazy { VoiceSettingsViewModel(repository) }

    @Test
    fun `toggling a setting applies it immediately`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.uiState.collect {} }

        viewModel.onSpeakResponsesChange(false)

        assertFalse(viewModel.uiState.value.speakResponses)
        assertFalse(repository.voiceSettings.value.speakResponses)
    }

    @Test
    fun `response style is updated`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.uiState.collect {} }

        viewModel.onResponseStyleSelected(ResponseStyle.Detailed)

        assertEquals(ResponseStyle.Detailed, viewModel.uiState.value.responseStyle)
    }

    @Test
    fun `coaching style is shared with the profile`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.uiState.collect {} }

        viewModel.onCoachingStyleSelected(CoachingStyle.Direct)

        assertEquals(CoachingStyle.Direct, repository.profile.value.coachingStyle)
        assertEquals(CoachingStyle.Direct, viewModel.uiState.value.coachingStyle)
    }

    @Test
    fun `preview shows the demo message until it is shown`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.uiState.collect {} }

        viewModel.onPreviewClick()
        assertEquals(Strings.DemoMode, viewModel.uiState.value.message)

        viewModel.onMessageShown()
        assertNull(viewModel.uiState.value.message)
    }

    @Test
    fun `privacy shows the demo message`() = runTest {
        backgroundScope.launch(UnconfinedTestDispatcher(testScheduler)) { viewModel.uiState.collect {} }

        viewModel.onPrivacyClick()

        assertEquals(Strings.DemoMode, viewModel.uiState.value.message)
    }
}
