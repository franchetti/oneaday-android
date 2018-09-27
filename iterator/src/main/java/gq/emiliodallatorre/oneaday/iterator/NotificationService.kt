package gq.emiliodallatorre.oneaday.iterator

import android.app.IntentService
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.os.Build
import android.support.v4.app.NotificationCompat
import android.support.v4.app.NotificationManagerCompat
import android.support.v4.content.ContextCompat.getSystemService

class NotificationService : IntentService("NotificationService") {

    override fun onHandleIntent(intent: Intent) {
        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        createNotificationChannel(this)
        val mBuilder = NotificationCompat.Builder(this, "oneADay-notifications")
                .setSmallIcon(R.drawable.ic_launcher)
                .setContentTitle(getString(R.string.notifications_title))
                .setContentText(getString(R.string.notifications_subtitle))
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        val notificationManager = NotificationManagerCompat.from(this)

        // TODO: Define notification ID.
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
