package com.example.tp3_parcial_grupo_1.ui.screens.profile

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
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.MonitorHeart
import androidx.compose.material.icons.outlined.Person
import androidx.compose.material.icons.outlined.Settings
import androidx.compose.material.icons.outlined.Tune
import androidx.compose.material3.HorizontalDivider
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
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tp3_parcial_grupo_1.data.demo.ProfileDemoData
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppCardStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.strings.ProfileStrings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val BorderWidth = 1.dp
private val AvatarSize = 64.dp
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
    AppScaffold(modifier = modifier.fillMaxSize(), snackbarHostState = snackbarHostState) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = MaterialTheme.spacing.screen, vertical = MaterialTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.lg)
        ) {
            ProfileHeader(uiState, onSettingsClick)
            AppButton(
                text = ProfileStrings.EditProfile,
                onClick = onEditProfileClick,
                style = AppButtonStyle.Outlined
            )
            SnapshotCard(uiState)
            PersonalisationPanel(onReviewPreferencesClick = onVoiceAiClick)
            ProfileLink(
                icon = Icons.Outlined.Tune,
                title = ProfileStrings.YourGoals,
                subtitle = ProfileStrings.YourGoalsSubtitle,
                onClick = onGoalsClick
            )
            ProfileLink(
                icon = Icons.Outlined.Person,
                title = ProfileStrings.YourPreferences,
                subtitle = ProfileStrings.YourPreferencesSubtitle,
                onClick = onPreferencesClick
            )
            ProfileLink(
                icon = Icons.Outlined.MonitorHeart,
                title = ProfileStrings.ConnectedData,
                subtitle = ProfileStrings.ConnectedDataSubtitle,
                onClick = onConnectedDataClick,
                badge = if (uiState.isDataSynced) ProfileStrings.Synced else null
            )
            ProfileLink(
                icon = Icons.Outlined.Lock,
                title = ProfileStrings.Account,
                subtitle = ProfileStrings.AccountSubtitle,
                onClick = onAccountClick
            )
        }
    }
}

@Composable
private fun ProfileHeader(uiState: ProfileUiState, onSettingsClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.lg)
    ) {
        // Marcador del avatar; se reemplaza por la foto exportada de Figma.
        Box(
            modifier = Modifier.size(AvatarSize).background(MaterialTheme.extendedColors.mintSoft, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Text(uiState.initials, style = MaterialTheme.typography.titleMedium, color = MaterialTheme.colorScheme.primary)
        }
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xxs)
        ) {
            Text(uiState.fullName, style = MaterialTheme.typography.titleLarge)
            Text(
                uiState.tagline,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
        }
        IconButton(
            onClick = onSettingsClick,
            colors = IconButtonDefaults.iconButtonColors(containerColor = MaterialTheme.colorScheme.surface),
            modifier = Modifier.border(BorderWidth, MaterialTheme.colorScheme.outlineVariant, CircleShape)
        ) {
            Icon(Icons.Outlined.Settings, contentDescription = ProfileStrings.SettingsButton)
        }
    }
}

@Composable
private fun SnapshotCard(uiState: ProfileUiState) {
    AppCard {
        Text(ProfileStrings.MySnapshot, style = MaterialTheme.typography.titleSmall)
        SnapshotRow(ProfileStrings.Goal, uiState.goal)
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        SnapshotRow(ProfileStrings.Experience, uiState.experience)
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
        SnapshotRow(ProfileStrings.Training, uiState.training)
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
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
        Text(
            ProfileStrings.PersonalisationTitle,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.extendedColors.mint
        )
        Text(ProfileStrings.PersonalisationHeadline, style = MaterialTheme.typography.headlineSmall)
        Text(
            ProfileStrings.PersonalisationBody,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
        )
        AppButton(
            text = ProfileStrings.ReviewPreferences,
            onClick = onReviewPreferencesClick,
            style = AppButtonStyle.Accent
        )
    }
}

@Composable
private fun ProfileLink(
    icon: ImageVector,
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
                modifier = Modifier.size(LinkIconBoxSize).background(MaterialTheme.extendedColors.mintSoft, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(icon, contentDescription = null, modifier = Modifier.size(LinkIconSize), tint = MaterialTheme.colorScheme.primary)
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
                    color = MaterialTheme.extendedColors.success,
                    modifier = Modifier
                        .background(MaterialTheme.extendedColors.mintSoft, PillShape)
                        .padding(horizontal = MaterialTheme.spacing.md, vertical = MaterialTheme.spacing.xs)
                )
            }
            Icon(
                Icons.AutoMirrored.Outlined.KeyboardArrowRight,
                contentDescription = null,
                tint = MaterialTheme.extendedColors.textSubtle
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 900)
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
