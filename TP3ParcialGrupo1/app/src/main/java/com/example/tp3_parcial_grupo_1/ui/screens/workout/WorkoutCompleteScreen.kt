package com.example.tp3_parcial_grupo_1.ui.screens.workout

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
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
fun WorkoutCompleteScreen(
    workoutId: String,
    onBackToWorkoutsClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppScaffold(
        title = "Sesión Finalizada",
        onBack = onBackToWorkoutsClick,
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
                        Icon(
                            painter = painterResource(id = R.drawable.ic_trophy),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.tertiary,
                            modifier = Modifier.size(56.dp)
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))
                        Text(
                            text = "¡Excelente Trabajo!",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))
                        Text(
                            text = "Has completado con éxito la rutina $workoutId",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                        )
                    }
                }

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))

                AppCard(style = AppCardStyle.Surface) {
                    Column(modifier = Modifier.fillMaxWidth().padding(MaterialTheme.spacing.lg)) {
                        Text(
                            text = "Resumen del Entrenamiento",
                            style = MaterialTheme.typography.titleMedium,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onSurface
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.sm))
                        Text(
                            text = "Duración: 45 min · Volumen estimado: 1,420 kg · Calorías: 320 kcal",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Box(modifier = Modifier.fillMaxWidth().padding(vertical = MaterialTheme.spacing.md)) {
                AppButton(
                    text = "Volver a Entrenamientos",
                    onClick = onBackToWorkoutsClick,
                    style = AppButtonStyle.Accent
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun WorkoutCompleteScreenPreview() {
    AppTheme {
        WorkoutCompleteScreen(workoutId = "w01", onBackToWorkoutsClick = {})
    }
}
