package com.example.presentation.workOutHistory

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle

const val WORKOUT_HISTORY_VIEW = "workout_history_view"

@Composable
fun WorkoutHistoryViewNavigation(
    onBackPressed: () -> Unit
) {
    val viewModel: WorkoutHistoryViewModel = hiltViewModel()
    val state by viewModel.uiState.collectAsStateWithLifecycle()
    val onAction = viewModel::onAction

    WorkoutHistoryContent(
        contract = WorkoutHistoryContract(
            state = state,
            onAction = onAction,
            navigator = WorkoutHistoryNavigator(
                onBackPressed = onBackPressed
            )
        )
    )
}