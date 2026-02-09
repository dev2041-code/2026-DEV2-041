package com.bnp.berlinclock.domain.model

/**
 * Represents the complete Berlin Clock display state.
 *
 * All lamp rows are represented as lists of [LampState] for type safety.
 *
 * @property secondsLamp Top lamp that blinks every 2 seconds
 * @property fiveHourRow 4 lamps, each representing 5 hours
 * @property oneHourRow 4 lamps, each representing 1 hour
 * @property fiveMinuteRow 11 lamps, each representing 5 minutes (positions 3,6,9 are red for quarters)
 * @property oneMinuteRow 4 lamps, each representing 1 minute
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

    /**
     * Converts to traditional string representation for compatibility.
     */
    override fun toString(): String =
        buildString {
            append(secondsLamp)
            append('\n')
            append(fiveHourRow.joinToString(""))
            append('\n')
            append(oneHourRow.joinToString(""))
            append('\n')
            append(fiveMinuteRow.joinToString(""))
            append('\n')
            append(oneMinuteRow.joinToString(""))
        }
}
