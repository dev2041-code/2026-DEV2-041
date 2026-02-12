package com.bnp.berlinclock.presentation

import androidx.lifecycle.ViewModel
import com.bnp.berlinclock.domain.model.Result
import com.bnp.berlinclock.domain.usecase.ConvertTimeToBerlinClockUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import java.time.LocalTime
import javax.inject.Inject

/**
 * ViewModel for Berlin Clock screen.
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
            updateBerlinTime()
        }

        /**
         * Handles all user actions.
         */
        fun onAction(action: BerlinClockAction) {
            when (action) {
                is BerlinClockAction.AdjustTime -> adjustTime(action.unit, action.delta)
                is BerlinClockAction.UpdateTime -> updateTime(action.time)
            }
        }

        /**
         * Adjusts current time by delta for specified unit.
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
         */
        private fun updateTime(time: LocalTime) {
            _uiState.update { it.copy(currentTime = time) }
            updateBerlinTime()
        }

        /**
         * Converts current time to Berlin Clock format.
         *
         */
        private fun updateBerlinTime() {
            val time = _uiState.value.currentTime

            when (val result = convertTimeToBerlinClock(time.hour, time.minute, time.second)) {
                is Result.Success -> {
                    _uiState.update {
                        it.copy(
                            berlinTime = result.value,
                            error = null,
                        )
                    }
                }
                is Result.Failure -> {
                    _uiState.update {
                        it.copy(
                            berlinTime = null,
                            error = result.error.toString(),
                        )
                    }
                }
            }
        }
    }
