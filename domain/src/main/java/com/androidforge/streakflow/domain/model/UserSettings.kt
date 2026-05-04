package com.androidforge.streakflow.domain.model

import java.time.LocalTime

/**
 * Domain model representing user-specific settings.
 */
data class UserSettings(
    val id: Long = 1L, // Assuming a single user settings entry with a fixed ID
    val enableNotifications: Boolean = true,
    val notificationTime: LocalTime = LocalTime.of(9, 0), // Default reminder time
    val theme: String = "system_default" // e.g., "system_default", "dark", "light"
)