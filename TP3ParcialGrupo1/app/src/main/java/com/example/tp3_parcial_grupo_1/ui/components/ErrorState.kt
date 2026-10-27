package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tp3_parcial_grupo_1.ui.strings.ComponentStrings
import com.example.tp3_parcial_grupo_1.ui.strings.Strings
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme

// Error con reintento (RF05). El mensaje llega en español desde el ViewModel.
@Composable
fun ErrorState(
    message: String,
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
    title: String = ComponentStrings.ErrorTitle,
    retryLabel: String = Strings.Retry
) {
    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        StateMessage(
            icon = AppIcons.AlertCircle,
            iconTint = MaterialTheme.colorScheme.error,
            iconBackground = MaterialTheme.colorScheme.error.copy(alpha = 0.1f),
            title = title,
            message = message,
            action = { AppButton(text = retryLabel, onClick = onRetry) }
        )
    }
}

@Preview(showBackground = true, widthDp = 390, heightDp = 420)
@Composable
private fun ErrorStatePreview() {
    AppTheme {
        ErrorState(message = "The server isn't responding. Check your connection.", onRetry = {})
    }
}
