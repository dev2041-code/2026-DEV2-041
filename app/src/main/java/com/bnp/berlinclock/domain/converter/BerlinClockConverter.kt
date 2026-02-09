package com.bnp.berlinclock.domain.converter

import com.bnp.berlinclock.domain.model.BerlinTime
import com.bnp.berlinclock.domain.model.LampState

/**
 * Converts standard time format to Berlin Clock representation.
 */
class BerlinClockConverter {
    /**
     * Converts seconds to lamp state.
     *
     * @param seconds The seconds value (0-59)
     * @return YELLOW for even seconds, OFF for odd
     */
    fun convertSeconds(seconds: Int): LampState =
        if (seconds % 2 == 0) LampState.YELLOW else LampState.OFF

    /**
     * Converts hours to five-hour lamp row.
     *
     * Each lamp represents 5 hours.
     *
     * @param hours The hours value (0-23)
     * @return List of 4 lamp states (RED for on, OFF for off)
     */
    fun convertFiveHourRow(hours: Int): List<LampState> {
        val lampsOn = hours / 5
        return List(4) { index ->
            if (index < lampsOn) LampState.RED else LampState.OFF
        }
    }

    /**
     * Converts hours to one-hour lamp row.
     *
     * Each lamp represents 1 hour (remainder after five-hour blocks).
     *
     * @param hours The hours value (0-23)
     * @return List of 4 lamp states (RED for on, OFF for off)
     */
    fun convertOneHourRow(hours: Int): List<LampState> {
        val lampsOn = hours % 5
        return List(4) { index ->
            if (index < lampsOn) LampState.RED else LampState.OFF
        }
    }

    /**
     * Converts minutes to five-minute lamp row.
     *
     * Each lamp represents 5 minutes. Lamps at positions 3, 6, and 9
     * (indices 2, 5, 8) are RED to mark quarter hours.
     *
     * @param minutes The minutes value (0-59)
     * @return List of 11 lamp states
     */
    fun convertFiveMinuteRow(minutes: Int): List<LampState> {
        val lampsOn = minutes / 5
        return List(11) { index ->
            when {
                index >= lampsOn -> LampState.OFF
                isQuarterPosition(index) -> LampState.RED
                else -> LampState.YELLOW
            }
        }
    }

    /**
     * Determines if a position represents a quarter hour.
     *
     * @param index Zero-based index (0-10)
     * @return true if position 3, 6, or 9 (indices 2, 5, 8)
     */
    private fun isQuarterPosition(index: Int): Boolean = (index + 1) % 3 == 0

    /**
     * Converts minutes to one-minute lamp row.
     *
     * Each lamp represents 1 minute (remainder after five-minute blocks).
     *
     * @param minutes The minutes value (0-59)
     * @return List of 4 lamp states (YELLOW for on, OFF for off)
     */
    fun convertOneMinuteRow(minutes: Int): List<LampState> {
        val lampsOn = minutes % 5
        return List(4) { index ->
            if (index < lampsOn) LampState.YELLOW else LampState.OFF
        }
    }

    /**
     * Converts a time to Berlin Clock format.
     *
     * @param hours Hours in 24-hour format (0-23)
     * @param minutes Minutes (0-59)
     * @param seconds Seconds (0-59)
     * @return BerlinTime representing the lamp states
     * @throws IllegalArgumentException if any parameter is out of valid range
     */
    fun convert(
        hours: Int,
        minutes: Int,
        seconds: Int,
    ): BerlinTime {
        require(hours in 0..23) { "Hours must be between 0 and 23, got $hours" }
        require(minutes in 0..59) { "Minutes must be between 0 and 59, got $minutes" }
        require(seconds in 0..59) { "Seconds must be between 0 and 59, got $seconds" }

        return BerlinTime(
            secondsLamp = convertSeconds(seconds),
            fiveHourRow = convertFiveHourRow(hours),
            oneHourRow = convertOneHourRow(hours),
            fiveMinuteRow = convertFiveMinuteRow(minutes),
            oneMinuteRow = convertOneMinuteRow(minutes),
        )
    }
}
