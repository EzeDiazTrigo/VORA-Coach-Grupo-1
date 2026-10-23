package com.example.tp3_parcial_grupo_1.ui.screens.progress

import androidx.lifecycle.ViewModel
import com.example.tp3_parcial_grupo_1.data.demo.ProgressDemoData
import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class WorkoutProgressViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(
        WorkoutProgressUiState(
            period = ProgressPeriod.Week,
            progress = ProgressDemoData.workoutPeriods.getValue(ProgressPeriod.Week)
        )
    )
    val uiState: StateFlow<WorkoutProgressUiState> = _uiState.asStateFlow()

    fun onPeriodSelect(period: ProgressPeriod) = _uiState.update {
        it.copy(period = period, progress = ProgressDemoData.workoutPeriods.getValue(period))
    }

    fun onMoreClick() = _uiState.update { it.copy(message = Strings.DemoMode) }

    fun onMessageShown() = _uiState.update { it.copy(message = null) }
}
