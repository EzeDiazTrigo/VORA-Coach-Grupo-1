package com.example.tp3_parcial_grupo_1.ui.screens.workout

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.R
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.VoraSymbol
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

@Composable
fun PreWorkoutScreen(
    workoutId: String,
    onBackClick: () -> Unit,
    onBeginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
            .padding(horizontal = MaterialTheme.spacing.screen, vertical = MaterialTheme.spacing.lg)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            // Top Header Row
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = "GET READY",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.extendedColors.mint
                )

                IconButton(
                    onClick = onBackClick,
                    modifier = Modifier.size(32.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_close),
                        contentDescription = "Close",
                        tint = MaterialTheme.colorScheme.onPrimary
                    )
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))

            // Title Section
            Text(
                text = "Upper Body Strength",
                style = MaterialTheme.typography.headlineLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onPrimary
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "42 min • 5 exercises",
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.extendedColors.mint
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.xl))

            // Card 1: TODAY'S FOCUS
            DarkWorkoutCard {
                Text(
                    text = "TODAY'S FOCUS",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))
                Text(
                    text = "Controlled Repetitions",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Keep concentric lifts explosive and focus on a slow, 2-second eccentric return for maximal hypertrophy stimulus.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))

            // Card 2: YOUR LAST SESSION
            DarkWorkoutCard {
                Text(
                    text = "YOUR LAST SESSION",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))
                Text(
                    text = "Bench Press Volume",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )
                Spacer(modifier = Modifier.height(4.dp))
                Text(
                    text = "Set 1: 40 kg × 10 • Set 2: 40 kg × 9 • Set 3: 37.5 kg × 10",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))

            // Card 3: VORA'S COACH TIP
            DarkWorkoutCard {
                Text(
                    text = "VORA'S COACH TIP",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))
                Text(
                    text = "\"Start slightly lighter on your first set. If your form feels exceptionally robust, increase the load.\"",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))

            // Card 4: Need anything? Mic Widget
            DarkWorkoutCard {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            VoraSymbol(size = 18.dp)
                            Spacer(modifier = Modifier.width(MaterialTheme.spacing.xs))
                            Text(
                                text = "Need anything?",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.extendedColors.mint
                            )
                        }
                        Spacer(modifier = Modifier.height(2.dp))
                        Text(
                            text = "\"Recommend a chest warm-up\"",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                        )
                    }

                    Box(
                        modifier = Modifier
                            .size(44.dp)
                            .clip(CircleShape)
                            .background(MaterialTheme.colorScheme.tertiary),
                        contentAlignment = Alignment.Center
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_mic),
                            contentDescription = "Voice Assistant",
                            tint = MaterialTheme.colorScheme.onTertiary,
                            modifier = Modifier.size(20.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))

            // Mint #65D6B0 Start Workout Button directly below cards
            AppButton(
                text = "Start Workout",
                onClick = onBeginClick,
                style = AppButtonStyle.Accent,
                containerColor = Color(0xFF65D6B0),
                textColor = Color(0xFF123D35)
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))
        }
    }
}

@Composable
private fun DarkWorkoutCard(
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.12f))
            .padding(MaterialTheme.spacing.lg)
    ) {
        Column(content = content)
    }
}

@Preview(showBackground = true)
@Composable
private fun PreWorkoutScreenPreview() {
    AppTheme {
        PreWorkoutScreen(workoutId = "w01", onBackClick = {}, onBeginClick = {})
    }
}
