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
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
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
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.components.VoraLogo
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing
import java.util.Calendar

data class DayItem(
    val dayName: String,
    val dayNumber: String
)

@Composable
fun WeeklyPlanRoute(
    initialDayNumber: String = "8",
    onBackClick: () -> Unit,
    onWorkoutSelect: (String) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WorkoutListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    WeeklyPlanScreen(
        initialDayNumber = initialDayNumber,
        workouts = uiState.workouts,
        onBackClick = onBackClick,
        onWorkoutSelect = onWorkoutSelect,
        modifier = modifier
    )
}

@Composable
fun WeeklyPlanScreen(
    initialDayNumber: String = "8",
    workouts: List<Workout> = emptyList(),
    onBackClick: () -> Unit,
    onWorkoutSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val currentCal = remember { Calendar.getInstance() }
    val todayNumStr = remember(currentCal) { currentCal.get(Calendar.DAY_OF_MONTH).toString() }

    val days = remember(currentCal) {
        val cal = Calendar.getInstance()
        cal.firstDayOfWeek = Calendar.MONDAY
        cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY)
        val dayNames = listOf("MON", "TUE", "WED", "THU", "FRI", "SAT", "SUN")
        dayNames.map { name ->
            val num = cal.get(Calendar.DAY_OF_MONTH).toString()
            cal.add(Calendar.DAY_OF_MONTH, 1)
            DayItem(name, num)
        }
    }

    var selectedDayNumber by remember(initialDayNumber) {
        mutableStateOf(if (days.any { it.dayNumber == initialDayNumber }) initialDayNumber else todayNumStr)
    }

    AppScaffold(
        onBack = onBackClick,
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
            Text(
                text = "Your Plan",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Text(
                text = "Designed around your goals and recovery.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))

            // Days Carousel
            LazyRow(
                horizontalArrangement = Arrangement.SpaceBetween,
                modifier = Modifier.fillMaxWidth()
            ) {
                items(days, key = { it.dayNumber }) { day ->
                    val isSelected = day.dayNumber == selectedDayNumber
                    Box(
                        modifier = Modifier
                            .clip(PillShape)
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.surface
                            )
                            .clickable { selectedDayNumber = day.dayNumber }
                            .padding(horizontal = MaterialTheme.spacing.sm, vertical = MaterialTheme.spacing.md),
                        contentAlignment = Alignment.Center
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = day.dayName,
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) MaterialTheme.colorScheme.tertiary
                                        else MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = day.dayNumber,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                                        else MaterialTheme.colorScheme.onSurface
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.sm))

            val selectedIndex = days.indexOfFirst { it.dayNumber == selectedDayNumber }.coerceAtLeast(0)
            val selectedWorkout = workouts.getOrNull(selectedIndex % workouts.size.coerceAtLeast(1))

            val selectedDayLabel = when (selectedIndex) {
                0 -> "TODAY'S WORKOUT"
                1 -> "TUESDAY WORKOUT"
                2 -> "WEDNESDAY WORKOUT"
                3 -> "THURSDAY WORKOUT"
                4 -> "FRIDAY WORKOUT"
                5 -> "SATURDAY WORKOUT"
                else -> "SUNDAY WORKOUT"
            }

            if (selectedWorkout != null) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .border(1.5.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
                ) {
                    AppCard(
                        style = AppCardStyle.Surface,
                        onClick = { onWorkoutSelect(selectedWorkout.id) }
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = selectedDayLabel,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )

                                Box(
                                    modifier = Modifier
                                        .clip(PillShape)
                                        .background(MaterialTheme.extendedColors.mint)
                                        .padding(horizontal = MaterialTheme.spacing.sm, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = selectedWorkout.level.uppercase(),
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(MaterialTheme.spacing.sm))

                            Text(
                                text = selectedWorkout.name,
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "${selectedWorkout.durationMinutes} min • ${selectedWorkout.exerciseCount} exercises • ${selectedWorkout.level}",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            } else {
                // Fallback single card for selected day
                Box(
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(MaterialTheme.shapes.medium)
                        .border(1.5.dp, MaterialTheme.colorScheme.primary, MaterialTheme.shapes.medium)
                ) {
                    AppCard(
                        style = AppCardStyle.Surface,
                        onClick = { onWorkoutSelect("w01") }
                    ) {
                        Column(modifier = Modifier.fillMaxWidth()) {
                            Row(
                                modifier = Modifier.fillMaxWidth(),
                                horizontalArrangement = Arrangement.SpaceBetween,
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = selectedDayLabel,
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )

                                Box(
                                    modifier = Modifier
                                        .clip(PillShape)
                                        .background(MaterialTheme.extendedColors.mint)
                                        .padding(horizontal = MaterialTheme.spacing.sm, vertical = 2.dp)
                                ) {
                                    Text(
                                        text = "READY",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = MaterialTheme.colorScheme.primary
                                    )
                                }
                            }

                            Spacer(modifier = Modifier.height(MaterialTheme.spacing.sm))

                            Text(
                                text = "Upper Body Strength",
                                style = MaterialTheme.typography.titleLarge,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )

                            Spacer(modifier = Modifier.height(4.dp))

                            Text(
                                text = "42 min • 5 exercises • Intermediate",
                                style = MaterialTheme.typography.bodyMedium,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                        }
                    }
                }
            }

            // Card 4: NEED TO ADJUST YOUR WEEK?
            AppCard(style = AppCardStyle.Panel) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        VoraLogo(size = 20.dp)
                        Spacer(modifier = Modifier.width(MaterialTheme.spacing.xs))
                        Text(
                            text = "NEED TO ADJUST YOUR WEEK?",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.extendedColors.mint
                        )
                    }

                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.sm))

                    Text(
                        text = "Tell VORA what changed (e.g., sleep, muscle soreness, or schedule cuts) and your coach will realign the week dynamically.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                    )

                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))

                    Row(
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm),
                        modifier = Modifier.fillMaxWidth()
                    ) {
                        Box(
                            modifier = Modifier
                                .clip(PillShape)
                                .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.15f))
                                .clickable { }
                                .padding(horizontal = MaterialTheme.spacing.md, vertical = MaterialTheme.spacing.sm)
                        ) {
                            Text(
                                text = "I'm tired today",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }

                        Box(
                            modifier = Modifier
                                .clip(PillShape)
                                .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.15f))
                                .clickable { }
                                .padding(horizontal = MaterialTheme.spacing.md, vertical = MaterialTheme.spacing.sm)
                        ) {
                            Text(
                                text = "Move Wed workout to Fri",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                    }
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 707)
@Composable
private fun WeeklyPlanScreenPreview() {
    AppTheme {
        WeeklyPlanScreen(onBackClick = {}, onWorkoutSelect = {})
    }
}
