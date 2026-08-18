package com.example.data.data

import com.google.gson.annotations.SerializedName

data class WorkoutRequestDto(
    @SerializedName("id")
    val id: Long,

    @SerializedName("exercise_name")
    val exerciseName: String,

    @SerializedName("completed_sets")
    val completedSets: Int,

    @SerializedName("total_sets")
    val totalSets: Int,

    @SerializedName("weight_kg")
    val weightKg: Double,

    @SerializedName("elapsed_time_seconds")
    val elapsedTimeSeconds: Long,

    @SerializedName("timestamp")
    val timestamp: Long,

    @SerializedName("sync_status")
    val syncStatus: String
)

data class SyncResponseDto(
    @SerializedName("success")
    val success: Boolean,

    @SerializedName("message")
    val message: String,

    @SerializedName("synced_id")
    val syncedId: Long
)