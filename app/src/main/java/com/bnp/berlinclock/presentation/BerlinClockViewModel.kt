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
         * @param action The user action to handle
         */
        fun onAction(action: BerlinClockAction) {
            when (action) {
                is BerlinClockAction.IncrementHours -> {
                    val newTime = _uiState.value.currentTime.plusHours(1)
                    updateTime(newTime)
                }
                is BerlinClockAction.DecrementHours -> {
                    val newTime = _uiState.value.currentTime.minusHours(1)
                    updateTime(newTime)
                }
                is BerlinClockAction.IncrementMinutes -> {
                    val newTime = _uiState.value.currentTime.plusMinutes(1)
                    updateTime(newTime)
                }
                is BerlinClockAction.DecrementMinutes -> {
                    val newTime = _uiState.value.currentTime.minusMinutes(1)
                    updateTime(newTime)
                }
                is BerlinClockAction.IncrementSeconds -> {
                    val newTime = _uiState.value.currentTime.plusSeconds(1)
                    updateTime(newTime)
                }
                is BerlinClockAction.DecrementSeconds -> {
                    val newTime = _uiState.value.currentTime.minusSeconds(1)
                    updateTime(newTime)
                }
                is BerlinClockAction.SetCurrentTime -> {
                    updateTime(LocalTime.now())
                }
                is BerlinClockAction.UpdateTime -> {
                    updateTime(action.time)
                }
            }
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
