package com.androidforge.streakflow.data.local.mapper

import com.androidforge.streakflow.data.local.entity.UserSettingsEntity
import com.androidforge.streakflow.domain.model.UserSettings

fun UserSettingsEntity.toDomain(): UserSettings {
    return UserSettings(
        id = id,
        enableNotifications = enableNotifications,
        notificationTime = notificationTime,
        theme = theme
    )
}

fun UserSettings.toEntity(): UserSettingsEntity {
    return UserSettingsEntity(
        id = id,
        enableNotifications = enableNotifications,
        notificationTime = notificationTime,
        theme = theme
    )
}