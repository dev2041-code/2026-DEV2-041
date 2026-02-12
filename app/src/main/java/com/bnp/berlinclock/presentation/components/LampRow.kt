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
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.tooling.preview.Preview
import com.bnp.berlinclock.domain.model.LampColor
import com.bnp.berlinclock.domain.model.LampState
import com.bnp.berlinclock.presentation.theme.BerlinClockTheme
import com.bnp.berlinclock.presentation.theme.Dimensions

/**
 * Determines the display color for a lamp.
 *
 * @param lampState Current state of the lamp (ON/OFF)
 * @param lampColor Color type for this lamp (RED/YELLOW)
 * @param isQuarterPosition Whether this is a quarter marker position
 * @return Color from MaterialTheme
 */
@Composable
private fun getLampColor(
    lampState: LampState,
    lampColor: LampColor,
    isQuarterPosition: Boolean,
): Color =
    when {
        lampState == LampState.OFF -> MaterialTheme.colorScheme.tertiary
        isQuarterPosition -> MaterialTheme.colorScheme.secondary
        lampColor == LampColor.RED -> MaterialTheme.colorScheme.secondary
        else -> MaterialTheme.colorScheme.primary
    }

/**
 * Determines the accessibility description for a lamp.
 *
 * @param lampState Current state of the lamp (ON/OFF)
 * @param lampColor Color type for this lamp (RED/YELLOW)
 * @param isQuarterPosition Whether this is a quarter marker position
 * @return Accessibility description string
 */
private fun getAccessibilityDescription(
    lampState: LampState,
    lampColor: LampColor,
    isQuarterPosition: Boolean,
): String =
    when (lampState) {
        LampState.ON ->
            when {
                isQuarterPosition -> "Red lamp on"
                lampColor == LampColor.RED -> "Red lamp on"
                else -> "Yellow lamp on"
            }
        LampState.OFF -> "Lamp off"
    }

/**
 * Lamp row component for Berlin Clock.
 *
 *
 * @param lamps List of lamp states (ON/OFF)
 * @param lampColor Color for illuminated lamps (RED or YELLOW)
 * @param label Text label to display above the row
 * @param quarterPositions Positions that should be RED (for 5-minute row)
 * @param modifier Optional modifier
 */
@Composable
fun LampRow(
    lamps: List<LampState>,
    lampColor: LampColor,
    label: String,
    quarterPositions: Set<Int> = emptySet(),
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.labelSmall,
            color = Color.White.copy(alpha = 0.6f),
        )

        Spacer(modifier = Modifier.height(Dimensions.LampRowSpacer))

        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(Dimensions.LampSpacing),
        ) {
            lamps.forEachIndexed { index, lampState ->
                val isQuarterPosition = index in quarterPositions

                Box(
                    modifier =
                        Modifier
                            .weight(1f)
                            .height(Dimensions.LampHeight)
                            .clip(RoundedCornerShape(Dimensions.LampCornerRadius))
                            .background(getLampColor(lampState, lampColor, isQuarterPosition))
                            .border(
                                width = Dimensions.LampBorderWidth,
                                color = Color.White.copy(alpha = 0.2f),
                                shape = RoundedCornerShape(Dimensions.LampCornerRadius),
                            )
                            .semantics {
                                contentDescription =
                                    getAccessibilityDescription(
                                        lampState,
                                        lampColor,
                                        isQuarterPosition,
                                    )
                            },
                )
            }
        }
    }
}

/**
 * Preview of five-hour row (RED lamps).
 */
@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun LampRowFiveHoursPreview() {
    BerlinClockTheme {
        LampRow(
            lamps = listOf(LampState.ON, LampState.ON, LampState.OFF, LampState.OFF),
            lampColor = LampColor.RED,
            label = "5 HOURS",
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
                    LampState.ON,
                    LampState.ON,
                    LampState.ON,
                    LampState.ON,
                    LampState.ON,
                    LampState.ON,
                    LampState.OFF,
                    LampState.OFF,
                    LampState.OFF,
                    LampState.OFF,
                    LampState.OFF,
                ),
            lampColor = LampColor.YELLOW,
            label = "5 MINUTES",
            quarterPositions = setOf(2, 5, 8),
        )
    }
}

/**
 * Preview of one-minute row (YELLOW lamps).
 */
@Preview(showBackground = true, backgroundColor = 0xFF121212)
@Composable
private fun LampRowOneMinutePreview() {
    BerlinClockTheme {
        LampRow(
            lamps = listOf(LampState.ON, LampState.ON, LampState.OFF, LampState.OFF),
            lampColor = LampColor.YELLOW,
            label = "1 MINUTE",
        )
    }
}
