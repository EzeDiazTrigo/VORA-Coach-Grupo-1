package com.example.tp3_parcial_grupo_1.ui.screens.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tp3_parcial_grupo_1.model.Exercise
import com.example.tp3_parcial_grupo_1.model.Workout
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppCardStyle
import com.example.tp3_parcial_grupo_1.ui.components.LightSystemBarIcons
import com.example.tp3_parcial_grupo_1.ui.components.VoraLogo
import com.example.tp3_parcial_grupo_1.ui.strings.WorkoutStrings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing
import java.util.Locale

private const val DefaultSets = 3

private val FinishButtonWidth = 106.dp
private val FinishButtonHeight = 28.dp
private val MatrixWidth = 189.dp
private val MatrixHeight = 57.dp
private val StepButtonSize = 28.dp
private val SetChipHeight = 38.dp
private val SetBadgeSize = 20.dp
private val CurrentSetBorder = 1.5.dp
private val CardVerticalPadding = 10.dp
private val CoachLogoSize = 18.dp
private val TextGap = 2.dp
private const val WeightStepKg = 2.5
private const val LowAlpha = 0.12f
private const val MediumAlpha = 0.15f
private const val MutedAlpha = 0.7f

@Composable
fun ActiveWorkoutRoute(
    onBackClick: () -> Unit,
    onExerciseClick: (String) -> Unit,
    onFinishWorkoutClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ActiveWorkoutViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ActiveWorkoutScreen(
        workout = uiState.workout,
        exerciseIndex = uiState.exerciseIndex,
        activeSetIndex = uiState.activeSetIndex,
        weightKg = uiState.weightKg,
        restTimeSeconds = uiState.restTimeSeconds,
        onBackClick = onBackClick,
        onExerciseClick = onExerciseClick,
        onFinishWorkoutClick = onFinishWorkoutClick,
        onAddRestTime = { viewModel.addRestTime() },
        onSkipRest = viewModel::skipRest,
        onAddWeight = viewModel::addWeight,
        onSelectSet = viewModel::selectSet,
        onCompleteSet = { viewModel.completeCurrentSet(onFinishWorkoutClick) },
        modifier = modifier
    )
}

