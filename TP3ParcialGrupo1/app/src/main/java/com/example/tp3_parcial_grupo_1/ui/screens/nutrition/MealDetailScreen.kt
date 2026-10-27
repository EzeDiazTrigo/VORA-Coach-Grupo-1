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
import androidx.compose.ui.unit.sp
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
import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.iconSizes
import com.example.tp3_parcial_grupo_1.ui.theme.spacing
import kotlinx.coroutines.launch

@Composable
fun MealDetailScreen(
    viewModel: MealsViewModel,
    onBackClick: () -> Unit,
    onDeleteSuccess: () -> Unit
) {
    val uiState by viewModel.uiState.collectAsState()
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
                .padding(20.dp)
        ) {
            AppTopBar(
                title = meal.name,
                navigation = { AppBackButton(onClick = onBackClick) },
                titleStyle = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                titleColor = MaterialTheme.colorScheme.onBackground,
                spacing = MaterialTheme.spacing.xxs
            ) {
                Text(
                    text = "13:12",
                    style = MaterialTheme.typography.bodySmall.copy(
                        color = MaterialTheme.extendedColors.textSubtle,
                        fontSize = 13.sp
                    )
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Total Calories & Ring Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                shape = RoundedCornerShape(16.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
            ) {
                Column(modifier = Modifier.padding(16.dp)) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Column {
                            Text(
                                text = "TOTAL CALORIES",
                                style = MaterialTheme.typography.labelSmall.copy(
                                    color = MaterialTheme.extendedColors.textSubtle,
                                    fontSize = 10.sp,
                                    fontWeight = FontWeight.Bold
                                )
                            )
                            Spacer(modifier = Modifier.height(4.dp))
                            Text(
                                text = "${meal.calories} kcal",
                                style = MaterialTheme.typography.headlineLarge.copy(
                                    color = MaterialTheme.colorScheme.onBackground,
                                    fontWeight = FontWeight.Bold,
                                    fontSize = 28.sp
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

                    Spacer(modifier = Modifier.height(16.dp))
                    HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)
                    Spacer(modifier = Modifier.height(16.dp))

                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        AppStat(
                            label = "PROTEIN",
                            value = "${meal.protein}g",
                            labelStyle = AppStatDefaults.MacroLabelStyle,
                            labelColor = MaterialTheme.extendedColors.textSubtle,
                            valueStyle = AppStatDefaults.MacroValueStyle,
                            valueColor = MaterialTheme.colorScheme.onBackground,
                            spacing = 2.dp
                        )
                        AppStat(
                            label = "CARBS",
                            value = "${meal.carbs}g",
                            labelStyle = AppStatDefaults.MacroLabelStyle,
                            labelColor = MaterialTheme.extendedColors.textSubtle,
                            valueStyle = AppStatDefaults.MacroValueStyle,
                            valueColor = MaterialTheme.colorScheme.onBackground,
                            spacing = 2.dp
                        )
                        AppStat(
                            label = "FAT",
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

            Spacer(modifier = Modifier.height(16.dp))

            // Advice Banner
            AppNoticeCard(
                text = "Great protein choice. You're now 36g away from your daily protein target.",
                leading = {
                    AppIcon(AppIcons.Sparkles, contentDescription = null, size = MaterialTheme.iconSizes.xl, tint = MaterialTheme.colorScheme.primary)
                },
                contentPadding = 14.dp,
                spacing = 10.dp
            )

            Spacer(modifier = Modifier.height(24.dp))

            Text(
                text = "Ingredients",
                style = MaterialTheme.typography.titleLarge.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold,
                    fontSize = 18.sp
                )
            )

            Spacer(modifier = Modifier.height(12.dp))

            DetailedMealCard(
                mealTitle = "",
                totalCalories = "",
                items = listOf(Triple(meal.itemsSummary.ifBlank { meal.name }, "", "${meal.calories} kcal"))
            )

            Spacer(modifier = Modifier.height(24.dp))

            AppButton(
                text = "Edit Meal",
                onClick = showDemoNotice,
                style = AppButtonStyle.Outlined,
                textColor = MaterialTheme.colorScheme.onBackground,
                containerColor = MaterialTheme.colorScheme.surface,
                minHeight = AppButtonDefaults.MediumHeight,
                textStyle = AppButtonDefaults.BoldTextStyle
            )

            Spacer(modifier = Modifier.height(12.dp))

            AppButton(
                text = "Duplicate Meal",
                onClick = {
                    viewModel.duplicateMeal(meal.id) {
                        onBackClick()
                    }
                },
                style = AppButtonStyle.Outlined,
                textColor = MaterialTheme.colorScheme.onBackground,
                containerColor = MaterialTheme.colorScheme.surface,
                minHeight = AppButtonDefaults.MediumHeight,
                textStyle = AppButtonDefaults.BoldTextStyle
            )
        }
    }
}

