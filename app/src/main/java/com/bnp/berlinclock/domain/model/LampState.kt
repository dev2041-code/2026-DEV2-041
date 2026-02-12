package com.bnp.berlinclock.domain.model

/**
 * Binary state of a lamp.
 */
enum class LampState {
    /**
     * Lamp is illuminated.
     */
    ON,

    /**
     * Lamp is not illuminated.
     */
    OFF,
}

/**
 * Color of a lamp when illuminated.
 *
 */
enum class LampColor {
    /**
     * Red lamp (used for hours and quarter markers).
     */
    RED,

    /**
     * Yellow lamp (used for seconds and minutes).
     */
    YELLOW,
}
