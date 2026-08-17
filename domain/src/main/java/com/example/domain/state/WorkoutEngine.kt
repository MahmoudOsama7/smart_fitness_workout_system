package com.example.domain.state

import com.example.domain.model.WorkoutSession
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class WorkoutEngine @Inject constructor(
    private val scope: CoroutineScope
) {
    var session: WorkoutSession = WorkoutSession(
        exerciseName = "Barbell Squat",
        currentSet = 1,
        totalSets = 5,
        weightKg = 60.0
    )
    private val _currentState = MutableStateFlow<WorkoutState>(ReadyState())
    val currentState: StateFlow<WorkoutState> = _currentState.asStateFlow()

    var sessionStartTimeMs: Long = 0L
    private var timerJob: Job? = null

    private val stateMutex = Mutex()

    fun transitionTo(newState: WorkoutState) {
        _currentState.value = newState
    }

    fun startWorkout() = scope.launch { stateMutex.withLock { _currentState.value.startWorkout(this@WorkoutEngine) } }
    fun completeSet() = scope.launch { stateMutex.withLock { _currentState.value.completeSet(this@WorkoutEngine) } }
    fun skipRest() = scope.launch { stateMutex.withLock { _currentState.value.skipRest(this@WorkoutEngine) } }
    fun pauseWorkout() = scope.launch { stateMutex.withLock { _currentState.value.pauseWorkout(this@WorkoutEngine) } }
    fun resumeWorkout() = scope.launch { stateMutex.withLock { _currentState.value.resumeWorkout(this@WorkoutEngine) } }
    fun endWorkout() = scope.launch { stateMutex.withLock { _currentState.value.endWorkout(this@WorkoutEngine) } }

    fun startRestTimer(durationSeconds: Int) {
        stopRestTimer()
        transitionTo(RestTimerState(session = session, remainingSeconds = durationSeconds))

        timerJob = scope.launch {
            var timeLeft = durationSeconds
            while (isActive && timeLeft > 0) {
                delay(1000L)
                timeLeft--
                stateMutex.withLock {
                    if (_currentState.value is RestTimerState) {
                        _currentState.value.onTimerTick(this@WorkoutEngine, timeLeft)
                    }
                }
            }
        }
    }

    fun stopRestTimer() {
        timerJob?.cancel()
        timerJob = null
    }
}