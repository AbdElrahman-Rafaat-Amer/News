package com.abdelrahman.rafaat.news.ui.setting

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.abdelrahman.rafaat.news.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}