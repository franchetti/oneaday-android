package gq.emiliodallatorre.oneaday.iterator

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v7.preference.PreferenceManager
import java.util.*

class NotificationReceiver: BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val action = Intent(context, MainActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        }


        System.out.println(PreferenceManager.getDefaultSharedPreferences(context).getBoolean("persistentNotification", false).toString() + " +++++++++++")
        val pendingIntent: PendingIntent = PendingIntent.getActivity(context, 0, action, 0)

        createNotificationChannel(context)

        val mBuilder = NotificationCompat.Builder(context, "oneADay-notifications")
                .setSmallIcon(R.drawable.ic_notification)
                .setLargeIcon(BitmapFactory.decodeResource(context.resources, R.drawable.ic_launcher))
                .setColor(ResourcesCompat.getColor(context.resources, R.color.colorPrimaryDark, null))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setContentIntent(pendingIntent)

        if(PreferenceManager.getDefaultSharedPreferences(context).getBoolean("persistentNotification", false)) {
            mBuilder.setContentTitle(context.resources.getStringArray(R.array.advicesTitle)[Calendar.getInstance().get(Calendar.DAY_OF_MONTH) - PreferenceManager.getDefaultSharedPreferences(context).getInt("startDay", 1)])
            mBuilder.setOngoing(true)
        } else {
            mBuilder.setAutoCancel(true)
            mBuilder.setContentTitle(context.getString(R.string.notifications_title))
            mBuilder.setContentText(context.getString(R.string.notifications_subtitle))
        }

        // Actually show the notification.
        val notificationManager = NotificationManagerCompat.from(context)
        notificationManager.notify(1201, mBuilder.build())
    }

    // Create a channel for the notifications, required since Android Oreo.
    private fun createNotificationChannel(context: Context) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = "One a Day"
            val description = "Channel for notification from \"One a Day\" app."
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel("oneADay-notifications", name, importance)
            channel.enableVibration(PreferenceManager.getDefaultSharedPreferences(context).getBoolean("apticFeedback", false))
            channel.description = description
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = ContextCompat.getSystemService<NotificationManager>(context, NotificationManager::class.java)
            notificationManager!!.createNotificationChannel(channel)
        }
    }
}
