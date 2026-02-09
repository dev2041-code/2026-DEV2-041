package com.bnp.berlinclock.presentation

import com.bnp.berlinclock.domain.model.BerlinTime
import java.time.LocalTime

/**
 * Represents the state of the Berlin Clock UI.
 * @property currentTime The time being displayed (for controls)
 * @property berlinTime The converted Berlin Clock representation
 */
data class BerlinClockUiState(
    val currentTime: LocalTime = LocalTime.now(),
    val berlinTime: BerlinTime? = null,
)
