package com.arkteya.vkneewsclient.ui.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Black_500, //Purple80,
    secondary = Black_500,
    tertiary = Pink80,
    onPrimary = Color.White,
    onSecondary = Black_500,
    background = background
)

private val LightColorScheme = lightColorScheme(
    primary = Black_500,//Purple40,
    secondary = Black_500,
    tertiary = Pink40,
    onPrimary = Black_900,
    onSecondary = Black_500,
    background = background,
    surface = Color(0xFFFFFBFE),
    onTertiary = Color.White,
    onBackground = Color(0xFF201F24),
    onSurface = Color(0xFF1C1B1F),
    outline = Color.Yellow


)

@Composable
fun VKNeewsClientTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }

        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}