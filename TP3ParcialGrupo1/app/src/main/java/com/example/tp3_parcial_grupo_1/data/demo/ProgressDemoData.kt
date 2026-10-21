package com.example.tp3_parcial_grupo_1.data.demo

import com.example.tp3_parcial_grupo_1.ui.screens.progress.ActiveGoal
import com.example.tp3_parcial_grupo_1.ui.screens.progress.BreakdownWorkout
import com.example.tp3_parcial_grupo_1.ui.screens.progress.CategorySummary
import com.example.tp3_parcial_grupo_1.ui.screens.progress.ChartBar
import com.example.tp3_parcial_grupo_1.ui.screens.progress.CompletionMetric
import com.example.tp3_parcial_grupo_1.ui.screens.progress.DayCompliance
import com.example.tp3_parcial_grupo_1.ui.screens.progress.ExerciseProgress
import com.example.tp3_parcial_grupo_1.ui.screens.progress.ExerciseTrend
import com.example.tp3_parcial_grupo_1.ui.screens.progress.FoodHabit
import com.example.tp3_parcial_grupo_1.ui.screens.progress.HabitArea
import com.example.tp3_parcial_grupo_1.ui.screens.progress.HabitSignal
import com.example.tp3_parcial_grupo_1.ui.screens.progress.HabitTrend
import com.example.tp3_parcial_grupo_1.ui.screens.progress.HealthTrendsUiState
import com.example.tp3_parcial_grupo_1.ui.screens.progress.MacroTarget
import com.example.tp3_parcial_grupo_1.ui.screens.progress.MetricTone
import com.example.tp3_parcial_grupo_1.ui.screens.progress.NutritionMacro
import com.example.tp3_parcial_grupo_1.ui.screens.progress.NutritionPeriodProgress
import com.example.tp3_parcial_grupo_1.ui.screens.progress.PeriodProgress
import com.example.tp3_parcial_grupo_1.ui.screens.progress.PersonalBest
import com.example.tp3_parcial_grupo_1.ui.screens.progress.ProgressCategory
import com.example.tp3_parcial_grupo_1.ui.screens.progress.ProgressPeriod
import com.example.tp3_parcial_grupo_1.ui.screens.progress.ReviewHighlight
import com.example.tp3_parcial_grupo_1.ui.screens.progress.SummaryStat
import com.example.tp3_parcial_grupo_1.ui.screens.progress.TodaysOverviewUiState
import com.example.tp3_parcial_grupo_1.ui.screens.progress.WeeklyReviewUiState
import com.example.tp3_parcial_grupo_1.ui.screens.progress.WorkoutPeriodProgress

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

    val workoutPeriods: Map<ProgressPeriod, WorkoutPeriodProgress> = mapOf(
        ProgressPeriod.Week to WorkoutPeriodProgress(
            stats = listOf(
                SummaryStat("SESSIONS", "4"),
                SummaryStat("VOLUME", "8,420 kg"),
                SummaryStat("AVG TIME", "41 min")
            ),
            chartSubtitle = "KILOGRAMS LIFTED PER SESSION",
            volumeTrend = "+8% vs last week",
            bars = listOf(
                ChartBar("M", 1900f, isHighlighted = true),
                ChartBar("T", 2050f, isHighlighted = true),
                ChartBar("W", 0f),
                ChartBar("T", 2180f, isHighlighted = true),
                ChartBar("F", 2290f, isHighlighted = true)
            ),
            personalBest = PersonalBest("Seated Cable Row", "45 kg × 12 reps", "(+5 kg vs prev best)"),
            insight = "Your training volume increased without adding workout duration. That's efficiency improving.",
            exercises = listOf(
                ExerciseProgress("Bench Press", "40 kg × 10 reps", "↑ 2.5 kg", ExerciseTrend.Up),
                ExerciseProgress("Cable Row", "45 kg × 12 reps", "↑ 5.0 kg", ExerciseTrend.Up),
                ExerciseProgress("Shoulder Press", "20 kg × 10 reps", "→ Stable", ExerciseTrend.Stable),
                ExerciseProgress("Lateral Raise", "8 kg × 12 reps", "↑ 1.0 kg", ExerciseTrend.Up)
            )
        ),
        ProgressPeriod.Month to WorkoutPeriodProgress(
            stats = listOf(
                SummaryStat("SESSIONS", "15"),
                SummaryStat("VOLUME", "31,760 kg"),
                SummaryStat("AVG TIME", "43 min")
            ),
            chartSubtitle = "KILOGRAMS LIFTED PER WEEK",
            volumeTrend = "+5% vs last month",
            bars = listOf(
                ChartBar("W1", 7150f, isHighlighted = true),
                ChartBar("W2", 7740f, isHighlighted = true),
                ChartBar("W3", 8450f, isHighlighted = true),
                ChartBar("W4", 8420f, isHighlighted = true)
            ),
            personalBest = PersonalBest("Bench Press", "40 kg × 10 reps", "(+2.5 kg vs prev best)"),
            insight = "You added one extra session per week compared to last month while keeping your workouts short.",
            exercises = listOf(
                ExerciseProgress("Bench Press", "40 kg × 10 reps", "↑ 5.0 kg", ExerciseTrend.Up),
                ExerciseProgress("Cable Row", "45 kg × 12 reps", "↑ 7.5 kg", ExerciseTrend.Up),
                ExerciseProgress("Shoulder Press", "20 kg × 10 reps", "↑ 2.5 kg", ExerciseTrend.Up),
                ExerciseProgress("Lateral Raise", "8 kg × 12 reps", "→ Stable", ExerciseTrend.Stable)
            )
        ),
        ProgressPeriod.ThreeMonths to WorkoutPeriodProgress(
            stats = listOf(
                SummaryStat("SESSIONS", "41"),
                SummaryStat("VOLUME", "84,300 kg"),
                SummaryStat("AVG TIME", "44 min")
            ),
            chartSubtitle = "KILOGRAMS LIFTED PER MONTH",
            volumeTrend = "+18% vs previous 3 months",
            bars = listOf(
                ChartBar("JUN", 24900f, isHighlighted = true),
                ChartBar("JUL", 27640f, isHighlighted = true),
                ChartBar("AUG", 31760f, isHighlighted = true)
            ),
            personalBest = PersonalBest("Seated Cable Row", "45 kg × 12 reps", "(+10 kg vs 3 months ago)"),
            insight = "Your strength has grown steadily for three months. Consider a lighter deload week soon.",
            exercises = listOf(
                ExerciseProgress("Bench Press", "40 kg × 10 reps", "↑ 7.5 kg", ExerciseTrend.Up),
                ExerciseProgress("Cable Row", "45 kg × 12 reps", "↑ 10.0 kg", ExerciseTrend.Up),
                ExerciseProgress("Shoulder Press", "20 kg × 10 reps", "↑ 5.0 kg", ExerciseTrend.Up),
                ExerciseProgress("Lateral Raise", "8 kg × 12 reps", "↑ 2.0 kg", ExerciseTrend.Up)
            )
        )
    )

    val nutritionPeriods: Map<ProgressPeriod, NutritionPeriodProgress> = mapOf(
        ProgressPeriod.Week to NutritionPeriodProgress(
            stats = listOf(
                SummaryStat("AVG CALORIES", "1,980 kcal"),
                SummaryStat("AVG PROTEIN", "132g"),
                SummaryStat("TARGET HIT", "5 / 7 days")
            ),
            analysis = "Your protein intake has improved this week, especially on workout days. Keeping this consistent will better support your strength goal.",
            habits = listOf(
                FoodHabit("Frequent lunch", "Chicken & Rice Bowl"),
                FoodHabit("Top protein source", "Whey & Chicken Breast"),
                FoodHabit("Avg breakfast", "420 kcal"),
                FoodHabit("Avg dinner", "610 kcal")
            )
        ),
        ProgressPeriod.Month to NutritionPeriodProgress(
            stats = listOf(
                SummaryStat("AVG CALORIES", "1,940 kcal"),
                SummaryStat("AVG PROTEIN", "124g"),
                SummaryStat("TARGET HIT", "19 / 30 days")
            ),
            analysis = "Protein hit its target on most training days this month. Rest days are where most of the gap remains.",
            habits = listOf(
                FoodHabit("Frequent lunch", "Chicken & Rice Bowl"),
                FoodHabit("Top protein source", "Chicken Breast"),
                FoodHabit("Avg breakfast", "410 kcal"),
                FoodHabit("Avg dinner", "640 kcal")
            )
        ),
        ProgressPeriod.ThreeMonths to NutritionPeriodProgress(
            stats = listOf(
                SummaryStat("AVG CALORIES", "1,910 kcal"),
                SummaryStat("AVG PROTEIN", "116g"),
                SummaryStat("TARGET HIT", "52 / 90 days")
            ),
            analysis = "Your average protein rose every month for the last three months. Dinner is still the meal with the lowest protein.",
            habits = listOf(
                FoodHabit("Frequent lunch", "Turkey Wrap"),
                FoodHabit("Top protein source", "Greek Yogurt & Eggs"),
                FoodHabit("Avg breakfast", "390 kcal"),
                FoodHabit("Avg dinner", "660 kcal")
            )
        )
    )

    val macroTargets: Map<NutritionMacro, MacroTarget> = mapOf(
        NutritionMacro.Calories to MacroTarget("2,100 kcal", week(true, true, false, true, false, false, true)),
        NutritionMacro.Protein to MacroTarget("140g", week(true, true, false, true, true, false, true)),
        NutritionMacro.Carbs to MacroTarget("220g", week(true, false, true, true, true, false, false)),
        NutritionMacro.Fat to MacroTarget("70g", week(true, true, true, false, true, true, false))
    )

    val healthTrends = HealthTrendsUiState(
        stats = listOf(
            SummaryStat("AVG STEPS", "7,842"),
            SummaryStat("AVG DISTANCE", "5.6 km"),
            SummaryStat("ACTIVE KCAL", "312 kcal")
        ),
        stepsGoal = "8,000",
        bars = listOf(
            ChartBar("M", 6100f),
            ChartBar("T", 7700f),
            ChartBar("W", 8450f, isHighlighted = true),
            ChartBar("T", 7400f),
            ChartBar("F", 9600f, isHighlighted = true),
            ChartBar("S", 7800f),
            ChartBar("S", 7844f)
        ),
        insight = "Your activity has been more consistent over the last two weeks. Your workout days also tend to be your most active days.",
        signals = listOf(
            HabitSignal(HabitArea.Sleep, "Sleep Quality", HabitTrend.Improving),
            HabitSignal(HabitArea.Movement, "Daily Movement", HabitTrend.Consistent),
            HabitSignal(HabitArea.Workout, "Workout Frequency", HabitTrend.Improving),
            HabitSignal(HabitArea.Protein, "Protein Intake", HabitTrend.Improving)
        )
    )

    val weeklyReview = WeeklyReviewUiState(
        headline = "You had a strong week.",
        stats = listOf(
            SummaryStat("WORKOUTS", "4 sessions"),
            SummaryStat("AVG STEPS", "7,842 steps"),
            SummaryStat("PROTEIN", "132g daily")
        ),
        wins = listOf(
            ReviewHighlight("You stayed consistent", "Four completed workouts helped you maintain your weekly training rhythm."),
            ReviewHighlight("Your protein improved", "You reached your protein target on 5 of 7 days."),
            ReviewHighlight("Your training volume increased", "You completed 8% more volume than last week.")
        ),
        improvement = ReviewHighlight(
            "Dinner consistency",
            "Your protein intake tends to drop at dinner. Try planning a protein-rich dinner before the evening."
        ),
        recommendations = listOf(
            "Keep your current workout schedule.",
            "Try to reach 140g protein on at least 6 days.",
            "Aim for 8,000+ steps on non-training days."
        )
    )

    private fun week(vararg hits: Boolean): List<DayCompliance> =
        listOf("M", "T", "W", "T", "F", "S", "S").zip(hits.toList()) { day, isHit -> DayCompliance(day, isHit) }
}
