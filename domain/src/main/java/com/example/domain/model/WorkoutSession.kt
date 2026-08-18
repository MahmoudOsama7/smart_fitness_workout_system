package com.example.domain.model

enum class SyncStatus {
    SYNCED,
    PENDING_SYNC
}

data class WorkoutSession(
    val id: Long,
    val exerciseName: String,
    val currentSet: Int = 1,
    val totalSets: Int,
    val completedSets: Int = 0,
    val weightKg: Double,
    val elapsedTimeSeconds: Long = 0L,
    val remainingRestSeconds: Int = 60,
    val timestamp: Long = System.currentTimeMillis(),
    val syncStatus: SyncStatus = SyncStatus.PENDING_SYNC
)