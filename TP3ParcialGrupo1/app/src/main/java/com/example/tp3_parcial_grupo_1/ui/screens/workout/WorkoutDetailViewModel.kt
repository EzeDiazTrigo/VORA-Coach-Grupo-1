package com.example.tp3_parcial_grupo_1.ui.screens.workout

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp3_parcial_grupo_1.data.repository.WorkoutRepository
import com.example.tp3_parcial_grupo_1.ui.strings.WorkoutStrings
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class WorkoutDetailViewModel @Inject constructor(
    private val repository: WorkoutRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val workoutId: String = checkNotNull(savedStateHandle["workoutId"])

    private val _uiState = MutableStateFlow(WorkoutDetailUiState(isLoading = true))
    val uiState: StateFlow<WorkoutDetailUiState> = _uiState.asStateFlow()

    private var hasApiRefreshCompleted = false

    init {
        observeWorkoutDetail()
        refreshWorkoutDetail()
    }

    private fun observeWorkoutDetail() {
        viewModelScope.launch {
            repository.getWorkoutById(workoutId).collect { workout ->
                _uiState.update {
                    it.copy(
                        workout = workout,
                        isLoading = if (workout != null || hasApiRefreshCompleted) false else it.isLoading
                    )
                }
            }
        }
    }

    fun refreshWorkoutDetail() {
        viewModelScope.launch {
            _uiState.update { it.copy(isLoading = true, errorMessage = null, isUnavailableOffline = false) }
            val result = repository.refreshWorkoutDetails(workoutId)
            hasApiRefreshCompleted = true
            if (result.isFailure) {
                // Sin red y sin copia en Room: el detalle nunca se descargó, así que no está disponible sin conexión.
                val unavailableOffline = result.exceptionOrNull() is IOException && _uiState.value.workout == null
                _uiState.update {
                    it.copy(
                        isLoading = false,
                        // La pantalla ya lo explica con su propio mensaje: no hace falta un aviso aparte.
                        errorMessage = if (unavailableOffline) null else WorkoutStrings.ErrorGeneric,
                        isUnavailableOffline = unavailableOffline
                    )
                }
            } else {
                _uiState.update { it.copy(isLoading = false) }
            }
        }
    }

    fun clearErrorMessage() {
        _uiState.update { it.copy(errorMessage = null) }
    }
}
