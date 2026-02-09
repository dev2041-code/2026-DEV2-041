package com.bnp.berlinclock.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

class BerlinTimeTest {
    @Test
    fun `should create valid BerlinTime`() {
        val berlinTime =
            BerlinTime(
                secondsLamp = LampState.YELLOW,
                fiveHourRow = List(4) { LampState.OFF },
                oneHourRow = List(4) { LampState.OFF },
                fiveMinuteRow = List(11) { LampState.OFF },
                oneMinuteRow = List(4) { LampState.OFF },
            )

        assertEquals(LampState.YELLOW, berlinTime.secondsLamp)
        assertEquals(4, berlinTime.fiveHourRow.size)
        assertEquals(4, berlinTime.oneHourRow.size)
        assertEquals(11, berlinTime.fiveMinuteRow.size)
        assertEquals(4, berlinTime.oneMinuteRow.size)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should reject invalid fiveHourRow size`() {
        BerlinTime(
            secondsLamp = LampState.OFF,
            fiveHourRow = List(3) { LampState.OFF },
            oneHourRow = List(4) { LampState.OFF },
            fiveMinuteRow = List(11) { LampState.OFF },
            oneMinuteRow = List(4) { LampState.OFF },
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should reject invalid fiveMinuteRow size`() {
        BerlinTime(
            secondsLamp = LampState.OFF,
            fiveHourRow = List(4) { LampState.OFF },
            oneHourRow = List(4) { LampState.OFF },
            fiveMinuteRow = List(10) { LampState.OFF },
            oneMinuteRow = List(4) { LampState.OFF },
        )
    }

    @Test
    fun `should format toString correctly`() {
        val berlinTime =
            BerlinTime(
                secondsLamp = LampState.OFF,
                fiveHourRow = listOf(LampState.RED, LampState.RED, LampState.OFF, LampState.OFF),
                oneHourRow = listOf(LampState.RED, LampState.RED, LampState.RED, LampState.OFF),
                fiveMinuteRow =
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
                oneMinuteRow = listOf(LampState.YELLOW, LampState.YELLOW, LampState.OFF, LampState.OFF),
            )

        val expected = "O\nRROO\nRRRO\nYYROOOOOOOO\nYYOO"
        assertEquals(expected, berlinTime.toString())
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should reject invalid oneHourRow size`() {
        BerlinTime(
            secondsLamp = LampState.OFF,
            fiveHourRow = List(4) { LampState.OFF },
            oneHourRow = List(3) { LampState.OFF },
            fiveMinuteRow = List(11) { LampState.OFF },
            oneMinuteRow = List(4) { LampState.OFF },
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should reject invalid oneMinuteRow size`() {
        BerlinTime(
            secondsLamp = LampState.OFF,
            fiveHourRow = List(4) { LampState.OFF },
            oneHourRow = List(4) { LampState.OFF },
            fiveMinuteRow = List(11) { LampState.OFF },
            oneMinuteRow = List(5) { LampState.OFF },
        )
    }
}
