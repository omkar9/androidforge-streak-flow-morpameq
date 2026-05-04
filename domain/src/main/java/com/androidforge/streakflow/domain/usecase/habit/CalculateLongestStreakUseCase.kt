package com.androidforge.streakflow.domain.usecase.habit

import com.androidforge.streakflow.core.common.Result
import com.androidforge.streakflow.domain.model.FrequencyType
import com.androidforge.streakflow.domain.model.Habit
import com.androidforge.streakflow.domain.repository.HabitRepository
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject
import kotlin.math.max

class CalculateLongestStreakUseCase @Inject constructor(
    private val habitRepository: HabitRepository,
    private val getHabitUseCase: GetHabitUseCase
) {
    suspend operator fun invoke(habitId: Long): Int {
        val habitResult = getHabitUseCase(habitId)
        val completionsResult = habitRepository.getHabitCompletions(habitId)

        val habit = (habitResult as? Result.Success)?.data ?: return 0
        val completedDates = completionsResult.filter { it.completed }.map { it.date }.toSet()

        var longestStreak = 0
        var currentStreak = 0
        var iterateDate = habit.startDate

        while (iterateDate <= LocalDate.now()) {
            val isScheduled = isHabitScheduledOnDate(habit, iterateDate)
            val isCompleted = completedDates.contains(iterateDate)

            if (isScheduled) {
                if (isCompleted) {
                    currentStreak++
                } else {
                    // Missed a scheduled day, streak breaks
                    longestStreak = max(longestStreak, currentStreak)
                    currentStreak = 0
                }
            } else {
                // Not scheduled, current streak is not affected
            }
            iterateDate = iterateDate.plusDays(1)
        }
        longestStreak = max(longestStreak, currentStreak) // Check streak at the end of the timeline
        return longestStreak
    }

    private fun isHabitScheduledOnDate(habit: Habit, date: LocalDate): Boolean {
        return when (habit.frequencyType) {
            FrequencyType.DAILY -> true
            FrequencyType.SPECIFIC_DAYS -> habit.frequencyDays.contains(date.dayOfWeek)
        }
    }
}