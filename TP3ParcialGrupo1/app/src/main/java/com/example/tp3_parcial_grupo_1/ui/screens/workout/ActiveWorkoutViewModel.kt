package com.example.tp3_parcial_grupo_1.ui.screens.workout

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp3_parcial_grupo_1.data.repository.WorkoutRepository
import com.example.tp3_parcial_grupo_1.model.Workout
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val DefaultSets = 3
private const val DefaultRestSeconds = 60
private const val InitialRestSeconds = 78
private const val AddedRestSeconds = 30
private const val MinWeightKg = 2.5
private const val OneSecondMs = 1_000L

data class ActiveWorkoutUiState(
    val workout: Workout? = null,
    val exerciseIndex: Int = 0,
    val activeSetIndex: Int = 0,
    val weightKg: Double = 45.0,
    val restTimeSeconds: Int = InitialRestSeconds,
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

    private var restJob: Job? = null

    init {
        loadWorkout()
        startRest(InitialRestSeconds)
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

    // El descanso cuenta hacia atrás de a un segundo y se detiene en cero.
    private fun startRest(seconds: Int) {
        restJob?.cancel()
        _uiState.update { it.copy(restTimeSeconds = seconds) }
        if (seconds <= 0) return
        restJob = viewModelScope.launch {
            repeat(seconds) {
                delay(OneSecondMs)
                _uiState.update { it.copy(restTimeSeconds = (it.restTimeSeconds - 1).coerceAtLeast(0)) }
            }
        }
    }

    fun addRestTime(seconds: Int = AddedRestSeconds) {
        startRest(_uiState.value.restTimeSeconds + seconds)
    }

    fun skipRest() = startRest(0)

    fun addWeight(delta: Double) {
        _uiState.update { it.copy(weightKg = (it.weightKg + delta).coerceAtLeast(MinWeightKg)) }
    }

    fun selectSet(setIndex: Int) {
        _uiState.update { it.copy(activeSetIndex = setIndex) }
    }

    // Termina la serie actual: pasa a la siguiente, luego al próximo ejercicio y, tras el último, cierra el entrenamiento.
    fun completeCurrentSet(onFinished: () -> Unit) {
        val state = _uiState.value
        val exercises = state.workout?.exercises.orEmpty()
        val exercise = exercises.getOrNull(state.exerciseIndex)
        val sets = exercise?.sets ?: DefaultSets
        val rest = exercise?.restSeconds ?: DefaultRestSeconds
        when {
            state.activeSetIndex < sets - 1 -> {
                _uiState.update { it.copy(activeSetIndex = it.activeSetIndex + 1) }
                startRest(rest)
            }
            state.exerciseIndex < exercises.size - 1 -> {
                _uiState.update { it.copy(exerciseIndex = it.exerciseIndex + 1, activeSetIndex = 0) }
                startRest(rest)
            }
            else -> onFinished()
        }
    }
}
