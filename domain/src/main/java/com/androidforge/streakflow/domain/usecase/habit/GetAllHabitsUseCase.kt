package com.androidforge.streakflow.domain.usecase.habit

import com.androidforge.streakflow.core.common.Result
import com.androidforge.streakflow.domain.model.Habit
import com.androidforge.streakflow.domain.repository.HabitRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import java.io.IOException
import javax.inject.Inject

class GetAllHabitsUseCase @Inject constructor(
    private val habitRepository: HabitRepository
) {
    operator fun invoke(archived: Boolean = false): Flow<Result<List<Habit>>> {
        return habitRepository.getAllHabits(archived)
            .map { habits ->
                Result.Success(habits)
            }
            .onStart { emit(Result.Loading) }
            .catch { e ->
                if (e is IOException) {
                    emit(Result.Error(e, "Network or database error. Please check your connection."))
                } else {
                    emit(Result.Error(e, "An unexpected error occurred while loading habits."))
                }
            }
    }
}