package com.androidforge.streakflow.domain.usecase.habit

import com.androidforge.streakflow.core.common.Result
import com.androidforge.streakflow.domain.model.Habit
import com.androidforge.streakflow.domain.repository.HabitRepository
import kotlinx.coroutines.flow.firstOrNull
import javax.inject.Inject

class GetHabitUseCase @Inject constructor(
    private val habitRepository: HabitRepository
) {
    suspend operator fun invoke(habitId: Long): Result<Habit> {
        return try {
            val habit = habitRepository.getHabitById(habitId).firstOrNull()
            if (habit != null) {
                Result.Success(habit)
            } else {
                Result.Error(message = "Habit not found with ID: $habitId")
            }
        } catch (e: Exception) {
            Result.Error(e, "Failed to retrieve habit.")
        }
    }
}