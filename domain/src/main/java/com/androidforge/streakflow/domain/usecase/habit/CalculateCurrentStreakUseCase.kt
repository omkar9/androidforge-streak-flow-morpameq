package com.androidforge.streakflow.domain.usecase.habit

import com.androidforge.streakflow.core.common.Result
import com.androidforge.streakflow.domain.model.FrequencyType
import com.androidforge.streakflow.domain.model.Habit
import com.androidforge.streakflow.domain.repository.HabitRepository
import java.time.DayOfWeek
import java.time.LocalDate
import javax.inject.Inject
import kotlin.math.max

class CalculateCurrentStreakUseCase @Inject constructor(
    private val habitRepository: HabitRepository,
    private val getHabitUseCase: GetHabitUseCase
) {
    suspend operator fun invoke(habitId: Long): Int {
        val habitResult = getHabitUseCase(habitId)
        val completionsResult = habitRepository.getHabitCompletions(habitId)

        val habit = (habitResult as? Result.Success)?.data ?: return 0
        val completedDates = completionsResult.filter { it.completed }.map { it.date }.toSet()

        var currentStreak = 0
        var checkDate = LocalDate.now()

        // If today's scheduled habit is not completed, current streak ends yesterday.
        val isTodayScheduled = isHabitScheduledOnDate(habit, checkDate)
        val isTodayCompleted = completedDates.contains(checkDate)

        if (isTodayScheduled && !isTodayCompleted) {
            checkDate = checkDate.minusDays(1)
        }

        while (checkDate >= habit.startDate) {
            val isScheduled = isHabitScheduledOnDate(habit, checkDate)
            val isCompleted = completedDates.contains(checkDate)

            if (isScheduled) {
                if (isCompleted) {
                    currentStreak++
                } else {
                    // Missed a scheduled day, streak breaks
                    break
                }
            }
            checkDate = checkDate.minusDays(1)
        }
        return currentStreak
    }

    private fun isHabitScheduledOnDate(habit: Habit, date: LocalDate): Boolean {
        return when (habit.frequencyType) {
            FrequencyType.DAILY -> true
            FrequencyType.SPECIFIC_DAYS -> habit.frequencyDays.contains(date.dayOfWeek)
        }
    }
}