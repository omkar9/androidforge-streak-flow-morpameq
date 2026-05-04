package com.androidforge.streakflow.data.local.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverter
import androidx.room.TypeConverters
import com.androidforge.streakflow.data.local.dao.HabitCompletionDao
import com.androidforge.streakflow.data.local.dao.HabitDao
import com.androidforge.streakflow.data.local.dao.UserSettingsDao
import com.androidforge.streakflow.data.local.entity.HabitCompletionEntity
import com.androidforge.streakflow.data.local.entity.HabitEntity
import com.androidforge.streakflow.data.local.entity.UserSettingsEntity
import com.androidforge.streakflow.domain.model.FrequencyType
import java.time.DayOfWeek
import java.time.LocalDate
import java.time.LocalTime
import java.time.format.DateTimeFormatter

@Database(
    entities = [
        HabitEntity::class,
        HabitCompletionEntity::class,
        UserSettingsEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    Converters.LocalDateConverter::class,
    Converters.LocalTimeConverter::class,
    Converters.DayOfWeekSetConverter::class,
    Converters.FrequencyTypeConverter::class
)
abstract class StreakFlowDatabase : RoomDatabase() {
    abstract fun habitDao(): HabitDao
    abstract fun habitCompletionDao(): HabitCompletionDao
    abstract fun userSettingsDao(): UserSettingsDao
}

object Converters {
    @TypeConverter
    fun fromLocalDate(date: LocalDate?): String? {
        return date?.format(DateTimeFormatter.ISO_LOCAL_DATE)
    }

    @TypeConverter
    fun toLocalDate(dateString: String?): LocalDate? {
        return dateString?.let { LocalDate.parse(it, DateTimeFormatter.ISO_LOCAL_DATE) }
    }

    @TypeConverter
    fun fromLocalTime(time: LocalTime?): String? {
        return time?.format(DateTimeFormatter.ISO_LOCAL_TIME)
    }

    @TypeConverter
    fun toLocalTime(timeString: String?): LocalTime? {
        return timeString?.let { LocalTime.parse(it, DateTimeFormatter.ISO_LOCAL_TIME) }
    }

    @TypeConverter
    fun fromDayOfWeekSet(daySet: Set<DayOfWeek>?): String? {
        return daySet?.joinToString(",") { it.name }
    }

    @TypeConverter
    fun toDayOfWeekSet(dayString: String?): Set<DayOfWeek>? {
        return dayString?.split(",")?.filter { it.isNotBlank() }?.map { DayOfWeek.valueOf(it) }?.toSet()
    }

    @TypeConverter
    fun fromFrequencyType(type: FrequencyType?): String? {
        return type?.name
    }

    @TypeConverter
    fun toFrequencyType(typeString: String?): FrequencyType? {
        return typeString?.let { FrequencyType.valueOf(it) }
    }
}