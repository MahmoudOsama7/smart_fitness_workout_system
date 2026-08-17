package com.example.domain.usecase

import com.example.domain.state.WorkoutEngine
import com.example.domain.state.WorkoutState
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class ObserveWorkoutStateUseCase @Inject constructor(
    private val engine: WorkoutEngine
) {
    operator fun invoke(): StateFlow<WorkoutState> = engine.currentState

    fun getCurrentSet(): Int = engine.currentSet
    fun getCompletedSets(): Int = engine.completedSets
}