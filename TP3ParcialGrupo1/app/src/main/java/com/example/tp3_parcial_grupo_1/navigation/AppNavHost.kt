package com.example.tp3_parcial_grupo_1.navigation

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.tp3_parcial_grupo_1.R
import com.example.tp3_parcial_grupo_1.ui.strings.NavigationStrings

@Composable
fun AppNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController = rememberNavController()
) {
    val currentEntry by navController.currentBackStackEntryAsState()
    val selectedTab = bottomBarSelection(currentEntry?.destination?.route)

    Scaffold(
        modifier = modifier.fillMaxSize(),
        containerColor = MaterialTheme.colorScheme.background,
        bottomBar = {
            if (selectedTab != null) {
                TemporaryBottomBar(
                    selected = selectedTab,
                    onSelect = navController::navigateToTopLevel
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
            coachPlaceholderGraph()
            workoutGraph(navController)
            nutritionGraph(navController)
            progressGraph(navController)
            profilePlaceholderGraph(navController)
        }
    }
}

@Composable
private fun TemporaryBottomBar(
    selected: TopLevelDestination,
    onSelect: (TopLevelDestination) -> Unit
) {
    NavigationBar(containerColor = MaterialTheme.colorScheme.surface) {
        TopLevelDestination.entries.forEach { destination ->
            NavigationBarItem(
                selected = destination == selected,
                onClick = { onSelect(destination) },
                icon = { Icon(painterResource(destination.iconRes()), contentDescription = null) },
                label = { Text(destination.label()) }
            )
        }
    }
}

@DrawableRes
private fun TopLevelDestination.iconRes(): Int = when (this) {
    TopLevelDestination.Home -> R.drawable.ic_home
    TopLevelDestination.Workout -> R.drawable.ic_dumbbell
    TopLevelDestination.Nutrition -> R.drawable.ic_apple
    TopLevelDestination.Progress -> R.drawable.ic_chart_line
    TopLevelDestination.Profile -> R.drawable.ic_user
}

private fun TopLevelDestination.label(): String = when (this) {
    TopLevelDestination.Home -> NavigationStrings.TabHome
    TopLevelDestination.Workout -> NavigationStrings.TabWorkout
    TopLevelDestination.Nutrition -> NavigationStrings.TabNutrition
    TopLevelDestination.Progress -> NavigationStrings.TabProgress
    TopLevelDestination.Profile -> NavigationStrings.TabProfile
}
