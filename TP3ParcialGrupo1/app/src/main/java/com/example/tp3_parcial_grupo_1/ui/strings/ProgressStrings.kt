package com.example.tp3_parcial_grupo_1.ui.strings

object ProgressStrings {
    const val TodaysOverviewTitle = "Today's Overview"
    const val DailyCompletion = "DAILY COMPLETION"
    const val VoraAdvice = "VORA ADVICE"
    const val CategoryBreakdowns = "Category Breakdowns"
    const val WorkoutCategory = "WORKOUT"
    const val StartWorkout = "Start Workout"
    const val CurrentGoal = "CURRENT GOAL"
    const val WeeklyTarget = "WEEKLY TARGET"

    const val DashboardTitle = "Your progress"
    const val DashboardSubtitle = "Small improvements add up."
    const val PeriodWeek = "Week"
    const val PeriodMonth = "Month"
    const val PeriodThreeMonths = "3 Months"
    const val VoraInsight = "VORA Insight"
    const val SeeWeeklyReview = "See weekly review"
    const val View = "View"
    const val ActiveGoal = "ACTIVE GOAL"

    fun percent(value: Int) = "$value%"
    fun quoted(text: String) = "\"$text\""
    fun complete(percent: Int) = "$percent% complete"
}
