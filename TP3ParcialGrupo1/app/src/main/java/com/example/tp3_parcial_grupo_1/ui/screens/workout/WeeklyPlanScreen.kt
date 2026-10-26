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
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppCardStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.components.VoraLogo
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

data class DayItem(
    val dayName: String,
    val dayNumber: String
)

@Composable
fun WeeklyPlanScreen(
    onBackClick: () -> Unit,
    onWorkoutSelect: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedDayNumber by remember { mutableStateOf("8") }

    val days = listOf(
        DayItem("MON", "7"),
        DayItem("TUE", "8"),
        DayItem("WED", "9"),
        DayItem("THU", "10"),
        DayItem("FRI", "11"),
        DayItem("SAT", "12"),
        DayItem("SUN", "13")
    )

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
                items(days) { day ->
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

            // Card 1: TODAY'S WORKOUT (With Black / Dark Green Border)
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
                                text = "TODAY'S WORKOUT",
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

            // Card 2: WEDNESDAY
            AppCard(
                style = AppCardStyle.Surface,
                onClick = { onWorkoutSelect("w02") }
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "WEDNESDAY",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )

                        VoraLogo(size = 18.dp)
                    }

                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.sm))

                    Text(
                        text = "Active Recovery",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "25 min • Mobility + Walk",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Card 3: THURSDAY
            AppCard(
                style = AppCardStyle.Surface,
                onClick = { onWorkoutSelect("w03") }
            ) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Text(
                        text = "THURSDAY",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )

                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.sm))

                    Text(
                        text = "Lower Body Strength",
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onSurface
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text(
                        text = "45 min • 6 exercises",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Card 4: NEED TO ADJUST YOUR WEEK?
            AppCard(style = AppCardStyle.Panel) {
                Column(modifier = Modifier.fillMaxWidth()) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        VoraLogo(size = 18.dp)
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
