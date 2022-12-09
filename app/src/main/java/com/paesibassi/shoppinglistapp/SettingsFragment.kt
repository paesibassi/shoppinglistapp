package com.paesibassi.shoppinglistapp

import android.os.Bundle
import android.text.InputFilter
import androidx.preference.EditTextPreference
import androidx.preference.PreferenceFragmentCompat

class SettingsFragment : PreferenceFragmentCompat() {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)
        val namePreference: EditTextPreference? = findPreference("name")
        namePreference?.setOnBindEditTextListener {
            it.filters = arrayOf<InputFilter>(InputFilter.LengthFilter(25))
        }

    }
}