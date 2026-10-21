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

data class ChartBar(
    val label: String,
    val value: Float,
    val isHighlighted: Boolean = false
)

data class PersonalBest(
    val exercise: String,
    val detail: String,
    val improvement: String
)

enum class ExerciseTrend { Up, Stable }

data class ExerciseProgress(
    val name: String,
    val detail: String,
    val change: String,
    val trend: ExerciseTrend
)

data class WorkoutPeriodProgress(
    val stats: List<SummaryStat>,
    val chartSubtitle: String,
    val volumeTrend: String,
    val bars: List<ChartBar>,
    val personalBest: PersonalBest,
    val insight: String,
    val exercises: List<ExerciseProgress>
)

data class WorkoutProgressUiState(
    val period: ProgressPeriod,
    val progress: WorkoutPeriodProgress,
    val message: String? = null
)

enum class NutritionMacro { Calories, Protein, Carbs, Fat }

data class DayCompliance(
    val day: String,
    val isHit: Boolean
)

data class MacroTarget(
    val target: String,
    val days: List<DayCompliance>
)

data class FoodHabit(
    val label: String,
    val value: String
)

data class NutritionPeriodProgress(
    val stats: List<SummaryStat>,
    val analysis: String,
    val habits: List<FoodHabit>
)

data class NutritionProgressUiState(
    val period: ProgressPeriod,
    val progress: NutritionPeriodProgress,
    val macro: NutritionMacro,
    val target: MacroTarget,
    val message: String? = null
)

enum class HabitArea { Sleep, Movement, Workout, Protein }

enum class HabitTrend { Improving, Consistent }

data class HabitSignal(
    val area: HabitArea,
    val name: String,
    val trend: HabitTrend
)

data class HealthTrendsUiState(
    val stats: List<SummaryStat>,
    val stepsGoal: String,
    val bars: List<ChartBar>,
    val insight: String,
    val signals: List<HabitSignal>,
    val message: String? = null
)

data class ReviewHighlight(
    val title: String,
    val description: String
)

data class WeeklyReviewUiState(
    val headline: String,
    val stats: List<SummaryStat>,
    val wins: List<ReviewHighlight>,
    val improvement: ReviewHighlight,
    val recommendations: List<String>
)
