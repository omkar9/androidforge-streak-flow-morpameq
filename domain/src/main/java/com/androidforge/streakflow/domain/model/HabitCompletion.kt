package com.androidforge.streakflow.domain.model

import java.time.LocalDate

/**
 * Domain model representing a single completion record for a habit on a specific date.
 */
data class HabitCompletion(
    val id: Long = 0L,
    val habitId: Long,
    val date: LocalDate,
    val completed: Boolean
)