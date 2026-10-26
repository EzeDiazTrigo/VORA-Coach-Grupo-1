package com.example.tp3_parcial_grupo_1.ui.screens.progress

import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.R
import com.example.tp3_parcial_grupo_1.data.demo.ProgressDemoData
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.VoraLogo
import com.example.tp3_parcial_grupo_1.ui.components.VoraLogoStyle
import com.example.tp3_parcial_grupo_1.ui.strings.ProgressStrings
import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

internal val ProgressBorderWidth = 1.dp
internal val ProgressSmallIconSize = 16.dp
private val HeaderButtonSize = 44.dp
private val InsightOrbSize = 40.dp

@Composable
internal fun ProgressHeader(
    title: String,
    onBackClick: () -> Unit,
    onMoreClick: () -> Unit,
    subtitle: String? = null
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.lg)
    ) {
        CircleIconButton(R.drawable.ic_chevron_left, Strings.Back, onBackClick)
        Column(modifier = Modifier.weight(1f)) {
            Text(title, style = MaterialTheme.typography.headlineMedium)
            subtitle?.let {
                Text(it, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
        CircleIconButton(R.drawable.ic_more_horizontal, ProgressStrings.MoreOptions, onMoreClick)
    }
}

@Composable
internal fun CircleIconButton(
    @DrawableRes iconRes: Int,
    contentDescription: String,
    onClick: () -> Unit,
    containerColor: Color = MaterialTheme.colorScheme.surface,
    contentColor: Color = MaterialTheme.colorScheme.onSurface,
    borderColor: Color? = MaterialTheme.colorScheme.outlineVariant
) {
    IconButton(
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(containerColor = containerColor, contentColor = contentColor),
        modifier = Modifier
            .size(HeaderButtonSize)
            .then(if (borderColor != null) Modifier.border(ProgressBorderWidth, borderColor, CircleShape) else Modifier)
    ) {
        Icon(painterResource(iconRes), contentDescription = contentDescription)
    }
}

@Composable
internal fun StatStrip(
    stats: List<SummaryStat>,
    modifier: Modifier = Modifier,
    horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally,
    labelColor: Color = MaterialTheme.colorScheme.onSurfaceVariant,
    dividerColor: Color = MaterialTheme.colorScheme.outlineVariant
) {
    Row(modifier = modifier.fillMaxWidth().height(IntrinsicSize.Min)) {
        stats.forEachIndexed { index, stat ->
            if (index > 0) VerticalDivider(color = dividerColor)
            Column(
                modifier = Modifier
                    .weight(1f)
                    .then(if (index > 0) Modifier.padding(start = MaterialTheme.spacing.md) else Modifier),
                horizontalAlignment = horizontalAlignment,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xxs)
            ) {
                Text(stat.label, style = MaterialTheme.typography.bodySmall, color = labelColor)
                Text(stat.value, style = MaterialTheme.typography.titleLarge)
            }
        }
    }
}

@Composable
internal fun StatStripCard(stats: List<SummaryStat>, horizontalAlignment: Alignment.Horizontal = Alignment.CenterHorizontally) {
    AppCard {
        StatStrip(stats = stats, horizontalAlignment = horizontalAlignment)
    }
}

@Composable
internal fun ProgressOrb(size: Dp = InsightOrbSize) {
    VoraLogo(size = size, glowAlpha = 0.25f, style = VoraLogoStyle.Insight)
}

@Composable
internal fun VoraQuoteCard(text: String) {
    val extended = MaterialTheme.extendedColors
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .background(extended.mintSoft, MaterialTheme.shapes.medium)
            .border(ProgressBorderWidth, extended.mint, MaterialTheme.shapes.medium)
            .padding(MaterialTheme.spacing.lg),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
    ) {
        ProgressOrb()
        Text(ProgressStrings.quoted(text), style = MaterialTheme.typography.bodyLarge, color = MaterialTheme.colorScheme.primary)
    }
}

@Composable
internal fun SectionLabel(text: String, color: Color = MaterialTheme.extendedColors.textSubtle) {
    Text(text, style = MaterialTheme.typography.titleSmall, color = color)
}

@Composable
internal fun ProgressLink(text: String, color: Color, onClick: () -> Unit) {
    Row(
        modifier = Modifier.clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xs)
    ) {
        Text(text, style = MaterialTheme.typography.labelLarge, color = color)
        Icon(painterResource(R.drawable.ic_arrow_right), contentDescription = null, tint = color, modifier = Modifier.size(ProgressSmallIconSize))
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun ProgressSectionsPreview() {
    AppTheme {
        Column(
            modifier = Modifier.padding(MaterialTheme.spacing.screen),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.lg)
        ) {
            ProgressHeader(ProgressStrings.HealthTrendsTitle, onBackClick = {}, onMoreClick = {}, subtitle = ProgressStrings.HealthTrendsSubtitle)
            StatStripCard(ProgressDemoData.healthTrends.stats)
            VoraQuoteCard(ProgressDemoData.healthTrends.insight)
            SectionLabel(ProgressStrings.HabitSignals)
        }
    }
}
