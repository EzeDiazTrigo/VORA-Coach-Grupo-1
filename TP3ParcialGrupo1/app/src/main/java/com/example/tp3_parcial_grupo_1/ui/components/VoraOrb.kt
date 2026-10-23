package com.example.tp3_parcial_grupo_1.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.example.tp3_parcial_grupo_1.R
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

// Active: verde (asistente disponible). Muted: gris (error de micrófono).
enum class OrbVariant { Active, Muted }

// Proporciones del componente vora-orb de Figma, relativas al tamaño del frame.
private const val RingDiameter = 0.9f
private const val RingThickness = 0.054f
private const val CoreDiameter = 0.75f
private const val RippleDiameter = 1.15f
private const val RippleThickness = 0.023f
private const val IconSize = 0.23f

/**
 * Orbe de VORA dibujado en Canvas: brillo radial, anillo con degradado, núcleo y una onda
 * opcional que late mientras el asistente escucha o habla. La onda se dibuja fuera del tamaño
 * indicado (115 %), como en Figma.
 */
@Composable
fun VoraOrb(
    size: Dp,
    modifier: Modifier = Modifier,
    variant: OrbVariant = OrbVariant.Active,
    glowAlpha: Float = 0.25f,
    showRipple: Boolean = false,
    @DrawableRes icon: Int = R.drawable.ic_vora_waveform
) {
    val colors = MaterialTheme.colorScheme
    val extended = MaterialTheme.extendedColors
    val ringColors: List<Color>
    val coreColors: List<Color>
    val glowColors: List<Color>
    if (variant == OrbVariant.Active) {
        ringColors = listOf(colors.tertiary, colors.secondary, colors.primary)
        coreColors = listOf(colors.secondary, colors.primary)
        glowColors = listOf(colors.secondary, colors.primary)
    } else {
        ringColors = listOf(colors.outlineVariant, extended.textSubtle, colors.primary)
        coreColors = listOf(extended.textSubtle, colors.onSurfaceVariant)
        glowColors = listOf(extended.textSubtle, colors.primary)
    }
    val rippleColor = colors.secondary

    val pulse by rememberInfiniteTransition(label = "orbPulse").animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(tween(1400, easing = LinearEasing), RepeatMode.Reverse),
        label = "orbPulseValue"
    )

    Box(modifier = modifier.size(size), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val side = this.size.minDimension
            val c = center

            drawCircle(
                brush = Brush.radialGradient(glowColors, center = c, radius = side / 2),
                radius = side / 2,
                alpha = glowAlpha
            )

            if (showRipple) {
                val rippleScale = 1f + 0.04f * pulse
                val thickness = side * RippleThickness
                drawCircle(
                    color = rippleColor,
                    radius = (side * RippleDiameter / 2 - thickness / 2) * rippleScale,
                    alpha = 0.3f * (1f - 0.5f * pulse),
                    style = Stroke(width = thickness)
                )
            }

            val ringThickness = side * RingThickness
            val ringRadius = side * RingDiameter / 2
            drawCircle(
                brush = Brush.horizontalGradient(ringColors, startX = c.x - ringRadius, endX = c.x + ringRadius),
                radius = ringRadius - ringThickness / 2,
                alpha = 0.8f,
                style = Stroke(width = ringThickness)
            )

            val coreRadius = side * CoreDiameter / 2
            drawCircle(
                brush = Brush.linearGradient(
                    coreColors,
                    start = Offset(c.x - coreRadius * 0.6f, c.y - coreRadius),
                    end = Offset(c.x + coreRadius, c.y - coreRadius * 0.7f)
                ),
                radius = coreRadius
            )
        }
        Icon(
            painter = painterResource(icon),
            contentDescription = null,
            tint = colors.onPrimary,
            modifier = Modifier.size(size * IconSize)
        )
    }
}

@Preview(showBackground = true, widthDp = 560, heightDp = 240)
@Composable
private fun VoraOrbPreview() {
    AppTheme {
        Row(
            modifier = Modifier.padding(MaterialTheme.spacing.xl),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.xxl),
            verticalAlignment = Alignment.CenterVertically
        ) {
            VoraOrb(size = 140.dp)
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .padding(MaterialTheme.spacing.lg)
            ) {
                VoraOrb(size = 140.dp, glowAlpha = 0.45f, showRipple = true)
            }
            VoraOrb(size = 120.dp, variant = OrbVariant.Muted, glowAlpha = 0.1f, icon = R.drawable.ic_repeat_1)
        }
    }
}
