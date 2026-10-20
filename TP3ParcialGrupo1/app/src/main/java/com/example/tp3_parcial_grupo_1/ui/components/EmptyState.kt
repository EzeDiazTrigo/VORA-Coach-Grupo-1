package com.example.tp3_parcial_grupo_1.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tp3_parcial_grupo_1.R
import com.example.tp3_parcial_grupo_1.ui.strings.ComponentStrings
import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors

// Lista sin resultados (RF05). La acción es opcional, por ejemplo «Agregar comida».
@Composable
fun EmptyState(
    modifier: Modifier = Modifier,
    title: String = ComponentStrings.EmptyTitle,
    message: String? = Strings.EmptyContent,
    @DrawableRes icon: Int = R.drawable.ic_search,
    actionLabel: String? = null,
    onAction: (() -> Unit)? = null
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        StateMessage(
            icon = icon,
            iconTint = MaterialTheme.colorScheme.primary,
            iconBackground = MaterialTheme.extendedColors.mintSoft,
            title = title,
            message = message,
            action = if (actionLabel != null && onAction != null) {
                { AppButton(text = actionLabel, onClick = onAction, style = AppButtonStyle.Outlined) }
            } else {
                null
            }
        )
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 420)
@Composable
private fun EmptyStatePreview() {
    AppTheme {
        EmptyState(
            title = "Sin comidas hoy",
            message = "Registrá tu primera comida para ver el total del día.",
            icon = R.drawable.ic_apple,
            actionLabel = "Agregar comida",
            onAction = {}
        )
    }
}
