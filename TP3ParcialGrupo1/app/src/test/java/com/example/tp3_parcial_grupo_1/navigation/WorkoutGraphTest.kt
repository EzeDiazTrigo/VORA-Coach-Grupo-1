package com.example.tp3_parcial_grupo_1.navigation

import org.junit.Assert.assertEquals
import org.junit.Test

class WorkoutGraphTest {

    @Test
    fun `WorkoutGraph start destination is Workout`() {
        assertEquals("workout/dashboard", AppDestination.Workout.route)
    }

    @Test
    fun `WorkoutGraph graph route matches AppDestination WorkoutGraph`() {
        assertEquals("workout_graph", AppDestination.WorkoutGraph.route)
    }

    @Test
    fun `WorkoutGraph sub-destinations create correct parameterized routes`() {
        assertEquals("workout/weekly_plan", AppDestination.WeeklyPlan.route)
        assertEquals("workout/detail/w100", AppDestination.WorkoutDetail.createRoute("w100"))
        assertEquals("workout/pre/w100", AppDestination.PreWorkout.createRoute("w100"))
        assertEquals("workout/active/w100", AppDestination.ActiveWorkout.createRoute("w100"))
        assertEquals("workout/exercise/w100/e200", AppDestination.ExerciseDetails.createRoute("w100", "e200"))
        assertEquals("workout/complete/w100", AppDestination.WorkoutComplete.createRoute("w100"))
    }
}
