package com.example.tp3_parcial_grupo_1.ui.screens.profile

import com.example.tp3_parcial_grupo_1.data.repository.ProfileRepository
import com.example.tp3_parcial_grupo_1.model.CoachingStyle
import com.example.tp3_parcial_grupo_1.model.PrimaryGoal
import com.example.tp3_parcial_grupo_1.model.WorkoutPreference
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertTrue
import org.junit.Test

class GoalsPreferencesViewModelTest {
    private val repository = ProfileRepository()
    private val viewModel = GoalsPreferencesViewModel(repository)

    @Test
    fun `starts with the current profile preferences`() {
        val state = viewModel.uiState.value

        assertEquals(PrimaryGoal.ImproveFitness, state.goal)
        assertEquals(4, state.trainingDays)
        assertFalse(state.isSaved)
    }

    @Test
    fun `selecting options does not touch the profile until saved`() {
        viewModel.onGoalSelected(PrimaryGoal.LoseBodyFat)

        assertEquals(PrimaryGoal.LoseBodyFat, viewModel.uiState.value.goal)
        assertEquals(PrimaryGoal.ImproveFitness, repository.profile.value.goal)
    }

    @Test
    fun `save writes every selection to the shared profile`() {
        viewModel.onGoalSelected(PrimaryGoal.BuildStrength)
        viewModel.onTrainingDaysSelected(6)
        viewModel.onWorkoutPreferenceSelected(WorkoutPreference.Hiit)
        viewModel.onCoachingStyleSelected(CoachingStyle.Direct)

        viewModel.onSaveClick()

        val profile = repository.profile.value
        assertTrue(viewModel.uiState.value.isSaved)
        assertEquals(PrimaryGoal.BuildStrength, profile.goal)
        assertEquals(6, profile.trainingDaysPerWeek)
        assertEquals(WorkoutPreference.Hiit, profile.preferredWorkout)
        assertEquals(CoachingStyle.Direct, profile.coachingStyle)
    }
}
