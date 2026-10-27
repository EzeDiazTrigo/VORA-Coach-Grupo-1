package com.example.tp3_parcial_grupo_1.ui.screens.profile

import androidx.compose.foundation.Image
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tp3_parcial_grupo_1.R
import com.example.tp3_parcial_grupo_1.data.demo.ProfileDemoData
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppCardStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppIcon
import com.example.tp3_parcial_grupo_1.ui.components.AppIcons
import com.example.tp3_parcial_grupo_1.ui.components.AppPillButton
import com.example.tp3_parcial_grupo_1.ui.components.AppPillStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.components.AppSectionLabel
import com.example.tp3_parcial_grupo_1.ui.components.VoraLogo
import com.example.tp3_parcial_grupo_1.ui.components.VoraLogoStyle
import com.example.tp3_parcial_grupo_1.ui.strings.ProfileStrings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val BorderWidth = 1.dp
private val AvatarSize = 72.dp
private val OrbSize = 40.dp
private val LinkIconBoxSize = 40.dp
private val LinkIconSize = 20.dp

@Composable
fun ProfileRoute(
    onSettingsClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onGoalsClick: () -> Unit,
    onPreferencesClick: () -> Unit,
    onVoiceAiClick: () -> Unit,
    onAccountClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ProfileViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.onMessageShown()
        }
    }

    ProfileScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onSettingsClick = onSettingsClick,
        onEditProfileClick = onEditProfileClick,
        onGoalsClick = onGoalsClick,
        onPreferencesClick = onPreferencesClick,
        onVoiceAiClick = onVoiceAiClick,
        onConnectedDataClick = viewModel::onDemoNoticeClick,
        onAccountClick = onAccountClick,
        modifier = modifier
    )
}

@Composable
fun ProfileScreen(
    uiState: ProfileUiState,
    snackbarHostState: SnackbarHostState,
    onSettingsClick: () -> Unit,
    onEditProfileClick: () -> Unit,
    onGoalsClick: () -> Unit,
    onPreferencesClick: () -> Unit,
    onVoiceAiClick: () -> Unit,
    onConnectedDataClick: () -> Unit,
    onAccountClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppScaffold(
        modifier = modifier.fillMaxSize(),
        title = ProfileStrings.ProfileTitle,
        actions = { SettingsButton(onClick = onSettingsClick) },
        snackbarHostState = snackbarHostState
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = MaterialTheme.spacing.screen, vertical = MaterialTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.lg)
        ) {
            HeroCard(uiState, onEditProfileClick)
            SnapshotCard(uiState)
            PersonalisationPanel(onReviewPreferencesClick = onVoiceAiClick)
            ProfileLink(
                icon = AppIcons.Target,
                title = ProfileStrings.YourGoals,
                subtitle = uiState.goal,
                onClick = onGoalsClick
            )
            ProfileLink(
                icon = AppIcons.Sliders,
                title = ProfileStrings.YourPreferences,
                subtitle = ProfileStrings.YourPreferencesSubtitle,
                onClick = onPreferencesClick
            )
            ProfileLink(
                icon = AppIcons.Activity,
                title = ProfileStrings.ConnectedData,
                subtitle = ProfileStrings.ConnectedDataSubtitle,
                onClick = onConnectedDataClick,
                badge = if (uiState.isDataSynced) ProfileStrings.Synced else null
            )
            ProfileLink(
                icon = AppIcons.Lock,
                title = ProfileStrings.Account,
                subtitle = ProfileStrings.AccountSubtitle,
                onClick = onAccountClick
            )
        }
    }
}

@Composable
private fun SettingsButton(onClick: () -> Unit) {
    IconButton(
        onClick = onClick,
        colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.surface),
        modifier = Modifier
            .padding(end = MaterialTheme.spacing.sm)
            .border(BorderWidth, MaterialTheme.colorScheme.outlineVariant, CircleShape)
    ) {
        AppIcon(AppIcons.Settings, contentDescription = ProfileStrings.SettingsButton)
    }
}

@Composable
private fun HeroCard(uiState: ProfileUiState, onEditProfileClick: () -> Unit) {
    AppCard {
        Column(
            modifier = Modifier.fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
        ) {
            Image(
                painter = painterResource(R.drawable.img_avatar_demo),
                contentDescription = null,
                contentScale = ContentScale.Crop,
                modifier = Modifier.size(AvatarSize).clip(CircleShape)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xxs)
            ) {
                Text(uiState.fullName, style = MaterialTheme.typography.titleLarge)
                Text(
                    uiState.tagline,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
            AppPillButton(text = ProfileStrings.EditProfile, onClick = onEditProfileClick)
        }
    }
}

@Composable
private fun SnapshotCard(uiState: ProfileUiState) {
    AppCard {
        AppSectionLabel(ProfileStrings.MySnapshot)
        SnapshotRow(ProfileStrings.Goal, uiState.goal)
        SnapshotRow(ProfileStrings.Experience, uiState.experience)
        SnapshotRow(ProfileStrings.Training, uiState.training)
        SnapshotRow(ProfileStrings.PreferredWorkout, uiState.preferredWorkout)
    }
}

@Composable
private fun SnapshotRow(label: String, value: String) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(
            value,
            style = MaterialTheme.typography.labelLarge,
            textAlign = TextAlign.End,
            modifier = Modifier.padding(start = MaterialTheme.spacing.lg)
        )
    }
}

@Composable
private fun PersonalisationPanel(onReviewPreferencesClick: () -> Unit) {
    AppCard(style = AppCardStyle.Panel) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
        ) {
            VoraLogo(size = OrbSize, glowAlpha = 0.25f, style = VoraLogoStyle.Insight)
            Text(
                ProfileStrings.PersonalisationTitle,
                style = MaterialTheme.typography.labelLarge,
                color = MaterialTheme.extendedColors.mint
            )
        }
        Text(ProfileStrings.PersonalisationHeadline, style = MaterialTheme.typography.titleSmall)
        Text(
            ProfileStrings.PersonalisationBody,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
        )
        AppPillButton(
            text = ProfileStrings.ReviewPreferences,
            onClick = onReviewPreferencesClick,
            style = AppPillStyle.Accent
        )
    }
}

@Composable
private fun ProfileLink(
    icon: AppIcons,
    title: String,
    subtitle: String,
    onClick: () -> Unit,
    badge: String? = null
) {
    AppCard(onClick = onClick) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
        ) {
            Box(
                modifier = Modifier.size(LinkIconBoxSize).background(MaterialTheme.colorScheme.background, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                AppIcon(
                    icon,
                    contentDescription = null,
                    size = LinkIconSize,
                    tint = MaterialTheme.colorScheme.primary
                )
            }
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xxs)
            ) {
                Text(title, style = MaterialTheme.typography.labelLarge)
                Text(subtitle, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
            if (badge != null) {
                Text(
                    badge,
                    style = MaterialTheme.typography.labelSmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .background(MaterialTheme.colorScheme.primary, PillShape)
                        .padding(horizontal = MaterialTheme.spacing.md, vertical = MaterialTheme.spacing.xs)
                )
            }
            AppIcon(
                AppIcons.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.extendedColors.textSubtle
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 1000)
@Composable
private fun ProfileScreenPreview() {
    AppTheme {
        ProfileScreen(
            uiState = ProfileDemoData.dashboard,
            snackbarHostState = remember { SnackbarHostState() },
            onSettingsClick = {},
            onEditProfileClick = {},
            onGoalsClick = {},
            onPreferencesClick = {},
            onVoiceAiClick = {},
            onConnectedDataClick = {},
            onAccountClick = {}
        )
    }
}
