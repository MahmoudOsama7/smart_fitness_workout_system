package com.example.domain.state

data class WorkoutRoutine(
    val exerciseName: String = "Barbell Back Squat",
    val totalSets: Int = 4,
    val targetReps: Int = 8,
    val baseWeightKg: Double = 100.0,
    val restDurationSeconds: Int = 60
)

interface WorkoutState {
    fun startWorkout(engine: WorkoutEngine) {}
    fun completeSet(engine: WorkoutEngine) {}
    fun skipRest(engine: WorkoutEngine) {}
    fun pauseWorkout(engine: WorkoutEngine) {}
    fun resumeWorkout(engine: WorkoutEngine) {}
    fun endWorkout(engine: WorkoutEngine) {}
    fun onTimerTick(engine: WorkoutEngine, remainingSeconds: Int) {}
}

class ReadyState : WorkoutState {
    override fun startWorkout(engine: WorkoutEngine) {
        engine.sessionStartTimeMs = System.currentTimeMillis()
        engine.currentSet = 1
        engine.completedSets = 0
        engine.transitionTo(ActiveSetState())
    }
}

class ActiveSetState : WorkoutState {
    override fun completeSet(engine: WorkoutEngine) {
//        engine.completedSets += 1
//        if (engine.completedSets >= engine.routine.totalSets) {
//            engine.transitionTo(WorkoutCompletedState())
//        } else {
//            engine.startRestTimer(engine.routine.restDurationSeconds)
//        }
    }

    override fun pauseWorkout(engine: WorkoutEngine) {
        engine.transitionTo(PausedState(previousState = this))
    }
}

class RestTimerState(val remainingSeconds: Int) : WorkoutState {
    override fun skipRest(engine: WorkoutEngine) {
        engine.stopRestTimer()
        advanceSet(engine)
    }

    override fun onTimerTick(engine: WorkoutEngine, remainingSeconds: Int) {
        if (remainingSeconds <= 0) {
            engine.stopRestTimer()
            advanceSet(engine)
        } else {
            engine.transitionTo(RestTimerState(remainingSeconds))
        }
    }

    override fun pauseWorkout(engine: WorkoutEngine) {
        engine.stopRestTimer()
        engine.transitionTo(PausedState(previousState = this))
    }

    private fun advanceSet(engine: WorkoutEngine) {
        engine.currentSet += 1
        engine.transitionTo(ActiveSetState())
    }
}

class PausedState(val previousState: WorkoutState) : WorkoutState {
    override fun resumeWorkout(engine: WorkoutEngine) {
        if (previousState is RestTimerState) {
            engine.startRestTimer(previousState.remainingSeconds)
        } else {
            engine.transitionTo(previousState)
        }
    }

    override fun endWorkout(engine: WorkoutEngine) {
        engine.stopRestTimer()
        engine.transitionTo(WorkoutCompletedState())
    }
}

class WorkoutCompletedState : WorkoutState