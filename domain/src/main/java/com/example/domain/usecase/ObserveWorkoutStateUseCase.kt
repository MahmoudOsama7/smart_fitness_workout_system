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
    fun getExerciseName(): String = engine.routine.exerciseName
    fun getTotalSets(): Int = engine.routine.totalSets
    fun getCurrentWeight(): String = "${engine.routine.baseWeightKg} kg"
}