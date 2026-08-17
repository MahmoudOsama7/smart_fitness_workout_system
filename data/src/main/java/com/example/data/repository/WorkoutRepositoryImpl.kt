package com.example.data.repository

import com.example.data.mapper.toWorkoutSessionEntity
import com.example.database.database.WorkoutDAO
import com.example.domain.model.WorkoutSession
import com.example.domain.repository.WorkoutRepository
import javax.inject.Inject


class WorkoutRepositoryImpl @Inject constructor(
    private val dao: WorkoutDAO
) : WorkoutRepository {
    override suspend fun saveCompletedWorkout(workoutSession: WorkoutSession) {
        dao.addWorkoutSession(workoutSession.toWorkoutSessionEntity())
    }

}