package com.example.domain.repository

import com.example.domain.model.WorkoutSession

interface WorkoutRepository {
    suspend fun saveCompletedWorkout(workoutSession: WorkoutSession)
}