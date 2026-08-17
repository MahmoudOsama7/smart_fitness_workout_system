package com.example.presentation.workOutHistory

import com.example.domain.model.WorkoutSession

data class WorkoutHistoryUiState(
    val workouts: List<WorkoutSession> = emptyList(),
)