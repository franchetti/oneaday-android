package gq.emiliodallatorre.oneaday.iterator

import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import android.support.v7.preference.PreferenceManager

class SettingsFragment: PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // Load the preferences from an XML resource.
        setPreferencesFromResource(R.xml.settings, rootKey)
        findPreference("apticFeedback").isEnabled = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("notificationsSwitch", true)
        findPreference("notificationsTime").isEnabled = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("notificationsSwitch", true)
        PreferenceManager.getDefaultSharedPreferences(context).registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        findPreference("apticFeedback").isEnabled = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("notificationsSwitch", true)
        findPreference("notificationsTime").isEnabled = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("notificationsSwitch", true)
        PreferenceManager.getDefaultSharedPreferences(context).registerOnSharedPreferenceChangeListener(this)
    }
}
