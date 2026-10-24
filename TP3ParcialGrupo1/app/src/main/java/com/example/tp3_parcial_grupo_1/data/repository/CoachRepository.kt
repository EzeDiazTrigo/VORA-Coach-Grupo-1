package com.example.tp3_parcial_grupo_1.data.repository

import com.example.tp3_parcial_grupo_1.data.local.dao.CoachMessageDao
import com.example.tp3_parcial_grupo_1.data.local.entity.CoachMessageEntity
import com.example.tp3_parcial_grupo_1.data.remote.api.CoachApi
import com.example.tp3_parcial_grupo_1.data.remote.dto.CoachRequestDto
import com.example.tp3_parcial_grupo_1.model.CoachAuthor
import com.example.tp3_parcial_grupo_1.model.CoachMessage
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject
import javax.inject.Singleton
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

/** El servidor respondió con un código de error (por ejemplo 503 del escenario «error»). */
class CoachServiceException(val code: Int) : Exception("Coach service error $code")

/** La respuesta llegó sin texto utilizable (escenario «malformed» o cuerpo vacío). */
class CoachMalformedReplyException : Exception("Coach reply without text")

/**
 * Única fuente del chat con VORA: el historial vive en Room y las respuestas escritas vienen de
 * POST /v1/coach/messages. Un fallo de red no borra el historial ya guardado.
 */
@Singleton
class CoachRepository @Inject constructor(
    private val api: CoachApi,
    private val dao: CoachMessageDao
) {
    fun observeMessages(): Flow<List<CoachMessage>> =
        dao.observeAll().map { messages -> messages.map { it.toModel() } }

    suspend fun addUserMessage(text: String) {
        dao.insert(newEntity(CoachAuthor.User, text))
    }

    /** Pide la respuesta de VORA para [question] y la guarda; el mensaje del usuario ya está en Room. */
    suspend fun requestReply(question: String): Result<CoachMessage> {
        return try {
            val response = api.sendMessage(CoachRequestDto(text = question))
            if (!response.isSuccessful) throw CoachServiceException(response.code())
            val text = response.body()?.data?.text?.takeIf { it.isNotBlank() }
                ?: throw CoachMalformedReplyException()
            val entity = newEntity(CoachAuthor.Vora, text, audioSecondsFor(text))
            val id = dao.insert(entity)
            Result.success(entity.copy(id = id).toModel())
        } catch (e: Exception) {
            if (e is CancellationException) throw e
            Result.failure(e)
        }
    }

    /** Guarda un intercambio por voz (simulado, sin red) para que aparezca en el chat. */
    suspend fun saveExchange(question: String, reply: String, audioSeconds: Int) {
        dao.insert(newEntity(CoachAuthor.User, question))
        dao.insert(newEntity(CoachAuthor.Vora, reply, audioSeconds))
    }

    private fun newEntity(author: CoachAuthor, text: String, audioSeconds: Int? = null) = CoachMessageEntity(
        author = author.toEntityValue(),
        text = text,
        createdAt = System.currentTimeMillis(),
        audioSeconds = audioSeconds
    )

    // Duración estimada del audio simulado: ~2,5 palabras por segundo.
    private fun audioSecondsFor(text: String): Int = (text.split(" ").size / 2.5).toInt().coerceIn(3, 60)
}

private const val AuthorUser = "user"
private const val AuthorVora = "vora"

private fun CoachAuthor.toEntityValue(): String = if (this == CoachAuthor.User) AuthorUser else AuthorVora

fun CoachMessageEntity.toModel(): CoachMessage = CoachMessage(
    id = id,
    author = if (author == AuthorUser) CoachAuthor.User else CoachAuthor.Vora,
    text = text,
    time = SimpleDateFormat("HH:mm", Locale.getDefault()).format(Date(createdAt)),
    audioSeconds = audioSeconds
)
