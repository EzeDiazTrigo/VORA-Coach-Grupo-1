package com.example.tp3_parcial_grupo_1.ui.screens.workout

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
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
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.R
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.components.VoraSymbol
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
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
            // Centered Top Header Bar
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = MaterialTheme.spacing.xs),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = onBackClick,
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_chevron_left),
                        contentDescription = "Back",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(20.dp)
                    )
                }

                Text(
                    text = "Exercise Guide",
                    style = MaterialTheme.typography.titleMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onSurface,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.weight(1f)
                )

                IconButton(
                    onClick = { },
                    colors = IconButtonDefaults.iconButtonColors(
                        containerColor = MaterialTheme.colorScheme.surface
                    ),
                    modifier = Modifier.size(40.dp)
                ) {
                    Icon(
                        painter = painterResource(id = R.drawable.ic_help_circle),
                        contentDescription = "Help",
                        tint = MaterialTheme.colorScheme.onSurface,
                        modifier = Modifier.size(20.dp)
                    )
                }
            }

            // Exercise Title Header
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = "Seated Cable Row",
                    style = MaterialTheme.typography.headlineMedium,
                    fontWeight = FontWeight.Bold,
                    color = MaterialTheme.colorScheme.onBackground
                )
                Spacer(modifier = Modifier.height(2.dp))
                Text(
                    text = "Target: Lats, Mid-Back, Rhomboids",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }

            // Real Exercise Image
            Image(
                painter = painterResource(id = R.drawable.img_exercise_demo),
                contentDescription = "Seated Cable Row Exercise",
                contentScale = ContentScale.Crop,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(200.dp)
                    .clip(MaterialTheme.shapes.large)
            )

            // Performance Steps Header
            Text(
                text = "Performance Steps",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = MaterialTheme.colorScheme.onSurface
            )

            // Performance Steps List
            Column(
                verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xs),
                modifier = Modifier.fillMaxWidth()
            ) {
                val steps = listOf(
                    "1. Sit upright with your chest lifted.",
                    "2. Pull the handle toward your lower torso.",
                    "3. Keep your elbows tucked close to your ribs.",
                    "4. Pause briefly during peak contraction.",
                    "5. Return the weight slowly to start."
                )
                steps.forEach { step ->
                    Text(
                        text = step,
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))

            // PRO FORM CUES Box
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.colorScheme.surface)
                    .border(1.dp, MaterialTheme.colorScheme.outlineVariant, MaterialTheme.shapes.medium)
                    .padding(MaterialTheme.spacing.lg)
            ) {
                Column {
                    Text(
                        text = "PRO FORM CUES",
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))
                    Text(
                        text = "• Keep your shoulders rolled down and back.\n• Do not lean backward to swing the load.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Ask Vora Card
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(MaterialTheme.shapes.medium)
                    .background(MaterialTheme.extendedColors.mintSoft)
                    .padding(MaterialTheme.spacing.md)
            ) {
                Row(verticalAlignment = Alignment.CenterVertically) {
                    VoraSymbol(size = 18.dp)
                    Spacer(modifier = Modifier.width(MaterialTheme.spacing.sm))
                    Text(
                        text = "Ask Vora: \"Explain grip variations\"",
                        style = MaterialTheme.typography.bodyMedium,
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.colorScheme.primary
                    )
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.sm))

            // Bottom Back Button
            AppButton(
                text = "Back to Workout",
                onClick = onBackClick,
                style = AppButtonStyle.Primary
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))
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
