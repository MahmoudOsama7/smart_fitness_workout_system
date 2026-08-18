package com.example.presentation.workOutHistory

import com.example.domain.model.WorkoutSession

data class WorkoutHistoryUiState(
    val workouts: List<WorkoutSession> = emptyList(),
)


sealed interface WorkoutHistoryAction {
    data object GetWorkoutHistory : WorkoutHistoryAction

    data object SyncWorkoutList : WorkoutHistoryAction
}

sealed interface WorkoutHistoryApiState {

    data object LoadingState : WorkoutHistoryApiState
    data class FailureState(val errorMessage: String) : WorkoutHistoryApiState
}

data class WorkoutHistoryNavigator(
    val onBackPressed: () -> Unit
)
