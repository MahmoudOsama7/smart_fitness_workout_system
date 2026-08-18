package com.example.presentation.workOutView

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

const val WORKOUT_VIEW = "workout_view"

@Composable
fun WorkoutViewNavigation(
    onNavigateToWorkoutHistory: () -> Unit
) {
    val viewModel: WorkoutViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val action = viewModel::onAction

    WorkoutViewScreen(
        contract = WorkoutViewContract(
            state = state,
            action = action,
            navigator = WorkoutNavigator(
                onNavigateToHistory = onNavigateToWorkoutHistory
            )
        )
    )
}