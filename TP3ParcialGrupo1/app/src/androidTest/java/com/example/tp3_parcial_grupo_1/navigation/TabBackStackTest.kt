package com.example.tp3_parcial_grupo_1.navigation

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import androidx.navigation.compose.rememberNavController
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class TabBackStackTest {

    @get:Rule
    val composeRule = createComposeRule()

    private lateinit var navController: NavHostController

    @Before
    fun setUp() {
        composeRule.setContent {
            navController = rememberNavController()
            NavHost(navController = navController, startDestination = AppDestination.AuthGraph.route) {
                tab(AppDestination.AuthGraph, AppDestination.Login)
                tab(AppDestination.HomeGraph, AppDestination.Home, AppDestination.Notifications)
                tab(AppDestination.WorkoutGraph, AppDestination.Workout, AppDestination.WeeklyPlan)
                tab(
                    AppDestination.NutritionGraph,
                    AppDestination.Nutrition,
                    AppDestination.AddFood,
                    AppDestination.FoodConfirmation,
                    AppDestination.MealDetails
                )
                tab(AppDestination.ProgressGraph, AppDestination.Progress, AppDestination.TodaysOverview)
                tab(AppDestination.ProfileGraph, AppDestination.Profile, AppDestination.Settings)
            }
        }
        onMain { navController.navigateToHomeAfterAuth() }
    }

    @Test
    fun givenANotificationOpenedInAnotherTab_whenTappingHome_thenHomeRestoresItsHistory() {
        onMain {
            navController.navigate(AppDestination.Notifications.route)
            navController.navigateInTab(AppDestination.WeeklyPlan.route)
            navController.navigateToTopLevel(TopLevelDestination.Home)
        }

        assertCurrentRoute(AppDestination.Notifications.route)
    }

    @Test
    fun givenANutritionNotification_whenOpeningIt_thenTheDashboardIsNotDuplicated() {
        onMain {
            navController.navigate(AppDestination.Notifications.route)
            navController.navigateInTab(AppDestination.Nutrition.route)
            navController.popBackStack()
        }
    
        assertCurrentRoute(AppDestination.Home.route)
    }

    @Test
    fun givenAMealLoggedFromHome_whenConfirmingTheFood_thenTheNutritionDashboardIsShown() {
        onMain {
            navController.navigateInTab(AppDestination.AddFood.route)
            navController.navigate(AppDestination.FoodConfirmation.route)
            navController.popBackStack(AppDestination.Nutrition.route, inclusive = false)
        }

        assertCurrentRoute(AppDestination.Nutrition.route)
    }

    @Test
    fun givenAMealLoggedFromHome_whenTappingNutritionFromHome_thenTheTabKeepsItsHistory() {
        onMain {
            navController.navigateInTab(AppDestination.AddFood.route)
            navController.navigateToTopLevel(TopLevelDestination.Home)
            navController.navigateToTopLevel(TopLevelDestination.Nutrition)
            navController.popBackStack()
        }

        assertCurrentRoute(AppDestination.Nutrition.route)
    }

    @Test
    fun givenATabWithHistory_whenLoggingOutAndBackIn_thenTheTabStartsFromItsDashboard() {
        onMain {
            navController.navigateToTopLevel(TopLevelDestination.Nutrition)
            navController.navigate(AppDestination.MealDetails.createRoute(2L))
            navController.navigateToTopLevel(TopLevelDestination.Profile)
            navController.navigate(AppDestination.Settings.route)
            navController.navigateToLoginAfterLogout()
            navController.navigateToHomeAfterAuth()
            navController.navigateToTopLevel(TopLevelDestination.Nutrition)
        }

        assertCurrentRoute(AppDestination.Nutrition.route)
    }

    @Test
    fun givenAScreenInsideATab_whenTappingTheSameTab_thenItGoesBackToTheTabStart() {
        onMain {
            navController.navigateToTopLevel(TopLevelDestination.Nutrition)
            navController.navigate(AppDestination.MealDetails.createRoute(2L))
            navController.navigateToTopLevel(TopLevelDestination.Nutrition)
        }

        assertCurrentRoute(AppDestination.Nutrition.route)
    }

    @Test
    fun givenHome_whenTappingProgress_thenTheProgressDashboardIsShown() {
        onMain { navController.navigateToTopLevel(TopLevelDestination.Progress) }

        assertCurrentRoute(AppDestination.Progress.route)
    }

    @Test
    fun givenTodaysOverviewOpenedFromHome_whenGoingBack_thenTheProgressDashboardIsShown() {
        onMain {
            navController.navigateInTab(AppDestination.TodaysOverview.route)
            navController.popBackStack()
        }

        assertCurrentRoute(AppDestination.Progress.route)
    }

    @Test
    fun givenALoggedInUser_whenLoggingOut_thenOnlyTheLoginRemains() {
        onMain {
            navController.navigateInTab(AppDestination.Settings.route)
            navController.navigateToLoginAfterLogout()
        }

        val routes = navController.currentBackStack.value.mapNotNull { it.destination.route }
        assertEquals(listOf(AppDestination.AuthGraph.route, AppDestination.Login.route), routes)
    }

    private fun onMain(block: () -> Unit) {
        composeRule.runOnUiThread(block)
        composeRule.waitForIdle()
    }

    private fun assertCurrentRoute(expected: String) {
        assertEquals(expected, navController.currentDestination?.route)
    }

    private fun NavGraphBuilder.tab(graph: AppDestination, start: AppDestination, vararg others: AppDestination) {
        navigation(startDestination = start.route, route = graph.route) {
            (listOf(start) + others).forEach { composable(it.route) {} }
        }
    }
}
