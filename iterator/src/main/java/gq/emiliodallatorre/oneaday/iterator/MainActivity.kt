package gq.emiliodallatorre.oneaday.iterator

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.support.v7.preference.PreferenceManager
import java.util.*


class MainActivity: AppCompatActivity(), MainFragment.OnFragmentInteractionListener, DashboardFragment.OnFragmentInteractionListener {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()


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
            editor.putBoolean("firstStart", false)
            editor.apply()
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
