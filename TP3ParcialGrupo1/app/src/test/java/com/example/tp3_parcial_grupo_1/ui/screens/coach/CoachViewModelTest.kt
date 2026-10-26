package com.example.tp3_parcial_grupo_1.ui.screens.coach

import com.example.tp3_parcial_grupo_1.data.demo.CoachDemoData
import com.example.tp3_parcial_grupo_1.data.repository.CoachRepository
import com.example.tp3_parcial_grupo_1.data.repository.FakeCoachApi
import com.example.tp3_parcial_grupo_1.data.repository.FakeCoachMessageDao
import com.example.tp3_parcial_grupo_1.model.CoachAuthor
import com.example.tp3_parcial_grupo_1.ui.strings.CoachStrings
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
    private val api = FakeCoachApi()
    private val dao = FakeCoachMessageDao()

    private fun newViewModel() = CoachViewModel(CoachRepository(api, dao))

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
        val state = newViewModel().uiState.value
        assertTrue(state is CoachUiState.Idle)
        assertEquals(CoachDemoData.suggestedQuestions, (state as CoachUiState.Idle).suggestions)
    }

    @Test
    fun micGoesThroughListeningProcessingAndReply() = scope.runTest {
        val viewModel = newViewModel()

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
        val viewModel = newViewModel()
        viewModel.onMicClick()

        advanceTimeBy(CoachViewModel.ListeningStartDelayMs + 1)
        runCurrent()
        val first = viewModel.uiState.value as CoachUiState.Listening
        assertEquals(CoachDemoData.lunch.question.split(" ").first(), first.transcript)
    }

    @Test
    fun suggestionSkipsListening() = scope.runTest {
        val viewModel = newViewModel()
        val question = CoachDemoData.suggestedQuestions[2]

        viewModel.onSuggestionClick(question)
        runCurrent()

        val state = viewModel.uiState.value as CoachUiState.Processing
        assertEquals(question, state.transcript)
    }

    @Test
    fun pauseStopsProgressAndResumeContinues() = scope.runTest {
        val viewModel = newViewModel()
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
    fun replyEndsInConversationWithTheExchangeSavedInRoom() = scope.runTest {
        val viewModel = newViewModel()
        val question = CoachDemoData.suggestedQuestions[3]
        viewModel.onSuggestionClick(question)

        advanceUntilIdle()

        val conversation = viewModel.uiState.value as CoachUiState.Conversation
        assertEquals(listOf(CoachAuthor.User, CoachAuthor.Vora), conversation.messages.map { it.author })
        assertEquals(question, conversation.messages.first().text)
        assertEquals(2, dao.stored.size)
        assertTrue(api.requests.isEmpty())

        viewModel.onBack()
        assertEquals(question, (viewModel.uiState.value as CoachUiState.Idle).lastConversation?.text)
    }

    @Test
    fun typeInsteadOpensDemoConversation() {
        val viewModel = newViewModel()
        viewModel.onTypeInstead()

        val conversation = viewModel.uiState.value as CoachUiState.Conversation
        assertEquals(CoachDemoData.conversation, conversation.messages)
        assertFalse(conversation.canSend)
    }

    @Test
    fun sendingStoresTheMessageAndTheRemoteReply() = scope.runTest {
        val viewModel = newViewModel()
        viewModel.onTypeInstead()
        viewModel.onDraftChange("How am I doing?")
        assertTrue((viewModel.uiState.value as CoachUiState.Conversation).canSend)

        viewModel.onSend()
        advanceUntilIdle()

        val replied = viewModel.uiState.value as CoachUiState.Conversation
        assertFalse(replied.isVoraTyping)
        assertEquals("", replied.draft)
        assertEquals(listOf("How am I doing?", api.replyText), replied.messages.map { it.text })
        assertEquals("How am I doing?", api.requests.single().text)
        assertEquals(null, replied.sendError)
    }

    @Test
    fun failedSendKeepsTheMessageAndRetryDoesNotDuplicateIt() = scope.runTest {
        val viewModel = newViewModel()
        viewModel.onTypeInstead()
        api.scenario = FakeCoachApi.Scenario.Offline
        viewModel.onDraftChange("Plan my week")
        viewModel.onSend()
        advanceUntilIdle()

        val failed = viewModel.uiState.value as CoachUiState.Conversation
        assertEquals(CoachStrings.SendErrorOffline, failed.sendError)
        assertEquals(listOf("Plan my week"), failed.messages.map { it.text })

        api.scenario = FakeCoachApi.Scenario.Normal
        viewModel.onRetrySend()
        advanceUntilIdle()

        val recovered = viewModel.uiState.value as CoachUiState.Conversation
        assertEquals(null, recovered.sendError)
        assertEquals(listOf("Plan my week", api.replyText), recovered.messages.map { it.text })
        assertEquals(2, api.requests.size)
    }

    @Test
    fun serverErrorShowsItsCode() = scope.runTest {
        val viewModel = newViewModel()
        viewModel.onTypeInstead()
        api.scenario = FakeCoachApi.Scenario.Error
        viewModel.onDraftChange("Hello")
        viewModel.onSend()
        advanceUntilIdle()

        assertEquals(CoachStrings.sendErrorServer(503), (viewModel.uiState.value as CoachUiState.Conversation).sendError)
    }

    @Test
    fun typedQuestionMatchesSuggestionIgnoringAccentsAndPunctuation() {
        assertEquals(
            CoachDemoData.exchangeFor("How am I doing?").reply,
            CoachDemoData.exchangeFor("how am i doing").reply
        )
        assertEquals(CoachDemoData.lunch, CoachDemoData.exchangeFor("hola"))
    }

    @Test
    fun blankDraftIsNotSent() {
        val viewModel = newViewModel()
        viewModel.onTypeInstead()
        viewModel.onDraftChange("   ")
        viewModel.onSend()

        val conversation = viewModel.uiState.value as CoachUiState.Conversation
        assertEquals(CoachDemoData.conversation.size, conversation.messages.size)
    }

    @Test
    fun finishingBeforeSpeakingShowsMicErrorAndRetryListensAgain() = scope.runTest {
        val viewModel = newViewModel()
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
        val viewModel = newViewModel()
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
        val viewModel = newViewModel()
        viewModel.onMicClick()
        runCurrent()

        assertTrue(viewModel.onBack())
        assertTrue(viewModel.uiState.value is CoachUiState.Idle)
        assertFalse(viewModel.onBack())

        advanceUntilIdle()
        assertTrue(viewModel.uiState.value is CoachUiState.Idle)
    }
}
