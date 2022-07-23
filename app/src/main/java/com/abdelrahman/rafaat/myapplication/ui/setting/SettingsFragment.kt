package com.abdelrahman.rafaat.myapplication.ui.setting

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.abdelrahman.rafaat.myapplication.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}