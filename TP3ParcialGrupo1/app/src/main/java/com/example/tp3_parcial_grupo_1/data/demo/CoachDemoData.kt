package com.example.tp3_parcial_grupo_1.data.demo

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

    val suggestedQuestions: List<String> = suggestions.map { it.question }

    val lastConversation = LastConversation(
        text = "Build me a 30-minute workout.",
        timeAgo = "2 hours ago"
    )

    fun exchangeFor(question: String): CoachExchange =
        suggestions.firstOrNull { it.question == question } ?: lunch
}
