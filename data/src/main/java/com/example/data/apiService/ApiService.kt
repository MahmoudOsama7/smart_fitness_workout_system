package com.example.data.apiService

import com.example.data.data.SyncResponseDto
import com.example.data.data.WorkoutRequestDto
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface WorkoutSyncApi {

    @POST("api/v1/workouts/sync")
    suspend fun syncWorkout(
        @Body request: WorkoutRequestDto
    ): Response<SyncResponseDto>
}