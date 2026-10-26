package com.example.tp3_parcial_grupo_1.ui.screens.workout

import android.app.Activity
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.example.tp3_parcial_grupo_1.R
import com.example.tp3_parcial_grupo_1.model.Workout
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.VoraLogo
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

@Composable
fun PreWorkoutScreen(
    workout: Workout? = null,
    workoutId: String = "w01",
    onBackClick: () -> Unit,
    onBeginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = android.graphics.Color.parseColor("#123D35")
            window.navigationBarColor = android.graphics.Color.parseColor("#123D35")
            window.isNavigationBarContrastEnforced = false
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = false
            WindowCompat.getInsetsController(window, view).isAppearanceLightNavigationBars = false
        }
    }

    val workoutName = workout?.name ?: "Upper Body Strength"
    val durationMinutes = workout?.durationMinutes ?: 42
    val exerciseCount = if (workout?.exercises?.isNotEmpty() == true) workout.exercises.size else (workout?.exerciseCount ?: 5)

    Box(
        modifier = modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .statusBarsPadding()
                .padding(horizontal = MaterialTheme.spacing.screen, vertical = MaterialTheme.spacing.md)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
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

            // Title Section (Dynamic workout name)
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = workoutName,
                    style = MaterialTheme.typography.headlineLarge,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onPrimary
                )

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "$durationMinutes min • $exerciseCount exercises",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.extendedColors.mint
                )
            }

            // Card 1: TODAY'S FOCUS (Has border)
            DarkWorkoutCard(hasBorder = true) {
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

            // Card 2: YOUR LAST SESSION (Has border)
            DarkWorkoutCard(hasBorder = true) {
                Text(
                    text = "YOUR LAST SESSION",
                    style = MaterialTheme.typography.labelSmall,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.tertiary
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))
                Text(
                    text = "$workoutName Volume",
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

            // Card 3: VORA'S COACH TIP (Has border)
            DarkWorkoutCard(hasBorder = true) {
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

            // Card 4: Need anything? Mic Widget (No border)
            DarkWorkoutCard(hasBorder = false) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Column {
                        Row(verticalAlignment = Alignment.CenterVertically) {
                            VoraLogo(size = 18.dp)
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

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))

            // Mint #65D6B0 Start Workout Button directly below cards
            AppButton(
                text = "Start Workout",
                onClick = onBeginClick,
                style = AppButtonStyle.Accent,
                containerColor = Color(0xFF65D6B0),
                textColor = Color(0xFF123D35)
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))
        }
    }
}

@Composable
private fun DarkWorkoutCard(
    hasBorder: Boolean = true,
    content: @Composable ColumnScope.() -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .clip(MaterialTheme.shapes.large)
            .background(MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.12f))
            .then(
                if (hasBorder) Modifier.border(1.dp, Color.White.copy(alpha = 0.1216f), MaterialTheme.shapes.large)
                else Modifier
            )
            .padding(MaterialTheme.spacing.lg)
    ) {
        Column(content = content)
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 676)
@Composable
private fun PreWorkoutScreenPreview() {
    AppTheme {
        PreWorkoutScreen(workout = null, workoutId = "w01", onBackClick = {}, onBeginClick = {})
    }
}
