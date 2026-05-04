package com.androidforge.streakflow.domain.usecase.habit

import com.androidforge.streakflow.core.common.Result
import com.androidforge.streakflow.domain.repository.HabitRepository
import javax.inject.Inject

class ArchiveHabitUseCase @Inject constructor(
    private val habitRepository: HabitRepository
) {
    suspend operator fun invoke(habitId: Long, archive: Boolean): Result<Unit> {
        return try {
            habitRepository.archiveHabit(habitId, archive)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e, "Failed to archive/unarchive habit.")
        }
    }
}