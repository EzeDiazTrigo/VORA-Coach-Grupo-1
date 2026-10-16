package com.example.tp3_parcial_grupo_1.ui.screens.home

data class MacroProgress(val current: Int, val goal: Int) {
    val fraction: Float get() = if (goal == 0) 0f else (current.toFloat() / goal).coerceIn(0f, 1f)
}

data class RecommendedWorkout(
    val title: String,
    val minutes: Int,
    val exercises: Int,
    val level: String,
    val estimatedVolumeKg: String
)

data class HomeUiState(
    val userName: String,
    val initials: String,
    val tip: String,
    val calories: MacroProgress,
    val protein: MacroProgress,
    val carbs: MacroProgress,
    val dailyGoal: Float,
    val steps: String,
    val distance: String,
    val activeKcal: String,
    val workout: RecommendedWorkout,
    val message: String? = null
)

enum class NotificationType { Coach, Workout, Nutrition, Milestone }

enum class NotificationGroup { Today, Yesterday }

data class NotificationItem(
    val id: Int,
    val type: NotificationType,
    val group: NotificationGroup,
    val category: String,
    val title: String,
    val body: String,
    val timeAgo: String,
    val isUnread: Boolean
)

data class NotificationsUiState(val items: List<NotificationItem>) {
    val hasUnread: Boolean get() = items.any { it.isUnread }
}
