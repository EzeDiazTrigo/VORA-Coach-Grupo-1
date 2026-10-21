package com.example.tp3_parcial_grupo_1.ui.screens.coach

import com.example.tp3_parcial_grupo_1.data.demo.CoachAuthor
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
    fun replyEndsInConversationWithTheNewExchange() = scope.runTest {
        val viewModel = CoachViewModel()
        val question = CoachDemoData.suggestedQuestions[3]
        viewModel.onSuggestionClick(question)

        advanceUntilIdle()

        val conversation = viewModel.uiState.value as CoachUiState.Conversation
        val lastTwo = conversation.messages.takeLast(2)
        assertEquals(CoachAuthor.User, lastTwo[0].author)
        assertEquals(question, lastTwo[0].text)
        assertEquals(CoachAuthor.Vora, lastTwo[1].author)
        assertEquals(CoachDemoData.conversation.size + 2, conversation.messages.size)
        assertEquals(conversation.messages.size, conversation.messages.map { it.id }.toSet().size)

        viewModel.onBack()
        assertEquals(question, (viewModel.uiState.value as CoachUiState.Idle).lastConversation?.text)
    }

    @Test
    fun typeInsteadOpensDemoConversation() {
        val viewModel = CoachViewModel()
        viewModel.onTypeInstead()

        val conversation = viewModel.uiState.value as CoachUiState.Conversation
        assertEquals(CoachDemoData.conversation, conversation.messages)
        assertFalse(conversation.canSend)
    }

    @Test
    fun sendingAddsUserMessageThenVoraReply() = scope.runTest {
        val viewModel = CoachViewModel()
        viewModel.onTypeInstead()
        viewModel.onDraftChange("¿Cómo voy?")
        assertTrue((viewModel.uiState.value as CoachUiState.Conversation).canSend)

        viewModel.onSend()
        runCurrent()
        val typing = viewModel.uiState.value as CoachUiState.Conversation
        assertTrue(typing.isVoraTyping)
        assertEquals("", typing.draft)
        assertEquals("¿Cómo voy?", typing.messages.last().text)

        advanceTimeBy(CoachViewModel.TypingDelayMs)
        runCurrent()
        val replied = viewModel.uiState.value as CoachUiState.Conversation
        assertFalse(replied.isVoraTyping)
        assertEquals(CoachAuthor.Vora, replied.messages.last().author)
        assertEquals(CoachDemoData.replyFor("¿Cómo voy?"), replied.messages.last().text)
    }

    @Test
    fun blankDraftIsNotSent() {
        val viewModel = CoachViewModel()
        viewModel.onTypeInstead()
        viewModel.onDraftChange("   ")
        viewModel.onSend()

        val conversation = viewModel.uiState.value as CoachUiState.Conversation
        assertEquals(CoachDemoData.conversation.size, conversation.messages.size)
    }

    @Test
    fun finishingBeforeSpeakingShowsMicErrorAndRetryListensAgain() = scope.runTest {
        val viewModel = CoachViewModel()
        viewModel.onMicClick()
        runCurrent()

        viewModel.onFinishListening()
        assertEquals(CoachUiState.MicError, viewModel.uiState.value)

        viewModel.onRetry()
        runCurrent()
        assertTrue(viewModel.uiState.value is CoachUiState.Listening)

        viewModel.onBack()
        viewModel.onMicClick()
        runCurrent()
        viewModel.onFinishListening()
        viewModel.onDismissError()
        assertTrue(viewModel.uiState.value is CoachUiState.Idle)
    }

    @Test
    fun listeningToAMessageDoesNotDuplicateIt() = scope.runTest {
        val viewModel = CoachViewModel()
        viewModel.onTypeInstead()
        val voraMessage = CoachDemoData.conversation.first { it.audioSeconds != null }

        viewModel.onListenToMessage(voraMessage.id)
        runCurrent()
        assertEquals(voraMessage.text, (viewModel.uiState.value as CoachUiState.Reply).reply)

        advanceUntilIdle()
        val conversation = viewModel.uiState.value as CoachUiState.Conversation
        assertEquals(CoachDemoData.conversation.size, conversation.messages.size)
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
