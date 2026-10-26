package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme

/**
 * Marca de VORA: el `vora-orb` de Figma, quieto. Es la única versión del logo en la app; el
 * ícono del launcher usa el mismo dibujo. [VoraOrb] es la variante animada de los flujos de voz.
 */
@Composable
fun VoraLogo(
    modifier: Modifier = Modifier,
    size: Dp = 18.dp
) {
    VoraOrb(size = size, modifier = modifier, icon = null)
}

@Preview(showBackground = true)
@Composable
private fun VoraLogoPreview() {
    AppTheme {
        Box(modifier = Modifier.background(androidx.compose.ui.graphics.Color.White).padding(8.dp)) {
            VoraLogo(size = 64.dp)
        }
    }
}
