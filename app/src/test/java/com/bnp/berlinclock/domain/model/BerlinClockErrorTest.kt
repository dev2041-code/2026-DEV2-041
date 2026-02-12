package com.bnp.berlinclock.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

class BerlinClockErrorTest {
    @Test
    fun `InvalidHours error contains hours value`() {
        val error = BerlinClockError.InvalidHours(25)
        assertEquals(25, error.hours)
    }

    @Test
    fun `InvalidHours toString returns readable message`() {
        val error = BerlinClockError.InvalidHours(25)
        assertEquals("Hours must be between 0 and 23, got 25", error.toString())
    }

    @Test
    fun `InvalidMinutes error contains minutes value`() {
        val error = BerlinClockError.InvalidMinutes(60)
        assertEquals(60, error.minutes)
    }

    @Test
    fun `InvalidMinutes toString returns readable message`() {
        val error = BerlinClockError.InvalidMinutes(60)
        assertEquals("Minutes must be between 0 and 59, got 60", error.toString())
    }

    @Test
    fun `InvalidSeconds error contains seconds value`() {
        val error = BerlinClockError.InvalidSeconds(-1)
        assertEquals(-1, error.seconds)
    }

    @Test
    fun `InvalidSeconds toString returns readable message`() {
        val error = BerlinClockError.InvalidSeconds(-1)
        assertEquals("Seconds must be between 0 and 59, got -1", error.toString())
    }
}
