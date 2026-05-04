package com.androidforge.streakflow.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.androidforge.streakflow.domain.model.FrequencyType
import java.time.LocalDate
import java.time.LocalTime

@Entity(tableName = "habits")
data class HabitEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0L,
    val name: String,
    val description: String?,
    val frequencyType: FrequencyType,
    val frequencyDays: String, // Stored as comma-separated string of DayOfWeek names
    val reminderTime: LocalTime?,
    val startDate: LocalDate,
    val color: Long,
    val icon: Int,
    val archived: Boolean
)