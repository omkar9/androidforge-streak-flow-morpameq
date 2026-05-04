package com.androidforge.streakflow.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.androidforge.streakflow.data.local.entity.HabitCompletionEntity
import java.time.LocalDate

@Dao
interface HabitCompletionDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabitCompletion(completion: HabitCompletionEntity)

    @Update
    suspend fun updateHabitCompletion(completion: HabitCompletionEntity)

    @Query("SELECT * FROM habit_completions WHERE habitId = :habitId AND date = :date LIMIT 1")
    suspend fun getHabitCompletionForDate(habitId: Long, date: LocalDate): HabitCompletionEntity?

    @Query("SELECT * FROM habit_completions WHERE habitId = :habitId AND completed = 1 ORDER BY date ASC")
    suspend fun getAllCompletedDatesForHabit(habitId: Long): List<HabitCompletionEntity>

    @Query("SELECT * FROM habit_completions WHERE habitId = :habitId ORDER BY date DESC")
    fun getHabitCompletionsFlow(habitId: Long): Flow<List<HabitCompletionEntity>>

    @Query("DELETE FROM habit_completions WHERE habitId = :habitId AND date = :date")
    suspend fun deleteHabitCompletionForDate(habitId: Long, date: LocalDate)

    @Query("DELETE FROM habit_completions WHERE habitId = :habitId")
    suspend fun deleteAllCompletionsForHabit(habitId: Long)
}