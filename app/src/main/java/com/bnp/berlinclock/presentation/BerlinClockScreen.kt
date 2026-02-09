package com.bnp.berlinclock.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bnp.berlinclock.R
import com.bnp.berlinclock.domain.model.BerlinTime
import com.bnp.berlinclock.domain.model.LampState
import com.bnp.berlinclock.presentation.components.LampRow
import com.bnp.berlinclock.presentation.components.SecondsLamp
import com.bnp.berlinclock.presentation.theme.Background
import com.bnp.berlinclock.presentation.theme.BerlinClockTheme
import java.time.LocalTime
import java.time.format.DateTimeFormatter

/**
 * Main Berlin Clock screen.
 *
 * @param modifier Optional modifier for layout customization
 * @param viewModel ViewModel injected by Hilt
 */
@Composable
fun BerlinClockScreen(
    modifier: Modifier = Modifier,
    viewModel: BerlinClockViewModel = hiltViewModel(),
) {
    val uiState by viewModel.uiState.collectAsState()

    BerlinClockScreenContent(
        uiState = uiState,
        onAction = viewModel::onAction,
        modifier = modifier,
    )
}

/**
 * Content of Berlin Clock screen.
 * @param uiState Current UI state
 * @param onAction Action handler
 * @param modifier Optional modifier
 */
@Composable
private fun BerlinClockScreenContent(
    uiState: BerlinClockUiState,
    onAction: (BerlinClockAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier =
            modifier
                .fillMaxSize()
                .background(Background)
                .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(20.dp),
    ) {
        // Title
        Text(
            text = stringResource(R.string.berlin_clock_title),
            style = MaterialTheme.typography.headlineMedium,
            color = Color.White,
        )

        // Digital Time Display
        Text(
            text = uiState.currentTime.format(DateTimeFormatter.ofPattern("HH:mm:ss")),
            style = MaterialTheme.typography.displayMedium,
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(8.dp))

        // Berlin Clock Display
        uiState.berlinTime?.let { time ->
            BerlinClockDisplay(berlinTime = time)
        }

        Spacer(modifier = Modifier.height(16.dp))

        // Time Controls
        TimeControls(
            onAction = onAction,
            hours = uiState.currentTime.hour,
            minutes = uiState.currentTime.minute,
            seconds = uiState.currentTime.second,
        )

        // Set Current Time Button
        Button(
            onClick = { onAction(BerlinClockAction.SetCurrentTime) },
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.1f),
                    contentColor = Color.White,
                ),
        ) {
            Text(stringResource(R.string.set_current_time))
        }
    }
}

/**
 * Berlin Clock lamp display.
 *
 * Shows seconds lamp and all four lamp rows.
 *
 * @param berlinTime The Berlin time to display
 * @param modifier Optional modifier
 */
@Composable
private fun BerlinClockDisplay(
    berlinTime: BerlinTime,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(12.dp),
        modifier = modifier,
    ) {
        // Seconds lamp
        SecondsLamp(lampState = berlinTime.secondsLamp)

        Spacer(modifier = Modifier.height(8.dp))

        // Five hours row
        LampRow(
            lamps = berlinTime.fiveHourRow,
            label = stringResource(R.string.five_hours_label),
        )

        // One hour row
        LampRow(
            lamps = berlinTime.oneHourRow,
            label = stringResource(R.string.one_hour_label),
        )

        // Five minutes row
        LampRow(
            lamps = berlinTime.fiveMinuteRow,
            label = stringResource(R.string.five_minutes_label),
        )

        // One minute row
        LampRow(
            lamps = berlinTime.oneMinuteRow,
            label = stringResource(R.string.one_minute_label),
        )
    }
}

/**
 * Time controls component.
 *
 * Three columns for hours, minutes, and seconds with +/- buttons.
 *
 * @param onAction Action handler
 * @param hours Current hours value
 * @param minutes Current minutes value
 * @param seconds Current seconds value
 * @param modifier Optional modifier
 */
@Composable
private fun TimeControls(
    onAction: (BerlinClockAction) -> Unit,
    hours: Int,
    minutes: Int,
    seconds: Int,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        TimeControl(
            label = stringResource(R.string.hours_label),
            value = hours,
            onIncrement = { onAction(BerlinClockAction.IncrementHours) },
            onDecrement = { onAction(BerlinClockAction.DecrementHours) },
        )

        TimeControl(
            label = stringResource(R.string.minutes_label),
            value = minutes,
            onIncrement = { onAction(BerlinClockAction.IncrementMinutes) },
            onDecrement = { onAction(BerlinClockAction.DecrementMinutes) },
        )

        TimeControl(
            label = stringResource(R.string.seconds_label),
            value = seconds,
            onIncrement = { onAction(BerlinClockAction.IncrementSeconds) },
            onDecrement = { onAction(BerlinClockAction.DecrementSeconds) },
        )
    }
}

