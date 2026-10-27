package com.example.tp3_parcial_grupo_1.ui.screens.workout

import androidx.compose.foundation.Image
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
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tp3_parcial_grupo_1.R
import com.example.tp3_parcial_grupo_1.data.demo.exerciseGuideFor
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
import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import com.example.tp3_parcial_grupo_1.ui.strings.WorkoutStrings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.iconSizes
import com.example.tp3_parcial_grupo_1.ui.theme.spacing
import kotlinx.coroutines.launch

private val HeaderButtonSize = 40.dp
private val TextGap = 2.dp

@Composable
fun ExerciseDetailsRoute(
    onBackClick: () -> Unit,
    onAskVoraClick: (String) -> Unit = {},
    modifier: Modifier = Modifier,
    viewModel: ExerciseDetailsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    ExerciseDetailsScreen(
        exercise = uiState.exercise,
        exerciseId = viewModel.exerciseId,
        onBackClick = onBackClick,
        onAskVoraClick = onAskVoraClick,
        modifier = modifier
    )
}

@Composable
fun ExerciseDetailsScreen(
    exercise: Exercise? = null,
    exerciseId: String = "e01",
    onBackClick: () -> Unit,
    onAskVoraClick: (String) -> Unit = {},
    modifier: Modifier = Modifier
) {
    val fallbackGuide = remember(exerciseId) { exerciseGuideFor(exerciseId) }
    val snackbarHostState = remember { SnackbarHostState() }
    val coroutineScope = rememberCoroutineScope()

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
    // La pregunta de la tarjeta «Ask Vora» es la que se carga en el chat del Asistente.
    val voraQuestion = exercise?.name?.let(WorkoutStrings::askVoraQuestion)
        ?: fallbackGuide.voraPrompt.substringAfter('"').substringBeforeLast('"')

    AppScaffold(
        snackbarHostState = snackbarHostState,
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
                title = WorkoutStrings.ExerciseGuide,
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
                    contentDescription = WorkoutStrings.Help,
                    onClick = {
                        coroutineScope.launch {
                            snackbarHostState.showSnackbar(Strings.DemoMode)
                        }
                    },
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
                Spacer(modifier = Modifier.height(TextGap))
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
                text = WorkoutStrings.PerformanceSteps,
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
                        text = WorkoutStrings.ProFormCues,
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

            // Ask Vora Card (Navigates to Coach AI)
            AppNoticeCard(
                text = WorkoutStrings.askVora(voraQuestion),
                leading = { VoraLogo(size = 24.dp) },
                shape = MaterialTheme.shapes.medium,
                borderColor = null,
                spacing = MaterialTheme.spacing.sm,
                modifier = Modifier.clickable { onAskVoraClick(voraQuestion) }
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.sm))

            // Bottom Back Button
            AppButton(
                text = WorkoutStrings.BackToWorkout,
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
