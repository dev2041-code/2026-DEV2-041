package com.bnp.berlinclock.presentation

import java.time.LocalTime

/**
 * Sealed hierarchy of all user actions for Berlin Clock.
 */
sealed class BerlinClockAction {

    data object IncrementHours : BerlinClockAction()

    data object DecrementHours : BerlinClockAction()

    data object IncrementMinutes : BerlinClockAction()

    data object DecrementMinutes : BerlinClockAction()

    data object IncrementSeconds : BerlinClockAction()

    data object DecrementSeconds : BerlinClockAction()

    data object SetCurrentTime : BerlinClockAction()

    /**
     * Programmatic time update (for testing/initialization)
     *
     * @property time The new time to set
     */
    data class UpdateTime(val time: LocalTime) : BerlinClockAction()
}
