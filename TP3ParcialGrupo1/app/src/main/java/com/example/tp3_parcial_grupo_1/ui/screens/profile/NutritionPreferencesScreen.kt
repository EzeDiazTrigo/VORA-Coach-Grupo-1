package com.example.tp3_parcial_grupo_1.ui.screens.profile

import com.example.tp3_parcial_grupo_1.ui.components.AppSectionLabel
import com.example.tp3_parcial_grupo_1.ui.components.AppChipStyle
import androidx.compose.ui.res.painterResource
import com.example.tp3_parcial_grupo_1.R
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.InputChip
import androidx.compose.material3.InputChipDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.example.tp3_parcial_grupo_1.data.demo.ProfileDemoData
import com.example.tp3_parcial_grupo_1.model.DietaryPreference
import com.example.tp3_parcial_grupo_1.model.NutritionPriority
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppChip
import com.example.tp3_parcial_grupo_1.ui.components.AppDropdownField
import com.example.tp3_parcial_grupo_1.ui.components.AppOptionCard
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.components.AppTextField
import com.example.tp3_parcial_grupo_1.ui.strings.ProfileStrings
import com.example.tp3_parcial_grupo_1.ui.strings.title
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val RemoveIconSize = 18.dp

@Composable
fun NutritionPreferencesRoute(
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: NutritionPreferencesViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()

    LaunchedEffect(uiState.isSaved) {
        if (uiState.isSaved) onBackClick()
    }

    NutritionPreferencesScreen(
        uiState = uiState,
        onBackClick = onBackClick,
        onDietSelected = viewModel::onDietSelected,
        onFoodEnjoyedToggled = viewModel::onFoodEnjoyedToggled,
        onIngredientInputChange = viewModel::onIngredientInputChange,
        onAddIngredientClick = viewModel::onAddIngredientClick,
        onRemoveIngredientClick = viewModel::onRemoveIngredientClick,
        onMealsPerDaySelected = viewModel::onMealsPerDaySelected,
        onBreakfastSelected = viewModel::onBreakfastSelected,
        onDinnerSelected = viewModel::onDinnerSelected,
        onPrioritySelected = viewModel::onPrioritySelected,
        onSaveClick = viewModel::onSaveClick,
        modifier = modifier
    )
}

@OptIn(ExperimentalLayoutApi::class, ExperimentalMaterial3Api::class)
@Composable
fun NutritionPreferencesScreen(
    uiState: NutritionPreferencesUiState,
    onBackClick: () -> Unit,
    onDietSelected: (DietaryPreference) -> Unit,
    onFoodEnjoyedToggled: (String) -> Unit,
    onIngredientInputChange: (String) -> Unit,
    onAddIngredientClick: () -> Unit,
    onRemoveIngredientClick: (String) -> Unit,
    onMealsPerDaySelected: (String) -> Unit,
    onBreakfastSelected: (String) -> Unit,
    onDinnerSelected: (String) -> Unit,
    onPrioritySelected: (NutritionPriority) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    AppScaffold(
        modifier = modifier.fillMaxSize(),
        title = ProfileStrings.NutritionHeading,
        onBack = onBackClick
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(horizontal = MaterialTheme.spacing.screen, vertical = MaterialTheme.spacing.lg),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xl)
        ) {
            Text(
                ProfileStrings.NutritionIntro,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant
            )
            PreferenceSection(ProfileStrings.DietaryPreferenceLabel) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)
                ) {
                    DietaryPreference.entries.forEach { diet ->
                        AppChip(diet.title(), selected = diet == uiState.diet, onClick = { onDietSelected(diet) })
                    }
                }
            }
            PreferenceSection(ProfileStrings.FoodsEnjoyLabel) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)
                ) {
                    ProfileStrings.FoodsEnjoyOptions.forEach { food ->
                        AppChip(
                            food,
                            selected = food in uiState.foodsEnjoyed,
                            onClick = { onFoodEnjoyedToggled(food) },
                            style = AppChipStyle.Outlined
                        )
                    }
                }
            }
            PreferenceSection(ProfileStrings.FoodsAvoidLabel) {
                AppTextField(
                    value = uiState.ingredientInput,
                    onValueChange = onIngredientInputChange,
                    label = "",
                    placeholder = ProfileStrings.AddIngredientPlaceholder,
                    errorMessage = uiState.ingredientError,
                    trailingIcon = {
                        IconButton(onClick = onAddIngredientClick) {
                            Icon(Icons.Outlined.Add, contentDescription = ProfileStrings.AddIngredient)
                        }
                    }
                )
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)
                ) {
                    uiState.foodsToAvoid.forEach { ingredient ->
                        InputChip(
                            selected = false,
                            onClick = { onRemoveIngredientClick(ingredient) },
                            label = { Text(ingredient, style = MaterialTheme.typography.labelLarge) },
                            trailingIcon = {
                                Icon(
                                    painterResource(R.drawable.ic_x_circle),
                                    contentDescription = ProfileStrings.RemoveIngredient,
                                    modifier = Modifier.size(RemoveIconSize),
                                    tint = MaterialTheme.colorScheme.error
                                )
                            },
                            shape = PillShape,
                            colors = InputChipDefaults.inputChipColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                labelColor = MaterialTheme.colorScheme.onSurface
                            ),
                            border = InputChipDefaults.inputChipBorder(
                                enabled = true,
                                selected = false,
                                borderColor = MaterialTheme.colorScheme.outlineVariant
                            )
                        )
                    }
                }
            }
            PreferenceSection(ProfileStrings.MealPreferencesLabel) {
                AppDropdownField(
                    label = ProfileStrings.MealsPerDayLabel,
                    selected = uiState.mealsPerDay,
                    options = ProfileStrings.MealsPerDayOptions,
                    onSelected = onMealsPerDaySelected
                )
                AppDropdownField(
                    label = ProfileStrings.PreferredBreakfastLabel,
                    selected = uiState.preferredBreakfast,
                    options = ProfileStrings.BreakfastOptions,
                    onSelected = onBreakfastSelected
                )
                AppDropdownField(
                    label = ProfileStrings.PreferredDinnerLabel,
                    selected = uiState.preferredDinner,
                    options = ProfileStrings.DinnerOptions,
                    onSelected = onDinnerSelected
                )
            }
            PreferenceSection(ProfileStrings.NutritionPriorityPrompt) {
                NutritionPriority.entries.forEach { priority ->
                    AppOptionCard(
                        title = priority.title(),
                        selected = priority == uiState.priority,
                        onClick = { onPrioritySelected(priority) }
                    )
                }
            }
            AppButton(text = ProfileStrings.SavePreferences, onClick = onSaveClick)
        }
    }
}

@Composable
private fun PreferenceSection(label: String, content: @Composable () -> Unit) {
    Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)) {
        AppSectionLabel(label)
        content()
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 1800)
@Composable
private fun NutritionPreferencesScreenPreview() {
    AppTheme {
        NutritionPreferencesScreen(
            uiState = ProfileDemoData.nutritionPreferences.toUiState(),
            onBackClick = {}, onDietSelected = {}, onFoodEnjoyedToggled = {}, onIngredientInputChange = {},
            onAddIngredientClick = {}, onRemoveIngredientClick = {}, onMealsPerDaySelected = {},
            onBreakfastSelected = {}, onDinnerSelected = {}, onPrioritySelected = {}, onSaveClick = {}
        )
    }
}
