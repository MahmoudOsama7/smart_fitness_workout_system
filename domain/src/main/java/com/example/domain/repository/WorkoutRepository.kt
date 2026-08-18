package com.example.domain.repository

import com.example.domain.model.WorkoutSession
import kotlinx.coroutines.flow.Flow

interface WorkoutRepository {
    suspend fun saveCompletedWorkout(workoutSession: WorkoutSession): Long
    suspend fun getSavedCompletedWorkout(id: Long): WorkoutSession?

    suspend fun getWorkoutHistoryList(): Flow<List<WorkoutSession>>
    suspend fun syncPendingWorkouts(): Result<List<WorkoutSession>>
}