package com.example.tp3_parcial_grupo_1.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument

private val workoutIdArgument = navArgument(NavArg.WorkoutId) { type = NavType.StringType }
private val exerciseIdArgument = navArgument(NavArg.ExerciseId) { type = NavType.StringType }

private fun NavGraphBuilder.placeholder(
    destination: AppDestination,
    title: String,
    arguments: List<NamedNavArgument> = emptyList(),
    actions: (NavBackStackEntry) -> List<PlaceholderAction> = { emptyList() }
) {
    composable(destination.route, arguments) { entry ->
        PlaceholderScreen(
            title = title,
            detail = entry.argumentSummary(arguments),
            actions = actions(entry)
        )
    }
}

private fun NavBackStackEntry.argumentSummary(arguments: List<NamedNavArgument>): String? {
    if (arguments.isEmpty()) return null
    return arguments.joinToString { argument ->
        "${argument.name} = ${savedStateHandle.get<Any>(argument.name)}"
    }
}

private fun NavBackStackEntry.workoutId(): String = savedStateHandle.get<String>(NavArg.WorkoutId).orEmpty()

fun NavGraphBuilder.workoutPlaceholderScreens(navController: NavController) {
    placeholder(AppDestination.WeeklyPlan, "Workout / 02 — Weekly Plan")
    placeholder(AppDestination.PreWorkout, "Workout / 04 — Pre Workout", listOf(workoutIdArgument)) { entry ->
        listOf(
            PlaceholderAction("Begin") {
                navController.navigate(AppDestination.ActiveWorkout.createRoute(entry.workoutId()))
            }
        )
    }
    placeholder(AppDestination.ActiveWorkout, "Workout / 05 — Active Workout", listOf(workoutIdArgument)) { entry ->
        listOf(
            PlaceholderAction("Exercise e01") {
                navController.navigate(AppDestination.ExerciseDetails.createRoute(entry.workoutId(), "e01"))
            },
            PlaceholderAction("Finish workout") {
                navController.navigate(AppDestination.WorkoutComplete.createRoute(entry.workoutId()))
            }
        )
    }
    placeholder(
        AppDestination.ExerciseDetails,
        "Workout / 06 — Exercise Details",
        listOf(workoutIdArgument, exerciseIdArgument)
    )
    placeholder(AppDestination.WorkoutComplete, "Workout / 07 — Workout Complete", listOf(workoutIdArgument)) {
        listOf(
            PlaceholderAction("Back to workouts") {
                navController.popBackStack(AppDestination.Workout.route, inclusive = false)
            }
        )
    }
}

fun NavGraphBuilder.profilePlaceholderGraph(navController: NavController) {
    navigation(startDestination = AppDestination.Profile.route, route = AppDestination.ProfileGraph.route) {
        placeholder(AppDestination.Profile, "Profile / 01 — Dashboard") {
            listOf(
                PlaceholderAction("Edit Profile") { navController.navigate(AppDestination.EditProfile.route) },
                PlaceholderAction("Goals & Preferences") { navController.navigate(AppDestination.GoalsPreferences.route) },
                PlaceholderAction("Nutrition Preferences") { navController.navigate(AppDestination.NutritionPreferences.route) },
                PlaceholderAction("Voice & AI Settings") { navController.navigate(AppDestination.VoiceAiSettings.route) },
                PlaceholderAction("Settings") { navController.navigate(AppDestination.Settings.route) }
            )
        }
        placeholder(AppDestination.EditProfile, "Profile / 02 — Edit Profile")
        placeholder(AppDestination.GoalsPreferences, "Profile / 03 — Goals & Preferences")
        placeholder(AppDestination.NutritionPreferences, "Profile / 04 — Nutrition Preferences")
        placeholder(AppDestination.VoiceAiSettings, "Profile / 05 — Voice & AI Settings")
        placeholder(AppDestination.Settings, "Profile / 06 — Settings") {
            listOf(PlaceholderAction("Log out") { navController.navigateToLoginAfterLogout() })
        }
    }
}
