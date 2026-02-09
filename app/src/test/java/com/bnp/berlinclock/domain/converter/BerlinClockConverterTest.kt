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

    // SECONDS LAMP
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
    // FIVE HOUR ROW
    @Test
    fun `should return all OFF for 0-4 hours in five hour row`() {
        val expected = List(4) { LampState.OFF }
        assertEquals(expected, converter.convertFiveHourRow(0))
        assertEquals(expected, converter.convertFiveHourRow(4))
    }

    @Test
    fun `should return 1 RED for 5-9 hours`() {
        val expected = listOf(LampState.RED, LampState.OFF, LampState.OFF, LampState.OFF)
        assertEquals(expected, converter.convertFiveHourRow(5))
        assertEquals(expected, converter.convertFiveHourRow(9))
    }

    @Test
    fun `should return 4 RED for 20-23 hours`() {
        val expected = List(4) { LampState.RED }
        assertEquals(expected, converter.convertFiveHourRow(20))
        assertEquals(expected, converter.convertFiveHourRow(23))
    }

    // ONE HOUR ROW
    @Test
    fun `should return all OFF for hours divisible by 5`() {
        val expected = List(4) { LampState.OFF }
        assertEquals(expected, converter.convertOneHourRow(0))
        assertEquals(expected, converter.convertOneHourRow(10))
        assertEquals(expected, converter.convertOneHourRow(20))
    }

    @Test
    fun `should return 3 RED for 13 hours`() {
        val expected = listOf(LampState.RED, LampState.RED, LampState.RED, LampState.OFF)
        assertEquals(expected, converter.convertOneHourRow(13))
    }

    @Test
    fun `should return 4 RED for hours ending in 9`() {
        val expected = List(4) { LampState.RED }
        assertEquals(expected, converter.convertOneHourRow(9))
        assertEquals(expected, converter.convertOneHourRow(19))
    }
}
