package com.example.presentation.workOutHistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetWeightUnitUseCase
import com.example.domain.usecase.GetWorkoutHistoryListUseCase
import com.example.domain.usecase.SyncPendingWorkoutsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutHistoryViewModel @Inject constructor(
    private val getWorkoutHistoryListUseCase: GetWorkoutHistoryListUseCase,
    private val syncPendingWorkoutsUseCase: SyncPendingWorkoutsUseCase,
    private val getWeightUnitUseCase: GetWeightUnitUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(WorkoutHistoryUiState())
    val uiState: StateFlow<WorkoutHistoryUiState> = _uiState.asStateFlow()

    private val _apiState = MutableSharedFlow<WorkoutHistoryApiState>(extraBufferCapacity = 1)

    init {
        loadWeightUnit()
    }

    private fun loadWeightUnit() {
        val unit = getWeightUnitUseCase()
        _uiState.update { it.copy(weightUnit = unit) }
    }

    fun onAction(action: WorkoutHistoryAction) {
        when (action) {
            is WorkoutHistoryAction.GetWorkoutHistory -> getWorkoutHistory()
            is WorkoutHistoryAction.SyncWorkoutList -> syncPendingWorkouts()
        }
    }

    fun getWorkoutHistory() {
        viewModelScope.launch {
            getWorkoutHistoryListUseCase().collect { workouts ->
                _uiState.update { currentState ->
                    currentState.copy(workouts = workouts)
                }
            }
        }
    }

    fun syncPendingWorkouts() {
        viewModelScope.launch {
            _uiState.update {
                it.copy(
                    isLoading = true
                )
            }
            val result = syncPendingWorkoutsUseCase()
            result.onSuccess { syncedWorkouts ->
                _uiState.update {
                    it.copy(
                        workouts = syncedWorkouts
                    )
                }
            }.onFailure { exception ->
                _apiState.emit(WorkoutHistoryApiState.FailureState(exception.message.orEmpty()))
            }
        }.invokeOnCompletion {
            _uiState.update {
                it.copy(
                    isLoading = false
                )
            }
        }
    }
}