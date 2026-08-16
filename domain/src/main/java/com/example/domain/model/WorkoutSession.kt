package com.example.domain.model

data class WorkoutSession(
    val exerciseName: String,
    val currentSet: Int,
    val totalSets: Int,
    val weightKg: Double,
    val completedSets: Int = 0,
    val elapsedTimeSeconds: Long = 0L,
    val remainingRestSeconds: Int = 60
)