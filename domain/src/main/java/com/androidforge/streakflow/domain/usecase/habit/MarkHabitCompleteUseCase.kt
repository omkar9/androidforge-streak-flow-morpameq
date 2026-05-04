package com.androidforge.streakflow.domain.usecase.habit

import com.androidforge.streakflow.core.common.Result
import com.androidforge.streakflow.domain.repository.HabitRepository
import java.time.LocalDate
import javax.inject.Inject

class MarkHabitCompleteUseCase @Inject constructor(
    private val habitRepository: HabitRepository
) {
    suspend operator fun invoke(habitId: Long, date: LocalDate): Result<Unit> {
        return try {
            habitRepository.markHabitComplete(habitId, date)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e, "Failed to mark habit complete.")
        }
    }
}