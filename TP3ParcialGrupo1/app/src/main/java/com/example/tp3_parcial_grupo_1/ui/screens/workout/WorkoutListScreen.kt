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
import androidx.compose.material3.CircularProgressIndicator
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
import com.example.tp3_parcial_grupo_1.ui.components.AppNoticeCard
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.components.AppStat
import com.example.tp3_parcial_grupo_1.ui.components.EmptyState
import com.example.tp3_parcial_grupo_1.ui.components.ErrorState
import com.example.tp3_parcial_grupo_1.ui.components.VoraLogo
import com.example.tp3_parcial_grupo_1.ui.strings.WorkoutStrings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing
import java.time.LocalDate

private val MediumIconSize = 20.dp
private val CalendarButtonSize = 44.dp
private val CalendarIconSize = 22.dp
private val ActionHeight = 43.dp
private val DayCircleSize = 36.dp
private val LogoSize = 20.dp
private const val OnPanelAlpha = 0.15f
private const val OnPanelBorderAlpha = 0.6f

private enum class DayStatus { Done, Fit, Rest }

// Estado de ejemplo de la semana de Figma, de lunes a domingo.
private val DayStatuses = listOf(DayStatus.Done, DayStatus.Done, DayStatus.Rest, DayStatus.Done, DayStatus.Fit, DayStatus.Rest, DayStatus.Rest)

