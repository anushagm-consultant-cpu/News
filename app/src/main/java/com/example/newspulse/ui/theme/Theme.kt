package com.example.newspulse.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontFamily


enum class AppTheme {
    LIGHT, DARK, AUTUMN, FAIRY, ROSE
}

private val DarkColorScheme = darkColorScheme(
    primary = NewsBlue,
    secondary = NewsBlueLight,
    tertiary = Color(0xFF4FC3F7),
    background = DarkBackground,
    surface = DarkSurface,
    onPrimary = Color.Black,
    onBackground = TextPrimary,
    onSurface = TextPrimary
)

private val LightColorScheme = lightColorScheme(
    primary = Teal700,
    secondary = PurpleGrey40,
    tertiary = Pink40,
    background = Color.White,
    surface = Color.White,
    onPrimary = Color.White,
    onBackground = Color.Black,
    onSurface = Color.Black
)

private val AutumnColorScheme = lightColorScheme(
    primary = AutumnOrange,
    secondary = AutumnRed,
    tertiary = AutumnYellow,
    background = AutumnLightTan,
    surface = AutumnLightTan,
    onPrimary = Color.White,
    onBackground = AutumnBrown,
    onSurface = AutumnBrown
)


private val fairyColorScheme = darkColorScheme(
    primary = FairyLavender,
    secondary = FairyLilac,
    tertiary = FairyMistBlue,
    background = FairyNight,
    surface = FairyNight,
    onPrimary = Color(0xFF472B8A),
    onBackground = FairyCream,
    onSurface = FairyCream
)

private val RoseColorScheme = lightColorScheme(
    primary = Color(0xFFE91E63),
    secondary = Color(0xFFF48FB1),
    tertiary = Color(0xFFF8BBD0),
    background = Color(0xFFFFF1F6),
    surface = Color(0xFFFFFFFF),
    onPrimary = AutumnRed,
    onBackground = Color(0xFF880E4F),
    onSurface = Color(0xFF880E4F)
)

@Composable
fun NewsPulseTheme(
    appTheme: AppTheme = if (isSystemInDarkTheme()) AppTheme.DARK else AppTheme.LIGHT,
    fontFamily: FontFamily = FontFamily.Default,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when (appTheme) {
        AppTheme.LIGHT -> LightColorScheme
        AppTheme.DARK -> DarkColorScheme
        AppTheme.AUTUMN -> AutumnColorScheme
        AppTheme.FAIRY -> fairyColorScheme
        AppTheme.ROSE -> RoseColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = getTypography(fontFamily),
        content = content
    )
}
