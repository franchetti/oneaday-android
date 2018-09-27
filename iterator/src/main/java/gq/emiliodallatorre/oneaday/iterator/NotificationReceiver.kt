package gq.emiliodallatorre.oneaday.iterator

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {

        // This method is called when the BroadcastReceiver is receiving an Intent broadcast.
        val notify = Intent(context, NotificationService::class.java)
        context.startService(notify)
    }
}
