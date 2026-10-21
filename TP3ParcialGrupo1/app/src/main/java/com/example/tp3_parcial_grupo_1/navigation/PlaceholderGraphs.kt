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
private val mealIdArgument = navArgument(NavArg.MealId) { type = NavType.LongType }

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

fun NavGraphBuilder.coachPlaceholderGraph() {
    placeholder(AppDestination.Coach, "Voice / 01 — Idle")
}

fun NavGraphBuilder.workoutPlaceholderGraph(navController: NavController) {
    navigation(startDestination = AppDestination.Workout.route, route = AppDestination.WorkoutGraph.route) {
        placeholder(AppDestination.Workout, "Workout / 01 — Dashboard") {
            listOf(
                PlaceholderAction("Weekly Plan") { navController.navigate(AppDestination.WeeklyPlan.route) },
                PlaceholderAction("Workout w01") { navController.navigate(AppDestination.WorkoutDetail.createRoute("w01")) },
                PlaceholderAction("Workout w02") { navController.navigate(AppDestination.WorkoutDetail.createRoute("w02")) }
            )
        }
        placeholder(AppDestination.WeeklyPlan, "Workout / 02 — Weekly Plan")
        placeholder(AppDestination.WorkoutDetail, "Workout / 03 — Workout Details", listOf(workoutIdArgument)) { entry ->
            listOf(
                PlaceholderAction("Start Workout") {
                    navController.navigate(AppDestination.PreWorkout.createRoute(entry.workoutId()))
                }
            )
        }
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
}

fun NavGraphBuilder.nutritionPlaceholderGraph(navController: NavController) {
    navigation(startDestination = AppDestination.Nutrition.route, route = AppDestination.NutritionGraph.route) {
        placeholder(AppDestination.Nutrition, "Nutrition / 01 — Dashboard") {
            listOf(
                PlaceholderAction("View History") { navController.navigate(AppDestination.TodaysMeals.route) },
                PlaceholderAction("Add Food") { navController.navigate(AppDestination.AddFood.route) },
                PlaceholderAction("Meal 1") { navController.navigate(AppDestination.MealDetails.createRoute(1L)) },
                PlaceholderAction("Meal 2") { navController.navigate(AppDestination.MealDetails.createRoute(2L)) }
            )
        }
        placeholder(AppDestination.TodaysMeals, "Nutrition / 02 — Today's Meals") {
            listOf(PlaceholderAction("+ Add Dinner") { navController.navigate(AppDestination.AddFood.route) })
        }
        placeholder(AppDestination.AddFood, "Nutrition / 03 — Add Food") {
            listOf(
                PlaceholderAction("Start Voice Logging") { navController.navigate(AppDestination.VoiceFoodLogging.route) },
                PlaceholderAction("Photo") { navController.navigate(AppDestination.PhotoAnalysis.route) },
                PlaceholderAction("Log Manually") { navController.navigate(AppDestination.FoodConfirmation.route) }
            )
        }
        placeholder(AppDestination.VoiceFoodLogging, "Nutrition / 04 — Voice Food Logging") {
            listOf(PlaceholderAction("Done") { navController.navigate(AppDestination.FoodAnalysis.route) })
        }
        placeholder(AppDestination.FoodAnalysis, "Nutrition / 05 — Food Analysis") {
            listOf(PlaceholderAction("Review") { navController.navigate(AppDestination.FoodConfirmation.route) })
        }
        placeholder(AppDestination.PhotoAnalysis, "Nutrition / 06 — Photo Analysis") {
            listOf(PlaceholderAction("Review & Add") { navController.navigate(AppDestination.FoodConfirmation.route) })
        }
        placeholder(AppDestination.FoodConfirmation, "Nutrition / 07 — Food Confirmation") {
            listOf(
                PlaceholderAction("Add to Lunch") {
                    navController.popBackStack(AppDestination.Nutrition.route, inclusive = false)
                }
            )
        }
        placeholder(AppDestination.MealDetails, "Nutrition / 08 — Meal Details", listOf(mealIdArgument))
    }
}

fun NavGraphBuilder.progressPlaceholderGraph(navController: NavController) {
    navigation(startDestination = AppDestination.TodaysOverview.route, route = AppDestination.ProgressGraph.route) {
        placeholder(AppDestination.TodaysOverview, "Progress / 01 — Today's Overview") {
            listOf(PlaceholderAction("Your progress") { navController.navigate(AppDestination.Progress.route) })
        }
        placeholder(AppDestination.Progress, "Progress / 02 — Dashboard") {
            listOf(
                PlaceholderAction("Workout") { navController.navigate(AppDestination.WorkoutProgress.route) },
                PlaceholderAction("Nutrition") { navController.navigate(AppDestination.NutritionProgress.route) },
                PlaceholderAction("Activity") { navController.navigate(AppDestination.HealthTrends.route) },
                PlaceholderAction("See weekly review") { navController.navigate(AppDestination.WeeklyReview.route) }
            )
        }
        placeholder(AppDestination.WorkoutProgress, "Progress / 03 — Workout Progress")
        placeholder(AppDestination.NutritionProgress, "Progress / 04 — Nutrition Progress")
        placeholder(AppDestination.HealthTrends, "Progress / 05 — Health Trends")
        placeholder(AppDestination.WeeklyReview, "Progress / 06 — Weekly Review")
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
