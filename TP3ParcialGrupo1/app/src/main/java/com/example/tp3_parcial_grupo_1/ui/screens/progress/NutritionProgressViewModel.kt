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
class NutritionProgressViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(
        NutritionProgressUiState(
            period = ProgressPeriod.Week,
            progress = ProgressDemoData.nutritionPeriods.getValue(ProgressPeriod.Week),
            macro = NutritionMacro.Protein,
            target = ProgressDemoData.macroTargets.getValue(NutritionMacro.Protein)
        )
    )
    val uiState: StateFlow<NutritionProgressUiState> = _uiState.asStateFlow()

    fun onPeriodSelect(period: ProgressPeriod) = _uiState.update {
        it.copy(period = period, progress = ProgressDemoData.nutritionPeriods.getValue(period))
    }

    fun onMacroSelect(macro: NutritionMacro) = _uiState.update {
        it.copy(macro = macro, target = ProgressDemoData.macroTargets.getValue(macro))
    }

    fun onMoreClick() = _uiState.update { it.copy(message = Strings.DemoMode) }

    fun onMessageShown() = _uiState.update { it.copy(message = null) }
}
