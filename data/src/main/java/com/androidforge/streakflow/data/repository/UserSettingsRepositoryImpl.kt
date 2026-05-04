package com.androidforge.streakflow.data.repository

import com.androidforge.streakflow.data.local.dao.UserSettingsDao
import com.androidforge.streakflow.data.local.mapper.toDomain
import com.androidforge.streakflow.data.local.mapper.toEntity
import com.androidforge.streakflow.domain.model.UserSettings
import com.androidforge.streakflow.domain.repository.UserSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserSettingsRepositoryImpl @Inject constructor(
    private val userSettingsDao: UserSettingsDao
) : UserSettingsRepository {

    override fun getUserSettings(): Flow<UserSettings> {
        // Provide a default if settings are not found in DB
        return userSettingsDao.getUserSettings().map { entity ->
            entity?.toDomain() ?: UserSettings()
        }
    }

    override suspend fun getSettingsSnapshot(): UserSettings {
        return userSettingsDao.getSettingsSnapshot()?.toDomain() ?: UserSettings()
    }

    override suspend fun updateUserSettings(settings: UserSettings) {
        userSettingsDao.updateUserSettings(settings.toEntity())
    }

    override suspend fun initializeUserSettings(settings: UserSettings) {
        // Only insert if settings don't exist (e.g., first app launch)
        if (userSettingsDao.getSettingsSnapshot() == null) {
            userSettingsDao.insertUserSettings(settings.toEntity())
        }
    }
}