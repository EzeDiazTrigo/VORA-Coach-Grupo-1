package com.example.tp3_parcial_grupo_1.ui.screens.nutrition

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.ui.components.AppBackButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppCardStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppIcons
import com.example.tp3_parcial_grupo_1.ui.components.AppListRow
import com.example.tp3_parcial_grupo_1.ui.components.AppTopBar
import com.example.tp3_parcial_grupo_1.ui.components.VoraOrb
import com.example.tp3_parcial_grupo_1.ui.strings.NutritionStrings
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val AddOptionIconSize = 22.dp
private val AddOptionIconBoxSize = 44.dp

@Composable
fun AddFoodScreen(
    onBackClick: () -> Unit,
    onNavigateToVoice: () -> Unit,
    onNavigateToPhoto: () -> Unit,
    onNavigateToManual: () -> Unit,
    onNavigateToReview: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = MaterialTheme.spacing.screen, vertical = MaterialTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.lg)
        ) {
            AppTopBar(
                title = NutritionStrings.addFoodTitle,
                navigation = { AppBackButton(onClick = onBackClick) },
                titleStyle = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                titleColor = MaterialTheme.colorScheme.onBackground,
                spacing = MaterialTheme.spacing.sm
            )

            // Talk to VORA Card (Panel style)
            AppCard(style = AppCardStyle.Panel) {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
                ) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = NutritionStrings.talkToVora,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        VoraOrb(size = 44.dp, glowAlpha = 0.3f, showRipple = true, icon = null)
                    }
                    Text(
                        text = NutritionStrings.talkToVoraDesc,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.extendedColors.mint
                        )
                    )
                    AppButton(
                        text = NutritionStrings.startVoiceLogging,
                        onClick = onNavigateToVoice,
                        style = AppButtonStyle.Accent,
                        leadingIcon = AppIcons.Mic,
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            // Option Cards
            Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)) {
                AppListRow(
                    icon = AppIcons.Camera,
                    title = NutritionStrings.snapAPhoto,
                    subtitle = NutritionStrings.snapAPhotoDesc,
                    onClick = onNavigateToPhoto,
                    iconSize = AddOptionIconSize,
                    iconBoxSize = AddOptionIconBoxSize,
                    iconContainerColor = MaterialTheme.extendedColors.mintSoft,
                    titleStyle = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    ),
                    subtitleStyle = MaterialTheme.typography.bodySmall,
                    subtitleColor = MaterialTheme.extendedColors.textSubtle,
                    spacing = MaterialTheme.spacing.lg,
                    textSpacing = 2.dp
                )
                AppListRow(
                    icon = AppIcons.Search,
                    title = NutritionStrings.searchDatabase,
                    subtitle = NutritionStrings.searchDatabaseDesc,
                    onClick = onNavigateToReview,
                    iconSize = AddOptionIconSize,
                    iconBoxSize = AddOptionIconBoxSize,
                    iconContainerColor = MaterialTheme.colorScheme.background,
                    titleStyle = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    ),
                    subtitleStyle = MaterialTheme.typography.bodySmall,
                    subtitleColor = MaterialTheme.extendedColors.textSubtle,
                    spacing = MaterialTheme.spacing.lg,
                    textSpacing = 2.dp
                )
                AppListRow(
                    icon = AppIcons.Pen,
                    title = NutritionStrings.logManually,
                    subtitle = NutritionStrings.logManuallyDesc,
                    onClick = onNavigateToManual,
                    iconSize = AddOptionIconSize,
                    iconBoxSize = AddOptionIconBoxSize,
                    iconContainerColor = MaterialTheme.colorScheme.background,
                    titleStyle = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    ),
                    subtitleStyle = MaterialTheme.typography.bodySmall,
                    subtitleColor = MaterialTheme.extendedColors.textSubtle,
                    spacing = MaterialTheme.spacing.lg,
                    textSpacing = 2.dp
                )
            }
        }
    }
}

