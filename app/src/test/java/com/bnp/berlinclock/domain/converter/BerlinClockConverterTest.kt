package com.bnp.berlinclock.domain.converter

import com.bnp.berlinclock.domain.model.LampState
import org.junit.Before
import org.junit.Test
import org.junit.Assert.assertEquals

class BerlinClockConverterTest {
    private lateinit var converter: BerlinClockConverter

    @Before
    fun setUp() {
        converter = BerlinClockConverter()
    }

    // SECONDS LAMP - RED PHASE
    @Test
    fun `should return YELLOW for even seconds`() {
        assertEquals(LampState.YELLOW, converter.convertSeconds(0))
        assertEquals(LampState.YELLOW, converter.convertSeconds(2))
        assertEquals(LampState.YELLOW, converter.convertSeconds(58))
    }

    @Test
    fun `should return OFF for odd seconds`() {
        assertEquals(LampState.OFF, converter.convertSeconds(1))
        assertEquals(LampState.OFF, converter.convertSeconds(3))
        assertEquals(LampState.OFF, converter.convertSeconds(59))
    }
}
