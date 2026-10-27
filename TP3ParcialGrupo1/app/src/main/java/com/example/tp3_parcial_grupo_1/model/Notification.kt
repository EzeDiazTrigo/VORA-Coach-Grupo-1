package com.example.tp3_parcial_grupo_1.model

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
