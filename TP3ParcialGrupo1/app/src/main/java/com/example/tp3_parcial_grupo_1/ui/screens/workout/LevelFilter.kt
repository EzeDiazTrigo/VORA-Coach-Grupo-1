package com.example.tp3_parcial_grupo_1.ui.screens.workout

import com.example.tp3_parcial_grupo_1.ui.strings.WorkoutStrings

enum class LevelFilter(val label: String) {
    ALL(WorkoutStrings.FilterAll),
    BEGINNER(WorkoutStrings.FilterBeginner),
    INTERMEDIATE(WorkoutStrings.FilterIntermediate),
    ADVANCED(WorkoutStrings.FilterAdvanced);

    fun matches(workoutLevel: String): Boolean {
        if (this == ALL) return true
        return workoutLevel.equals(label, ignoreCase = true)
    }
}
