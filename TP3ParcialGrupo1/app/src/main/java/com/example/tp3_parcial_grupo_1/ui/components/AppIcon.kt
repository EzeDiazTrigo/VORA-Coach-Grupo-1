package com.example.tp3_parcial_grupo_1.ui.components

import androidx.annotation.DrawableRes
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import com.example.tp3_parcial_grupo_1.R
import com.example.tp3_parcial_grupo_1.ui.theme.AppTheme
import com.example.tp3_parcial_grupo_1.ui.theme.extendedColors
import com.example.tp3_parcial_grupo_1.ui.theme.iconSizes
import com.example.tp3_parcial_grupo_1.ui.theme.spacing

/**
 * Íconos exportados de Figma (`res/drawable/ic_*`), con el nombre que tienen en el diseño.
 * Los de varios colores (logos e insignias) se dibujan sin tinte para conservar sus colores.
 */
enum class AppIcons(@param:DrawableRes val res: Int, val multicolor: Boolean = false) {
    Activity(R.drawable.ic_activity),
    AlertCircle(R.drawable.ic_alert_circle),
    Apple(R.drawable.ic_apple),
    ArrowForward(R.drawable.ic_arrow_forward),
    ArrowRight(R.drawable.ic_arrow_right),
    AudioWaveform(R.drawable.ic_audio_waveform),
    BadgeCompleted(R.drawable.ic_badge_completed, multicolor = true),
    Bed(R.drawable.ic_bed),
    Bell(R.drawable.ic_bell),
    Calendar(R.drawable.ic_calendar),
    Camera(R.drawable.ic_camera),
    ChartLine(R.drawable.ic_chart_line),
    Check(R.drawable.ic_check),
    CheckCircle(R.drawable.ic_check_circle_filled),
    ChevronDown(R.drawable.ic_chevron_down),
    ChevronLeft(R.drawable.ic_chevron_left),
    ChevronRight(R.drawable.ic_chevron_right),
    Clock(R.drawable.ic_clock),
    Close(R.drawable.ic_close),
    Compass(R.drawable.ic_compass),
    Dumbbell(R.drawable.ic_dumbbell),
    Edit(R.drawable.ic_edit),
    Eye(R.drawable.ic_eye),
    FaceId(R.drawable.ic_face_id),
    FieldEye(R.drawable.ic_field_eye),
    FieldLock(R.drawable.ic_field_lock),
    FieldMail(R.drawable.ic_field_mail),
    Flame(R.drawable.ic_flame),
    HelpCircle(R.drawable.ic_help_circle),
    Home(R.drawable.ic_home),
    Info(R.drawable.ic_info),
    Loader(R.drawable.ic_loader_circle),
    Lock(R.drawable.ic_lock),
    LogoApple(R.drawable.ic_logo_apple, multicolor = true),
    LogoGoogle(R.drawable.ic_logo_google, multicolor = true),
    Mail(R.drawable.ic_mail),
    Mic(R.drawable.ic_mic),
    MoreHorizontal(R.drawable.ic_more_horizontal),
    Navigation(R.drawable.ic_navigation),
    Pause(R.drawable.ic_pause),
    Pen(R.drawable.ic_pen),
    Play(R.drawable.ic_play),
    Plus(R.drawable.ic_plus),
    Repeat(R.drawable.ic_repeat_1),
    Search(R.drawable.ic_search),
    Send(R.drawable.ic_send),
    Settings(R.drawable.ic_settings),
    Shield(R.drawable.ic_shield),
    ShieldCheck(R.drawable.ic_shield_check),
    Sliders(R.drawable.ic_sliders),
    Sparkles(R.drawable.ic_sparkles),
    Star(R.drawable.ic_star),
    StopCircle(R.drawable.ic_stop_circle),
    Target(R.drawable.ic_target),
    TrendingUp(R.drawable.ic_trending_up),
    Trophy(R.drawable.ic_trophy),
    User(R.drawable.ic_user),
    Verified(R.drawable.ic_verified_user),
    Volume(R.drawable.ic_volume_2),
    VoraRetry(R.drawable.ic_vora_retry),
    VoraWaveform(R.drawable.ic_vora_waveform),
    XCircle(R.drawable.ic_x_circle)
}

/**
 * Único punto para dibujar un ícono del diseño. [contentDescription] es obligatorio: `null` solo si es decorativo.
 * El área táctil de 48 dp la pone el botón que lo contiene, no el ícono.
 */
@Composable
fun AppIcon(
    icon: AppIcons,
    contentDescription: String?,
    modifier: Modifier = Modifier,
    size: Dp = MaterialTheme.iconSizes.xxl,
    tint: Color = LocalContentColor.current
) {
    val painter = painterResource(icon.res)
    if (icon.multicolor) {
        Image(painter = painter, contentDescription = contentDescription, modifier = modifier.size(size))
    } else {
        Icon(painter = painter, contentDescription = contentDescription, modifier = modifier.size(size), tint = tint)
    }
}

@Preview(showBackground = true)
@Composable
private fun AppIconPreview() {
    AppTheme {
        val sizes = MaterialTheme.iconSizes
        Row(
            modifier = Modifier.padding(MaterialTheme.spacing.screen),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.md)
        ) {
            AppIcon(AppIcons.ChevronLeft, contentDescription = null, size = sizes.xs, tint = MaterialTheme.colorScheme.primary)
            AppIcon(AppIcons.Check, contentDescription = null, size = sizes.sm, tint = MaterialTheme.extendedColors.mint)
            AppIcon(AppIcons.Mic, contentDescription = null, size = sizes.md, tint = MaterialTheme.extendedColors.textSubtle)
            AppIcon(AppIcons.Search, contentDescription = null, size = sizes.lg)
            AppIcon(AppIcons.LogoGoogle, contentDescription = null, size = sizes.xl)
            AppIcon(AppIcons.LogoApple, contentDescription = null, size = sizes.xxl)
        }
    }
}
