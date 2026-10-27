package com.example.tp3_parcial_grupo_1.ui.screens.nutrition

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.ui.components.AppBackButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppCardStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppIcon
import com.example.tp3_parcial_grupo_1.ui.components.AppIcons
import com.example.tp3_parcial_grupo_1.ui.components.AppTopBar
import com.example.tp3_parcial_grupo_1.ui.components.VoraOrb
import com.example.tp3_parcial_grupo_1.ui.strings.NutritionStrings
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

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
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column(
                        modifier = Modifier.weight(1f),
                        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)
                    ) {
                        Text(
                            text = NutritionStrings.talkToVora,
                            style = MaterialTheme.typography.titleMedium.copy(
                                color = MaterialTheme.colorScheme.onPrimary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = NutritionStrings.talkToVoraDesc,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                            )
                        )
                        Box(modifier = Modifier.width(200.dp)) {
                            AppButton(
                                text = NutritionStrings.startVoiceLogging,
                                onClick = onNavigateToVoice,
                                style = AppButtonStyle.Accent,
                                isCompact = true
                            )
                        }
                    }
                    Spacer(modifier = Modifier.width(MaterialTheme.spacing.md))
                    VoraOrb(size = 44.dp, glowAlpha = 0.3f, showRipple = true, icon = null)
                }
            }

            // Option Cards
            Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)) {
                AddOptionCard(
                    icon = AppIcons.Camera,
                    title = NutritionStrings.snapAPhoto,
                    subtitle = NutritionStrings.snapAPhotoDesc,
                    onClick = onNavigateToPhoto
                )
                AddOptionCard(
                    icon = AppIcons.Search,
                    title = NutritionStrings.searchDatabase,
                    subtitle = NutritionStrings.searchDatabaseDesc,
                    onClick = onNavigateToReview
                )
                AddOptionCard(
                    icon = AppIcons.Pen,
                    title = NutritionStrings.logManually,
                    subtitle = NutritionStrings.logManuallyDesc,
                    onClick = onNavigateToManual
                )
            }
        }
    }
}

@Composable
fun AddOptionCard(icon: AppIcons, title: String, subtitle: String, onClick: () -> Unit) {
    AppCard(onClick = onClick) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(44.dp)
                    .clip(CircleShape)
                    .background(MaterialTheme.extendedColors.mintSoft),
                contentAlignment = Alignment.Center
            ) {
                AppIcon(
                    icon,
                    contentDescription = title,
                    tint = MaterialTheme.colorScheme.primary,
                    size = 22.dp
                )
            }
            Spacer(modifier = Modifier.width(MaterialTheme.spacing.lg))
            Column(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = title,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
                    )
                )
                Text(
                    text = subtitle,
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.extendedColors.textSubtle
                    )
                )
            }
        }
    }
}
