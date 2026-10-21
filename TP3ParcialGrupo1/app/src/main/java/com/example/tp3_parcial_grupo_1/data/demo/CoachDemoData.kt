package com.example.tp3_parcial_grupo_1.data.demo

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
        question = "Almorcé pollo, arroz y algunas verduras.",
        processingStep = "Revisando tu objetivo de nutrición…",
        reply = "Suena a un almuerzo equilibrado. Calculo unas 520 calorías con aproximadamente 38 gramos de proteína.",
        replySeconds = 22
    )

    private val suggestions = listOf(
        CoachExchange(
            question = "Empezar mi entrenamiento",
            processingStep = "Buscando tu plan de hoy…",
            reply = "Hoy te toca Upper Body Strength: 42 minutos y 5 ejercicios. ¿Arrancamos con la entrada en calor?",
            replySeconds = 14
        ),
        CoachExchange(
            question = "Registrar mi desayuno",
            processingStep = "Preparando el registro de comidas…",
            reply = "Contame qué desayunaste y lo agrego a tus comidas de hoy.",
            replySeconds = 9
        ),
        CoachExchange(
            question = "¿Qué ceno hoy?",
            processingStep = "Revisando tu objetivo de proteínas…",
            reply = "Te faltan 36 g de proteína. Te recomiendo pechuga grillada con verduras asadas y una porción de yogur griego.",
            replySeconds = 18
        ),
        CoachExchange(
            question = "¿Cómo voy?",
            processingStep = "Analizando tu semana…",
            reply = "Vas muy bien: completaste 4 entrenamientos esta semana y estás a 120 calorías de tu objetivo diario.",
            replySeconds = 16
        )
    )

    // Respuesta para preguntas escritas que no están en la demo.
    const val GenericReply =
        "Estoy en modo demostración, así que respondo con ejemplos. Probá con alguna de las preguntas sugeridas."

    val suggestedQuestions: List<String> = suggestions.map { it.question }

    val lastConversation = LastConversation(
        text = "Armame un entrenamiento de 30 minutos.",
        timeAgo = "hace 2 horas"
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

    fun exchangeFor(question: String): CoachExchange =
        suggestions.firstOrNull { it.question.equals(question.trim(), ignoreCase = true) } ?: lunch

    fun replyFor(question: String): String =
        suggestions.firstOrNull { it.question.equals(question.trim(), ignoreCase = true) }?.reply ?: GenericReply
}
