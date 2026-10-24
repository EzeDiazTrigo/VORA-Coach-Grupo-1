package com.example.tp3_parcial_grupo_1.ui.screens.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.R
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppCardStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

@Composable
fun ActiveWorkoutScreen(
    workoutId: String,
    onBackClick: () -> Unit,
    onExerciseClick: (String) -> Unit,
    onFinishWorkoutClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var currentSet by remember { mutableIntStateOf(1) }

    AppScaffold(
        title = "Sesión Activa",
        onBack = onBackClick,
        modifier = modifier
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = MaterialTheme.spacing.screen),
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Column {
                AppCard(style = AppCardStyle.Panel) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth().padding(MaterialTheme.spacing.lg)
                    ) {
                        Text(
                            text = "00:14:25",
                            style = MaterialTheme.typography.displayMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))
                        Text(
                            text = "TIEMPO TRANSCURRIDO",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.8f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))

                AppCard(
                    style = AppCardStyle.Surface,
                    onClick = { onExerciseClick("e01") }
                ) {
                    Column(modifier = Modifier.fillMaxWidth().padding(MaterialTheme.spacing.lg)) {
                        Row(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalArrangement = Arrangement.SpaceBetween,
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Text(
                                text = "Sentadilla con Barra",
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold,
                                color = MaterialTheme.colorScheme.onSurface
                            )
                            Icon(
                                painter = painterResource(id = R.drawable.ic_info),
                                contentDescription = "Info",
                                tint = MaterialTheme.colorScheme.primary,
                                modifier = Modifier.size(20.dp)
                            )
                        }

                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.sm))

                        Text(
                            text = "Serie $currentSet de 4 · 10 repeticiones (60kg)",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.primary
                        )
                    }
                }
            }

            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm),
                modifier = Modifier.fillMaxWidth().padding(vertical = MaterialTheme.spacing.md)
            ) {
                AppButton(
                    text = "Siguiente Serie / Ejercicio",
                    onClick = {
                        if (currentSet < 4) currentSet++
                    },
                    style = AppButtonStyle.Accent
                )
                AppButton(
                    text = "Finalizar Entrenamiento",
                    onClick = onFinishWorkoutClick,
                    style = AppButtonStyle.Outlined
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ActiveWorkoutScreenPreview() {
    AppTheme {
        ActiveWorkoutScreen(
            workoutId = "w01",
            onBackClick = {},
            onExerciseClick = {},
            onFinishWorkoutClick = {}
        )
    }
}
