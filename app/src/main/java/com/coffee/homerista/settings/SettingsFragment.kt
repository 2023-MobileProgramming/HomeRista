package com.coffee.homerista.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import com.coffee.homerista.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
    }
}