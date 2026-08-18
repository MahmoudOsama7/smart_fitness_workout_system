package com.example.di.modules

import android.content.Context
import android.content.SharedPreferences
import androidx.room.Room
import com.example.database.database.WORKOUT_DB
import com.example.database.database.WorkoutDAO
import com.example.database.database.WorkoutDB
import com.example.database.prefrence.PreferenceHelper
import com.example.database.prefrence.PreferenceHelper.Companion.PREF_NAME
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

    @Provides
    @Singleton
    fun provideSharedPreferences(
        @ApplicationContext context: Context
    ): SharedPreferences {
        return context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
    }

    @Provides
    @Singleton
    fun providePreferenceHelper(
        sharedPreferences: SharedPreferences
    ): PreferenceHelper {
        return PreferenceHelper(sharedPreferences)
    }
}