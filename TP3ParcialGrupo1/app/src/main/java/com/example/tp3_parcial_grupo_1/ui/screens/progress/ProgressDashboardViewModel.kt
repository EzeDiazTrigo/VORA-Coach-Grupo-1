package com.example.tp3_parcial_grupo_1.ui.screens.progress

import androidx.lifecycle.ViewModel
import com.example.tp3_parcial_grupo_1.data.demo.ProgressDemoData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class ProgressDashboardViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(
        ProgressDashboardUiState(
            period = ProgressPeriod.Week,
            progress = ProgressDemoData.periods.getValue(ProgressPeriod.Week),
            activeGoal = ProgressDemoData.activeGoal
        )
    )
    val uiState: StateFlow<ProgressDashboardUiState> = _uiState.asStateFlow()

    fun onPeriodSelect(period: ProgressPeriod) = _uiState.update {
        it.copy(period = period, progress = ProgressDemoData.periods.getValue(period))
    }
}
