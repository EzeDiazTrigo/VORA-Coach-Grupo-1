package com.example.tp3_parcial_grupo_1.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.ScaffoldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tp3_parcial_grupo_1.ui.components.BottomNavBar
import com.example.tp3_parcial_grupo_1.ui.components.BottomNavItem

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val currentEntry by navController.currentBackStackEntryAsState()
    val currentRoute = currentEntry?.destination?.route
    val selectedTab = bottomBarSelection(currentRoute)
    val drawsBehindSystemBars = drawsBehindSystemBars(currentRoute)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        contentWindowInsets = if (drawsBehindSystemBars) WindowInsets(0) else ScaffoldDefaults.contentWindowInsets,
        bottomBar = {
            if (selectedTab != null) {
                BottomNavBar(
                    selected = BottomNavItem.valueOf(selectedTab.name),
                    onSelect = { item -> navController.navigateToTopLevel(TopLevelDestination.valueOf(item.name)) }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = AppDestination.AuthGraph.route,
            modifier = Modifier
                .padding(innerPadding)
                .consumeWindowInsets(innerPadding)
        ) {
            authGraph(navController)
            homeGraph(navController)
            coachGraph(navController)
            workoutGraph(navController)
            nutritionGraph(navController)
            progressGraph(navController)
            profileGraph(navController)
        }
    }
}

// Pantallas que manejan sus propios insets (safeDrawingPadding) en lugar del Scaffold: el Asistente,
// Pre Workout y Active Workout pintan su fondo verde detrás de las barras del sistema.
private val fullScreenRoutes = setOf(
    AppDestination.Coach.route,
    AppDestination.PreWorkout.route,
    AppDestination.ActiveWorkout.route,
    AppDestination.VoiceFoodLogging.route,
    AppDestination.FoodAnalysis.route
)

internal fun drawsBehindSystemBars(route: String?): Boolean = route?.withoutQuery() in fullScreenRoutes
