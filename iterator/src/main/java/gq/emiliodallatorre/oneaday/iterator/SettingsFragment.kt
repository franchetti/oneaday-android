package gq.emiliodallatorre.oneaday.iterator

import android.app.Activity
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.support.v4.app.NotificationManagerCompat
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
        findPreference("firstStart").isVisible = false
        updateEnabling()
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences, key: String?) {
        updateEnabling()
        updateAlarm()
        sharedPreferences.registerOnSharedPreferenceChangeListener(this)
    }

    private fun updateAlarm() {
        val pendingIntent = PendingIntent.getBroadcast(context, 1201, Intent(context, NotificationReceiver::class.java), PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = context!!.getSystemService(Context.ALARM_SERVICE) as AlarmManager

        alarmManager.cancel(pendingIntent)

        // In this case, notifications are enabled and the notifications is not persistent.
        if (sharedPreferences.getBoolean("notificationsSwitch", true) && !sharedPreferences.getBoolean("persistentNotification", false)) {
            NotificationManagerCompat.from(context!!).cancel(1201)

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()

            when (Integer.parseInt(sharedPreferences.getString("notificationsTime", "0")!!)) {
                0 -> calendar.set(Calendar.HOUR_OF_DAY, 9)
                1 -> calendar.set(Calendar.HOUR_OF_DAY, 12)
                2 -> calendar.set(Calendar.HOUR_OF_DAY, 18)
            }

            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, (1000 * 60 * 60 * 24).toLong(), pendingIntent)
        }

        // In this case, notifications are enabled, and the notification is persistent.
        else if (sharedPreferences.getBoolean("notificationsSwitch", true) && sharedPreferences.getBoolean("persistentNotification", false)) {
            (context as Activity).sendBroadcast(Intent(context, NotificationReceiver::class.java))
        }

        // In this case, either notificationsSwitch and persistentNotification are false.
        else if (!sharedPreferences.getBoolean("notificationsSwitch", true) || !sharedPreferences.getBoolean("persistentNotification", false)) {
            NotificationManagerCompat.from(context!!).cancel(1201)
        }
    }

    private fun updateEnabling() {
        if (sharedPreferences.getBoolean("notificationsSwitch", true)) {
            findPreference("apticFeedback").isEnabled = !sharedPreferences.getBoolean("persistentNotification", false)
            findPreference("notificationsTime").isEnabled = !sharedPreferences.getBoolean("persistentNotification", false)
            findPreference("persistentNotification").isEnabled = true
        } else {
            findPreference("apticFeedback").isEnabled = false
            findPreference("notificationsTime").isEnabled = false
            findPreference("persistentNotification").isEnabled = false
        }
    }
}
