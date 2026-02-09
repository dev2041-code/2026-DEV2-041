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

    // FIVE MINUTE ROW
    @Test
    fun `should return all OFF for 0-4 minutes`() {
        val expected = List(11) { LampState.OFF }
        assertEquals(expected, converter.convertFiveMinuteRow(0))
        assertEquals(expected, converter.convertFiveMinuteRow(4))
    }

    @Test
    fun `should return 3 lamps for 17 minutes with quarter marker`() {
        val result = converter.convertFiveMinuteRow(17)
        assertEquals(11, result.size)
        assertEquals(LampState.YELLOW, result[0])
        assertEquals(LampState.YELLOW, result[1])
        assertEquals(LampState.RED, result[2]) // Quarter marker at position 3
        assertEquals(LampState.OFF, result[3])
    }

    @Test
    fun `should have RED at quarter positions for 32 minutes`() {
        val result = converter.convertFiveMinuteRow(32)
        assertEquals(LampState.RED, result[2]) // Position 3
        assertEquals(LampState.RED, result[5]) // Position 6
    }

    @Test
    fun `should return correct pattern for 59 minutes`() {
        val expected =
            listOf(
                LampState.YELLOW,
                LampState.YELLOW,
                LampState.RED, // Position 3
                LampState.YELLOW,
                LampState.YELLOW,
                LampState.RED, // Position 6
                LampState.YELLOW,
                LampState.YELLOW,
                LampState.RED, // Position 9
                LampState.YELLOW,
                LampState.YELLOW,
            )
        assertEquals(expected, converter.convertFiveMinuteRow(59))
    }

    // ONE MINUTE ROW
    @Test
    fun `should return all OFF for minutes divisible by 5`() {
        val expected = List(4) { LampState.OFF }
        assertEquals(expected, converter.convertOneMinuteRow(0))
        assertEquals(expected, converter.convertOneMinuteRow(15))
        assertEquals(expected, converter.convertOneMinuteRow(55))
    }

    @Test
    fun `should return 2 YELLOW for minutes ending in 2 or 7`() {
        val expected = listOf(LampState.YELLOW, LampState.YELLOW, LampState.OFF, LampState.OFF)
        assertEquals(expected, converter.convertOneMinuteRow(17))
        assertEquals(expected, converter.convertOneMinuteRow(32))
    }

    @Test
    fun `should return 4 YELLOW for minutes ending in 4 or 9`() {
        val expected = List(4) { LampState.YELLOW }
        assertEquals(expected, converter.convertOneMinuteRow(9))
        assertEquals(expected, converter.convertOneMinuteRow(59))
    }

    // FULL CONVERSION
    @Test
    fun `should convert 00h00m00s correctly`() {
        val berlinTime = converter.convert(0, 0, 0)

        assertEquals(LampState.YELLOW, berlinTime.secondsLamp)
        assertEquals(List(4) { LampState.OFF }, berlinTime.fiveHourRow)
        assertEquals(List(4) { LampState.OFF }, berlinTime.oneHourRow)
        assertEquals(List(11) { LampState.OFF }, berlinTime.fiveMinuteRow)
        assertEquals(List(4) { LampState.OFF }, berlinTime.oneMinuteRow)
    }

    @Test
    fun `should convert 13h17m01s correctly`() {
        val berlinTime = converter.convert(13, 17, 1)

        assertEquals(LampState.OFF, berlinTime.secondsLamp)
        assertEquals(
            listOf(LampState.RED, LampState.RED, LampState.OFF, LampState.OFF),
            berlinTime.fiveHourRow,
        )
        assertEquals(
            listOf(LampState.RED, LampState.RED, LampState.RED, LampState.OFF),
            berlinTime.oneHourRow,
        )
        assertEquals(
            listOf(
                LampState.YELLOW,
                LampState.YELLOW,
                LampState.RED,
                LampState.OFF,
                LampState.OFF,
                LampState.OFF,
                LampState.OFF,
                LampState.OFF,
                LampState.OFF,
                LampState.OFF,
                LampState.OFF,
            ),
            berlinTime.fiveMinuteRow,
        )
        assertEquals(
            listOf(LampState.YELLOW, LampState.YELLOW, LampState.OFF, LampState.OFF),
            berlinTime.oneMinuteRow,
        )
    }

    @Test
    fun `should convert 23h59m59s correctly`() {
        val berlinTime = converter.convert(23, 59, 59)

        assertEquals(LampState.OFF, berlinTime.secondsLamp)
        assertEquals(List(4) { LampState.RED }, berlinTime.fiveHourRow)
        assertEquals(
            listOf(LampState.RED, LampState.RED, LampState.RED, LampState.OFF),
            berlinTime.oneHourRow,
        )
        assertEquals(
            listOf(
                LampState.YELLOW,
                LampState.YELLOW,
                LampState.RED,
                LampState.YELLOW,
                LampState.YELLOW,
                LampState.RED,
                LampState.YELLOW,
                LampState.YELLOW,
                LampState.RED,
                LampState.YELLOW,
                LampState.YELLOW,
            ),
            berlinTime.fiveMinuteRow,
        )
        assertEquals(List(4) { LampState.YELLOW }, berlinTime.oneMinuteRow)
    }
}
