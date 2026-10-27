package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.HairlineWidth
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

// Línea de 1 dp con un texto al medio, como «Or continue with» en Login y Registro.
@Composable
fun LabeledDivider(text: String, modifier: Modifier = Modifier) {
    val lineColor = MaterialTheme.colorScheme.outlineVariant
    Row(
        modifier = modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
    ) {
        HorizontalDivider(modifier = Modifier.weight(1f), thickness = HairlineWidth, color = lineColor)
        Text(text, style = MaterialTheme.typography.bodySmall, color = MaterialTheme.colorScheme.onSurfaceVariant)
        HorizontalDivider(modifier = Modifier.weight(1f), thickness = HairlineWidth, color = lineColor)
    }
}

@Preview(showBackground = true, widthDp = 390)
@Composable
private fun LabeledDividerPreview() {
    AppTheme {
        LabeledDivider(text = "Or continue with", modifier = Modifier.padding(MaterialTheme.spacing.screen))
    }
}
