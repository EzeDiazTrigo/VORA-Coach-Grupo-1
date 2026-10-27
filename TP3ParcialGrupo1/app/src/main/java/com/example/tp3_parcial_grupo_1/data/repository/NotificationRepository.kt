package com.example.tp3_parcial_grupo_1.data.repository

import com.example.tp3_parcial_grupo_1.data.demo.HomeDemoData
import com.example.tp3_parcial_grupo_1.model.NotificationItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject
import javax.inject.Singleton

// Notificaciones de demostración en memoria: las comparten Inicio (punto de la campana) y la lista de notificaciones.
@Singleton
class NotificationRepository @Inject constructor() {

    private val _notifications = MutableStateFlow(HomeDemoData.notifications)
    val notifications: StateFlow<List<NotificationItem>> = _notifications.asStateFlow()

    fun markAllAsRead() = _notifications.update { items -> items.map { it.copy(isUnread = false) } }

    fun markAsRead(id: Int) = _notifications.update { items ->
        items.map { if (it.id == id) it.copy(isUnread = false) else it }
    }
}
