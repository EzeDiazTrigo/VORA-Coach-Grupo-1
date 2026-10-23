package com.example.tp3_parcial_grupo_1.ui.screens.home

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import java.util.Locale
import com.example.tp3_parcial_grupo_1.R
import com.example.tp3_parcial_grupo_1.data.demo.HomeDemoData
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppCardStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.components.GoalValueText
import com.example.tp3_parcial_grupo_1.ui.components.MacroProgressBar
import com.example.tp3_parcial_grupo_1.ui.components.ProgressRing
import com.example.tp3_parcial_grupo_1.ui.strings.HomeStrings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

@Composable
fun HomeRoute(
    onNotificationsClick: () -> Unit,
    onStartVoiceClick: () -> Unit,
    onStartWorkoutClick: () -> Unit,
    onLogMealClick: () -> Unit,
    onTodaysOverviewClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.onMessageShown()
        }
    }

    HomeScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onNotificationsClick = onNotificationsClick,
        onStartVoiceClick = onStartVoiceClick,
        onStartWorkoutClick = onStartWorkoutClick,
        onLogMealClick = onLogMealClick,
        onTodaysOverviewClick = onTodaysOverviewClick,
        modifier = modifier
    )
}

@Composable
fun HomeScreen(
    uiState: HomeUiState,
    snackbarHostState: SnackbarHostState,
    onNotificationsClick: () -> Unit,
    onStartVoiceClick: () -> Unit,
    onStartWorkoutClick: () -> Unit,
    onLogMealClick: () -> Unit,
    onTodaysOverviewClick: () -> Unit,
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
            HomeHeader(uiState, onNotificationsClick)
            CoachPanel(onStartVoiceClick, onStartWorkoutClick, onLogMealClick)
            TipCard(uiState.tip)
            Text(HomeStrings.TodaysOverview, style = MaterialTheme.typography.titleMedium)
            OverviewRow(uiState, onTodaysOverviewClick)
            StatsCard(uiState)
            WorkoutCard(uiState.workout, onStartWorkoutClick)
        }
    }
}

@Composable
private fun HomeHeader(uiState: HomeUiState, onNotificationsClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
    ) {
        Image(
            painter = painterResource(R.drawable.img_avatar_demo),
            contentDescription = null,
            contentScale = ContentScale.Crop,
            modifier = Modifier.size(48.dp).clip(CircleShape)
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(HomeStrings.Greeting, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            Text(uiState.userName, style = MaterialTheme.typography.titleMedium)
        }
        IconButton(
            onClick = onNotificationsClick,
            colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.border(1.dp, MaterialTheme.colorScheme.outlineVariant, CircleShape)
        ) {
            Icon(painterResource(R.drawable.ic_bell), contentDescription = HomeStrings.NotificationsButton)
        }
    }
}

@Composable
private fun CoachPanel(onStartVoiceClick: () -> Unit, onStartWorkoutClick: () -> Unit, onLogMealClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    AppCard(style = AppCardStyle.Panel) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(HomeStrings.CoachName, style = MaterialTheme.typography.labelLarge, color = MaterialTheme.extendedColors.mint)
            Text(
                HomeStrings.AiOnline,
                style = MaterialTheme.typography.labelSmall,
                modifier = Modifier
                    .background(Color.White.copy(alpha = 0.12f), PillShape)
                    .padding(horizontal = MaterialTheme.spacing.md, vertical = MaterialTheme.spacing.xs)
            )
        }
        Text(HomeStrings.CoachQuote, style = MaterialTheme.typography.headlineSmall)
        Text(HomeStrings.CoachHint, style = MaterialTheme.typography.bodyMedium, color = colors.onPrimary.copy(alpha = 0.7f))
        AppButton(
            text = HomeStrings.StartVoiceSession,
            onClick = onStartVoiceClick,
            style = AppButtonStyle.Accent,
            leadingIcon = painterResource(R.drawable.ic_mic)
        )
        Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)) {
            PanelChip(HomeStrings.StartMyWorkout, onStartWorkoutClick)
            PanelChip(HomeStrings.LogBreakfast, onLogMealClick)
        }
    }
}

