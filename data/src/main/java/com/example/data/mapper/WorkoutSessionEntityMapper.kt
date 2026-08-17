package com.example.data.mapper

import com.example.database.model.WorkoutSessionEntity
import com.example.domain.model.WorkoutSession

fun WorkoutSession.toWorkoutSessionEntity(): WorkoutSessionEntity {
    return WorkoutSessionEntity(
        exerciseName = exerciseName,
        currentSet = currentSet,
        totalSets = totalSets,
        weightKg = weightKg,
        completedSets = completedSets,
        elapsedTimeSeconds = elapsedTimeSeconds,
        remainingRestSeconds = remainingRestSeconds
    )
}

fun WorkoutSessionEntity.toWorkoutSession(): WorkoutSession {
    return WorkoutSession(
        exerciseName = exerciseName,
        currentSet = currentSet,
        totalSets = totalSets,
        weightKg = weightKg,
        completedSets = completedSets,
        elapsedTimeSeconds = elapsedTimeSeconds,
        remainingRestSeconds = remainingRestSeconds
    )
}