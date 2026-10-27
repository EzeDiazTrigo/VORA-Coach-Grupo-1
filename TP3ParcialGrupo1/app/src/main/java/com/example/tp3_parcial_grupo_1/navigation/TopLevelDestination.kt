package com.example.tp3_parcial_grupo_1.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

enum class TopLevelDestination(val graphRoute: String, val startRoute: String, private val routePrefix: String) {
    Home(AppDestination.HomeGraph.route, AppDestination.Home.route, "home/"),
    Workout(AppDestination.WorkoutGraph.route, AppDestination.Workout.route, "workout/"),
    Nutrition(AppDestination.NutritionGraph.route, AppDestination.Nutrition.route, "nutrition/"),
    Progress(AppDestination.ProgressGraph.route, AppDestination.Progress.route, "progress/"),
    Profile(AppDestination.ProfileGraph.route, AppDestination.Profile.route, "profile/");

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
    if (route == null || route !in routesWithBottomBar) return null
    return TopLevelDestination.entries.firstOrNull { it.owns(route) }
}

fun NavController.navigateToTopLevel(destination: TopLevelDestination) {
    if (bottomBarSelection(currentDestination?.route) == destination) {
        popBackStack(destination.startRoute, inclusive = false)
        return
    }
    navigate(destination.graphRoute) {
        popUpTo(graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateInTab(route: String) {
    val tab = TopLevelDestination.entries.firstOrNull { it.owns(route) } ?: TopLevelDestination.Home
    navigateToTopLevel(tab)
    if (currentDestination?.route != route) navigate(route)
}
