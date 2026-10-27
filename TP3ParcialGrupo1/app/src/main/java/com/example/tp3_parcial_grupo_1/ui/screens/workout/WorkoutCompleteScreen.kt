package com.example.tp3_parcial_grupo_1.ui.screens.workout

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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tp3_parcial_grupo_1.model.Exercise
import com.example.tp3_parcial_grupo_1.model.Workout
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppCardStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppIcon
import com.example.tp3_parcial_grupo_1.ui.components.AppIcons
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.components.VoraLogo
import com.example.tp3_parcial_grupo_1.ui.strings.WorkoutStrings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

// «victory-orb-wrap» de Figma: círculo de 180 dp con degradado detrás del orbe de 120 dp.
private val VictoryHaloSize = 180.dp
private val VictoryOrbSize = 120.dp
private val CompletedBadgeSize = 42.dp
private val InsightLogoSize = 18.dp
private val StatLabelGap = 2.dp
private const val HaloAlpha = 0.25f

@Composable
fun WorkoutCompleteRoute(
    onBackToWorkoutsClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WorkoutDetailViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    WorkoutCompleteScreen(
        workout = uiState.workout,
        onBackToWorkoutsClick = onBackToWorkoutsClick,
        modifier = modifier
    )
}

@Composable
fun WorkoutCompleteScreen(
    workout: Workout?,
    onBackToWorkoutsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var selectedDifficulty by rememberSaveable { mutableStateOf(WorkoutStrings.DefaultDifficulty) }

    val totalExercises = workout?.exercises?.size?.takeIf { it > 0 } ?: workout?.exerciseCount ?: 0
    val durationMinutes = workout?.durationMinutes ?: 0
    val insight = workout?.exercises?.firstOrNull()?.name
        ?.let(WorkoutStrings::insight)
        ?: WorkoutStrings.InsightFallback

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
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))

            Text(
                text = WorkoutStrings.Completed,
                style = MaterialTheme.typography.labelLarge, // 14 sp, en Bold como Figma
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.extendedColors.mint
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))

            Box(
                modifier = Modifier
                    .size(VictoryHaloSize)
                    .clip(CircleShape)
                    .background(
                        Brush.radialGradient(
                            colors = listOf(
                                MaterialTheme.extendedColors.mint.copy(alpha = HaloAlpha),
                                MaterialTheme.colorScheme.background
                            )
                        )
                    ),
                contentAlignment = Alignment.Center
            ) {
                Box(
                    modifier = Modifier.size(VictoryOrbSize),
                    contentAlignment = Alignment.Center
                ) {
                    VoraLogo(size = VictoryOrbSize)
                    AppIcon(AppIcons.BadgeCompleted, contentDescription = WorkoutStrings.CompletedBadge, size = CompletedBadgeSize)
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))

            Text(
                text = WorkoutStrings.YouDidWell,
                style = MaterialTheme.typography.headlineMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onBackground
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.xxs))

            Text(
                text = WorkoutStrings.StrongSession,
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
                    CompleteStat(value = WorkoutStrings.minutesCount(durationMinutes), label = WorkoutStrings.Duration)
                    StatDivider()
                    CompleteStat(value = "$totalExercises/$totalExercises", label = WorkoutStrings.Exercises)
                    StatDivider()
                    CompleteStat(value = WorkoutStrings.VolumeValue, label = WorkoutStrings.Volume)
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
                        VoraLogo(size = InsightLogoSize)
                        Spacer(modifier = Modifier.width(MaterialTheme.spacing.xs))
                        Text(
                            text = WorkoutStrings.VoraInsight,
                            style = MaterialTheme.typography.labelSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))
                    Text(
                        text = insight,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))

            // Rate Difficulty Section
            Text(
                text = WorkoutStrings.RateDifficulty,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.sm))

            Row(
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xs),
                modifier = Modifier.fillMaxWidth()
            ) {
                WorkoutStrings.Difficulties.forEach { level ->
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

            AppButton(
                text = WorkoutStrings.Done,
                onClick = onBackToWorkoutsClick,
                style = AppButtonStyle.Primary
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))
        }
    }
}

@Composable
private fun CompleteStat(value: String, label: String) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = value,
            style = MaterialTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = MaterialTheme.colorScheme.onSurface
        )
        Spacer(modifier = Modifier.height(StatLabelGap))
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
    }
}

@Composable
private fun StatDivider() {
    VerticalDivider(
        modifier = Modifier
            .fillMaxHeight()
            .padding(horizontal = MaterialTheme.spacing.xs),
        color = MaterialTheme.colorScheme.outlineVariant
    )
}

@Preview(showBackground = true)
@Composable
private fun WorkoutCompleteScreenPreview() {
    AppTheme {
        WorkoutCompleteScreen(
            workout = Workout(
                id = "w01",
                name = "Upper Body Strength",
                level = "Intermediate",
                durationMinutes = 42,
                exerciseCount = 5,
                exercises = listOf(Exercise("e01", "Bench Press", "Chest", 3, 10, 90, "", ""))
            ),
            onBackToWorkoutsClick = {}
        )
    }
}
