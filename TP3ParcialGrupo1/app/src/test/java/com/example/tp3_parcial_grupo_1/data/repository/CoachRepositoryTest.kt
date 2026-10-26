package com.example.tp3_parcial_grupo_1.data.repository

import com.example.tp3_parcial_grupo_1.model.CoachAuthor
import java.io.IOException
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class CoachRepositoryTest {

    private val api = FakeCoachApi()
    private val dao = FakeCoachMessageDao()
    private val repository = CoachRepository(api, dao)

    @Test
    fun `given a normal reply, when requesting it, then it is stored after the user message`() = runTest {
        repository.addUserMessage("How am I doing?")

        val result = repository.requestReply("How am I doing?")

        assertTrue(result.isSuccess)
        assertEquals("How am I doing?", api.requests.single().text)
        val history = repository.observeMessages().first()
        assertEquals(listOf(CoachAuthor.User, CoachAuthor.Vora), history.map { it.author })
        assertEquals(api.replyText, history.last().text)
        assertTrue((history.last().audioSeconds ?: 0) > 0)
    }

    @Test
    fun `given a 503, when requesting a reply, then it fails and keeps the history`() = runTest {
        repository.saveExchange("Earlier question", "Earlier answer", 10)
        repository.addUserMessage("New question")
        api.scenario = FakeCoachApi.Scenario.Error

        val result = repository.requestReply("New question")

        assertEquals(503, (result.exceptionOrNull() as CoachServiceException).code)
        assertEquals(3, dao.stored.size)
    }

    @Test
    fun `given no network, when requesting a reply, then it fails without touching the history`() = runTest {
        repository.addUserMessage("Hello")
        api.scenario = FakeCoachApi.Scenario.Offline

        val result = repository.requestReply("Hello")

        assertTrue(result.exceptionOrNull() is IOException)
        assertEquals(1, dao.stored.size)
    }

    @Test
    fun `given an empty reply, when requesting it, then it is reported as malformed`() = runTest {
        api.scenario = FakeCoachApi.Scenario.Empty

        val result = repository.requestReply("Hello")

        assertTrue(result.exceptionOrNull() is CoachMalformedReplyException)
        assertTrue(dao.stored.isEmpty())
    }

    @Test
    fun `given a voice exchange, when saving it, then both messages are mapped in order`() = runTest {
        repository.saveExchange("I had lunch", "Sounds balanced", 22)

        val history = repository.observeMessages().first()
        assertEquals(listOf("I had lunch", "Sounds balanced"), history.map { it.text })
        assertEquals(22, history.last().audioSeconds)
        assertEquals(null, history.first().audioSeconds)
    }
}
