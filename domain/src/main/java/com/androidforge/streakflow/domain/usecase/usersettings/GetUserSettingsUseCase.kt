package com.androidforge.streakflow.domain.usecase.usersettings

import com.androidforge.streakflow.core.common.Result
import com.androidforge.streakflow.domain.model.UserSettings
import com.androidforge.streakflow.domain.repository.UserSettingsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.io.IOException
import javax.inject.Inject

class GetUserSettingsUseCase @Inject constructor(
    private val userSettingsRepository: UserSettingsRepository
) {
    operator fun invoke(): Flow<Result<UserSettings>> {
        return userSettingsRepository.getUserSettings()
            .map { settings ->
                Result.Success(settings)
            }
            .onStart { emit(Result.Loading) }
            .catch { e ->
                if (e is IOException) {
                    emit(Result.Error(e, "Network or database error. Could not load settings."))
                } else {
                    emit(Result.Error(e, "An unexpected error occurred while loading settings."))
                }
            }
    }

    suspend fun getSnapshot(): UserSettings {
        return userSettingsRepository.getSettingsSnapshot()
    }
}