package com.example.tp3_parcial_grupo_1.ui.screens.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tp3_parcial_grupo_1.model.Workout
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppCardStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppPillButton
import com.example.tp3_parcial_grupo_1.ui.components.AppPillStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.components.EmptyState
import com.example.tp3_parcial_grupo_1.ui.components.VoraLogo
import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import com.example.tp3_parcial_grupo_1.ui.strings.WorkoutStrings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.Locale
import kotlinx.coroutines.launch

private val SelectedCardBorder = 1.5.dp
private val PlanLogoSize = 20.dp

@Composable
fun WeeklyPlanRoute(
    initialDate: String?,
    onBackClick: () -> Unit,
    onWorkoutSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WorkoutListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val today = remember { LocalDate.now() }

    WeeklyPlanScreen(
        initialDate = parsePlanDate(initialDate, today),
        today = today,
        workouts = uiState.workouts,
        isLoading = uiState.isLoading,
        onBackClick = onBackClick,
        onWorkoutSelect = onWorkoutSelect,
        onRetryClick = viewModel::refreshWorkouts,
        modifier = modifier
    )
}

@Composable
fun WeeklyPlanScreen(
    initialDate: LocalDate,
    today: LocalDate,
    workouts: List<Workout>,
    onBackClick: () -> Unit,
    onWorkoutSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
    isLoading: Boolean = false,
    onRetryClick: () -> Unit = {}
) {
    val week = remember(initialDate) { weekOf(initialDate) }
    var selectedIso by rememberSaveable(initialDate) { mutableStateOf(initialDate.toString()) }
    val selectedDate = parsePlanDate(selectedIso, initialDate)

    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val showDemoNotice: () -> Unit = { scope.launch { snackbarHostState.showSnackbar(Strings.DemoMode) } }

    AppScaffold(
        onBack = onBackClick,
        snackbarHostState = snackbarHostState,
        modifier = modifier
    ) { padding ->
        when {
            isLoading && workouts.isEmpty() -> Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator(color = MaterialTheme.colorScheme.primary)
            }
            workouts.isEmpty() -> EmptyState(
                title = WorkoutStrings.NoWorkoutsFound,
                message = WorkoutStrings.ErrorGeneric,
                actionLabel = WorkoutStrings.Retry,
                onAction = onRetryClick,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
            )
            else -> Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding)
                    .padding(horizontal = MaterialTheme.spacing.screen)
                    .verticalScroll(rememberScrollState()),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
            ) {
                Text(
                    text = WorkoutStrings.YourPlan,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Text(
                    text = WorkoutStrings.PlanSubtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))

                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    week.forEach { day ->
                        DayChip(
                            day = day,
                            isSelected = day.date == selectedDate,
                            onClick = { selectedIso = day.date.toString() }
                        )
                    }
                }

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.sm))

                // Cada día del plan rota por el catálogo; el resto de los workouts queda debajo, también tocable.
                val dayWorkout = workouts[selectedDate.dayOfWeek.ordinal % workouts.size]
                PlanWorkoutCard(
                    label = if (selectedDate == today) WorkoutStrings.TodaysWorkout else WorkoutStrings.dayWorkout(dayName(selectedDate)),
                    workout = dayWorkout,
                    highlighted = true,
                    onClick = { onWorkoutSelect(dayWorkout.id) }
                )

                val others = workouts.filter { it.id != dayWorkout.id }
                if (others.isNotEmpty()) {
                    Text(
                        text = WorkoutStrings.MoreWorkouts,
                        style = MaterialTheme.typography.titleMedium,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onBackground
                    )
                    others.forEach { workout ->
                        PlanWorkoutCard(
                            label = workout.level.uppercase(),
                            workout = workout,
                            highlighted = false,
                            onClick = { onWorkoutSelect(workout.id) }
                        )
                    }
                }

                AdjustWeekCard(onSuggestionClick = showDemoNotice)

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))
            }
        }
    }
}

@Composable
private fun DayChip(day: WeekDay, isSelected: Boolean, onClick: () -> Unit) {
    Box(
        modifier = Modifier
            .clip(PillShape)
            .background(
                if (isSelected) MaterialTheme.colorScheme.primary
                else MaterialTheme.colorScheme.surface
            )
            .clickable(onClick = onClick)
            .padding(horizontal = MaterialTheme.spacing.sm, vertical = MaterialTheme.spacing.md),
        contentAlignment = Alignment.Center
    ) {
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = day.name,
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) MaterialTheme.colorScheme.tertiary
                else MaterialTheme.colorScheme.onSurfaceVariant
            )
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.xxs))
            Text(
                text = day.number,
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                else MaterialTheme.colorScheme.onSurface
            )
        }
    }
}

@Composable
private fun PlanWorkoutCard(label: String, workout: Workout, highlighted: Boolean, onClick: () -> Unit) {
    val borderModifier = if (highlighted) {
        Modifier.border(SelectedCardBorder, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
    } else {
        Modifier
    }
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.medium)
            .then(borderModifier)
    ) {
        AppCard(style = AppCardStyle.Surface, onClick = onClick) {
            Column(modifier = Modifier.fillMaxWidth()) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = label,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    if (highlighted) {
                        Box(
                            modifier = Modifier
                                .clip(PillShape)
                                .background(MaterialTheme.extendedColors.mint)
                                .padding(horizontal = MaterialTheme.spacing.sm, vertical = MaterialTheme.spacing.xxs)
                        ) {
                            Text(
                                text = workout.level.uppercase(),
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.sm))

                Text(
                    text = workout.name,
                    style = MaterialTheme.typography.titleLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.xxs))

                Text(
                    text = WorkoutStrings.planCardSummary(
                        minutes = workout.durationMinutes,
                        count = workout.exerciseCount,
                        level = workout.level
                    ),
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun AdjustWeekCard(onSuggestionClick: () -> Unit) {
    AppCard(style = AppCardStyle.Panel) {
        Column(modifier = Modifier.fillMaxWidth()) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                VoraLogo(size = PlanLogoSize)
                Spacer(modifier = Modifier.width(MaterialTheme.spacing.xs))
                Text(
                    text = WorkoutStrings.AdjustWeekTitle,
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.extendedColors.mint
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.sm))

            Text(
                text = WorkoutStrings.AdjustWeekText,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))

            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm),
                modifier = Modifier.fillMaxWidth()
            ) {
                AppPillButton(
                    text = WorkoutStrings.ImTiredToday,
                    onClick = onSuggestionClick,
                    style = AppPillStyle.OnDark,
                    textStyle = MaterialTheme.typography.labelSmall
                )
                AppPillButton(
                    text = WorkoutStrings.MoveWorkout,
                    onClick = onSuggestionClick,
                    style = AppPillStyle.OnDark,
                    textStyle = MaterialTheme.typography.labelSmall
                )
            }
        }
    }
}

private fun dayName(date: LocalDate) = date.dayOfWeek.getDisplayName(TextStyle.FULL, Locale.ENGLISH).uppercase()

@Preview(showBackground = true, widthDp = 390, heightDp = 707)
@Composable
private fun WeeklyPlanScreenPreview() {
    val today = LocalDate.of(2026, 10, 27)
    AppTheme {
        WeeklyPlanScreen(
            initialDate = today,
            today = today,
            workouts = listOf(
                Workout("w01", "Upper Body Strength", "Intermediate", 42, 5),
                Workout("w02", "Lower Body Strength", "Intermediate", 45, 6),
                Workout("w03", "Active Recovery", "Beginner", 30, 4)
            ),
            onBackClick = {},
            onWorkoutSelect = {}
        )
    }
}
