package com.example.tp3_parcial_grupo_1.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.tp3_parcial_grupo_1.data.demo.HomeDemoData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class NotificationsViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(NotificationsUiState(HomeDemoData.notifications))
    val uiState: StateFlow<NotificationsUiState> = _uiState.asStateFlow()

    fun onMarkAllAsReadClick() = _uiState.update { state ->
        state.copy(items = state.items.map { it.copy(isUnread = false) })
    }

    fun onNotificationClick(id: Int) = _uiState.update { state ->
        state.copy(items = state.items.map { if (it.id == id) it.copy(isUnread = false) else it })
    }
}
