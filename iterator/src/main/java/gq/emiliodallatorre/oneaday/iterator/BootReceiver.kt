package gq.emiliodallatorre.oneaday.iterator

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v7.preference.PreferenceManager
import java.util.*

class BootReceiver : BroadcastReceiver() {

    // TODO: Fix security issue.
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving a BOOT_COMPLETED broadcast.
        val pendingIntent = PendingIntent.getBroadcast(context, 1201, Intent(BROADCAST), PendingIntent.FLAG_UPDATE_CURRENT)
        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

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
