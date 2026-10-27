package com.example.tp3_parcial_grupo_1.ui.screens.profile

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tp3_parcial_grupo_1.data.demo.ProfileDemoData
import com.example.tp3_parcial_grupo_1.model.CoachingStyle
import com.example.tp3_parcial_grupo_1.model.ResponseStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppChip
import com.example.tp3_parcial_grupo_1.ui.components.AppChipStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppIcon
import com.example.tp3_parcial_grupo_1.ui.components.AppIcons
import com.example.tp3_parcial_grupo_1.ui.components.AppNoticeCard
import com.example.tp3_parcial_grupo_1.ui.components.AppOptionCard
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.components.AppSection
import com.example.tp3_parcial_grupo_1.ui.components.AppSwitchRow
import com.example.tp3_parcial_grupo_1.ui.components.AppTextLink
import com.example.tp3_parcial_grupo_1.ui.strings.ProfileStrings
import com.example.tp3_parcial_grupo_1.ui.strings.description
import com.example.tp3_parcial_grupo_1.ui.strings.title
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val VoiceIconBoxSize = 40.dp
private val VoiceIconSize = 20.dp

@Composable
fun VoiceSettingsRoute(
    onBackClick: () -> Unit,
    onPrivacyClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: VoiceSettingsViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(uiState.message) {
        uiState.message?.let {
            snackbarHostState.showSnackbar(it)
            viewModel.onMessageShown()
        }
    }

    VoiceSettingsScreen(
        uiState = uiState,
        snackbarHostState = snackbarHostState,
        onBackClick = onBackClick,
        onCoachingStyleSelected = viewModel::onCoachingStyleSelected,
        onPreviewClick = viewModel::onPreviewClick,
        onSpeakResponsesChange = viewModel::onSpeakResponsesChange,
        onAutoPlayChange = viewModel::onAutoPlayChange,
        onAllowInterruptionsChange = viewModel::onAllowInterruptionsChange,
        onResponseStyleSelected = viewModel::onResponseStyleSelected,
        onVoiceFeedbackChange = viewModel::onVoiceFeedbackChange,
        onHapticFeedbackChange = viewModel::onHapticFeedbackChange,
        onPrivacyClick = onPrivacyClick,
        modifier = modifier
    )
}

@OptIn(ExperimentalLayoutApi::class)
@Composable
fun VoiceSettingsScreen(
    uiState: VoiceSettingsUiState,
    snackbarHostState: SnackbarHostState,
    onBackClick: () -> Unit,
    onCoachingStyleSelected: (CoachingStyle) -> Unit,
    onPreviewClick: () -> Unit,
    onSpeakResponsesChange: (Boolean) -> Unit,
    onAutoPlayChange: (Boolean) -> Unit,
    onAllowInterruptionsChange: (Boolean) -> Unit,
    onResponseStyleSelected: (ResponseStyle) -> Unit,
    onVoiceFeedbackChange: (Boolean) -> Unit,
    onHapticFeedbackChange: (Boolean) -> Unit,
    onPrivacyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppScaffold(
        modifier = modifier.fillMaxSize(),
        title = ProfileStrings.VoiceTitle,
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
                ProfileStrings.VoiceIntro,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            AppSection(ProfileStrings.CoachingStyleLabel) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)
                ) {
                    CoachingStyle.entries.forEach { style ->
                        AppChip(
                            text = style.title(),
                            selected = style == uiState.coachingStyle,
                            onClick = { onCoachingStyleSelected(style) },
                            style = AppChipStyle.Dark,
                            shape = MaterialTheme.shapes.small
                        )
                    }
                }
            }
            AppSection(ProfileStrings.ActiveVoiceLabel) {
                AppCard {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
                    ) {
                        Box(
                            modifier = Modifier.size(VoiceIconBoxSize).background(MaterialTheme.colorScheme.primary, CircleShape),
                            contentAlignment = Alignment.Center
                        ) {
                            AppIcon(
                                AppIcons.Volume,
                                contentDescription = null,
                                size = VoiceIconSize,
                                tint = MaterialTheme.colorScheme.onPrimary
                            )
                        }
                        Text(
                            uiState.voiceName,
                            style = MaterialTheme.typography.labelLarge,
                            modifier = Modifier.weight(1f)
                        )
                        AppButton(
                            text = ProfileStrings.Preview,
                            onClick = onPreviewClick,
                            style = AppButtonStyle.Outlined,
                            leadingIcon = AppIcons.AudioWaveform,
                            isCompact = true,
                            modifier = Modifier.weight(1f)
                        )
                    }
                }
            }
            AppSection(ProfileStrings.SpeakingBehaviourLabel) {
                AppCard {
                    AppSwitchRow(
                        title = ProfileStrings.SpeakResponsesAloud,
                        checked = uiState.speakResponses,
                        onCheckedChange = onSpeakResponsesChange
                    )
                    AppSwitchRow(
                        title = ProfileStrings.AutoPlayCoaching,
                        checked = uiState.autoPlayCoaching,
                        onCheckedChange = onAutoPlayChange
                    )
                    AppSwitchRow(
                        title = ProfileStrings.AllowInterruptions,
                        subtitle = ProfileStrings.AllowInterruptionsHint,
                        checked = uiState.allowInterruptions,
                        onCheckedChange = onAllowInterruptionsChange
                    )
                }
            }
            AppSection(ProfileStrings.ResponseStyleLabel) {
                ResponseStyle.entries.forEach { style ->
                    AppOptionCard(
                        title = style.title(),
                        description = style.description(),
                        selected = style == uiState.responseStyle,
                        onClick = { onResponseStyleSelected(style) }
                    )
                }
            }
            AppCard {
                AppSwitchRow(
                    title = ProfileStrings.VoiceFeedback,
                    checked = uiState.voiceFeedback,
                    onCheckedChange = onVoiceFeedbackChange
                )
                AppSwitchRow(
                    title = ProfileStrings.HapticFeedback,
                    checked = uiState.hapticFeedback,
                    onCheckedChange = onHapticFeedbackChange
                )
            }
            AppNoticeCard(
                text = ProfileStrings.ControlBody,
                title = ProfileStrings.ControlTitle,
                shape = MaterialTheme.shapes.large,
                contentPadding = MaterialTheme.spacing.lg,
                spacing = MaterialTheme.spacing.xs,
                footer = {
                    Spacer(Modifier.height(MaterialTheme.spacing.md))
                    AppTextLink(
                        text = ProfileStrings.PrivacyAndPermissions,
                        onClick = onPrivacyClick,
                        style = MaterialTheme.typography.labelMedium,
                        fontWeight = FontWeight.Bold
                    )
                }
            )
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 1600)
@Composable
private fun VoiceSettingsScreenPreview() {
    AppTheme {
        VoiceSettingsScreen(
            uiState = ProfileDemoData.voiceSettings.toUiState(ProfileDemoData.profile.coachingStyle),
            snackbarHostState = remember { SnackbarHostState() },
            onBackClick = {}, onCoachingStyleSelected = {}, onPreviewClick = {}, onSpeakResponsesChange = {},
            onAutoPlayChange = {}, onAllowInterruptionsChange = {}, onResponseStyleSelected = {},
            onVoiceFeedbackChange = {}, onHapticFeedbackChange = {}, onPrivacyClick = {}
        )
    }
}
