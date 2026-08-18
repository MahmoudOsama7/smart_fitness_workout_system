package com.example.presentation.workOutHistory

import com.example.domain.model.WeightUnit
import com.example.domain.model.WorkoutSession

data class WorkoutHistoryUiState(
    val isLoading: Boolean = false,
    val workouts: List<WorkoutSession> = emptyList(),
    val weightUnit: WeightUnit = WeightUnit.KG
)


sealed interface WorkoutHistoryAction {
    data object GetWorkoutHistory : WorkoutHistoryAction

    data object SyncWorkoutList : WorkoutHistoryAction
}

sealed interface WorkoutHistoryApiState {

    data class FailureState(val errorMessage: String) : WorkoutHistoryApiState
}

data class WorkoutHistoryNavigator(
    val onBackPressed: () -> Unit
)
