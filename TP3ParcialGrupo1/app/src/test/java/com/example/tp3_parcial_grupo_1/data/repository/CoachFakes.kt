package com.example.tp3_parcial_grupo_1.data.repository

import com.example.tp3_parcial_grupo_1.data.local.dao.CoachMessageDao
import com.example.tp3_parcial_grupo_1.data.local.entity.CoachMessageEntity
import com.example.tp3_parcial_grupo_1.data.remote.api.CoachApi
import com.example.tp3_parcial_grupo_1.data.remote.dto.CoachReplyDto
import com.example.tp3_parcial_grupo_1.data.remote.dto.CoachRequestDto
import com.example.tp3_parcial_grupo_1.data.remote.dto.DataResponse
import java.io.IOException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import okhttp3.ResponseBody.Companion.toResponseBody
import retrofit2.Response

// API falsa: responde según el escenario elegido, como los headers X-Mock-Scenario del mock.
class FakeCoachApi : CoachApi {
    enum class Scenario { Normal, Error, Empty, Offline }

    var scenario = Scenario.Normal
    var replyText = "Demo reply from VORA."
    val requests = mutableListOf<CoachRequestDto>()

    override suspend fun sendMessage(request: CoachRequestDto): Response<DataResponse<CoachReplyDto>> {
        requests += request
        return when (scenario) {
            Scenario.Normal -> Response.success(
                DataResponse(CoachReplyDto("assistant", replyText, "general", true))
            )
            Scenario.Empty -> Response.success(DataResponse(CoachReplyDto("assistant", "", "general", true)))
            Scenario.Error -> Response.error(503, "{}".toResponseBody())
            Scenario.Offline -> throw IOException("No network")
        }
    }
}

// DAO en memoria con el mismo orden que la consulta real (createdAt, id).
class FakeCoachMessageDao : CoachMessageDao {
    private val messages = MutableStateFlow<List<CoachMessageEntity>>(emptyList())
    private var nextId = 1L

    val stored: List<CoachMessageEntity> get() = messages.value

    override fun observeAll(): Flow<List<CoachMessageEntity>> = messages

    override suspend fun insert(message: CoachMessageEntity): Long {
        val id = nextId++
        messages.value = (messages.value + message.copy(id = id)).sortedWith(compareBy({ it.createdAt }, { it.id }))
        return id
    }
}
