package com.bnp.berlinclock.domain.converter

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
}
