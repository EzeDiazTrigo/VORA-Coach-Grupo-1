package com.example.tp3_parcial_grupo_1.navigation

object NavArg {
    const val WorkoutId = "workoutId"
    const val ExerciseId = "exerciseId"
    const val MealId = "mealId"
}

sealed class AppDestination(val route: String) {

    data object AuthGraph : AppDestination("auth_graph")
    data object Login : AppDestination("auth/login")
    data object Register : AppDestination("auth/register")

    data object HomeGraph : AppDestination("home_graph")
    data object Home : AppDestination("home/dashboard")
    data object Notifications : AppDestination("home/notifications")

    data object Coach : AppDestination("coach")

    data object WorkoutGraph : AppDestination("workout_graph")
    data object Workout : AppDestination("workout/dashboard")
    data object WeeklyPlan : AppDestination("workout/weekly_plan")
    data object WorkoutDetail : AppDestination("workout/detail/{${NavArg.WorkoutId}}") {
        fun createRoute(workoutId: String) = "workout/detail/$workoutId"
    }
    data object PreWorkout : AppDestination("workout/pre/{${NavArg.WorkoutId}}") {
        fun createRoute(workoutId: String) = "workout/pre/$workoutId"
    }
    data object ActiveWorkout : AppDestination("workout/active/{${NavArg.WorkoutId}}") {
        fun createRoute(workoutId: String) = "workout/active/$workoutId"
    }
    data object ExerciseDetails : AppDestination("workout/exercise/{${NavArg.WorkoutId}}/{${NavArg.ExerciseId}}") {
        fun createRoute(workoutId: String, exerciseId: String) = "workout/exercise/$workoutId/$exerciseId"
    }
    data object WorkoutComplete : AppDestination("workout/complete/{${NavArg.WorkoutId}}") {
        fun createRoute(workoutId: String) = "workout/complete/$workoutId"
    }

    data object NutritionGraph : AppDestination("nutrition_graph")
    data object Nutrition : AppDestination("nutrition/dashboard")
    data object TodaysMeals : AppDestination("nutrition/todays_meals")
    data object AddFood : AppDestination("nutrition/add_food")
    data object VoiceFoodLogging : AppDestination("nutrition/voice_logging")
    data object FoodAnalysis : AppDestination("nutrition/food_analysis")
    data object PhotoAnalysis : AppDestination("nutrition/photo_analysis")
    data object FoodConfirmation : AppDestination("nutrition/food_confirmation")
    data object MealDetails : AppDestination("nutrition/meal/{${NavArg.MealId}}") {
        fun createRoute(mealId: Long) = "nutrition/meal/$mealId"
    }

    data object ProgressGraph : AppDestination("progress_graph")
    data object TodaysOverview : AppDestination("progress/todays_overview")
    data object Progress : AppDestination("progress/dashboard")
    data object WorkoutProgress : AppDestination("progress/workout")
    data object NutritionProgress : AppDestination("progress/nutrition")
    data object HealthTrends : AppDestination("progress/health_trends")
    data object WeeklyReview : AppDestination("progress/weekly_review")

    data object ProfileGraph : AppDestination("profile_graph")
    data object Profile : AppDestination("profile/dashboard")
    data object EditProfile : AppDestination("profile/edit")
    data object GoalsPreferences : AppDestination("profile/goals")
    data object NutritionPreferences : AppDestination("profile/nutrition_preferences")
    data object VoiceAiSettings : AppDestination("profile/voice_ai")
    data object Settings : AppDestination("profile/settings")
}
