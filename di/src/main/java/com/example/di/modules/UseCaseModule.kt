package com.example.di.modules

import com.example.domain.repository.WorkoutRepository
import com.example.domain.state.WorkoutEngine
import com.example.domain.usecase.ObserveWorkoutStateUseCase
import com.example.domain.usecase.ProcessWorkoutActionUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UseCaseModule {

    @Provides
    @Singleton
    fun provideProcessWorkoutActionUseCase(
        workoutRepository: WorkoutRepository,
        engine: WorkoutEngine,
        scope: CoroutineScope
    ): ProcessWorkoutActionUseCase {
        return ProcessWorkoutActionUseCase(
            repository = workoutRepository,
            engine = engine,
            scope = scope
        )
    }

    @Provides
    @Singleton
    fun provideObserveWorkoutStateUseCase(
        engine: WorkoutEngine
    ): ObserveWorkoutStateUseCase {
        return ObserveWorkoutStateUseCase(
            engine = engine
        )
    }

    @Provides
    @Singleton
    fun provideCoroutineScope(): CoroutineScope {
        return CoroutineScope(SupervisorJob() + Dispatchers.Default)
    }
}