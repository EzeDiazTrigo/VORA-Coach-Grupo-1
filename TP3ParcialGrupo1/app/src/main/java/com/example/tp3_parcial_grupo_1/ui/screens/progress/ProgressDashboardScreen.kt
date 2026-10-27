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
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tp3_parcial_grupo_1.data.demo.ProgressDemoData
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppCardStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppIcon
import com.example.tp3_parcial_grupo_1.ui.components.AppIcons
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.components.AppStat
import com.example.tp3_parcial_grupo_1.ui.strings.ProgressStrings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val CategoryIconContainerSize = 44.dp
private val PanelTagAlpha = 0.12f

@Composable
fun ProgressDashboardRoute(
    onSeeWeeklyReviewClick: () -> Unit,
    onCategoryClick: (ProgressCategory) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProgressDashboardViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    ProgressDashboardScreen(
        uiState = uiState,
        onPeriodSelect = viewModel::onPeriodSelect,
        onSeeWeeklyReviewClick = onSeeWeeklyReviewClick,
        onCategoryClick = onCategoryClick,
        modifier = modifier
    )
}

@Composable
fun ProgressDashboardScreen(
    uiState: ProgressDashboardUiState,
    onPeriodSelect: (ProgressPeriod) -> Unit,
    onSeeWeeklyReviewClick: () -> Unit,
    onCategoryClick: (ProgressCategory) -> Unit,
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
                Text(ProgressStrings.DashboardTitle, style = MaterialTheme.typography.headlineLarge)
                Text(ProgressStrings.DashboardSubtitle, style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            PeriodSelector(selected = uiState.period, onSelect = onPeriodSelect)
            OverviewCard(uiState.progress)
            InsightCard(uiState.progress, onSeeWeeklyReviewClick)
            uiState.progress.categories.forEach { summary ->
                CategoryCard(summary, onClick = { onCategoryClick(summary.category) })
            }
            ActiveGoalCard(uiState.activeGoal)
        }
    }
}

@Composable
private fun OverviewCard(progress: PeriodProgress) {
    AppCard {
        Text(progress.overviewLabel, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        progress.stats.chunked(2).forEach { pair ->
            Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)) {
                pair.forEach { stat -> StatTile(stat, Modifier.weight(1f)) }
            }
        }
    }
}

@Composable
private fun StatTile(stat: SummaryStat, modifier: Modifier = Modifier) {
    AppStat(
        label = stat.label,
        value = stat.value,
        modifier = modifier
            .background(MaterialTheme.colorScheme.background, MaterialTheme.shapes.small)
            .padding(MaterialTheme.spacing.md),
        labelStyle = MaterialTheme.typography.bodySmall,
        valueStyle = MaterialTheme.typography.titleLarge
    ) {
        if (stat.isStreak) {
            AppIcon(AppIcons.Flame, contentDescription = null, tint = MaterialTheme.colorScheme.error, size = ProgressSmallIconSize)
        }
        stat.trend?.let {
            Text(it, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.extendedColors.mint)
        }
    }
}

@Composable
private fun InsightCard(progress: PeriodProgress, onSeeWeeklyReviewClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    AppCard(style = AppCardStyle.Panel) {
        Row(modifier = Modifier.fillMaxWidth(), verticalAlignment = Alignment.CenterVertically) {
            ProgressOrb()
            Text(
                ProgressStrings.VoraInsight,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.extendedColors.mint,
                modifier = Modifier.weight(1f).padding(start = MaterialTheme.spacing.md)
            )
            Text(
                progress.insightTag,
                style = MaterialTheme.typography.labelSmall,
                color = colors.tertiary,
                modifier = Modifier
                    .background(colors.onPrimary.copy(alpha = PanelTagAlpha), PillShape)
                    .padding(horizontal = MaterialTheme.spacing.md, vertical = MaterialTheme.spacing.xxs)
            )
        }
        Text(ProgressStrings.quoted(progress.insight), style = MaterialTheme.typography.bodyLarge)
        ProgressLink(ProgressStrings.SeeWeeklyReview, colors.tertiary, onSeeWeeklyReviewClick)
    }
}

@Composable
private fun CategoryCard(summary: CategorySummary, onClick: () -> Unit) {
    AppCard(onClick = onClick) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Box(
                modifier = Modifier
                    .size(CategoryIconContainerSize)
                    .background(MaterialTheme.colorScheme.background, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                AppIcon(summary.category.icon(), contentDescription = null, tint = MaterialTheme.colorScheme.primary)
            }
            Column(
                modifier = Modifier.weight(1f).padding(start = MaterialTheme.spacing.md),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xxs)
            ) {
                Text(summary.title, style = MaterialTheme.typography.titleSmall)
                Text(summary.subtitle, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text(ProgressStrings.View, style = MaterialTheme.typography.labelMedium)
            AppIcon(AppIcons.ChevronRight, contentDescription = null, size = ProgressSmallIconSize)
        }
    }
}

@Composable
private fun ActiveGoalCard(goal: ActiveGoal) {
    val extended = MaterialTheme.extendedColors
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(extended.mintSoft, MaterialTheme.shapes.medium)
            .border(ProgressBorderWidth, extended.mint, MaterialTheme.shapes.medium)
            .padding(MaterialTheme.spacing.lg),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xs)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)) {
            AppIcon(AppIcons.Target, contentDescription = null, tint = MaterialTheme.colorScheme.primary, size = ProgressSmallIconSize)
            Text(ProgressStrings.ActiveGoal, style = MaterialTheme.typography.labelMedium, modifier = Modifier.weight(1f))
            Text(ProgressStrings.complete(goal.percentComplete), style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.primary)
        }
        Text(goal.title, style = MaterialTheme.typography.titleSmall)
        Text(goal.description, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
    }
}

private fun ProgressCategory.icon(): AppIcons = when (this) {
    ProgressCategory.Workout -> AppIcons.Dumbbell
    ProgressCategory.Nutrition -> AppIcons.Apple
    ProgressCategory.Activity -> AppIcons.ChartLine
}

@Preview(showBackground = true, widthDp = 390, heightDp = 1200)
@Composable
private fun ProgressDashboardScreenPreview() {
    AppTheme {
        ProgressDashboardScreen(
            uiState = ProgressDashboardUiState(
                period = ProgressPeriod.Week,
                progress = ProgressDemoData.periods.getValue(ProgressPeriod.Week),
                activeGoal = ProgressDemoData.activeGoal
            ),
            onPeriodSelect = {},
            onSeeWeeklyReviewClick = {},
            onCategoryClick = {}
        )
    }
}
