package com.example.tp3_parcial_grupo_1.ui.screens.workout

import androidx.compose.foundation.background
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
fun PreWorkoutScreen(
    workoutId: String,
    onBackClick: () -> Unit,
    onBeginClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppScaffold(
        title = "Preparación",
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
                Text(
                    text = "Ajusta tu equipo y prepárate para comenzar la sesión.",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )

                Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))

                AppCard(style = AppCardStyle.Panel) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        modifier = Modifier.fillMaxWidth().padding(MaterialTheme.spacing.lg)
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.ic_dumbbell),
                            contentDescription = null,
                            tint = MaterialTheme.colorScheme.onPrimary,
                            modifier = Modifier.size(48.dp)
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))
                        Text(
                            text = "Rutina $workoutId",
                            style = MaterialTheme.typography.headlineSmall,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.onPrimary
                        )
                        Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))
                        Text(
                            text = "Asegúrate de hidratarte y realizar un calentamiento de 5 minutos.",
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                        )
                    }
                }
            }

            Box(modifier = Modifier.fillMaxWidth().padding(vertical = MaterialTheme.spacing.md)) {
                AppButton(
                    text = "Comenzar Entrenamiento",
                    onClick = onBeginClick,
                    style = AppButtonStyle.Accent
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun PreWorkoutScreenPreview() {
    AppTheme {
        PreWorkoutScreen(workoutId = "w01", onBackClick = {}, onBeginClick = {})
    }
}
