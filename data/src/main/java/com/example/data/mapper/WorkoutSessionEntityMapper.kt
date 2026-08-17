package com.example.data.mapper

import com.example.database.model.SyncStatusEntity
import com.example.database.model.WorkoutSessionEntity
import com.example.domain.model.SyncStatus
import com.example.domain.model.WorkoutSession

fun WorkoutSession.toWorkoutSessionEntity(): WorkoutSessionEntity {
    return WorkoutSessionEntity(
        exerciseName = exerciseName,
        completedSets = completedSets,
        totalSets = totalSets,
        weightKg = weightKg,
        elapsedTimeSeconds = elapsedTimeSeconds,
        timestamp = timestamp,
        syncStatus = syncStatus.toSyncStatusEntity()
    )
}

fun SyncStatus.toSyncStatusEntity(): SyncStatusEntity {
    return when (this) {
        SyncStatus.SYNCED -> SyncStatusEntity.SYNCED
        SyncStatus.PENDING_SYNC -> SyncStatusEntity.PENDING_SYNC
    }
}

fun WorkoutSessionEntity.toWorkoutSession(): WorkoutSession {
    return WorkoutSession(
        exerciseName = exerciseName,
        currentSet = totalSets,
        totalSets = totalSets,
        weightKg = weightKg,
        completedSets = completedSets,
        elapsedTimeSeconds = elapsedTimeSeconds,
        remainingRestSeconds = 0,
        timestamp = timestamp,
        syncStatus = syncStatus.toSyncStatus()
    )
}

fun SyncStatusEntity.toSyncStatus(): SyncStatus {
    return when (this) {
        SyncStatusEntity.SYNCED -> SyncStatus.SYNCED
        SyncStatusEntity.PENDING_SYNC -> SyncStatus.PENDING_SYNC
    }
}