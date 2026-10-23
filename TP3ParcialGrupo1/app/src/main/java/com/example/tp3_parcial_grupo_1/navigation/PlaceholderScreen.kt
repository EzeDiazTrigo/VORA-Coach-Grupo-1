package com.example.tp3_parcial_grupo_1.navigation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tp3_parcial_grupo_1.ui.strings.NavigationStrings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

data class PlaceholderAction(val label: String, val onClick: () -> Unit)

@Composable
fun PlaceholderScreen(
    title: String,
    actions: List<PlaceholderAction>,
    modifier: Modifier = Modifier,
    detail: String? = null
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(MaterialTheme.spacing.screen),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
    ) {
        Text(title, style = MaterialTheme.typography.headlineSmall)
        if (detail != null) {
            Text(detail, style = MaterialTheme.typography.bodyMedium, color = MaterialTheme.colorScheme.primary)
        }
        Text(
            NavigationStrings.PlaceholderNotice,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.extendedColors.textSubtle
        )
        actions.forEach { action ->
            OutlinedButton(onClick = action.onClick, modifier = Modifier.fillMaxWidth()) {
                Text(action.label)
            }
        }
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 844)
@Composable
private fun PlaceholderScreenPreview() {
    AppTheme {
        PlaceholderScreen(
            title = "Workout / 03 — Workout Details",
            detail = "workoutId = w01",
            actions = listOf(PlaceholderAction("Start workout") {})
        )
    }
}
