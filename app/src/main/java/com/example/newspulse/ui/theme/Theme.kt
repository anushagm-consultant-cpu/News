package com.example.newspulse.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color


enum class AppTheme {
    LIGHT, DARK, AUTUMN, FAIRY
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
    onPrimary = AutumnRed,
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

@Composable
fun NewsPulseTheme(
    appTheme: AppTheme = if (isSystemInDarkTheme()) AppTheme.DARK else AppTheme.LIGHT,
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = when (appTheme) {
        AppTheme.LIGHT -> LightColorScheme
        AppTheme.DARK -> DarkColorScheme
        AppTheme.AUTUMN -> AutumnColorScheme
        AppTheme.FAIRY -> fairyColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
