package com.example.tp3_parcial_grupo_1.navigation

import androidx.navigation.NavController

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

// La ruta de un destino con argumentos opcionales llega con su query («workout/weekly_plan?date={date}»).
internal fun String.withoutQuery() = substringBefore('?')

fun bottomBarSelection(route: String?): TopLevelDestination? {
    val base = route?.withoutQuery()
    if (base == null || base !in routesWithBottomBar) return null
    return TopLevelDestination.entries.firstOrNull { it.owns(base) }
}

fun NavController.navigateToTopLevel(destination: TopLevelDestination) {
    if (bottomBarSelection(currentDestination?.route) == destination) {
        popBackStack(destination.startRoute, inclusive = false)
        return
    }
    navigate(destination.graphRoute) {
        popUpTo(AppDestination.Home.route) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}

fun NavController.navigateInTab(route: String) {
    val tab = TopLevelDestination.entries.firstOrNull { it.owns(route) } ?: TopLevelDestination.Home
    navigateToTopLevel(tab)
    if (currentDestination?.route != route) navigate(route)
}
