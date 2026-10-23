package com.example.tp3_parcial_grupo_1.ui.screens.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
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
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppCardStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.components.ProgressRing
import com.example.tp3_parcial_grupo_1.ui.strings.ProgressStrings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val MetricDotSize = 8.dp
private val AdviceIconSize = 18.dp

@Composable
fun TodaysOverviewRoute(
    onStartWorkoutClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: TodaysOverviewViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    TodaysOverviewScreen(uiState = uiState, onStartWorkoutClick = onStartWorkoutClick, modifier = modifier)
}

@Composable
fun TodaysOverviewScreen(
    uiState: TodaysOverviewUiState,
    onStartWorkoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppScaffold(modifier = modifier.fillMaxSize()) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = MaterialTheme.spacing.screen, vertical = MaterialTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.lg)
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xxs)) {
                Text(uiState.dateLabel, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(ProgressStrings.TodaysOverviewTitle, style = MaterialTheme.typography.headlineLarge)
            }
            CompletionCard(uiState)
            AdviceCard(uiState.advice)
            Text(ProgressStrings.CategoryBreakdowns, style = MaterialTheme.typography.titleMedium)
            WorkoutBreakdownCard(uiState.workout, onStartWorkoutClick)
            GoalCard(uiState.currentGoal, uiState.weeklyTarget)
        }
    }
}

@Composable
private fun CompletionCard(uiState: TodaysOverviewUiState) {
    AppCard {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Column(modifier = Modifier.weight(1f), verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xxs)) {
                Text(ProgressStrings.DailyCompletion, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text(ProgressStrings.percent(uiState.completionPercent), style = MaterialTheme.typography.displaySmall)
            }
            ProgressRing(progress = uiState.completion)
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        uiState.metrics.forEach { metric -> MetricRow(metric) }
    }
}

@Composable
private fun MetricRow(metric: CompletionMetric) {
    Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
        Box(Modifier.size(MetricDotSize).background(metric.tone.color(), CircleShape))
        Spacer(Modifier.width(MaterialTheme.spacing.md))
        Text(metric.label, style = MaterialTheme.typography.bodyLarge, modifier = Modifier.weight(1f))
        Text(
            buildAnnotatedString {
                withStyle(SpanStyle(color = MaterialTheme.colorScheme.onSurfaceVariant)) { append("${metric.detail} ") }
                withStyle(MaterialTheme.typography.labelLarge.toSpanStyle()) { append(ProgressStrings.percent(metric.percent)) }
            },
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
private fun AdviceCard(advice: String) {
    AppCard(style = AppCardStyle.Panel) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)) {
            Icon(
                painterResource(R.drawable.ic_sparkles),
                contentDescription = null,
                tint = MaterialTheme.extendedColors.mint,
                modifier = Modifier.size(AdviceIconSize)
            )
            Text(ProgressStrings.VoraAdvice, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.extendedColors.mint)
        }
        Text(ProgressStrings.quoted(advice), style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun WorkoutBreakdownCard(workout: BreakdownWorkout, onStartWorkoutClick: () -> Unit) {
    AppCard {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(ProgressStrings.WorkoutCategory, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant, modifier = Modifier.weight(1f))
            Text(workout.status, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.error)
        }
        Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xxs)) {
            Text(workout.title, style = MaterialTheme.typography.titleMedium)
            Text(workout.meta, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        }
        AppButton(text = ProgressStrings.StartWorkout, onClick = onStartWorkoutClick)
    }
}

@Composable
private fun GoalCard(currentGoal: String, weeklyTarget: String) {
    AppCard {
        Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
            GoalItem(ProgressStrings.CurrentGoal, currentGoal, Modifier.weight(1f))
            VerticalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            GoalItem(ProgressStrings.WeeklyTarget, weeklyTarget, Modifier.weight(1f).padding(start = MaterialTheme.spacing.lg))
        }
    }
}

@Composable
private fun GoalItem(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xxs)) {
        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.titleSmall)
    }
}

@Composable
private fun MetricTone.color(): Color = when (this) {
    MetricTone.Attention -> MaterialTheme.colorScheme.error
    MetricTone.Mint -> MaterialTheme.extendedColors.mint
    MetricTone.Primary -> MaterialTheme.colorScheme.primary
    MetricTone.Lime -> MaterialTheme.colorScheme.tertiary
}

@Preview(showBackground = true, widthDp = 390, heightDp = 1000)
@Composable
private fun TodaysOverviewScreenPreview() {
    AppTheme {
        TodaysOverviewScreen(uiState = ProgressDemoData.todaysOverview, onStartWorkoutClick = {})
    }
}
