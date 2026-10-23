package com.example.tp3_parcial_grupo_1.ui.screens.progress

import androidx.lifecycle.ViewModel
import com.example.tp3_parcial_grupo_1.data.demo.ProgressDemoData
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

@HiltViewModel
class WeeklyReviewViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(ProgressDemoData.weeklyReview)
    val uiState: StateFlow<WeeklyReviewUiState> = _uiState.asStateFlow()
}
