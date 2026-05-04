package com.androidforge.streakflow.domain.usecase.ads

import com.google.android.gms.ads.interstitial.InterstitialAd
import com.androidforge.streakflow.core.common.Result
import com.androidforge.streakflow.core.utils.AdManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadInterstitialAdUseCase @Inject constructor(
    private val adManager: AdManager
) {
    operator fun invoke(adUnitId: String): Flow<Result<InterstitialAd>> = flow {
        emit(Result.Loading)
        try {
            val interstitialAd = adManager.loadInterstitialAd(adUnitId)
            if (interstitialAd != null) {
                emit(Result.Success(interstitialAd))
            } else {
                emit(Result.Error(message = "Failed to load interstitial ad."))
            }
        } catch (e: Exception) {
            emit(Result.Error(e, "Error loading interstitial ad."))
        }
    }
}