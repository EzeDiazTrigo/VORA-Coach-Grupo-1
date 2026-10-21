package com.example.tp3_parcial_grupo_1.ui.strings

object CoachStrings {
    const val Title = "VORA Coach"
    const val Online = "EN LÍNEA"
    const val IdleHeadline = "¿En qué te ayudo?"
    const val IdleSubtitle = "Hablá con naturalidad sobre tu entrenamiento, tu nutrición o tu progreso."
    const val SuggestedQuestions = "PREGUNTAS SUGERIDAS"
    const val LastConversation = "ÚLTIMA CONVERSACIÓN"
    const val TapToTalk = "TOCÁ PARA HABLAR"
    const val TypeInstead = "Prefiero escribir"

    const val ListeningTitle = "Escuchando"
    const val LiveAudio = "AUDIO EN VIVO"
    const val LiveTranscript = "TRANSCRIPCIÓN EN VIVO"
    const val ListeningHint = "Hablá con naturalidad. Te escucho."
    const val StopListening = "Terminar de hablar"

    const val ProcessingTitle = "Procesando"
    const val Thinking = "PENSANDO…"

    const val SpeakingTitle = "VORA responde"
    const val Speaking = "HABLANDO"
    const val TapToInterrupt = "Tocá para interrumpir"
    const val Replay = "Repetir respuesta"
    const val Pause = "Pausar"
    const val Resume = "Reanudar"
    const val Stop = "Detener"

    const val Microphone = "Micrófono"

    const val AskPlaceholder = "Preguntale a VORA…"
    const val Attach = "Adjuntar"
    const val Send = "Enviar"
    const val Talk = "Hablar"
    const val VoraTyping = "VORA está escribiendo…"
    const val YouSaid = "Vos"
    const val VoraSaid = "VORA"

    const val ErrorTitle = "Alerta del sistema"
    const val ErrorHeadline = "Perdón, no te entendí"
    const val ErrorSubtitle = "Probá hablar de nuevo o escribí tu pedido. Revisá que el micrófono esté habilitado."
    const val MicAccessTitle = "Se necesita acceso al micrófono"
    const val MicAccessBody =
        "VORA usa tu micrófono para escuchar tus comandos de voz y acompañarte en tiempo real, sin usar las manos."
    const val AllowAccess = "Permitir acceso"
    const val NotNow = "Ahora no"

    fun listenToResponse(seconds: Int) = "Escuchar respuesta (${time(seconds)})"

    fun quoted(text: String) = "“$text”"
    fun time(seconds: Int) = "%d:%02d".format(seconds / 60, seconds % 60)
    fun remaining(seconds: Int) = "-" + time(seconds)
}
