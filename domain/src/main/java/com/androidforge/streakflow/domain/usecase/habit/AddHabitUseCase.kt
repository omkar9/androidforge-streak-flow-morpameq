package com.androidforge.streakflow.domain.usecase.habit

import com.androidforge.streakflow.core.common.Result
import com.androidforge.streakflow.domain.model.Habit
import com.androidforge.streakflow.domain.repository.HabitRepository
import javax.inject.Inject

class AddHabitUseCase @Inject constructor(
    private val habitRepository: HabitRepository
) {
    suspend operator fun invoke(habit: Habit): Result<Long> {
        return try {
            val newHabitId = habitRepository.addHabit(habit)
            Result.Success(newHabitId)
        } catch (e: Exception) {
            Result.Error(e, "Failed to add habit.")
        }
    }
}