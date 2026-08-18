package com.example.data.mapper

import com.example.data.data.WorkoutRequestDto
import com.example.domain.model.SyncStatus
import com.example.domain.model.WorkoutSession

fun String.toSyncStatus(): SyncStatus {
    return SyncStatus.entries.find { it.name == this } ?: SyncStatus.PENDING_SYNC
}

fun SyncStatus.toSyncStatusString(): String {
    return this.name
}

fun WorkoutSession.toDto(): WorkoutRequestDto {
    return WorkoutRequestDto(
        id = id,
        exerciseName = exerciseName,
        completedSets = completedSets,
        totalSets = totalSets,
        weightKg = weightKg,
        elapsedTimeSeconds = elapsedTimeSeconds,
        timestamp = timestamp,
        syncStatus = syncStatus.toSyncStatusString()
    )
}

fun WorkoutRequestDto.toDomain(): WorkoutSession {
    return WorkoutSession(
        id = id,
        exerciseName = exerciseName,
        currentSet = 1,
        totalSets = totalSets,
        completedSets = completedSets,
        weightKg = weightKg,
        elapsedTimeSeconds = elapsedTimeSeconds,
        timestamp = timestamp,
        syncStatus = syncStatus.toSyncStatus()
    )
}