package com.example.tp3_parcial_grupo_1.ui.screens.workout

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tp3_parcial_grupo_1.R
import com.example.tp3_parcial_grupo_1.model.Exercise
import com.example.tp3_parcial_grupo_1.ui.components.AppBackButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppIconButton
import com.example.tp3_parcial_grupo_1.ui.components.AppIconButtonDefaults
import com.example.tp3_parcial_grupo_1.ui.components.AppIcons
import com.example.tp3_parcial_grupo_1.ui.components.AppNoticeCard
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.components.AppTopBar
import com.example.tp3_parcial_grupo_1.ui.components.VoraLogo
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.iconSizes
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val HeaderButtonSize = 40.dp

data class ExerciseGuideData(
    val title: String,
    val target: String,
    val steps: List<String>,
    val proCues: String,
    val voraPrompt: String
)

private fun getExerciseGuideData(exerciseId: String): ExerciseGuideData {
    return when (exerciseId) {
        "e01" -> ExerciseGuideData(
            title = "Bench Press",
            target = "Target: Chest, Front Delts, Triceps",
            steps = listOf(
                "1. Lie flat on the bench with feet firmly planted.",
                "2. Grip the barbell slightly wider than shoulder-width.",
                "3. Lower the bar smoothly to your mid-chest.",
                "4. Press the weight upward explosively without locking elbows.",
                "5. Control the weight on the way down."
            ),
            proCues = "• Retract your shoulder blades into the bench.\n• Keep a slight arch in your lower back.",
            voraPrompt = "Ask Vora: \"How to fix shoulder pain on bench?\""
        )
        "e03" -> ExerciseGuideData(
            title = "Shoulder Press",
            target = "Target: Shoulders, Upper Chest, Triceps",
            steps = listOf(
                "1. Sit or stand with dumbbells at shoulder height.",
                "2. Brace your core and keep your back straight.",
                "3. Press the dumbbells overhead until arms are extended.",
                "4. Pause briefly at peak contraction.",
                "5. Lower dumbbells with control back to shoulder height."
            ),
            proCues = "• Avoid flaring elbows out excessively.\n• Keep your ribs down and avoid leaning backward.",
            voraPrompt = "Ask Vora: \"Dumbbell vs barbell shoulder press?\""
        )
        "e04" -> ExerciseGuideData(
            title = "Dumbbell Lateral Raise",
            target = "Target: Lateral Delts, Shoulders",
            steps = listOf(
                "1. Stand tall with dumbbells at your sides.",
                "2. Keep a slight bend in your elbows throughout.",
                "3. Raise your arms out to the sides until parallel with floor.",
                "4. Pause briefly at the top.",
                "5. Lower the dumbbells slowly to starting position."
            ),
            proCues = "• Lead with your elbows rather than wrists.\n• Don't use momentum or swing your body.",
            voraPrompt = "Ask Vora: \"Best cues for side delt activation?\""
        )
        "e05" -> ExerciseGuideData(
            title = "Tricep Pushdown",
            target = "Target: Triceps, Arms",
            steps = listOf(
                "1. Attach a bar or rope to a high cable pulley.",
                "2. Keep elbows tucked closely to your sides.",
                "3. Push the cable down until your arms are fully extended.",
                "4. Squeeze your triceps at the bottom position.",
                "5. Return the handle slowly to chest level."
            ),
            proCues = "• Keep upper arms stationary throughout the movement.\n• Focus on full elbow extension at the bottom.",
            voraPrompt = "Ask Vora: \"Rope vs straight bar pushdown?\""
        )
        else -> ExerciseGuideData(
            title = "Seated Cable Row",
            target = "Target: Lats, Mid-Back, Rhomboids",
            steps = listOf(
                "1. Sit upright with your chest lifted.",
                "2. Pull the handle toward your lower torso.",
                "3. Keep your elbows tucked close to your ribs.",
                "4. Pause briefly during peak contraction.",
                "5. Return the weight slowly to start."
            ),
            proCues = "• Keep your shoulders rolled down and back.\n• Do not lean backward to swing the load.",
            voraPrompt = "Ask Vora: \"Explain grip variations\""
        )
    }
}

@Composable
fun ExerciseDetailsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ExerciseDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ExerciseDetailsScreen(
        exercise = uiState.exercise,
        exerciseId = viewModel.exerciseId,
        onBackClick = onBackClick,
        modifier = modifier
    )
}

@Composable
fun ExerciseDetailsScreen(
    exercise: Exercise? = null,
    exerciseId: String = "e01",
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val fallbackGuide = remember(exerciseId) { getExerciseGuideData(exerciseId) }

    val title = exercise?.name ?: fallbackGuide.title
    val target = if (!exercise?.muscleGroup.isNullOrBlank()) "Target: ${exercise.muscleGroup}" else fallbackGuide.target
    val steps = remember(exercise, fallbackGuide) {
        if (!exercise?.instructions.isNullOrBlank()) {
            exercise.instructions.split("\n", ".").filter { it.isNotBlank() }.mapIndexed { i, s -> "${i + 1}. ${s.trim()}" }
        } else {
            fallbackGuide.steps
        }
    }
    val proCues = if (!exercise?.tip.isNullOrBlank()) "• ${exercise.tip}" else fallbackGuide.proCues

    AppScaffold(
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
            // Centered Top Header Bar
            AppTopBar(
                title = "Exercise Guide",
                modifier = Modifier.padding(vertical = MaterialTheme.spacing.xs),
                titleStyle = MaterialTheme.typography.titleMedium.copy(fontWeight = FontWeight.Bold),
                titleColor = MaterialTheme.colorScheme.onSurface,
                centerTitle = true,
                spacing = 0.dp,
                navigation = {
                    AppBackButton(
                        onClick = onBackClick,
                        size = HeaderButtonSize,
                        iconSize = MaterialTheme.iconSizes.xl,
                        colors = AppIconButtonDefaults.surface()
                    )
                }
            ) {
                AppIconButton(
                    AppIcons.HelpCircle,
                    contentDescription = "Help",
                    onClick = { },
                    size = HeaderButtonSize,
                    iconSize = MaterialTheme.iconSizes.xl,
                    colors = AppIconButtonDefaults.surface()
                )
            }

            // Exercise Title Header
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = target,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Real Exercise Image
            Image(
                painter = painterResource(id = R.drawable.img_exercise_demo),
                contentDescription = "$title Exercise",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(MaterialTheme.shapes.large)
            )

            // Performance Steps Header
            Text(
                text = "Performance Steps",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Performance Steps List
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xs),
                modifier = Modifier.fillMaxWidth()
            ) {
                steps.forEach { step ->
                    Text(
                        text = step,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))

            // PRO FORM CUES Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.surface)
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, MaterialTheme.shapes.medium)
                    .padding(MaterialTheme.spacing.lg)
            ) {
                Column {
                    Text(
                        text = "PRO FORM CUES",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))
                    Text(
                        text = proCues,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Ask Vora Card
            AppNoticeCard(
                text = fallbackGuide.voraPrompt,
                leading = { VoraLogo(size = 24.dp) },
                shape = MaterialTheme.shapes.medium,
                borderColor = null,
                spacing = MaterialTheme.spacing.sm
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.sm))

            // Bottom Back Button
            AppButton(
                text = "Back to Workout",
                onClick = onBackClick,
                style = AppButtonStyle.Primary
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 695)
@Composable
private fun ExerciseDetailsScreenPreview() {
    AppTheme {
        ExerciseDetailsScreen(exercise = null, exerciseId = "e01", onBackClick = {})
    }
}