@Composable
fun WorkoutListRoute(
    modifier: Modifier = Modifier,
    onBackClick: (() -> Unit)? = null,
    onWorkoutClick: (String) -> Unit,
    onStartWorkoutClick: (String) -> Unit = {},
    onWeeklyPlanClick: (String) -> Unit = {},
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
    onWeeklyPlanClick: (String) -> Unit,
    onViewProgressClick: () -> Unit,
    onRetryClick: () -> Unit
) {
    val today = remember { LocalDate.now() }
    val week = remember(today) { weekOf(today) }

    AppScaffold(
        onBack = onBackClick,
        snackbarHostState = snackbarHostState,
        modifier = modifier
    ) { padding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
        ) {
            when {
                uiState.isLoading && uiState.workouts.isEmpty() -> {
                    Box(
                        modifier = Modifier.fillMaxSize(),
                        contentAlignment = Alignment.Center
                    ) {
                        CircularProgressIndicator(
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
                uiState.errorMessage != null && uiState.workouts.isEmpty() -> {
                    ErrorState(
                        message = uiState.errorMessage,
                        onRetry = onRetryClick,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                uiState.workouts.isEmpty() -> {
                    EmptyState(
                        title = WorkoutStrings.NoWorkoutsFound,
                        message = WorkoutStrings.ErrorGeneric,
                        actionLabel = WorkoutStrings.Retry,
                        onAction = onRetryClick,
                        modifier = Modifier.fillMaxSize()
                    )
                }
                else -> {
                    val recommendedWorkout = uiState.workouts.first()

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
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
                                    text = WorkoutStrings.WorkoutGoal,
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onSurfaceVariant
                                )
                                Text(
                                    text = WorkoutStrings.Title,
                                    style = MaterialTheme.typography.headlineLarge,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.onBackground
                                )
                            }

                            AppIconButton(
                                AppIcons.Calendar,
                                contentDescription = WorkoutStrings.WeeklyPlan,
                                onClick = { onWeeklyPlanClick(today.toString()) },
                                size = CalendarButtonSize,
                                iconSize = CalendarIconSize,
                                colors = AppIconButtonDefaults.surface()
                            )
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
                                        text = WorkoutStrings.ReadyForToday,
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.extendedColors.mint
                                    )

                                    Box(
                                        modifier = Modifier
                                            .clip(PillShape)
                                            .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = OnPanelAlpha))
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
                                    text = "${WorkoutStrings.durationAndExercises(recommendedWorkout.durationMinutes, recommendedWorkout.exerciseCount)} • ${recommendedWorkout.description ?: WorkoutStrings.DefaultFocus}",
                                    style = MaterialTheme.typography.bodyMedium,
                                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                                )

                                Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))

                                Row(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .height(ActionHeight),
                                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm),
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    AppButton(
                                        text = WorkoutStrings.StartWorkout,
                                        onClick = { onStartWorkoutClick(recommendedWorkout.id) },
                                        style = AppButtonStyle.Accent,
                                        fullWidth = false,
                                        modifier = Modifier
                                            .weight(1f)
                                            .height(ActionHeight)
                                    )

                                    AppButton(
                                        text = WorkoutStrings.Details,
                                        onClick = { onWorkoutClick(recommendedWorkout.id) },
                                        style = AppButtonStyle.Outlined,
                                        fullWidth = false,
                                        textColor = MaterialTheme.colorScheme.onPrimary,
                                        borderColor = MaterialTheme.colorScheme.onPrimary.copy(alpha = OnPanelBorderAlpha),
                                        modifier = Modifier.height(ActionHeight)
                                    )
                                }
                            }
                        }

                        // Card 2: VORA RECOMMENDS
                        AppNoticeCard(
                            text = WorkoutStrings.RecommendsText,
                            title = WorkoutStrings.RecommendsTitle,
                            leading = { VoraLogo(size = LogoSize) },
                            shape = MaterialTheme.shapes.large,
                            borderColor = null,
                            contentPadding = MaterialTheme.spacing.lg,
                            spacing = MaterialTheme.spacing.xs,
                            textStyle = MaterialTheme.typography.bodyMedium,
                            textColor = MaterialTheme.colorScheme.onSurface
                        )

                        // Card 3: This Week's Activity (cada día abre el Weekly Plan en esa fecha)
                        AppCard(
                            style = AppCardStyle.Surface,
                            onClick = { onWeeklyPlanClick(today.toString()) }
                        ) {
                            Column(modifier = Modifier.fillMaxWidth()) {
                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    Text(
                                        text = WorkoutStrings.ThisWeeksActivity,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )

                                    AppIcon(
                                        AppIcons.ChevronRight,
                                        contentDescription = WorkoutStrings.ViewWeeklyPlan,
                                        tint = MaterialTheme.colorScheme.onSurfaceVariant,
                                        size = MediumIconSize
                                    )
                                }

                                Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))

                                Row(
                                    modifier = Modifier.fillMaxWidth(),
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.CenterVertically
                                ) {
                                    week.forEachIndexed { index, day ->
                                        val status = DayStatuses[index]
                                        Column(
                                            horizontalAlignment = Alignment.CenterHorizontally,
                                            modifier = Modifier.clickable { onWeeklyPlanClick(day.date.toString()) }
                                        ) {
                                            Text(
                                                text = day.initial,
                                                style = MaterialTheme.typography.labelSmall,
                                                color = MaterialTheme.colorScheme.onSurfaceVariant
                                            )
                                            Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))
                                            DayCircle(status)
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
                                        text = WorkoutStrings.YourProgress,
                                        style = MaterialTheme.typography.titleMedium,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.onSurface
                                    )

                                    Text(
                                        text = WorkoutStrings.ViewProgress,
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
                                    horizontalArrangement = Arrangement.SpaceBetween,
                                    verticalAlignment = Alignment.Top
                                ) {
                                    AppStat(
                                        label = WorkoutStrings.Streak,
                                        value = WorkoutStrings.StreakValue,
                                        modifier = Modifier.weight(0.9f),
                                        valueStyle = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        valueColor = MaterialTheme.colorScheme.onSurface
                                    )

                                    VerticalDivider(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .padding(horizontal = MaterialTheme.spacing.xs),
                                        color = MaterialTheme.colorScheme.outlineVariant
                                    )

                                    AppStat(
                                        label = WorkoutStrings.WorkoutsLabel,
                                        value = WorkoutStrings.WorkoutsValue,
                                        modifier = Modifier.weight(1.3f),
                                        valueStyle = MaterialTheme.typography.bodyLarge.copy(fontWeight = FontWeight.Bold),
                                        valueColor = MaterialTheme.colorScheme.onSurface
                                    )

                                    VerticalDivider(
                                        modifier = Modifier
                                            .fillMaxHeight()
                                            .padding(horizontal = MaterialTheme.spacing.xs),
                                        color = MaterialTheme.colorScheme.outlineVariant
                                    )

                                    AppStat(
                                        label = WorkoutStrings.VolumeLifted,
                                        value = WorkoutStrings.VolumeLiftedValue,
                                        modifier = Modifier.weight(1.1f),
                                        valueStyle = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                                        valueColor = MaterialTheme.colorScheme.onSurface
                                    )
                                }
                            }
                        }

                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))
                    }
                }
            }
        }
    }
}

@Composable
private fun DayCircle(status: DayStatus) {
    val container = when (status) {
        DayStatus.Done -> MaterialTheme.extendedColors.mint
        DayStatus.Fit -> MaterialTheme.colorScheme.primary
        DayStatus.Rest -> MaterialTheme.colorScheme.surfaceVariant
    }
    Box(
        modifier = Modifier
            .size(DayCircleSize)
            .clip(CircleShape)
            .background(container),
        contentAlignment = Alignment.Center
    ) {
        when (status) {
            DayStatus.Fit -> Text(
                text = WorkoutStrings.DayFit,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.tertiary
            )
            DayStatus.Rest -> Text(
                text = WorkoutStrings.DayRest,
                style = MaterialTheme.typography.labelSmall,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            DayStatus.Done -> Unit
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
