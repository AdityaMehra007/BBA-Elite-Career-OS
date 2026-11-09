package com.example.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = GoldMetallic,
    onPrimary = ObsidianBlack,
    primaryContainer = GoldMuted,
    onPrimaryContainer = GoldChampagne,
    secondary = EmeraldFinTech,
    onSecondary = ObsidianBlack,
    secondaryContainer = EmeraldDark,
    onSecondaryContainer = EmeraldFinTech,
    tertiary = CyanAccent,
    background = ObsidianBlack,
    onBackground = TextPrimary,
    surface = ObsidianDark,
    onSurface = TextPrimary,
    surfaceVariant = ObsidianSurface,
    onSurfaceVariant = TextSecondary,
    outline = ObsidianBorder,
    error = RoseAlert,
    onError = Color.White
)

@Composable
fun AureliusTheme(
    content: @Composable () -> Unit
) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content
    )
}
