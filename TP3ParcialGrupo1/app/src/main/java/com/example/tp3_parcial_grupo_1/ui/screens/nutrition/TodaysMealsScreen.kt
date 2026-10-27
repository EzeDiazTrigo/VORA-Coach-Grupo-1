package com.example.tp3_parcial_grupo_1.ui.screens.nutrition

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.model.MealType
import com.example.tp3_parcial_grupo_1.ui.components.AppBackButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import com.example.tp3_parcial_grupo_1.ui.components.AppTopBar
import com.example.tp3_parcial_grupo_1.ui.strings.NutritionStrings
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

@Composable
fun TodaysMealsScreen(
    uiState: MealUiState,
    onBackClick: () -> Unit,
    onAddDinner: () -> Unit
) {
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { paddingValues ->
        LazyColumn(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(horizontal = MaterialTheme.spacing.screen),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.lg)
        ) {
            item {
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.sm))
                AppTopBar(
                    title = NutritionStrings.todaysMeals,
                    navigation = { AppBackButton(onClick = onBackClick) },
                    eyebrow = "TIMELINE",
                    titleStyle = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                    titleColor = MaterialTheme.colorScheme.onBackground,
                    spacing = MaterialTheme.spacing.xs
                ) {
                    Surface(
                        shape = PillShape,
                        color = MaterialTheme.colorScheme.surface,
                        border = BorderStroke(1.dp, MaterialTheme.colorScheme.outlineVariant)
                    ) {
                        Text(
                            text = "${uiState.totalCalories} kcal",
                            modifier = Modifier.padding(horizontal = 12.dp, vertical = 6.dp),
                            style = MaterialTheme.typography.bodySmall.copy(fontWeight = FontWeight.Bold)
                        )
                    }
                }
            }

            items(uiState.meals, key = { it.id }) { meal ->
                DetailedMealCard(
                    mealTitle = meal.name,
                    totalCalories = "${meal.calories} kcal",
                    items = listOf(Triple(meal.itemsSummary.ifBlank { meal.type.displayName() }, "", "${meal.calories} kcal"))
                )
            }

            if (uiState.meals.none { it.type == MealType.DINNER }) {
                item {
                    AppCard {
                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(MaterialTheme.spacing.md),
                            horizontalAlignment = Alignment.CenterHorizontally,
                            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
                        ) {
                            Text(
                                text = "Dinner is not logged yet",
                                style = MaterialTheme.typography.bodyMedium.copy(
                                    color = MaterialTheme.extendedColors.textSubtle
                                )
                            )
                            Box(modifier = Modifier.width(160.dp)) {
                                AppButton(
                                    text = "+ Add Dinner",
                                    onClick = onAddDinner,
                                    style = AppButtonStyle.Primary,
                                    isCompact = true
                                )
                            }
                        }
                    }
                }
            }
            item { Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg)) }
        }
    }
}

@Composable
fun DetailedMealCard(mealTitle: String, totalCalories: String, items: List<Triple<String, String, String>>) {
    AppCard {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = mealTitle,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
            )
            Text(
                text = totalCalories,
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                    fontWeight = FontWeight.Bold
                )
            )
        }
        HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant, thickness = 1.dp)

        items.forEachIndexed { index, item ->
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(2.dp)) {
                    Text(
                        text = item.first,
                        style = MaterialTheme.typography.bodyMedium.copy(
                            color = MaterialTheme.colorScheme.onBackground,
                            fontWeight = FontWeight.Medium
                        )
                    )
                    if (item.second.isNotBlank()) {
                        Text(
                            text = item.second,
                            style = MaterialTheme.typography.bodySmall.copy(
                                color = MaterialTheme.extendedColors.textSubtle
                            )
                        )
                    }
                }
                Text(
                    text = item.third,
                    style = MaterialTheme.typography.bodyMedium.copy(
                        color = MaterialTheme.extendedColors.textSubtle
                    )
                )
            }
            if (index < items.size - 1) {
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.xs))
            }
        }
    }
}
