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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppCardStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

data class WeeklyDayPlan(
    val dayName: String,
    val dateText: String,
    val workoutTitle: String?,
    val durationMinutes: Int?,
    val workoutId: String?
)

@Composable
fun WeeklyPlanScreen(
    onBackClick: () -> Unit,
    onWorkoutSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val days = listOf(
        WeeklyDayPlan("Lunes", "20 Oct", "Fuerza Total", 45, "w01"),
        WeeklyDayPlan("Martes", "21 Oct", "Cardio HIIT", 30, "w02"),
        WeeklyDayPlan("Miércoles", "22 Oct", "Descanso Activo", null, null),
        WeeklyDayPlan("Jueves", "23 Oct", "Tren Superior", 40, "w03"),
        WeeklyDayPlan("Viernes", "24 Oct", "Leg Day Intensivo", 50, "w01"),
        WeeklyDayPlan("Sábado", "25 Oct", "Core & Flexibilidad", 25, "w02"),
        WeeklyDayPlan("Domingo", "26 Oct", "Descanso", null, null)
    )

    AppScaffold(
        title = "Plan Semanal",
        onBack = onBackClick,
        modifier = modifier
    ) { padding ->
        LazyColumn(
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = MaterialTheme.spacing.screen)
        ) {
            item {
                Text(
                    text = "Tu programación de entrenamiento para esta semana.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            items(days) { day ->
                AppCard(
                    style = if (day.workoutId != null) AppCardStyle.Surface else AppCardStyle.Panel,
                    onClick = { day.workoutId?.let { onWorkoutSelect(it) } }
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "${day.dayName} · ${day.dateText}",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onSurfaceVariant
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = day.workoutTitle ?: "Día de Descanso",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                        }

                        if (day.durationMinutes != null) {
                            Box(
                                modifier = Modifier
                                    .clip(PillShape)
                                    .background(MaterialTheme.extendedColors.mintSoft)
                                    .padding(horizontal = MaterialTheme.spacing.sm, vertical = 4.dp)
                            ) {
                                Text(
                                    text = "${day.durationMinutes} min",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WeeklyPlanScreenPreview() {
    AppTheme {
        WeeklyPlanScreen(onBackClick = {}, onWorkoutSelect = {})
    }
}
