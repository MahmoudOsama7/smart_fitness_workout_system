package com.example.domain.usecase

import com.example.domain.model.WorkoutSession
import com.example.domain.repository.WorkoutRepository
import javax.inject.Inject

class SyncPendingWorkoutsUseCase @Inject constructor(
    private val repository: WorkoutRepository
) {
    suspend operator fun invoke(): Result<List<WorkoutSession>> {
        return repository.syncPendingWorkouts()
    }
}