package com.androidforge.streakflow.domain.usecase.ads

import com.google.android.gms.ads.AdView
import com.androidforge.streakflow.core.common.Result
import com.androidforge.streakflow.core.utils.AdManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class LoadBannerAdUseCase @Inject constructor(
    private val adManager: AdManager
) {
    operator fun invoke(adUnitId: String): Flow<Result<AdView>> = flow {
        emit(Result.Loading)
        try {
            val adView = adManager.loadBannerAd(adUnitId)
            if (adView != null) {
                emit(Result.Success(adView))
            } else {
                emit(Result.Error(message = "Failed to load banner ad."))
            }
        } catch (e: Exception) {
            emit(Result.Error(e, "Error loading banner ad."))
        }
    }
}