package com.androidforge.streakflow.data.local.mapper

import com.androidforge.streakflow.data.local.entity.HabitEntity
import com.androidforge.streakflow.domain.model.Habit
import com.androidforge.streakflow.data.local.database.Converters // Import Converters

fun HabitEntity.toDomain(): Habit {
    return Habit(
        id = id,
        name = name,
        description = description,
        frequencyType = frequencyType,
        frequencyDays = Converters.toDayOfWeekSet(frequencyDays) ?: emptySet(),
        reminderTime = reminderTime,
        startDate = startDate,
        color = color,
        icon = icon,
        archived = archived
    )
}

fun Habit.toEntity(): HabitEntity {
    return HabitEntity(
        id = id,
        name = name,
        description = description,
        frequencyType = frequencyType,
        frequencyDays = Converters.fromDayOfWeekSet(frequencyDays) ?: "",
        reminderTime = reminderTime,
        startDate = startDate,
        color = color,
        icon = icon,
        archived = archived
    )
}