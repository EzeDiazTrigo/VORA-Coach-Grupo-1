package com.example.tp3_parcial_grupo_1.ui.screens.workout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.R
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

@Composable
fun ActiveWorkoutScreen(
    workoutId: String,
    onBackClick: () -> Unit,
    onExerciseClick: (String) -> Unit,
    onFinishWorkoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var activeSetIndex by remember { mutableIntStateOf(1) } // 0: Set 1, 1: Set 2, 2: Set 3
    var weightKg by remember { mutableDoubleStateOf(45.0) }
    var restTimeSeconds by remember { mutableIntStateOf(78) }

    val formatRestTime = remember(restTimeSeconds) {
        val mins = restTimeSeconds / 60
        val secs = restTimeSeconds % 60
        String.format("%02d:%02d", mins, secs)
    }

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = MaterialTheme.spacing.screen, vertical = MaterialTheme.spacing.lg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                // Top Header Row
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Text(
                            text = "EXERCISE 2 OF 5",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.extendedColors.mint
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "Seated Cable Row",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.clickable { onExerciseClick("e02") }
                        )
                    }

                    Box(
                        modifier = Modifier
                            .clip(PillShape)
                            .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.15f))
                            .clickable { onFinishWorkoutClick() }
                            .padding(horizontal = MaterialTheme.spacing.md, vertical = MaterialTheme.spacing.sm)
                    ) {
                        Text(
                            text = "Finish Workout",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                    }
                }

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))

                // Card 1: CURRENT VOLUME WORK Matrix
                DarkWorkoutCard {
                    Text(
                        text = "CURRENT VOLUME WORK",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.extendedColors.mint
                    )

                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Min),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "10",
                                style = MaterialTheme.typography.displayMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Text(
                                text = "REPS TARGET",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                            )
                        }

                        VerticalDivider(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(horizontal = MaterialTheme.spacing.md),
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f)
                        )

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "3 × 12",
                                style = MaterialTheme.typography.displayMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                            Text(
                                text = "SET MATRIX",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))

                // Card 2: LAST SESSION & Weight Selector
                DarkWorkoutCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "LAST SESSION",
                                style = MaterialTheme.typography.labelSmall,
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                            )
                            Spacer(modifier = Modifier.height(2.dp))
                            Text(
                                text = "45 kg × 12 reps",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary
                            )
                        }

                        // Weight Selector Controls (- / +)
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            IconButton(
                                onClick = { if (weightKg > 2.5) weightKg -= 2.5 },
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.15f))
                            ) {
                                Text("-", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onPrimary)
                            }

                            Spacer(modifier = Modifier.width(MaterialTheme.spacing.sm))

                            Text(
                                text = "${weightKg.toInt()} kg",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onPrimary
                            )

                            Spacer(modifier = Modifier.width(MaterialTheme.spacing.sm))

                            IconButton(
                                onClick = { weightKg += 2.5 },
                                modifier = Modifier
                                    .size(36.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.15f))
                            ) {
                                Text("+", style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.onPrimary)
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))

                // Set Chips Row (Interactive)
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)
                ) {
                    val sets = listOf("Set 1", "Set 2", "Set 3")
                    sets.forEachIndexed { index, setName ->
                        val isCompleted = index < activeSetIndex
                        val isCurrent = index == activeSetIndex

                        Box(
                            modifier = Modifier
                                .weight(1f)
                                .clip(MaterialTheme.shapes.medium)
                                .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.12f))
                                .clickable { activeSetIndex = index }
                                .padding(vertical = MaterialTheme.spacing.md, horizontal = MaterialTheme.spacing.sm),
                            contentAlignment = Alignment.Center
                        ) {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Box(
                                    modifier = Modifier
                                        .size(20.dp)
                                        .clip(CircleShape)
                                        .background(
                                            when {
                                                isCompleted -> MaterialTheme.extendedColors.mint
                                                isCurrent -> MaterialTheme.colorScheme.tertiary
                                                else -> MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f)
                                            }
                                        ),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "${index + 1}",
                                        style = MaterialTheme.typography.labelSmall,
                                        fontWeight = FontWeight.Bold,
                                        color = if (isCompleted || isCurrent) MaterialTheme.colorScheme.onTertiary
                                                else MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.6f)
                                    )
                                }
                                Spacer(modifier = Modifier.width(MaterialTheme.spacing.xs))
                                Text(
                                    text = setName,
                                    style = MaterialTheme.typography.labelMedium,
                                    fontWeight = if (isCurrent) FontWeight.Bold else FontWeight.Normal,
                                    color = if (isCurrent) MaterialTheme.colorScheme.onPrimary
                                            else MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))

                // Resting Timer Card
                DarkWorkoutCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "Resting • $formatRestTime",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.extendedColors.mint
                        )

                        Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xs)) {
                            Box(
                                modifier = Modifier
                                    .clip(PillShape)
                                    .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.15f))
                                    .clickable { restTimeSeconds += 30 }
                                    .padding(horizontal = MaterialTheme.spacing.md, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "+30s",
                                    style = MaterialTheme.typography.labelSmall,
                                    color = MaterialTheme.colorScheme.onPrimary
                                )
                            }

                            Box(
                                modifier = Modifier
                                    .clip(PillShape)
                                    .background(MaterialTheme.extendedColors.mint)
                                    .clickable { restTimeSeconds = 0 }
                                    .padding(horizontal = MaterialTheme.spacing.md, vertical = 6.dp)
                            ) {
                                Text(
                                    text = "Skip",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = MaterialTheme.colorScheme.primary
                                )
                            }
                        }
                    }
                }

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))

                // VORA Coach Note Card
                DarkWorkoutCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(16.dp)
                                    .clip(CircleShape)
                                    .background(MaterialTheme.extendedColors.mint)
                            )
                            Spacer(modifier = Modifier.width(MaterialTheme.spacing.xs))
                            Text(
                                text = "VORA COACH",
                                style = MaterialTheme.typography.labelSmall,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.extendedColors.mint
                            )
                        }

                        Text(
                            text = "Voice On",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                        )
                    }

                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))

                    Text(
                        text = "\"Two more reps. Keep your chest elevated, brace your core, and control the bar's return.\"",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                    )
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.xl))

            // Bottom Complete Set Button
            AppButton(
                text = "Complete Set",
                onClick = {
                    if (activeSetIndex < 2) {
                        activeSetIndex++
                    } else {
                        onFinishWorkoutClick()
                    }
                },
                style = AppButtonStyle.Accent
            )
        }
    }
}

@Composable
private fun DarkWorkoutCard(
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.12f))
            .padding(MaterialTheme.spacing.lg)
    ) {
        Column(content = content)
    }
}

@Preview(showBackground = true)
@Composable
private fun ActiveWorkoutScreenPreview() {
    AppTheme {
        ActiveWorkoutScreen(
            workoutId = "w01",
            onBackClick = {},
            onExerciseClick = {},
            onFinishWorkoutClick = {}
        )
    }
}
