package com.example.tp3_parcial_grupo_1.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp3_parcial_grupo_1.data.demo.HomeDemoData
import com.example.tp3_parcial_grupo_1.data.repository.NotificationRepository
import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    notificationRepository: NotificationRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeDemoData.dashboard)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    init {
        notificationRepository.notifications
            .onEach { items ->
                val hasUnread = items.any { it.isUnread }
                _uiState.update { it.copy(hasUnreadNotifications = hasUnread) }
            }
            .launchIn(viewModelScope)
    }

    fun onDemoNoticeClick() = _uiState.update { it.copy(message = Strings.DemoMode) }

    fun onMessageShown() = _uiState.update { it.copy(message = null) }
}
