package com.example.tp3_parcial_grupo_1.ui.strings

object CoachStrings {
    const val Title = "VORA Coach"
    const val Online = "ONLINE"
    const val IdleHeadline = "How can I help?"
    const val IdleSubtitle = "Talk naturally about your training, nutrition or progress."
    const val SuggestedQuestions = "SUGGESTED QUESTIONS"
    const val LastConversation = "LAST CONVERSATION"
    const val JustNow = "Just now"
    const val TapToTalk = "TAP TO TALK"
    const val TypeInstead = "Type instead"

    const val ListeningTitle = "Listening"
    const val LiveAudio = "LIVE AUDIO"
    const val LiveTranscript = "LIVE TRANSCRIPT"
    const val ListeningHint = "Speak naturally. I'm listening."
    const val StopListening = "Done talking"

    const val ProcessingTitle = "Processing"
    const val Thinking = "THINKING…"

    const val SpeakingTitle = "VORA is replying"
    const val Speaking = "SPEAKING"
    const val TapToInterrupt = "Tap to interrupt"
    const val Replay = "Replay response"
    const val Pause = "Pause"
    const val Resume = "Resume"
    const val Stop = "Stop"

    const val Microphone = "Microphone"

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
