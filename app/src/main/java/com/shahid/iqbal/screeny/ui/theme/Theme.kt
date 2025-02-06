package com.shahid.iqbal.screeny.ui.theme

import android.os.Build
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFBB86FC),  // Vibrant Purple
    secondary = Color(0xFF03DAC5), // Teal Accent
    tertiary = Color(0xFFFFA726)   // Warm Orange Accent
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFF6200EE),  // Deep Purple
    secondary = Color(0xFF03A9F4), // Bright Blue Accent
    tertiary = Color(0xFFFF5722)   // Bold Red-Orange Accent
)


@Composable
fun ScreenyTheme(
    darkTheme: Boolean,
    dynamicColor: Boolean,
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