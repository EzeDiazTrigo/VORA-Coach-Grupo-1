package com.example.tp3_parcial_grupo_1.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.RadialGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme

// Colores propios de la marca de acceso en Figma; no forman parte de la paleta de la app.
private val BrandDark = Color(0xFF0E382F)
private val BrandSoft = Color(0xFF86EFAC)
private val BrandEmerald = Color(0xFF34D399)
private val BrandMintText = Color(0xFF6EE7B7)
private val BrandLime = Color(0xFFCCE65E)
private val BrandShadow = Color(0xFF064E3B)

private val SignInSize = 64.dp
private val SignUpSize = 56.dp

/** Las dos versiones del logo de acceso que dibuja Figma: Login - 01 y Login - 02. */
enum class VoraBrandMarkStyle { SignIn, SignUp }

/**
 * Logo de Login y Registro («VORA Bioluminescent AI Coach Orb Logo» y «Glowing Ambient Orb»):
 * disco con degradado, punto de pulso central e insignia «AI». Los flujos de la app usan [VoraLogo].
 */
@Composable
fun VoraBrandMark(
    modifier: Modifier = Modifier,
    style: VoraBrandMarkStyle = VoraBrandMarkStyle.SignIn
) {
    when (style) {
        VoraBrandMarkStyle.SignIn -> SignInMark(modifier)
        VoraBrandMarkStyle.SignUp -> SignUpMark(modifier)
    }
}

@Composable
private fun SignInMark(modifier: Modifier) {
    Box(modifier = modifier.size(SignInSize)) {
        Box(
            modifier = Modifier
                .size(SignInSize)
                // Anillo blanco de 4 dp que Figma dibuja como sombra con spread.
                .drawBehind { drawCircle(Color.White, radius = size.minDimension / 2 + 4.dp.toPx()) }
                .shadow(8.dp, CircleShape, ambientColor = Color.Black.copy(alpha = 0.1f), spotColor = Color.Black.copy(alpha = 0.1f))
                .background(SignInGradient, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Box(Modifier.size(20.dp).background(Color.White.copy(alpha = 0.3f), CircleShape))
        }
        AiBadge(
            modifier = Modifier.offset(39.dp, 47.dp),
            borderColor = Color.White,
            textColor = BrandMintText
        )
    }
}

@Composable
private fun SignUpMark(modifier: Modifier) {
    Box(
        modifier = modifier
            .size(SignUpSize)
            // Resplandor esmeralda: Figma lo dibuja con un rectángulo de 64 dp y desenfoque de 16.
            .drawBehind {
                drawCircle(
                    brush = Brush.radialGradient(
                        colors = listOf(BrandEmerald.copy(alpha = 0.3f), BrandEmerald.copy(alpha = 0f)),
                        center = center,
                        radius = 40.dp.toPx()
                    ),
                    radius = 40.dp.toPx()
                )
            }
    ) {
        Box(
            modifier = Modifier
                .size(SignUpSize)
                .shadow(8.dp, CircleShape, ambientColor = BrandShadow.copy(alpha = 0.2f), spotColor = BrandShadow.copy(alpha = 0.2f))
                .background(SignUpGradient, CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Box(Modifier.size(20.dp).background(Color.White.copy(alpha = 0.3f), CircleShape), contentAlignment = Alignment.Center) {
                Box(Modifier.size(10.dp).background(Color.White, CircleShape))
            }
        }
        AiBadge(
            modifier = Modifier.offset(33.dp, 39.dp),
            borderColor = BrandShadow.copy(alpha = 0.3f),
            textColor = BrandLime,
            elevated = true
        )
    }
}

@Composable
private fun AiBadge(modifier: Modifier, borderColor: Color, textColor: Color, elevated: Boolean = false) {
    Box(
        modifier = modifier
            .size(width = 29.dp, height = 21.dp)
            .then(if (elevated) Modifier.shadow(2.dp, CircleShape, ambientColor = Color.Black.copy(alpha = 0.05f), spotColor = Color.Black.copy(alpha = 0.05f)) else Modifier)
            .background(BrandDark, CircleShape)
            .border(1.dp, borderColor, CircleShape),
        contentAlignment = Alignment.Center
    ) {
        Text(
            text = "AI",
            style = MaterialTheme.typography.labelSmall.copy(
                fontSize = 10.sp,
                lineHeight = 15.sp,
                fontWeight = FontWeight.Bold,
                letterSpacing = 0.5.sp
            ),
            color = textColor
        )
    }
}

// Degradado radial con centro al 35 % (arriba a la izquierda) y radio hasta la esquina opuesta.
private val SignInGradient = object : ShaderBrush() {
    override fun createShader(size: Size): Shader = RadialGradientShader(
        center = Offset(size.width * 0.349f, size.height * 0.349f),
        radius = size.width * 0.919f,
        colors = listOf(BrandSoft, BrandEmerald, BrandDark),
        colorStops = listOf(0f, 0.45f, 1f)
    )
}

// Degradado lineal a 45°, de la esquina inferior izquierda a la superior derecha.
private val SignUpGradient = object : ShaderBrush() {
    override fun createShader(size: Size): Shader = LinearGradientShader(
        from = Offset(0f, size.height),
        to = Offset(size.width, 0f),
        colors = listOf(BrandDark, BrandEmerald, BrandLime),
        colorStops = listOf(0f, 0.5f, 1f)
    )
}

@Preview(showBackground = true)
@Composable
private fun VoraBrandMarkPreview() {
    AppTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            VoraBrandMark(style = VoraBrandMarkStyle.SignIn)
        }
    }
}

@Preview(showBackground = true)
@Composable
private fun VoraBrandMarkSignUpPreview() {
    AppTheme {
        Box(modifier = Modifier.padding(16.dp)) {
            VoraBrandMark(style = VoraBrandMarkStyle.SignUp)
        }
    }
}
