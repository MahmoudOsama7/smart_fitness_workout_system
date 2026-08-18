package com.example.data.repository

import com.example.data.apiService.WorkoutSyncApi
import com.example.data.mapper.toDto
import com.example.data.mapper.toWorkoutSession
import com.example.data.mapper.toWorkoutSessionEntity
import com.example.database.database.WorkoutDAO
import com.example.domain.model.WorkoutSession
import com.example.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import javax.inject.Inject


class WorkoutRepositoryImpl @Inject constructor(
    private val dao: WorkoutDAO,
    private val api: WorkoutSyncApi
) : WorkoutRepository {

    override suspend fun saveCompletedWorkout(workoutSession: WorkoutSession): Long {
        return dao.addWorkoutSession(workoutSession.toWorkoutSessionEntity())
    }

    override suspend fun getSavedCompletedWorkout(id: Long): WorkoutSession? {
        return dao.getWorkoutSessionById(sessionId = id)?.toWorkoutSession()
    }

    override suspend fun getWorkoutHistoryList(): Flow<List<WorkoutSession>> {
        return dao.getAllWorkoutSessions().map { entities ->
            entities.map { entity -> entity.toWorkoutSession() }
        }
    }

    override suspend fun syncPendingWorkouts(): Result<List<WorkoutSession>> {
        val pendingWorkouts = dao.getUnsyncedWorkouts().map { it.toWorkoutSession() }
        if (pendingWorkouts.isEmpty()) {
            val oldList = dao.getAllWorkoutSessions().first().map { it.toWorkoutSession() }
            return Result.success(oldList)
        }
        val pendingWorkoutsDto = pendingWorkouts.map { it.toDto() }
        for (workout in pendingWorkoutsDto) {

            try {
                val response = api.syncWorkout(workout)
                if (response.isSuccessful && response.body()?.success == true) {
                    dao.markAsSynced(workout.id)
                } else {
                    return Result.failure(Exception("Sync failed: HTTP ${response.code()}"))
                }
            } catch (e: Exception) {
                return Result.failure(e)
            }
        }
        return Result.success(pendingWorkouts)
    }
}