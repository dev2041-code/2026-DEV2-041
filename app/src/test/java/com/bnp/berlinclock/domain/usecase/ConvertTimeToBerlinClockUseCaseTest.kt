package com.bnp.berlinclock.domain.usecase

import com.bnp.berlinclock.domain.converter.BerlinClockConverter
import com.bnp.berlinclock.domain.model.BerlinClockError
import com.bnp.berlinclock.domain.model.Result
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test

class ConvertTimeToBerlinClockUseCaseTest {
    private lateinit var useCase: ConvertTimeToBerlinClockUseCase

    @Before
    fun setup() {
        useCase = ConvertTimeToBerlinClockUseCase(BerlinClockConverter())
    }

    // Valid Input Tests
    @Test
    fun `invoke with valid time returns Success`() {
        val result = useCase(13, 17, 1)
        assertTrue(result is Result.Success)
    }

    // Invalid Hours Tests
    @Test
    fun `invoke with hours -1 returns Failure with InvalidHours error`() {
        val result = useCase(-1, 0, 0)
        assertTrue(result is Result.Failure)
        assertEquals(
            BerlinClockError.InvalidHours(-1),
            (result as Result.Failure).error,
        )
    }

    @Test
    fun `invoke with hours 24 returns Failure with InvalidHours error`() {
        val result = useCase(24, 0, 0)
        assertTrue(result is Result.Failure)
        assertEquals(
            BerlinClockError.InvalidHours(24),
            (result as Result.Failure).error,
        )
    }

    // Invalid Minutes Tests
    @Test
    fun `invoke with minutes -1 returns Failure with InvalidMinutes error`() {
        val result = useCase(0, -1, 0)
        assertTrue(result is Result.Failure)
        assertEquals(
            BerlinClockError.InvalidMinutes(-1),
            (result as Result.Failure).error,
        )
    }

    @Test
    fun `invoke with minutes 60 returns Failure with InvalidMinutes error`() {
        val result = useCase(0, 60, 0)
        assertTrue(result is Result.Failure)
        assertEquals(
            BerlinClockError.InvalidMinutes(60),
            (result as Result.Failure).error,
        )
    }

    // Invalid Seconds Tests
    @Test
    fun `invoke with seconds -1 returns Failure with InvalidSeconds error`() {
        val result = useCase(0, 0, -1)
        assertTrue(result is Result.Failure)
        assertEquals(
            BerlinClockError.InvalidSeconds(-1),
            (result as Result.Failure).error,
        )
    }

    @Test
    fun `invoke with seconds 60 returns Failure with InvalidSeconds error`() {
        val result = useCase(0, 0, 60)
        assertTrue(result is Result.Failure)
        assertEquals(
            BerlinClockError.InvalidSeconds(60),
            (result as Result.Failure).error,
        )
    }

    // Boundary Tests
    @Test
    fun `invoke with hours 0 returns Success`() {
        val result = useCase(0, 0, 0)
        assertTrue(result is Result.Success)
    }

    @Test
    fun `invoke with hours 23 returns Success`() {
        val result = useCase(23, 0, 0)
        assertTrue(result is Result.Success)
    }

    @Test
    fun `invoke with minutes 0 returns Success`() {
        val result = useCase(0, 0, 0)
        assertTrue(result is Result.Success)
    }

    @Test
    fun `invoke with minutes 59 returns Success`() {
        val result = useCase(0, 59, 0)
        assertTrue(result is Result.Success)
    }

    @Test
    fun `invoke with seconds 0 returns Success`() {
        val result = useCase(0, 0, 0)
        assertTrue(result is Result.Success)
    }

    @Test
    fun `invoke with seconds 59 returns Success`() {
        val result = useCase(0, 0, 59)
        assertTrue(result is Result.Success)
    }
}
