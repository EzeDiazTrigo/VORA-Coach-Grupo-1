package com.example.tp3_parcial_grupo_1.ui.screens.profile

import androidx.lifecycle.ViewModel
import com.example.tp3_parcial_grupo_1.data.repository.ProfileRepository
import com.example.tp3_parcial_grupo_1.model.CoachingStyle
import com.example.tp3_parcial_grupo_1.model.PrimaryGoal
import com.example.tp3_parcial_grupo_1.model.WorkoutPreference
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import javax.inject.Inject

@HiltViewModel
class GoalsPreferencesViewModel @Inject constructor(
    private val repository: ProfileRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(repository.profile.value.toGoalsUiState())
    val uiState: StateFlow<GoalsPreferencesUiState> = _uiState.asStateFlow()

    fun onGoalSelected(goal: PrimaryGoal) = _uiState.update { it.copy(goal = goal) }

    fun onTrainingDaysSelected(days: Int) = _uiState.update { it.copy(trainingDays = days) }

    fun onWorkoutPreferenceSelected(preference: WorkoutPreference) =
        _uiState.update { it.copy(workoutPreference = preference) }

    fun onCoachingStyleSelected(style: CoachingStyle) = _uiState.update { it.copy(coachingStyle = style) }

    fun onSaveClick() {
        val state = _uiState.value
        repository.update {
            it.copy(
                goal = state.goal,
                trainingDaysPerWeek = state.trainingDays,
                preferredWorkout = state.workoutPreference,
                coachingStyle = state.coachingStyle
            )
        }
        _uiState.update { it.copy(isSaved = true) }
    }
}
