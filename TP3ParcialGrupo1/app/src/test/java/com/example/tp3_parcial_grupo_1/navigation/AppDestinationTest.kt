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
}
