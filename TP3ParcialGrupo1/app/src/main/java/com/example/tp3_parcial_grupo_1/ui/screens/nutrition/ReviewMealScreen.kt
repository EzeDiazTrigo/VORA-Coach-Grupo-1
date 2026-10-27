package com.example.tp3_parcial_grupo_1.ui.screens.nutrition

import com.example.tp3_parcial_grupo_1.data.demo.AnalysedMeal
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.model.Meal
import com.example.tp3_parcial_grupo_1.model.MealType
import com.example.tp3_parcial_grupo_1.ui.components.AppBackButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonDefaults
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppStat
import com.example.tp3_parcial_grupo_1.ui.components.AppStatDefaults
import com.example.tp3_parcial_grupo_1.ui.components.AppStepButton
import com.example.tp3_parcial_grupo_1.ui.components.AppTopBar
import com.example.tp3_parcial_grupo_1.ui.strings.NutritionStrings
import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.chip
import com.example.tp3_parcial_grupo_1.ui.theme.spacing
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.coroutines.launch

@Composable
fun ReviewMealScreen(
    onBackClick: () -> Unit,
    onSaveMeal: (Meal) -> Unit
) {
    // El idioma se lee de la configuración para que Compose recomponga si cambia.
    val locale = LocalConfiguration.current.locales[0]
    val currentDate = remember(locale) { SimpleDateFormat("yyyy-MM-dd", locale).format(Date()) }
    val snackbarHostState = remember { SnackbarHostState() }
    val scope = rememberCoroutineScope()
    val showDemoNotice: () -> Unit = { scope.launch { snackbarHostState.showSnackbar(Strings.DemoMode) } }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(snackbarHostState) }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .verticalScroll(rememberScrollState())
                .padding(MaterialTheme.spacing.screen)
        ) {
            AppTopBar(
                title = NutritionStrings.reviewMeal,
                navigation = { AppBackButton(onClick = onBackClick) },
                titleStyle = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                titleColor = MaterialTheme.colorScheme.onBackground,
                spacing = MaterialTheme.spacing.sm
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))

            AnalysedMeal.ingredients.forEachIndexed { index, ingredient ->
                if (index > 0) Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))
                ReviewIngredientCard(
                    name = ingredient.name,
                    quantity = ingredient.quantity,
                    calories = NutritionStrings.caloriesValue(ingredient.calories),
                    onDemoClick = showDemoNotice
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.sm))
            TextButton(onClick = showDemoNotice) {
                Text(
                    text = NutritionStrings.addIngredient,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
)
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))

            // Meal Totals Card (Dark Green)
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(20.dp))
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(MaterialTheme.spacing.screen)
            ) {
                Column {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = NutritionStrings.mealTotals,
                            style = MaterialTheme.typography.labelMedium.copy(
                                color = MaterialTheme.colorScheme.secondary,
                                fontWeight = FontWeight.Bold
                            )
                        )
                        Text(
                            text = NutritionStrings.caloriesValue(AnalysedMeal.calories),
                            style = MaterialTheme.typography.headlineSmall.copy(
                                color = MaterialTheme.colorScheme.surface,
                                fontWeight = FontWeight.Bold
)
                        )
                    }
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))
                    HorizontalDivider(
                        color = MaterialTheme.colorScheme.surface.copy(alpha = 0.15f),
                        thickness = 1.dp
                    )
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        AppStat(
                            label = NutritionStrings.protein.uppercase(),
                            value = "${AnalysedMeal.protein}g",
                            labelStyle = AppStatDefaults.MacroLabelStyle,
                            labelColor = MaterialTheme.colorScheme.secondary,
                            valueStyle = AppStatDefaults.MacroValueStyle,
                            valueColor = MaterialTheme.colorScheme.surface,
                            spacing = 2.dp
                        )
                        AppStat(
                            label = NutritionStrings.carbs.uppercase(),
                            value = "${AnalysedMeal.carbs}g",
                            labelStyle = AppStatDefaults.MacroLabelStyle,
                            labelColor = MaterialTheme.colorScheme.secondary,
                            valueStyle = AppStatDefaults.MacroValueStyle,
                            valueColor = MaterialTheme.colorScheme.surface,
                            spacing = 2.dp
                        )
                        AppStat(
                            label = NutritionStrings.fat.uppercase(),
                            value = "${AnalysedMeal.fat}g",
                            labelStyle = AppStatDefaults.MacroLabelStyle,
                            labelColor = MaterialTheme.colorScheme.secondary,
                            valueStyle = AppStatDefaults.MacroValueStyle,
                            valueColor = MaterialTheme.colorScheme.surface,
                            spacing = 2.dp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.xl))

            AppButton(
                text = NutritionStrings.addToLunch,
                onClick = {
                    onSaveMeal(AnalysedMeal.asLunch(currentDate))
                },
                minHeight = AppButtonDefaults.MediumHeight,
                textStyle = AppButtonDefaults.BoldTextStyle
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))

            AppButton(
                text = NutritionStrings.saveWithoutLogging,
                onClick = onBackClick,
                style = AppButtonStyle.Outlined,
                textColor = MaterialTheme.colorScheme.onBackground,
                minHeight = AppButtonDefaults.MediumHeight,
                textStyle = AppButtonDefaults.BoldTextStyle
            )
        }
    }
}

@Composable
fun ReviewIngredientCard(name: String, quantity: String, calories: String, onDemoClick: () -> Unit) {
    Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
    ) {
        Column(modifier = Modifier.padding(MaterialTheme.spacing.lg)) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = name,
                    style = MaterialTheme.typography.bodyLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
)
                )
                Text(
                    text = calories,
                    style = MaterialTheme.typography.labelLarge.copy(
                        color = MaterialTheme.colorScheme.onBackground,
                        fontWeight = FontWeight.Bold
)
                )
            }
            Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)
                ) {
                    Row(
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
                    ) {
                        AppStepButton(symbol = "-", contentDescription = NutritionStrings.decreaseQuantity, onClick = onDemoClick)
                        Text(text = quantity, style = MaterialTheme.typography.labelLarge)
                        AppStepButton(symbol = "+", contentDescription = NutritionStrings.increaseQuantity, onClick = onDemoClick)
                    }
                }
                TextButton(onClick = onDemoClick) {
                    Text(
                        text = NutritionStrings.remove,
                        style = MaterialTheme.typography.chip.copy(
                            color = MaterialTheme.colorScheme.error,
                            fontWeight = FontWeight.SemiBold
)
                    )
                }
            }
        }
    }
}


