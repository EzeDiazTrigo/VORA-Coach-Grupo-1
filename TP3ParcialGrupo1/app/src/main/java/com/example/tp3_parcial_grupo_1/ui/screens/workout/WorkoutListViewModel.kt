package com.example.tp3_parcial_grupo_1.ui.screens.workout

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp3_parcial_grupo_1.data.repository.WorkoutRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutListViewModel @Inject constructor(
    private val repository: WorkoutRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(WorkoutListUiState())
    val uiState: StateFlow<WorkoutListUiState> = _uiState.asStateFlow()

    init {
        observeWorkouts()
        refreshWorkouts()
    }

    private fun observeWorkouts() {
        viewModelScope.launch {
            repository.getWorkouts().collect { workouts ->
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        workouts = workouts
                    )
                }
            }
        }
    }

    fun refreshWorkouts() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null) }
            val result = repository.refreshWorkouts()
            if (result.isFailure) {
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        errorMessage = result.exceptionOrNull()?.message ?: "Error loading workouts"
                    )
                }
            } else {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun setFilter(level: String) {
        _uiState.update { it.copy(selectedLevelFilter = level) }
    }

    fun clearErrorMessage() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
