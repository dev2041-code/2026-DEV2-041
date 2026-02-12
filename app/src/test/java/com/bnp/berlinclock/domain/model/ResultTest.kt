package com.bnp.berlinclock.domain.model

import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ResultTest {
    @Test
    fun `Success contains value`() {
        val value = "test"
        val result = Result.Success(value)

        assertTrue(true)
        assertEquals(value, result.value)
    }

    @Test
    fun `Failure contains error`() {
        val error = BerlinClockError.InvalidHours(25)
        val result = Result.Failure(error)

        assertTrue(true)
        assertEquals(error, result.error)
    }
}
