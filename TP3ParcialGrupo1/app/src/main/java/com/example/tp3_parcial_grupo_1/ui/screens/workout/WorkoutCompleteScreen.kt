package com.example.tp3_parcial_grupo_1.ui.screens.workout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.R
import com.example.tp3_parcial_grupo_1.model.Workout
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppCardStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.components.VoraLogo
import com.example.tp3_parcial_grupo_1.ui.components.VoraOrb
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

@Composable
fun WorkoutCompleteScreen(
    workout: Workout? = null,
    workoutId: String = "w01",
    onBackToWorkoutsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedDifficulty by remember { mutableStateOf("Good") }
    val difficulties = listOf("Great", "Good", "Tough", "Too Easy")

    val totalExercises = if (workout?.exercises?.isNotEmpty() == true) workout.exercises.size else (workout?.exerciseCount ?: 3)
    val durationMinutes = workout?.durationMinutes ?: 42

    AppScaffold(
        modifier = modifier
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = MaterialTheme.spacing.screen)
                .verticalScroll(rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "COMPLETED",
                style = MaterialTheme.typography.labelSmall,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.extendedColors.mint
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))

            // Completed Hero Orb Component [vora-orb: 120 x 120]
            Box(
                modifier = Modifier.size(120.dp),
                contentAlignment = Alignment.Center
            ) {
                VoraOrb(size = 120.dp, icon = null)
                Image(
                    painter = painterResource(id = R.drawable.ic_badge_completed),
                    contentDescription = "Completed Badge",
                    modifier = Modifier.size(42.dp)
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))

            Text(
                text = "You did well!",
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Strong session. Your consistency is improving.",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))

            // Stats 3-Column Card
            AppCard(style = AppCardStyle.Surface) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.SpaceEvenly,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$durationMinutes min",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "DURATION",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    VerticalDivider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(horizontal = MaterialTheme.spacing.xs),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "$totalExercises/$totalExercises",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "EXERCISES",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }

                    VerticalDivider(
                        modifier = Modifier
                            .fillMaxHeight()
                            .padding(horizontal = MaterialTheme.spacing.xs),
                        color = MaterialTheme.colorScheme.outlineVariant
                    )

                    Column(horizontalAlignment = Alignment.CenterHorizontally) {
                        Text(
                            text = "2,340 kg",
                            style = MaterialTheme.typography.titleLarge,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "VOLUME",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))

            // VORA INSIGHT Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.large)
                    .background(MaterialTheme.extendedColors.mintSoft)
                    .padding(MaterialTheme.spacing.lg)
            ) {
                Column {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        VoraLogo(size = 18.dp)
                        Spacer(modifier = Modifier.width(MaterialTheme.spacing.xs))
                        Text(
                            text = "VORA INSIGHT",
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))
                    Text(
                        text = "You increased your Seated Cable Row volume by 8% compared with your previous session. Keep this controlled tempo next time.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))

            // Rate Difficulty Section
            Text(
                text = "Rate this workout difficulty",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.sm))

            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xs),
                modifier = Modifier.fillMaxWidth()
            ) {
                difficulties.forEach { level ->
                    val isSelected = level == selectedDifficulty
                    Box(
                        modifier = Modifier
                            .weight(1f)
                            .clip(PillShape)
                            .background(
                                if (isSelected) MaterialTheme.colorScheme.primary
                                else MaterialTheme.colorScheme.surface
                            )
                            .then(
                                if (!isSelected) Modifier.border(1.dp, MaterialTheme.colorScheme.outlineVariant, PillShape)
                                else Modifier
                            )
                            .clickable { selectedDifficulty = level }
                            .padding(vertical = MaterialTheme.spacing.sm),
                        contentAlignment = Alignment.Center
                    ) {
                        Text(
                            text = level,
                            style = MaterialTheme.typography.labelMedium,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Normal,
                            color = if (isSelected) MaterialTheme.colorScheme.onPrimary
                                    else MaterialTheme.colorScheme.onSurface
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))

            // Done Button directly below content
            AppButton(
                text = "Done",
                onClick = onBackToWorkoutsClick,
                style = AppButtonStyle.Primary
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 713)
@Composable
private fun WorkoutCompleteScreenPreview() {
    AppTheme {
        WorkoutCompleteScreen(workout = null, workoutId = "w01", onBackToWorkoutsClick = {})
    }
}
