package com.example.tp3_parcial_grupo_1.navigation

import com.example.tp3_parcial_grupo_1.ui.screens.home.NotificationType
import org.junit.Assert.assertEquals
import org.junit.Test

class HomeGraphTest {

    @Test
    fun `given a coach notification, when opening it, then it goes to the weekly review`() {
        assertEquals(AppDestination.WeeklyReview.route, NotificationType.Coach.destinationRoute())
    }

    @Test
    fun `given a workout notification, when opening it, then it goes to the weekly plan`() {
        assertEquals(AppDestination.WeeklyPlan.route, NotificationType.Workout.destinationRoute())
    }

    @Test
    fun `given a nutrition notification, when opening it, then it goes to the nutrition dashboard`() {
        assertEquals(AppDestination.Nutrition.route, NotificationType.Nutrition.destinationRoute())
    }

    @Test
    fun `given a milestone notification, when opening it, then it goes to the workout progress`() {
        assertEquals(AppDestination.WorkoutProgress.route, NotificationType.Milestone.destinationRoute())
    }
}
