package com.androidforge.streakflow.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.LocalTime

@Entity(tableName = "user_settings")
data class UserSettingsEntity(
    @PrimaryKey
    val id: Long = 1L, // Only one settings entry, fixed ID
    val enableNotifications: Boolean,
    val notificationTime: LocalTime,
    val theme: String
)