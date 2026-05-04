package com.androidforge.streakflow.domain.usecase.usersettings

import com.androidforge.streakflow.core.common.Result
import com.androidforge.streakflow.domain.model.UserSettings
import com.androidforge.streakflow.domain.repository.UserSettingsRepository
import javax.inject.Inject

class UpdateUserSettingsUseCase @Inject constructor(
    private val userSettingsRepository: UserSettingsRepository
) {
    suspend operator fun invoke(settings: UserSettings): Result<Unit> {
        return try {
            userSettingsRepository.updateUserSettings(settings)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e, "Failed to update user settings.")
        }
    }
}