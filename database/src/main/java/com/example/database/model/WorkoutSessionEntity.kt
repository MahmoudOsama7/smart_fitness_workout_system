package com.example.database.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "workout_history")
data class WorkoutSessionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val exerciseName: String,
    val currentSet: Int,
    val totalSets: Int,
    val weightKg: Double,
    val completedSets: Int = 0,
    val elapsedTimeSeconds: Long = 0L,
    val remainingRestSeconds: Int = 60
)