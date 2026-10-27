package com.example.tp3_parcial_grupo_1.navigation

import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class FullScreenRoutesTest {

    @Test
    fun `given the green screens, when resolving the insets, then they draw behind the system bars`() {
        assertTrue(drawsBehindSystemBars(AppDestination.Coach.route))
        assertTrue(drawsBehindSystemBars(AppDestination.PreWorkout.route))
        assertTrue(drawsBehindSystemBars(AppDestination.ActiveWorkout.route))
    }

    @Test
    fun `given a regular screen or no route, when resolving the insets, then the scaffold pads them`() {
        assertFalse(drawsBehindSystemBars(AppDestination.Home.route))
        assertFalse(drawsBehindSystemBars(AppDestination.WorkoutDetail.route))
        assertFalse(drawsBehindSystemBars(null))
    }
}
