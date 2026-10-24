package com.example.tp3_parcial_grupo_1.ui.strings

object WorkoutStrings {
    const val Title = "Workouts"
    const val Subtitle = "Choose a routine for your training session today."
    const val WorkoutDetailTitle = "Workout Details"
    const val ExercisesTitle = "Exercises in this Routine"
    const val StartWorkout = "Start Workout"
    const val FilterAll = "All"
    const val FilterBeginner = "Beginner"
    const val FilterIntermediate = "Intermediate"
    const val FilterAdvanced = "Advanced"
    const val Loading = "Loading workouts..."
    const val Retry = "Retry"
    const val NoWorkoutsFound = "No workouts found for this level."
    const val ErrorGeneric = "An error occurred while loading. Please try again."
    const val RestTimeLabel = "Rest"

    fun exercisesCount(count: Int) = "$count exercises"
    fun minutesCount(minutes: Int) = "$minutes min"
    fun setsReps(sets: Int, reps: Int) = "$sets sets x $reps reps"
    fun restSeconds(seconds: Int) = "$seconds sec rest"
}
