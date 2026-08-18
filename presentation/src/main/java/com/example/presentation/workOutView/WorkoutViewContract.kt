package com.example.presentation.workOutView

data class WorkoutViewContract(
    val state: WorkoutUiState,
    val action: (WorkoutAction) -> Unit,
    val navigator: WorkoutNavigator
)