package com.androidforge.streakflow

import android.app.Application
import com.google.android.gms.ads.MobileAds
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class StreakFlowApp : Application() {
    override fun onCreate() {
        super.onCreate()
        // Initialize the Mobile Ads SDK.
        MobileAds.initialize(this) {}
    }
}