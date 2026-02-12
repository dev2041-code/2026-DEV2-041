package com.bnp.berlinclock.domain.model

/**
 * Represents time in Berlin Clock format.
 *
 */
data class BerlinTime(
    val secondsLamp: LampState,
    val fiveHourRow: List<LampState>,
    val oneHourRow: List<LampState>,
    val fiveMinuteRow: List<LampState>,
    val oneMinuteRow: List<LampState>,
) {
    init {
        require(fiveHourRow.size == 4) { "Five hour row must have exactly 4 lamps" }
        require(oneHourRow.size == 4) { "One hour row must have exactly 4 lamps" }
        require(fiveMinuteRow.size == 11) { "Five minute row must have exactly 11 lamps" }
        require(oneMinuteRow.size == 4) { "One minute row must have exactly 4 lamps" }
    }

    override fun toString(): String =
        """
        ${secondsLamp.symbol}
        ${fiveHourRow.joinToString("") { it.symbol.toString() }}
        ${oneHourRow.joinToString("") { it.symbol.toString() }}
        ${fiveMinuteRow.joinToString("") { it.symbol.toString() }}
        ${oneMinuteRow.joinToString("") { it.symbol.toString() }}
        """.trimIndent()

    private val LampState.symbol: Char
        get() =
            when (this) {
                LampState.ON -> 'O'
                LampState.OFF -> '-'
            }
}
