package com.example.tp3_parcial_grupo_1.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.tp3_parcial_grupo_1.ui.screens.workout.ActiveWorkoutRoute
import com.example.tp3_parcial_grupo_1.ui.screens.workout.ExerciseDetailsRoute
import com.example.tp3_parcial_grupo_1.ui.screens.workout.PreWorkoutScreen
import com.example.tp3_parcial_grupo_1.ui.screens.workout.WeeklyPlanRoute
import com.example.tp3_parcial_grupo_1.ui.screens.workout.WorkoutCompleteScreen
import com.example.tp3_parcial_grupo_1.ui.screens.workout.WorkoutDetailRoute
import com.example.tp3_parcial_grupo_1.ui.screens.workout.WorkoutListRoute

private val workoutIdArgument = navArgument(NavArg.WorkoutId) { type = NavType.StringType }
private val exerciseIdArgument = navArgument(NavArg.ExerciseId) { type = NavType.StringType }

fun NavGraphBuilder.workoutGraph(navController: NavController) {
    navigation(
        startDestination = AppDestination.Workout.route,
        route = AppDestination.WorkoutGraph.route
    ) {
        composable(AppDestination.Workout.route) {
            WorkoutListRoute(
                onBackClick = null,
                onWorkoutClick = { workoutId ->
                    navController.navigate(AppDestination.WorkoutDetail.createRoute(workoutId))
                },
                onStartWorkoutClick = { workoutId ->
                    navController.navigate(AppDestination.PreWorkout.createRoute(workoutId))
                },
                onWeeklyPlanClick = {
                    navController.navigate(AppDestination.WeeklyPlan.route)
                },
                onViewProgressClick = {
                    navController.navigateInTab(AppDestination.WorkoutProgress.route)
                }
            )
        }

        composable(AppDestination.WeeklyPlan.route) { entry ->
            val dayNumber = entry.savedStateHandle.get<String>("dayNumber") ?: "8"
            WeeklyPlanRoute(
                initialDayNumber = dayNumber,
                onBackClick = { navController.popBackStack() },
                onWorkoutSelect = { workoutId ->
                    navController.navigate(AppDestination.WorkoutDetail.createRoute(workoutId))
                }
            )
        }

        composable(
            route = AppDestination.WorkoutDetail.route,
            arguments = listOf(workoutIdArgument)
        ) { entry ->
            val workoutId = entry.arguments?.getString(NavArg.WorkoutId).orEmpty()
            WorkoutDetailRoute(
                onBackClick = { navController.popBackStack() },
                onStartWorkoutClick = { id ->
                    navController.navigate(AppDestination.PreWorkout.createRoute(id))
                },
                onExerciseClick = { exerciseId ->
                    navController.navigate(AppDestination.ExerciseDetails.createRoute(workoutId, exerciseId))
                }
            )
        }

        composable(
            route = AppDestination.PreWorkout.route,
            arguments = listOf(workoutIdArgument)
        ) { entry ->
            val workoutId = entry.arguments?.getString(NavArg.WorkoutId).orEmpty()
            PreWorkoutScreen(
                workoutId = workoutId,
                onBackClick = { navController.popBackStack() },
                onBeginClick = {
                    navController.navigate(AppDestination.ActiveWorkout.createRoute(workoutId))
                },
                onMicClick = {
                    navController.navigate(AppDestination.Coach.route)
                }
            )
        }

        composable(
            route = AppDestination.ActiveWorkout.route,
            arguments = listOf(workoutIdArgument)
        ) { entry ->
            val workoutId = entry.arguments?.getString(NavArg.WorkoutId).orEmpty()
            ActiveWorkoutRoute(
                onBackClick = { navController.popBackStack() },
                onExerciseClick = { exerciseId ->
                    navController.navigate(AppDestination.ExerciseDetails.createRoute(workoutId, exerciseId))
                },
                onFinishWorkoutClick = {
                    navController.navigate(AppDestination.WorkoutComplete.createRoute(workoutId))
                }
            )
        }

        composable(
            route = AppDestination.ExerciseDetails.route,
            arguments = listOf(workoutIdArgument, exerciseIdArgument)
        ) {
            ExerciseDetailsRoute(
                onBackClick = { navController.popBackStack() },
                onAskVoraClick = {
                    navController.navigate(AppDestination.Coach.route)
                }
            )
        }

        composable(
            route = AppDestination.WorkoutComplete.route,
            arguments = listOf(workoutIdArgument)
        ) { entry ->
            val workoutId = entry.arguments?.getString(NavArg.WorkoutId).orEmpty()
            WorkoutCompleteScreen(
                workoutId = workoutId,
                onBackToWorkoutsClick = {
                    navController.popBackStack(AppDestination.Workout.route, inclusive = false)
                }
            )
        }
    }
}
