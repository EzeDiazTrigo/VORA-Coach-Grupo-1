package com.example.tp3_parcial_grupo_1.data.demo

import java.text.Normalizer

// Datos de demostración del Asistente VORA: no hay voz ni IA real (NAV05).
data class CoachExchange(
    val question: String,
    val processingStep: String,
    val reply: String,
    val replySeconds: Int
)

data class LastConversation(val text: String, val timeAgo: String)

enum class CoachAuthor { User, Vora }

data class CoachMessage(
    val id: Long,
    val author: CoachAuthor,
    val text: String,
    val time: String,
    val audioSeconds: Int? = null
)

object CoachDemoData {
    val lunch = CoachExchange(
        question = "I had chicken, rice and some vegetables for lunch.",
        processingStep = "Checking your nutrition goal…",
        reply = "That sounds like a balanced lunch. I estimate about 520 calories with roughly 38 grams of protein.",
        replySeconds = 22
    )

    private val suggestions = listOf(
        CoachExchange(
            question = "Start my workout",
            processingStep = "Finding today's plan…",
            reply = "Today is Upper Body Strength: 42 minutes and 5 exercises. Shall we start with the warm-up?",
            replySeconds = 14
        ),
        CoachExchange(
            question = "Log my breakfast",
            processingStep = "Opening your meal log…",
            reply = "Tell me what you had for breakfast and I'll add it to today's meals.",
            replySeconds = 9
        ),
        CoachExchange(
            question = "What should I eat for dinner?",
            processingStep = "Checking your protein goal…",
            reply = "You're 36 g short on protein. I'd suggest grilled chicken breast with roasted vegetables and a serving of Greek yogurt.",
            replySeconds = 18
        ),
        CoachExchange(
            question = "How am I doing?",
            processingStep = "Analyzing your week…",
            reply = "You're doing great: you completed 4 workouts this week and you're 120 calories away from your daily goal.",
            replySeconds = 16
        )
    )

    // Respuesta para preguntas escritas que no están en la demo.
    const val GenericReply =
        "Estoy en modo demostración, así que respondo con ejemplos. Probá con alguna de las preguntas sugeridas."

    val suggestedQuestions: List<String> = suggestions.map { it.question }

    val lastConversation = LastConversation(
        text = "Build me a 30-minute workout.",
        timeAgo = "2 hours ago"
    )

    const val ConversationDate = "HOY A LAS 12:40"

    // La conversación de ejemplo del frame «Voice / 05 · Conversation».
    val conversation = listOf(
        CoachMessage(1, CoachAuthor.User, "¿Qué ceno hoy?", "12:41"),
        CoachMessage(
            2, CoachAuthor.Vora,
            "Hoy te faltan 36 g de proteína. Te recomiendo pechuga grillada con verduras de estación asadas y una porción de yogur griego.",
            "12:41",
            audioSeconds = 14
        ),
        CoachMessage(3, CoachAuthor.User, "¿Me lo pasás a vegetariano?", "12:42"),
        CoachMessage(
            4, CoachAuthor.Vora,
            "Claro. Probá con tofu grillado, verduras asadas, quinoa y yogur griego.",
            "12:43"
        )
    )

    fun exchangeFor(question: String): CoachExchange = find(question) ?: lunch

    fun replyFor(question: String): String = find(question)?.reply ?: GenericReply

    // «como voy» encuentra «¿Cómo voy?»: se comparan sin tildes, signos ni mayúsculas.
    private fun find(question: String): CoachExchange? {
        val key = normalize(question)
        return suggestions.firstOrNull { normalize(it.question) == key }
    }

    private fun normalize(text: String): String =
        Normalizer.normalize(text, Normalizer.Form.NFD)
            .replace(Regex("\\p{M}+"), "")
            .lowercase()
            .replace(Regex("[^a-z0-9 ]"), "")
            .trim()
}
