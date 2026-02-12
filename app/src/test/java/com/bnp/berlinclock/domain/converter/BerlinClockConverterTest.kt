package com.bnp.berlinclock.domain.converter

import com.bnp.berlinclock.domain.model.LampState
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class BerlinClockConverterTest {
    private lateinit var converter: BerlinClockConverter

    @Before
    fun setup() {
        converter = BerlinClockConverter()
    }

    // Seconds Lamp Tests
    @Test
    fun `convert seconds - even second returns ON`() {
        val result = converter.convert(0, 0, 0)
        assertEquals(LampState.ON, result.secondsLamp)
    }

    @Test
    fun `convert seconds - odd second returns OFF`() {
        val result = converter.convert(0, 0, 1)
        assertEquals(LampState.OFF, result.secondsLamp)
    }

    // Five Hour Row Tests
    @Test
    fun `convert five hour row - 0 hours returns all OFF`() {
        val result = converter.convert(0, 0, 0)
        assertEquals(
            listOf(LampState.OFF, LampState.OFF, LampState.OFF, LampState.OFF),
            result.fiveHourRow,
        )
    }

    @Test
    fun `convert five hour row - 7 hours returns 1 ON`() {
        val result = converter.convert(7, 0, 0)
        assertEquals(
            listOf(LampState.ON, LampState.OFF, LampState.OFF, LampState.OFF),
            result.fiveHourRow,
        )
    }

    @Test
    fun `convert five hour row - 23 hours returns 4 ON`() {
        val result = converter.convert(23, 0, 0)
        assertEquals(
            listOf(LampState.ON, LampState.ON, LampState.ON, LampState.ON),
            result.fiveHourRow,
        )
    }

    // One Hour Row Tests
    @Test
    fun `convert one hour row - 0 hours returns all OFF`() {
        val result = converter.convert(0, 0, 0)
        assertEquals(
            listOf(LampState.OFF, LampState.OFF, LampState.OFF, LampState.OFF),
            result.oneHourRow,
        )
    }

    @Test
    fun `convert one hour row - 3 hours returns 3 ON`() {
        val result = converter.convert(3, 0, 0)
        assertEquals(
            listOf(LampState.ON, LampState.ON, LampState.ON, LampState.OFF),
            result.oneHourRow,
        )
    }

    @Test
    fun `convert one hour row - 13 hours returns 3 ON`() {
        val result = converter.convert(13, 0, 0)
        assertEquals(
            listOf(LampState.ON, LampState.ON, LampState.ON, LampState.OFF),
            result.oneHourRow,
        )
    }

    // Five Minute Row Tests
    @Test
    fun `convert five minute row - 0 minutes returns all OFF`() {
        val result = converter.convert(0, 0, 0)
        assertEquals(
            List(11) { LampState.OFF },
            result.fiveMinuteRow,
        )
    }

    @Test
    fun `convert five minute row - 17 minutes returns 3 ON`() {
        val result = converter.convert(0, 17, 0)
        val expected =
            listOf(
                LampState.ON,
                LampState.ON,
                LampState.ON,
                LampState.OFF,
                LampState.OFF,
                LampState.OFF,
                LampState.OFF,
                LampState.OFF,
                LampState.OFF,
                LampState.OFF,
                LampState.OFF,
            )
        assertEquals(expected, result.fiveMinuteRow)
    }

    @Test
    fun `convert five minute row - 32 minutes returns 6 ON`() {
        val result = converter.convert(0, 32, 0)
        val expected =
            listOf(
                LampState.ON,
                LampState.ON,
                LampState.ON,
                LampState.ON,
                LampState.ON,
                LampState.ON,
                LampState.OFF,
                LampState.OFF,
                LampState.OFF,
                LampState.OFF,
                LampState.OFF,
            )
        assertEquals(expected, result.fiveMinuteRow)
    }

    @Test
    fun `convert five minute row - 59 minutes returns 11 ON`() {
        val result = converter.convert(0, 59, 0)
        assertEquals(List(11) { LampState.ON }, result.fiveMinuteRow)
    }

    // One Minute Row Tests
    @Test
    fun `convert one minute row - 0 minutes returns all OFF`() {
        val result = converter.convert(0, 0, 0)
        assertEquals(
            listOf(LampState.OFF, LampState.OFF, LampState.OFF, LampState.OFF),
            result.oneMinuteRow,
        )
    }

    @Test
    fun `convert one minute row - 2 minutes returns 2 ON`() {
        val result = converter.convert(0, 2, 0)
        assertEquals(
            listOf(LampState.ON, LampState.ON, LampState.OFF, LampState.OFF),
            result.oneMinuteRow,
        )
    }

    @Test
    fun `convert one minute row - 17 minutes returns 2 ON`() {
        val result = converter.convert(0, 17, 0)
        assertEquals(
            listOf(LampState.ON, LampState.ON, LampState.OFF, LampState.OFF),
            result.oneMinuteRow,
        )
    }

    // Full Conversion Tests
    @Test
    fun `convert 00_00_00 returns correct Berlin time`() {
        val result = converter.convert(0, 0, 0)

        assertEquals(LampState.ON, result.secondsLamp)
        assertEquals(List(4) { LampState.OFF }, result.fiveHourRow)
        assertEquals(List(4) { LampState.OFF }, result.oneHourRow)
        assertEquals(List(11) { LampState.OFF }, result.fiveMinuteRow)
        assertEquals(List(4) { LampState.OFF }, result.oneMinuteRow)
    }

    @Test
    fun `convert 13_17_01 returns correct Berlin time`() {
        val result = converter.convert(13, 17, 1)

        assertEquals(LampState.OFF, result.secondsLamp)
        assertEquals(
            listOf(LampState.ON, LampState.ON, LampState.OFF, LampState.OFF),
            result.fiveHourRow,
        )
        assertEquals(
            listOf(LampState.ON, LampState.ON, LampState.ON, LampState.OFF),
            result.oneHourRow,
        )
        assertEquals(
            listOf(
                LampState.ON,
                LampState.ON,
                LampState.ON,
                LampState.OFF,
                LampState.OFF,
                LampState.OFF,
                LampState.OFF,
                LampState.OFF,
                LampState.OFF,
                LampState.OFF,
                LampState.OFF,
            ),
            result.fiveMinuteRow,
        )
        assertEquals(
            listOf(LampState.ON, LampState.ON, LampState.OFF, LampState.OFF),
            result.oneMinuteRow,
        )
    }

    @Test
    fun `convert 23_59_59 returns correct Berlin time`() {
        val result = converter.convert(23, 59, 59)

        assertEquals(LampState.OFF, result.secondsLamp)
        assertEquals(List(4) { LampState.ON }, result.fiveHourRow)
        assertEquals(
            listOf(LampState.ON, LampState.ON, LampState.ON, LampState.OFF),
            result.oneHourRow,
        )
        assertEquals(List(11) { LampState.ON }, result.fiveMinuteRow)
        assertEquals(List(4) { LampState.ON }, result.oneMinuteRow)
    }
}
