package com.example.tp3_parcial_grupo_1.data.demo

import com.example.tp3_parcial_grupo_1.model.Workout

object WorkoutDemoData {
    val workouts = listOf(
        Workout(
            id = "demo_w01",
            name = "Fuerza inicial",
            level = "beginner",
            durationMinutes = 25,
            exerciseCount = 3,
            description = "Una rutina simple para comenzar a entrenar."
        ),
        Workout(
            id = "demo_w02",
            name = "Entrenamiento completo",
            level = "intermediate",
            durationMinutes = 35,
            exerciseCount = 3,
            description = "Una rutina equilibrada para todo el cuerpo."
        ),
        Workout(
            id = "demo_w03",
            name = "Fuerza y constancia",
            level = "beginner",
            durationMinutes = 30,
            exerciseCount = 3,
            description = "Una rutina para sostener el progreso."
        )
    )
}
