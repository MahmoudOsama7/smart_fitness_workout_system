package com.example.presentation.workOutHistory

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.domain.usecase.GetWorkoutHistoryList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class WorkoutHistoryViewModel @Inject constructor(
    private val getWorkoutHistoryList: GetWorkoutHistoryList
) : ViewModel() {

    private val _uiState = MutableStateFlow(WorkoutHistoryUiState())
    val uiState: StateFlow<WorkoutHistoryUiState> = _uiState.asStateFlow()

    init {
        getWorkoutHistory()
    }

    fun getWorkoutHistory() {
        viewModelScope.launch {
            getWorkoutHistoryList().collect { workouts ->
                _uiState.update { currentState ->
                    currentState.copy(workouts = workouts)
                }
            }
        }
    }
}