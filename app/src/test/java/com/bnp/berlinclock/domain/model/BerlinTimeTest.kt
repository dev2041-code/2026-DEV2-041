package com.bnp.berlinclock.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

class BerlinTimeTest {
    @Test
    fun `BerlinTime with valid rows succeeds`() {
        val berlinTime =
            BerlinTime(
                secondsLamp = LampState.ON,
                fiveHourRow = List(4) { LampState.OFF },
                oneHourRow = List(4) { LampState.OFF },
                fiveMinuteRow = List(11) { LampState.OFF },
                oneMinuteRow = List(4) { LampState.OFF },
            )

        assertEquals(LampState.ON, berlinTime.secondsLamp)
    }

    @Test(expected = IllegalArgumentException::class)
    fun `BerlinTime with invalid fiveHourRow size throws exception`() {
        BerlinTime(
            secondsLamp = LampState.ON,
            fiveHourRow = List(3) { LampState.OFF },
            oneHourRow = List(4) { LampState.OFF },
            fiveMinuteRow = List(11) { LampState.OFF },
            oneMinuteRow = List(4) { LampState.OFF },
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `BerlinTime with invalid oneHourRow size throws exception`() {
        BerlinTime(
            secondsLamp = LampState.ON,
            fiveHourRow = List(4) { LampState.OFF },
            oneHourRow = List(5) { LampState.OFF },
            fiveMinuteRow = List(11) { LampState.OFF },
            oneMinuteRow = List(4) { LampState.OFF },
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `BerlinTime with invalid fiveMinuteRow size throws exception`() {
        BerlinTime(
            secondsLamp = LampState.ON,
            fiveHourRow = List(4) { LampState.OFF },
            oneHourRow = List(4) { LampState.OFF },
            fiveMinuteRow = List(10) { LampState.OFF },
            oneMinuteRow = List(4) { LampState.OFF },
        )
    }

    @Test(expected = IllegalArgumentException::class)
    fun `BerlinTime with invalid oneMinuteRow size throws exception`() {
        BerlinTime(
            secondsLamp = LampState.ON,
            fiveHourRow = List(4) { LampState.OFF },
            oneHourRow = List(4) { LampState.OFF },
            fiveMinuteRow = List(11) { LampState.OFF },
            oneMinuteRow = List(3) { LampState.OFF },
        )
    }

    @Test
    fun `toString returns correct format`() {
        val berlinTime =
            BerlinTime(
                secondsLamp = LampState.ON,
                fiveHourRow = listOf(LampState.ON, LampState.OFF, LampState.OFF, LampState.OFF),
                oneHourRow = List(4) { LampState.OFF },
                fiveMinuteRow = List(11) { LampState.OFF },
                oneMinuteRow = List(4) { LampState.OFF },
            )

        val expected =
            """
            O
            O---
            ----
            -----------
            ----
            """.trimIndent()

        assertEquals(expected, berlinTime.toString())
    }
}
