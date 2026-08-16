package com.example.domain.model

sealed interface WorkoutAction {
    data object StartWorkout : WorkoutAction
    data object CompleteSet : WorkoutAction
    data object SkipRest : WorkoutAction
    data object PauseWorkout : WorkoutAction
    data object ResumeWorkout : WorkoutAction
    data object EndWorkout : WorkoutAction
}