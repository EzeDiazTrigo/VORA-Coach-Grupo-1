package com.example.tp3_parcial_grupo_1.ui.screens.nutrition

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation

sealed class NutritionDestination(val route: String) {
    object Dashboard : NutritionDestination("nutrition_dashboard")
    object History : NutritionDestination("nutrition_history")
    object AddFood : NutritionDestination("nutrition_add_food")
    object ManualLog : NutritionDestination("nutrition_manual_log")
    object Voice : NutritionDestination("nutrition_voice")
    object Analysis : NutritionDestination("nutrition_analysis")
    object Photo : NutritionDestination("nutrition_photo")
    object Review : NutritionDestination("nutrition_review")
    object Detail : NutritionDestination("nutrition_detail/{mealId}") {
        fun createRoute(mealId: Long) = "nutrition_detail/$mealId"
    }
}

fun NavGraphBuilder.nutritionGraph(navController: NavController) {
    navigation(
        startDestination = NutritionDestination.Dashboard.route,
        route = "nutrition"
    ) {
        composable(NutritionDestination.Dashboard.route) {
            val viewModel: MealsViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            NutritionDashboardScreen(
                uiState = uiState,
                onNavigate = { route -> navController.navigate(route) { popUpTo(route) { inclusive = true } } },
                onNavigateToAddFood = { navController.navigate(NutritionDestination.AddFood.route) },
                onNavigateToHistory = { navController.navigate(NutritionDestination.History.route) },
                onNavigateToDetail = { mealId -> navController.navigate(NutritionDestination.Detail.createRoute(mealId)) },
                onNavigateToVoice = { navController.navigate(NutritionDestination.Voice.route) },
                onNavigateToPhoto = { navController.navigate(NutritionDestination.Photo.route) },
                onDeleteMeal = { mealId -> viewModel.deleteMeal(mealId) },
                onDateSelected = { date -> viewModel.setDate(date) }
            )
        }
        composable(NutritionDestination.History.route) {
            val viewModel: MealsViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            TodaysMealsScreen(
                uiState = uiState,
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
                onNavigateToManual = { navController.navigate(NutritionDestination.ManualLog.route) },
                onNavigateToReview = { navController.navigate(NutritionDestination.Review.route) }
            )
        }
        composable(NutritionDestination.ManualLog.route) {
            val viewModel: MealsViewModel = hiltViewModel()
            ManualLogScreen(
                onNavigate = { route -> navController.navigate(route) { popUpTo(route) { inclusive = true } } },
                onBackClick = { navController.popBackStack() },
                onSaveMeal = { meal ->
                    viewModel.addMeal(meal)
                    navController.popBackStack(NutritionDestination.Dashboard.route, false)
                }
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
            val viewModel: MealsViewModel = hiltViewModel()
            ReviewMealScreen(
                onNavigate = { route -> navController.navigate(route) { popUpTo(route) { inclusive = true } } },
                onBackClick = { navController.popBackStack() },
                onSaveMeal = { meal ->
                    viewModel.addMeal(meal)
                    navController.popBackStack(NutritionDestination.Dashboard.route, false)
                }
            )
        }
        composable(
            route = NutritionDestination.Detail.route,
            arguments = listOf(navArgument("mealId") { type = NavType.LongType; defaultValue = 0L })
        ) { _ ->
            val viewModel: MealsViewModel = hiltViewModel()
            MealDetailScreen(
                viewModel = viewModel,
                onNavigate = { route -> navController.navigate(route) { popUpTo(route) { inclusive = true } } },
                onBackClick = { navController.popBackStack() },
                onEditMeal = { navController.navigate(NutritionDestination.AddFood.route) },
                onDeleteSuccess = { navController.popBackStack() }
            )
        }
    }
}
