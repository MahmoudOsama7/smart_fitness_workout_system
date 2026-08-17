package com.example.data.repository

import com.example.data.mapper.toWorkoutSession
import com.example.data.mapper.toWorkoutSessionEntity
import com.example.database.database.WorkoutDAO
import com.example.domain.model.WorkoutSession
import com.example.domain.repository.WorkoutRepository
import javax.inject.Inject


class WorkoutRepositoryImpl @Inject constructor(
    private val dao: WorkoutDAO
) : WorkoutRepository {

    override suspend fun saveCompletedWorkout(workoutSession: WorkoutSession): Long {
        return dao.addWorkoutSession(workoutSession.toWorkoutSessionEntity())
    }

    override suspend fun getSavedCompletedWorkout(id: Long): WorkoutSession? {
        return dao.getWorkoutSessionById(sessionId = id)?.toWorkoutSession()
    }
}