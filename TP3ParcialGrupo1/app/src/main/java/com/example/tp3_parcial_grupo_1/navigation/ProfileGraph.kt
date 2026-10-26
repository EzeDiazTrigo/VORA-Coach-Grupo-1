package com.example.tp3_parcial_grupo_1.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.tp3_parcial_grupo_1.ui.screens.profile.EditProfileRoute
import com.example.tp3_parcial_grupo_1.ui.screens.profile.GoalsPreferencesRoute
import com.example.tp3_parcial_grupo_1.ui.screens.profile.NutritionPreferencesRoute
import com.example.tp3_parcial_grupo_1.ui.screens.profile.ProfileRoute
import com.example.tp3_parcial_grupo_1.ui.screens.profile.SettingsRoute
import com.example.tp3_parcial_grupo_1.ui.screens.profile.VoiceSettingsRoute

fun NavGraphBuilder.profileGraph(navController: NavController) {
    navigation(startDestination = AppDestination.Profile.route, route = AppDestination.ProfileGraph.route) {
        composable(AppDestination.Profile.route) {
            ProfileRoute(
                onSettingsClick = { navController.navigate(AppDestination.Settings.route) },
                onEditProfileClick = { navController.navigate(AppDestination.EditProfile.route) },
                onGoalsClick = { navController.navigate(AppDestination.GoalsPreferences.route) },
                onPreferencesClick = { navController.navigate(AppDestination.NutritionPreferences.route) },
                onVoiceAiClick = { navController.navigate(AppDestination.VoiceAiSettings.route) },
                onAccountClick = { navController.navigate(AppDestination.Settings.route) }
            )
        }
        composable(AppDestination.EditProfile.route) {
            EditProfileRoute(onBackClick = { navController.popBackStack() })
        }
        composable(AppDestination.GoalsPreferences.route) {
            GoalsPreferencesRoute(onBackClick = { navController.popBackStack() })
        }
        composable(AppDestination.NutritionPreferences.route) {
            NutritionPreferencesRoute(onBackClick = { navController.popBackStack() })
        }
        composable(AppDestination.VoiceAiSettings.route) {
            VoiceSettingsRoute(onBackClick = { navController.popBackStack() })
        }
        composable(AppDestination.Settings.route) {
            SettingsRoute(
                onBackClick = { navController.popBackStack() },
                onLogOutConfirmed = { navController.navigateToLoginAfterLogout() }
            )
        }
    }
}
