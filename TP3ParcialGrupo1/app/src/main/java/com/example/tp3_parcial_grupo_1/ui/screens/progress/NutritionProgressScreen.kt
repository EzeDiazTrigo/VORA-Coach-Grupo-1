package com.example.tp3_parcial_grupo_1.ui.screens.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
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
import androidx.compose.ui.draw.clip
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tp3_parcial_grupo_1.data.demo.ProgressDemoData
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppCardStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.strings.ProgressStrings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val DayCircleSize = 40.dp

@Composable
fun NutritionProgressRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NutritionProgressViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.onMessageShown()
        }
    }

    NutritionProgressScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onBackClick = onBackClick,
        onMoreClick = viewModel::onMoreClick,
        onPeriodSelect = viewModel::onPeriodSelect,
        onMacroSelect = viewModel::onMacroSelect,
        modifier = modifier
    )
}

@Composable
fun NutritionProgressScreen(
    uiState: NutritionProgressUiState,
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    onMoreClick: () -> Unit,
    onPeriodSelect: (ProgressPeriod) -> Unit,
    onMacroSelect: (NutritionMacro) -> Unit,
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
            ProgressHeader(ProgressStrings.NutritionProgressTitle, onBackClick = onBackClick, onMoreClick = onMoreClick)
            PeriodSelector(selected = uiState.period, onSelect = onPeriodSelect)
            StatStripCard(uiState.progress.stats)
            MacroComplianceCard(uiState.macro, uiState.target, onMacroSelect)
            AnalysisCard(uiState.progress.analysis)
            SectionLabel(ProgressStrings.FoodHabits)
            uiState.progress.habits.chunked(2).forEach { pair ->
                Row(
                    modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
                ) {
                    pair.forEach { habit -> HabitTile(habit, Modifier.weight(1f).fillMaxHeight()) }
                }
            }
        }
    }
}

@Composable
private fun MacroComplianceCard(selected: NutritionMacro, target: MacroTarget, onMacroSelect: (NutritionMacro) -> Unit) {
    AppCard {
        MacroSelector(selected, onMacroSelect)
        Text(
            ProgressStrings.dailyTarget(selected.label(), target.target),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = MaterialTheme.spacing.sm)
        )
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            target.days.forEach { day -> DayIndicator(day) }
        }
    }
}

@Composable
private fun MacroSelector(selected: NutritionMacro, onMacroSelect: (NutritionMacro) -> Unit) {
    val colors = MaterialTheme.colorScheme
    Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xs)) {
        NutritionMacro.entries.forEach { macro ->
            val isSelected = macro == selected
            Box(
                modifier = Modifier
                    .weight(1f)
                    .clip(MaterialTheme.shapes.extraSmall)
                    .background(if (isSelected) colors.primary else colors.background)
                    .clickable { onMacroSelect(macro) }
                    .padding(vertical = MaterialTheme.spacing.sm),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    macro.label(),
                    style = MaterialTheme.typography.labelLarge,
                    color = if (isSelected) colors.onPrimary else colors.onSurfaceVariant
                )
            }
        }
    }
}

@Composable
private fun DayIndicator(day: DayCompliance) {
    val colors = MaterialTheme.colorScheme
    val extended = MaterialTheme.extendedColors
    Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)) {
        Box(
            modifier = Modifier
                .size(DayCircleSize)
                .background(if (day.isHit) extended.mintSoft else colors.background, CircleShape)
                .border(ProgressBorderWidth, if (day.isHit) extended.mint else colors.outlineVariant, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(day.day, style = MaterialTheme.typography.labelLarge, color = if (day.isHit) colors.primary else extended.textSubtle)
        }
        Text(
            if (day.isHit) ProgressStrings.Hit else ProgressStrings.Miss,
            style = MaterialTheme.typography.bodySmall,
            color = if (day.isHit) extended.mint else extended.textSubtle
        )
    }
}

@Composable
private fun AnalysisCard(analysis: String) {
    AppCard(style = AppCardStyle.Panel) {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)) {
            ProgressOrb()
            Text(ProgressStrings.NutritionAnalysis, style = MaterialTheme.typography.titleSmall, color = MaterialTheme.extendedColors.mint)
        }
        Text(ProgressStrings.quoted(analysis), style = MaterialTheme.typography.bodyLarge)
    }
}

@Composable
private fun HabitTile(habit: FoodHabit, modifier: Modifier = Modifier) {
    AppCard(modifier = modifier) {
        Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xxs)) {
            Text(habit.label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(habit.value, style = MaterialTheme.typography.titleMedium)
        }
    }
}

private fun NutritionMacro.label(): String = when (this) {
    NutritionMacro.Calories -> ProgressStrings.MacroCalories
    NutritionMacro.Protein -> ProgressStrings.MacroProtein
    NutritionMacro.Carbs -> ProgressStrings.MacroCarbs
    NutritionMacro.Fat -> ProgressStrings.MacroFat
}

@Preview(showBackground = true, widthDp = 390, heightDp = 1250)
@Composable
private fun NutritionProgressScreenPreview() {
    AppTheme {
        NutritionProgressScreen(
            uiState = NutritionProgressUiState(
                period = ProgressPeriod.Week,
                progress = ProgressDemoData.nutritionPeriods.getValue(ProgressPeriod.Week),
                macro = NutritionMacro.Protein,
                target = ProgressDemoData.macroTargets.getValue(NutritionMacro.Protein)
            ),
            snackbarHostState = remember { SnackbarHostState() },
            onBackClick = {},
            onMoreClick = {},
            onPeriodSelect = {},
            onMacroSelect = {}
        )
    }
}