@Composable
private fun PanelChip(text: String, onClick: () -> Unit) {
    Text(
        text = text,
        style = MaterialTheme.typography.labelMedium,
        modifier = Modifier
            .background(Color.White.copy(alpha = 0.12f), PillShape)
            .clickable(onClick = onClick)
            .padding(horizontal = MaterialTheme.spacing.lg, vertical = MaterialTheme.spacing.md)
    )
}

@Composable
private fun TipCard(tip: String) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(MaterialTheme.extendedColors.mintSoft, MaterialTheme.shapes.small)
            .border(BorderStroke(1.dp, MaterialTheme.extendedColors.mint.copy(alpha = 0.5f)), MaterialTheme.shapes.small)
            .padding(MaterialTheme.spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
    ) {
        Icon(painterResource(R.drawable.ic_sparkles), contentDescription = null, tint = MaterialTheme.colorScheme.primary)
        Text(tip, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
private fun OverviewRow(uiState: HomeUiState, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Max),
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
    ) {
        AppCard(modifier = Modifier.width(110.dp).fillMaxHeight(), onClick = onClick) {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)
            ) {
                ProgressRing(progress = uiState.dailyGoal) {
                    Text("${(uiState.dailyGoal * 100).toInt()}%", style = MaterialTheme.typography.labelLarge)
                }
                Text(HomeStrings.DailyGoal, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        AppCard(modifier = Modifier.weight(1f), onClick = onClick) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(HomeStrings.Nutrition, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
                GoalValueText(
                    current = format(uiState.calories.current),
                    goal = "${format(uiState.calories.goal)} kcal"
                )
            }
            MacroProgressBar(
                name = HomeStrings.Protein,
                current = "${uiState.protein.current}g",
                goal = "${uiState.protein.goal}g",
                progress = uiState.protein.fraction,
                color = MaterialTheme.colorScheme.primary
            )
            MacroProgressBar(
                name = HomeStrings.Carbs,
                current = "${uiState.carbs.current}g",
                goal = "${uiState.carbs.goal}g",
                progress = uiState.carbs.fraction,
                color = MaterialTheme.extendedColors.mint
            )
        }
    }
}

@Composable
private fun StatsCard(uiState: HomeUiState) {
    AppCard {
        Row(modifier = Modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
            StatItem(HomeStrings.Steps, uiState.steps, Modifier.weight(1f))
            VerticalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            StatItem(HomeStrings.Distance, uiState.distance, Modifier.weight(1f).padding(start = MaterialTheme.spacing.md))
            VerticalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            StatItem(HomeStrings.ActiveKcal, uiState.activeKcal, Modifier.weight(1f).padding(start = MaterialTheme.spacing.md))
        }
    }
}

@Composable
private fun StatItem(label: String, value: String, modifier: Modifier = Modifier) {
    Column(modifier = modifier, verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xxs)) {
        Text(label, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.titleSmall)
    }
}

@Composable
private fun WorkoutCard(workout: RecommendedWorkout, onStartWorkoutClick: () -> Unit) {
    AppCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)) {
                Icon(painterResource(R.drawable.ic_calendar), contentDescription = null, modifier = Modifier.size(16.dp), tint = MaterialTheme.colorScheme.primary)
                Text(HomeStrings.RecommendedWorkout, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            Text(HomeStrings.Today, style = MaterialTheme.typography.labelSmall, color = MaterialTheme.colorScheme.primary, fontWeight = FontWeight.Bold)
        }
        Text(workout.title, style = MaterialTheme.typography.headlineSmall)
        Text(
            HomeStrings.workoutMeta(workout.minutes, workout.exercises, workout.level),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                HomeStrings.estimatedVolume(workout.estimatedVolumeKg),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.outline,
                modifier = Modifier.weight(1f)
            )
            AppButton(
                text = HomeStrings.StartWorkout,
                onClick = onStartWorkoutClick,
                isCompact = true,
                modifier = Modifier.width(140.dp)
            )
        }
    }
}

private fun format(value: Int) = String.format(Locale.US, "%,d", value)

@Preview(showBackground = true, widthDp = 390, heightDp = 1000)
@Composable
private fun HomeScreenPreview() {
    AppTheme {
        HomeScreen(
            uiState = HomeDemoData.dashboard,
            snackbarHostState = remember { SnackbarHostState() },
            onNotificationsClick = {}, onStartVoiceClick = {}, onStartWorkoutClick = {}, onLogMealClick = {},
            onTodaysOverviewClick = {}
        )
    }
}
