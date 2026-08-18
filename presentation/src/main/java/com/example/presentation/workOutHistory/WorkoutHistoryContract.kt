package com.example.presentation.workOutHistory

data class WorkoutHistoryContract(
    val state: WorkoutHistoryUiState,
    val onAction: (WorkoutHistoryAction) -> Unit,
    val navigator: WorkoutHistoryNavigator
)