package com.bnp.berlinclock.domain.usecase

import com.bnp.berlinclock.domain.converter.BerlinClockConverter
import com.bnp.berlinclock.domain.model.BerlinClockError
import com.bnp.berlinclock.domain.model.BerlinTime
import com.bnp.berlinclock.domain.model.Result
import javax.inject.Inject

/**
 * Use case for converting time to Berlin Clock format.
 *
 */
class ConvertTimeToBerlinClockUseCase
    @Inject
    constructor(
        private val converter: BerlinClockConverter,
    ) {
        /**
         * Converts time to Berlin Clock with validation.
         *
         * @param hours Hours in 24-hour format (0-23)
         * @param minutes Minutes (0-59)
         * @param seconds Seconds (0-59)
         * @return Result containing BerlinTime or error
         */
        operator fun invoke(
            hours: Int,
            minutes: Int,
            seconds: Int,
        ): Result<BerlinTime> {
            // Validate all inputs
            val error =
                when {
                    hours !in 0..23 -> BerlinClockError.InvalidHours(hours)
                    minutes !in 0..59 -> BerlinClockError.InvalidMinutes(minutes)
                    seconds !in 0..59 -> BerlinClockError.InvalidSeconds(seconds)
                    else -> null
                }

            // Return error if validation failed
            if (error != null) {
                return Result.Failure(error)
            }

            // All valid - convert and return success
            val berlinTime = converter.convert(hours, minutes, seconds)
            return Result.Success(berlinTime)
        }
    }
