package gq.emiliodallatorre.oneaday.iterator

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceManager
import java.util.*
import android.support.v4.content.ContextCompat.getSystemService

val BROADCAST = "gq.emiliodallatorre.oneaday.app.android.action.broadcast"

class MainActivity: AppCompatActivity(), MainFragment.OnFragmentInteractionListener, DashboardFragment.OnFragmentInteractionListener {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        val intentFilter = IntentFilter(BROADCAST)
        registerReceiver(NotificationReceiver(), intentFilter)

        when (item.itemId) {
            R.id.navigation_home -> {
                fragmentTransaction.replace(R.id.fragment, MainFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                fragmentTransaction.replace(R.id.fragment, DashboardFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
                sendBroadcast(Intent(BROADCAST))
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                fragmentTransaction.replace(R.id.fragment, SettingsFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        System.out.println(Calendar.getInstance().get(Calendar.DAY_OF_MONTH).toString())

        if(PreferenceManager.getDefaultSharedPreferences(this).getBoolean("firstStart", true)) {
            val preferences = PreferenceManager.getDefaultSharedPreferences(this)
            val editor = preferences.edit()
            editor.putInt("startDay", Calendar.getInstance().get(Calendar.DAY_OF_MONTH))
            editor.putInt("startMonth", Calendar.getInstance().get(Calendar.MONTH))
            editor.putBoolean("firstStart", false)
            editor.apply()

            val pendingIntent = PendingIntent.getBroadcast(this, 1201, Intent(BROADCAST), PendingIntent.FLAG_UPDATE_CURRENT)
            val alarmManager = this.getSystemService(Context.ALARM_SERVICE) as AlarmManager

            val calendar = Calendar.getInstance()
            calendar.timeInMillis = System.currentTimeMillis()

            when (PreferenceManager.getDefaultSharedPreferences(this).getInt("notificationsTime", 0)) {
                0 -> calendar.set(Calendar.HOUR_OF_DAY, 9)
                1 -> calendar.set(Calendar.HOUR_OF_DAY, 12)
                2 -> calendar.set(Calendar.HOUR_OF_DAY, 18)
            }

            calendar.set(Calendar.MINUTE, 0)
            calendar.set(Calendar.SECOND, 0)

            alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, calendar.timeInMillis, (1000 * 60 * 60 * 24).toLong(), pendingIntent)
        }

        // Load main fragment at start.
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.add(R.id.fragment, MainFragment())
        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        findViewById<BottomNavigationView>(R.id.navigation).setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onFragmentInteraction(uri: Uri) {
        // TODO: To be understood.
    }
}
