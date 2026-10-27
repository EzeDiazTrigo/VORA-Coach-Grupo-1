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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tp3_parcial_grupo_1.model.Workout
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppCardStyle
import com.example.tp3_parcial_grupo_1.ui.components.LightSystemBarIcons
import com.example.tp3_parcial_grupo_1.ui.components.VoraLogo
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing
import kotlinx.coroutines.delay

@Composable
fun ActiveWorkoutScreen(
    workout: Workout? = null,
    currentExerciseIndex: Int = 1,
    workoutId: String = "w01",
    onBackClick: () -> Unit = {},
    onExerciseClick: (String) -> Unit = {},
    onFinishWorkoutClick: () -> Unit = {},
    modifier: Modifier = Modifier
) {
    LightSystemBarIcons()

    var activeSetIndex by rememberSaveable { mutableIntStateOf(1) } // 0: Set 1, 1: Set 2, 2: Set 3
    var weightKg by rememberSaveable { mutableDoubleStateOf(45.0) }
    var restTimeSeconds by rememberSaveable { mutableIntStateOf(78) }

    LaunchedEffect(restTimeSeconds) {
        if (restTimeSeconds > 0) {
            delay(1000L)
            restTimeSeconds--
        }
    }

    val exercises = workout?.exercises.orEmpty()
    val totalExercises = if (exercises.isNotEmpty()) exercises.size else (workout?.exerciseCount ?: 5)
    val exercise2Index = if (exercises.size >= 2) 1 else 0
    val currentExercise = exercises.getOrNull(exercise2Index)

    val exerciseName = currentExercise?.name ?: "Seated Cable Row"
    val exerciseId = currentExercise?.id ?: "e02"

    val formatRestTime = remember(restTimeSeconds) {
        val mins = restTimeSeconds / 60
        val secs = restTimeSeconds % 60
        String.format("%02d:%02d", mins, secs)
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
                // Header Title Box
                Column(
                    modifier = Modifier.weight(1f),
                    verticalArrangement = Arrangement.Center
                ) {
                    Text(
                        text = "EXERCISE 2 OF $totalExercises",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.extendedColors.mint,
                        maxLines = 1
                    )
                    Spacer(modifier = Modifier.height(2.dp))
                    Text(
                        text = exerciseName,
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary,
                        modifier = Modifier.clickable { onExerciseClick(exerciseId) }
                    )
                }

                Spacer(modifier = Modifier.width(MaterialTheme.spacing.sm))

                // Finish Workout Button [106 Hug x 28 Hug]
                Box(
                    modifier = Modifier
                        .width(106.dp)
                        .height(28.dp)
                        .clip(PillShape)
                        .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.15f))
                        .clickable { onFinishWorkoutClick() },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = "Finish Workout",
                        style = MaterialTheme.typography.labelSmall,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            // Card 1: CURRENT VOLUME WORK Matrix
            AppCard(
                style = AppCardStyle.Translucent,
                contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.md, vertical = 10.dp),
                spacing = 0.dp
            ) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "CURRENT VOLUME WORK",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.extendedColors.mint
                    )

                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))

                    // Matrix Box [189 Hug x 57 Hug]
                    Row(
                        modifier = Modifier
                            .width(189.dp)
                            .height(57.dp),
                        horizontalArrangement = Arrangement.SpaceEvenly,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${currentExercise?.reps ?: 10}",
                                style = MaterialTheme.typography.headlineMedium,
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
                                .padding(horizontal = MaterialTheme.spacing.xs),
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.2f)
                        )

                        Column(horizontalAlignment = Alignment.CenterHorizontally) {
                            Text(
                                text = "${currentExercise?.sets ?: 3} × ${currentExercise?.reps ?: 12}",
                                style = MaterialTheme.typography.headlineMedium,
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
            }

            // Card 2: LAST SESSION & Weight Selector
            AppCard(
                style = AppCardStyle.Translucent,
                contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.md, vertical = 10.dp),
                spacing = 0.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    // Last Session Label Box
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.Center
                    ) {
                        Text(
                            text = "LAST SESSION",
                            fontSize = 11.sp,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f),
                            maxLines = 1
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "45 kg × ${currentExercise?.reps ?: 12} reps",
                            fontSize = 13.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary,
                            maxLines = 1
                        )
                    }

                    Spacer(modifier = Modifier.width(MaterialTheme.spacing.sm))

                    // Weight Selector Controls (- / 45 kg / +)
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.15f))
                                .clickable { if (weightKg > 2.5) weightKg -= 2.5 },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("-", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
                        }

                        Text(
                            text = formattedWeight,
                            fontSize = 18.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )

                        Box(
                            modifier = Modifier
                                .size(28.dp)
                                .clip(CircleShape)
                                .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.15f))
                                .clickable { weightKg += 2.5 },
                            contentAlignment = Alignment.Center
                        ) {
                            Text("+", fontSize = 16.sp, fontWeight = FontWeight.Bold, color = MaterialTheme.colorScheme.onPrimary)
                        }
                    }
                }
            }

            // Set Chips Row (Interactive - Always shows number 1, 2, 3)
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
                            .height(38.dp)
                            .clip(PillShape)
                            .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.12f))
                            .then(
                                if (isCurrent) Modifier.border(1.5.dp, MaterialTheme.colorScheme.tertiary, PillShape)
                                else Modifier
                            )
                            .clickable { activeSetIndex = index },
                        contentAlignment = Alignment.Center
                    ) {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            Box(
                                modifier = Modifier
                                    .size(20.dp)
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
                                    text = "${index + 1}",
                                    style = MaterialTheme.typography.labelSmall,
                                    fontWeight = FontWeight.Bold,
                                    color = if (isCurrent) MaterialTheme.colorScheme.onTertiary
                                            else if (isCompleted) MaterialTheme.colorScheme.primary
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

            // Resting Timer Card [350 Fill x 47 Hug]
            AppCard(
                style = AppCardStyle.Translucent,
                contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.md, vertical = 10.dp),
                spacing = 0.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Resting • $formatRestTime",
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.extendedColors.mint
                    )

                    Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xs)) {
                        Box(
                            modifier = Modifier
                                .clip(PillShape)
                                .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.12f))
                                .border(1.dp, Color.White.copy(alpha = 0.3f), PillShape)
                                .clickable { restTimeSeconds += 30 }
                                .padding(horizontal = MaterialTheme.spacing.md, vertical = 4.dp)
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
                                .background(MaterialTheme.colorScheme.secondary)
                                .clickable { restTimeSeconds = 0 }
                                .padding(horizontal = MaterialTheme.spacing.md, vertical = 4.dp)
                        ) {
                            Text(
                                text = "Skip",
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
                contentPadding = PaddingValues(horizontal = MaterialTheme.spacing.md, vertical = 10.dp),
                spacing = 0.dp
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        VoraLogo(size = 18.dp)
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

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))

            // Complete Set Button directly below cards
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

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 597)
@Composable
private fun ActiveWorkoutScreenPreview() {
    AppTheme {
        ActiveWorkoutScreen(
            workout = null,
            currentExerciseIndex = 1,
            workoutId = "w01",
            onBackClick = {},
            onExerciseClick = {},
            onFinishWorkoutClick = {}
        )
    }
}
