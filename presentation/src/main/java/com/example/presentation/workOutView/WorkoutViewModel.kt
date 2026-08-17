package com.example.presentation.workOutView

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.state.ActiveSetState
import com.example.domain.state.PausedState
import com.example.domain.state.ReadyState
import com.example.domain.state.RestTimerState
import com.example.domain.state.WorkoutCompletedState
import com.example.domain.usecase.ObserveWorkoutStateUseCase
import com.example.domain.usecase.ProcessWorkoutActionUseCase
import com.example.presentation.mapper.toDomain
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutViewModel @Inject constructor(
    private val processWorkoutActionUseCase: ProcessWorkoutActionUseCase,
    private val observeWorkoutStateUseCase: ObserveWorkoutStateUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(WorkoutUiState())
    val uiState: StateFlow<WorkoutUiState> = _uiState.asStateFlow()


    init {
        getWorkoutStatus()
    }

    private fun getWorkoutStatus() {
        viewModelScope.launch {
            observeWorkoutStateUseCase().collect { domainState ->
                _uiState.update { currentUi ->
                    when (domainState) {
                        is ReadyState -> currentUi.copy(
                            workoutState = WorkoutStateType.READY,
                            currentSet = 1,
                            completedSets = 0,
                            exerciseName = "test",
                            totalSets = 5,
                            currentWeight = "60kg"
                        )

                        is ActiveSetState -> currentUi.copy(
                            workoutState = WorkoutStateType.ACTIVE_SET,
                            currentSet = observeWorkoutStateUseCase.getCurrentSet(),
                            completedSets = observeWorkoutStateUseCase.getCompletedSets()
                        )

                        is RestTimerState -> currentUi.copy(
                            workoutState = WorkoutStateType.RESTING,
                            remainingRestSeconds = domainState.remainingSeconds
                        )

                        is PausedState -> currentUi.copy(
                            workoutState = WorkoutStateType.PAUSED
                        )

                        is WorkoutCompletedState -> currentUi.copy(
                            workoutState = WorkoutStateType.COMPLETED,
                            completedSets = observeWorkoutStateUseCase.getCompletedSets()
                        )

                        else -> currentUi
                    }
                }
            }
        }
    }

    fun onAction(action: WorkoutAction) {
        when (action) {
            WorkoutAction.CompleteSet -> Log.d("debugging", "1: ")
            WorkoutAction.EndWorkout -> Log.d("debugging", "2: ")
            WorkoutAction.PauseWorkout -> Log.d("debugging", "3: ")
            WorkoutAction.ResumeWorkout -> Log.d("debugging", "4: ")
            WorkoutAction.SkipRest -> Log.d("debugging", "5: ")
            WorkoutAction.StartWorkout -> processWorkoutActionUseCase(action.toDomain())
        }
    }
}