package gq.emiliodallatorre.oneaday.iterator

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v7.preference.PreferenceFragmentCompat
import android.support.v7.preference.PreferenceManager
import java.util.*

class SettingsFragment : PreferenceFragmentCompat(), SharedPreferences.OnSharedPreferenceChangeListener {
    // Save an instance of PreferenceManager, universal for this class.
    private lateinit var sharedPreferences: SharedPreferences

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        // Initialize the sharedPreferences var.
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context)

        // Load the preferences from an XML resource.
        setPreferencesFromResource(R.xml.settings, rootKey)
        findPreference("apticFeedback").isEnabled = sharedPreferences.getBoolean("notificationsSwitch", true)
        findPreference("notificationsTime").isEnabled = sharedPreferences.getBoolean("notificationsSwitch", true)
        findPreference("firstStart").isVisible = false
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        findPreference("apticFeedback").isEnabled = sharedPreferences.getBoolean("notificationsSwitch", true)
        findPreference("notificationsTime").isEnabled = sharedPreferences.getBoolean("notificationsSwitch", true)
        updateAlarm(Integer.parseInt(sharedPreferences.getString("notificationsTime", "0")))
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    private fun updateAlarm(notificationsTime: Int) {
        val pendingIntent = PendingIntent.getBroadcast(context, 1201, Intent(context, NotificationReceiver::class.java), PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.cancel(pendingIntent)

        if(sharedPreferences.getBoolean("notificationsSwitch", true)) {
            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()

            when (notificationsTime) {
                0 -> calendar.set(Calendar.HOUR_OF_DAY, 9)
                1 -> calendar.set(Calendar.HOUR_OF_DAY, 12)
                2 -> calendar.set(Calendar.HOUR_OF_DAY, 18)
            }

            calendar.set(Calendar.MINUTE, 36)
            calendar.set(Calendar.SECOND, 0)

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, (1000 * 60 * 60 * 24).toLong(), pendingIntent)
        }
    }
}
