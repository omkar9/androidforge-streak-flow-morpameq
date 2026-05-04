package com.androidforge.streakflow.data.notifications

import android.Manifest
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.androidforge.streakflow.MainActivity
import com.androidforge.streakflow.R
import com.androidforge.streakflow.core.common.Constants

class HabitReminderBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent?) {
        val habitId = intent?.getLongExtra("HABIT_ID", -1L) ?: -1L
        val habitName = intent?.getStringExtra("HABIT_NAME") ?: "Your Habit"

        if (habitId != -1L) {
            showNotification(context, habitId, habitName)
        }
    }

    private fun showNotification(context: Context, habitId: Long, habitName: String) {
        val tapIntent = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
            // Optionally navigate to a specific habit details screen
            // putExtra("NAVIGATE_TO_HABIT_DETAILS", habitId)
        }
        val pendingIntent: PendingIntent = PendingIntent.getActivity(
            context,
            habitId.toInt(), // Use habitId as request code for uniqueness
            tapIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val builder = NotificationCompat.Builder(context, Constants.NOTIFICATION_CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_launcher_foreground) // Use a proper icon
            .setContentTitle("Time to complete your habit!")
            .setContentText("Don't forget to \"$habitName\" today.")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)

        with(NotificationManagerCompat.from(context)) {
            if (ActivityCompat.checkSelfPermission(
                    context,
                    Manifest.permission.POST_NOTIFICATIONS
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // Permissions not granted, cannot show notification. Log or handle gracefully.
                return
            }
            notify(habitId.toInt(), builder.build()) // Use habitId as notification ID
        }
    }
}