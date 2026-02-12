package com.bnp.berlinclock.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

class LampStateTest {
    @Test
    fun `LampState has ON and OFF values`() {
        assertEquals(LampState.ON, LampState.ON)
        assertEquals(LampState.OFF, LampState.OFF)
    }

    @Test
    fun `LampColor has RED and YELLOW values`() {
        assertEquals(LampColor.RED, LampColor.RED)
        assertEquals(LampColor.YELLOW, LampColor.YELLOW)
    }
}
