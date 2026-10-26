package com.example.tp3_parcial_grupo_1.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument
import com.example.tp3_parcial_grupo_1.ui.screens.workout.ActiveWorkoutScreen
import com.example.tp3_parcial_grupo_1.ui.screens.workout.ExerciseDetailsScreen
import com.example.tp3_parcial_grupo_1.ui.screens.workout.PreWorkoutScreen
import com.example.tp3_parcial_grupo_1.ui.screens.workout.WeeklyPlanScreen
import com.example.tp3_parcial_grupo_1.ui.screens.workout.WorkoutCompleteScreen
import com.example.tp3_parcial_grupo_1.ui.screens.workout.WorkoutDetailRoute
import com.example.tp3_parcial_grupo_1.ui.screens.workout.WorkoutDetailViewModel
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

        composable(AppDestination.WeeklyPlan.route) {
            WeeklyPlanScreen(
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
        ) {
            val viewModel = hiltViewModel<WorkoutDetailViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            PreWorkoutScreen(
                workout = uiState.workout,
                workoutId = viewModel.workoutId,
                onBackClick = { navController.popBackStack() },
                onBeginClick = {
                    navController.navigate(AppDestination.ActiveWorkout.createRoute(viewModel.workoutId))
                }
            )
        }

        composable(
            route = AppDestination.ActiveWorkout.route,
            arguments = listOf(workoutIdArgument)
        ) {
            val viewModel = hiltViewModel<WorkoutDetailViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            ActiveWorkoutScreen(
                workout = uiState.workout,
                workoutId = viewModel.workoutId,
                onBackClick = { navController.popBackStack() },
                onExerciseClick = { exerciseId ->
                    navController.navigate(AppDestination.ExerciseDetails.createRoute(viewModel.workoutId, exerciseId))
                },
                onFinishWorkoutClick = {
                    navController.navigate(AppDestination.WorkoutComplete.createRoute(viewModel.workoutId))
                }
            )
        }

        composable(
            route = AppDestination.ExerciseDetails.route,
            arguments = listOf(workoutIdArgument, exerciseIdArgument)
        ) { entry ->
            val exerciseId = entry.arguments?.getString(NavArg.ExerciseId).orEmpty()
            val viewModel = hiltViewModel<WorkoutDetailViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            val exercise = uiState.workout?.exercises?.find { it.id == exerciseId }
            ExerciseDetailsScreen(
                exercise = exercise,
                exerciseId = exerciseId,
                onBackClick = { navController.popBackStack() }
            )
        }

        composable(
            route = AppDestination.WorkoutComplete.route,
            arguments = listOf(workoutIdArgument)
        ) {
            val viewModel = hiltViewModel<WorkoutDetailViewModel>()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            WorkoutCompleteScreen(
                workout = uiState.workout,
                workoutId = viewModel.workoutId,
                onBackToWorkoutsClick = {
                    navController.popBackStack(AppDestination.Workout.route, inclusive = false)
                }
            )
        }
    }
}
