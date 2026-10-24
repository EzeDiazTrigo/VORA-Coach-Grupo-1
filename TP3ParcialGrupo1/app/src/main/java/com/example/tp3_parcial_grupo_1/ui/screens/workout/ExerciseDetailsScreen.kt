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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.R
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppCardStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

@Composable
fun ExerciseDetailsScreen(
    workoutId: String,
    exerciseId: String,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppScaffold(
        title = "Guía de Ejercicio",
        onBack = onBackClick,
        modifier = modifier
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = MaterialTheme.spacing.screen)
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
        ) {
            AppCard(style = AppCardStyle.Panel) {
                Column(modifier = Modifier.fillMaxWidth().padding(MaterialTheme.spacing.lg)) {
                    Box(
                        modifier = Modifier
                            .clip(PillShape)
                            .background(MaterialTheme.colorScheme.tertiary)
                            .padding(horizontal = MaterialTheme.spacing.sm, vertical = MaterialTheme.spacing.xxs)
                    ) {
                        Text(
                            text = "Piernas",
                            style = MaterialTheme.typography.labelSmall,
                            color = MaterialTheme.colorScheme.onTertiary,
                            fontWeight = FontWeight.Bold
                        )
                    }
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.sm))
                    Text(
                        text = "Sentadilla con Barra ($exerciseId)",
                        style = MaterialTheme.typography.headlineSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))
                    Text(
                        text = "4 series x 10 repeticiones · 90s descanso",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.85f)
                    )
                }
            }

            Text(
                text = "Instrucciones de Ejecución",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            AppCard(style = AppCardStyle.Surface) {
                Column(modifier = Modifier.fillMaxWidth().padding(MaterialTheme.spacing.lg)) {
                    Text(
                        text = "1. Coloca la barra sobre la parte superior de la espalda.\n2. Mantén los pies a la anchura de los hombros.\n3. Flexiona las rodillas y caderas hasta romper el paralelo.\n4. Empuja fuerte desde los talones para volver a la posición inicial.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Text(
                text = "Consejo del Entrenador",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.extendedColors.mintSoft)
                    .padding(MaterialTheme.spacing.lg)
            ) {
                Row(verticalAlignment = Alignment.Top) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_info),
                        contentDescription = null,
                        tint = MaterialTheme.colorScheme.primary,
                        modifier = Modifier.size(20.dp)
                    )
                    Spacer(modifier = Modifier.width(MaterialTheme.spacing.sm))
                    Text(
                        text = "Asegúrate de inhalar al bajar y exhalar al subir. Mantén la mirada al frente y el abdomen contraído.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                }
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun ExerciseDetailsScreenPreview() {
    AppTheme {
        ExerciseDetailsScreen(workoutId = "w01", exerciseId = "e01", onBackClick = {})
    }
}
