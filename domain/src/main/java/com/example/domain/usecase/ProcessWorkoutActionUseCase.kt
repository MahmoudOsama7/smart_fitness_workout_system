package com.example.domain.usecase

import com.example.domain.model.WorkoutAction
import com.example.domain.model.WorkoutSession
import com.example.domain.repository.WorkoutRepository
import com.example.domain.state.WorkoutCompletedState
import com.example.domain.state.WorkoutEngine
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class ProcessWorkoutActionUseCase @Inject constructor(
    private val engine: WorkoutEngine,
    private val repository: WorkoutRepository,
    private val scope: CoroutineScope
) {
    operator fun invoke(action: WorkoutAction) {
        when (action) {
            WorkoutAction.StartWorkout -> engine.startWorkout()
            WorkoutAction.CompleteSet -> engine.completeSet()
            WorkoutAction.SkipRest -> engine.skipRest()
            WorkoutAction.PauseWorkout -> engine.pauseWorkout()
            WorkoutAction.ResumeWorkout -> engine.resumeWorkout()
            WorkoutAction.EndWorkout -> engine.endWorkout()
        }
    }

    init {
        scope.launch {
            engine.currentState.collect { state ->
                if (state is WorkoutCompletedState) {
                    val duration = (System.currentTimeMillis() - engine.sessionStartTimeMs) / 1000
                    repository.saveCompletedWorkout(
                        WorkoutSession(
                            exerciseName = "test",
                            currentSet = 1,
                            totalSets = 1,
                            weightKg = 0.0,
                            completedSets = 1,
                            elapsedTimeSeconds = duration,
                            remainingRestSeconds = 0
                        )
                    )
                }
            }
        }
    }
}