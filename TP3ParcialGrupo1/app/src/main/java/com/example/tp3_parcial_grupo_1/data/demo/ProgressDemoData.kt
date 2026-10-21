package com.example.tp3_parcial_grupo_1.data.demo

import com.example.tp3_parcial_grupo_1.ui.screens.progress.ActiveGoal
import com.example.tp3_parcial_grupo_1.ui.screens.progress.BreakdownWorkout
import com.example.tp3_parcial_grupo_1.ui.screens.progress.CategorySummary
import com.example.tp3_parcial_grupo_1.ui.screens.progress.CompletionMetric
import com.example.tp3_parcial_grupo_1.ui.screens.progress.MetricTone
import com.example.tp3_parcial_grupo_1.ui.screens.progress.PeriodProgress
import com.example.tp3_parcial_grupo_1.ui.screens.progress.ProgressCategory
import com.example.tp3_parcial_grupo_1.ui.screens.progress.ProgressPeriod
import com.example.tp3_parcial_grupo_1.ui.screens.progress.SummaryStat
import com.example.tp3_parcial_grupo_1.ui.screens.progress.TodaysOverviewUiState

// Datos de demostración: no provienen de ningún servidor ni sensor.
object ProgressDemoData {

    val todaysOverview = TodaysOverviewUiState(
        dateLabel = "TUESDAY, 8 SEPTEMBER",
        completion = 0.72f,
        metrics = listOf(
            CompletionMetric("Workout Progress", "Not started", 0, MetricTone.Attention),
            CompletionMetric("Nutrition Target", "1,420 / 2,100 kcal", 78, MetricTone.Mint),
            CompletionMetric("Activity Target", "7,842 steps", 84, MetricTone.Primary),
            CompletionMetric("Hydration Intake", "1.6 / 2.5 Litres", 65, MetricTone.Lime)
        ),
        advice = "Completing today's intermediate workout and a protein-rich dinner will secure your optimal target recovery.",
        workout = BreakdownWorkout(
            status = "NOT STARTED",
            title = "Upper Body Strength",
            meta = "42 min • 5 exercises • Intermediate"
        ),
        currentGoal = "Build strength",
        weeklyTarget = "4 / 5 Workouts"
    )

    val periods: Map<ProgressPeriod, PeriodProgress> = mapOf(
        ProgressPeriod.Week to PeriodProgress(
            overviewLabel = "THIS WEEK OVERVIEW",
            stats = listOf(
                SummaryStat("Workouts completed", "4 workouts", "↑ 1"),
                SummaryStat("Avg Daily Steps", "7,842", "↑ 6%"),
                SummaryStat("Daily Avg Protein", "104g", "↑ 9%"),
                SummaryStat("Active Streak", "4 days", isStreak = true)
            ),
            insight = "You've completed 4 workouts this week and increased your training volume by 8%. Your consistency is trending in the right direction.",
            insightTag = "CONSISTENT",
            categories = listOf(
                CategorySummary(ProgressCategory.Workout, "Workout", "3 sessions • 2,340 kg volume"),
                CategorySummary(ProgressCategory.Nutrition, "Nutrition", "104g protein • 74% avg target"),
                CategorySummary(ProgressCategory.Activity, "Activity", "7,842 steps • 5.6 km daily avg")
            )
        ),
        ProgressPeriod.Month to PeriodProgress(
            overviewLabel = "THIS MONTH OVERVIEW",
            stats = listOf(
                SummaryStat("Workouts completed", "15 workouts", "↑ 3"),
                SummaryStat("Avg Daily Steps", "7,315", "↑ 4%"),
                SummaryStat("Daily Avg Protein", "98g", "↑ 5%"),
                SummaryStat("Active Streak", "4 days", isStreak = true)
            ),
            insight = "You trained 15 times this month, three more than last month. Protein is improving but still below target on rest days.",
            insightTag = "IMPROVING",
            categories = listOf(
                CategorySummary(ProgressCategory.Workout, "Workout", "15 sessions • 9,820 kg volume"),
                CategorySummary(ProgressCategory.Nutrition, "Nutrition", "98g protein • 70% avg target"),
                CategorySummary(ProgressCategory.Activity, "Activity", "7,315 steps • 5.2 km daily avg")
            )
        ),
        ProgressPeriod.ThreeMonths to PeriodProgress(
            overviewLabel = "LAST 3 MONTHS OVERVIEW",
            stats = listOf(
                SummaryStat("Workouts completed", "41 workouts", "↑ 9"),
                SummaryStat("Avg Daily Steps", "6,980", "↑ 11%"),
                SummaryStat("Daily Avg Protein", "92g", "↑ 14%"),
                SummaryStat("Active Streak", "4 days", isStreak = true)
            ),
            insight = "Over the last three months your training frequency and daily activity have grown steadily. Keep the current rhythm.",
            insightTag = "STEADY",
            categories = listOf(
                CategorySummary(ProgressCategory.Workout, "Workout", "41 sessions • 26,400 kg volume"),
                CategorySummary(ProgressCategory.Nutrition, "Nutrition", "92g protein • 66% avg target"),
                CategorySummary(ProgressCategory.Activity, "Activity", "6,980 steps • 4.9 km daily avg")
            )
        )
    )

    val activeGoal = ActiveGoal(
        title = "Build strength & improve fitness",
        description = "You're building consistency across workouts and nutrition.",
        percentComplete = 68
    )
}
