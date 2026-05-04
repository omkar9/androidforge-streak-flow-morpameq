package com.androidforge.streakflow.data.ads

import android.app.Activity
import android.content.Context
import android.util.Log
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.AdSize
import com.google.android.gms.ads.AdView
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.interstitial.InterstitialAd
import com.google.android.gms.ads.interstitial.InterstitialAdLoadCallback
import com.androidforge.streakflow.core.utils.AdManager
import kotlinx.coroutines.suspendCancellableCoroutine
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.coroutines.resume

@Singleton
class AdMobManager @Inject constructor(
    private val context: Context
) : AdManager {

    override suspend fun loadBannerAd(adUnitId: String): AdView? = suspendCancellableCoroutine {\ continuation ->
        val adView = AdView(context).apply {
            setAdSize(AdSize.BANNER)
            this.adUnitId = adUnitId
            adListener = object : AdListener() {
                override fun onAdLoaded() {
                    Log.d("AdMobManager", "Banner ad loaded successfully.")
                    if (continuation.isActive) {
                        continuation.resume(this@apply)
                    }
                }

                override fun onAdFailedToLoad(adError: LoadAdError) {
                    Log.e("AdMobManager", "Banner ad failed to load: ${adError.message}")
                    if (continuation.isActive) {
                        continuation.resume(null)
                    }
                }
            }
        }
        adView.loadAd(AdRequest.Builder().build())

        continuation.invokeOnCancellation { adView.destroy() }
    }

    override suspend fun loadInterstitialAd(adUnitId: String): InterstitialAd? = suspendCancellableCoroutine { continuation ->
        val adRequest = AdRequest.Builder().build()
        InterstitialAd.load(context, adUnitId, adRequest, object : InterstitialAdLoadCallback() {
            override fun onAdFailedToLoad(adError: LoadAdError) {
                Log.e("AdMobManager", "Interstitial ad failed to load: ${adError.message}")
                if (continuation.isActive) {
                    continuation.resume(null)
                }
            }

            override fun onAdLoaded(interstitialAd: InterstitialAd) {
                Log.d("AdMobManager", "Interstitial ad loaded successfully.")
                if (continuation.isActive) {
                    continuation.resume(interstitialAd)
                }
            }
        })
    }

    override fun showInterstitialAd(interstitialAd: InterstitialAd) {
        if (context is Activity) {
            interstitialAd.show(context)
        } else {
            Log.e("AdMobManager", "Cannot show interstitial ad: context is not an Activity.")
        }
    }

    override fun destroyBanner(adView: AdView) {
        adView.destroy()
    }

    override fun pauseBanner(adView: AdView) {
        adView.pause()
    }

    override fun resumeBanner(adView: AdView) {
        adView.resume()
    }
}