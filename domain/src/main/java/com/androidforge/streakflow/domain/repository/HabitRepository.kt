package com.androidforge.streakflow.domain.repository

import com.androidforge.streakflow.domain.model.Habit
import com.androidforge.streakflow.domain.model.HabitCompletion
import kotlinx.coroutines.flow.Flow
import java.time.LocalDate

/**
 * Repository interface for managing habits and their completion records.
 */
interface HabitRepository {
    suspend fun addHabit(habit: Habit): Long
    suspend fun updateHabit(habit: Habit)
    suspend fun deleteHabit(habitId: Long)
    suspend fun archiveHabit(habitId: Long, archived: Boolean)
    fun getHabitById(habitId: Long): Flow<Habit?>
    fun getAllHabits(archived: Boolean): Flow<List<Habit>>

    suspend fun markHabitComplete(habitId: Long, date: LocalDate)
    suspend fun markHabitIncomplete(habitId: Long, date: LocalDate)
    suspend fun getHabitCompletionStatus(habitId: Long, date: LocalDate): Boolean
    suspend fun getHabitCompletions(habitId: Long): List<HabitCompletion>
    fun getHabitCompletionsFlow(habitId: Long): Flow<List<HabitCompletion>>
}