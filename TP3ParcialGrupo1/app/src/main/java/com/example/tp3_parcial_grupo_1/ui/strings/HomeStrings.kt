package com.example.tp3_parcial_grupo_1.ui.strings

// Textos de Inicio. Se mantienen los del diseño de Figma.
object HomeStrings {
    const val Greeting = "Good morning"
    const val CoachName = "VORA Coach"
    const val AiOnline = "AI ONLINE"
    const val CoachQuote = "\"How can I support your fitness targets today?\""
    const val CoachHint = "Tap the mic to talk naturally. I'll plan or log for you."
    const val StartVoiceSession = "Start voice session"
    const val StartMyWorkout = "Start my workout"
    const val LogBreakfast = "Log breakfast"
    const val TodaysOverview = "Today's Overview"
    const val DailyGoal = "Daily Goal"
    const val Nutrition = "Nutrition"
    const val Protein = "Protein"
    const val Carbs = "Carbs"
    const val Steps = "STEPS"
    const val Distance = "DISTANCE"
    const val ActiveKcal = "ACTIVE KCAL"
    const val RecommendedWorkout = "RECOMMENDED WORKOUT"
    const val Today = "TODAY"
    const val StartWorkout = "Start workout"
    const val NotificationsTitle = "Notifications"
    const val MarkAllAsRead = "Mark all as read"
    const val GroupToday = "TODAY"
    const val GroupYesterday = "YESTERDAY"
    const val NotificationsButton = "Notificaciones"

    fun estimatedVolume(kg: String) = "Estimated Vol: $kg kg"
    fun workoutMeta(minutes: Int, exercises: Int, level: String) = "$minutes min • $exercises exercises • $level"
}
