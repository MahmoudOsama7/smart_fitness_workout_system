package com.example.presentation.workOutView

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.model.WeightConverter
import com.example.domain.state.ActiveSetState
import com.example.domain.state.PausedState
import com.example.domain.state.ReadyState
import com.example.domain.state.RestTimerState
import com.example.domain.state.WorkoutCompletedState
import com.example.domain.usecase.GetWeightUnitUseCase
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
    private val observeWorkoutStateUseCase: ObserveWorkoutStateUseCase,
    private val getWeightUnitUseCase: GetWeightUnitUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(WorkoutUiState())
    val uiState: StateFlow<WorkoutUiState> = _uiState.asStateFlow()

    init {
        getWorkoutStatus()
    }

    private fun getWorkoutStatus() {
        viewModelScope.launch {
            observeWorkoutStateUseCase().collect { domainState ->
                val selectedUnit = getWeightUnitUseCase()

                _uiState.update { currentUi ->
                    when (domainState) {
                        is ReadyState -> currentUi.copy(
                            workoutState = WorkoutStateType.READY
                        )

                        is ActiveSetState -> {
                            val formattedWeight = WeightConverter.formatWeight(
                                weightInKg = domainState.session.weightKg,
                                unit = selectedUnit
                            )
                            currentUi.copy(
                                workoutState = WorkoutStateType.ACTIVE_SET,
                                exerciseName = domainState.session.exerciseName,
                                currentSet = domainState.session.currentSet,
                                totalSets = domainState.session.totalSets,
                                completedSets = domainState.session.completedSets,
                                currentWeight = formattedWeight
                            )
                        }

                        is RestTimerState -> currentUi.copy(
                            workoutState = WorkoutStateType.RESTING,
                            remainingRestSeconds = domainState.remainingSeconds
                        )

                        is PausedState -> currentUi.copy(
                            workoutState = WorkoutStateType.PAUSED
                        )

                        is WorkoutCompletedState -> {
                            val formattedWeight = WeightConverter.formatWeight(
                                weightInKg = domainState.session.weightKg,
                                unit = selectedUnit
                            )
                            currentUi.copy(
                                workoutState = WorkoutStateType.COMPLETED,
                                completedSets = domainState.session.completedSets,
                                exerciseName = domainState.session.exerciseName,
                                currentWeight = formattedWeight,
                                elapsedTimeSeconds = domainState.session.elapsedTimeSeconds
                            )
                        }

                        else -> currentUi
                    }
                }
            }
        }
    }

    fun onAction(action: WorkoutAction) {
        if (action is WorkoutAction.RefreshSettings) {
            val selectedUnit = getWeightUnitUseCase()
            _uiState.update { currentUi ->
                val formattedWeight = WeightConverter.formatWeight(
                    weightInKg = 60.0,
                    unit = selectedUnit
                )
                currentUi.copy(currentWeight = formattedWeight)
            }
        } else {
            processWorkoutActionUseCase(action.toDomain())
        }
    }
}