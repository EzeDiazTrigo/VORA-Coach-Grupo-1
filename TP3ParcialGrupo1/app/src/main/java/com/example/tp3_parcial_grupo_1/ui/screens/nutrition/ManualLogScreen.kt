package com.example.tp3_parcial_grupo_1.ui.screens.nutrition

import com.example.tp3_parcial_grupo_1.ui.strings.NutritionStrings
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.model.Meal
import com.example.tp3_parcial_grupo_1.model.MealType
import com.example.tp3_parcial_grupo_1.ui.components.AppBackButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButton
import com.example.tp3_parcial_grupo_1.ui.components.AppButtonDefaults
import com.example.tp3_parcial_grupo_1.ui.components.AppTopBar
import com.example.tp3_parcial_grupo_1.ui.theme.spacing
import com.example.tp3_parcial_grupo_1.utils.validateMeal
import java.text.SimpleDateFormat
import java.util.*

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ManualLogScreen(
    onBackClick: () -> Unit,
    onSaveMeal: (Meal) -> Unit
) {
    var name by rememberSaveable { mutableStateOf("") }
    var date by rememberSaveable { mutableStateOf(SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date())) }
    var calories by rememberSaveable { mutableStateOf("") }
    var protein by rememberSaveable { mutableStateOf("") }
    var carbs by rememberSaveable { mutableStateOf("") }
    var fat by rememberSaveable { mutableStateOf("") }
    var selectedType by rememberSaveable { mutableStateOf(MealType.LUNCH) }
    var errorMessage by rememberSaveable { mutableStateOf<String?>(null) }

    Scaffold(
        containerColor = MaterialTheme.colorScheme.background
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
                .padding(MaterialTheme.spacing.screen),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.lg)
        ) {
            AppTopBar(
                title = NutritionStrings.logManually,
                navigation = { AppBackButton(onClick = onBackClick) },
                titleStyle = MaterialTheme.typography.headlineMedium.copy(fontWeight = FontWeight.Bold),
                titleColor = MaterialTheme.colorScheme.onBackground,
                spacing = MaterialTheme.spacing.sm
            )

            OutlinedTextField(
                value = name,
                onValueChange = { name = it },
                label = { Text(NutritionStrings.mealNameLabel) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            OutlinedTextField(
                value = date,
                onValueChange = { date = it },
                label = { Text(NutritionStrings.dateLabel) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            // Meal Type Selector
            Column(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = NutritionStrings.mealTypeLabel,
                    style = MaterialTheme.typography.labelMedium.copy(fontWeight = FontWeight.SemiBold)
                )
                Spacer(modifier = Modifier.height(MaterialTheme.spacing.xxs))
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)
                ) {
                    MealType.entries.forEach { type ->
                        val isSelected = selectedType == type
                        FilterChip(
                            selected = isSelected,
                            onClick = { selectedType = type },
                            label = { Text(type.displayName(), style = MaterialTheme.typography.bodySmall) }
                        )
                    }
                }
            }

            OutlinedTextField(
                value = calories,
                onValueChange = { calories = it },
                label = { Text(NutritionStrings.caloriesLabel) },
                modifier = Modifier.fillMaxWidth(),
                singleLine = true,
                shape = RoundedCornerShape(12.dp)
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.sm)
            ) {
                OutlinedTextField(
                    value = protein,
                    onValueChange = { protein = it },
                    label = { Text(NutritionStrings.proteinGramsLabel) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = carbs,
                    onValueChange = { carbs = it },
                    label = { Text(NutritionStrings.carbsGramsLabel) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )
                OutlinedTextField(
                    value = fat,
                    onValueChange = { fat = it },
                    label = { Text(NutritionStrings.fatGramsLabel) },
                    modifier = Modifier.weight(1f),
                    singleLine = true,
                    shape = RoundedCornerShape(12.dp)
                )
            }

            if (errorMessage != null) {
                Text(
                    text = errorMessage!!,
                    color = MaterialTheme.colorScheme.error,
                    style = MaterialTheme.typography.bodySmall
                )
            }

            Spacer(modifier = Modifier.height(MaterialTheme.spacing.lg))

            AppButton(
                text = NutritionStrings.saveMeal,
                onClick = {
                    val validation = validateMeal(name, calories, protein, carbs, fat)
                    if (validation.isValid) {
                        val meal = Meal(
                            name = name,
                            date = date.ifBlank { SimpleDateFormat("yyyy-MM-dd", Locale.getDefault()).format(Date()) },
                            type = selectedType,
                            calories = calories.toInt(),
                            protein = protein.toInt(),
                            carbs = carbs.toInt(),
                            fat = fat.toInt(),
                            itemsSummary = name
                        )
                        onSaveMeal(meal)
                    } else {
                        errorMessage = validation.errorMessage
                    }
                },
                minHeight = AppButtonDefaults.MediumHeight,
                textStyle = AppButtonDefaults.BoldTextStyle
            )
        }
    }
}
