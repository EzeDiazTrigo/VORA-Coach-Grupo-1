package com.example.tp3_parcial_grupo_1.ui.screens.profile

import com.example.tp3_parcial_grupo_1.ui.components.AppSectionLabel
import com.example.tp3_parcial_grupo_1.ui.components.AppChipStyle
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tp3_parcial_grupo_1.data.demo.ProfileDemoData
import com.example.tp3_parcial_grupo_1.model.CoachingStyle
import com.example.tp3_parcial_grupo_1.model.PrimaryGoal
import com.example.tp3_parcial_grupo_1.model.WorkoutPreference
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppChip
import com.example.tp3_parcial_grupo_1.ui.components.AppOptionCard
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.strings.ProfileStrings
import com.example.tp3_parcial_grupo_1.ui.strings.TrainingDaysOptions
import com.example.tp3_parcial_grupo_1.ui.strings.chipLabel
import com.example.tp3_parcial_grupo_1.ui.strings.description
import com.example.tp3_parcial_grupo_1.ui.strings.title
import com.example.tp3_parcial_grupo_1.ui.strings.trainingDaysLabel
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

@Composable
fun GoalsPreferencesRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: GoalsPreferencesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) onBackClick()
    }

    GoalsPreferencesScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onGoalSelected = viewModel::onGoalSelected,
        onTrainingDaysSelected = viewModel::onTrainingDaysSelected,
        onWorkoutPreferenceSelected = viewModel::onWorkoutPreferenceSelected,
        onCoachingStyleSelected = viewModel::onCoachingStyleSelected,
        onSaveClick = viewModel::onSaveClick,
        modifier = modifier
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun GoalsPreferencesScreen(
    uiState: GoalsPreferencesUiState,
    onBackClick: () -> Unit,
    onGoalSelected: (PrimaryGoal) -> Unit,
    onTrainingDaysSelected: (Int) -> Unit,
    onWorkoutPreferenceSelected: (WorkoutPreference) -> Unit,
    onCoachingStyleSelected: (CoachingStyle) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppScaffold(
        modifier = modifier.fillMaxSize(),
        title = ProfileStrings.GoalsHeading,
        onBack = onBackClick
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = MaterialTheme.spacing.screen, vertical = MaterialTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xl)
        ) {
            Text(
                ProfileStrings.GoalsIntro,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            Section(label = ProfileStrings.PrimaryGoalLabel, prompt = ProfileStrings.PrimaryGoalPrompt) {
                PrimaryGoal.entries.forEach { goal ->
                    AppOptionCard(
                        title = goal.title(),
                        description = goal.description(),
                        selected = goal == uiState.goal,
                        onClick = { onGoalSelected(goal) }
                    )
                }
            }
            Section(label = ProfileStrings.TrainingFrequencyLabel) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)
                ) {
                    TrainingDaysOptions.forEach { days ->
                        AppChip(
                            text = trainingDaysLabel(days),
                            selected = days == uiState.trainingDays,
                            onClick = { onTrainingDaysSelected(days) },
                            style = AppChipStyle.Dark
                        )
                    }
                }
            }
            Section(label = ProfileStrings.WorkoutPreferenceLabel) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)
                ) {
                    WorkoutPreference.entries.forEach { preference ->
                        AppChip(
                            text = preference.chipLabel(),
                            selected = preference == uiState.workoutPreference,
                            onClick = { onWorkoutPreferenceSelected(preference) }
                        )
                    }
                }
            }
            Section(label = ProfileStrings.CoachingStyleLabel, prompt = ProfileStrings.CoachingStylePrompt) {
                CoachingStyle.entries.forEach { style ->
                    AppOptionCard(
                        title = style.title(),
                        description = style.description(),
                        selected = style == uiState.coachingStyle,
                        onClick = { onCoachingStyleSelected(style) }
                    )
                }
            }
            AppButton(text = ProfileStrings.SavePreferences, onClick = onSaveClick)
        }
    }
}

@Composable
private fun Section(
    label: String,
    prompt: String? = null,
    content: @Composable () -> Unit
) {
    Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)) {
        AppSectionLabel(if (prompt != null) "$label — $prompt" else label)
        content()
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 1400)
@Composable
private fun GoalsPreferencesScreenPreview() {
    AppTheme {
        GoalsPreferencesScreen(
            uiState = ProfileDemoData.profile.toGoalsUiState(),
            onBackClick = {}, onGoalSelected = {}, onTrainingDaysSelected = {},
            onWorkoutPreferenceSelected = {}, onCoachingStyleSelected = {}, onSaveClick = {}
        )
    }
}
