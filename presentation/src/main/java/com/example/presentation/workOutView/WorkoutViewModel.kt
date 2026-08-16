package com.example.presentation.workOutView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor() : ViewModel() {

    private val _uiState = MutableStateFlow(
        WorkoutUiState()
    )
    val uiState = _uiState.asStateFlow()

    private val _actionState = MutableSharedFlow<WorkoutActionState>()

    fun onAction(action: WorkoutAction) {
        when (action) {
            WorkoutAction.StartWorkout -> startWorkout()

            WorkoutAction.CompleteSet -> completeSet()

            WorkoutAction.SkipRest -> skipRest()

            WorkoutAction.PauseWorkout -> pauseWorkout()

            WorkoutAction.ResumeWorkout -> resumeWorkout()

            WorkoutAction.EndWorkout -> endWorkout()

            WorkoutAction.HistoryClick -> openHistory()

            WorkoutAction.SettingsClick -> openSettings()
        }
    }

    private fun startWorkout() {
        _uiState.update {
            it.copy(
                workoutState = WorkoutStateType.ACTIVE_SET
            )
        }
    }

    private fun completeSet() {
        val currentState = _uiState.value

        if (currentState.currentSet >= currentState.totalSets) {
            endWorkout()
            return
        }

        _uiState.update {
            it.copy(
                workoutState = WorkoutStateType.RESTING,
                completedSets = it.completedSets + 1,
                remainingRestSeconds = 60
            )
        }
    }

    private fun skipRest() {
        _uiState.update {
            it.copy(
                workoutState = WorkoutStateType.ACTIVE_SET,
                currentSet = it.currentSet + 1
            )
        }
    }

    private fun pauseWorkout() {
        _uiState.update {
            it.copy(
                workoutState = WorkoutStateType.PAUSED
            )
        }
    }

    private fun resumeWorkout() {
        _uiState.update {
            it.copy(
                workoutState = WorkoutStateType.ACTIVE_SET
            )
        }
    }

    private fun endWorkout() {
        _uiState.update {
            it.copy(
                workoutState = WorkoutStateType.COMPLETED
            )
        }
    }

    private fun openHistory() {
        viewModelScope.launch {
            _actionState.emit(
                WorkoutActionState.NavigateToHistory
            )
        }
    }

    private fun openSettings() {
        viewModelScope.launch {
            _actionState.emit(
                WorkoutActionState.NavigateToSettings
            )
        }
    }
}

sealed interface WorkoutActionState {

    data object NavigateToHistory : WorkoutActionState

    data object NavigateToSettings : WorkoutActionState
}