package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme

private val VoraLime = Color(0xFFC9E86A)
private val VoraMint = Color(0xFF65D6B0)
private val VoraGreen = Color(0xFF123D35)

@Composable
fun VoraSymbol(
    modifier: Modifier = Modifier,
    size: Dp = 18.dp
) {
    Box(
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(
                Brush.linearGradient(
                    colors = listOf(VoraLime, VoraMint, VoraGreen)
                )
            )
    )
}

@Preview(showBackground = true)
@Composable
private fun VoraSymbolPreview() {
    AppTheme {
        Box(modifier = Modifier.background(VoraGreen)) {
            VoraSymbol(size = 24.dp)
        }
    }
}
