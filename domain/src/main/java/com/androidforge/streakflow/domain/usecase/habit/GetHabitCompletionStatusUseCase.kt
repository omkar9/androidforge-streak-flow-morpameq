package com.androidforge.streakflow.domain.usecase.habit

import com.androidforge.streakflow.core.common.Result
import com.androidforge.streakflow.domain.repository.HabitRepository
import java.time.LocalDate
import javax.inject.Inject

class GetHabitCompletionStatusUseCase @Inject constructor(
    private val habitRepository: HabitRepository
) {
    suspend operator fun invoke(habitId: Long, date: LocalDate): Boolean {
        return try {
            habitRepository.getHabitCompletionStatus(habitId, date)
        } catch (e: Exception) {
            // Log error, but return false to indicate status is unknown/not completed
            false
        }
    }
}