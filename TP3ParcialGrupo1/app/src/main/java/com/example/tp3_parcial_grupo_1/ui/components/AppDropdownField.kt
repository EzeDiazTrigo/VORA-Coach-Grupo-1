package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.iconSizes
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

// Campo de selección: se ve como un AppTextField y abre un menú con las opciones.
@Composable
fun AppDropdownField(
    label: String,
    selected: String,
    options: List<String>,
    onSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    uppercaseLabel: Boolean = false
) {
    var expanded by remember { mutableStateOf(false) }
    Box(modifier = modifier) {
        AppTextField(
            value = selected,
            onValueChange = {},
            label = label,
            uppercaseLabel = uppercaseLabel,
            trailingIcon = { AppIcon(AppIcons.ChevronDown, contentDescription = null, size = MaterialTheme.iconSizes.sm) }
        )
        // Captura el toque para abrir el menú en lugar de editar el texto.
        Box(Modifier.matchParentSize().clickable { expanded = true })
        DropdownMenu(expanded = expanded, onDismissRequest = { expanded = false }) {
            options.forEach { option ->
                DropdownMenuItem(
                    text = { Text(option) },
                    onClick = {
                        onSelected(option)
                        expanded = false
                    }
                )
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun AppDropdownFieldPreview() {
    AppTheme {
        Box(Modifier.padding(MaterialTheme.spacing.screen)) {
            AppDropdownField(
                label = "Gender",
                selected = "Prefer not to say",
                options = listOf("Female", "Male", "Prefer not to say"),
                onSelected = {}
            )
        }
    }
}
