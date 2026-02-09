package com.bnp.berlinclock.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bnp.berlinclock.domain.usecase.ConvertTimeToBerlinClockUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.time.LocalTime
import javax.inject.Inject

/**
 * ViewModel for Berlin Clock screen.
 * @property convertTimeToBerlinClock Use case for domain logic
 */
@HiltViewModel
class BerlinClockViewModel
    @Inject
    constructor(
        private val convertTimeToBerlinClock: ConvertTimeToBerlinClockUseCase,
    ) : ViewModel() {
        private val _uiState = MutableStateFlow(BerlinClockUiState())

        /**
         * UI state exposed as immutable StateFlow.
         */
        val uiState: StateFlow<BerlinClockUiState> = _uiState.asStateFlow()

        init {
            // Initialize with current Berlin time
            updateBerlinTime()
        }

        /**
         * Handles all user actions.
         *
         * @param action The user action to handle
         */
        fun onAction(action: BerlinClockAction) {
            when (action) {
                is BerlinClockAction.AdjustTime -> adjustTime(action.unit, action.delta)
                is BerlinClockAction.UpdateTime -> updateTime(action.time)
            }
        }

        /**
         * Adjusts current time using provided transformation.
         * @param Function to transform LocalTime
         */
        private fun adjustTime(
            unit: BerlinClockAction.TimeUnit,
            delta: Long,
        ) {
            val currentTime = _uiState.value.currentTime
            val newTime =
                when (unit) {
                    BerlinClockAction.TimeUnit.HOURS -> currentTime.plusHours(delta)
                    BerlinClockAction.TimeUnit.MINUTES -> currentTime.plusMinutes(delta)
                    BerlinClockAction.TimeUnit.SECONDS -> currentTime.plusSeconds(delta)
                }
            updateTime(newTime)
        }

        /**
         * Updates the current time and triggers Berlin time conversion.
         *
         * @param time The new time to set
         */
        private fun updateTime(time: LocalTime) {
            _uiState.update { it.copy(currentTime = time) }
            updateBerlinTime()
        }

        /**
         * Converts current time to Berlin Clock format.
         * Updates state atomically to prevent race conditions.
         */
        private fun updateBerlinTime() {
            viewModelScope.launch {
                val time = _uiState.value.currentTime
                val berlinTime =
                    convertTimeToBerlinClock(
                        time.hour,
                        time.minute,
                        time.second,
                    )
                _uiState.update { it.copy(berlinTime = berlinTime) }
            }
        }
    }
