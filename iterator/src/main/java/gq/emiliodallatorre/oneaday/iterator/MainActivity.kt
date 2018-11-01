package gq.emiliodallatorre.oneaday.iterator

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceManager
import android.widget.Toast
import java.util.*

class MainActivity: AppCompatActivity(), MainFragment.OnFragmentInteractionListener, DashboardFragment.OnFragmentInteractionListener {
    private val fragmentManager = supportFragmentManager

    private val mainFragment = MainFragment()
    private val dashboardFragment = DashboardFragment()
    private val settingsFragment = SettingsFragment()
    private var selectedFragment: Any = mainFragment

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->

        when (item.itemId) {
            R.id.navigation_home -> {
                fragmentManager.beginTransaction()
                        .hide(selectedFragment as Fragment)
                        .show(mainFragment)
                        .commit()
                selectedFragment = mainFragment
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                fragmentManager.beginTransaction()
                        .hide(selectedFragment as Fragment)
                        .show(dashboardFragment)
                        .commit()
                selectedFragment = dashboardFragment
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                fragmentManager.beginTransaction()
                        .hide(selectedFragment as Fragment)
                        .show(settingsFragment)
                        .commit()
                selectedFragment = settingsFragment
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

            val pendingIntent = PendingIntent.getBroadcast(this, 1201, Intent(this, NotificationReceiver::class.java), PendingIntent.FLAG_UPDATE_CURRENT)
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

        fragmentTransaction.add(R.id.fragment, mainFragment)
        fragmentTransaction.add(R.id.fragment, dashboardFragment)
        fragmentTransaction.add(R.id.fragment, settingsFragment)

        fragmentTransaction.hide(dashboardFragment)
        fragmentTransaction.hide(settingsFragment)

        fragmentTransaction.show(mainFragment)

        fragmentTransaction.addToBackStack(null)
        fragmentTransaction.commit()

        findViewById<BottomNavigationView>(R.id.navigation).setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onFragmentInteraction(uri: Uri) {
        // TODO: To be understood.
    }

    // This handles the back button, to prevent the user from seeing the blank fragment.
    private var doubleBackToExitPressedOnce: Boolean = true
    override fun onBackPressed() {
        if (!doubleBackToExitPressedOnce) {
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
                // Only if on Lollipop or newer versions.
                finishAndRemoveTask()
            } else {
                // Only if on a version older than Lollipop.
                finishAffinity()
            }
        }

        this.doubleBackToExitPressedOnce = false
        Toast.makeText(this, getString(R.string.press_exit), Toast.LENGTH_SHORT).show()

        Handler().postDelayed({ doubleBackToExitPressedOnce = true }, 1000)
    }
}
