package com.example.presentation.mapper

import com.example.presentation.workOutView.WorkoutAction as PresentationAction
import com.example.domain.model.WorkoutAction as DomainAction

fun PresentationAction.toDomain(): DomainAction = when (this) {
    PresentationAction.StartWorkout -> DomainAction.StartWorkout
    PresentationAction.CompleteSet -> DomainAction.CompleteSet
    PresentationAction.SkipRest -> DomainAction.SkipRest
    PresentationAction.PauseWorkout -> DomainAction.PauseWorkout
    PresentationAction.ResumeWorkout -> DomainAction.ResumeWorkout
    PresentationAction.EndWorkout -> DomainAction.EndWorkout
}