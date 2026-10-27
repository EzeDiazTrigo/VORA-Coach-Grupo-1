package com.example.tp3_parcial_grupo_1.ui.strings

object CoachStrings {
    const val Title = "VORA Coach"
    const val Online = "ONLINE"
    const val IdleHeadline = "How can I help?"
    const val IdleSubtitle = "Talk naturally about your workout, nutrition or progress."
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

    const val AskPlaceholder = "Ask VORA anything…"
    const val Attach = "Attach"
    const val Send = "Send"
    const val Talk = "Talk"
    const val VoraTyping = "VORA is typing…"
    const val Today = "TODAY"
    const val SendErrorOffline = "Couldn't reach VORA. Check your connection and try again."
    const val SendErrorMalformed = "VORA's reply couldn't be read. Try again."
    fun sendErrorServer(code: Int) = "VORA is unavailable right now (error $code). Try again."
    const val RetrySend = "Retry"
    const val YouSaid = "You"
    const val VoraSaid = "VORA"

    const val ErrorTitle = "System alert"
    const val ErrorHeadline = "Sorry, I didn't catch that"
    const val ErrorSubtitle = "Try speaking again or type your request. Make sure your microphone is enabled."
    const val MicAccessTitle = "Microphone access needed"
    const val MicAccessBody =
        "VORA uses your microphone to hear your voice commands and coach you in real time, hands-free."
    const val AllowAccess = "Allow access"
    const val NotNow = "Not now"

    fun listenToResponse(seconds: Int) = "Listen to response (${time(seconds)})"

    fun quoted(text: String) = "“$text”"
    fun time(seconds: Int) = "%d:%02d".format(seconds / 60, seconds % 60)
    fun remaining(seconds: Int) = "-" + time(seconds)
}
