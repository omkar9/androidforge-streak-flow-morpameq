package com.androidforge.streakflow.core.utils

import com.androidforge.streakflow.domain.model.Habit
import com.androidforge.streakflow.domain.model.UserSettings

/**
 * Interface for scheduling and managing local notifications.
 */
interface NotificationScheduler {
    fun scheduleHabitReminder(habit: Habit)
    fun cancelHabitReminder(habitId: Long)
    fun updateAllHabitReminders(habits: List<Habit>, userSettings: UserSettings)
    fun initializeNotificationChannel()
}