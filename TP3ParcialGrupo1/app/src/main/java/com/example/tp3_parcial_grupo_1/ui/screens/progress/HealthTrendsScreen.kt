package com.example.tp3_parcial_grupo_1.ui.screens.progress

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tp3_parcial_grupo_1.data.demo.ProgressDemoData
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppIcon
import com.example.tp3_parcial_grupo_1.ui.components.AppIcons
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.components.AppSectionLabel
import com.example.tp3_parcial_grupo_1.ui.components.AppSectionLabelStyle
import com.example.tp3_parcial_grupo_1.ui.strings.ProgressStrings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val SignalIconSize = 24.dp
private val TrendIconSize = 18.dp

@Composable
fun HealthTrendsRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HealthTrendsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.onMessageShown()
        }
    }

    HealthTrendsScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onBackClick = onBackClick,
        onMoreClick = viewModel::onMoreClick,
        modifier = modifier
    )
}

@Composable
fun HealthTrendsScreen(
    uiState: HealthTrendsUiState,
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    onMoreClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppScaffold(modifier = modifier.fillMaxSize(), snackbarHostState = snackbarHostState) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = MaterialTheme.spacing.screen, vertical = MaterialTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.lg)
        ) {
            ProgressHeader(
                ProgressStrings.HealthTrendsTitle,
                onBackClick = onBackClick,
                onMoreClick = onMoreClick,
                subtitle = ProgressStrings.HealthTrendsSubtitle
            )
            StatStripCard(uiState.stats, horizontalAlignment = Alignment.Start)
            StepsTrendCard(uiState)
            VoraQuoteCard(uiState.insight)
            AppSectionLabel(ProgressStrings.HabitSignals, style = AppSectionLabelStyle.Title)
            uiState.signals.forEach { signal -> HabitSignalCard(signal) }
        }
    }
}

@Composable
private fun StepsTrendCard(uiState: HealthTrendsUiState) {
    AppCard {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            Text(ProgressStrings.WeeklyStepsTrend, style = MaterialTheme.typography.titleSmall, modifier = Modifier.weight(1f))
            Text(ProgressStrings.stepsGoal(uiState.stepsGoal), style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.extendedColors.mint)
        }
        ProgressBarChart(bars = uiState.bars, modifier = Modifier.padding(top = MaterialTheme.spacing.sm))
    }
}

@Composable
private fun HabitSignalCard(signal: HabitSignal) {
    val trendColor = signal.trend.color()
    AppCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
        ) {
            AppIcon(
                signal.area.icon(),
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                size = SignalIconSize
            )
            Text(signal.name, style = MaterialTheme.typography.titleSmall, modifier = Modifier.weight(1f))
            Text(signal.trend.label(), style = MaterialTheme.typography.bodyLarge, color = trendColor)
            AppIcon(signal.trend.icon(), contentDescription = null, tint = trendColor, size = TrendIconSize)
        }
    }
}

private fun HabitArea.icon(): AppIcons = when (this) {
    HabitArea.Sleep -> AppIcons.Bed
    HabitArea.Movement -> AppIcons.Navigation
    HabitArea.Workout -> AppIcons.Dumbbell
    HabitArea.Protein -> AppIcons.Apple
}

private fun HabitTrend.label(): String = when (this) {
    HabitTrend.Improving -> ProgressStrings.Improving
    HabitTrend.Consistent -> ProgressStrings.Consistent
}

private fun HabitTrend.icon(): AppIcons = when (this) {
    HabitTrend.Improving -> AppIcons.TrendingUp
    HabitTrend.Consistent -> AppIcons.Activity
}

@Composable
private fun HabitTrend.color(): Color = when (this) {
    HabitTrend.Improving -> MaterialTheme.extendedColors.success
    HabitTrend.Consistent -> MaterialTheme.colorScheme.onSurfaceVariant
}

@Preview(showBackground = true, widthDp = 390, heightDp = 1150)
@Composable
private fun HealthTrendsScreenPreview() {
    AppTheme {
        HealthTrendsScreen(
            uiState = ProgressDemoData.healthTrends,
            snackbarHostState = remember { SnackbarHostState() },
            onBackClick = {},
            onMoreClick = {}
        )
    }
}
