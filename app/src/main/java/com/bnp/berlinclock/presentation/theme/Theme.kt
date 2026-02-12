package com.bnp.berlinclock.presentation.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

/**
 * Berlin Clock color scheme.
 *
 */
private val BerlinColorScheme =
    darkColorScheme(
        primary = BerlinYellow,
        secondary = BerlinRed,
        tertiary = LampOff,
        background = Background,
    )

/**
 * Berlin Clock Material3 theme.
 */
@Composable
fun BerlinClockTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = BerlinColorScheme,
        typography = Typography,
        content = content,
    )
}
