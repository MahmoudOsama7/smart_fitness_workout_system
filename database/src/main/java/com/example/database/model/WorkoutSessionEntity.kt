package com.example.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_history")
data class WorkoutSessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val exerciseName: String,
    val completedSets: Int,
    val totalSets: Int,
    val weightKg: Double,
    val elapsedTimeSeconds: Long,
    val timestamp: Long = System.currentTimeMillis(),
    val syncStatus: SyncStatusEntity = SyncStatusEntity.PENDING_SYNC
)

enum class SyncStatusEntity {
    SYNCED,
    PENDING_SYNC
}