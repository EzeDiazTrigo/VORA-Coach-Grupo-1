package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.selection.selectable
import androidx.compose.foundation.selection.selectableGroup
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.iconSizes
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val OptionRowMinHeight = 48.dp

// Diálogo para elegir una opción de una lista corta; la elegida lleva un check y tocar una cierra el diálogo.
@Composable
fun AppOptionDialog(
    title: String,
    options: List<String>,
    selected: String,
    cancelLabel: String,
    onSelected: (String) -> Unit,
    onDismiss: () -> Unit
) {
    AlertDialog(
        onDismissRequest = onDismiss,
        containerColor = MaterialTheme.colorScheme.surface,
        title = { Text(title, style = MaterialTheme.typography.titleSmall) },
        text = {
            Column(modifier = Modifier.selectableGroup(), verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xxs)) {
                options.forEach { option ->
                    val isSelected = option == selected
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .heightIn(min = OptionRowMinHeight)
                            .selectable(selected = isSelected, role = Role.RadioButton, onClick = { onSelected(option) })
                            .padding(horizontal = MaterialTheme.spacing.xs),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text(
                            text = option,
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                        )
                        if (isSelected) {
                            AppIcon(AppIcons.Check, contentDescription = null, size = MaterialTheme.iconSizes.lg, tint = MaterialTheme.colorScheme.primary)
                        }
                    }
                }
            }
        },
        confirmButton = {},
        dismissButton = { TextButton(onClick = onDismiss) { Text(cancelLabel) } }
    )
}

@Preview(showBackground = true)
@Composable
private fun AppOptionDialogPreview() {
    AppTheme {
        AppOptionDialog(
            title = "Meals per day",
            options = listOf("3 meals", "3 meals + 1 snack", "4 meals"),
            selected = "3 meals + 1 snack",
            cancelLabel = "Cancel",
            onSelected = {},
            onDismiss = {}
        )
    }
}
