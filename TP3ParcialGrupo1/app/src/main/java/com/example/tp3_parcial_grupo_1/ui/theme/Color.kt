package com.example.tp3_parcial_grupo_1.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

// Paleta observada en el sistema de diseño de Figma.
private val Green = Color(0xFF123D35)
private val Mint = Color(0xFF65D6B0)
private val Lime = Color(0xFFC9E86A)
private val Surface = Color(0xFFFFFFFF)
private val Background = Color(0xFFF7F8F5)
private val Border = Color(0xFFE4E9E6)
private val TextPrimary = Color(0xFF17211F)
private val TextSecondary = Color(0xFF68736F)
private val TextSubtle = Color(0xFF9AA39F)
private val Attention = Color(0xFFD95D5D)
private val Success = Color(0xFF27AE60)
private val MintSoft = Color(0x1F65D6B0)

// Marca de acceso (Login y Registro): Figma usa una paleta esmeralda propia para el orbe.
private val BrandDark = Color(0xFF0E382F)
private val BrandSoft = Color(0xFF86EFAC)
private val BrandEmerald = Color(0xFF34D399)
private val BrandMintText = Color(0xFF6EE7B7)
private val BrandLime = Color(0xFFCCE65E)
private val BrandShadow = Color(0xFF064E3B)

val AppColorScheme = lightColorScheme(
    primary = Green,
    onPrimary = Surface,
    primaryContainer = Green,
    onPrimaryContainer = Surface,
    secondary = Mint,
    onSecondary = Green,
    secondaryContainer = MintSoft,
    onSecondaryContainer = Green,
    tertiary = Lime,
    onTertiary = Green,
    background = Background,
    onBackground = TextPrimary,
    surface = Surface,
    onSurface = TextPrimary,
    surfaceVariant = Surface,
    onSurfaceVariant = TextSecondary,
    outline = TextSubtle,
    outlineVariant = Border,
    error = Attention,
    onError = Surface
)

// Colores del diseño sin equivalente en Material 3.
data class ExtendedColors(
    val textSubtle: Color = TextSubtle,
    val success: Color = Success,
    val mintSoft: Color = MintSoft,
    val mint: Color = Mint,
    val brand: BrandColors = BrandColors()
)

// Colores del logo de Login y Registro (VoraBrandMark); no se usan en el resto de la app.
data class BrandColors(
    val dark: Color = BrandDark,
    val soft: Color = BrandSoft,
    val emerald: Color = BrandEmerald,
    val mintText: Color = BrandMintText,
    val lime: Color = BrandLime,
    val shadow: Color = BrandShadow
)

val LocalExtendedColors = staticCompositionLocalOf { ExtendedColors() }

val MaterialTheme.extendedColors: ExtendedColors
    @Composable
    @ReadOnlyComposable
    get() = LocalExtendedColors.current
