package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors

// Rótulo de sección del diseño: mayúsculas, chico y en gris tenue.
@Composable
fun AppSectionLabel(text: String, modifier: Modifier = Modifier) {
    Text(
        text = text.uppercase(),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.extendedColors.textSubtle,
        modifier = modifier
    )
}

@Preview(showBackground = true)
@Composable
private fun AppSectionLabelPreview() {
    AppTheme {
        AppSectionLabel(text = "Primary goal — what's your main goal?")
    }
}
