package com.example.tp3_parcial_grupo_1.ui.screens.home

import androidx.lifecycle.ViewModel
import com.example.tp3_parcial_grupo_1.data.demo.HomeDemoData
import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(HomeDemoData.dashboard)
    val uiState: StateFlow<HomeUiState> = _uiState.asStateFlow()

    fun onDemoNoticeClick() = _uiState.update { it.copy(message = Strings.DemoMode) }

    fun onMessageShown() = _uiState.update { it.copy(message = null) }
}
