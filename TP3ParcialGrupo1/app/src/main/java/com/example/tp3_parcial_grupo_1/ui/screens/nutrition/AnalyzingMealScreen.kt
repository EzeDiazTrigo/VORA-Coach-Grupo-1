package com.example.tp3_parcial_grupo_1.ui.screens.nutrition

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
import androidx.compose.ui.unit.sp
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
            .padding(20.dp)
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            // Top bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = NutritionStrings.voraAi,
                    style = MaterialTheme.typography.titleMedium.copy(
                        color = MaterialTheme.colorScheme.surface,
                        fontWeight = FontWeight.Bold,
                        fontSize = 14.sp
                    )
                )
                IconButton(
                    onClick = onCancelClick,
                    modifier = Modifier
                        .size(36.dp)
                        .clip(CircleShape)
                        .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.1f))
                ) {
                    AppIcon(AppIcons.Close, contentDescription = "Close", tint = MaterialTheme.colorScheme.surface, size = 18.dp)
                }
            }

            Spacer(modifier = Modifier.height(36.dp))

            // Audio Orb
            VoraOrb(size = 140.dp, glowAlpha = 0.3f, showRipple = true)

            Spacer(modifier = Modifier.height(28.dp))

            Text(
                text = NutritionStrings.analyzingMeal,
                style = MaterialTheme.typography.headlineLarge.copy(
                    color = MaterialTheme.colorScheme.surface,
                    fontWeight = FontWeight.Bold,
                    fontSize = 24.sp
                )
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Captured Input Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(MaterialTheme.colorScheme.surface.copy(alpha = 0.1f))
                    .padding(16.dp)
            ) {
                Column {
                    Text(
                        text = NutritionStrings.capturedInput,
                        style = MaterialTheme.typography.labelSmall.copy(
                            color = MaterialTheme.colorScheme.secondary,
                            fontSize = 10.sp,
                            fontWeight = FontWeight.Bold
                        )
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = "\"Grilled chicken, rice and mixed vegetables\"",
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.surface,
                            fontSize = 13.sp
                        )
                    )
                }
            }

            Spacer(modifier = Modifier.height(24.dp))

            // Steps Checklist
            Column(
                modifier = Modifier.fillMaxWidth(),
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                AnalysisStep(text = "Identifying foods...", isDone = true)
                AnalysisStep(text = "Estimating portions...", isInProgress = true)
                AnalysisStep(text = "Calculating nutrition...", isPending = true)
            }

            Spacer(modifier = Modifier.height(32.dp))

            TextButton(
                onClick = onCancelClick,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = NutritionStrings.cancelAnalysis,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.8f),
                        fontSize = 14.sp,
                        fontWeight = FontWeight.SemiBold
                    )
                )
            }
            Spacer(modifier = Modifier.height(16.dp))
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
        Spacer(modifier = Modifier.width(12.dp))
        Text(
            text = text,
            style = MaterialTheme.typography.bodyMedium.copy(
                color = if (isPending) MaterialTheme.colorScheme.surface.copy(alpha = 0.5f) else MaterialTheme.colorScheme.surface,
                fontWeight = if (isInProgress) FontWeight.Bold else FontWeight.Normal,
                fontSize = 14.sp
            )
        )
    }
}