/**
 * Individual time control with +/- buttons.
 *
 * @param label Label text (HOURS, MINUTES, SECONDS)
 * @param value Current value to display
 * @param onIncrement Callback for + button
 * @param onDecrement Callback for - button
 * @param modifier Optional modifier
 */
@Composable
private fun TimeControl(
    label: String,
    value: Int,
    onIncrement: () -> Unit,
    onDecrement: () -> Unit,
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

        Spacer(modifier = Modifier.height(4.dp))

        // + Button
        Button(
            onClick = onIncrement,
            modifier = Modifier.size(45.dp),
            contentPadding = PaddingValues(0.dp),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.15f),
                ),
        ) {
            Text("+", fontSize = 20.sp, color = Color.White)
        }

        Spacer(modifier = Modifier.height(8.dp))

        // Value Display
        Text(
            text = value.toString().padStart(2, '0'),
            fontSize = 24.sp,
            color = Color.White,
            fontWeight = FontWeight.Bold,
        )

        Spacer(modifier = Modifier.height(8.dp))

        // - Button
        Button(
            onClick = onDecrement,
            modifier = Modifier.size(45.dp),
            contentPadding = PaddingValues(0.dp),
            colors =
                ButtonDefaults.buttonColors(
                    containerColor = Color.White.copy(alpha = 0.15f),
                ),
        ) {
            Text("-", fontSize = 20.sp, color = Color.White)
        }
    }
}

// PREVIEWS

/**
 * Preview of Berlin Clock at 13:17:01
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BerlinClockScreenPreview() {
    BerlinClockTheme {
        BerlinClockScreenContent(
            uiState =
                BerlinClockUiState(
                    currentTime = LocalTime.of(13, 17, 1),
                    berlinTime =
                        BerlinTime(
                            secondsLamp = LampState.OFF,
                            fiveHourRow =
                                listOf(
                                    LampState.RED,
                                    LampState.RED,
                                    LampState.OFF,
                                    LampState.OFF,
                                ),
                            oneHourRow =
                                listOf(
                                    LampState.RED,
                                    LampState.RED,
                                    LampState.RED,
                                    LampState.OFF,
                                ),
                            fiveMinuteRow =
                                listOf(
                                    LampState.YELLOW,
                                    LampState.YELLOW,
                                    LampState.RED,
                                    LampState.OFF,
                                    LampState.OFF,
                                    LampState.OFF,
                                    LampState.OFF,
                                    LampState.OFF,
                                    LampState.OFF,
                                    LampState.OFF,
                                    LampState.OFF,
                                ),
                            oneMinuteRow =
                                listOf(
                                    LampState.YELLOW,
                                    LampState.YELLOW,
                                    LampState.OFF,
                                    LampState.OFF,
                                ),
                        ),
                ),
            onAction = {},
        )
    }
}

/**
 * Preview of Berlin Clock at midnight (00:00:00)
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BerlinClockScreenMidnightPreview() {
    BerlinClockTheme {
        BerlinClockScreenContent(
            uiState =
                BerlinClockUiState(
                    currentTime = LocalTime.of(0, 0, 0),
                    berlinTime =
                        BerlinTime(
                            secondsLamp = LampState.YELLOW,
                            fiveHourRow = List(4) { LampState.OFF },
                            oneHourRow = List(4) { LampState.OFF },
                            fiveMinuteRow = List(11) { LampState.OFF },
                            oneMinuteRow = List(4) { LampState.OFF },
                        ),
                ),
            onAction = {},
        )
    }
}

/**
 * Preview of Berlin Clock at maximum time (23:59:59)
 */
@Preview(showBackground = true, showSystemUi = true)
@Composable
private fun BerlinClockScreenMaxTimePreview() {
    BerlinClockTheme {
        BerlinClockScreenContent(
            uiState =
                BerlinClockUiState(
                    currentTime = LocalTime.of(23, 59, 59),
                    berlinTime =
                        BerlinTime(
                            secondsLamp = LampState.OFF,
                            fiveHourRow = List(4) { LampState.RED },
                            oneHourRow =
                                listOf(
                                    LampState.RED,
                                    LampState.RED,
                                    LampState.RED,
                                    LampState.OFF,
                                ),
                            fiveMinuteRow =
                                listOf(
                                    LampState.YELLOW,
                                    LampState.YELLOW,
                                    LampState.RED,
                                    LampState.YELLOW,
                                    LampState.YELLOW,
                                    LampState.RED,
                                    LampState.YELLOW,
                                    LampState.YELLOW,
                                    LampState.RED,
                                    LampState.YELLOW,
                                    LampState.YELLOW,
                                ),
                            oneMinuteRow = List(4) { LampState.YELLOW },
                        ),
                ),
            onAction = {},
        )
    }
}
