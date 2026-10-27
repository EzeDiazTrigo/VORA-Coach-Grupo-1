package com.example.tp3_parcial_grupo_1.navigation

import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavType
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import androidx.navigation.navigation
import com.example.tp3_parcial_grupo_1.ui.screens.nutrition.AddFoodScreen
import com.example.tp3_parcial_grupo_1.ui.screens.nutrition.AnalyseMealPhotoScreen
import com.example.tp3_parcial_grupo_1.ui.screens.nutrition.AnalyzingMealScreen
import com.example.tp3_parcial_grupo_1.ui.screens.nutrition.ManualLogScreen
import com.example.tp3_parcial_grupo_1.ui.screens.nutrition.MealDetailScreen
import com.example.tp3_parcial_grupo_1.ui.screens.nutrition.MealsViewModel
import com.example.tp3_parcial_grupo_1.ui.screens.nutrition.NutritionDashboardScreen
import com.example.tp3_parcial_grupo_1.ui.screens.nutrition.ReviewMealScreen
import com.example.tp3_parcial_grupo_1.ui.screens.nutrition.TodaysMealsScreen
import com.example.tp3_parcial_grupo_1.ui.screens.nutrition.VoiceLoggingScreen

fun NavGraphBuilder.nutritionGraph(navController: NavController) {
    navigation(
        startDestination = AppDestination.Nutrition.route,
        route = AppDestination.NutritionGraph.route
    ) {
        composable(AppDestination.Nutrition.route) {
            val viewModel: MealsViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            NutritionDashboardScreen(
                uiState = uiState,
                onNavigateToAddFood = { navController.navigate(AppDestination.AddFood.route) },
                onNavigateToHistory = { navController.navigate(AppDestination.TodaysMeals.route) },
                onNavigateToDetail = { mealId -> navController.navigate(AppDestination.MealDetails.createRoute(mealId)) },
                onNavigateToVoice = { navController.navigate(AppDestination.VoiceFoodLogging.route) },
                onNavigateToPhoto = { navController.navigate(AppDestination.PhotoAnalysis.route) },
                onDeleteMeal = { mealId -> viewModel.deleteMeal(mealId) },
                onDateSelected = { date -> viewModel.setDate(date) }
            )
        }
        composable(AppDestination.TodaysMeals.route) {
            val viewModel: MealsViewModel = hiltViewModel()
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            TodaysMealsScreen(
                uiState = uiState,
                onBackClick = { navController.popBackStack() },
                onAddDinner = { navController.navigate(AppDestination.AddFood.route) }
            )
        }
        composable(AppDestination.AddFood.route) {
            AddFoodScreen(
                onBackClick = { navController.popBackStack() },
                onNavigateToVoice = { navController.navigate(AppDestination.VoiceFoodLogging.route) },
                onNavigateToPhoto = { navController.navigate(AppDestination.PhotoAnalysis.route) },
                onNavigateToManual = { navController.navigate(AppDestination.ManualLog.route) },
                onNavigateToReview = { navController.navigate(AppDestination.FoodConfirmation.route) }
            )
        }
        composable(AppDestination.ManualLog.route) {
            val viewModel: MealsViewModel = hiltViewModel()
            ManualLogScreen(
                onBackClick = { navController.popBackStack() },
                onSaveMeal = { meal ->
                    viewModel.addMeal(meal)
                    navController.popBackStack(AppDestination.Nutrition.route, false)
                }
            )
        }
        composable(AppDestination.VoiceFoodLogging.route) {
            VoiceLoggingScreen(
                onCloseClick = { navController.popBackStack() },
                onDoneSpeaking = { navController.navigate(AppDestination.FoodAnalysis.route) },
                onTypeInsteadClick = {
                    navController.navigate(AppDestination.ManualLog.route) {
                        popUpTo(AppDestination.VoiceFoodLogging.route) { inclusive = true }
                    }
                }
            )
        }
        composable(AppDestination.FoodAnalysis.route) {
            AnalyzingMealScreen(
                onCancelClick = { navController.popBackStack() },
                onAnalysisComplete = {
                    val isStillAnalyzing = navController.currentDestination?.route == AppDestination.FoodAnalysis.route
                    if (isStillAnalyzing) {
                        navController.navigate(AppDestination.FoodConfirmation.route) {
                            popUpTo(AppDestination.FoodAnalysis.route) { inclusive = true }
                        }
                    }
                }
            )
        }
        composable(AppDestination.PhotoAnalysis.route) {
            AnalyseMealPhotoScreen(
                onBackClick = { navController.popBackStack() },
                onReviewAndAdd = { navController.navigate(AppDestination.FoodConfirmation.route) }
            )
        }
        composable(AppDestination.FoodConfirmation.route) {
            val viewModel: MealsViewModel = hiltViewModel()
            ReviewMealScreen(
                onBackClick = { navController.popBackStack() },
                onSaveMeal = { meal ->
                    viewModel.addMeal(meal)
                    navController.popBackStack(AppDestination.Nutrition.route, false)
                }
            )
        }
        composable(
            route = AppDestination.MealDetails.route,
            arguments = listOf(navArgument(NavArg.MealId) { type = NavType.LongType; defaultValue = 0L })
        ) { _ ->
            val viewModel: MealsViewModel = hiltViewModel()
            MealDetailScreen(
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() },
                onMealDuplicated = { navController.popBackStack(AppDestination.Nutrition.route, false) }
            )
        }
    }
}
