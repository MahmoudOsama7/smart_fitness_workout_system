package com.example.database.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import com.example.database.model.WorkoutSessionEntity

@Dao
interface WorkoutDAO {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addWorkoutSession(workoutSessionEntity: WorkoutSessionEntity)
}