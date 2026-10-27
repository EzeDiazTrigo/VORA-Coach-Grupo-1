package com.example.tp3_parcial_grupo_1.ui.screens.nutrition

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import com.example.tp3_parcial_grupo_1.model.Meal
import com.example.tp3_parcial_grupo_1.ui.strings.NutritionStrings

// Confirmación para eliminar una comida: se abre con un toque largo sobre la comida en el Dashboard y en Today's Meals.
@Composable
fun DeleteMealDialog(meal: Meal, onConfirm: () -> Unit, onDismiss: () -> Unit) {
    AlertDialog(
        onDismissRequest = onDismiss,
        title = { Text(NutritionStrings.deleteMealTitle) },
        text = { Text(NutritionStrings.deleteMealConfirm(meal.name)) },
        confirmButton = {
            TextButton(onClick = onConfirm) {
                Text(NutritionStrings.delete, color = MaterialTheme.colorScheme.error)
            }
        },
        dismissButton = {
            TextButton(onClick = onDismiss) { Text(NutritionStrings.cancel) }
        }
    )
}
