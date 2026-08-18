package com.example.domain.usecase

import com.example.domain.model.WorkoutSession
import com.example.domain.repository.WorkoutRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


class GetWorkoutHistoryListUseCase @Inject constructor(
    private val repository: WorkoutRepository
) {
    suspend operator fun invoke(): Flow<List<WorkoutSession>> {
        return repository.getWorkoutHistoryList()
    }
}