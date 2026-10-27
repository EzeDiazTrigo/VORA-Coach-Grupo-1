package com.example.tp3_parcial_grupo_1.data.demo

import com.example.tp3_parcial_grupo_1.model.NotificationGroup
import com.example.tp3_parcial_grupo_1.model.NotificationItem
import com.example.tp3_parcial_grupo_1.model.NotificationType
import com.example.tp3_parcial_grupo_1.ui.screens.home.HomeUiState
import com.example.tp3_parcial_grupo_1.ui.screens.home.MacroProgress
import com.example.tp3_parcial_grupo_1.ui.screens.home.RecommendedWorkout

// Datos de demostración: no provienen de ningún servidor.
object HomeDemoData {
    val dashboard = HomeUiState(
        userName = "Alex Morgan",
        tip = "You're on track. A protein-rich dinner would help you reach your daily target.",
        calories = MacroProgress(current = 1420, goal = 2100),
        protein = MacroProgress(current = 104, goal = 140),
        carbs = MacroProgress(current = 152, goal = 220),
        dailyGoal = 0.72f,
        steps = "7,842",
        distance = "5.6 km",
        activeKcal = "312 kcal",
        workout = RecommendedWorkout(
            title = "Upper Body Strength",
            minutes = 42,
            exercises = 5,
            level = "Intermediate",
            estimatedVolumeKg = "2,340"
        )
    )

    val notifications = listOf(
        NotificationItem(
            id = 1, type = NotificationType.Coach, group = NotificationGroup.Today,
            category = "VORA Coach", title = "Your weekly health review is ready.",
            body = "We noticed your training consistency improved by 14% over the last week.",
            timeAgo = "2h ago", isUnread = true
        ),
        NotificationItem(
            id = 2, type = NotificationType.Workout, group = NotificationGroup.Today,
            category = "Workout Plan", title = "Upper Body Strength is ready.",
            body = "Equipped for home/gym. Approx. 42 minutes.",
            timeAgo = "4h ago", isUnread = true
        ),
        NotificationItem(
            id = 3, type = NotificationType.Nutrition, group = NotificationGroup.Today,
            category = "Nutrition Goal", title = "Protein target milestone alert.",
            body = "You require 36g more of protein to hit today's UK performance baseline.",
            timeAgo = "6h ago", isUnread = false
        ),
        NotificationItem(
            id = 4, type = NotificationType.Milestone, group = NotificationGroup.Yesterday,
            category = "Milestone Reached", title = "4-day workout streak logged!",
            body = "Outstanding work, Alex. Let's make it 5 today.",
            timeAgo = "1d ago", isUnread = false
        )
    )
}
