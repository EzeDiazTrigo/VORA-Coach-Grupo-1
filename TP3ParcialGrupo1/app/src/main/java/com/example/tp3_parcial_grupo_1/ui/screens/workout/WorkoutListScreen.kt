package com.example.tp3_parcial_grupo_1.ui.screens.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
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
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tp3_parcial_grupo_1.R
import com.example.tp3_parcial_grupo_1.model.Workout
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppCardStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.components.VoraOrb
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val SmallIconSize = 16.dp
private val MediumIconSize = 20.dp

@Composable
fun WorkoutListRoute(
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
    onWorkoutClick: (String) -> Unit,
    onStartWorkoutClick: (String) -> Unit = {},
    onWeeklyPlanClick: () -> Unit = {},
    onViewProgressClick: () -> Unit = {},
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
        onStartWorkoutClick = onStartWorkoutClick,
        onWeeklyPlanClick = onWeeklyPlanClick,
        onViewProgressClick = onViewProgressClick,
        onRetryClick = viewModel::refreshWorkouts,
        modifier = modifier
    )
}

@Composable
fun WorkoutListScreen(
    uiState: WorkoutListUiState,
    modifier: Modifier = Modifier,
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    onBackClick: (() -> Unit)? = null,
    onWorkoutClick: (String) -> Unit,
    onStartWorkoutClick: (String) -> Unit,
    onWeeklyPlanClick: () -> Unit,
    onViewProgressClick: () -> Unit,
    onRetryClick: () -> Unit
) {
    val recommendedWorkout = uiState.workouts.firstOrNull() ?: Workout(
        id = "w01",
        name = "Upper Body Strength",
        level = "MODERATE",
        durationMinutes = 42,
        exerciseCount = 5,
        description = "Focus: Chest & Back"
    )

    AppScaffold(
        onBack = onBackClick,
        snackbarHostState = snackbarHostState,
        modifier = modifier
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = MaterialTheme.spacing.screen)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
        ) {
            // Dashboard Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column {
                    Text(
                        text = "Workout Goal",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Text(
                        text = "Workouts",
                        style = MaterialTheme.typography.headlineLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                }

                IconButton(
                    onClick = onWeeklyPlanClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier.size(44.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_calendar),
                        contentDescription = "Weekly Plan",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(22.dp)
                    )
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))

            // Card 1: READY FOR TODAY? (Dark Green Panel)
            AppCard(style = AppCardStyle.Panel) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "READY FOR TODAY?",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.extendedColors.mint
                        )

                        Box(
                            modifier = Modifier
                                .clip(PillShape)
                                .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.15f))
                                .padding(horizontal = MaterialTheme.spacing.sm, vertical = MaterialTheme.spacing.xxs)
                        ) {
                            Text(
                                text = recommendedWorkout.level.uppercase(),
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.sm))

                    Text(
                        text = recommendedWorkout.name,
                        style = MaterialTheme.typography.headlineMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )

                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.xxs))

                    Text(
                        text = "${recommendedWorkout.durationMinutes} min • ${recommendedWorkout.exerciseCount} exercises • ${recommendedWorkout.description ?: "Focus: Chest & Back"}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                    )

                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Box(modifier = Modifier.weight(1.8f)) {
                            AppButton(
                                text = "Start Workout",
                                onClick = { onStartWorkoutClick(recommendedWorkout.id) },
                                style = AppButtonStyle.Accent
                            )
                        }
                        Box(modifier = Modifier.weight(1.2f)) {
                            AppButton(
                                text = "Details",
                                onClick = { onWorkoutClick(recommendedWorkout.id) },
                                style = AppButtonStyle.Outlined
                            )
                        }
                    }
                }
            }

            // Card 2: VORA RECOMMENDS
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.large)
                    .background(MaterialTheme.extendedColors.mintSoft)
                    .padding(MaterialTheme.spacing.lg)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        VoraOrb(size = 18.dp)
                        Spacer(modifier = Modifier.width(MaterialTheme.spacing.xs))
                        Text(
                            text = "VORA RECOMMENDS",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))
                    Text(
                        text = "Your last upper-body session was 4 days ago. Today is a great opportunity to hit your strength volume targets.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            // Card 3: This Week's Activity (Clicking navigates to Weekly Plan)
            AppCard(
                style = AppCardStyle.Surface,
                onClick = onWeeklyPlanClick
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "This Week's Activity",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Icon(
                            painter = painterResource(id = R.drawable.ic_chevron_right),
                            contentDescription = "View Weekly Plan",
                            tint = MaterialTheme.colorScheme.onSurfaceVariant,
                            modifier = Modifier.size(MediumIconSize)
                        )
                    }

                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        val activityDays = listOf(
                            Pair("M", "filled"),
                            Pair("T", "filled"),
                            Pair("W", "zzz"),
                            Pair("T", "filled"),
                            Pair("F", "fit"),
                            Pair("S", "zzz"),
                            Pair("S", "zzz")
                        )

                        activityDays.forEach { (day, status) ->
                            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                                Text(
                                    text = day,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Spacer(modifier = Modifier.height(6.dp))
                                when (status) {
                                    "filled" -> Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.extendedColors.mint)
                                    )
                                    "fit" -> Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.primary),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "FIT",
                                            style = MaterialTheme.typography.labelSmall,
                                            fontWeight = FontWeight.Bold,
                                            color = MaterialTheme.colorScheme.tertiary
                                        )
                                    }
                                    else -> Box(
                                        modifier = Modifier
                                            .size(36.dp)
                                            .clip(CircleShape)
                                            .background(MaterialTheme.colorScheme.surfaceVariant),
                                        contentAlignment = Alignment.Center
                                    ) {
                                        Text(
                                            text = "Zzz",
                                            style = MaterialTheme.typography.labelSmall,
                                            color = MaterialTheme.colorScheme.onSurfaceVariant
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }

            // Card 4: Your Progress
            AppCard(style = AppCardStyle.Surface) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Your Progress",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )

                        Text(
                            text = "View progress",
                            style = MaterialTheme.typography.labelMedium,
                            color = MaterialTheme.colorScheme.primary,
                            modifier = Modifier.clickable { onViewProgressClick() }
                        )
                    }

                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(modifier = Modifier.weight(1f)) {
                            Text(
                                text = "STREAK",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "4 Days",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        VerticalDivider(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(horizontal = MaterialTheme.spacing.xs),
                            color = MaterialTheme.colorScheme.outlineVariant
                        )

                        Column(modifier = Modifier.weight(1.2f)) {
                            Text(
                                text = "WORKOUTS",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "3/5 complete",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        VerticalDivider(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(horizontal = MaterialTheme.spacing.xs),
                            color = MaterialTheme.colorScheme.outlineVariant
                        )

                        Column(modifier = Modifier.weight(1.2f)) {
                            Text(
                                text = "VOL. LIFTED",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "8,420 kg",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))
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
                        name = "Upper Body Strength",
                        level = "MODERATE",
                        durationMinutes = 42,
                        exerciseCount = 5,
                        description = "Focus: Chest & Back"
                    )
                )
            ),
            onBackClick = {},
            onWorkoutClick = {},
            onStartWorkoutClick = {},
            onWeeklyPlanClick = {},
            onViewProgressClick = {},
            onRetryClick = {}
        )
    }
}
