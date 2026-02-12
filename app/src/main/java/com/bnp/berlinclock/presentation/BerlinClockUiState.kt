package com.bnp.berlinclock.presentation

import com.bnp.berlinclock.domain.model.BerlinTime
import java.time.LocalTime

/**
 * UI state for Berlin Clock screen.
 *
 */
data class BerlinClockUiState(
    val currentTime: LocalTime = LocalTime.now(),
    val berlinTime: BerlinTime? = null,
    val error: String? = null,
)
