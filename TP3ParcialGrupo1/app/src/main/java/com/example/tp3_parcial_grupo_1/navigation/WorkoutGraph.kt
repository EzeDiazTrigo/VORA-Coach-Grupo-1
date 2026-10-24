package com.example.tp3_parcial_grupo_1.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.tp3_parcial_grupo_1.ui.screens.workout.WorkoutDetailRoute
import com.example.tp3_parcial_grupo_1.ui.screens.workout.WorkoutListRoute

fun NavGraphBuilder.workoutGraph(navController: NavController) {
    navigation(startDestination = AppDestination.Workout.route, route = AppDestination.WorkoutGraph.route) {
        composable(AppDestination.Workout.route) {
            WorkoutListRoute(
                onWorkoutClick = { workoutId -> navController.navigate(AppDestination.WorkoutDetail.createRoute(workoutId)) }
            )
        }
        composable(
            route = AppDestination.WorkoutDetail.route,
            arguments = listOf(navArgument(NavArg.WorkoutId) { type = NavType.StringType })
        ) {
            WorkoutDetailRoute(
                onBackClick = { navController.popBackStack() },
                onStartWorkoutClick = { workoutId -> navController.navigate(AppDestination.PreWorkout.createRoute(workoutId)) }
            )
        }
        workoutPlaceholderScreens(navController)
    }
}
