package com.bnp.berlinclock.domain.converter

import com.bnp.berlinclock.domain.model.BerlinTime
import com.bnp.berlinclock.domain.model.LampState

/**
 * Converts time to Berlin Clock format.
 */
class BerlinClockConverter {
    /**
     * Converts time to Berlin Clock representation.
     *
     * @param hours Hours (0-23)
     * @param minutes Minutes (0-59)
     * @param seconds Seconds (0-59)
     * @return Berlin Clock representation
     */
    fun convert(
        hours: Int,
        minutes: Int,
        seconds: Int,
    ): BerlinTime =
        BerlinTime(
            secondsLamp = convertSeconds(seconds),
            fiveHourRow = convertFiveHourRow(hours),
            oneHourRow = convertOneHourRow(hours),
            fiveMinuteRow = convertFiveMinuteRow(minutes),
            oneMinuteRow = convertOneMinuteRow(minutes),
        )

    /**
     * Converts seconds to lamp state.
     *
     * ON for even seconds, OFF for odd.
     */
    private fun convertSeconds(seconds: Int): LampState = if (seconds % 2 == 0) LampState.ON else LampState.OFF

    /**
     * Converts hours to five-hour row.
     *
     * Each ON lamp represents 5 hours.
     */
    private fun convertFiveHourRow(hours: Int): List<LampState> {
        val lampsOn = hours / 5
        return List(4) { index ->
            if (index < lampsOn) LampState.ON else LampState.OFF
        }
    }

    /**
     * Converts hours to one-hour row.
     *
     * Each ON lamp represents 1 hour (remainder after 5-hour blocks).
     */
    private fun convertOneHourRow(hours: Int): List<LampState> {
        val lampsOn = hours % 5
        return List(4) { index ->
            if (index < lampsOn) LampState.ON else LampState.OFF
        }
    }

    /**
     * Converts minutes to five-minute row.
     *
     * Each ON lamp represents 5 minutes.
     * Positions 3, 6, 9 (indices 2, 5, 8) mark quarters.
     */
    private fun convertFiveMinuteRow(minutes: Int): List<LampState> {
        val lampsOn = minutes / 5
        return List(11) { index ->
            if (index < lampsOn) LampState.ON else LampState.OFF
        }
    }

    /**
     * Converts minutes to one-minute row.
     *
     * Each ON lamp represents 1 minute (remainder after 5-minute blocks).
     */
    private fun convertOneMinuteRow(minutes: Int): List<LampState> {
        val lampsOn = minutes % 5
        return List(4) { index ->
            if (index < lampsOn) LampState.ON else LampState.OFF
        }
    }
}
