package com.abdelrahman.rafaat.news.ui.setting

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.abdelrahman.rafaat.news.databinding.ActivitySettingBinding
import com.google.android.gms.ads.AdListener
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.LoadAdError
import com.google.android.gms.ads.MobileAds

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
            override fun onAdClicked() {}
            override fun onAdClosed() {}
            override fun onAdFailedToLoad(adError : LoadAdError) {}
            override fun onAdImpression() {}
            override fun onAdLoaded() {}
            override fun onAdOpened() {}
        }

        binding.adBannerViewBottom.loadAd(adRequestBottom)
        binding.adBannerViewBottom.adListener = object: AdListener() {
            override fun onAdClicked() {}
            override fun onAdClosed() {}
            override fun onAdFailedToLoad(adError : LoadAdError) {}
            override fun onAdImpression() {}
            override fun onAdLoaded() {}
            override fun onAdOpened() {}
        }
    }
}