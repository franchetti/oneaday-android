package gq.emiliodallatorre.oneaday.iterator

import android.net.Uri
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v7.app.AppCompatActivity
import android.widget.TextView

class MainActivity : AppCompatActivity(), MainFragment.OnFragmentInteractionListener, DashboardFragment.OnFragmentInteractionListener {

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        when (item.itemId) {
            R.id.navigation_home -> {
                findViewById<TextView>(R.id.message).text = getString(R.string.title_home)
                fragmentTransaction.add(R.id.fragment_main, MainFragment())
                fragmentTransaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                fragmentTransaction.replace(R.id.fragment_main, DashboardFragment())
                fragmentTransaction.addToBackStack(null)
                fragmentTransaction.commit()
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                findViewById<TextView>(R.id.message).text = getString(R.string.title_notifications)
                val fragment = MainFragment()
                fragmentTransaction.add(R.id.fragment_main, fragment)
                fragmentTransaction.commit()
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        findViewById<BottomNavigationView>(R.id.navigation).setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)
    }

    override fun onFragmentInteraction(uri: Uri) {
        // TODO: To be understood.
    }
}
