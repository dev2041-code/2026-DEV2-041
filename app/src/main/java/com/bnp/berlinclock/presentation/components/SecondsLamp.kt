package com.bnp.berlinclock.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.bnp.berlinclock.R
import com.bnp.berlinclock.domain.model.LampState
import com.bnp.berlinclock.presentation.theme.BerlinClockTheme
import com.bnp.berlinclock.presentation.theme.BerlinYellow
import com.bnp.berlinclock.presentation.theme.LampOff

/**
 * Seconds lamp component for Berlin Clock.
 *
 * @param lampState Current state of the seconds lamp
 * @param modifier Optional modifier for layout customization
 */
@Composable
fun SecondsLamp(
    lampState: LampState,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        // Label
        Text(
            text = stringResource(R.string.seconds_label),
            style = MaterialTheme.typography.labelSmall,
            color = Color.White.copy(alpha = 0.6f),
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Circular lamp
        Box(
            modifier =
                Modifier
                    .size(60.dp)
                    .clip(CircleShape)
                    .background(if (lampState == LampState.YELLOW) BerlinYellow else LampOff)
                    .border(
                        width = 2.dp,
                        color = Color.White.copy(alpha = 0.2f),
                        shape = CircleShape,
                    ),
        )
    }
}

/**
 * Preview of seconds lamp in ON state (yellow).
 */
@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun SecondsLampOnPreview() {
    BerlinClockTheme {
        SecondsLamp(lampState = LampState.YELLOW)
    }
}

/**
 * Preview of seconds lamp in OFF state.
 */
@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun SecondsLampOffPreview() {
    BerlinClockTheme {
        SecondsLamp(lampState = LampState.OFF)
    }
}
