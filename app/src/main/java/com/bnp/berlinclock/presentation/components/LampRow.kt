package com.bnp.berlinclock.presentation.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.bnp.berlinclock.R
import com.bnp.berlinclock.domain.model.LampState
import com.bnp.berlinclock.presentation.theme.BerlinClockTheme
import com.bnp.berlinclock.presentation.theme.BerlinRed
import com.bnp.berlinclock.presentation.theme.BerlinYellow
import com.bnp.berlinclock.presentation.theme.Dimensions
import com.bnp.berlinclock.presentation.theme.LampOff

/**
 * Lamp row component for Berlin Clock.
 *
 * @param lamps List of lamp states (type-safe, no raw strings)
 * @param label Text label to display above the row
 * @param modifier Optional modifier for layout customization
 */
@Composable
fun LampRow(
    lamps: List<LampState>,
    label: String,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        // Label
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White.copy(alpha = 0.6f),
        )

        Spacer(modifier = Modifier.height(Dimensions.LampRowSpacer))

        // Lamp row
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Dimensions.LampSpacing),
        ) {
            lamps.forEach { lampState ->
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(Dimensions.LampHeight)
                        .clip(RoundedCornerShape(Dimensions.LampCornerRadius))
                        .background(getLampColor(lampState))
                        .border(
                            width = Dimensions.LampBorderWidth,
                            color = Color.White.copy(alpha = 0.2f),
                            shape = RoundedCornerShape(Dimensions.LampCornerRadius),
                        )
                        .semantics {
                            contentDescription = when (lampState) {
                                LampState.RED -> "Red lamp on"
                                LampState.YELLOW -> "Yellow lamp on"
                                LampState.OFF -> "Lamp off"
                            }
                        },
                )
            }
        }
    }
}

/**
 * Maps lamp state to display color.
 * @param lampState The lamp state to map
 * @return The corresponding color
 */
private fun getLampColor(lampState: LampState): Color =
    when (lampState) {
        LampState.RED -> BerlinRed
        LampState.YELLOW -> BerlinYellow
        LampState.OFF -> LampOff
    }

/**
 * Preview of five-hour row (2 lamps on).
 */
@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun LampRowFiveHoursPreview() {
    BerlinClockTheme {
        LampRow(
            lamps =
                listOf(
                    LampState.RED,
                    LampState.RED,
                    LampState.OFF,
                    LampState.OFF,
                ),
            label = stringResource(R.string.five_hours_label),
        )
    }
}

/**
 * Preview of five-minute row with quarter markers.
 */
@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun LampRowFiveMinutesPreview() {
    BerlinClockTheme {
        LampRow(
            lamps =
                listOf(
                    LampState.YELLOW,
                    LampState.YELLOW,
                    LampState.RED,
                    LampState.YELLOW,
                    LampState.YELLOW,
                    LampState.RED,
                    LampState.OFF,
                    LampState.OFF,
                    LampState.OFF,
                    LampState.OFF,
                    LampState.OFF,
                ),
            label = stringResource(R.string.five_hours_label),
        )
    }
}

/**
 * Preview of all lamps on (4 lamps).
 */
@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun LampRowAllOnPreview() {
    BerlinClockTheme {
        LampRow(
            lamps =
                listOf(
                    LampState.RED,
                    LampState.RED,
                    LampState.RED,
                    LampState.RED,
                ),
            label = stringResource(R.string.one_hour_label),
        )
    }
}
