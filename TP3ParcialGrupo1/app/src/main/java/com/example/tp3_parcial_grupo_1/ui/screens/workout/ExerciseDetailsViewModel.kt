package com.example.tp3_parcial_grupo_1.ui.screens.workout

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tp3_parcial_grupo_1.data.repository.WorkoutRepository
import com.example.tp3_parcial_grupo_1.model.Exercise
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ExerciseDetailsUiState(
    val exercise: Exercise? = null,
    val isLoading: Boolean = false
)

@HiltViewModel
class ExerciseDetailsViewModel @Inject constructor(
    private val repository: WorkoutRepository,
    savedStateHandle: SavedStateHandle
) : ViewModel() {

    val workoutId: String = savedStateHandle.get<String>("workoutId") ?: "w01"
    val exerciseId: String = savedStateHandle.get<String>("exerciseId") ?: "e01"

    private val _uiState = MutableStateFlow(ExerciseDetailsUiState(isLoading = true))
    val uiState: StateFlow<ExerciseDetailsUiState> = _uiState.asStateFlow()

    init {
        loadExerciseDetails()
    }

    private fun loadExerciseDetails() {
        viewModelScope.launch {
            repository.getWorkoutById(workoutId).collect { workout ->
                val found = workout?.exercises?.find { it.id == exerciseId }
                    ?: workout?.exercises?.find { it.name.equals(exerciseId, ignoreCase = true) }
                    ?: workout?.exercises?.firstOrNull()

                _uiState.update {
                    it.copy(
                        exercise = found,
                        isLoading = false
                    )
                }
            }
        }
    }
}
