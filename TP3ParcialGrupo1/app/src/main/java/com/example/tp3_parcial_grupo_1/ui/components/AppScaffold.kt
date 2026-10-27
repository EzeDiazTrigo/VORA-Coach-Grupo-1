package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

private val BackButtonInset = 12.dp
private val BackButtonSize = 40.dp
private val BackButtonTouchInset = (MinTouchTarget - BackButtonSize) / 2

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppScaffold(
    modifier: Modifier = Modifier,
    title: String? = null,
    onBack: (() -> Unit)? = null,
    actions: @Composable RowScope.() -> Unit = {},
    snackbarHostState: SnackbarHostState = remember { SnackbarHostState() },
    content: @Composable (PaddingValues) -> Unit
) {
    Scaffold(
        modifier = modifier,
        containerColor = MaterialTheme.colorScheme.background,
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            if (title != null || onBack != null) {
                TopAppBar(
                    title = {
                        if (title != null) Text(title, style = MaterialTheme.typography.titleLarge)
                    },
                    navigationIcon = {
                        if (onBack != null) {
                            // Círculo blanco de 40 dp dentro de un área de 48 dp, alineado al margen de la pantalla.
                            AppBackButton(
                                onClick = onBack,
                                modifier = Modifier
                                    .padding(start = MaterialTheme.spacing.screen - BackButtonInset)
                                    .padding(BackButtonTouchInset),
                                size = BackButtonSize,
                                colors = AppIconButtonDefaults.surface()
                            )
                        }
                    },
                    actions = actions,
                    colors = TopAppBarDefaults.topAppBarColors(containerColor = MaterialTheme.colorScheme.background)
                )
            }
        },
        content = content
    )
}

@Preview(showBackground = true, widthDp = 390, heightDp = 400)
@Composable
private fun AppScaffoldPreview() {
    AppTheme {
        AppScaffold(title = "Edit profile", onBack = {}) { padding ->
            Box(Modifier.padding(padding).padding(MaterialTheme.spacing.screen)) {
                AppTextField(value = "Alex", onValueChange = {}, label = "FIRST NAME")
            }
        }
    }
}
