package com.example.tp3_parcial_grupo_1.ui.screens.coach

import com.example.tp3_parcial_grupo_1.data.demo.CoachDemoData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.TestScope
import kotlinx.coroutines.test.advanceTimeBy
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runCurrent
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class CoachViewModelTest {

    private val dispatcher = StandardTestDispatcher()
    private val scope = TestScope(dispatcher)

    @Before
    fun setUp() = Dispatchers.setMain(dispatcher)

    @After
    fun tearDown() = Dispatchers.resetMain()

    private fun listeningDurationMs(): Long {
        val words = CoachDemoData.lunch.question.split(" ").size
        return CoachViewModel.ListeningStartDelayMs + words * CoachViewModel.WordDelayMs +
            CoachViewModel.ListeningEndDelayMs
    }

    @Test
    fun startsIdleWithSuggestions() {
        val state = CoachViewModel().uiState.value
        assertTrue(state is CoachUiState.Idle)
        assertEquals(CoachDemoData.suggestedQuestions, (state as CoachUiState.Idle).suggestions)
    }

    @Test
    fun micGoesThroughListeningProcessingAndReply() = scope.runTest {
        val viewModel = CoachViewModel()

        viewModel.onMicClick()
        runCurrent()
        assertTrue(viewModel.uiState.value is CoachUiState.Listening)

        advanceTimeBy(listeningDurationMs())
        runCurrent()
        val processing = viewModel.uiState.value as CoachUiState.Processing
        assertEquals(CoachDemoData.lunch.question, processing.transcript)

        advanceTimeBy(CoachViewModel.ProcessingDelayMs)
        runCurrent()
        val reply = viewModel.uiState.value as CoachUiState.Reply
        assertEquals(CoachDemoData.lunch.reply, reply.reply)
        assertEquals(0, reply.elapsedSeconds)
    }

    @Test
    fun listeningTranscriptGrowsWordByWord() = scope.runTest {
        val viewModel = CoachViewModel()
        viewModel.onMicClick()

        advanceTimeBy(CoachViewModel.ListeningStartDelayMs + 1)
        runCurrent()
        val first = viewModel.uiState.value as CoachUiState.Listening
        assertEquals(CoachDemoData.lunch.question.split(" ").first(), first.transcript)
    }

    @Test
    fun suggestionSkipsListening() = scope.runTest {
        val viewModel = CoachViewModel()
        val question = CoachDemoData.suggestedQuestions[2]

        viewModel.onSuggestionClick(question)
        runCurrent()

        val state = viewModel.uiState.value as CoachUiState.Processing
        assertEquals(question, state.transcript)
    }

    @Test
    fun pauseStopsProgressAndResumeContinues() = scope.runTest {
        val viewModel = CoachViewModel()
        viewModel.onSuggestionClick(CoachDemoData.suggestedQuestions[0])
        advanceTimeBy(CoachViewModel.ProcessingDelayMs + 3 * CoachViewModel.SecondMs)
        runCurrent()
        val playing = viewModel.uiState.value as CoachUiState.Reply

        viewModel.onTogglePause()
        advanceTimeBy(5 * CoachViewModel.SecondMs)
        runCurrent()
        val paused = viewModel.uiState.value as CoachUiState.Reply
        assertTrue(paused.isPaused)
        assertEquals(playing.elapsedSeconds, paused.elapsedSeconds)

        viewModel.onTogglePause()
        advanceTimeBy(CoachViewModel.SecondMs)
        runCurrent()
        val resumed = viewModel.uiState.value as CoachUiState.Reply
        assertFalse(resumed.isPaused)
        assertEquals(paused.elapsedSeconds + 1, resumed.elapsedSeconds)
    }

    @Test
    fun replyEndsInIdleWithUpdatedLastConversation() = scope.runTest {
        val viewModel = CoachViewModel()
        val question = CoachDemoData.suggestedQuestions[3]
        viewModel.onSuggestionClick(question)

        advanceUntilIdle()

        val idle = viewModel.uiState.value as CoachUiState.Idle
        assertEquals(question, idle.lastConversation?.text)
    }

    @Test
    fun backReturnsToIdleAndThenLeaves() = scope.runTest {
        val viewModel = CoachViewModel()
        viewModel.onMicClick()
        runCurrent()

        assertTrue(viewModel.onBack())
        assertTrue(viewModel.uiState.value is CoachUiState.Idle)
        assertFalse(viewModel.onBack())

        advanceUntilIdle()
        assertTrue(viewModel.uiState.value is CoachUiState.Idle)
    }
}
