package com.example.tp3_parcial_grupo_1.ui.screens.progress

enum class MetricTone { Attention, Mint, Primary, Lime }

data class CompletionMetric(
    val label: String,
    val detail: String,
    val percent: Int,
    val tone: MetricTone
)

data class BreakdownWorkout(
    val status: String,
    val title: String,
    val meta: String
)

data class TodaysOverviewUiState(
    val dateLabel: String,
    val completion: Float,
    val metrics: List<CompletionMetric>,
    val advice: String,
    val workout: BreakdownWorkout,
    val currentGoal: String,
    val weeklyTarget: String
)

enum class ProgressPeriod { Week, Month, ThreeMonths }

data class SummaryStat(
    val label: String,
    val value: String,
    val trend: String? = null,
    val isStreak: Boolean = false
)

enum class ProgressCategory { Workout, Nutrition, Activity }

data class CategorySummary(
    val category: ProgressCategory,
    val title: String,
    val subtitle: String
)

data class PeriodProgress(
    val overviewLabel: String,
    val stats: List<SummaryStat>,
    val insight: String,
    val insightTag: String,
    val categories: List<CategorySummary>
)

data class ActiveGoal(
    val title: String,
    val description: String,
    val percentComplete: Int
)

data class ProgressDashboardUiState(
    val period: ProgressPeriod,
    val progress: PeriodProgress,
    val activeGoal: ActiveGoal
)
