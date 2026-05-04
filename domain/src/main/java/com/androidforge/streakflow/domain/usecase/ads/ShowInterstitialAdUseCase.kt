package com.androidforge.streakflow.domain.usecase.ads

import com.google.android.gms.ads.interstitial.InterstitialAd
import com.androidforge.streakflow.core.common.Result
import com.androidforge.streakflow.core.utils.AdManager
import javax.inject.Inject

class ShowInterstitialAdUseCase @Inject constructor(
    private val adManager: AdManager
) {
    operator fun invoke(interstitialAd: InterstitialAd): Result<Unit> {
        return try {
            adManager.showInterstitialAd(interstitialAd)
            Result.Success(Unit)
        } catch (e: Exception) {
            Result.Error(e, "Failed to show interstitial ad.")
        }
    }
}