package com.bnp.berlinclock.presentation

import java.time.LocalTime

/**
 * Sealed hierarchy of all user actions for Berlin Clock.
 *
 */
sealed class BerlinClockAction {
    /**
     * @property unit Which time unit to adjust
     * @property delta Amount to adjust (+1 for increment, -1 for decrement)
     */
    data class AdjustTime(
        val unit: TimeUnit,
        val delta: Long,
    ) : BerlinClockAction()

    /**
     * Update time to specified value.
     *
     * @property time The new time to set
     */
    data class UpdateTime(val time: LocalTime) : BerlinClockAction()

    /**
     * Time units that can be adjusted.
     */
    enum class TimeUnit {
        HOURS,
        MINUTES,
        SECONDS,
    }
}
