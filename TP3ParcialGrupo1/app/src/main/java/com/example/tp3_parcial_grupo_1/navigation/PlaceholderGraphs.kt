package com.example.tp3_parcial_grupo_1.navigation

import androidx.navigation.NamedNavArgument
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.navArgument

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
