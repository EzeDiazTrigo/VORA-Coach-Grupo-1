package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme

// Default: el vora-orb de las tarjetas de Workout, Nutrition, Voice y Home. Insight: la variante
// de 40 dp de «VORA Insight» (Progress) y «VORA Personalisation» (Profile), con anillo y núcleo más finos.
enum class VoraLogoStyle { Default, Insight }

/**
 * Marca de VORA: el `vora-orb` de Figma, quieto y sin onda. Es la única versión del logo en la app;
 * el ícono del launcher usa el mismo dibujo. [VoraOrb] es la variante animada de los flujos de voz.
 * El brillo cambia por pantalla en Figma, por eso [glowAlpha] se indica en cada uso.
 */
@Composable
fun VoraLogo(
    modifier: Modifier = Modifier,
    size: Dp = 18.dp,
    glowAlpha: Float = 0.2f,
    style: VoraLogoStyle = VoraLogoStyle.Default
) {
    when (style) {
        VoraLogoStyle.Default -> VoraOrb(size = size, modifier = modifier, glowAlpha = glowAlpha, icon = null)
        VoraLogoStyle.Insight -> VoraOrb(
            size = size,
            modifier = modifier,
            glowAlpha = glowAlpha,
            icon = null,
            ringFraction = 0.85f,
            coreFraction = 0.7f,
            ringAlpha = 0.9f
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun VoraLogoPreview() {
    AppTheme {
        Box(modifier = Modifier.background(Color.White).padding(8.dp)) {
            VoraLogo(size = 64.dp)
        }
    }
}
