package com.androidforge.streakflow.data.local.mapper

import com.androidforge.streakflow.data.local.entity.HabitCompletionEntity
import com.androidforge.streakflow.domain.model.HabitCompletion

fun HabitCompletionEntity.toDomain(): HabitCompletion {
    return HabitCompletion(
        id = id,
        habitId = habitId,
        date = date,
        completed = completed
    )
}

fun HabitCompletion.toEntity(): HabitCompletionEntity {
    return HabitCompletionEntity(
        id = id,
        habitId = habitId,
        date = date,
        completed = completed
    )
}