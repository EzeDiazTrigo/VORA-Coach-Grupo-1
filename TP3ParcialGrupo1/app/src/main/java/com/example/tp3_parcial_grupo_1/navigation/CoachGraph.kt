package com.example.tp3_parcial_grupo_1.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.tp3_parcial_grupo_1.ui.screens.coach.CoachRoute

// Los seis frames de «AI Voice Flow» son estados de este único destino (CoachUiState).
fun NavGraphBuilder.coachGraph(navController: NavController) {
    composable(
        route = AppDestination.Coach.pattern,
        arguments = listOf(navArgument(NavArg.Prompt) { type = NavType.StringType; nullable = true; defaultValue = null })
    ) {
        CoachRoute(onBack = { navController.popBackStack() })
    }
}
