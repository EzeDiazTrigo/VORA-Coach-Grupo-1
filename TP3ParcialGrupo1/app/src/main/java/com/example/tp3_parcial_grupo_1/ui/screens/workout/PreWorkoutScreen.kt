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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tp3_parcial_grupo_1.model.Workout
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppCardStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppIcon
import com.example.tp3_parcial_grupo_1.ui.components.AppIconButton
import com.example.tp3_parcial_grupo_1.ui.components.AppIconButtonDefaults
import com.example.tp3_parcial_grupo_1.ui.components.AppIcons
import com.example.tp3_parcial_grupo_1.ui.components.LightSystemBarIcons
import com.example.tp3_parcial_grupo_1.ui.components.VoraLogo
import com.example.tp3_parcial_grupo_1.ui.strings.WorkoutStrings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.iconSizes
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val CloseButtonSize = 32.dp
private val MicButtonSize = 44.dp
private val CoachLogoSize = 32.dp
private val TextGap = 2.dp

@Composable
fun PreWorkoutRoute(
    onBackClick: () -> Unit,
    onBeginClick: () -> Unit,
    onMicClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WorkoutDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    PreWorkoutScreen(
        workout = uiState.workout,
        onBackClick = onBackClick,
        onBeginClick = onBeginClick,
        onMicClick = onMicClick,
        modifier = modifier
    )
}

@Composable
fun PreWorkoutScreen(
    workout: Workout?,
    modifier: Modifier = Modifier,
    onBackClick: () -> Unit = {},
    onBeginClick: () -> Unit = {},
    onMicClick: () -> Unit = {}
) {
    LightSystemBarIcons()

    val workoutName = workout?.name.orEmpty()
    val durationMinutes = workout?.durationMinutes ?: 0
    val exerciseCount = workout?.exercises?.size?.takeIf { it > 0 } ?: workout?.exerciseCount ?: 0

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
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
                Text(
                    text = WorkoutStrings.GetReady,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.extendedColors.mint
                )

                AppIconButton(
                    AppIcons.Close,
                    contentDescription = WorkoutStrings.Close,
                    onClick = onBackClick,
                    size = CloseButtonSize,
                    colors = AppIconButtonDefaults.onDark()
                )
            }

            // Title Section (workout name from the API)
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = workoutName,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.xxs))

                Text(
                    text = WorkoutStrings.durationAndExercises(durationMinutes, exerciseCount),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.extendedColors.mint
                )
            }

            PreWorkoutNote(
                label = WorkoutStrings.TodaysFocus,
                title = WorkoutStrings.FocusTitle,
                text = WorkoutStrings.FocusText
            )

            PreWorkoutNote(
                label = WorkoutStrings.YourLastSession,
                title = WorkoutStrings.lastSessionVolume(workoutName),
                text = WorkoutStrings.LastSessionSets
            )

            PreWorkoutNote(
                label = WorkoutStrings.CoachTip,
                title = null,
                text = WorkoutStrings.CoachTipText
            )

            // Need anything? Mic Widget (No border)
            AppCard(
                style = AppCardStyle.Translucent,
                spacing = 0.dp,
                borderColor = Color.Transparent,
                onClick = onMicClick
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            VoraLogo(size = CoachLogoSize)
                            Spacer(modifier = Modifier.width(MaterialTheme.spacing.xs))
                            Text(
                                text = WorkoutStrings.NeedAnything,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.extendedColors.mint
                            )
                        }
                        Spacer(modifier = Modifier.height(TextGap))
                        Text(
                            text = WorkoutStrings.RecommendWarmup,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(MicButtonSize)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.tertiary)
                            .clickable(onClick = onMicClick),
                        contentAlignment = Alignment.Center
                    ) {
                        AppIcon(
                            AppIcons.Mic,
                            contentDescription = WorkoutStrings.VoiceAssistant,
                            tint = MaterialTheme.colorScheme.onTertiary,
                            size = MaterialTheme.iconSizes.xl
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.xxs))

            // Mint Start Workout button directly below the cards
            AppButton(
                text = WorkoutStrings.StartWorkout,
                onClick = onBeginClick,
                style = AppButtonStyle.Accent,
                containerColor = MaterialTheme.extendedColors.mint,
                textColor = MaterialTheme.colorScheme.primary
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))
        }
    }
}

// Tarjeta translúcida con etiqueta, título opcional y texto: las tres primeras de Pre Workout.
@Composable
private fun PreWorkoutNote(label: String, title: String?, text: String) {
    AppCard(style = AppCardStyle.Translucent, spacing = 0.dp) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.tertiary
        )
        Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))
        if (title != null) {
            Text(
                text = title,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.xxs))
        }
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreWorkoutScreenPreview() {
    AppTheme {
        PreWorkoutScreen(workout = Workout("w01", "Upper Body Strength", "Intermediate", 42, 5))
    }
}
