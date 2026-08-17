package com.example.database.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.example.database.model.WorkoutSessionEntity

const val WORKOUT_DB = "workout_db"
@Database(
    entities = [WorkoutSessionEntity::class],
    version = 1
)

abstract class WorkoutDB : RoomDatabase() {
    abstract fun getWorkoutDAO(): WorkoutDAO
}