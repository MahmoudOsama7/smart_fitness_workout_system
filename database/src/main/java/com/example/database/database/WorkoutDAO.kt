package com.example.database.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.database.model.WorkoutSessionEntity

@Dao
interface WorkoutDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWorkoutSession(workoutSessionEntity: WorkoutSessionEntity): Long

    @Query("SELECT * FROM workout_history WHERE id = :sessionId LIMIT 1")
    suspend fun getWorkoutSessionById(sessionId: Long): WorkoutSessionEntity?
}