package com.example.tp3_parcial_grupo_1.ui.screens.profile

import androidx.annotation.DrawableRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tp3_parcial_grupo_1.R
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.components.AppSectionLabel
import com.example.tp3_parcial_grupo_1.ui.strings.ProfileStrings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val BorderWidth = 1.dp
private val RowIconBoxSize = 40.dp
private val RowIconSize = 20.dp
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
                    SettingsRowData(R.drawable.ic_user, uiState.fullName, uiState.email),
                    SettingsRowData(R.drawable.ic_star, ProfileStrings.PremiumName, ProfileStrings.PremiumRenewal),
                    SettingsRowData(R.drawable.ic_edit, ProfileStrings.ManageAccount)
                ),
                onRowClick = onDemoNoticeClick
            )
            SettingsGroup(
                label = ProfileStrings.AppPreferencesLabel,
                rows = listOf(
                    SettingsRowData(R.drawable.ic_bell, ProfileStrings.NotificationsRow, ProfileStrings.NotificationsRowSubtitle),
                    SettingsRowData(R.drawable.ic_compass, ProfileStrings.UnitsRow, ProfileStrings.UnitsRowSubtitle),
                    SettingsRowData(R.drawable.ic_eye, ProfileStrings.AppearanceRow, ProfileStrings.AppearanceRowSubtitle)
                ),
                onRowClick = onDemoNoticeClick
            )
            SettingsGroup(
                label = ProfileStrings.ConnectedData,
                rows = listOf(
                    SettingsRowData(R.drawable.ic_activity, ProfileStrings.HealthRow, ProfileStrings.HealthRowSubtitle)
                ),
                onRowClick = onDemoNoticeClick
            )
            SettingsGroup(
                label = ProfileStrings.PrivacySecurityLabel,
                rows = listOf(
                    SettingsRowData(R.drawable.ic_lock, ProfileStrings.PrivacyPolicyRow),
                    SettingsRowData(R.drawable.ic_shield, ProfileStrings.PermissionsRow)
                ),
                onRowClick = onDemoNoticeClick
            )
            SettingsGroup(
                label = ProfileStrings.SupportLabel,
                rows = listOf(
                    SettingsRowData(R.drawable.ic_help_circle, ProfileStrings.HelpCentreRow),
                    SettingsRowData(R.drawable.ic_mail, ProfileStrings.ContactSupportRow)
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

private data class SettingsRowData(@DrawableRes val iconRes: Int, val title: String, val subtitle: String? = null)

@Composable
private fun SettingsGroup(label: String, rows: List<SettingsRowData>, onRowClick: () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)) {
        AppSectionLabel(label)
        rows.forEach { row -> SettingsRow(row, onClick = onRowClick) }
    }
}

@Composable
private fun SettingsRow(row: SettingsRowData, onClick: () -> Unit) {
    AppCard(onClick = onClick) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
        ) {
            Box(
                modifier = Modifier.size(RowIconBoxSize).background(MaterialTheme.colorScheme.background, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    painterResource(row.iconRes),
                    contentDescription = null,
                    modifier = Modifier.size(RowIconSize),
                    tint = MaterialTheme.colorScheme.primary
                )
            }
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
                painterResource(R.drawable.ic_chevron_right),
                contentDescription = null,
                tint = MaterialTheme.extendedColors.textSubtle
            )
        }
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