@Composable
fun ActiveWorkoutScreen(
    workout: Workout?,
    modifier: Modifier = Modifier,
    exerciseIndex: Int = 0,
    activeSetIndex: Int = 0,
    weightKg: Double = 45.0,
    restTimeSeconds: Int = 0,
    onBackClick: () -> Unit = {},
    onExerciseClick: (String) -> Unit = {},
    onFinishWorkoutClick: () -> Unit = {},
    onAddRestTime: () -> Unit = {},
    onSkipRest: () -> Unit = {},
    onAddWeight: (Double) -> Unit = {},
    onSelectSet: (Int) -> Unit = {},
    onCompleteSet: () -> Unit = {}
) {
    LightSystemBarIcons()

    val exercises = workout?.exercises.orEmpty()
    val totalExercises = exercises.size.takeIf { it > 0 } ?: workout?.exerciseCount ?: 0
    val currentExercise = exercises.getOrNull(exerciseIndex)

    val formatRestTime = remember(restTimeSeconds) {
        String.format(Locale.ROOT, "%02d:%02d", restTimeSeconds / 60, restTimeSeconds % 60)
    }

    val formattedWeight = remember(weightKg) {
        if (weightKg % 1.0 == 0.0) "${weightKg.toInt()} kg" else "$weightKg kg"
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .safeDrawingPadding()
                .padding(horizontal = MaterialTheme.spacing.screen, vertical = MaterialTheme.spacing.md)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
        ) {
            // Top Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = WorkoutStrings.exerciseOf(exerciseIndex + 1, totalExercises),
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.extendedColors.mint,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(TextGap))
                    Text(
                        text = currentExercise?.name.orEmpty(),
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.clickable(enabled = currentExercise != null) {
                            currentExercise?.let { onExerciseClick(it.id) }
                        }
                    )
                }

                Spacer(modifier = Modifier.width(MaterialTheme.spacing.sm))

                Box(
                    modifier = Modifier
                        .width(FinishButtonWidth)
                        .height(FinishButtonHeight)
                        .clip(PillShape)
                        .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = MediumAlpha))
                        .clickable(onClick = onFinishWorkoutClick),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = WorkoutStrings.FinishWorkout,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            // CURRENT VOLUME WORK
            AppCard(
                style = AppCardStyle.Translucent,
                contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.md, vertical = CardVerticalPadding),
                spacing = 0.dp
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = WorkoutStrings.CurrentVolumeWork,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.extendedColors.mint
                    )

                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))

                    Row(
                        modifier = Modifier
                            .width(MatrixWidth)
                            .height(MatrixHeight),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${currentExercise?.reps ?: 0}",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Text(
                                text = WorkoutStrings.RepsTarget,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = MutedAlpha)
                            )
                        }

                        VerticalDivider(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(horizontal = MaterialTheme.spacing.xs),
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f)
                        )

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${currentExercise?.sets ?: DefaultSets} × ${currentExercise?.reps ?: 0}",
                                style = MaterialTheme.typography.headlineMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Text(
                                text = WorkoutStrings.SetMatrix,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = MutedAlpha)
                            )
                        }
                    }
                }
            }

            // LAST SESSION & weight selector
            AppCard(
                style = AppCardStyle.Translucent,
                contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.md, vertical = CardVerticalPadding),
                spacing = 0.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = WorkoutStrings.LastSession,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Normal,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = MutedAlpha),
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.height(TextGap))
                        Text(
                            text = WorkoutStrings.lastSessionValue(currentExercise?.reps ?: 0),
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary,
                            maxLines = 1
                        )
                    }

                    Spacer(modifier = Modifier.width(MaterialTheme.spacing.sm))

                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)
                    ) {
                        WeightStepButton(symbol = "-", onClick = { onAddWeight(-WeightStepKg) })

                        Text(
                            text = formattedWeight,
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        WeightStepButton(symbol = "+", onClick = { onAddWeight(WeightStepKg) })
                    }
                }
            }

            // Set chips: una por serie del ejercicio actual
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)
            ) {
                val setCount = currentExercise?.sets ?: DefaultSets
                repeat(setCount) { index ->
                    SetChip(
                        number = index + 1,
                        isCompleted = index < activeSetIndex,
                        isCurrent = index == activeSetIndex,
                        onClick = { onSelectSet(index) },
                        modifier = Modifier.weight(1f)
                    )
                }
            }

            // Resting Timer Card
            AppCard(
                style = AppCardStyle.Translucent,
                contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.md, vertical = CardVerticalPadding),
                spacing = 0.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = WorkoutStrings.resting(formatRestTime),
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.extendedColors.mint
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xs)) {
                        Box(
                            modifier = Modifier
                                .clip(PillShape)
                                .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = LowAlpha))
                                .border(1.dp, MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.3f), PillShape)
                                .clickable(onClick = onAddRestTime)
                                .padding(horizontal = MaterialTheme.spacing.md, vertical = MaterialTheme.spacing.xxs)
                        ) {
                            Text(
                                text = WorkoutStrings.AddRest,
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(PillShape)
                                .background(MaterialTheme.colorScheme.secondary)
                                .clickable(onClick = onSkipRest)
                                .padding(horizontal = MaterialTheme.spacing.md, vertical = MaterialTheme.spacing.xxs)
                        ) {
                            Text(
                                text = WorkoutStrings.SkipRest,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSecondary
                            )
                        }
                    }
                }
            }

            // VORA Coach Note Card
            AppCard(
                style = AppCardStyle.Translucent,
                contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.md, vertical = CardVerticalPadding),
                spacing = 0.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        VoraLogo(size = CoachLogoSize)
                        Spacer(modifier = Modifier.width(MaterialTheme.spacing.xs))
                        Text(
                            text = WorkoutStrings.VoraCoach,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.extendedColors.mint
                        )
                    }

                    Text(
                        text = WorkoutStrings.VoiceOn,
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = MutedAlpha)
                    )
                }

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))

                Text(
                    text = WorkoutStrings.CoachNote,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.xxs))

            AppButton(
                text = WorkoutStrings.CompleteSet,
                onClick = onCompleteSet,
                style = AppButtonStyle.Accent
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))
        }
    }
}

@Composable
private fun WeightStepButton(symbol: String, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .size(StepButtonSize)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = MediumAlpha))
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = symbol,
            style = MaterialTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onPrimary
        )
    }
}

@Composable
private fun SetChip(
    number: Int,
    isCompleted: Boolean,
    isCurrent: Boolean,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .height(SetChipHeight)
            .clip(PillShape)
            .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = LowAlpha))
            .then(
                if (isCurrent) Modifier.border(CurrentSetBorder, MaterialTheme.colorScheme.tertiary, PillShape)
                else Modifier
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(SetBadgeSize)
                    .clip(CircleShape)
                    .background(
                        when {
                            isCurrent -> MaterialTheme.colorScheme.tertiary
                            isCompleted -> MaterialTheme.extendedColors.mint
                            else -> MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f)
                        }
                    ),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "$number",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = when {
                        isCurrent -> MaterialTheme.colorScheme.onTertiary
                        isCompleted -> MaterialTheme.colorScheme.primary
                        else -> MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
                    }
                )
            }
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.xs))
            Text(
                text = WorkoutStrings.setName(number),
                style = MaterialTheme.typography.labelMedium,
                fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                color = if (isCurrent) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.onPrimary.copy(alpha = MutedAlpha)
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 597)
@Composable
private fun ActiveWorkoutScreenPreview() {
    AppTheme {
        ActiveWorkoutScreen(
            workout = Workout(
                id = "w01",
                name = "Upper Body Strength",
                level = "Intermediate",
                durationMinutes = 42,
                exerciseCount = 2,
                exercises = listOf(
                    Exercise("e01", "Bench Press", "Chest", 3, 10, 90, "", ""),
                    Exercise("e02", "Seated Cable Row", "Back", 3, 12, 75, "", "")
                )
            ),
            exerciseIndex = 1,
            activeSetIndex = 1,
            restTimeSeconds = 78
        )
    }
}
