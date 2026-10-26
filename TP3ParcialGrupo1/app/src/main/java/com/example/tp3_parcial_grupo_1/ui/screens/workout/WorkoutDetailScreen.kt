package com.example.tp3_parcial_grupo_1.ui.screens.workout

import androidx.compose.foundation.background
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
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tp3_parcial_grupo_1.R
import com.example.tp3_parcial_grupo_1.model.Exercise
import com.example.tp3_parcial_grupo_1.model.Workout
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppCardStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.components.VoraLogo
import com.example.tp3_parcial_grupo_1.ui.strings.WorkoutStrings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

@Composable
fun WorkoutDetailRoute(
    onBackClick: () -> Unit,
    onStartWorkoutClick: (String) -> Unit,
    onExerciseClick: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WorkoutDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.errorMessage) {
        uiState.errorMessage?.let { msg ->
            snackbarHostState.showSnackbar(msg)
            viewModel.clearErrorMessage()
        }
    }

    WorkoutDetailScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onBackClick = onBackClick,
        onStartWorkoutClick = {
            uiState.workout?.id?.let { onStartWorkoutClick(it) }
        },
        onExerciseClick = onExerciseClick,
        onRetryClick = viewModel::refreshWorkoutDetail,
        modifier = modifier
    )
}

@Composable
fun WorkoutDetailScreen(
    uiState: WorkoutDetailUiState,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onBackClick: () -> Unit,
    onStartWorkoutClick: () -> Unit,
    onExerciseClick: (String) -> Unit,
    onRetryClick: () -> Unit
) {
    val workout = uiState.workout

    AppScaffold(
        snackbarHostState = snackbarHostState,
        modifier = modifier
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                uiState.isLoading && workout == null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                workout == null -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = WorkoutStrings.ErrorGeneric,
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
                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(horizontal = MaterialTheme.spacing.screen)
                    ) {
                        // Centered Top Header Bar
                        Row(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = MaterialTheme.spacing.xs),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            IconButton(
                                onClick = onBackClick,
                                colors = IconButtonDefaults.iconButtonColors(
                                    containerColor = MaterialTheme.colorScheme.surface
                                ),
                                modifier = Modifier.size(40.dp)
                            ) {
                                Icon(
                                    painter = painterResource(id = R.drawable.ic_chevron_left),
                                    contentDescription = "Back",
                                    tint = MaterialTheme.colorScheme.onSurface,
                                    modifier = Modifier.size(20.dp)
                                )
                            }

                            Text(
                                text = "Workout Overview",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface,
                                textAlign = TextAlign.Center,
                                modifier = Modifier.weight(1f)
                            )

                            Spacer(modifier = Modifier.width(40.dp))
                        }

                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))

                        LazyColumn(
                            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md),
                            modifier = Modifier
                                .fillMaxWidth()
                                .weight(1f)
                        ) {
                            item {
                                Column(modifier = Modifier.fillMaxWidth()) {
                                    Text(
                                        text = workout.name,
                                        style = MaterialTheme.typography.headlineMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onBackground
                                    )

                                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))

                                    Text(
                                        text = "${workout.durationMinutes} min   •   ${workout.level}   •   ${WorkoutStrings.exercisesCount(workout.exercises.size)}",
                                        style = MaterialTheme.typography.bodyMedium,
                                        color = MaterialTheme.colorScheme.onSurfaceVariant
                                    )

                                    workout.description?.let { description ->
                                        Spacer(modifier = Modifier.height(4.dp))

                                        Text(
                                            text = description,
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }

                            // Coach Note Box
                            item {
                                Box(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .clip(MaterialTheme.shapes.large)
                                        .background(MaterialTheme.extendedColors.mintSoft)
                                        .padding(MaterialTheme.spacing.lg)
                                ) {
                                    Column {
                                        Row(verticalAlignment = Alignment.CenterVertically) {
                                            VoraLogo(size = 16.dp)
                                            Spacer(modifier = Modifier.width(MaterialTheme.spacing.xs))
                                            Text(
                                                text = "VORA COACH NOTE",
                                                style = MaterialTheme.typography.labelSmall,
                                                fontWeight = FontWeight.Bold,
                                                color = MaterialTheme.colorScheme.primary
                                            )
                                        }
                                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))
                                        Text(
                                            text = "Based on your last session, VORA suggests keeping the same weights and focusing on controlled repetitions today.",
                                            style = MaterialTheme.typography.bodyMedium,
                                            color = MaterialTheme.colorScheme.onSurface
                                        )
                                    }
                                }
                            }

                            itemsIndexed(workout.exercises, key = { _, e -> e.id }) { index, exercise ->
                                NumberedExerciseItem(
                                    number = String.format("%02d", index + 1),
                                    exercise = exercise,
                                    onClick = { onExerciseClick(exercise.id) }
                                )
                            }

                            item {
                                Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))
                            }
                        }

                        // Bottom Action Buttons
                        Column(
                            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(vertical = MaterialTheme.spacing.md)
                        ) {
                            AppButton(
                                text = "Start Workout",
                                onClick = onStartWorkoutClick,
                                style = AppButtonStyle.Primary
                            )
                            AppButton(
                                text = "Edit workout",
                                onClick = { },
                                style = AppButtonStyle.Outlined,
                                textColor = MaterialTheme.colorScheme.primary,
                                borderColor = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun NumberedExerciseItem(
    number: String,
    exercise: Exercise,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppCard(
        style = AppCardStyle.Surface,
        onClick = onClick,
        modifier = modifier
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .clip(MaterialTheme.shapes.small)
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = MaterialTheme.spacing.md, vertical = MaterialTheme.spacing.sm),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = number,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
            }

            Spacer(modifier = Modifier.width(MaterialTheme.spacing.md))

            Column {
                Text(
                    text = exercise.name,
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "${exercise.sets} × ${exercise.reps}  •  Rest ${exercise.restSeconds}s",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WorkoutDetailScreenPreview() {
    AppTheme {
        WorkoutDetailScreen(
            uiState = WorkoutDetailUiState(
                isLoading = false,
                workout = Workout(
                    id = "w01",
                    name = "Upper Body Strength",
                    level = "Moderate",
                    durationMinutes = 42,
                    exerciseCount = 5,
                    description = "Focus: Chest • Back • Shoulders • Arms",
                    exercises = listOf(
                        Exercise("e01", "Bench Press", "Chest", 3, 10, 90, "", ""),
                        Exercise("e02", "Seated Cable Row", "Back", 3, 12, 75, "", ""),
                        Exercise("e03", "Shoulder Press", "Shoulders", 3, 10, 75, "", ""),
                        Exercise("e04", "Dumbbell Lateral Raise", "Shoulders", 3, 12, 60, "", ""),
                        Exercise("e05", "Tricep Pushdown", "Arms", 3, 12, 60, "", "")
                    )
                )
            ),
            onBackClick = {},
            onStartWorkoutClick = {},
            onExerciseClick = {},
            onRetryClick = {}
        )
    }
}
