package com.example.tp3_parcial_grupo_1.navigation

import org.junit.Assert.assertEquals
import org.junit.Test

class AppDestinationTest {

    @Test
    fun `given a workout id, when building the detail route, then it replaces the argument`() {
        assertEquals("workout/detail/w01", AppDestination.WorkoutDetail.createRoute("w01"))
    }

    @Test
    fun `given a workout id, when building the pre workout route, then it replaces the argument`() {
        assertEquals("workout/pre/w02", AppDestination.PreWorkout.createRoute("w02"))
    }

    @Test
    fun `given a workout id, when building the active workout route, then it replaces the argument`() {
        assertEquals("workout/active/w03", AppDestination.ActiveWorkout.createRoute("w03"))
    }

    @Test
    fun `given a workout and an exercise, when building the exercise route, then it replaces both arguments`() {
        assertEquals("workout/exercise/w01/e02", AppDestination.ExerciseDetails.createRoute("w01", "e02"))
    }

    @Test
    fun `given a workout id, when building the complete route, then it replaces the argument`() {
        assertEquals("workout/complete/w01", AppDestination.WorkoutComplete.createRoute("w01"))
    }

    @Test
    fun `given a meal id, when building the meal details route, then it replaces the argument`() {
        assertEquals("nutrition/meal/7", AppDestination.MealDetails.createRoute(7L))
    }

    @Test
    fun `given the route templates, when reading their arguments, then they use the shared argument names`() {
        assertEquals("workout/detail/{workoutId}", AppDestination.WorkoutDetail.route)
        assertEquals("workout/exercise/{workoutId}/{exerciseId}", AppDestination.ExerciseDetails.route)
        assertEquals("nutrition/meal/{mealId}", AppDestination.MealDetails.route)
    }

    @Test
    fun `given a date, when building the weekly plan route, then it carries the date as a query`() {
        assertEquals("workout/weekly_plan?date=2026-10-27", AppDestination.WeeklyPlan.createRoute("2026-10-27"))
        assertEquals("workout/weekly_plan", AppDestination.WeeklyPlan.createRoute())
        assertEquals("workout/weekly_plan?date={date}", AppDestination.WeeklyPlan.pattern)
    }

    @Test
    fun `given a question, when building the coach route, then it is url encoded`() {
        assertEquals("coach?prompt=How%20do%20I%20fix%20my%20grip%3F", AppDestination.Coach.createRoute("How do I fix my grip?"))
        assertEquals("coach", AppDestination.Coach.createRoute())
        assertEquals("coach", AppDestination.Coach.createRoute("  "))
        assertEquals("coach?prompt={prompt}", AppDestination.Coach.pattern)
    }
}
