package com.example.tp3_parcial_grupo_1.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.KeyboardArrowRight
import androidx.compose.material.icons.automirrored.outlined.Logout
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Explore
import androidx.compose.material.icons.outlined.HelpOutline
import androidx.compose.material.icons.outlined.MonitorHeart
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.material.icons.outlined.Policy
import androidx.compose.material.icons.outlined.Shield
import androidx.compose.material.icons.outlined.Star
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
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
import androidx.compose.foundation.clickable
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppCardStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.strings.ProfileStrings
import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val RowIconSize = 20.dp

@Composable
fun SettingsRoute(
    onBackClick: () -> Unit,
    onLogOutConfirmed: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: SettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.onMessageShown()
        }
    }

    SettingsScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onBackClick = onBackClick,
        onDemoNoticeClick = viewModel::onDemoNoticeClick,
        onLogOutClick = viewModel::onLogOutClick,
        onLogOutConfirmed = {
            viewModel.onLogOutDismissed()
            onLogOutConfirmed()
        },
        onLogOutDismissed = viewModel::onLogOutDismissed,
        modifier = modifier
    )
}

@Composable
fun SettingsScreen(
    uiState: SettingsUiState,
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    onDemoNoticeClick: () -> Unit,
    onLogOutClick: () -> Unit,
    onLogOutConfirmed: () -> Unit,
    onLogOutDismissed: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppScaffold(
        modifier = modifier.fillMaxSize(),
        title = ProfileStrings.SettingsTitle,
        onBack = onBackClick,
        snackbarHostState = snackbarHostState
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = MaterialTheme.spacing.screen, vertical = MaterialTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xl)
        ) {
            Text(
                uiState.email,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            PremiumCard(onManageAccountClick = onDemoNoticeClick)
            SettingsGroup(
                label = ProfileStrings.AppPreferencesLabel,
                rows = listOf(
                    SettingsRowData(Icons.Outlined.Notifications, ProfileStrings.NotificationsRow, ProfileStrings.NotificationsRowSubtitle),
                    SettingsRowData(Icons.Outlined.Explore, ProfileStrings.UnitsRow, ProfileStrings.UnitsRowSubtitle),
                    SettingsRowData(Icons.Outlined.Visibility, ProfileStrings.AppearanceRow, ProfileStrings.AppearanceRowSubtitle),
                    SettingsRowData(Icons.Outlined.MonitorHeart, ProfileStrings.HealthRow, ProfileStrings.HealthRowSubtitle)
                ),
                onRowClick = onDemoNoticeClick
            )
            SettingsGroup(
                label = ProfileStrings.PrivacySecurityLabel,
                rows = listOf(
                    SettingsRowData(Icons.Outlined.Policy, ProfileStrings.PrivacyPolicyRow),
                    SettingsRowData(Icons.Outlined.Shield, ProfileStrings.PermissionsRow)
                ),
                onRowClick = onDemoNoticeClick
            )
            SettingsGroup(
                label = ProfileStrings.SupportLabel,
                rows = listOf(
                    SettingsRowData(Icons.Outlined.HelpOutline, ProfileStrings.HelpCentreRow),
                    SettingsRowData(Icons.Outlined.Email, ProfileStrings.ContactSupportRow)
                ),
                onRowClick = onDemoNoticeClick
            )
            AppButton(
                text = ProfileStrings.LogOut,
                onClick = onLogOutClick,
                style = AppButtonStyle.Outlined,
                leadingIcon = Icons.AutoMirrored.Outlined.Logout
            )
            Text(
                ProfileStrings.AppVersion,
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.extendedColors.textSubtle,
                textAlign = TextAlign.Center,
                modifier = Modifier.fillMaxWidth()
            )
        }
    }

    if (uiState.showLogOutDialog) {
        AlertDialog(
            onDismissRequest = onLogOutDismissed,
            title = { Text(ProfileStrings.LogOutDialogTitle) },
            text = { Text(ProfileStrings.LogOutDialogBody) },
            confirmButton = {
                TextButton(onClick = onLogOutConfirmed) { Text(ProfileStrings.LogOutDialogTitle) }
            },
            dismissButton = {
                TextButton(onClick = onLogOutDismissed) { Text(Strings.Cancel) }
            }
        )
    }
}

@Composable
private fun PremiumCard(onManageAccountClick: () -> Unit) {
    AppCard(style = AppCardStyle.Panel) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
        ) {
            Icon(
                Icons.Outlined.Star,
                contentDescription = null,
                modifier = Modifier.size(RowIconSize),
                tint = MaterialTheme.colorScheme.tertiary
            )
            Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xxs)) {
                Text(ProfileStrings.PremiumName, style = MaterialTheme.typography.titleSmall)
                Text(
                    ProfileStrings.PremiumRenewal,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.7f)
                )
            }
        }
        AppButton(
            text = ProfileStrings.ManageAccount,
            onClick = onManageAccountClick,
            style = AppButtonStyle.Accent
        )
    }
}

private data class SettingsRowData(val icon: ImageVector, val title: String, val subtitle: String? = null)

@Composable
private fun SettingsGroup(label: String, rows: List<SettingsRowData>, onRowClick: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)) {
        Text(label, style = MaterialTheme.typography.labelMedium)
        AppCard {
            rows.forEachIndexed { index, row ->
                if (index > 0) HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
                SettingsRow(row, onClick = onRowClick)
            }
        }
    }
}

@Composable
private fun SettingsRow(row: SettingsRowData, onClick: () -> Unit) {
    Row(
        modifier = Modifier.fillMaxWidth().clickable(onClick = onClick),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
    ) {
        Icon(
            row.icon,
            contentDescription = null,
            modifier = Modifier.size(RowIconSize),
            tint = MaterialTheme.colorScheme.primary
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xxs)
        ) {
            Text(row.title, style = MaterialTheme.typography.labelLarge)
            if (row.subtitle != null) {
                Text(
                    row.subtitle,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        }
        Icon(
            Icons.AutoMirrored.Outlined.KeyboardArrowRight,
            contentDescription = null,
            tint = MaterialTheme.extendedColors.textSubtle
        )
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 1300)
@Composable
private fun SettingsScreenPreview() {
    AppTheme {
        SettingsScreen(
            uiState = SettingsUiState(email = "alex@morgan.com"),
            snackbarHostState = remember { SnackbarHostState() },
            onBackClick = {}, onDemoNoticeClick = {}, onLogOutClick = {},
            onLogOutConfirmed = {}, onLogOutDismissed = {}
        )
    }
}
