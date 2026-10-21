package com.example.tp3_parcial_grupo_1.navigation

import org.junit.Assert.assertEquals
import org.junit.Assert.assertNull
import org.junit.Test

class TopLevelDestinationTest {

    @Test
    fun `given the home dashboard, when resolving the bottom bar, then home is selected`() {
        assertEquals(TopLevelDestination.Home, bottomBarSelection(AppDestination.Home.route))
    }

    @Test
    fun `given a meal details entry, when resolving the bottom bar, then nutrition is selected`() {
        assertEquals(TopLevelDestination.Nutrition, bottomBarSelection(AppDestination.MealDetails.route))
    }

    @Test
    fun `given a progress screen with bottom bar, when resolving, then progress is selected`() {
        assertEquals(TopLevelDestination.Progress, bottomBarSelection(AppDestination.HealthTrends.route))
    }

    @Test
    fun `given the workout detail, when resolving the bottom bar, then it is hidden`() {
        assertNull(bottomBarSelection(AppDestination.WorkoutDetail.route))
    }

    @Test
    fun `given the weekly review, when resolving the bottom bar, then it is hidden`() {
        assertNull(bottomBarSelection(AppDestination.WeeklyReview.route))
    }

    @Test
    fun `given auth and coach screens, when resolving the bottom bar, then it is hidden`() {
        assertNull(bottomBarSelection(AppDestination.Login.route))
        assertNull(bottomBarSelection(AppDestination.Coach.route))
    }

    @Test
    fun `given no current route, when resolving the bottom bar, then it is hidden`() {
        assertNull(bottomBarSelection(null))
    }

    @Test
    fun `given each tab, when reading its graph, then it opens the area graph`() {
        assertEquals(AppDestination.HomeGraph.route, TopLevelDestination.Home.graphRoute)
        assertEquals(AppDestination.WorkoutGraph.route, TopLevelDestination.Workout.graphRoute)
        assertEquals(AppDestination.NutritionGraph.route, TopLevelDestination.Nutrition.graphRoute)
        assertEquals(AppDestination.ProgressGraph.route, TopLevelDestination.Progress.graphRoute)
        assertEquals(AppDestination.ProfileGraph.route, TopLevelDestination.Profile.graphRoute)
    }
}
