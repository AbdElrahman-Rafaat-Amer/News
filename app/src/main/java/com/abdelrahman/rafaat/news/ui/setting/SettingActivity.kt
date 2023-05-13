package com.abdelrahman.rafaat.news.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.abdelrahman.rafaat.news.databinding.ActivitySettingBinding
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds

private const val TAG = "SettingActivity"

class SettingActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySettingBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySettingBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initBannerAds()
    }

    private fun initBannerAds() {
        MobileAds.initialize(this) {
        }

        val adRequestTop = AdRequest.Builder().build()
        val adRequestBottom = AdRequest.Builder().build()
        binding.adBannerViewTop.loadAd(adRequestTop)
        binding.adBannerViewTop.adListener = object: AdListener() {
            override fun onAdClicked() {
                Log.i(TAG, "onAdClicked: ")
            }

            override fun onAdClosed() {
                Log.i(TAG, "onAdClosed: ")
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                Log.i(TAG, "onAdFailedToLoad:adError " + adError.message)
            }

            override fun onAdImpression() {
                Log.i(TAG, "onAdImpression: ")
            }

            override fun onAdLoaded() {
                Log.i(TAG, "onAdLoaded: ")
            }

            override fun onAdOpened() {
                Log.i(TAG, "onAdOpened: ")
            }
        }

        binding.adBannerViewBottom.loadAd(adRequestBottom)
        binding.adBannerViewBottom.adListener = object: AdListener() {
            override fun onAdClicked() {
                Log.i(TAG, "onAdClicked: ")
            }

            override fun onAdClosed() {
                Log.i(TAG, "onAdClosed: ")
            }

            override fun onAdFailedToLoad(adError : LoadAdError) {
                Log.i(TAG, "onAdFailedToLoad:adError " + adError.message)
            }

            override fun onAdImpression() {
                Log.i(TAG, "onAdImpression: ")
            }

            override fun onAdLoaded() {
                Log.i(TAG, "onAdLoaded: ")
            }

            override fun onAdOpened() {
                Log.i(TAG, "onAdOpened: ")
            }
        }
    }
}