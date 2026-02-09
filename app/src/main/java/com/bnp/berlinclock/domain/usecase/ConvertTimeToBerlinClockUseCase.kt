package com.bnp.berlinclock.domain.usecase

import com.bnp.berlinclock.domain.converter.BerlinClockConverter
import com.bnp.berlinclock.domain.model.BerlinTime
import javax.inject.Inject

/**
 * Use case for converting time to Berlin Clock format.
 */
class ConvertTimeToBerlinClockUseCase
    @Inject
    constructor(
        private val converter: BerlinClockConverter,
    ) {
        /**
         * Converts the given time to Berlin Clock format.
         *
         * @param hours Hours in 24-hour format (0-23)
         * @param minutes Minutes (0-59)
         * @param seconds Seconds (0-59)
         * @return BerlinTime object with all lamp states
         * @throws IllegalArgumentException if parameters are out of range
         */
        operator fun invoke(
            hours: Int,
            minutes: Int,
            seconds: Int,
        ): BerlinTime = converter.convert(hours, minutes, seconds)
    }
