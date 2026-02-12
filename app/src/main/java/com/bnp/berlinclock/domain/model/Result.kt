package com.bnp.berlinclock.domain.model

/**
 * Sealed class representing the result of an operation.
 */
sealed class Result<out T> {
    /**
     * Success result containing a value.
     */
    data class Success<T>(val value: T) : Result<T>()

    /**
     * Failure result containing an error.
     */
    data class Failure(val error: BerlinClockError) : Result<Nothing>()
}

/**
 * Domain errors for Berlin Clock operations.
 */
sealed class BerlinClockError {
    data class InvalidHours(val hours: Int) : BerlinClockError() {
        override fun toString() = "Hours must be between 0 and 23, got $hours"
    }

    data class InvalidMinutes(val minutes: Int) : BerlinClockError() {
        override fun toString() = "Minutes must be between 0 and 59, got $minutes"
    }

    data class InvalidSeconds(val seconds: Int) : BerlinClockError() {
        override fun toString() = "Seconds must be between 0 and 59, got $seconds"
    }
}
