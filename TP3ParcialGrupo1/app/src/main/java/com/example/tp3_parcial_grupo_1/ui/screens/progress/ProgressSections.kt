package com.example.tp3_parcial_grupo_1.ui.screens.progress

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.VerticalDivider
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.data.demo.ProgressDemoData
import com.example.tp3_parcial_grupo_1.ui.components.AppBackButton
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppIcon
import com.example.tp3_parcial_grupo_1.ui.components.AppIconButton
import com.example.tp3_parcial_grupo_1.ui.components.AppIconButtonDefaults
import com.example.tp3_parcial_grupo_1.ui.components.AppIcons
import com.example.tp3_parcial_grupo_1.ui.components.AppNoticeCard
import com.example.tp3_parcial_grupo_1.ui.components.AppTopBar
import com.example.tp3_parcial_grupo_1.ui.components.VoraLogo
import com.example.tp3_parcial_grupo_1.ui.components.VoraLogoStyle
import com.example.tp3_parcial_grupo_1.ui.strings.ProgressStrings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

internal val ProgressBorderWidth = 1.dp
internal val ProgressSmallIconSize = 16.dp
internal val HeaderButtonSize = 44.dp
private val InsightOrbSize = 40.dp

// Encabezado de Progress: volver y «más opciones» en círculos de 44 dp con borde.
@Composable
internal fun ProgressHeader(
    title: String,
    onBackClick: () -> Unit,
    onMoreClick: () -> Unit,
    subtitle: String? = null
) {
    AppTopBar(
        title = title,
        subtitle = subtitle,
        spacing = MaterialTheme.spacing.lg,
        navigation = { AppBackButton(onClick = onBackClick, size = HeaderButtonSize, colors = AppIconButtonDefaults.outlined()) },
        trailing = {
            AppIconButton(
                AppIcons.MoreHorizontal,
                contentDescription = ProgressStrings.MoreOptions,
                onClick = onMoreClick,
                size = HeaderButtonSize,
                colors = AppIconButtonDefaults.outlined()
            )
        }
    )
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
    AppNoticeCard(
        text = ProgressStrings.quoted(text),
        leading = { ProgressOrb() },
        shape = MaterialTheme.shapes.medium,
        borderColor = MaterialTheme.extendedColors.mint,
        contentPadding = MaterialTheme.spacing.lg,
        textStyle = MaterialTheme.typography.bodyLarge
    )
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
        AppIcon(AppIcons.ArrowRight, contentDescription = null, tint = color, size = ProgressSmallIconSize)
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
