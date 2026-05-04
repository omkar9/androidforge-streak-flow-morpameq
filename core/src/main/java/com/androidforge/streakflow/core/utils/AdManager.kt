package com.androidforge.streakflow.core.utils

import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.interstitial.InterstitialAd
import android.content.Context

/**
 * Interface for AdMob operations, abstracting ad loading/display logic from UI and business logic.
 */
interface AdManager {
    suspend fun loadBannerAd(adUnitId: String): AdView?
    suspend fun loadInterstitialAd(adUnitId: String): InterstitialAd?
    fun showInterstitialAd(interstitialAd: InterstitialAd)
    fun destroyBanner(adView: AdView)
    fun pauseBanner(adView: AdView)
    fun resumeBanner(adView: AdView)
}