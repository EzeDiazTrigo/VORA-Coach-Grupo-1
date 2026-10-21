package com.example.tp3_parcial_grupo_1.ui.screens.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tp3_parcial_grupo_1.R
import com.example.tp3_parcial_grupo_1.model.Workout
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppCardStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.strings.WorkoutStrings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

@Composable
fun WorkoutListRoute(
    onBackClick: () -> Unit,
    onWorkoutClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WorkoutListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearErrorMessage()
        }
    }

    WorkoutListScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onBackClick = onBackClick,
        onWorkoutClick = onWorkoutClick,
        onFilterSelect = viewModel::setFilter,
        onRetryClick = viewModel::refreshWorkouts,
        modifier = modifier
    )
}

@Composable
fun WorkoutListScreen(
    uiState: WorkoutListUiState,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onBackClick: () -> Unit,
    onWorkoutClick: (String) -> Unit,
    onFilterSelect: (String) -> Unit,
    onRetryClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val filters = listOf(
        WorkoutStrings.FilterAll,
        WorkoutStrings.FilterBeginner,
        WorkoutStrings.FilterIntermediate,
        WorkoutStrings.FilterAdvanced
    )

    val filteredWorkouts = remember(uiState.workouts, uiState.selectedLevelFilter) {
        if (uiState.selectedLevelFilter == WorkoutStrings.FilterAll) {
            uiState.workouts
        } else {
            uiState.workouts.filter { it.level.equals(uiState.selectedLevelFilter, ignoreCase = true) }
        }
    }

    AppScaffold(
        title = WorkoutStrings.Title,
        onBack = onBackClick,
        snackbarHostState = snackbarHostState,
        modifier = modifier
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = MaterialTheme.spacing.screen)
        ) {
            Text(
                text = WorkoutStrings.Subtitle,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))

            // Filter Chips
            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(filters) { filter ->
                    val isSelected = filter.equals(uiState.selectedLevelFilter, ignoreCase = true)
                    Box(
                        modifier = Modifier
                            .clip(PillShape)
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.tertiary
                                else MaterialTheme.extendedColors.mintSoft
                            )
                            .clickable { onFilterSelect(filter) }
                            .padding(horizontal = MaterialTheme.spacing.lg, vertical = MaterialTheme.spacing.sm)
                    ) {
                        Text(
                            text = filter,
                            style = MaterialTheme.typography.labelLarge,
                            color = if (isSelected) MaterialTheme.colorScheme.onTertiary
                                    else MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))

            when {
                uiState.isLoading && uiState.workouts.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                filteredWorkouts.isEmpty() -> {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = WorkoutStrings.NoWorkoutsFound,
                                style = MaterialTheme.typography.bodyLarge,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))
                            AppButton(
                                text = WorkoutStrings.Retry,
                                onClick = onRetryClick,
                                style = AppButtonStyle.Outlined
                            )
                        }
                    }
                }
                else -> {
                    LazyColumn(
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md),
                        modifier = Modifier
                            .fillMaxWidth()
                            .weight(1f)
                    ) {
                        items(filteredWorkouts, key = { it.id }) { workout ->
                            WorkoutCardItem(
                                workout = workout,
                                onClick = { onWorkoutClick(workout.id) }
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun WorkoutCardItem(
    workout: Workout,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppCard(
        onClick = onClick,
        style = AppCardStyle.Surface,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.lg)
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Box(
                    modifier = Modifier
                        .clip(PillShape)
                        .background(MaterialTheme.extendedColors.mintSoft)
                        .padding(horizontal = MaterialTheme.spacing.sm, vertical = 4.dp)
                ) {
                    Text(
                        text = workout.level.uppercase(),
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_forward),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.sm))

            Text(
                text = workout.name,
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Spacer(modifier = Modifier.height(4.dp))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_clock),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = WorkoutStrings.minutesCount(workout.durationMinutes),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.lg))
                Icon(
                    painter = painterResource(id = R.drawable.ic_dumbbell),
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurfaceVariant,
                    modifier = Modifier.size(16.dp)
                )
                Spacer(modifier = Modifier.width(4.dp))
                Text(
                    text = WorkoutStrings.exercisesCount(workout.exerciseCount),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            workout.description?.let { desc ->
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.sm))
                Text(
                    text = desc,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WorkoutListScreenPreview() {
    AppTheme {
        WorkoutListScreen(
            uiState = WorkoutListUiState(
                isLoading = false,
                workouts = listOf(
                    Workout(
                        id = "w01",
                        name = "Fuerza Total",
                        level = "Intermedio",
                        durationMinutes = 45,
                        exerciseCount = 6,
                        description = "Entrenamiento intenso enfocado en tren superior e inferior."
                    )
                )
            ),
            onBackClick = {},
            onWorkoutClick = {},
            onFilterSelect = {},
            onRetryClick = {}
        )
    }
}
