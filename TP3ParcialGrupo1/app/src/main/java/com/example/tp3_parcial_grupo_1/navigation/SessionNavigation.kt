package com.example.tp3_parcial_grupo_1.navigation

import androidx.navigation.NavController

fun NavController.navigateToHomeAfterAuth() {
    navigate(AppDestination.HomeGraph.route) {
        popUpTo(AppDestination.AuthGraph.route) { inclusive = true }
        launchSingleTop = true
    }
}

fun NavController.navigateToLoginAfterLogout() {
    navigate(AppDestination.AuthGraph.route) {
        popUpTo(graph.id) { inclusive = true }
        launchSingleTop = true
    }
    TopLevelDestination.entries.forEach { clearBackStack(it.graphRoute) }
}
