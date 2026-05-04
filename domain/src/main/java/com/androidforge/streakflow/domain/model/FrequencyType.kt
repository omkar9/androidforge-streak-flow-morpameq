package com.androidforge.streakflow.domain.model

import androidx.annotation.StringRes
import com.androidforge.streakflow.R

/**
 * Defines how often a habit should be performed.
 */
enum class FrequencyType(@StringRes val labelResId: Int) {
    DAILY(R.string.frequency_daily),
    SPECIFIC_DAYS(R.string.frequency_specific_days),
    // Add more types if needed, e.g., WEEKLY, MONTHLY
}