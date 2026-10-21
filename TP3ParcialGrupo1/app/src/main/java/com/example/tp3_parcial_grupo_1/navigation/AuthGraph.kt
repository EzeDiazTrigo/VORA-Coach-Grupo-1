package com.example.tp3_parcial_grupo_1.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.tp3_parcial_grupo_1.ui.screens.auth.LoginRoute
import com.example.tp3_parcial_grupo_1.ui.screens.auth.RegisterRoute

fun NavGraphBuilder.authGraph(navController: NavController) {
    navigation(startDestination = AppDestination.Login.route, route = AppDestination.AuthGraph.route) {
        composable(AppDestination.Login.route) {
            LoginRoute(
                onSignedIn = navController::navigateToHomeAfterAuth,
                onSignUpClick = { navController.navigate(AppDestination.Register.route) }
            )
        }
        composable(AppDestination.Register.route) {
            RegisterRoute(
                onSignedUp = navController::navigateToHomeAfterAuth,
                onBackClick = { navController.popBackStack() },
                onSignInClick = { navController.popBackStack() }
            )
        }
    }
}
