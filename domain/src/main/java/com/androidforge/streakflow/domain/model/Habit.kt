package com.androidforge.streakflow.domain.model

import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime

/**
 * Domain model representing a habit.
 */
data class Habit(
    val id: Long = 0L,
    val name: String,
    val description: String? = null,
    val frequencyType: FrequencyType,
    val frequencyDays: Set<DayOfWeek> = emptySet(), // Used if frequencyType is SPECIFIC_DAYS
    val reminderTime: LocalTime? = null,
    val startDate: LocalDate = LocalDate.now(),
    val color: Long, // ARGB color as Long
    val icon: Int, // Resource ID for icon
    val archived: Boolean = false
)