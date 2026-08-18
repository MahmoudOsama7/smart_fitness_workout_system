package com.example.domain.state

import com.example.domain.model.WorkoutSession

interface WorkoutState {
    fun startWorkout(engine: WorkoutEngine) {}
    fun completeSet(engine: WorkoutEngine) {}
    fun skipRest(engine: WorkoutEngine) {}
    fun pauseWorkout(engine: WorkoutEngine) {}
    fun resumeWorkout(engine: WorkoutEngine) {}
    fun endWorkout(engine: WorkoutEngine) {}
    fun onTimerTick(engine: WorkoutEngine, remainingSeconds: Int) {}
    fun onTimerFinished(engine: WorkoutEngine) {}
}

class ReadyState : WorkoutState {
    override fun startWorkout(engine: WorkoutEngine) {
        engine.sessionStartTimeMs = System.currentTimeMillis()

        engine.session = engine.session.copy(
            currentSet = 1,
            completedSets = 0
        )

        engine.transitionTo(ActiveSetState(engine.session))
    }
}

data class ActiveSetState(val session: WorkoutSession) : WorkoutState {
    override fun completeSet(engine: WorkoutEngine) {
        val updatedCompletedSets = engine.session.completedSets + 1
        engine.session = engine.session.copy(completedSets = updatedCompletedSets)

        if (updatedCompletedSets >= engine.session.totalSets) {
            val durationSeconds = (System.currentTimeMillis() - engine.sessionStartTimeMs) / 1000

            val finalSession = engine.session.copy(
                elapsedTimeSeconds = durationSeconds,
                remainingRestSeconds = 0
            )
            engine.session = finalSession

            engine.transitionTo(WorkoutCompletedState(finalSession))
        } else {
            engine.startRestTimer(engine.session.remainingRestSeconds)
        }
    }

    override fun pauseWorkout(engine: WorkoutEngine) {
        engine.transitionTo(PausedState(previousState = this))
    }
}

data class RestTimerState(
    val session: WorkoutSession,
    val remainingSeconds: Int
) : WorkoutState {

    override fun skipRest(engine: WorkoutEngine) {
        advanceSet(engine)
    }

    override fun onTimerTick(engine: WorkoutEngine, remainingSeconds: Int) {
        engine.transitionTo(
            RestTimerState(
                session = engine.session.copy(remainingRestSeconds = remainingSeconds),
                remainingSeconds = remainingSeconds
            )
        )
    }

    override fun onTimerFinished(engine: WorkoutEngine) {
        advanceSet(engine)
    }

    override fun pauseWorkout(engine: WorkoutEngine) {
        engine.transitionTo(PausedState(previousState = this))
    }

    private fun advanceSet(engine: WorkoutEngine) {
        val nextSet = engine.session.currentSet + 1
        engine.session = engine.session.copy(currentSet = nextSet)
        engine.transitionTo(ActiveSetState(engine.session))
    }
}

data class PausedState(val previousState: WorkoutState) : WorkoutState {
    override fun resumeWorkout(engine: WorkoutEngine) {
        if (previousState is RestTimerState) {
            engine.startRestTimer(previousState.remainingSeconds)
        } else {
            engine.transitionTo(previousState)
        }
    }

    override fun endWorkout(engine: WorkoutEngine) {
        engine.stopRestTimer()

        val durationSeconds = (System.currentTimeMillis() - engine.sessionStartTimeMs) / 1000
        val finalSession = engine.session.copy(
            elapsedTimeSeconds = durationSeconds,
            remainingRestSeconds = 0
        )
        engine.session = finalSession

        engine.transitionTo(WorkoutCompletedState(finalSession))
    }
}

data class WorkoutCompletedState(val session: WorkoutSession) : WorkoutState