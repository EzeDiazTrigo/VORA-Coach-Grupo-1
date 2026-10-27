package com.example.tp3_parcial_grupo_1.ui.screens.nutrition

import com.example.tp3_parcial_grupo_1.ui.theme.spacing
import com.example.tp3_parcial_grupo_1.ui.theme.micro
import com.example.tp3_parcial_grupo_1.ui.theme.message
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.ui.components.AppIcon
import com.example.tp3_parcial_grupo_1.ui.components.AppIcons
import com.example.tp3_parcial_grupo_1.ui.components.VoraOrb
import com.example.tp3_parcial_grupo_1.ui.strings.NutritionStrings
import kotlinx.coroutines.delay

private const val AnalysisDurationMillis = 3_000L

@Composable
fun AnalyzingMealScreen(
    onCancelClick: () -> Unit,
    onAnalysisComplete: () -> Unit
) {
    val currentOnAnalysisComplete by rememberUpdatedState(onAnalysisComplete)
    val analysisEndsAt = rememberSaveable { System.currentTimeMillis() + AnalysisDurationMillis }
    LaunchedEffect(analysisEndsAt) {
        delay(analysisEndsAt - System.currentTimeMillis())
        currentOnAnalysisComplete()
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .safeDrawingPadding()
            .padding(MaterialTheme.spacing.screen)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = MaterialTheme.spacing.lg),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = NutritionStrings.voraAi,
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.surface,
                        fontWeight = FontWeight.Bold
)
                )
                IconButton(
                    onClick = onCancelClick,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.1f))
                ) {
                    AppIcon(AppIcons.Close, contentDescription = NutritionStrings.close, tint = MaterialTheme.colorScheme.surface, size = 18.dp)
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            // Audio Orb
            VoraOrb(size = 140.dp, glowAlpha = 0.3f, showRipple = true)

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = NutritionStrings.analyzingMeal,
                style = MaterialTheme.typography.headlineMedium.copy(
                    color = MaterialTheme.colorScheme.surface,
                    fontWeight = FontWeight.Bold
)
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.xl))

            // Captured Input Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.1f))
                    .padding(MaterialTheme.spacing.lg)
            ) {
                Column {
                    Text(
                        text = NutritionStrings.capturedInput,
                        style = MaterialTheme.typography.micro.copy(
                            color = MaterialTheme.colorScheme.secondary,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.xxs))
                    Text(
                        text = NutritionStrings.capturedInputText,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.surface
)
                    )
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.xl))

            // Steps Checklist
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.lg)
            ) {
                AnalysisStep(text = NutritionStrings.identifyingFoods, isDone = true)
                AnalysisStep(text = NutritionStrings.estimatingPortions, isInProgress = true)
                AnalysisStep(text = NutritionStrings.calculatingNutrition, isPending = true)
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.xxl))

            TextButton(
                onClick = onCancelClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = NutritionStrings.cancelAnalysis,
                    style = MaterialTheme.typography.message.copy(
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))
        }
    }
}

@Composable
fun AnalysisStep(text: String, isDone: Boolean = false, isInProgress: Boolean = false, isPending: Boolean = false) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        val dotColor = when {
            isDone -> MaterialTheme.colorScheme.secondary
            isInProgress -> MaterialTheme.colorScheme.tertiary
            else -> MaterialTheme.colorScheme.surface.copy(alpha = 0.3f)
        }
        Box(
            modifier = Modifier
                .size(10.dp)
                .clip(CircleShape)
                .background(dotColor)
        )
        Spacer(modifier = Modifier.width(MaterialTheme.spacing.md))
        Text(
            text = text,
            style = MaterialTheme.typography.message.copy(
                color = if (isPending) MaterialTheme.colorScheme.surface.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surface,
                fontWeight = if (isInProgress) FontWeight.Bold else FontWeight.Normal
)
        )
    }
}
