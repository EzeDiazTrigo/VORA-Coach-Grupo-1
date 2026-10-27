package com.example.tp3_parcial_grupo_1.ui.screens.nutrition

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.model.Meal
import com.example.tp3_parcial_grupo_1.ui.components.AppBackButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonDefaults
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppIcon
import com.example.tp3_parcial_grupo_1.ui.components.AppIcons
import com.example.tp3_parcial_grupo_1.ui.components.AppNoticeCard
import com.example.tp3_parcial_grupo_1.ui.components.AppStat
import com.example.tp3_parcial_grupo_1.ui.components.AppStatDefaults
import com.example.tp3_parcial_grupo_1.ui.components.AppTopBar
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tp3_parcial_grupo_1.ui.strings.NutritionStrings
import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.iconSizes
import com.example.tp3_parcial_grupo_1.ui.theme.micro
import com.example.tp3_parcial_grupo_1.ui.theme.spacing
import kotlinx.coroutines.launch

@Composable
fun MealDetailScreen(
    viewModel: MealsViewModel,
    onBackClick: () -> Unit,
    onMealDuplicated: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    val meal = uiState.currentMealDetail ?: uiState.meals.firstOrNull() ?: Meal(name = "Lunch", date = "", type = com.example.tp3_parcial_grupo_1.model.MealType.LUNCH, calories = 520, protein = 38, carbs = 54, fat = 14, itemsSummary = "Chicken - rice - vegetables")

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
                title = meal.name,
                navigation = { AppBackButton(onClick = onBackClick) },
                titleStyle = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                titleColor = MaterialTheme.colorScheme.onBackground,
                spacing = MaterialTheme.spacing.xxs
            ) {
                Text(
                    text = NutritionStrings.mealTime,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.extendedColors.textSubtle
)
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))

            // Total Calories & Ring Card
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
                        Column {
                            Text(
                                text = NutritionStrings.totalCalories,
                                style = MaterialTheme.typography.micro.copy(
                                    color = MaterialTheme.extendedColors.textSubtle,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(MaterialTheme.spacing.xxs))
                            Text(
                                text = NutritionStrings.caloriesValue(meal.calories),
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontWeight = FontWeight.Bold
)
                            )
                        }
                        Box(
                            contentAlignment = Alignment.Center,
                            modifier = Modifier.size(56.dp)
                        ) {
                            CircularProgressIndicator(
                                progress = { 0.75f },
                                modifier = Modifier.size(56.dp),
                                color = MaterialTheme.colorScheme.primary,
                                trackColor = MaterialTheme.colorScheme.outlineVariant,
                                strokeWidth = 5.dp
                            )
                        }
                    }

                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        AppStat(
                            label = NutritionStrings.protein.uppercase(),
                            value = "${meal.protein}g",
                            labelStyle = AppStatDefaults.MacroLabelStyle,
                            labelColor = MaterialTheme.extendedColors.textSubtle,
                            valueStyle = AppStatDefaults.MacroValueStyle,
                            valueColor = MaterialTheme.colorScheme.onBackground,
                            spacing = 2.dp
                        )
                        AppStat(
                            label = NutritionStrings.carbs.uppercase(),
                            value = "${meal.carbs}g",
                            labelStyle = AppStatDefaults.MacroLabelStyle,
                            labelColor = MaterialTheme.extendedColors.textSubtle,
                            valueStyle = AppStatDefaults.MacroValueStyle,
                            valueColor = MaterialTheme.colorScheme.onBackground,
                            spacing = 2.dp
                        )
                        AppStat(
                            label = NutritionStrings.fat.uppercase(),
                            value = "${meal.fat}g",
                            labelStyle = AppStatDefaults.MacroLabelStyle,
                            labelColor = MaterialTheme.extendedColors.textSubtle,
                            valueStyle = AppStatDefaults.MacroValueStyle,
                            valueColor = MaterialTheme.colorScheme.onBackground,
                            spacing = 2.dp
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))

            // Advice Banner
            AppNoticeCard(
                text = NutritionStrings.proteinAdvice,
                leading = {
                    AppIcon(AppIcons.Sparkles, contentDescription = null, size = MaterialTheme.iconSizes.xl, tint = MaterialTheme.colorScheme.primary)
                },
                contentPadding = 14.dp,
                spacing = 10.dp
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.xl))

            Text(
                text = NutritionStrings.ingredients,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
)
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))

            DetailedMealCard(
                mealTitle = "",
                totalCalories = "",
                items = listOf(Triple(meal.itemsSummary.ifBlank { meal.name }, "", "${meal.calories} kcal"))
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.xl))

            AppButton(
                text = NutritionStrings.editMeal,
                onClick = showDemoNotice,
                style = AppButtonStyle.Outlined,
                textColor = MaterialTheme.colorScheme.onBackground,
                containerColor = MaterialTheme.colorScheme.surface,
                minHeight = AppButtonDefaults.MediumHeight,
                textStyle = AppButtonDefaults.BoldTextStyle
            )

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.md))

            AppButton(
                text = NutritionStrings.duplicateMeal,
                onClick = { viewModel.duplicateMeal(meal.id, onMealDuplicated) },
                style = AppButtonStyle.Outlined,
                textColor = MaterialTheme.colorScheme.onBackground,
                containerColor = MaterialTheme.colorScheme.surface,
                minHeight = AppButtonDefaults.MediumHeight,
                textStyle = AppButtonDefaults.BoldTextStyle
            )
        }
    }
}

