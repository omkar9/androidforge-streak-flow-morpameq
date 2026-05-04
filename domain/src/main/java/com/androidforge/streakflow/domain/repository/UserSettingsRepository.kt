package com.androidforge.streakflow.domain.repository

import com.androidforge.streakflow.domain.model.UserSettings
import kotlinx.coroutines.flow.Flow

/**
 * Repository interface for managing user settings.
 */
interface UserSettingsRepository {
    fun getUserSettings(): Flow<UserSettings>
    suspend fun getSettingsSnapshot(): UserSettings // For immediate access without flow
    suspend fun updateUserSettings(settings: UserSettings)
    suspend fun initializeUserSettings(settings: UserSettings)
}