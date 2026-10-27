package com.example.tp3_parcial_grupo_1.data.demo

import com.example.tp3_parcial_grupo_1.model.CoachAuthor
import com.example.tp3_parcial_grupo_1.model.CoachMessage
import java.text.Normalizer

// Datos de demostración del Asistente VORA: no hay voz ni IA real (NAV05).
data class CoachExchange(
    val question: String,
    val processingStep: String,
    val reply: String,
    val replySeconds: Int
)

data class LastConversation(val text: String, val timeAgo: String)

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
            question = "What should I eat tonight?",
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


    val suggestedQuestions: List<String> = suggestions.map { it.question }

    val lastConversation = LastConversation(
        text = "Create a 30-minute workout for me.",
        timeAgo = "2 hours ago"
    )

    const val ConversationDate = "TODAY AT 12:40 PM"

    // La conversación de ejemplo del frame «Voice / 05 · Conversation».
    val conversation = listOf(
        CoachMessage(1, CoachAuthor.User, "What should I eat for dinner?", "12:41"),
        CoachMessage(
            2, CoachAuthor.Vora,
            "You're 36 g short on protein today. I'd suggest grilled chicken breast with roasted seasonal vegetables and a serving of Greek yogurt.",
            "12:41",
            audioSeconds = 14
        ),
        CoachMessage(3, CoachAuthor.User, "Can you make it vegetarian?", "12:42"),
        CoachMessage(
            4, CoachAuthor.Vora,
            "Sure. Try grilled tofu, roasted vegetables, quinoa and Greek yogurt.",
            "12:43"
        )
    )

    fun exchangeFor(question: String): CoachExchange = find(question) ?: lunch

    // «how am i doing» encuentra «How am I doing?»: se comparan sin tildes, signos ni mayúsculas.
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
