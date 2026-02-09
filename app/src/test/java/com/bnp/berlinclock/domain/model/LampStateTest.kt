package com.bnp.berlinclock.domain.model

import org.junit.Assert.assertEquals
import org.junit.Test

class LampStateTest {
    @Test
    fun `should convert RED char to LampState`() {
        assertEquals(LampState.RED, LampState.fromChar('R'))
    }

    @Test
    fun `should convert YELLOW char to LampState`() {
        assertEquals(LampState.YELLOW, LampState.fromChar('Y'))
    }

    @Test
    fun `should convert OFF char to LampState`() {
        assertEquals(LampState.OFF, LampState.fromChar('O'))
    }

    @Test(expected = IllegalArgumentException::class)
    fun `should throw exception for invalid char`() {
        LampState.fromChar('X')
    }

    @Test
    fun `should convert to string correctly`() {
        assertEquals("R", LampState.RED.toString())
        assertEquals("Y", LampState.YELLOW.toString())
        assertEquals("O", LampState.OFF.toString())
    }
}
