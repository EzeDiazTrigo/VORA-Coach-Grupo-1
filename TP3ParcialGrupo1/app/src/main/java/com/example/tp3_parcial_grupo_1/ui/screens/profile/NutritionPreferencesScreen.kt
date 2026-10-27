package com.example.tp3_parcial_grupo_1.ui.screens.profile

import androidx.compose.foundation.layout.Arrangement
import com.example.tp3_parcial_grupo_1.ui.components.AppOptionDialog
import com.example.tp3_parcial_grupo_1.ui.components.AppCard
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.Alignment
import androidx.compose.runtime.setValue
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.example.tp3_parcial_grupo_1.ui.components.AppChipStyle
import com.example.tp3_parcial_grupo_1.ui.components.AppIcon
import com.example.tp3_parcial_grupo_1.ui.components.AppIcons
import com.example.tp3_parcial_grupo_1.ui.components.AppOptionCard
import com.example.tp3_parcial_grupo_1.ui.components.AppScaffold
import com.example.tp3_parcial_grupo_1.ui.components.AppSection
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
        onPriorityToggled = viewModel::onPriorityToggled,
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
    onPriorityToggled: (NutritionPriority) -> Unit,
    onSaveClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    var editing by rememberSaveable { mutableStateOf<MealPreferenceField?>(null) }
    editing?.let { field ->
        val (label, options, selected, onSelected) = when (field) {
            MealPreferenceField.MealsPerDay ->
                MealDialog(ProfileStrings.MealsPerDayLabel, ProfileStrings.MealsPerDayOptions, uiState.mealsPerDay, onMealsPerDaySelected)
            MealPreferenceField.Breakfast ->
                MealDialog(ProfileStrings.PreferredBreakfastLabel, ProfileStrings.BreakfastOptions, uiState.preferredBreakfast, onBreakfastSelected)
            MealPreferenceField.Dinner ->
                MealDialog(ProfileStrings.PreferredDinnerLabel, ProfileStrings.DinnerOptions, uiState.preferredDinner, onDinnerSelected)
        }
        AppOptionDialog(
            title = label,
            options = options,
            selected = selected,
            cancelLabel = ProfileStrings.Cancel,
            onSelected = {
                onSelected(it)
                editing = null
            },
            onDismiss = { editing = null }
        )
    }
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
            AppSection(ProfileStrings.DietaryPreferenceLabel) {
                FlowRow(
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm),
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)
                ) {
                    DietaryPreference.entries.forEach { diet ->
                        AppChip(diet.title(), selected = diet == uiState.diet, onClick = { onDietSelected(diet) })
                    }
                }
            }
            AppSection(ProfileStrings.FoodsEnjoyLabel) {
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
            AppSection(ProfileStrings.FoodsAvoidLabel) {
                AppTextField(
                    value = uiState.ingredientInput,
                    onValueChange = onIngredientInputChange,
                    label = "",
                    placeholder = ProfileStrings.AddIngredientPlaceholder,
                    errorMessage = uiState.ingredientError,
                    trailingIcon = {
                        IconButton(onClick = onAddIngredientClick) {
                            AppIcon(AppIcons.Plus, contentDescription = ProfileStrings.AddIngredient)
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
                                AppIcon(
                                    AppIcons.XCircle,
                                    contentDescription = ProfileStrings.RemoveIngredient,
                                    size = RemoveIconSize,
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
            AppSection(ProfileStrings.MealPreferencesLabel) {
                AppCard {
                    Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xxs)) {
                        MealPreferenceRow(
                            label = ProfileStrings.MealsPerDayLabel,
                            value = uiState.mealsPerDay,
                            onClick = { editing = MealPreferenceField.MealsPerDay }
                        )
                        MealPreferenceRow(
                            label = ProfileStrings.PreferredBreakfastLabel,
                            value = uiState.preferredBreakfast,
                            onClick = { editing = MealPreferenceField.Breakfast }
                        )
                        MealPreferenceRow(
                            label = ProfileStrings.PreferredDinnerLabel,
                            value = uiState.preferredDinner,
                            onClick = { editing = MealPreferenceField.Dinner }
                        )
                    }
                }
            }
            AppSection(ProfileStrings.NutritionPriorityPrompt) {
                NutritionPriority.entries.forEach { priority ->
                    AppOptionCard(
                        title = priority.title(),
                        selected = priority in uiState.priorities,
                        onClick = { onPriorityToggled(priority) },
                        multiSelect = true
                    )
                }
            }
            AppButton(text = ProfileStrings.SavePreferences, onClick = onSaveClick)
        }
    }
}

private enum class MealPreferenceField { MealsPerDay, Breakfast, Dinner }

private data class MealDialog(
    val label: String,
    val options: List<String>,
    val selected: String,
    val onSelected: (String) -> Unit
)

// Fila de solo lectura de la tarjeta de Meal Preferences: etiqueta gris a la izquierda y valor en SemiBold a la derecha.
@Composable
private fun MealPreferenceRow(label: String, value: String, onClick: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(role = Role.Button, onClickLabel = label, onClick = onClick)
            .padding(vertical = MaterialTheme.spacing.xxs),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(label, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.onSurfaceVariant)
        Text(value, style = MaterialTheme.typography.labelMedium, color = MaterialTheme.colorScheme.onSurface)
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
            onBreakfastSelected = {}, onDinnerSelected = {}, onPriorityToggled = {}, onSaveClick = {}
        )
    }
}
