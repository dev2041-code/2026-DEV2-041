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
}
