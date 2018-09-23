package gq.emiliodallatorre.oneaday.iterator

import android.app.Activity
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat.getSystemService

class NotificationService : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        createNotificationChannel(context)
        val mBuilder = NotificationCompat.Builder(context, "oneADay-notifications")
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle((context as Activity).getString(R.string.notifications_title))
                .setContentText((context as Activity).getString(R.string.notifications_subtitle))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        val notificationManager = NotificationManagerCompat.from(context)

// notificationId is a unique int for each notification that you must define
        notificationManager.notify(1, mBuilder.build())
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
            channel.description = description
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            val notificationManager = getSystemService<NotificationManager>(context, NotificationManager::class.java)
            notificationManager!!.createNotificationChannel(channel)
        }
    }
}
