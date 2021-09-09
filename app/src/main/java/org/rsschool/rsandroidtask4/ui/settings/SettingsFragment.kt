package org.rsschool.rsandroidtask4.ui.settings

import android.os.Bundle
import androidx.preference.PreferenceFragmentCompat
import org.rsschool.rsandroidtask4.R

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.settings, rootKey)
    }
}