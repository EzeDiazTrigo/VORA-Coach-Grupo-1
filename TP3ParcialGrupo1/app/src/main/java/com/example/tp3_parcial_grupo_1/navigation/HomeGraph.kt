package com.example.tp3_parcial_grupo_1.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.tp3_parcial_grupo_1.ui.screens.home.HomeRoute
import com.example.tp3_parcial_grupo_1.ui.screens.home.NotificationType
import com.example.tp3_parcial_grupo_1.ui.screens.home.NotificationsRoute

fun NotificationType.destinationRoute(): String = when (this) {
    NotificationType.Coach -> AppDestination.WeeklyReview.route
    NotificationType.Workout -> AppDestination.WeeklyPlan.route
    NotificationType.Nutrition -> AppDestination.Nutrition.route
    NotificationType.Milestone -> AppDestination.WorkoutProgress.route
}

fun NavGraphBuilder.homeGraph(navController: NavController) {
    navigation(startDestination = AppDestination.Home.route, route = AppDestination.HomeGraph.route) {
        composable(AppDestination.Home.route) {
            HomeRoute(
                onNotificationsClick = { navController.navigate(AppDestination.Notifications.route) },
                onStartVoiceClick = { navController.navigate(AppDestination.Coach.route) },
                onStartWorkoutClick = { navController.navigateToTopLevel(TopLevelDestination.Workout) },
                onLogMealClick = { navController.navigate(AppDestination.AddFood.route) }
            )
        }
        composable(AppDestination.Notifications.route) {
            NotificationsRoute(
                onNotificationOpen = { type -> navController.navigate(type.destinationRoute()) }
            )
        }
    }
}
