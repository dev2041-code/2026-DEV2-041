package com.bnp.berlinclock.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

/**
 * Dark color scheme for Berlin Clock.
 *
 */
private val DarkColorScheme =
    darkColorScheme(
        primary = BerlinYellow,
        secondary = BerlinRed,
        background = Background,
        surface = Surface,
    )

/**
 * Berlin Clock Material3 theme.
 *
 * @param content The composable content to theme
 */
@Composable
fun BerlinClockTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = DarkColorScheme,
        typography = Typography,
        content = content,
    )
}
