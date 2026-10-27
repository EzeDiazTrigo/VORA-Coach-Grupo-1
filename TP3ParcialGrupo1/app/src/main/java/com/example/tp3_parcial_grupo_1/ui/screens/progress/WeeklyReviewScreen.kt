package com.example.tp3_parcial_grupo_1.ui.screens.progress

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tp3_parcial_grupo_1.data.demo.ProgressDemoData
import com.example.tp3_parcial_grupo_1.ui.components.AppBackButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppCardStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppIcon
import com.example.tp3_parcial_grupo_1.ui.components.AppIconButtonColors
import com.example.tp3_parcial_grupo_1.ui.components.AppIcons
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.components.AppSectionLabel
import com.example.tp3_parcial_grupo_1.ui.components.AppSectionLabelStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppTextLink
import com.example.tp3_parcial_grupo_1.ui.components.AppTopBar
import com.example.tp3_parcial_grupo_1.ui.strings.ProgressStrings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val CheckContainerSize = 32.dp
private val BulletSize = 6.dp
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
                AppSectionLabel(ProgressStrings.WhatWentWell, style = AppSectionLabelStyle.Title, color = MaterialTheme.extendedColors.mint)
                uiState.wins.forEach { win -> WinRow(win) }
                AppSectionLabel(ProgressStrings.WhatToImprove, style = AppSectionLabelStyle.Title, color = colors.tertiary)
                ImprovementPanel(uiState.improvement, onDinnerIdeasClick)
                RecommendationsCard(uiState.recommendations, onNextWeekPlanClick)
                AppButton(
                    text = ProgressStrings.AskVora,
                    onClick = onAskVoraClick,
                    style = AppButtonStyle.Accent,
                    leadingIcon = AppIcons.Mic
                )
                DoneButton(onDoneClick)
            }
        }
    }
}

@Composable
private fun ReviewHeader(onBackClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    AppTopBar(
        title = ProgressStrings.WeeklyReviewTitle,
        subtitle = ProgressStrings.WeeklyReviewSubtitle,
        subtitleColor = MaterialTheme.extendedColors.mint,
        spacing = MaterialTheme.spacing.lg,
        navigation = {
            AppBackButton(
                onClick = onBackClick,
                size = HeaderButtonSize,
                colors = AppIconButtonColors(colors.onPrimary.copy(alpha = PanelAlpha), colors.onPrimary)
            )
        },
        trailing = { ProgressOrb() }
    )
}

@Composable
private fun ReviewPanel(content: @Composable ColumnScope.() -> Unit) {
    val onPrimary = MaterialTheme.colorScheme.onPrimary
    AppCard(
        style = AppCardStyle.Translucent,
        shape = MaterialTheme.shapes.extraLarge,
        contentPadding = PaddingValues(MaterialTheme.spacing.screen),
        containerColor = onPrimary.copy(alpha = PanelAlpha),
        borderColor = onPrimary.copy(alpha = PanelBorderAlpha),
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
            AppIcon(
                AppIcons.Check,
                contentDescription = null,
                tint = MaterialTheme.extendedColors.mint,
                size = ProgressSmallIconSize
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
            AppIcon(AppIcons.AlertCircle, contentDescription = null, tint = colors.tertiary)
            Text(improvement.title, style = MaterialTheme.typography.titleMedium)
        }
        Text(improvement.description, style = MaterialTheme.typography.bodyLarge, color = colors.onPrimary.copy(alpha = MutedTextAlpha))
        AppTextLink(ProgressStrings.GetDinnerIdeas, onClick = onDinnerIdeasClick, color = colors.tertiary, trailingIcon = AppIcons.ArrowRight)
    }
}

@Composable
private fun RecommendationsCard(recommendations: List<String>, onNextWeekPlanClick: () -> Unit) {
    val colors = MaterialTheme.colorScheme
    AppCard {
        AppSectionLabel(ProgressStrings.NextWeekRecommendations, style = AppSectionLabelStyle.Title)
        recommendations.forEach { recommendation ->
            Row(verticalAlignment = Alignment.CenterVertically, horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)) {
                Box(Modifier.size(BulletSize).background(colors.onSurface, CircleShape))
                Text(recommendation, style = MaterialTheme.typography.bodyLarge)
            }
        }
        HorizontalDivider(color = colors.outlineVariant)
        AppTextLink(ProgressStrings.ViewNextWeekPlan, onClick = onNextWeekPlanClick, color = colors.primary, trailingIcon = AppIcons.ArrowRight)
    }
}

@Composable
private fun DoneButton(onDoneClick: () -> Unit) {
    val onPrimary = MaterialTheme.colorScheme.onPrimary
    AppButton(
        text = ProgressStrings.Done,
        onClick = onDoneClick,
        style = AppButtonStyle.Outlined,
        textColor = onPrimary,
        borderColor = onPrimary.copy(alpha = PanelBorderAlpha),
        containerColor = onPrimary.copy(alpha = PanelAlpha)
    )
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
