package gq.emiliodallatorre.oneaday.iterator.notifications

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.support.v7.preference.PreferenceManager
import java.util.*

class BootReceiver: BroadcastReceiver() {
    // TODO: Fix security issue.
    override fun onReceive(context: Context, intent: Intent) {
        if (intent.action == "android.intent.action.BOOT_COMPLETED") {
            // This method is called when the BroadcastReceiver is receiving a BOOT_COMPLETED broadcast.
            if(!PreferenceManager.getDefaultSharedPreferences(context).getBoolean("persistentNotification", false)) {
                val pendingIntent = PendingIntent.getBroadcast(context, 1201, Intent(context, NotificationReceiver::class.java), PendingIntent.FLAG_UPDATE_CURRENT)
                val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager

                val calendar = Calendar.getInstance()
                calendar.timeInMillis = System.currentTimeMillis()

                when (PreferenceManager.getDefaultSharedPreferences(context).getInt("notificationsTime", 0)) {
                    0 -> calendar.set(Calendar.HOUR_OF_DAY, 9)
                    1 -> calendar.set(Calendar.HOUR_OF_DAY, 15)
                    2 -> calendar.set(Calendar.HOUR_OF_DAY, 18)
                }
                calendar.set(Calendar.MINUTE, 37)
                calendar.set(Calendar.SECOND, 0)

                alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, (1000 * 60 * 60 * 24).toLong(), pendingIntent)
            } else {
                context.sendBroadcast(Intent(context, NotificationReceiver::class.java))
            }
        }
    }
}
