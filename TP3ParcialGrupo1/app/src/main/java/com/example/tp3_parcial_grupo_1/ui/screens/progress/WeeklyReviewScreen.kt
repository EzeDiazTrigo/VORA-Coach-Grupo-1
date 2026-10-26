package com.example.tp3_parcial_grupo_1.ui.screens.progress

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tp3_parcial_grupo_1.R
import com.example.tp3_parcial_grupo_1.data.demo.ProgressDemoData
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.strings.ProgressStrings
import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val CheckContainerSize = 32.dp
private val BulletSize = 6.dp
private val ButtonMinHeight = 52.dp
private const val PanelAlpha = 0.06f
private const val PanelBorderAlpha = 0.14f
private const val MutedTextAlpha = 0.72f

@Composable
fun WeeklyReviewRoute(
    onBackClick: () -> Unit,
    onDoneClick: () -> Unit,
    onAskVoraClick: () -> Unit,
    onDinnerIdeasClick: () -> Unit,
    onNextWeekPlanClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: WeeklyReviewViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    WeeklyReviewScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onDoneClick = onDoneClick,
        onAskVoraClick = onAskVoraClick,
        onDinnerIdeasClick = onDinnerIdeasClick,
        onNextWeekPlanClick = onNextWeekPlanClick,
        modifier = modifier
    )
}

@Composable
fun WeeklyReviewScreen(
    uiState: WeeklyReviewUiState,
    onBackClick: () -> Unit,
    onDoneClick: () -> Unit,
    onAskVoraClick: () -> Unit,
    onDinnerIdeasClick: () -> Unit,
    onNextWeekPlanClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val colors = MaterialTheme.colorScheme
    AppScaffold(modifier = modifier.fillMaxSize()) { padding ->
        CompositionLocalProvider(LocalContentColor provides colors.onPrimary) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .background(colors.primary)
                    .padding(padding)
                    .verticalScroll(rememberScrollState())
                    .padding(horizontal = MaterialTheme.spacing.screen, vertical = MaterialTheme.spacing.lg),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.lg)
            ) {
                ReviewHeader(onBackClick)
                SummaryPanel(uiState)
                SectionLabel(ProgressStrings.WhatWentWell, color = MaterialTheme.extendedColors.mint)
                uiState.wins.forEach { win -> WinRow(win) }
                SectionLabel(ProgressStrings.WhatToImprove, color = colors.tertiary)
                ImprovementPanel(uiState.improvement, onDinnerIdeasClick)
                RecommendationsCard(uiState.recommendations, onNextWeekPlanClick)
                AppButton(
                    text = ProgressStrings.AskVora,
                    onClick = onAskVoraClick,
                    style = AppButtonStyle.Accent,
                    leadingIcon = painterResource(R.drawable.ic_mic)
                )
                DoneButton(onDoneClick)
            }
        }
    }
}

@Composable
private fun ReviewHeader(onBackClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.lg)
    ) {
        CircleIconButton(
            R.drawable.ic_chevron_left,
            Strings.Back,
            onBackClick,
            containerColor = colors.onPrimary.copy(alpha = PanelAlpha),
            contentColor = colors.onPrimary,
            borderColor = null
        )
        Column(modifier = Modifier.weight(1f)) {
            Text(ProgressStrings.WeeklyReviewTitle, style = MaterialTheme.typography.headlineMedium)
            Text(ProgressStrings.WeeklyReviewSubtitle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.extendedColors.mint)
        }
        ProgressOrb()
    }
}

@Composable
private fun ReviewPanel(content: @Composable ColumnScope.() -> Unit) {
    val onPrimary = MaterialTheme.colorScheme.onPrimary
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(onPrimary.copy(alpha = PanelAlpha), MaterialTheme.shapes.extraLarge)
            .border(ProgressBorderWidth, onPrimary.copy(alpha = PanelBorderAlpha), MaterialTheme.shapes.extraLarge)
            .padding(MaterialTheme.spacing.screen),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md),
        content = content
    )
}

@Composable
private fun SummaryPanel(uiState: WeeklyReviewUiState) {
    val onPrimary = MaterialTheme.colorScheme.onPrimary
    ReviewPanel {
        Text(ProgressStrings.quoted(uiState.headline), style = MaterialTheme.typography.headlineSmall, color = MaterialTheme.colorScheme.tertiary)
        StatStrip(
            stats = uiState.stats,
            horizontalAlignment = Alignment.Start,
            labelColor = onPrimary.copy(alpha = MutedTextAlpha),
            dividerColor = onPrimary.copy(alpha = PanelBorderAlpha)
        )
    }
}

@Composable
private fun WinRow(win: ReviewHighlight) {
    val onPrimary = MaterialTheme.colorScheme.onPrimary
    Row(horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)) {
        Box(
            modifier = Modifier.size(CheckContainerSize).background(onPrimary.copy(alpha = PanelAlpha), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Icon(
                painterResource(R.drawable.ic_check),
                contentDescription = null,
                tint = MaterialTheme.extendedColors.mint,
                modifier = Modifier.size(ProgressSmallIconSize)
            )
        }
        Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xxs)) {
            Text(win.title, style = MaterialTheme.typography.titleMedium)
            Text(win.description, style = MaterialTheme.typography.bodyLarge, color = onPrimary.copy(alpha = MutedTextAlpha))
        }
    }
}

@Composable
private fun ImprovementPanel(improvement: ReviewHighlight, onDinnerIdeasClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    ReviewPanel {
        Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)) {
            Icon(painterResource(R.drawable.ic_alert_circle), contentDescription = null, tint = colors.tertiary)
            Text(improvement.title, style = MaterialTheme.typography.titleMedium)
        }
        Text(improvement.description, style = MaterialTheme.typography.bodyLarge, color = colors.onPrimary.copy(alpha = MutedTextAlpha))
        ProgressLink(ProgressStrings.GetDinnerIdeas, colors.tertiary, onDinnerIdeasClick)
    }
}

@Composable
private fun RecommendationsCard(recommendations: List<String>, onNextWeekPlanClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    AppCard {
        SectionLabel(ProgressStrings.NextWeekRecommendations)
        recommendations.forEach { recommendation ->
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)) {
                Box(Modifier.size(BulletSize).background(colors.onSurface, CircleShape))
                Text(recommendation, style = MaterialTheme.typography.bodyLarge)
            }
        }
        HorizontalDivider(color = colors.outlineVariant)
        ProgressLink(ProgressStrings.ViewNextWeekPlan, colors.primary, onNextWeekPlanClick)
    }
}

@Composable
private fun DoneButton(onDoneClick: () -> Unit) {
    val onPrimary = MaterialTheme.colorScheme.onPrimary
    OutlinedButton(
        onClick = onDoneClick,
        modifier = Modifier.fillMaxWidth().heightIn(min = ButtonMinHeight),
        shape = PillShape,
        border = BorderStroke(ProgressBorderWidth, onPrimary.copy(alpha = PanelBorderAlpha)),
        colors = ButtonDefaults.outlinedButtonColors(containerColor = onPrimary.copy(alpha = PanelAlpha), contentColor = onPrimary)
    ) {
        Text(ProgressStrings.Done, style = MaterialTheme.typography.labelLarge)
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 1400)
@Composable
private fun WeeklyReviewScreenPreview() {
    AppTheme {
        WeeklyReviewScreen(
            uiState = ProgressDemoData.weeklyReview,
            onBackClick = {},
            onDoneClick = {},
            onAskVoraClick = {},
            onDinnerIdeasClick = {},
            onNextWeekPlanClick = {}
        )
    }
}
