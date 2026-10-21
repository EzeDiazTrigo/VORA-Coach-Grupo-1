package com.example.tp3_parcial_grupo_1.navigation

import androidx.navigation.NavController

enum class TopLevelDestination(val graphRoute: String, private val routePrefix: String) {
    Home(AppDestination.HomeGraph.route, "home/"),
    Workout(AppDestination.WorkoutGraph.route, "workout/"),
    Nutrition(AppDestination.NutritionGraph.route, "nutrition/"),
    Progress(AppDestination.ProgressGraph.route, "progress/"),
    Profile(AppDestination.ProfileGraph.route, "profile/");

    fun owns(route: String) = route.startsWith(routePrefix)
}

private val routesWithBottomBar = setOf(
    AppDestination.Home,
    AppDestination.Notifications,
    AppDestination.Workout,
    AppDestination.WeeklyPlan,
    AppDestination.Nutrition,
    AppDestination.TodaysMeals,
    AppDestination.AddFood,
    AppDestination.PhotoAnalysis,
    AppDestination.FoodConfirmation,
    AppDestination.MealDetails,
    AppDestination.TodaysOverview,
    AppDestination.Progress,
    AppDestination.WorkoutProgress,
    AppDestination.NutritionProgress,
    AppDestination.HealthTrends,
    AppDestination.Profile
).map { it.route }.toSet()

fun bottomBarSelection(route: String?): TopLevelDestination? {
    if (route !in routesWithBottomBar) return null
    return TopLevelDestination.entries.first { it.owns(route!!) }
}

fun NavController.navigateToTopLevel(destination: TopLevelDestination) {
    navigate(destination.graphRoute) {
        popUpTo(AppDestination.Home.route) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}
