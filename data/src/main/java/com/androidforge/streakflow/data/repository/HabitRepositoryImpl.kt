package com.androidforge.streakflow.data.repository

import com.androidforge.streakflow.data.local.dao.HabitCompletionDao
import com.androidforge.streakflow.data.local.dao.HabitDao
import com.androidforge.streakflow.data.local.entity.HabitCompletionEntity
import com.androidforge.streakflow.data.local.mapper.toDomain
import com.androidforge.streakflow.data.local.mapper.toEntity
import com.androidforge.streakflow.domain.model.Habit
import com.androidforge.streakflow.domain.model.HabitCompletion
import com.androidforge.streakflow.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class HabitRepositoryImpl @Inject constructor(
    private val habitDao: HabitDao,
    private val habitCompletionDao: HabitCompletionDao
) : HabitRepository {

    override suspend fun addHabit(habit: Habit): Long {
        return habitDao.insertHabit(habit.toEntity())
    }

    override suspend fun updateHabit(habit: Habit) {
        habitDao.updateHabit(habit.toEntity())
    }

    override suspend fun deleteHabit(habitId: Long) {
        habitDao.deleteHabit(habitId)
        habitCompletionDao.deleteAllCompletionsForHabit(habitId) // Also delete associated completions
    }

    override suspend fun archiveHabit(habitId: Long, archived: Boolean) {
        habitDao.updateHabitArchivedStatus(habitId, archived)
    }

    override fun getHabitById(habitId: Long): Flow<Habit?> {
        return habitDao.getHabitById(habitId).map { it?.toDomain() }
    }

    override fun getAllHabits(archived: Boolean): Flow<List<Habit>> {
        return habitDao.getAllHabits(archived).map { entities ->
            entities.map { it.toDomain() }
        }
    }

    override suspend fun markHabitComplete(habitId: Long, date: LocalDate) {
        val existingCompletion = habitCompletionDao.getHabitCompletionForDate(habitId, date)
        if (existingCompletion == null) {
            habitCompletionDao.insertHabitCompletion(HabitCompletionEntity(habitId = habitId, date = date, completed = true))
        } else {
            habitCompletionDao.updateHabitCompletion(existingCompletion.copy(completed = true))
        }
    }

    override suspend fun markHabitIncomplete(habitId: Long, date: LocalDate) {
        val existingCompletion = habitCompletionDao.getHabitCompletionForDate(habitId, date)
        if (existingCompletion != null) {
            // If it was completed, mark incomplete. If it didn't exist or was already incomplete, do nothing.
            habitCompletionDao.updateHabitCompletion(existingCompletion.copy(completed = false))
        }
        // Option: delete the entry if marking incomplete, but keeping it as `completed = false` is useful for history.
    }

    override suspend fun getHabitCompletionStatus(habitId: Long, date: LocalDate): Boolean {
        return habitCompletionDao.getHabitCompletionForDate(habitId, date)?.completed ?: false
    }

    override suspend fun getHabitCompletions(habitId: Long): List<HabitCompletion> {
        return habitCompletionDao.getAllCompletedDatesForHabit(habitId).map { it.toDomain() }
    }

    override fun getHabitCompletionsFlow(habitId: Long): Flow<List<HabitCompletion>> {
        return habitCompletionDao.getHabitCompletionsFlow(habitId).map { entities ->
            entities.map { it.toDomain() }
        }
    }
}