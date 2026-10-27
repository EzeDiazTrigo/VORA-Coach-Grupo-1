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

        assertEquals(setOf(PrimaryGoal.BuildStrength, PrimaryGoal.ImproveFitness), state.goals)
        assertEquals(4, state.trainingDays)
        assertFalse(state.isSaved)
    }

    @Test
    fun `selecting options does not touch the profile until saved`() {
        viewModel.onGoalToggled(PrimaryGoal.LoseBodyFat)

        assertTrue(PrimaryGoal.LoseBodyFat in viewModel.uiState.value.goals)
        assertFalse(PrimaryGoal.LoseBodyFat in repository.profile.value.goals)
    }

    @Test
    fun `save writes every selection to the shared profile`() {
        viewModel.onGoalToggled(PrimaryGoal.BuildStrength)
        viewModel.onGoalToggled(PrimaryGoal.BuildHealthyHabits)
        viewModel.onTrainingDaysSelected(6)
        viewModel.onWorkoutPreferenceSelected(WorkoutPreference.Hiit)
        viewModel.onCoachingStyleSelected(CoachingStyle.Direct)

        viewModel.onSaveClick()

        val profile = repository.profile.value
        assertTrue(viewModel.uiState.value.isSaved)
        assertEquals(setOf(PrimaryGoal.ImproveFitness, PrimaryGoal.BuildHealthyHabits), profile.goals)
        assertEquals(6, profile.trainingDaysPerWeek)
        assertEquals(WorkoutPreference.Hiit, profile.preferredWorkout)
        assertEquals(CoachingStyle.Direct, profile.coachingStyle)
    }

    @Test
    fun `toggling a goal alternates it and never leaves the list empty`() {
        viewModel.onGoalToggled(PrimaryGoal.BuildStrength)
        assertEquals(setOf(PrimaryGoal.ImproveFitness), viewModel.uiState.value.goals)

        viewModel.onGoalToggled(PrimaryGoal.ImproveFitness)
        assertEquals(setOf(PrimaryGoal.ImproveFitness), viewModel.uiState.value.goals)

        viewModel.onGoalToggled(PrimaryGoal.BuildStrength)
        assertEquals(setOf(PrimaryGoal.BuildStrength, PrimaryGoal.ImproveFitness), viewModel.uiState.value.goals)
    }
}
