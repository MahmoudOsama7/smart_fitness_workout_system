package com.example.di.modules

import android.content.Context
import androidx.room.Room
import com.example.database.database.WORKOUT_DB
import com.example.database.database.WorkoutDAO
import com.example.database.database.WorkoutDB
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideWorkoutDao(
        database: WorkoutDB
    ): WorkoutDAO {
        return database.getWorkoutDAO()
    }

    @Provides
    @Singleton
    fun provideWorkoutDB(
        @ApplicationContext applicationContext: Context
    ): WorkoutDB = Room.databaseBuilder(
        applicationContext,
        WorkoutDB::class.java,
        WORKOUT_DB
    ).fallbackToDestructiveMigration()
        .build()

}