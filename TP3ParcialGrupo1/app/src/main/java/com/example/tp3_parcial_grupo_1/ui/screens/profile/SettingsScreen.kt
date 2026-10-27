package com.example.tp3_parcial_grupo_1.ui.screens.profile

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tp3_parcial_grupo_1.ui.components.AppIcons
import com.example.tp3_parcial_grupo_1.ui.components.AppListRow
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.components.AppSection
import com.example.tp3_parcial_grupo_1.ui.strings.ProfileStrings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val BorderWidth = 1.dp
private val LogOutMinHeight = 48.dp

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
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.lg)
        ) {
            SettingsGroup(
                label = ProfileStrings.Account,
                rows = listOf(
                    SettingsRowData(AppIcons.User, uiState.fullName, uiState.email),
                    SettingsRowData(AppIcons.Star, ProfileStrings.PremiumName, ProfileStrings.PremiumRenewal),
                    SettingsRowData(AppIcons.Edit, ProfileStrings.ManageAccount)
                ),
                onRowClick = onDemoNoticeClick
            )
            SettingsGroup(
                label = ProfileStrings.AppPreferencesLabel,
                rows = listOf(
                    SettingsRowData(AppIcons.Bell, ProfileStrings.NotificationsRow, ProfileStrings.NotificationsRowSubtitle),
                    SettingsRowData(AppIcons.Compass, ProfileStrings.UnitsRow, ProfileStrings.UnitsRowSubtitle),
                    SettingsRowData(AppIcons.Eye, ProfileStrings.AppearanceRow, ProfileStrings.AppearanceRowSubtitle)
                ),
                onRowClick = onDemoNoticeClick
            )
            SettingsGroup(
                label = ProfileStrings.ConnectedData,
                rows = listOf(
                    SettingsRowData(AppIcons.Activity, ProfileStrings.HealthRow, ProfileStrings.HealthRowSubtitle)
                ),
                onRowClick = onDemoNoticeClick
            )
            SettingsGroup(
                label = ProfileStrings.PrivacySecurityLabel,
                rows = listOf(
                    SettingsRowData(AppIcons.Lock, ProfileStrings.PrivacyPolicyRow),
                    SettingsRowData(AppIcons.Shield, ProfileStrings.PermissionsRow)
                ),
                onRowClick = onDemoNoticeClick
            )
            SettingsGroup(
                label = ProfileStrings.SupportLabel,
                rows = listOf(
                    SettingsRowData(AppIcons.HelpCircle, ProfileStrings.HelpCentreRow),
                    SettingsRowData(AppIcons.Mail, ProfileStrings.ContactSupportRow)
                ),
                onRowClick = onDemoNoticeClick
            )
            OutlinedButton(
                onClick = onLogOutClick,
                modifier = Modifier.fillMaxWidth().heightIn(min = LogOutMinHeight),
                shape = PillShape,
                border = BorderStroke(BorderWidth, MaterialTheme.colorScheme.error),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = MaterialTheme.colorScheme.error)
            ) {
                Text(ProfileStrings.LogOut, style = MaterialTheme.typography.labelLarge)
            }
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
                TextButton(onClick = onLogOutDismissed) { Text(ProfileStrings.LogOutDialogCancel) }
            }
        )
    }
}

private data class SettingsRowData(val icon: AppIcons, val title: String, val subtitle: String? = null)

@Composable
private fun SettingsGroup(label: String, rows: List<SettingsRowData>, onRowClick: () -> Unit) {
    AppSection(label = label, spacing = MaterialTheme.spacing.sm) {
        rows.forEach { row -> AppListRow(row.icon, title = row.title, subtitle = row.subtitle, onClick = onRowClick) }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 1300)
@Composable
private fun SettingsScreenPreview() {
    AppTheme {
        SettingsScreen(
            uiState = SettingsUiState(fullName = "Alex Morgan", email = "alex@morgan.com"),
            snackbarHostState = remember { SnackbarHostState() },
            onBackClick = {}, onDemoNoticeClick = {}, onLogOutClick = {},
            onLogOutConfirmed = {}, onLogOutDismissed = {}
        )
    }
}
