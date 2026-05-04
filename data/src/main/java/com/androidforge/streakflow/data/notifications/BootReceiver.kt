package com.androidforge.streakflow.data.notifications

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.androidforge.streakflow.domain.usecase.habit.GetAllHabitsUseCase
import com.androidforge.streakflow.domain.usecase.usersettings.GetUserSettingsUseCase
import com.androidforge.streakflow.core.utils.NotificationScheduler
import com.androidforge.streakflow.core.common.Result
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class BootReceiver : BroadcastReceiver() {

    @Inject
    lateinit var getAllHabitsUseCase: GetAllHabitsUseCase

    @Inject
    lateinit var getUserSettingsUseCase: GetUserSettingsUseCase

    @Inject
    lateinit var notificationScheduler: NotificationScheduler

    override fun onReceive(context: Context?, intent: Intent?) {
        if (intent?.action == Intent.ACTION_BOOT_COMPLETED || intent?.action == Intent.ACTION_MY_PACKAGE_REPLACED) {
            Log.d("BootReceiver", "Boot or package replaced action received. Re-scheduling notifications.")

            // Use a CoroutineScope to perform suspend calls
            val scope = CoroutineScope(Dispatchers.IO)
            scope.launch {
                // Initialize notification channel (safe to call multiple times)
                notificationScheduler.initializeNotificationChannel()

                val habitsResult = getAllHabitsUseCase(false).first()
                val userSettingsResult = getUserSettingsUseCase().first()

                if (habitsResult is Result.Success && userSettingsResult is Result.Success) {
                    notificationScheduler.updateAllHabitReminders(
                        habitsResult.data,
                        userSettingsResult.data
                    )
                    Log.d("BootReceiver", "Habit reminders re-scheduled successfully.")
                } else {
                    Log.e("BootReceiver", "Failed to re-schedule habits. Habits: ${habitsResult} Settings: ${userSettingsResult}")
                }
            }
        }
    }
}