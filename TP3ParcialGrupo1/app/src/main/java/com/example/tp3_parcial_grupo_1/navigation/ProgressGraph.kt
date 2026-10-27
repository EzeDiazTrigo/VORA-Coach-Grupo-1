package com.example.tp3_parcial_grupo_1.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import com.example.tp3_parcial_grupo_1.ui.screens.progress.HealthTrendsRoute
import com.example.tp3_parcial_grupo_1.ui.screens.progress.NutritionProgressRoute
import com.example.tp3_parcial_grupo_1.ui.screens.progress.ProgressCategory
import com.example.tp3_parcial_grupo_1.ui.screens.progress.ProgressDashboardRoute
import com.example.tp3_parcial_grupo_1.ui.screens.progress.TodaysOverviewRoute
import com.example.tp3_parcial_grupo_1.ui.screens.progress.WeeklyReviewRoute
import com.example.tp3_parcial_grupo_1.ui.screens.progress.WorkoutProgressRoute

private fun ProgressCategory.destinationRoute(): String = when (this) {
    ProgressCategory.Workout -> AppDestination.WorkoutProgress.route
    ProgressCategory.Nutrition -> AppDestination.NutritionProgress.route
    ProgressCategory.Activity -> AppDestination.HealthTrends.route
}

fun NavGraphBuilder.progressGraph(navController: NavController) {
    navigation(startDestination = AppDestination.Progress.route, route = AppDestination.ProgressGraph.route) {
        composable(AppDestination.Progress.route) {
            ProgressDashboardRoute(
                onSeeWeeklyReviewClick = { navController.navigate(AppDestination.WeeklyReview.route) },
                onCategoryClick = { category -> navController.navigate(category.destinationRoute()) }
            )
        }
        composable(AppDestination.TodaysOverview.route) {
            TodaysOverviewRoute(
                onStartWorkoutClick = { navController.navigateToTopLevel(TopLevelDestination.Workout) }
            )
        }
        composable(AppDestination.WorkoutProgress.route) {
            WorkoutProgressRoute(onBackClick = { navController.popBackStack() })
        }
        composable(AppDestination.NutritionProgress.route) {
            NutritionProgressRoute(onBackClick = { navController.popBackStack() })
        }
        composable(AppDestination.HealthTrends.route) {
            HealthTrendsRoute(onBackClick = { navController.popBackStack() })
        }
        composable(AppDestination.WeeklyReview.route) {
            WeeklyReviewRoute(
                onBackClick = { navController.popBackStack() },
                onDoneClick = { navController.popBackStack() },
                onAskVoraClick = { navController.navigate(AppDestination.Coach.createRoute()) },
                onDinnerIdeasClick = { navController.navigateInTab(AppDestination.Nutrition.route) },
                onNextWeekPlanClick = { navController.navigateInTab(AppDestination.WeeklyPlan.route) }
            )
        }
    }
}
