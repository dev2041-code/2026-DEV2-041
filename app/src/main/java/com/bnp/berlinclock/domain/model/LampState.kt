package com.bnp.berlinclock.domain.model

/**
 * Represents the state of a lamp in the Berlin Clock.
 *
 * Type-safe representation avoiding raw string literals.
 */
enum class LampState(val symbol: Char) {
    /** Lamp is on and displays red */
    RED('R'),

    /** Lamp is on and displays yellow */
    YELLOW('Y'),

    /** Lamp is off */
    OFF('O'),
    ;

    override fun toString(): String = symbol.toString()

    companion object {
        fun fromChar(char: Char): LampState =
            entries.find { it.symbol == char }
                ?: throw IllegalArgumentException("Invalid lamp state: $char")
    }
}
