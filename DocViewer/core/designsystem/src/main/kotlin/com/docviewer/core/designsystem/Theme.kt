package com.docviewer.core.designsystem

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

private val LightColors = lightColorScheme(
    primary = Indigo40,
    primaryContainer = IndigoContainerLight,
    secondary = Teal40,
    background = Surface99,
    surface = Surface99,
)

private val DarkColors = darkColorScheme(
    primary = Indigo80,
    primaryContainer = IndigoContainerDark,
    secondary = Teal80,
    background = SurfaceDark,
    surface = SurfaceDark,
)

@Composable
fun DocViewerTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit,
) {
    val colors = when {
        // Динамические цвета Material You: опционально (включим в настройках).
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val ctx = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(ctx) else dynamicLightColorScheme(ctx)
        }
        darkTheme -> DarkColors
        else -> LightColors
    }
    MaterialTheme(
        colorScheme = colors,
        typography = DocTypography,
        shapes = DocShapes,
        content = content,
    )
}
