package com.example.tp3_parcial_grupo_1.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp3_parcial_grupo_1.data.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor(
    private val repository: NotificationRepository
) : ViewModel() {

    val uiState: StateFlow<NotificationsUiState> = repository.notifications
        .map(::NotificationsUiState)
        .stateIn(viewModelScope, SharingStarted.Eagerly, NotificationsUiState(repository.notifications.value))

    fun onMarkAllAsReadClick() = repository.markAllAsRead()

    fun onNotificationClick(id: Int) = repository.markAsRead(id)
}
