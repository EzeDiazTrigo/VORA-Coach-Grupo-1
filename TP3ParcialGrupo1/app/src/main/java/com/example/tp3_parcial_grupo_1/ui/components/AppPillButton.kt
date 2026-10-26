package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.PillShape
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

enum class AppPillStyle { Neutral, Accent }

// Botón chico en píldora del diseño («Edit profile», «Review preferences»); AppButton tiene un alto mínimo de 52 dp.
@Composable
fun AppPillButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    style: AppPillStyle = AppPillStyle.Neutral
) {
    val colors = MaterialTheme.colorScheme
    Surface(
        onClick = onClick,
        modifier = modifier,
        shape = PillShape,
        color = if (style == AppPillStyle.Accent) colors.tertiary else colors.background,
        contentColor = if (style == AppPillStyle.Accent) colors.onTertiary else colors.onSurface
    ) {
        Text(
            text,
            style = MaterialTheme.typography.labelMedium,
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.lg, vertical = MaterialTheme.spacing.sm)
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun AppPillButtonPreview() {
    AppTheme {
        Row(modifier = Modifier.padding(MaterialTheme.spacing.screen)) {
            AppPillButton(text = "Edit profile", onClick = {})
            AppPillButton(text = "Review preferences", onClick = {}, style = AppPillStyle.Accent)
        }
    }
}
