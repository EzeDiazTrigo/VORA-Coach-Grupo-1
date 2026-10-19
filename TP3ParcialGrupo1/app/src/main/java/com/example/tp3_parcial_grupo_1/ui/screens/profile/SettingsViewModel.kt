package com.example.tp3_parcial_grupo_1.ui.screens.profile

import androidx.lifecycle.ViewModel
import com.example.tp3_parcial_grupo_1.data.repository.ProfileRepository
import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    repository: ProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(SettingsUiState(email = repository.profile.value.email))
    val uiState: StateFlow<SettingsUiState> = _uiState.asStateFlow()

    fun onDemoNoticeClick() = _uiState.update { it.copy(message = Strings.DemoMode) }

    fun onMessageShown() = _uiState.update { it.copy(message = null) }

    fun onLogOutClick() = _uiState.update { it.copy(showLogOutDialog = true) }

    fun onLogOutDismissed() = _uiState.update { it.copy(showLogOutDialog = false) }
}
