package com.androidforge.streakflow.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.androidforge.streakflow.data.local.entity.HabitEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface HabitDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertHabit(habit: HabitEntity): Long

    @Update
    suspend fun updateHabit(habit: HabitEntity)

    @Query("DELETE FROM habits WHERE id = :habitId")
    suspend fun deleteHabit(habitId: Long)

    @Query("SELECT * FROM habits WHERE id = :habitId")
    fun getHabitById(habitId: Long): Flow<HabitEntity?>

    @Query("SELECT * FROM habits WHERE archived = :archived ORDER BY startDate DESC, name ASC")
    fun getAllHabits(archived: Boolean): Flow<List<HabitEntity>>

    @Query("UPDATE habits SET archived = :archived WHERE id = :habitId")
    suspend fun updateHabitArchivedStatus(habitId: Long, archived: Boolean)
}