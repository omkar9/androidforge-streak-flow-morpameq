package com.androidforge.streakflow.di

import android.content.Context
import androidx.room.Room
import com.androidforge.streakflow.core.common.Constants
import com.androidforge.streakflow.core.utils.AdManager
import com.androidforge.streakflow.core.utils.NotificationScheduler
import com.androidforge.streakflow.data.ads.AdMobManager
import com.androidforge.streakflow.data.local.database.StreakFlowDatabase
import com.androidforge.streakflow.data.notifications.AndroidNotificationScheduler
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideStreakFlowDatabase(@ApplicationContext context: Context): StreakFlowDatabase {
        return Room.databaseBuilder(
            context,
            StreakFlowDatabase::class.java,
            Constants.DATABASE_NAME
        ).build()
    }

    @Provides
    @Singleton
    fun provideHabitDao(database: StreakFlowDatabase) = database.habitDao()

    @Provides
    @Singleton
    fun provideHabitCompletionDao(database: StreakFlowDatabase) = database.habitCompletionDao()

    @Provides
    @Singleton
    fun provideUserSettingsDao(database: StreakFlowDatabase) = database.userSettingsDao()

    @Provides
    @Singleton
    fun provideAdManager(@ApplicationContext context: Context): AdManager {
        return AdMobManager(context)
    }

    @Provides
    @Singleton
    fun provideNotificationScheduler(@ApplicationContext context: Context): NotificationScheduler {
        return AndroidNotificationScheduler(context)
    }
}