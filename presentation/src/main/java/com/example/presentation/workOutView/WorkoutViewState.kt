package com.example.presentation.workOutView

data class WorkoutUiState(
    val workoutState: WorkoutStateType = WorkoutStateType.READY,

    val exerciseName: String = "Barbell Bench Press",

    val currentSet: Int = 1,
    val totalSets: Int = 4,

    val currentWeight: String = "60 kg",

    val remainingRestSeconds: Int = 60,

    val elapsedTimeSeconds: Long = 0L,

    val completedSets: Int = 0,
)

enum class WorkoutStateType {
    READY,
    ACTIVE_SET,
    RESTING,
    PAUSED,
    COMPLETED
}

sealed interface WorkoutAction {

    data object StartWorkout : WorkoutAction

    data object CompleteSet : WorkoutAction

    data object SkipRest : WorkoutAction

    data object PauseWorkout : WorkoutAction

    data object ResumeWorkout : WorkoutAction

    data object EndWorkout : WorkoutAction
    data object RefreshSettings : WorkoutAction

}

data class WorkoutNavigator(
    val onNavigateToHistory: () -> Unit,
    val onNavigateToDynamicSettings: () -> Unit
)