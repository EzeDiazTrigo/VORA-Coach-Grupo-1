package com.example.tp3_parcial_grupo_1.ui.screens.nutrition

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation

sealed class NutritionDestination(val route: String) {
    object Dashboard : NutritionDestination("nutrition_dashboard")
    object History : NutritionDestination("nutrition_history")
    object AddFood : NutritionDestination("nutrition_add_food")
    object Voice : NutritionDestination("nutrition_voice")
    object Analysis : NutritionDestination("nutrition_analysis")
    object Photo : NutritionDestination("nutrition_photo")
    object Review : NutritionDestination("nutrition_review")
    object Detail : NutritionDestination("nutrition_detail")
}

fun NavGraphBuilder.nutritionGraph(navController: NavController) {
    navigation(
        startDestination = NutritionDestination.Dashboard.route,
        route = "nutrition_graph"
    ) {
        composable(NutritionDestination.Dashboard.route) {
            val viewModel: MealsViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            NutritionDashboardScreen(
                uiState = uiState,
                onNavigate = { route -> navController.navigate(route) { popUpTo(route) { inclusive = true } } },
                onNavigateToAddFood = { navController.navigate(NutritionDestination.AddFood.route) },
                onNavigateToHistory = { navController.navigate(NutritionDestination.History.route) },
                onNavigateToDetail = { navController.navigate(NutritionDestination.Detail.route) },
                onNavigateToVoice = { navController.navigate(NutritionDestination.Voice.route) },
                onNavigateToPhoto = { navController.navigate(NutritionDestination.Photo.route) }
            )
        }
        composable(NutritionDestination.History.route) {
            TodaysMealsScreen(
                onNavigate = { route -> navController.navigate(route) { popUpTo(route) { inclusive = true } } },
                onBackClick = { navController.popBackStack() },
                onAddDinner = { navController.navigate(NutritionDestination.AddFood.route) }
            )
        }
        composable(NutritionDestination.AddFood.route) {
            AddFoodScreen(
                onNavigate = { route -> navController.navigate(route) { popUpTo(route) { inclusive = true } } },
                onBackClick = { navController.popBackStack() },
                onNavigateToVoice = { navController.navigate(NutritionDestination.Voice.route) },
                onNavigateToPhoto = { navController.navigate(NutritionDestination.Photo.route) },
                onNavigateToReview = { navController.navigate(NutritionDestination.Review.route) }
            )
        }
        composable(NutritionDestination.Voice.route) {
            VoiceLoggingScreen(
                onCloseClick = { navController.popBackStack() },
                onDoneSpeaking = { navController.navigate(NutritionDestination.Analysis.route) }
            )
        }
        composable(NutritionDestination.Analysis.route) {
            AnalyzingMealScreen(
                onCancelClick = { navController.popBackStack() }
            )
        }
        composable(NutritionDestination.Photo.route) {
            AnalyseMealPhotoScreen(
                onNavigate = { route -> navController.navigate(route) { popUpTo(route) { inclusive = true } } },
                onBackClick = { navController.popBackStack() },
                onReviewAndAdd = { navController.navigate(NutritionDestination.Review.route) }
            )
        }
        composable(NutritionDestination.Review.route) {
            ReviewMealScreen(
                onNavigate = { route -> navController.navigate(route) { popUpTo(route) { inclusive = true } } },
                onBackClick = { navController.popBackStack() },
                onSaveMeal = { navController.popBackStack() }
            )
        }
        composable(NutritionDestination.Detail.route) {
            MealDetailScreen(
                onNavigate = { route -> navController.navigate(route) { popUpTo(route) { inclusive = true } } },
                onBackClick = { navController.popBackStack() },
                onEditMeal = { navController.navigate(NutritionDestination.AddFood.route) }
            )
        }
    }
}
