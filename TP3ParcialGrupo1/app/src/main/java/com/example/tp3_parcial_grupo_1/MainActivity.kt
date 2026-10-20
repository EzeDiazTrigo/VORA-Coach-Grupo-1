package com.example.tp3_parcial_grupo_1

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.tp3_parcial_grupo_1.ui.components.BottomNavBar
import com.example.tp3_parcial_grupo_1.ui.screens.nutrition.nutritionGraph
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            AppTheme {
                val navController = rememberNavController()
                NavHost(
                    navController = navController,
                    startDestination = "nutrition_graph"
                ) {
                    nutritionGraph(navController)
                    composable("home") {
                        PlaceholderScreen(title = "Home", currentRoute = "home") { route -> navController.navigate(route) { popUpTo(route) { inclusive = true } } }
                    }
                    composable("workout") {
                        PlaceholderScreen(title = "Workout", currentRoute = "workout") { route -> navController.navigate(route) { popUpTo(route) { inclusive = true } } }
                    }
                    composable("progress") {
                        PlaceholderScreen(title = "Progress", currentRoute = "progress") { route -> navController.navigate(route) { popUpTo(route) { inclusive = true } } }
                    }
                    composable("profile") {
                        PlaceholderScreen(title = "Profile", currentRoute = "profile") { route -> navController.navigate(route) { popUpTo(route) { inclusive = true } } }
                    }
                }
            }
        }
    }
}

@androidx.compose.runtime.Composable
fun PlaceholderScreen(title: String, currentRoute: String, onNavigate: (String) -> Unit) {
    Scaffold(
        bottomBar = { BottomNavBar(currentRoute = currentRoute, onNavigate = onNavigate) }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding), contentAlignment = Alignment.Center) {
            Text(text = "$title Screen (Demo / Placeholder)")
        }
    }
}
