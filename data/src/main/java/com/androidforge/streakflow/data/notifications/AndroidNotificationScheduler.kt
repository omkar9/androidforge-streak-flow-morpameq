package com.androidforge.streakflow.data.notifications

import android.app.AlarmManager
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import android.util.Log
import com.androidforge.streakflow.core.common.Constants
import com.androidforge.streakflow.core.utils.NotificationScheduler
import com.androidforge.streakflow.domain.model.FrequencyType
import com.androidforge.streakflow.domain.model.Habit
import com.androidforge.streakflow.domain.model.UserSettings
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.Calendar
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class AndroidNotificationScheduler @Inject constructor(
    private val context: Context
) : NotificationScheduler {

    private val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
    private val notificationManager = context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    override fun initializeNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel(
                Constants.NOTIFICATION_CHANNEL_ID,
                Constants.NOTIFICATION_CHANNEL_NAME,
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Reminders for your daily habits."
            }
            notificationManager.createNotificationChannel(channel)
        }
    }

    override fun scheduleHabitReminder(habit: Habit) {
        if (habit.reminderTime == null || habit.archived) return

        val intent = Intent(context, HabitReminderBroadcastReceiver::class.java).apply {
            putExtra("HABIT_ID", habit.id)
            putExtra("HABIT_NAME", habit.name)
            // Add other habit details if needed for notification content
        }

        // Use a unique request code for each habit's reminder
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            habit.id.toInt(),
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val now = LocalDate.now()
        val reminderDateTime = LocalDateTime.of(now, habit.reminderTime)

        // Calculate the next reminder time based on frequency
        var nextReminderCal = Calendar.getInstance().apply {
            timeInMillis = reminderDateTime.atZone(ZoneId.systemDefault()).toInstant().toEpochMilli()
        }

        // If the reminder time for today has already passed, schedule for tomorrow
        if (System.currentTimeMillis() > nextReminderCal.timeInMillis) {
            nextReminderCal.add(Calendar.DAY_OF_YEAR, 1)
        }

        // Adjust for specific days frequency
        if (habit.frequencyType == FrequencyType.SPECIFIC_DAYS) {
            var foundNextDay = false
            for (i in 0..7) { // Check up to 7 days in future
                val dayOfWeek = LocalDate.ofEpochDay(nextReminderCal.timeInMillis / (1000 * 60 * 60 * 24)).dayOfWeek
                if (habit.frequencyDays.contains(dayOfWeek)) {
                    foundNextDay = true
                    break
                }
                nextReminderCal.add(Calendar.DAY_OF_YEAR, 1)
            }
            if (!foundNextDay) {
                Log.w("NotificationScheduler", "No future scheduled day found for habit ${habit.id}. Not scheduling.")
                return // No valid day in the next week, don't schedule
            }
        }

        val interval = AlarmManager.INTERVAL_DAY // Daily interval for repeating

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S && !alarmManager.canScheduleExactAlarms()) {
            // Handle cases where exact alarms cannot be scheduled (e.g., user disabled permission)
            Log.w("NotificationScheduler", "Exact alarms cannot be scheduled. Habit reminder for ${habit.name} may be delayed.")
            alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, nextReminderCal.timeInMillis, pendingIntent)
        } else if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP, nextReminderCal.timeInMillis, pendingIntent)
        } else {
            alarmManager.setExact(AlarmManager.RTC_WAKEUP, nextReminderCal.timeInMillis, pendingIntent)
        }

        Log.d("NotificationScheduler", "Scheduled reminder for habit ${habit.name} at ${nextReminderCal.time}")
    }

    override fun cancelHabitReminder(habitId: Long) {
        val intent = Intent(context, HabitReminderBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(
            context,
            habitId.toInt(),
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE // Use FLAG_NO_CREATE to avoid creating if not exists
        )
        pendingIntent?.let { alarmManager.cancel(it) }
        Log.d("NotificationScheduler", "Cancelled reminder for habit $habitId")
    }

    override fun updateAllHabitReminders(habits: List<Habit>, userSettings: UserSettings) {
        // Cancel all existing reminders first
        habits.forEach { cancelHabitReminder(it.id) }

        if (userSettings.enableNotifications) {
            // Reschedule only active habits with a reminder time
            habits.filter { !it.archived && it.reminderTime != null }.forEach { habit ->
                scheduleHabitReminder(habit)
            }
        }
    }
}