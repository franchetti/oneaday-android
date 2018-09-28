package gq.emiliodallatorre.oneaday.iterator

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.content.ContextCompat.getSystemService
import android.support.v7.preference.PreferenceFragmentCompat
import android.support.v7.preference.PreferenceManager
import java.util.*

class SettingsFragment: PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // Load the preferences from an XML resource.
        setPreferencesFromResource(R.xml.settings, rootKey)
        findPreference("apticFeedback").isEnabled = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("notificationsSwitch", true)
        findPreference("notificationsTime").isEnabled = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("notificationsSwitch", true)
        findPreference("firstStart").isVisible = false
        PreferenceManager.getDefaultSharedPreferences(context).registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        findPreference("apticFeedback").isEnabled = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("notificationsSwitch", true)
        findPreference("notificationsTime").isEnabled = PreferenceManager.getDefaultSharedPreferences(context).getBoolean("notificationsSwitch", true)
        updateAlarm()
        PreferenceManager.getDefaultSharedPreferences(context).registerOnSharedPreferenceChangeListener(this)
    }

    fun updateAlarm() {
        val pendingIntent = PendingIntent.getBroadcast(context, 1201, Intent(BROADCAST), PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.cancel(pendingIntent)

        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        when (PreferenceManager.getDefaultSharedPreferences(context).getInt("notificationsTime", 0)) {
            0 -> calendar.set(Calendar.HOUR_OF_DAY, 9)
            1 -> calendar.set(Calendar.HOUR_OF_DAY, 12)
            2 -> calendar.set(Calendar.HOUR_OF_DAY, 18)
        }

        calendar.set(Calendar.MINUTE, 0)
        calendar.set(Calendar.SECOND, 0)

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, (1000 * 60 * 60 * 24).toLong(), pendingIntent)
    }
}
