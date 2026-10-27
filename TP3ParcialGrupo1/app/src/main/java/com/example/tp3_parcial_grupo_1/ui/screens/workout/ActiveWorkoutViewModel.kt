package com.example.tp3_parcial_grupo_1.ui.screens.workout

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp3_parcial_grupo_1.data.repository.WorkoutRepository
import com.example.tp3_parcial_grupo_1.model.Workout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ActiveWorkoutUiState(
    val workout: Workout? = null,
    val activeSetIndex: Int = 1, // 0: Set 1, 1: Set 2, 2: Set 3
    val weightKg: Double = 45.0,
    val restTimeSeconds: Int = 78,
    val isLoading: Boolean = false
)

@HiltViewModel
class ActiveWorkoutViewModel @Inject constructor(
    private val repository: WorkoutRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val workoutId: String = savedStateHandle.get<String>("workoutId") ?: "w01"

    private val _uiState = MutableStateFlow(ActiveWorkoutUiState(isLoading = true))
    val uiState: StateFlow<ActiveWorkoutUiState> = _uiState.asStateFlow()

    init {
        loadWorkout()
        startTimer()
    }

    private fun loadWorkout() {
        viewModelScope.launch {
            repository.getWorkoutById(workoutId).collect { workout ->
                _uiState.update {
                    it.copy(workout = workout, isLoading = false)
                }
            }
        }
    }

    private fun startTimer() {
        viewModelScope.launch {
            while (true) {
                delay(1000L)
                if (_uiState.value.restTimeSeconds > 0) {
                    _uiState.update { state ->
                        if (state.restTimeSeconds > 0) {
                            state.copy(restTimeSeconds = state.restTimeSeconds - 1)
                        } else {
                            state
                        }
                    }
                }
            }
        }
    }

    fun addRestTime(seconds: Int = 30) {
        _uiState.update { it.copy(restTimeSeconds = it.restTimeSeconds + seconds) }
    }

    fun skipRest() {
        _uiState.update { it.copy(restTimeSeconds = 0) }
    }

    fun addWeight(delta: Double) {
        _uiState.update { it.copy(weightKg = (it.weightKg + delta).coerceAtLeast(2.5)) }
    }

    fun selectSet(setIndex: Int) {
        _uiState.update { it.copy(activeSetIndex = setIndex) }
    }

    fun completeCurrentSet(onFinished: () -> Unit) {
        val currentState = _uiState.value
        if (currentState.activeSetIndex < 2) {
            _uiState.update { it.copy(activeSetIndex = it.activeSetIndex + 1) }
        } else {
            onFinished()
        }
    }
}
