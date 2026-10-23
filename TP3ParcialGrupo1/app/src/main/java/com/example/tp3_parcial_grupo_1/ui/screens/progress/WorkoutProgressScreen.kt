package com.example.tp3_parcial_grupo_1.ui.screens.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tp3_parcial_grupo_1.R
import com.example.tp3_parcial_grupo_1.data.demo.ProgressDemoData
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.strings.ProgressStrings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val TrophyContainerSize = 48.dp
private val TrophyIconSize = 24.dp

@Composable
fun WorkoutProgressRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WorkoutProgressViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.onMessageShown()
        }
    }

    WorkoutProgressScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onBackClick = onBackClick,
        onMoreClick = viewModel::onMoreClick,
        onPeriodSelect = viewModel::onPeriodSelect,
        modifier = modifier
    )
}

@Composable
fun WorkoutProgressScreen(
    uiState: WorkoutProgressUiState,
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    onMoreClick: () -> Unit,
    onPeriodSelect: (ProgressPeriod) -> Unit,
    modifier: Modifier = Modifier
) {
    val progress = uiState.progress
    AppScaffold(modifier = modifier.fillMaxSize(), snackbarHostState = snackbarHostState) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = MaterialTheme.spacing.screen, vertical = MaterialTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.lg)
        ) {
            ProgressHeader(ProgressStrings.WorkoutProgressTitle, onBackClick = onBackClick, onMoreClick = onMoreClick)
            PeriodSelector(selected = uiState.period, onSelect = onPeriodSelect)
            StatStripCard(progress.stats)
            TrainingVolumeCard(progress)
            PersonalBestCard(progress.personalBest)
            VoraQuoteCard(progress.insight)
            SectionLabel(ProgressStrings.ExerciseProgress)
            progress.exercises.forEach { exercise -> ExerciseCard(exercise) }
        }
    }
}

@Composable
private fun TrainingVolumeCard(progress: WorkoutPeriodProgress) {
    AppCard {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xxs)) {
                Text(ProgressStrings.TrainingVolume, style = MaterialTheme.typography.titleSmall)
                Text(progress.chartSubtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text(progress.volumeTrend, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.colorScheme.primary)
        }
        ProgressBarChart(bars = progress.bars, modifier = Modifier.padding(top = MaterialTheme.spacing.sm))
    }
}

@Composable
private fun PersonalBestCard(personalBest: PersonalBest) {
    val extended = MaterialTheme.extendedColors
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.colorScheme.surface, MaterialTheme.shapes.medium)
            .border(ProgressBorderWidth, extended.mint, MaterialTheme.shapes.medium)
            .padding(MaterialTheme.spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
    ) {
        Box(
            modifier = Modifier.size(TrophyContainerSize).background(extended.mintSoft, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(painterResource(R.drawable.ic_trophy), contentDescription = null, tint = extended.success, modifier = Modifier.size(TrophyIconSize))
        }
        Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xxs)) {
            Text(ProgressStrings.PersonalBest, style = MaterialTheme.typography.labelSmall, color = extended.success)
            Text(personalBest.exercise, style = MaterialTheme.typography.titleMedium)
            Text(
                buildAnnotatedString {
                    withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSurfaceVariant)) { append("${personalBest.detail} ") }
                    withStyle(MaterialTheme.typography.labelLarge.toSpanStyle().copy(color = extended.success)) { append(personalBest.improvement) }
                },
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Composable
private fun ExerciseCard(exercise: ExerciseProgress) {
    AppCard {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xxs)) {
                Text(exercise.name, style = MaterialTheme.typography.titleSmall)
                Text(exercise.detail, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            TrendBadge(exercise)
        }
    }
}

@Composable
private fun TrendBadge(exercise: ExerciseProgress) {
    val isUp = exercise.trend == ExerciseTrend.Up
    Text(
        exercise.change,
        style = MaterialTheme.typography.labelMedium,
        color = if (isUp) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.onSurfaceVariant,
        modifier = Modifier
            .background(if (isUp) MaterialTheme.extendedColors.mintSoft else MaterialTheme.colorScheme.background, PillShape)
            .padding(horizontal = MaterialTheme.spacing.md, vertical = MaterialTheme.spacing.xs)
    )
}

@Preview(showBackground = true, widthDp = 390, heightDp = 1500)
@Composable
private fun WorkoutProgressScreenPreview() {
    AppTheme {
        WorkoutProgressScreen(
            uiState = WorkoutProgressUiState(
                period = ProgressPeriod.Week,
                progress = ProgressDemoData.workoutPeriods.getValue(ProgressPeriod.Week)
            ),
            snackbarHostState = remember { SnackbarHostState() },
            onBackClick = {},
            onMoreClick = {},
            onPeriodSelect = {}
        )
    }
}
