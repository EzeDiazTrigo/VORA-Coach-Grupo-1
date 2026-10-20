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

    val suggestedQuestions: List<String> = suggestions.map { it.question }

    val lastConversation = LastConversation(
        text = "Armame un entrenamiento de 30 minutos.",
        timeAgo = "hace 2 horas"
    )

    fun exchangeFor(question: String): CoachExchange =
        suggestions.firstOrNull { it.question == question } ?: lunch
}
